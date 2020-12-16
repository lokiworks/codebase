#include "coding.h"
#include "slice.h"

bool GetVarint32(std::string *input, uint32_t *value)
{
    const char *p = input->data();
    const char *limit = p + input->size();
    const char *q = GetVarint32Ptr(p, limit, value);
    if (q == nullptr)
    {
        return false;
    }
    else
    {
        *input = input->substr(q - p);
        return true;
    }
}
bool GetVarint64(std::string *input, uint64_t *value)
{
    const char *p = input->data();
    const char *limit = p + input->size();
    const char *q = GetVarint64Ptr(p, limit, value);
    if (q == nullptr)
    {
        return false;
    }
    else
    {
        *input = input->substr(q - p);
        return true;
    }
}
void PutFixed32(std::string *dst, uint32_t value)
{
    char buf[sizeof(value)];
    EncodeFixed32(buf, value);
    dst->append(buf, sizeof(buf));
}
void PutFixed64(std::string *dst, uint64_t value)
{
    char buf[sizeof(value)];
    EncodeFixed64(buf, value);
    dst->append(buf, sizeof(value));
}
void PutVarint32(std::string *dst, uint32_t value)
{
    char buf[sizeof(value)];
    char *ptr = EncodeVarint32(buf, value);
    dst->append(buf, sizeof(buf));
}

void PutVarint64(std::string *dst, uint64_t value)
{
    char buf[sizeof(value)];
    char *ptr = EncodeVarint64(buf, value);
    dst->append(buf, sizeof(buf));
}

char *EncodeVarint32(char *dst, uint32_t v)
{
    static const int B = 128;
    // Operate on characters as unsigneds
    uint8_t *ptr = reinterpret_cast<uint8_t *>(dst);
    while (v >= B)
    {
        *(ptr++) = v | B;
        v >>= 7;
    }
    *(ptr++) = static_cast<uint8_t>(v);
    return reinterpret_cast<char *>(ptr);
}
char *EncodeVarint64(char *dst, uint64_t v)
{
    static const int B = 128;
    // Operate on characters as unsigneds
    uint8_t *ptr = reinterpret_cast<uint8_t *>(dst);
    while (v >= B)
    {
        *(ptr++) = v | B;
        v >>= 7;
    }
    *(ptr++) = static_cast<uint8_t>(v);
    return reinterpret_cast<char *>(ptr);
}

const char *GetVarint64Ptr(const char *p, const char *limit, uint64_t *v)
{
    uint64_t result = 0;
    for (uint32_t shift = 0; shift <= 56 && p < limit; shift += 7)
    {
        uint64_t byte = *(reinterpret_cast<const uint8_t *>(p));
        p++;
        if (byte & 128)
        {
            // More byte are present
            result |= ((byte & 127) << shift);
        }
        else
        {
            result |= (byte << shift);
            *v = result;
            return reinterpret_cast<const char *>(p);
        }
    }
    return nullptr;
}

const char *GetVarint32PtrFallback(const char *p, const char *limit, uint32_t *value)
{
    uint32_t result = 0;
    for (uint32_t shift = 0; shift <= 28 && p < limit; shift += 7)
    {
        uint32_t byte = *(reinterpret_cast<const uint8_t *>(p));
        p++;
        if (byte & 128)
        {
            // More byte are present
            result |= ((byte & 127) << shift);
        }
        else
        {
            result |= (byte << shift);
            *value = result;
            return reinterpret_cast<const char *>(p);
        }
    }
    return nullptr;
}

void PutLengthPrefixedSlice(std::string *dst, const Slice &value)
{
    PutVarint32(dst, value.size());
    dst->append(value.data(), value.size());
}

bool GetLengthPrefixedSlice(Slice *input, Slice *result)
{
    uint32_t len;
    if (GetVarint32(input, &len) && input->size() >= len)
    {
        *result = Slice(input->data(), len);
        input->remove_prefix(len);
        return true;
    }
    else
    {
        return false;
    }
}

bool GetVarint32(Slice *input, uint32_t *value)
{
    const char *p = input->data();
    const char *limit = p + input->size();
    const char *q = GetVarint32Ptr(p, limit, value);
    if (q == nullptr)
    {
        return false;
    }
    else
    {
        *input = Slice(q, limit - q);
        return true;
    }
}
bool GetVarint64(Slice *input, uint64_t *value)
{
    const char *p = input->data();
    const char *limit = p + input->size();
    const char *q = GetVarint64Ptr(p, limit, value);
    if (q == nullptr)
    {
        return false;
    }
    else
    {
        *input = Slice(q, limit - q);
        return true;
    }
}