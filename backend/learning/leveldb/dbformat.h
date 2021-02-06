//
// Created by loki on 2021/1/24.
//

#ifndef DBFORMAT_H_
#define DBFORMAT_H_

#include <cstddef>
#include <cassert>
#include "comparator.h"

#include "backend/learning/leveldb/slice.h"
typedef uint64_t SequenceNumber;

class InternalKeyComparator: public Comparator{
private:
    const Comparator* user_comparator_;

public:
    InternalKeyComparator(const  Comparator*c):user_comparator_(c){}
    int Compare(const Slice& a, const  Slice & b) const override ;

};

class LookupKey{
public:
    LookupKey(const Slice& user_key, SequenceNumber sequence);

    Slice memtable_key() const {return Slice(start_, end_-start_);}
    Slice internal_key() const {return Slice(kstart_, end_-kstart_);}
    Slice user_key() const {return Slice(kstart_, end_-kstart_-8);}
private:
    //  |<------------LookupKey------------->|
    //  +----------+--------------+-------------+
    //  ｜key_size ｜  key_data    ｜   seqNum    ｜
    //  ｜         ｜<--user_key-->｜             ｜
    //  ｜         ｜<-------internal_key-------->｜
    //  ｜<------------- memtable_key------------>｜
    const char * start_;
    const char * kstart_;
    const char* end_;
    char* space_[200];

};


#endif //DBFORMAT_H_
