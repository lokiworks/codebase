---
id: build-monorepo
title: 构建单体仓库
author: Loki
author_title: SE
author_url: https://lokiworks.github.io
author_image_url: https://avatars1.githubusercontent.com/u/16199375?v=4
tags: [design]
---

# 构建单体仓库
本文主要介绍在单体仓库上的一些思考及实践，至于是选择单仓还是多仓，需要根据实际的情况去做选择。

## 优势
* **代码共享和重用**
> 因为只有一个仓库，每个人都可以看到其他人写的代码。在写新的工具或者通用功能时可以在仓库中查找是不是已经有现成的代码了，避免重复的造轮子

* **单一版本管理**
> 可以直接依赖其他团队的代码， 而不是使用其提供的类库。使用类库会存在着钻石依赖问题。
![alt](../img/monorepo_f8.jpg)
* **方便重构**
> 可以使用重构工具，对仓库中的类、方法等进行重命名。而不用担心有任何遗漏的地方


## Git单体仓库上的实践
```
实验环境: Win10
git版本: 2.26.2
VFSForGit 版本: 1.0.20112.1
克隆对象:https://github.com/torvalds/linux

```

* 部分克隆和稀疏检出
> 从2.25.0开始支持的特性，**部分克隆**可以避免下载不必要的对象，从而减少下载的时间。**稀疏检出** 客户端可以检出特定的目录。通常是两者搭配使用
```shell
$ git clone --filter=blob:none --no-checkout https://github.com/torvalds/linux
$ cd linux/
$ git sparse-checkout init --cone
$ git sparse-checkout set init/usr # 这里只检出init和usr目录
```

* VFSForGit
> 微软开发的产品，本地的Git库副本都是虚拟化的。只包含元数据和必要的源代码文件。
![alt](../img/gvfs-architecture.png)
```shell
$ gvfs clone https://lokiworks@dev.azure.com/lokiworks/linux/_git/linux # 只需要将git命令换成gvfs，因为github目前不支持gvfs协议，所以将linux代码克隆到个人的代码仓库下
$ cd src/ # 所有的代码文件全都放在src文件夹下
```

## 结语
* 单体仓库固然好，但是并不适合每一个企业
* git目前在大型仓库上支持的并不好。即使使用了**部分克隆**、**稀疏检出**，克隆的速度依然很慢
* VFSForGit固然很好，但是目前也只有Azure DevOps、Bitbucket支持gvfs协议。希望未来在github、gitlab平台上也能使用gvfs。

## 参考
* https://cacm.acm.org/magazines/2016/7/204032-why-google-stores-billions-of-lines-of-code-in-a-single-repository/fulltext
* https://github.com/microsoft/VFSForGit
* https://git-scm.com
* https://github.blog/2020-01-17-bring-your-monorepo-down-to-size-with-sparse-checkout/
* https://docs.microsoft.com/en-us/azure/devops/learn/git/git-at-scale


### 联系方式
 * 欢迎订阅我的公众号，这里主要会发表些软件工程上的一些想法及实践
![微信公众号](../img/weixin.jpg)

* [邮箱号](loki.yen@outlook.com) 欢迎通过邮件的方式与我交流
* [Microsoft Teams](https://teams.microsoft.com)  账号同邮箱号，欢迎通过Teams与我交流
* [Github Issues](https://github.com/lokiworks/design/issues) 欢迎通过issues的方式与我交流
* [我的博客](lokiworks.github.io)