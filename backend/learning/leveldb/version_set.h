//
// Created by loki on 2021/1/15.
//

#ifndef CODEBASE_VERSION_SET_H
#define CODEBASE_VERSION_SET_H


#include <cstdint>
#include <cassert>
#include "backend/learning/leveldb/options.h"
#include "backend/learning/leveldb/dbformat.h"

class Version {
public:




    struct GetStats{

    };


    void Ref() {

    }

    void UnRef(){

    }

    size_t Get(const ReadOptions &, const LookupKey & key, std::string * val, GetStats* stats){
        return 0;
    }
    bool UpdateStats(const GetStats& stats){
        return false;
    }

};

class VersionSet {

public:

    Version *current() {
        return current_;
    }

    void SetLastSequence(uint64_t s) {
        assert(s >= last_sequence_);
        last_sequence_ = s;
    }

    uint64_t LastSequence() const {
        return last_sequence_;
    }

private:
    uint64_t last_sequence_;
    Version *current_;
};


#endif //CODEBASE_VERSION_SET_H
