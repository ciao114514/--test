# 前端界面 (Frontend)

基于 Vue.js 的医院管理系统前端界面。

## 技术栈

- Vue.js 2.6.11
- Element UI 2.15.0
- Vue Router 3.2.0
- Axios 0.21.1
- ECharts 4.8.0

## 功能页面

1. 登录页面
2. 管理员后台
3. 医生工作站
4. 患者主页
5. 预约挂号
6. 订单管理
7. 科室信息
8. AI 问诊

## 项目结构

```
src/
├── assets/          # 静态资源
├── components/      # 公共组件
├── router/          # 路由配置
├── utils/           # 工具函数
├── views/           # 页面组件
└── App.vue         # 根组件
```

## 开发指南

```bash
# 安装依赖
npm install

# 启动开发服务器
npm run serve

# 构建生产版本
npm run build
```

## 配置说明

- 开发服务器端口: 9282
- API 代理地址: http://localhost:9281
- 配置文件: vue.config.js