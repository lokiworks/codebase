#ifndef WRITE_BATCH_INTERNAL_H_
#define WRITE_BATCH_INTERNAL_H_


#include <cstddef>
#include "write_batch.h"
typedef uint64_t SequenceNumber;

class MemTable;

// WriteBatchInternal provides static methods for manipulating a
// WriteBatch that we don't want in the public WriteBatch interface.
class WriteBatchInternal
{

public:
    // Return the number of entries in the batch.
    static int Count(const WriteBatch *batch);
    // Set the count for the nubmer of entries in the batch.
    static void SetCount(WriteBatch *batch, int n);

    // Return the sequence number for the start of this batch.
    static SequenceNumber Sequence(const WriteBatch *batch);
    // Store the specified number as the sequence number for the start of
    // this batch.
    static void SetSequence(WriteBatch *batch, SequenceNumber seq);


    static Slice Contents(const WriteBatch * batch);

    static size_t ByteSize(const WriteBatch * batch);

    static void SetContents(WriteBatch * batch, const Slice& contents);

    static void Append(WriteBatch *dst, const WriteBatch *src);

    static  size_t  InsertInto(const WriteBatch* batch, MemTable* memTable);

};


#endif