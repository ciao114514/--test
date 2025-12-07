# 医院管理系统 (Hospital Management System)

一个基于 Spring Boot + Vue 的医院管理系统，包含完整的前后端实现和自动化测试。

## 项目结构

```
hospital-management-system/
├── backend/         # 后端代码 (Spring Boot)
├── frontend/        # 前端代码 (Vue.js)
├── selenium-test/   # 自动化测试代码 (Selenium)
├── sql/            # 数据库脚本
└── docs/           # 文档资料
```

## 技术栈

### 后端技术栈
- Spring Boot
- MyBatis & MyBatis Plus
- MySQL
- Redis
- JWT 认证

### 前端技术栈
- Vue.js 2.x
- Element UI
- Vue Router
- Axios

### 测试技术栈
- Selenium WebDriver
- JUnit 5
- Maven

## 功能特性

1. 用户登录认证
2. 患者信息管理
3. 医生排班管理
4. 预约挂号系统
5. 住院管理
6. 检查检验管理
7. 药品管理
8. 套餐管理

## 自动化测试

项目包含完整的 UI 自动化测试，使用 Selenium 实现了以下测试用例：

1. 登录页面加载测试
2. 用户登录测试
3. 患者主页测试
4. 订单列表导航测试
5. 我的订单页面测试
6. 预约功能页面测试
7. 导航菜单测试
8. AI 问诊功能测试

所有测试均已通过，确保系统功能稳定可靠。

## 部署说明

### 后端部署
1. 确保已安装 Java 8+ 和 Maven
2. 创建 MySQL 数据库并导入 sql/hospital_manager.sql
3. 修改 backend/src/main/resources/application.yml 中的数据库配置
4. 在 backend 目录下运行 `mvn spring-boot:run`

### 前端部署
1. 确保已安装 Node.js 和 npm
2. 在 frontend 目录下运行 `npm install`
3. 运行 `npm run serve` 启动开发服务器

### 测试运行
1. 确保后端和前端服务已启动
2. 在 selenium-test 目录下运行 `mvn test`