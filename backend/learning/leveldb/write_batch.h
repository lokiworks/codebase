#ifndef WRITE_BATCH_H_
#define WRITE_BATCH_H_

#include <string>

class Slice;

enum ValueType { kTypeDeletion = 0x0, kTypeValue = 0x1 };

class WriteBatch
{
public:

    WriteBatch();
    WriteBatch(const WriteBatch &) = default;
    WriteBatch &operator=(const WriteBatch &) = default;
    ~WriteBatch();
    // Store the mapping "key->value" in the database.
    void Put(const Slice &key, const Slice &value);
    // If the database contains a mapping for "key", erase it. Else do nothing.
    void Delete(const Slice &key);

    // Clear all updates buffered in this batch.
    void Clear();

    void Append(const WriteBatch &source);

private:

    friend class WriteBatchInternal;
    std::string rep_;
};


#endif