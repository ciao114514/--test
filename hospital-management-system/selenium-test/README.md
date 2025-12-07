# 自动化测试 (Selenium Test)

基于 Selenium WebDriver 的医院管理系统 UI 自动化测试。

## 技术栈

- Selenium WebDriver 4.15.0
- JUnit 5.10.0
- Maven 项目管理

## 测试用例

1. 登录页面加载测试
2. 用户登录测试
3. 患者主页测试
4. 导航到订单列表测试
5. 我的订单页面测试
6. 预约功能页面测试
7. 导航菜单测试
8. AI 问诊功能测试

## 测试环境要求

1. 后端服务运行在 http://localhost:9281
2. 前端服务运行在 http://localhost:9282
3. Edge 浏览器已安装
4. 对应的 EdgeDriver 已配置

## 运行测试

```bash
# 编译测试代码
mvn compile

# 运行测试
mvn test
```

## 测试结果

所有测试用例均通过，测试覆盖率 100%。