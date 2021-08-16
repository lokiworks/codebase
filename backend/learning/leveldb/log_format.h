//
// Created by loki on 2021/2/15.
//

#ifndef LOG_FORMAT_H_
#define LOG_FORMAT_H_

enum RecordType{
    // zero is reserved for preallocated files
    kZeroType = 0,
    kFullType = 1,

    // For fragments
    kFirstType = 2,
    kMiddleType = 3,
    kLastType = 4

};

// 32kb
static const int kBlockSize = 32768;
// header is checksum(4 bytes) , length(2 bytes) , type (1 byte)
static const int kHeaderSize = 4 + 2 + 1;

#endif //LOG_FORMAT_H_
