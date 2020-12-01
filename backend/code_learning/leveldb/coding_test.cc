
#include "gtest/gtest.h"

#include <cstdint>
#include <cstring>
#include <string>


// Lower-level versions of Put... that write directly into character buffer
// REQUIRES: dst has enough space for the value being written
inline void EncodeFixed32(char *dst, uint32_t value)
{
    uint8_t *const buffer = reinterpret_cast<uint8_t *>(dst);
    // Recent clang and gcc optimize this to a single mov / str instruction.
    buffer[0] = static_cast<uint8_t>(value);
    buffer[1] = static_cast<uint8_t>(value >> 8);
    buffer[2] = static_cast<uint8_t>(value >> 16);
    buffer[3] = static_cast<uint8_t>(value >> 24);
}

inline void EncodeFixed64(char *dst, uint64_t value)
{
    uint8_t *const buffer = reinterpret_cast<uint8_t *>(dst);
    // Recent clang and gcc optimize this to a single mov / str instruction.
    buffer[0] = static_cast<uint8_t>(value);
    buffer[1] = static_cast<uint8_t>(value >> 8);
    buffer[2] = static_cast<uint8_t>(value >> 16);
    buffer[3] = static_cast<uint8_t>(value >> 24);
    buffer[4] = static_cast<uint8_t>(value >> 32);
    buffer[5] = static_cast<uint8_t>(value >> 40);
    buffer[6] = static_cast<uint8_t>(value >> 48);
    buffer[7] = static_cast<uint8_t>(value >> 56);
}

inline uint32_t DecodeFixed32(const char *ptr)
{
    const uint8_t *const buffer = reinterpret_cast<const uint8_t *>(ptr);

    // Recent clang and gcc optimize this to a single mov / ldr instruction.
    return (static_cast<uint32_t>(buffer[0])) |
           (static_cast<uint32_t>(buffer[1]) << 8) |
           (static_cast<uint32_t>(buffer[2]) << 16) |
           (static_cast<uint32_t>(buffer[3]) << 24);
}

inline uint64_t DecodeFixed64(const char *ptr)
{
    const uint8_t *const buffer = reinterpret_cast<const uint8_t *>(ptr);

    // Recent clang and gcc optimize this to a single mov / ldr instruction.
    return (static_cast<uint64_t>(buffer[0])) |
           (static_cast<uint64_t>(buffer[1]) << 8) |
           (static_cast<uint64_t>(buffer[2]) << 16) |
           (static_cast<uint64_t>(buffer[3]) << 24) |
           (static_cast<uint64_t>(buffer[4]) << 32) |
           (static_cast<uint64_t>(buffer[5]) << 40) |
           (static_cast<uint64_t>(buffer[6]) << 48) |
           (static_cast<uint64_t>(buffer[7]) << 56);
}

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

int main(int argc, char **argv)
{
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}