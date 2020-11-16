<!-- TOC -->

- [1. IAM](#1-iam)
  - [1.1. 交互流程](#11-%e4%ba%a4%e4%ba%92%e6%b5%81%e7%a8%8b)
- [设计细节](#%e8%ae%be%e8%ae%a1%e7%bb%86%e8%8a%82)
  - [路径匹配算法](#%e8%b7%af%e5%be%84%e5%8c%b9%e9%85%8d%e7%ae%97%e6%b3%95)

<!-- /TOC -->
# 1. IAM
全称为身份认证及访问控制管理系统，是企业所有系统中最重要的一环。
* **iam** 负责存放资源的元信息
* **services** 业务系统在iam平台中配置资源及对应的权限
* **client** client在iam平台中申请token及资源的权限
* **unified-config-management** iam将元信息同步到配置管理平台
## 1.1. 交互流程
* client 发起资源的请求，请求在api-gateway处拦截
* api-gateway内部通过iam-client查询client是否有访问资源的权限,iam-client通过unified-config-management接收iam-server的元数据，并将数据分别存入文件和内存。
如果查询的结果为无权限则返回
* api-gateway将请求转发到services
* services内部通过iam-services查询client对应的行、列权限，iam-client通过unified-config-management接收iam-server的元数据，并将数据分别存入文件和内存。
* 根据返回行、列权限进行数据的处理，并将处理后的结果返回该api-gateway
* api-gateway对数据做一些后置处理，并将处理后的结果返回给client

# 设计细节
## 路径匹配算法
当一个请求过来时，需要检查该请求是否有该资源的操作权限时，需要有套算法能够快速计算出有无操作权限。前缀树算法能够满足路径匹配的要求
- [算法的实现](draft/src/main/java/com/loki/iam/PathTrie.java)
- [测试用例](draft/src/test/java/com/loki/iam/PathTrieTest.java)
