---
slug: database-version-management
title: 工程实践之数据库脚本版本管理
author: Loki
author_title: SE
author_url: https://lokiworks.github.io
author_image_url: https://avatars1.githubusercontent.com/u/16199375?v=4
tags: [source code]
---

已经好久没写博客了，最近一直在研读leveldb的源码，进展不是很顺利。想要写出一篇自己的见解还要一段时间。正好最近在数据库脚本版本管理控制方面有一些自己的想法与实践，拿出来与各位分享。

## 需求
* 数据库脚本可以用git进行版本管理
* 数据库脚本可以像普通代码一样纳入code review流程(Gitlab、Gerrit等)进行脚本合规的审查
* 数据库脚本可以与CI集成，作为pipeline中的一个环节。通过与上一版本进行比较,生成待上线的脚本及对应的回退脚本。

## 思路
关于数据库脚本版本管理方面的工具，我在网上也搜了很多工具，比如：flyway、liquibase、uml建模工具等，但是这些工具都不是我想要的。这里简单讲下我的思路：

1. 在代码仓库下创建数据库脚本的仓库进行脚本的维护
2. 每个系统一个文件夹,文件夹下放入系统英文名称.sql的文件(比如订单系统叫oms，则脚本文件的名称为oms.sql)
3. 脚本文件中只放创建表语句，每次脚本的修改，只是对表、列的属性做增删改。

基于以上，实际需要的能够解析DDL中建表语句的工具，通过对比两个版本的DDL生成对应的diff文件。基于这样的诉求，在github上找到了这样的工具，工具的名称叫[schemalex](https://github.com/schemalex/schemalex)

## 实践
1. 本地环境创建个文件夹并初始化git仓库
```bash
$ mkdir demo
$ cd demo
$ git init

```
2. 创建demo.sql文件，初始数据为

```sql
CREATE TABLE student (
 id INTEGER NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id)
);


```
3. 将demo.sql文件保存到本地工作区并打上tag
```bash
git add .
git commit -m 'add demo.sql'
git tag -a v1.0 -m 'add demo.sql'
```
4. 修改demo.sql，修改后的数据为
```sql
CREATE TABLE student (
    id INTEGER NOT NULL AUTO_INCREMENT,
    name VARCHAR (64) NOT NULL DEFAULT "",
    PRIMARY KEY (id)
);

CREATE TABLE course (
    id INTEGER NOT NULL AUTO_INCREMENT,
    name VARCHAR (64) NOT NULL DEFAULT "",
    PRIMARY KEY (id)
);
```
5. 保存文件到本地工作区
```sql
git add .
git commit -m 'modify demo.sql'

```
6. 执行脚本`./gen.sh /root/demo demo`，脚本的第一个参数为数据库脚本文件夹的全路径，第二个参数为数据库脚本名称
```bash
#!/bin/bash
#
set -e

dst_dir=$1
sys_name=$2
if [ -z "$sys_name" ]; then
    echo "usage gen_script.sh dst_dir sys_name"
    exit 1
fi

script_file=${sys_name}.sql

cur_dir=$(pwd)
cd "$dst_dir"

if [ ! -f "$script_file" ]; then
    echo "$script_file not be found"
    exit 1
fi


# test git command
if ! command -v git &> /dev/null; then
    echo "git cound not be found"
    exit 1
fi



if ! git checkout master &> /dev/null; then
    echo "checkout master failed"
    exit 1
fi

current_file=$(mktemp)

if ! cp "$script_file" "$current_file" &> /dev/null; then
    echo "copy $script_file $current_file failed"
    exit 1
fi


# list latest tag
latest_tag=$(git describe --tags "$(git rev-list --tags --max-count=1)")

if [ -z "$latest_tag" ]; then
    echo "current repo no tag."
    exit 1
fi

if ! git checkout "$latest_tag" &> /dev/null; then
    echo "checkout tags failed"
    exit 1
fi

tag_file=$(mktemp)
if ! cp "$script_file" "$tag_file" &> /dev/null; then
    echo "copy $script_file $tag_file failed"
    exit 1
fi

rollback=$(schemalex "$current_file" "$tag_file" )
echo "$rollback" > "$cur_dir"/rollback.sql

diff=$(schemalex "$tag_file" "$current_file" )
echo "$diff" > "$cur_dir"/diff.sql

```
7. 最终会生成两个文件:diff.sql和rollback.sql
* diff.sql
```sql
BEGIN;

SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE `course` (
`id` INT (11) NOT NULL AUTO_INCREMENT,
`name` VARCHAR (64) NOT NULL DEFAULT '',
PRIMARY KEY (`id`)
);

ALTER TABLE `student` ADD COLUMN `name` VARCHAR (64) NOT NULL DEFAULT '' AFTER `id`;

SET FOREIGN_KEY_CHECKS = 1;

COMMIT;
```
* rollback.sql
```sql
BEGIN;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE `course`;

ALTER TABLE `student` DROP COLUMN `name`;

SET FOREIGN_KEY_CHECKS = 1;

COMMIT;
```

## 结语
虽然schemalex对脚本语句上有一些限制，但是基本满足了我对数据库版本管理的诉求，以上是我在数据库版本管理上的一些实践，供各位参考。





### 联系方式
 * 欢迎订阅我的公众号，这里主要会发表些软件工程上的一些想法及实践
![微信公众号](../static/img/weixin.jpg)