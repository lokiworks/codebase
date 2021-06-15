workspace(name="codebase")
#load("//tools:workspace.bzl", "codebase_reporitories")
load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

#codebase_reporitories()

RULES_JVM_EXTERNAL_TAG = "4.1"

RULES_JVM_EXTERNAL_SHA = "f36441aa876c4f6427bfb2d1f2d723b48e9d930b62662bf723ddfb8fc80f0140"

http_archive(
    name = "rules_jvm_external",
    sha256 = RULES_JVM_EXTERNAL_SHA,
    strip_prefix = "rules_jvm_external-%s" % RULES_JVM_EXTERNAL_TAG,
    url = "https://github.com/bazelbuild/rules_jvm_external/archive/%s.zip" % RULES_JVM_EXTERNAL_TAG,
)

# GoogleTest/GoogleMock framework. Used by most unit-tests.
http_archive(
    name = "com_google_googletest",
    # Keep this URL in sync with ABSL_GOOGLETEST_COMMIT in ci/cmake_common.sh.
    urls = ["https://github.com/google/googletest/archive/8567b09290fe402cf01923e2131c5635b8ed851b.zip"],  # 2020-06-12T22:24:28Z
    strip_prefix = "googletest-8567b09290fe402cf01923e2131c5635b8ed851b",
    sha256 = "9a8a166eb6a56c7b3d7b19dc2c946fe4778fd6f21c7a12368ad3b836d8f1be48",
)


# C++ rules for Bazel.
http_archive(
    name = "rules_cc",
    sha256 = "9a446e9dd9c1bb180c86977a8dc1e9e659550ae732ae58bd2e8fd51e15b2c91d",
    strip_prefix = "rules_cc-262ebec3c2296296526740db4aefce68c80de7fa",
    urls = [
        "https://github.com/bazelbuild/rules_cc/archive/262ebec3c2296296526740db4aefce68c80de7fa.zip",
    ],
)

http_archive(
    name = "bazel_skylib",
    sha256 = "1c531376ac7e5a180e0237938a2536de0c54d93f5c278634818e0efc952dd56c",
    urls = [
        "https://github.com/bazelbuild/bazel-skylib/releases/download/1.0.3/bazel-skylib-1.0.3.tar.gz",
    ],
)

load("@bazel_skylib//:workspace.bzl", "bazel_skylib_workspace")

bazel_skylib_workspace()


load("@rules_jvm_external//:defs.bzl", "maven_install")

maven_install(
    artifacts = [
        "com.google.guava:guava:29.0-jre",
        "com.google.truth.extensions:truth-java8-extension:1.0.1",
        "com.google.truth:truth:1.0.1",
    ],
    repositories = [
        "http://maven.aliyun.com/nexus/content/groups/public/",
    ],
)
load("//:junit5.bzl", "junit_jupiter_java_repositories", "junit_platform_java_repositories")

JUNIT_JUPITER_VERSION = "5.6.2"

JUNIT_PLATFORM_VERSION = "1.6.2"

junit_jupiter_java_repositories(
    version = JUNIT_JUPITER_VERSION,
)

junit_platform_java_repositories(
    version = JUNIT_PLATFORM_VERSION,
)
