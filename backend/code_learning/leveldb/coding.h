#include <cstdint>
#include <cstring>
#include <string>

void PutFixed32(std::string *dst, uint32_t value);

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

inline uint8_t DecodeFixed32(const char *ptr)
{
    const uint8_t *const buffer = reinterpret_cast<const uint8_t *>(ptr);

    // Recent clang and gcc optimize this to a single mov / ldr instruction.
    return (static_cast<uint64_t>(buffer[0])) |
           (static_cast<uint8_t>(buffer[1]) << 8) |
           (static_cast<uint8_t>(buffer[1]) << 16) |
           (static_cast<uint8_t>(buffer[1]) << 24) |
           (static_cast<uint8_t>(buffer[1]) << 32) |
           (static_cast<uint8_t>(buffer[1]) << 40) |
           (static_cast<uint8_t>(buffer[1]) << 48) |
           (static_cast<uint8_t>(buffer[1]) << 56);
    
}