# 阿里Bizworks产品使用体验

# Bizworks介绍

这里不得不提阿里的TMF2.0，整体TMF2.0的介绍可以参看****毗卢****的这篇文章：

[Developing a Trading Platform that Processes 325,000 Transactions per Second](https://alibabatech.medium.com/developing-a-trading-platform-for-the-worlds-largest-online-marketplace-7fe4e90b7ed9)

借用网上的一幅图介绍TMF2.0整体的所阐述的思想：

![Untitled](%E9%98%BF%E9%87%8CBizworks%E4%BA%A7%E5%93%81%E4%BD%BF%E7%94%A8%E4%BD%93%E9%AA%8C%20a618bec9b813416fb62ee9b8fe2360ba/Untitled.png)

回到今天的主角：BizWorks，是阿里将内部构建业务中台的最佳实践，以开放产品的形式将其能力对外输出。

# Bizworks的使用

这里使用了Bizworks的示例项目，整个概览如图：

![Untitled](%E9%98%BF%E9%87%8CBizworks%E4%BA%A7%E5%93%81%E4%BD%BF%E7%94%A8%E4%BD%93%E9%AA%8C%20a618bec9b813416fb62ee9b8fe2360ba/Untitled.jpeg)

整个Bizworks涵盖了产品研发过程的整个生命周期，整个portal分为：建模、应用、测试、运维、运营共5个版块。

## 建模版块

建模版块中主要分为两块技术建模以及模型的导入导出。这里着重介绍技术建模，技术建模中分为业领域以及商业能力的建模

### 业务域

业务域是基于业务场景与业务规则进行领域划分，抽象出来的业务单元，业务域是一个相对自闭环的能实现一定业务功能的整体。示例的业务域中分别用用户域、商品域作为演示。如下图所示：

![Untitled](%E9%98%BF%E9%87%8CBizworks%E4%BA%A7%E5%93%81%E4%BD%BF%E7%94%A8%E4%BD%93%E9%AA%8C%20a618bec9b813416fb62ee9b8fe2360ba/Untitled%201.jpeg)

以商品域为示例，产品分为：领域模型、数据模型、应用服务、对象字段映射、关联商业能力

- 领域建模

![Untitled](%E9%98%BF%E9%87%8CBizworks%E4%BA%A7%E5%93%81%E4%BD%BF%E7%94%A8%E4%BD%93%E9%AA%8C%20a618bec9b813416fb62ee9b8fe2360ba/Untitled%202.jpeg)

在领域建模阶段，提供了模型设计器用于可视化建模，如下图所示：

![Untitled](%E9%98%BF%E9%87%8CBizworks%E4%BA%A7%E5%93%81%E4%BD%BF%E7%94%A8%E4%BD%93%E9%AA%8C%20a618bec9b813416fb62ee9b8fe2360ba/Untitled%203.jpeg)

每个领域实体可进行基本信息、对象属性、对象行为、对象关系的建模。这里1⃣️商品SKU为例，如下图所示：

![Untitled](%E9%98%BF%E9%87%8CBizworks%E4%BA%A7%E5%93%81%E4%BD%BF%E7%94%A8%E4%BD%93%E9%AA%8C%20a618bec9b813416fb62ee9b8fe2360ba/Untitled%204.jpeg)

![Untitled](%E9%98%BF%E9%87%8CBizworks%E4%BA%A7%E5%93%81%E4%BD%BF%E7%94%A8%E4%BD%93%E9%AA%8C%20a618bec9b813416fb62ee9b8fe2360ba/Untitled%201.png)

数据类型分为：基本类型（String、Integer、Double等）、聚合类型（List、Set、Map）、对象类型（可以关联到当前设计器中的所有对象）

### 数据模型

![Untitled](%E9%98%BF%E9%87%8CBizworks%E4%BA%A7%E5%93%81%E4%BD%BF%E7%94%A8%E4%BD%93%E9%AA%8C%20a618bec9b813416fb62ee9b8fe2360ba/Untitled%205.jpeg)

在数模设计阶段，提供了设计器用于数模的可视化设计，如下图所示：

![Untitled](%E9%98%BF%E9%87%8CBizworks%E4%BA%A7%E5%93%81%E4%BD%BF%E7%94%A8%E4%BD%93%E9%AA%8C%20a618bec9b813416fb62ee9b8fe2360ba/Untitled%206.jpeg)

### 应用服务

示例商品域中，提供了三个服务：商品服务、商品SKU服务、产品服务。如下图所示：

![Untitled](%E9%98%BF%E9%87%8CBizworks%E4%BA%A7%E5%93%81%E4%BD%BF%E7%94%A8%E4%BD%93%E9%AA%8C%20a618bec9b813416fb62ee9b8fe2360ba/Untitled%207.jpeg)

![Untitled](%E9%98%BF%E9%87%8CBizworks%E4%BA%A7%E5%93%81%E4%BD%BF%E7%94%A8%E4%BD%93%E9%AA%8C%20a618bec9b813416fb62ee9b8fe2360ba/Untitled%208.jpeg)

![Untitled](%E9%98%BF%E9%87%8CBizworks%E4%BA%A7%E5%93%81%E4%BD%BF%E7%94%A8%E4%BD%93%E9%AA%8C%20a618bec9b813416fb62ee9b8fe2360ba/Untitled%209.jpeg)

![Untitled](%E9%98%BF%E9%87%8CBizworks%E4%BA%A7%E5%93%81%E4%BD%BF%E7%94%A8%E4%BD%93%E9%AA%8C%20a618bec9b813416fb62ee9b8fe2360ba/Untitled%2010.jpeg)

### 商业能力

将业务场景以结构化的方式沉淀到平台，通过流程设计器，将业务场景可视。

![Untitled](%E9%98%BF%E9%87%8CBizworks%E4%BA%A7%E5%93%81%E4%BD%BF%E7%94%A8%E4%BD%93%E9%AA%8C%20a618bec9b813416fb62ee9b8fe2360ba/Untitled%2011.jpeg)

整个商业能力设计阶段，分为商业能力流程、商业能力服务、结构对象、依赖业务域四个版块。

一个商业能力可能由多个业务域构成一个完整的能力。示例中以商品发布举例：

![Untitled](%E9%98%BF%E9%87%8CBizworks%E4%BA%A7%E5%93%81%E4%BD%BF%E7%94%A8%E4%BD%93%E9%AA%8C%20a618bec9b813416fb62ee9b8fe2360ba/Untitled%2012.jpeg)

整个商业能力（业务流程）由多个业务活动组成，设计器中提供的流程节点有：开始、业务活动、决策节点、注释节点、结束。

业务活动中可关联多个业务域中的多个应用服务，如下图所示：

![Untitled](%E9%98%BF%E9%87%8CBizworks%E4%BA%A7%E5%93%81%E4%BD%BF%E7%94%A8%E4%BD%93%E9%AA%8C%20a618bec9b813416fb62ee9b8fe2360ba/Untitled%2013.jpeg)

## 应用版块

罗列当前项目中的所有应用，每个应用可独立部署与运维。

![Untitled](%E9%98%BF%E9%87%8CBizworks%E4%BA%A7%E5%93%81%E4%BD%BF%E7%94%A8%E4%BD%93%E9%AA%8C%20a618bec9b813416fb62ee9b8fe2360ba/Untitled%2014.jpeg)

以商品中心为示例，如下图所示：

![Untitled](%E9%98%BF%E9%87%8CBizworks%E4%BA%A7%E5%93%81%E4%BD%BF%E7%94%A8%E4%BD%93%E9%AA%8C%20a618bec9b813416fb62ee9b8fe2360ba/Untitled%2015.jpeg)

![Untitled](%E9%98%BF%E9%87%8CBizworks%E4%BA%A7%E5%93%81%E4%BD%BF%E7%94%A8%E4%BD%93%E9%AA%8C%20a618bec9b813416fb62ee9b8fe2360ba/Untitled%2016.jpeg)

## 运营版块

![Untitled](%E9%98%BF%E9%87%8CBizworks%E4%BA%A7%E5%93%81%E4%BD%BF%E7%94%A8%E4%BD%93%E9%AA%8C%20a618bec9b813416fb62ee9b8fe2360ba/Untitled%2017.jpeg)

应用发布后，商业能力的视图如下：

![Untitled](%E9%98%BF%E9%87%8CBizworks%E4%BA%A7%E5%93%81%E4%BD%BF%E7%94%A8%E4%BD%93%E9%AA%8C%20a618bec9b813416fb62ee9b8fe2360ba/Untitled%2018.jpeg)

至此，整个Bizworks产品流程基本跑完了。这里加深了TMF2.0的产品设计理念，感谢**Chris Richardson、屹远**、****毗卢****三位，在不同阶段带我领略了不同的产品，开拓了我的视野。