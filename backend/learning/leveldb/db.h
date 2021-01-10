//
// Created by loki on 2021/1/7.
//

#ifndef DB_H_
#define DB_H_

#include <cstddef>
#include <string>
#include "backend/learning/leveldb/options.h"
#include "backend/learning/leveldb/slice.h"

class WriteBatch;
class Snapshot;

class DB {
public:

    static size_t Open(const Options &options, const std::string &name, DB **dbptr);

    DB() = default;

    DB(const DB &) = delete;

    DB &operator=(const DB &) = delete;

    virtual ~DB();

    virtual size_t Put(const WriteOptions &options,
                       const Slice &key, const Slice &value) = 0;

    virtual size_t  Delete(const WriteOptions & options, const Slice&key) = 0;

    virtual size_t  Write(const WriteOptions& options, WriteBatch* updates) = 0;

    virtual size_t Get(const ReadOptions & options, const Slice& key, std::string* value) = 0;



};


#endif //DB_H_
