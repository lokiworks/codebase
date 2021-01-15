load("@rules_cc//cc:defs.bzl", "cc_library", "cc_test")

package(default_visibility = ["//visibility:public"])

cc_library(
    name="coding",
    srcs=glob(["coding.cc"]),
    hdrs=glob(["coding.h", "slice.h"])

)

cc_library(
    name="write_batch",
    srcs=glob(["write_batch.cc"]),
    hdrs=glob(["write_batch.h", "write_batch_internal.h", "slice.h", "coding.h"])
)

cc_library(
    name = "log_writer",
    srcs = glob(["log_writer.cc"]),
    hdrs = glob(["log_writer.h", "slice.h"]),
)

cc_library(
    name = "version_set",
    srcs = glob(["version_set.cc"]),
    hdrs = glob(["version_set.h"]),
)

cc_library(
    name = "db_impl",
    srcs = glob(["db_impl.cpp"]),
    hdrs = glob(["db.h", "db_impl.h", "write_batch.h", "version_set.h", "options.h", "thread_annotations.h"]),
    deps=[
        ":write_batch",
        ":version_set",
        ":log_writer"
    ]
)


cc_test(
    name = "coding_test",
    srcs = glob(["coding_test.cc"]),
    deps = [
        ":coding",
        "@com_google_googletest//:gtest_main",
    ],
)

cc_test(
    name = "slice_test",
    srcs = glob(["slice.h", "slice_test.cc"]),
    deps = [
        "@com_google_googletest//:gtest_main",
    ],
)

cc_test(
    name="write_batch_test",
    srcs=glob(["write_batch_test.cc"]),
    deps=[
        ":write_batch",
        ":coding",
        "@com_google_googletest//:gtest_main",
    ],
)