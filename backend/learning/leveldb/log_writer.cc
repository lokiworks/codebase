//
// Created by loki on 2021/1/15.
//



#include "backend/learning/leveldb/log_writer.h"

log::Writer::Writer(WritableFile *dest) {

}

log::Writer::Writer(WritableFile *dest, uint64_t dest_length) {

}

log::Writer::~Writer() {

}

size_t log::Writer::AddRecord(const Slice &slice) {
    return 0;
}

