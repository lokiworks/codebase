//
// Created by loki on 2021/1/10.
//

#ifndef DB_IMPL_H_
#define DB_IMPL_H_


#include <mutex>
#include "backend/learning/leveldb/db.h"

class DBImpl : public DB {
public:
    DBImpl(const Options &options, const std::string &dbname);


    DBImpl(const DBImpl &db) = delete ;
    DBImpl& operator=(const DBImpl &)  = delete ;

    ~DBImpl() override;

    size_t Put(const WriteOptions &options, const Slice &key, const Slice &value) override;

    size_t Delete(const WriteOptions &options, const Slice &key) override;

    size_t Write(const WriteOptions &options, WriteBatch *updates) override;

    size_t Get(const ReadOptions &options, const Slice &key, std::string *value) override;


private:
    friend class DB;
    struct Writer;



    std::mutex mutex_;


};


#endif //DB_IMPL_H_
