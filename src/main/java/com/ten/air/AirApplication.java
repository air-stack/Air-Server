package com.ten.air;

import com.ten.air.bytesserver.BytesProtocol;
import com.ten.air.bytesserver.BytesServerProcessor;
import org.smartboot.socket.transport.AioQuickServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AirApplication {

    private static final int PORT = 2759;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AirApplication.class, args);
        AioQuickServer<byte[]> server = new AioQuickServer<>(PORT, new BytesProtocol(), new BytesServerProcessor());
        server.start();
    }
}
