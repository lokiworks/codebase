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

#include "write_batch.h"
#include <cstddef>

// WriteBatch header has an 8-byte sequence number followed by a 4-byte count.
static const size_t kHeader = 12;

void WriteBatch::Clear(){
    rep_.clear();
    rep_.resize(kHeader);
}

