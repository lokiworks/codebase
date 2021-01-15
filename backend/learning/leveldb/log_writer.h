//
// Created by loki on 2021/1/14.
//

#ifndef LOG_WRITER_H_
#define LOG_WRITER_H_


#include <cstdint>
#include <cstddef>
#include "backend/learning/leveldb/slice.h"

class WritableFile;


namespace log{


    class Writer{
    public:
        explicit Writer(WritableFile* dest);
        // Create a writer that will append data to "*dest"
        // "*dest" must have initial length "dest_length"
        // "*dest" must remain live while this Writer is in use.
        Writer(WritableFile* dest, uint64_t dest_length);

        Writer(const Writer&) = delete ;
        Writer& operator=(const Writer&) = delete ;

        ~Writer();

        size_t AddRecord(const Slice & slice);
    };
}

#endif //LOG_WRITER_H_
