//
// Created by loki on 2021/1/15.
//

#ifndef CODEBASE_VERSION_SET_H
#define CODEBASE_VERSION_SET_H


#include <cstdint>
#include <cassert>

class VersionSet {

public:
    void SetLastSequence(uint64_t s){
        assert(s >= last_sequence_);
        last_sequence_ = s;
    }

private:
    uint64_t last_sequence_;
};


#endif //CODEBASE_VERSION_SET_H
