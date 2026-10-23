<div align="center">

## 本次功能更新：库存ABC价值分层

按年用量乘单位成本排序，累计价值占比生成ABC分类，识别重点物料；按分类前累计份额划分80%与95%边界，不直接生成补货单。 登录后从「业务计算工具」进入，支持真实后端校验与结果导出。详见[接口、示例与边界说明](docs/AI_BUSINESS_UPGRADE_20261023.md)。本次不改变既有许可及联系方式。

# ZhuaTech InventoryAI

[简体中文](README.md) | [English](README.en.md)

### 智能库存健康、补货与滞销处置社区版

[知华科技官网](https://www.zhuatech.cn/) · 上海如静知华信息科技有限公司

</div>

## 本次升级：补货点与在途库存净缺口

按日需求、提前期和需求标准差计算安全库存及补货点，扣除预留后计入在途库存，整件需求向上取整。不生成采购订单。

新增已认证的结构化录入工作台（`/operations.html`），支持批量核算、逐条结果依据、分级复核和CSV/完整JSON导出。结果由真实后端算法计算，不是预置演示数字。所有数值精确传递，不调用外部模型。

这是**只读核算**：不写库、不审批、不自动执行财务、签署、销毁或设备控制。输入条件满足不是执行授权。[算法、输入边界与接口说明](docs/OPERATIONS-REVIEW.md)。

InventoryAI 连接库存、在途、日均需求、采购交期、最后销售时间、效期和毛利数据，把分散的库存数字转换为 `REPLENISH / HOLD / REDUCE / LIQUIDATE` 四类可解释动作。

![库存健康运营中心](docs/images/inventoryai-admin.png)

## 从库存诊断到业务动作

| 业务问题 | 项目能力 |
| --- | --- |
| 会不会缺货 | 交期需求、安全缓冲、建议补货点与补货量 |
| 库存是否过量 | 库存覆盖天数和减量信号 |
| 是否已经滞销 | 长期无销售、超高覆盖和毛利信号 |
| 是否临期 | 剩余效期风险和人工处置审批 |

核心接口：`POST /api/ai/inventory/optimize`。默认实现完全本地运行，不需要外部模型密钥。

![库存分析师 H5 工作台](docs/images/inventoryai-h5.png)

## 项目组成

- 库存运营驾驶舱、仓群负荷和重点风险队列
- 商品组合、补货日历、库存策略、动作审批和健康分析
- 响应式业务工作台、库存反馈和重大风险升级
- Java 21、Spring Boot 4、JPA、MySQL、Flyway、JWT
- Vue 3、Pinia、Vue Router、Axios、Vite、Docker Compose
- JUnit、MockMvc、H2 和自动化构建配置

```bash
cd frontend
npm install
npm run dev:demo
```

访问 `http://localhost:5173`，管理端 `planner / Demo@2026`，分析师端 `operator / Demo@2026`。演示商品、库存、金额和人员均为虚构数据。后端包名：`cn.zhuatech.inventoryai`。

资料：[API](docs/api.md) · [架构](docs/architecture.md) · [数据库](docs/database.md) · [部署](deploy/README.md)

> [!WARNING]
> 本工程仅限个人非商业学习与技术交流，不得商用。企业内部部署、生产使用、SaaS、客户交付、品牌替换和收费服务须事先取得上海如静知华信息科技有限公司书面授权，详见 [LICENSE](LICENSE)。

需要 WMS/ERP/OMS 集成、库存优化、供应链 AI、软件外包或私有化开发，请联系[知华科技](https://www.zhuatech.cn/)：

| 方案咨询 | 商业合作 |
| --- | --- |
| ![微信咨询一](docs/images/zhuatech-wechat-consulting.png) | ![微信咨询二](docs/images/zhuatech-wechat-consulting-2.png) |

SEO：智能库存、库存优化、AI 补货、滞销库存、临期管理、Java Vue 源码、知华科技。

## 企业级智能补货发布

新增 `POST /api/enterprise/inventoryai/replenishment-plan-release`，覆盖库存、供应、提前期、安全库存、预算、风险、审批和撤回，返回 `RELEASE / PLANNER_REVIEW / BLOCKED`。详见 [补货发布说明](docs/ENTERPRISE_REPLENISHMENT_RELEASE.md)。
