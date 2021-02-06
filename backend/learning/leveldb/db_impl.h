//
// Created by loki on 2021/1/10.
//

#ifndef DB_IMPL_H_
#define DB_IMPL_H_


#include <mutex>
#include <deque>
#include "backend/learning/leveldb/db.h"
#include "backend/learning/leveldb/thread_annotations.h"
#include "backend/learning/leveldb/log_writer.h"


class MemTable;
class VersionSet;


class DBImpl : public DB {
public:
    DBImpl(const Options &options, const std::string &dbname);


    DBImpl(const DBImpl &db) = delete;

    DBImpl &operator=(const DBImpl &) = delete;

    ~DBImpl() override;

    size_t Put(const WriteOptions &options, const Slice &key, const Slice &value) override;

    size_t Delete(const WriteOptions &options, const Slice &key) override;

    size_t Write(const WriteOptions &options, WriteBatch *updates) override;

    size_t Get(const ReadOptions &options, const Slice &key, std::string *value) override;


    size_t MakeRoomForWrite(bool force) EXCLUSIVE_LOCKS_REQUIRED(mutex_);

    void RecordBackgroundError(const size_t s);


private:
    friend class DB;

    struct Writer;


    std::deque<Writer *> writes_ GUARDED_BY(mutex_);

    size_t bg_error_ GUARDED_BY(mutex_);


    log::Writer *log_;
    std::mutex mutex_;
    MemTable* mem_;
    MemTable* imm_ GUARDED_BY(mutex_);
    WriteBatch* tmp_batch_ GUARDED_BY(mutex_);

    VersionSet* const versions_ GUARDED_BY(mutex_);

    void MaybeScheduleCompaction();




};


#endif //DB_IMPL_H_
