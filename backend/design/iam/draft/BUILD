load("@rules_java//java:defs.bzl", "java_library", "java_test")

package(default_visibility = ["//visibility:public"])

java_library(
    name = "iam-lib",
    srcs = glob(["src/main/java/**/*.java"]),
    resources = glob(["src/main/resources/**"]),
    deps = [
        "@maven//:com_google_guava_guava",
    ],
)
load("//:junit5.bzl", "java_junit5_test")
java_junit5_test(
    name = "iam-tests",
    srcs = glob(["src/test/java/**/*.java"]),
    test_package = "com.loki.iam",
    deps = [
        ":iam-lib",
        "@maven//:com_google_truth_extensions_truth_java8_extension",
        "@maven//:com_google_truth_truth",
    ],
)
