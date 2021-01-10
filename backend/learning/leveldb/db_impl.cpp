//
// Created by loki on 2021/1/10.
//

#include "db_impl.h"
#include "write_batch.h"


struct DBImpl::Writer{

    explicit Writer(std::mutex * mu)
    :batch(nullptr),
    sync(false),
    done(false)
    {

    }

    size_t  status;
    WriteBatch* batch;
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


