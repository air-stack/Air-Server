package com.ten.air.tcp;

import com.ten.air.tcp.bytesserver.BytesProtocol;
import com.ten.air.tcp.bytesserver.BytesServerProcessor;
import org.mybatis.spring.annotation.MapperScan;
import org.smartboot.socket.transport.AioQuickServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 127.0.0.1:2759
 *
 * @author wshten
 * @date 2018/10/26
 */
@SpringBootApplication
@ComponentScan("com.ten.air.tcp")
@MapperScan("com.ten.air.tcp.dao")
public class AirApplication {

    private static final int PORT = 2759;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AirApplication.class, args);
        AioQuickServer<byte[]> server = new AioQuickServer<>(PORT, new BytesProtocol(), new BytesServerProcessor());
        server.start();
    }
}
