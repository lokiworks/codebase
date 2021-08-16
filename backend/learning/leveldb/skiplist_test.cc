//
// Created by loki on 2021/1/23.
//

#include "gtest/gtest.h"
#include "backend/learning/leveldb/skiplist.h"


typedef uint64_t Key;

struct Comparator {
    int operator()(const Key &a, const Key &b) const{
        if (a < b) {
            return -1;
        } else if (a > b) {
            return +1;
        } else {
            return 0;
        }
    }
};


TEST(SkipList, InsertAndLookup) {
    Arena arena;
    Comparator cmp;
    SkipList<Key, Comparator> list(cmp, &arena);
    for (int i = 0; i < 5; ++i) {
        list.Insert(i+1);
    }
    for (int i = 0; i < 5; ++i) {
        ASSERT_TRUE(list.Contains(i+1));
    }


}