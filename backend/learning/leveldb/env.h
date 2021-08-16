//
// Created by loki on 2021/2/17.
//

#ifndef ENV_H_
#define ENV_H_


#include <cstddef>
#include "backend/learning/leveldb/slice.h"

class WritableFile{
    public:
    WritableFile() = default;
    WritableFile(const WritableFile&) = delete;
    WritableFile& operator=(const WritableFile&) = delete;

    virtual ~WritableFile();

    virtual size_t Append(const Slice& data) = 0;
    virtual size_t Close() = 0;
    virtual size_t Flush() = 0;
    virtual size_t Sync() = 0;
};

#endif //ENV_H_
