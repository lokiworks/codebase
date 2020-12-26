---
slug: git-engineering-practices
title: Git工程实践
author: Loki
author_title: SE
author_url: https://lokiworks.github.io
author_image_url: https://avatars1.githubusercontent.com/u/16199375?v=4
tags: [vcs]
---
# Git工程实践
我使用过的版本管理工具主要有：SVN、Git，这几年Git社区的发展势头要远超过SVN，Git已成为版本工具事实的标准，Git是每一个工程师必须掌握的技能，这里总结下Git使用上的一些心得与经验。

---

## 最佳实践
* ```git help``` 列出常用的git命令
```shell
These are common Git commands used in various situations:

start a working area (see also: git help tutorial)
   clone      Clone a repository into a new directory
   init       Create an empty Git repository or reinitialize an existing one

work on the current change (see also: git help everyday)
   add        Add file contents to the index
   mv         Move or rename a file, a directory, or a symlink
   reset      Reset current HEAD to the specified state
   rm         Remove files from the working tree and from the index

examine the history and state (see also: git help revisions)
   bisect     Use binary search to find the commit that introduced a bug
   grep       Print lines matching a pattern
   log        Show commit logs
   show       Show various types of objects
   status     Show the working tree status

grow, mark and tweak your common history
   branch     List, create, or delete branches
   checkout   Switch branches or restore working tree files
   commit     Record changes to the repository
   diff       Show changes between commits, commit and working tree, etc
   merge      Join two or more development histories together
   rebase     Reapply commits on top of another base tip
   tag        Create, list, delete or verify a tag object signed with GPG

collaborate (see also: git help workflows)
   fetch      Download objects and refs from another repository
   pull       Fetch from and integrate with another repository or a local branch
   push       Update remote refs along with associated objects
```
* ```git ls-files -s``` 与 ```git cat-file -p 2d832d9044c698081e59c322d5a2a459da546469```配合使用，用来查看所有已暂存的文件
* ```git ls-files -m```与 ```git diff h.txt```配合使用，用来查看已修改但未暂存的文件
```diff
diff --git a/h.txt b/h.txt
index 2d832d9..10f825a 100644
--- a/h.txt
+++ b/h.txt
@@ -1 +1,2 @@
 hello,world
+hello world
```
  