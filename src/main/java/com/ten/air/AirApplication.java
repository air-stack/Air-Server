package com.ten.air;

import com.ten.air.bytesserver.BytesProtocol;
import com.ten.air.bytesserver.BytesServerProcessor;
import org.smartboot.socket.transport.AioQuickServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 127.0.0.1:2759
 *
 * @author wshten
 * @date 2018/10/26
 */
@SpringBootApplication
public class AirApplication {
    private static final int PORT = 2759;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(AirApplication.class, args);
        AioQuickServer<byte[]> server = new AioQuickServer<>(PORT, new BytesProtocol(), new BytesServerProcessor());
        server.start();
    }
}
