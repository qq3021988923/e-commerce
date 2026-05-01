项目架构
多模块架构 my-common、my-pojo、my-server

三层架构 Controller → Service → Mapper

RESTful API 接口设计风格

核心框架
Spring Boot３.0 、Java 17 、 项目构建工具 Maven

数据层
数据库 MySQL 8.0、ORM框架 MyBatis、数据库连接池 Druid、分页插件 PageHelper

认证与安全
Token认证 JWT、切面编程（公共字段填充）Spring AOP

####缓存与存储

缓存（菜品、套餐、店铺状态） Redis、文件存储（图片上传） 阿里云 OSS

文档与接口
API文档（OpenAPI 3） Knife4j

报表导出
Excel导出 Apache POI

实时通信
下单提醒、客户催单 WebSocket、RabbitMQ

工具库
简化代码 Lombok、JSON处理 FastJSON、字符串工具 Commons Lang



rabbitMQ/这是普通队列的整合，不是延迟消息的调用
使用@Configuration配置定义路由和持久化队列
    在需要的业务层调用生产者方法，通过匹配的交换机，根据我的路由键 绑定队列。
   消费者方法通过RabbitListener监听队列，获取消息。手动ack机制保证消息的可靠性

调用延迟方法：= 延迟队列（定时炸弹）+(死信队列)普通队列


mybatis:
主配置文件配置驱动，链接地址，用户名，数据库密码。mybatis相关配置（数据库下划线自动映射为Java驼峰属性，指定mybatis的xml映射文件路径，实体类包别名）。通过xml的mapper标签属性namespace映射对应的mapper接口，编写sql语句，在服务层注入mapper接口，调用对应的方法进行数据库操作。

redis:
配置连接信息，创建@Configuration配置类，定义一个redistemplate返回值的@Bean方法，spring会自动将该方法的返回值注册为容器中的Bean，可以在任何地方通过@Autowired,@Resource注入该redistempate，执行redis的存取操作。先从缓存获取，没有则从数据库查询并缓存，最后在数据修改,添加中删除对应的缓存，确保数据一致性。

jwt：
配置自定义签名密钥和过期时间。
生成令牌：调用jwts.builder()构建器对象，通过setClaims方法存储用户信息，setExpriation方法设置过期时间，指定对称加密算法并传入密钥签名，调用compact()生成的字符串返回给前端
解析令牌：调用jwts.parser()创建解析器，设置与生成令牌时一致的签名密钥，算法，通过parseClaimsJwts()方法解析令牌，自动校验签名是否正确和过期时间，解析成功直接获取用户存储的信息
拦截器统一校验：@Component注解实现HandlerInterceptor接口，重写preHandle方法，在进入接口请求之前获取请求头部的token，调用jwt工具解析令牌。@Configuration实现WebMvcConfigurer接口，重写addInterceptors方法注册拦截器，设置拦截指定路径请求并放行登录接口，实现项目接口的身份认证与权限控制

异常处理：
通过@RestControllerAdvice注解实现异常处理，@ExceptionHandler注解指定捕获异常，在处理方法中记录异常日志，同意返回封装好的错误结果，实现全局异常同意捕获，处理与响应格式标准化。

Spring AOP：
自定义添加和更新注解，通过@Aspect创建切面类，配置前置通知拦截mapper层带注解的方法，通过反射获取实体对象，根据操作类型自动填充公共字段，简化开发。

Spring WebSocket：
自定义继承TextWebSocketHandler类，实现连接管理，消息推送功能，@Configuration配置类开启注册处理并配置路径与跨域规则，在业务层注入websocket实现消息推送。

Excel 数据导出：
创建XSSworkbook工作薄和工作表，格式化日期，设置响应头 表头、填充数据，以流的方式实现文件下载

阿里云 OSS：
配置文件上传的大小限制， 地区地址，云账号id，云账号密码。使用 OSSClientBuilder 创建 OSS 客户端。以字节流方式调用 putObject 方法上传文件到指定存储空间，上传成功后拼接文件的访问 URL 返回

Knife4j:
实现OpenApi方法的返回值，在里面配置文档的基本信息，在接口的方法上使用@Operation 添加说明，访问/doc.html生成api文档
创建@Configuration配置类，定义OpenAPI的@Bean方法，配置文档标题、描述等基本信息。在Controller方法上使用@Operation注解说明接口功能。访问/doc.html可生成API文档。



