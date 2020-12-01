load("//third_party/gtest:workspace.bzl", gtest = "repo")
load("//third_party/junit:workspace.bzl", junit = "repo")

def initialize_third_party():
    gtest()
    junit()


def codebase_reporitories():
    initialize_third_party()