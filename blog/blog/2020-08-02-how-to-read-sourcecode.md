---
id: how-to-read-sourcecode
title: 如何阅读源码
author: Loki
author_title: SE
author_url: https://lokiworks.github.io
author_image_url: https://avatars1.githubusercontent.com/u/16199375?v=4
tags: [source code]
---

# 如何阅读源码
出于某一些原因（工作的需要、个人技术上的自我提升）,时常需要阅读一些开源项目的源码。面对一大坨的代码，该如何阅读源码？ 

## 目的
首先，我是不建议上来就阅读源码的。很多功能点是可以参照官方文档，以做实验的方式去学习、掌握、加深印象的。通常我阅读源码的目的主要有：
- 学习架构上的设计、单测、集测的编写以及文档的书写
- 加深在某一技术栈上知识的积累
- 在其现有功能基础上做定制化开发

## 选型
可以选择工作中常用的开源项目或者未来一段时间内非常有前景的开源项目。刚开始的时候不建议读中大型项目的源码，原因有几点：
- 会消耗大量的时间，投入产出比不成正比
- 需要更高的素质、更广的知识面
- 打击积极性

## 工具
使用自己熟悉的工具，好的工具会令你事半功倍。通常我用到的工具有：
- **Visual Code** 轻量级文本编辑器，其提供了很多语言的插件。 
- **Visual Studio** 主要用来阅读C++、C#的代码，它的debug功能是我目前用过的工具中最好用的
- **Jetbrains全家桶** 主要用来阅读java、go、python的代码
- **Visual Paradigm** 主要用来绘制类图、时序图


## 方法
好的方法，有助于快速的阅读源码，达到自己预期的效果。通过我用到的方法有：
- **调试** 通过debug可以知道程序执行过程中的交互流程
- **打日志** 开启debug级别的日志，通过观察日志的输出，猜出程序的大致执行流程
- **识别** 识别流程中的主要步骤，过滤次要步骤。先理清程序的骨架，再逐一击破
- **跑用例** 一般好的开源项目中，都会提供丰富的用例。可以通过跑用例，了解某一功能的使用、实现逻辑

## 案例
java开发中通常会用到mybatis，mybatis作为ORM框架，有着简单易用等特点。 这里以mybatis-3的源码举例，整个mybatis不含测试用例的代码量为37189，如果没有一定的方法看着万行代码还是挺累的。

### 核心功能
- **加载配置** 加载配置的过程，就是为了构造Configuration类，mybatis中关键的几个类，都跟Configuration类有着千丝万缕的关系。
![alt](../img/加载配置.jpg)
![alt](../img/加载配置_seq.jpg)
- **执行SQL** 通过SqlSession、Executor、StatementHandler这几个类的协作，最终通过Statement完成SQL的执行。
![alt](../img/执行SQL.jpg)
![alt](../img/执行SQL_seq.jpg)


## 结语
这里不鼓励阅读源码，很多时候可以通过做实验的方式掌握某一门知识。通过**调试**、**打日志**、**识别**、**跑用例**几种方式，便于我们阅读源码，掌握其核心功能。




### 联系方式
 * 欢迎订阅我的公众号，这里主要会发表些软件工程上的一些想法及实践
![微信公众号](../img/weixin.jpg)

* [邮箱号](loki.yen@outlook.com) 欢迎通过邮件的方式与我交流
* [Microsoft Teams](https://teams.microsoft.com)  账号同邮箱号，欢迎通过Teams与我交流
* [Github Issues](https://github.com/lokiworks/design/issues) 欢迎通过issues的方式与我交流
* [我的博客](lokiworks.github.io)