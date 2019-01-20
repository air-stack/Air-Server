package com.ten.air.server;

import com.ten.air.server.server.BytesProtocol;
import com.ten.air.server.server.BytesServerProcessor;
import org.smartboot.socket.transport.AioQuickServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 监听127.0.0.1:9001
 */
@SpringBootApplication
@ComponentScan("com.ten.air.server")
public class AirServerApplication {

    /**
     * back服务的端口
     */
    public static final int BACK_PORT = 9003;
    /**
     * smartsocket监听端口
     */
    private static final int PORT = 9001;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AirServerApplication.class, args);
        AioQuickServer<byte[]> server = new AioQuickServer<>(PORT, new BytesProtocol(), new BytesServerProcessor());
        server.start();
    }
}
