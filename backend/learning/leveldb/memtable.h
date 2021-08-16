//
// Created by loki on 2021/1/19.
//

#ifndef MEMTABLE_H_
#define MEMTABLE_H_


#include "dbformat.h"
#include "backend/learning/leveldb/skiplist.h"


class InternalKeyComparator;


class MemTable {
public:
    explicit MemTable(const InternalKeyComparator &comparator);

    MemTable(const MemTable &) = delete;

    MemTable &operator=(const MemTable &) = delete;

    void Ref() {
        ++refs_;
    }

    void UnRef(){
        --refs_;
    }

    bool Get(const LookupKey& key, std::string * value, size_t* s);

private:
    struct KeyComparator {
        const InternalKeyComparator comparator;

        explicit KeyComparator(const InternalKeyComparator &c) : comparator(c) {};

        int operator()(const char *a, const char *b) const;
    };

    typedef SkipList<const char *, KeyComparator> Table;

    ~MemTable();

    KeyComparator comparator_;
    int refs_;
    Arena arena_;
    Table table_;


};


#endif //MEMTABLE_H_
