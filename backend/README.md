# 健身器材在线销售管理系统

基于Spring Boot+Vue的健身器材在线销售管理系统，包含用户端和管理端功能。

## 项目结构

### 后端（Spring Boot）

```
backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/
│   │   │       └── fitness/
│   │   │           ├── controller/           # 控制器
│   │   │           │   ├── UserController.java      # 用户相关接口
│   │   │           │   ├── ProductController.java   # 商品相关接口
│   │   │           │   ├── CartController.java      # 购物车相关接口
│   │   │           │   ├── OrderController.java     # 订单相关接口
│   │   │           │   └── AdminController.java     # 管理端相关接口
│   │   │           ├── service/              # 服务层
│   │   │           │   ├── UserService.java         # 用户服务
│   │   │           │   ├── ProductService.java      # 商品服务
│   │   │           │   ├── CartService.java         # 购物车服务
│   │   │           │   ├── OrderService.java        # 订单服务
│   │   │           │   └── LogService.java          # 日志服务
│   │   │           ├── repository/           # 数据访问层
│   │   │           │   ├── UserRepository.java      # 用户数据访问
│   │   │           │   ├── ProductRepository.java   # 商品数据访问
│   │   │           │   ├── CartRepository.java      # 购物车数据访问
│   │   │           │   ├── OrderRepository.java     # 订单数据访问
│   │   │           │   └── LogRepository.java       # 日志数据访问
│   │   │           ├── entity/              # 实体类
│   │   │           │   ├── User.java                # 用户实体
│   │   │           │   ├── Product.java             # 商品实体
│   │   │           │   ├── Cart.java                # 购物车实体
│   │   │           │   ├── Order.java               # 订单实体
│   │   │           │   └── Log.java                 # 日志实体
│   │   │           ├── config/              # 配置
│   │   │           │   ├── WebConfig.java           # Web配置
│   │   │           │   ├── SecurityConfig.java      # 安全配置
│   │   │           │   └── SwaggerConfig.java       # Swagger配置
│   │   │           ├── util/                # 工具类
│   │   │           │   ├── JwtUtil.java             # JWT工具
│   │   │           │   ├── PasswordUtil.java        # 密码工具
│   │   │           │   └── ResponseUtil.java        # 响应工具
│   │   │           └── FitnessApplication.java      # 应用入口
│   │   └── resources/
│   │       ├── application.yml            # 应用配置
│   │       └── static/                    # 静态资源
│   └── test/                              # 测试
├── pom.xml                                # Maven配置
```

### 前端（Vue）

```
frontend/
├── public/                                # 公共资源
│   ├── favicon.ico
│   └── index.html
├── src/
│   ├── assets/                            # 静态资源
│   │   ├── images/                        # 图片
│   │   └── styles/                        # 样式
│   ├── components/                        # 组件
│   │   ├── common/                        # 公共组件
│   │   │   ├── Header.vue                 # 头部组件
│   │   │   ├── Footer.vue                 # 底部组件
│   │   │   └── NavBar.vue                 # 导航栏组件
│   │   ├── user/                          # 用户端组件
│   │   │   ├── ProductCard.vue            # 商品卡片组件
│   │   │   ├── CartItem.vue               # 购物车项组件
│   │   │   └── OrderItem.vue              # 订单项组件
│   │   └── admin/                         # 管理端组件
│   │       ├── ProductForm.vue            # 商品表单组件
│   │       ├── OrderTable.vue             # 订单表格组件
│   │       └── LogTable.vue               # 日志表格组件
│   ├── views/                             # 页面
│   │   ├── user/                          # 用户端页面
│   │   │   ├── Home.vue                   # 首页
│   │   │   ├── Login.vue                  # 登录页
│   │   │   ├── Register.vue               # 注册页
│   │   │   ├── ProductList.vue            # 商品列表页
│   │   │   ├── ProductDetail.vue          # 商品详情页
│   │   │   ├── Cart.vue                   # 购物车页
│   │   │   ├── OrderConfirm.vue           # 订单确认页
│   │   │   ├── OrderList.vue              # 订单列表页
│   │   │   └── UserProfile.vue            # 用户个人中心
│   │   └── admin/                         # 管理端页面
│   │       ├── AdminLogin.vue             # 管理员登录页
│   │       ├── Dashboard.vue              # 管理面板
│   │       ├── ProductManage.vue          # 商品管理
│   │       ├── OrderManage.vue            # 订单管理
│   │       └── LogManage.vue              # 日志管理
│   ├── router/                            # 路由
│   │   ├── index.js                       # 路由配置
│   │   ├── user.js                        # 用户端路由
│   │   └── admin.js                       # 管理端路由
│   ├── store/                             # 状态管理
│   │   ├── index.js                       # 状态管理配置
│   │   ├── modules/                       # 状态模块
│   │   │   ├── user.js                    # 用户状态
│   │   │   ├── product.js                 # 商品状态
│   │   │   ├── cart.js                    # 购物车状态
│   │   │   └── order.js                   # 订单状态
│   ├── services/                          # 服务
│   │   ├── api.js                         # API配置
│   │   ├── userService.js                 # 用户服务
│   │   ├── productService.js              # 商品服务
│   │   ├── cartService.js                 # 购物车服务
│   │   └── orderService.js                # 订单服务
│   ├── utils/                             # 工具类
│   │   ├── request.js                     # 请求工具
│   │   ├── storage.js                     # 存储工具
│   │   └── validator.js                   # 验证工具
│   ├── App.vue                            # 根组件
│   └── main.js                            # 入口文件
├── package.json                           # 依赖配置
├── vue.config.js                          # Vue配置
└── babel.config.js                        # Babel配置
```

## 功能模块

### 用户端功能
1. **用户注册与登录**：实现用户注册、登录、退出登录以及个人基本信息查看等功能
2. **商品展示与检索**：实现健身器材分类展示、关键字搜索、商品详情查看等功能
3. **购物车与下单支付**：实现购物车商品添加、数量修改、删除、订单提交和模拟支付等功能
4. **个人订单查询**：实现用户对历史订单、当前订单状态等信息的查看

### 管理端功能
1. **商品信息管理**：实现商品新增、修改、删除、分类维护和上下架等功能
2. **订单管理**：实现订单查看、订单状态更新和发货信息录入等功能
3. **日志管理**：记录商品管理、订单处理等关键操作信息

## 技术栈

### 后端
- Spring Boot 2.5+
- Spring Security
- JPA/Hibernate
- MySQL
- JWT

### 前端
- Vue 3
- Vue Router
- Vuex
- Axios
- Element Plus
- ECharts（可选，用于数据可视化）

## 开发环境

### 后端
- JDK 11+
- Maven 3.6+
- MySQL 8.0+

### 前端
- Node.js 14+
- npm 6+
- Vue CLI 4+

## 部署说明

1. **后端部署**：
   - 配置数据库连接信息
   - 执行 `mvn clean package` 打包
   - 运行 `java -jar backend/target/fitness-1.0.0.jar`

2. **前端部署**：
   - 执行 `npm install` 安装依赖
   - 执行 `npm run build` 打包
   - 将 `dist` 目录部署到Web服务器

## 注意事项

- 确保数据库连接正确配置
- 前端API地址需要根据实际部署环境修改
- 生产环境需要配置HTTPS和CORS
- 敏感信息（如数据库密码、JWT密钥）应通过环境变量或配置文件管理
