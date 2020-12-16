#include "coding.h"
#include "gtest/gtest.h"

#include <cstdint>
#include <cstring>
#include <string>

TEST(Coding, Fixed32)
{
    char buf[32] = {0};
    uint32_t expectal = 12;
    EncodeFixed32(buf, expectal);
    uint32_t actual = DecodeFixed32(buf);
    ASSERT_EQ(actual, expectal);
}

TEST(Coding, Fixed64)
{
    char buf[64] = {0};
    uint64_t expectal = 666;
    EncodeFixed64(buf, expectal);
    uint64_t actual = DecodeFixed64(buf);
    ASSERT_EQ(actual, expectal);
}

TEST(Coding, Varint32)
{
    std::string s;
    uint32_t expectal = 12;
    PutVarint32(&s, expectal);
    uint32_t actual;
    GetVarint32(&s, &actual);
    ASSERT_EQ(actual, expectal);
}

TEST(Coding, Varint64)
{
    std::string s;
    uint64_t expectal = 666;
    PutVarint64(&s, expectal);
    uint64_t actual;
    GetVarint64(&s, &actual);
    ASSERT_EQ(actual, expectal);
}

int main(int argc, char **argv)
{
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}
