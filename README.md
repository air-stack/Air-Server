# Air Server

> TCP_SERVER服务端，监听物联网设备发送的TCP数据包，将TCP数据包编解码后通过HTTP发送到BACK端进行数据存储更新。

## 技术栈

Smart-Socket + SpringBoot + Http客户端

## 数据发送端

1. 通过Mocker模拟发送TCP数据: [Air-Room](https://github.com/kevinten10/Air-Room)

2. Stm32嵌入式设备发送WIFI数据: [Air-Iot](https://github.com/kevinten10/Air-Iot)

## 运行流程

1. 基于Air-Protocol按照协议编码，模拟生成大气监测数据，通过线程池Socket模拟发送TCP数据包到TCP_SERVER。

2. 基于Air-Protocol按照协议编码，通过物联网设备采集数据，通过WIFI模块发送TCP数据包到TCP_SERVER，解码数据包并将数据通过HTTP发送到BACK服务器。

通过心跳机制监测传感器状态，若失联三分钟，则关闭死连接，收到心跳包时再连接

BACK数据存储服务详见: [https://github.com/kevinten10/Air-Back]

WEB数据展示网站详见：[https://github.com/kevinten10/Air-Webapp]

## 数据协议

需导入Protocol协议编解码jar包 [Air-Protocol](https://github.com/kevinten10/Air-Protocol)

## 使用

```txt
# 1. 下载项目到本地
# git clone https://github.com/kevinten10/Air-Server.git

# 2. 打开项目(推荐使用IDEA)

# 3. TODO 数据库存储部分

# 5. 启动项目

# 6. 使用tools目录下TCP测试工具进行测试
```