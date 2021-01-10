//
// Created by loki on 2021/1/7.
//

#ifndef OPTIONS_H_
#define OPTIONS_H_

#include <cstddef>

class Comparator;

class Snapshot;


struct Options {
    // Create an Options object with default values for all fields
    Options() = default;

    // --------------------------
    // Parameters that affect behavior
    const Comparator *comparator;

    // If true, the database will be created if it is missing.
    bool create_if_missing = false;

    bool error_if_exists = false;

    bool paranoid_checks = false;

    size_t write_buffer_size = 4 * 1024 * 1024;

    int max_open_files = 1000;


    size_t block_size = 4 * 1024;

    int block_restart_interval = 16;

    size_t max_file_size = 2 * 1024 * 1024;

    bool reuse_log = false;

};

struct ReadOptions {
    ReadOptions() = default;

    bool verify_checksums = false;

    bool fill_cache = true;


};

struct WriteOptions {
    WriteOptions() = default;

    bool sync = false;
};

#endif //OPTIONS_H_


