# Air Server

> 大气环境检测系统的TCP_SERVER服务端，接收物联网设备发送的TCP数据包，将TCP数据包编解码后通过HTTP发送到BACK端进行数据存储更新。

## 技术栈

Smart-Socket + 编解码协议及工具 + SpringBoot + Http调用

## 数据获取(物联网)

通过物联网设备采集数据，通过WIFI模块发送TCP数据包到TCP_SERVER，解码数据包并将数据通过HTTP发送到BACK服务器。

通过心跳机制监测传感器状态，若失联三分钟，则关闭死连接，收到心跳包时再连接

BACK数据存储服务详见: [https://github.com/kevinten10/Air-Back]

WEB数据展示网站详见：[https://github.com/kevinten10/Air-Webapp]

## 数据协议

使用十六进制编码的TCP数据包，格式详见：tools/STM32WIFI模块TCP通信协议.xlsx

## 使用

```txt
# 1. 下载项目到本地
# git clone https://github.com/kevinten10/Air-Server.git

# 2. 打开项目(推荐使用IDEA)

# 3. TODO 数据库存储部分

# 5. 启动项目

# 6. 使用tools目录下TCP测试工具进行测试
```