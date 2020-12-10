#include "gtest/gtest.h"
#include "write_batch.h"
#include "write_batch_internal.h"

TEST(WriteBatchTest, Empty)
{
    WriteBatch b;
    ASSERT_EQ(0, WriteBatchInernal::Count(&b));
}