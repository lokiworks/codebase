//
// Created by loki on 2021/3/7.
//

#include "backend/learning/leveldb/env.h"

constexpr const size_t kWritableFileBufferSize = 65536;

class PosixWritableFile final : public WritableFile {
public:
    PosixWritableFile(std::string filename, int fd)
            : pos_(0), fd_(fd), is_manifest_() {

    }

    ~PosixWritableFile() override {

    }

    size_t Append(const Slice &data) override {
        return 0;
    }

    size_t Close() override {
        return 0;
    }

    size_t Flush() override {
        return 0;
    }

    size_t Sync() override {
        return 0;
    }

    static bool IsManifest(const std::string &filename) {
        return false;
    }
    static Slice Basename(const std::string &filename){
        std::string::size_type separator_pos = filename.rfind('/');
        if (separator_pos == std::string::npos){
            return Slice(filename);
        }
        assert(filename.find('/', separator_pos+1) == std::string::npos);
    }

private:
    char buf_[kWritableFileBufferSize];
    size_t pos_;
    int fd_;
    const bool is_manifest_;
    const std::string filename_;
    const std::string dirname_;
};