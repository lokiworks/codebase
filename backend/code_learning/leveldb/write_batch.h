#include <string>

class Slice;

class WriteBatch
{
public:
    class Handler
    {
    public:
        virtual ~Handler();
        virtual void Put(const Slice &key, const Slice &value) = 0;
        virtual void Delete(const Slice &key) = 0;
    };

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