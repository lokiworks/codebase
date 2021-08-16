//
// Created by loki on 2021/1/21.
//

#include "gtest/gtest.h"
#include "backend/learning/leveldb/arena.h"

TEST(ArenaTest, Demo) {
    Arena arena;

    char *c1 = arena.Allocate(1 << 4);
    char *c2 = arena.Allocate(1 << 5);
    char *c3 = arena.Allocate(1 << 4);
    ASSERT_EQ(c1 + (1 << 4), c2);
    ASSERT_EQ(c3 - (1 << 5), c2);

}

int main(int argc, char** argv){
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}
