//
// Created by loki on 2021/1/10.
//

#include "db_impl.h"
#include "write_batch.h"
#include "backend/learning/leveldb/write_batch_internal.h"
#include "backend/learning/leveldb/version_set.h"
#include <condition_variable>

struct DBImpl::Writer {

    explicit Writer(std::mutex *mu)
            : batch(nullptr),
              sync(false),
              done(false) {

    }

    size_t status;
    WriteBatch *batch;
    bool sync;
    bool done;
    std::condition_variable cv;
};

size_t DBImpl::Put(const WriteOptions &options, const Slice &key, const Slice &value) {
    return DB::Put(options, key, value);
}

size_t DBImpl::Delete(const WriteOptions &options, const Slice &key) {
    return 0;
}

size_t DBImpl::Write(const WriteOptions &options, WriteBatch *updates) {
    Writer w(&mutex_);
    w.batch = updates;
    w.sync = options.sync;
    w.done = false;

    std::lock_guard<std::mutex> guard(mutex_);
    writes_.push_back(&w);

    while (!w.done && &w != writes_.front()) {
        std::unique_lock<std::mutex> unique_lock(mutex_, std::adopt_lock);
        w.cv.wait(unique_lock);
        unique_lock.release();
    }

    if (w.done) {
        return w.status;
    }

    size_t status = MakeRoomForWrite(updates == nullptr);
    uint64_t last_sequence = 0;
    Writer * last_writer = &w;
    if (status == 0 && updates != nullptr){
        WriteBatch* write_batch = nullptr;
        WriteBatchInternal::SetSequence(write_batch, last_sequence + 1);
        last_sequence += WriteBatchInternal::Count(write_batch);

        // Add to log and apply to memtable. we can release the lock
        // during this phase since &w is currently responsible for logging
        // and protects against concurrent loggers and concurrent writes
        // into mem_.
        {
            mutex_.unlock();
            status = log_->AddRecord(WriteBatchInternal::Contents(write_batch));
            bool  sync_error = false;
            if (status == 0 && options.sync){
                // log file sync
                if (status){
                    sync_error = true;
                }
            }

            // 写入数据
            if (status == 0){
                status = WriteBatchInternal::InsertInto(write_batch, mem_);
            }

            mutex_.lock();

            if (sync_error){
                RecordBackgroundError(status);
            }
        }

        if (write_batch == tmp_batch_){
            tmp_batch_->Clear();
        }

        versions_->SetLastSequence(last_sequence);

    }

    while (true){
        Writer* ready = writes_.front();
        writes_.pop_front();
        if (ready != &w){
            ready->status = status;
            ready->done = true;
            ready->cv.notify_one();
        }

        if (ready == last_writer){
            break;
        }
    }


    // Notify new head of write queue
    if (!writes_.empty()){
        writes_.front()->cv.notify_one();
    }

    return status;
}

void DBImpl::RecordBackgroundError(size_t s) {

}

size_t DBImpl::MakeRoomForWrite(bool force) {

    return 0;
}

size_t DBImpl::Get(const ReadOptions &options, const Slice &key, std::string *value) {
    return 0;
}

size_t DB::Put(const WriteOptions &options, const Slice &key, const Slice &value) {
    WriteBatch batch;
    batch.Put(key, value);
    return Write(options, &batch);
}


