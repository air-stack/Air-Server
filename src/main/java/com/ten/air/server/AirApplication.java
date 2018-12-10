package com.ten.air.server;

import com.ten.air.server.bytesserver.BytesProtocol;
import com.ten.air.server.bytesserver.BytesServerProcessor;
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
@ComponentScan("com.ten.air.server")
public class AirApplication {

    private static final int PORT = 2759;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AirApplication.class, args);
        AioQuickServer<byte[]> server = new AioQuickServer<>(PORT, new BytesProtocol(), new BytesServerProcessor());
        server.start();
    }
}
