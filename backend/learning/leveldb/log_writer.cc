//
// Created by loki on 2021/1/15.
//



#include "backend/learning/leveldb/log_writer.h"
#include "backend/learning/leveldb/env.h"


log::Writer::Writer(WritableFile *dest) {

}

log::Writer::Writer(WritableFile *dest, uint64_t dest_length) {

}

log::Writer::~Writer() {

}

size_t log::Writer::EmitPhysicalRecord(RecordType type, const char* ptr, size_t length){
    return 0;
}
size_t log::Writer::AddRecord(const Slice &slice) {
    const char * ptr = slice.data();
    size_t left = slice.size();
    size_t s;
    bool  begin = true;

    do {
        const int leftover = kBlockSize - block_offset_;
        assert(leftover>=0);
        if (leftover < kHeaderSize){
            if (leftover > 0){
                static_assert(kHeaderSize == 7, "");
                dest_->Append(Slice("\x00\x00\x00\x00\x00\x00", leftover));
            }
            block_offset_ = 0;
        }

        assert(kBlockSize - block_offset_-kHeaderSize >=0);
        const size_t avail = kBlockSize - block_offset_-kHeaderSize;
        const size_t fragment_length = (left < avail) ? left: avail;

        const  bool end = (left == fragment_length);
        RecordType type;
        if (begin && end){
            type = kFullType;
        }else if(begin){
            type = kFirstType;
        }else if(end){
            type = kLastType;
        }else{
            type = kMiddleType;
        }
        s = EmitPhysicalRecord(type, ptr, fragment_length);
        ptr += fragment_length;
        left -= fragment_length;
        begin = false;





    }while (s == 0 && left > 0);
    return s;



    return 0;
}

