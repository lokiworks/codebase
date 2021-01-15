// WriteBatch::rep_ :=
// sequence: fixed64
// count: fixed32
// data: record[count]
// record:=
// kTypeValue varstring varstring
// kTypeDeletion varstring
// varstring:=
// len: varint32
// data: uint8[len]
#include <cstddef>
#include "write_batch.h"
#include "write_batch_internal.h"
#include "coding.h"
#include "slice.h"

// WriteBatch header has an 8-byte sequence number followed by a 4-byte count.
static const size_t kHeader = 12;

WriteBatch::WriteBatch()
{
    Clear();
}

WriteBatch::~WriteBatch() = default;

int WriteBatchInternal::Count(const WriteBatch *b)
{
    return DecodeFixed32(b->rep_.data() + 8);
}

void WriteBatchInternal::SetCount(WriteBatch *b, int n)
{
    EncodeFixed32(&b->rep_[8], n);
}

SequenceNumber WriteBatchInternal::Sequence(const WriteBatch *b)
{
    return SequenceNumber(DecodeFixed64(b->rep_.data()));
}

void WriteBatchInternal::SetSequence(WriteBatch *b, SequenceNumber seq)
{
    EncodeFixed64(&b->rep_[0], seq);
}

Slice WriteBatchInternal::Contents(const WriteBatch *batch)
{
    return Slice(batch->rep_);
}

size_t WriteBatchInternal::ByteSize(const WriteBatch *batch)
{
    return batch->rep_.size();
}

void WriteBatchInternal::SetContents(WriteBatch *b, const Slice &contents)
{
    assert(contents.size() >= kHeader);
    b->rep_.assign(contents.data(), contents.size());
}

void WriteBatchInternal::Append(WriteBatch *dst, const WriteBatch *src)
{
    SetCount(dst, Count(dst) + Count(src));
    assert(src->rep_.size() >= kHeader);
    dst->rep_.append(src->rep_.data() + kHeader, src->rep_.size() - kHeader);
}

size_t  WriteBatchInternal::InsertInto(const WriteBatch* batch, MemTable* memTable){
    return 0;
}

void WriteBatch::Clear()
{
    rep_.clear();
    rep_.resize(kHeader);
}

void WriteBatch::Put(const Slice &key, const Slice &value)
{
    WriteBatchInternal::SetCount(this, WriteBatchInternal::Count(this) + 1);
    rep_.push_back(static_cast<char>(kTypeValue));
    PutLengthPrefixedSlice(&rep_, key);
    PutLengthPrefixedSlice(&rep_, value);
}

void WriteBatch::Delete(const Slice &key)
{
    WriteBatchInternal::SetCount(this, WriteBatchInternal::Count(this) + 1);
    rep_.push_back(static_cast<char>(kTypeDeletion));
    PutLengthPrefixedSlice(&rep_, key);
}

void WriteBatch::Append(const WriteBatch &source)
{
    WriteBatchInternal::Append(this, &source);
}
