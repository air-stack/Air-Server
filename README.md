# Air Server

> TCP_SERVER服务端，监听物联网设备发送的TCP数据包，将TCP数据包编解码后通过HTTP发送到BACK端进行数据存储更新。

## 技术栈

Smart-Socket + SpringBoot + Http客户端

## 数据发送端

1. [Air-Room](https://github.com/kevinten10/Air-Room)：通过Mocker模拟发送TCP数据

2. [Air-Iot](https://github.com/kevinten10/Air-Iot)：Stm32嵌入式设备发送WIFI数据

## 运行流程

1. [Air-Room](https://github.com/kevinten10/Air-Room)：基于Air-Protocol按照协议编码，模拟生成大气监测数据，通过线程池Socket模拟发送TCP数据包到TCP_SERVER。

2. [Air-Iot](https://github.com/kevinten10/Air-Iot)：基于Air-Protocol按照协议编码，通过物联网设备采集数据，通过WIFI模块发送TCP数据包到TCP_SERVER，解码数据包并将数据通过HTTP发送到BACK服务器。

> 通过心跳机制监测传感器状态，若失联三分钟，则关闭死连接，收到心跳包时再连接

PROTOCOL协议编解码工具详见：[https://github.com/kevinten10/Air-Protocol]

BACK数据存储服务详见: [https://github.com/kevinten10/Air-Back]

WEB数据展示网站详见：[https://github.com/kevinten10/Air-Webapp]

## 数据协议

需导入Protocol协议编解码jar包 [Air-Protocol](https://github.com/kevinten10/Air-Protocol)

Air-Protocol.jar已经放在了项目目录下，需要将其手动导入到项目libraries

File -> Project Structure -> Libraries -> add -> java -> 选择jar包

## 使用

```txt
# 1. 下载项目到本地
# git clone https://github.com/kevinten10/Air-Server.git

# 2. 打开项目(推荐使用IDEA)

# 3. 启动项目

# 4. 可使用tools目录下TCP测试工具进行测试
```

## 服务器部署

1. 打包成JAR

File -> Project Structure -> Artifacts -> Add -> JAR -> from modules with dependencies

配置“Directory for META-INF/MAINFEST.MF”，此项配置的缺省值是：..Project\src\main\java，需要改成：..Project(项目目录)

选择“Build - Build Artifacts”下的“Build”或者“Rebuild”即可生成最终的可运行的jar，在..\Project\out\artifacts\Project_jar下面找到生成的目标jar，(JAR名与项目名相同的一项)

2. 配置JAR

打开JAR，找到META-INF/MAINFEST.MF文件，检查 "Main-Class" 配置是否正确

正确配置为 Main-Clas: com...(包名)...AirBackApplication(main函数类)

3. 上传JAR

通过winscp工具上传jar所在文件到服务器中

运行 

    java -jar project.jar (即运行主JAR包)
    
检查是否启动成功

4. 开启防火墙

放通2759监听端口，8090调用端口

### 快速部署

已将打包好的jar包放在目录下，将Air_Server_jar拷贝到服务器文件下即可，进入目录，输入

    java -jar Air-Server.jar
    
即可运行服务