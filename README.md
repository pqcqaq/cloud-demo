# Spring-Cloud项目示例

## 像单体项目一样开发微服务项目

- 模块依赖关系
- ![](https://cdn.jsdelivr.net/gh/pqcqaq/imageSource/upload/20240130210854.png)
- 基础项目名
    - 后端模块
        - 通用类
        - 远程调用客户端
        - 服务网关
        - 微服务主依赖
            - 认证服务
            - 示例服务
            - 微服务配置信息
            - 测试服务

## 项目依赖

- SpringCloud 2021

- SpringBoot 2.7
- mysql 8.2
- redis
- mybatisplus
- 微服务：
    - ribbon
    - feign
    - nacos



## 开发示例

- 新建模块（可以直接复制demo模块，然后重命名）
- ![](https://cdn.jsdelivr.net/gh/pqcqaq/imageSource/upload/20240130211247.png)
    - 默认情况下只需要修改这几个信息：
        - 服务：模块ID
        - 版本：模块版本
        - 名称：服务名称
        - 描述：服务描述
- 修改配置信息
    - ![](https://cdn.jsdelivr.net/gh/pqcqaq/imageSource/upload/20240130211522.png)
        - 服务名称
        - 服务端口
        - 其他配置信息

- 正常开发
- 配置feign客户端
    - ![](https://cdn.jsdelivr.net/gh/pqcqaq/imageSource/upload/20240130211721.png)
        - 将服务的接口同步到feign-clients模块中，以便其他模块调用

