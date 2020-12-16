#include "slice.h"
#include "gtest/gtest.h"

#include <cstdint>
#include <cstring>
#include <string>

TEST(Slice, size)
{
    Slice s = Slice(std::string("abc"));
    size_t expectal = 3;
    size_t actual = s.size();
    ASSERT_EQ(actual, expectal);
}

int main(int argc, char **argv)
{
    testing::InitGoogleTest(&argc, argv);
    return RUN_ALL_TESTS();
}
