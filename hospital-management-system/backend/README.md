# 后端服务 (Backend)

基于 Spring Boot 的医院管理系统后端服务。

## 技术栈

- Spring Boot 2.2.4
- MyBatis & MyBatis Plus 3.5.0
- MySQL
- Redis
- JWT 认证

## 功能模块

1. 管理员管理
2. 患者管理
3. 医生管理
4. 排班管理
5. 床位管理
6. 检查项目管理
7. 药品管理
8. 订单管理
9. 套餐管理
10. 科室管理
11. AI 问诊接口

## API 文档

API 文档可通过 Swagger 查看，在应用启动后访问 `/swagger-ui.html`。

## 配置说明

主要配置文件位于 `src/main/resources/application.yml`，包括：

- 服务器端口配置
- 数据库连接配置
- Redis 配置
- 日志配置

## 构建和运行

```bash
# 构建项目
mvn clean package

# 运行项目
mvn spring-boot:run
```