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
    // 生产者消费者模型
    Writer w(&mutex_);
    w.batch = updates;
    w.sync = options.sync;
    w.done = false;
    // 尝试获取锁
    std::lock_guard<std::mutex> guard(mutex_);
    // 只有一个线程获取到锁，w插入到队列
    writes_.push_back(&w);
    // 只有w位于队列头部且w没完成才不用等待
    while (!w.done && &w != writes_.front()) {
        std::unique_lock<std::mutex> unique_lock(mutex_, std::adopt_lock);
        w.cv.wait(unique_lock);
        unique_lock.release();
    }
    // 被其他线程通过合并操作完成
    if (w.done) {
        return w.status;
    }

    size_t status = MakeRoomForWrite(updates == nullptr);
    uint64_t last_sequence = 0;
    Writer * last_writer = &w;
    if (status == 0 && updates != nullptr){
        // 合并队列中各个batch到一个新batch中
        WriteBatch* write_batch = nullptr;
        // 为合并后的batch赋上全局序列号
        WriteBatchInternal::SetSequence(write_batch, last_sequence + 1);
        // 计算新的全局序列号
        last_sequence += WriteBatchInternal::Count(write_batch);

        // 释放锁提高并发，其他线程可以将新的writer插入到队列中
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
            // 重新加锁
            mutex_.lock();

            if (sync_error){
                RecordBackgroundError(status);
            }
        }
        // 因为write_batch已经写入log和memtable，可以清空
        if (write_batch == tmp_batch_){
            tmp_batch_->Clear();
        }
        // 重新设置全局序列号
        versions_->SetLastSequence(last_sequence);

    }

    while (true){
        // 当前线程完成了其他线程的writer，只需唤醒这些已经完成的writer线程
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
    size_t size;
    std::lock_guard<std::mutex> l(mutex_);
    SequenceNumber  snapshot;
    snapshot = versions_->LastSequence();

    return 0;
}

size_t DB::Put(const WriteOptions &options, const Slice &key, const Slice &value) {
    WriteBatch batch;
    batch.Put(key, value);
    return Write(options, &batch);
}


