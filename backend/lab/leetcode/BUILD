load("@rules_java//java:defs.bzl", "java_library", "java_test")


package(default_visibility = ["//visibility:public"])

java_library(
    name = "leetcode-lib",
    srcs = glob(["src/main/java/**/*.java"]),
    resources = glob(["src/main/resources/**"]),
)


load("//:junit5.bzl", "java_junit5_test")
java_junit5_test(
    name = "leetcode-tests",
    srcs = glob(["src/test/java/**/*.java"]),
       test_package = "com.loki.lab.leetcode",
    deps = [
        ":leetcode-lib",

    ],
)
