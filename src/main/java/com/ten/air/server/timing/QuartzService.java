package com.ten.air.server.timing;

import com.ten.air.server.server.BytesServerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author schafferxu
 */

@Configuration
@EnableScheduling
@EnableAutoConfiguration
public class QuartzService {

    private static Logger logger = LoggerFactory.getLogger(QuartzService.class);

    /**
     * 毫秒 : 三分钟
     */
    private static final long TIME_OUT_MS = 1000 * 60 * 3;

    private BytesServerHandler handler = BytesServerHandler.getInstance();

    /**
     * 每隔三分钟，清理死连接
     */
    @Scheduled(cron = "0 0/3 * * * ?")
    public void reWrite() {
        ConcurrentHashMap<String, Long> imeiLastTime = handler.getImeiLastTime();
        long currentTime = System.currentTimeMillis();

        int count = 0;
        int error = 0;
        for (Map.Entry<String, Long> imei : imeiLastTime.entrySet()) {
            long subtract = currentTime - imei.getValue();
            // 连接超时
            if (subtract > TIME_OUT_MS) {
                boolean success = handler.removeConnection(imei.getKey());
                count++;
                if (!success) {
                    error++;
                }
            }
        }

        if (count > 0) {
            logger.info("-------------clear finish, count : " + count);
            if (error > 0) {
                logger.warn("-------------clear finish, error : " + error);
            }
        }
    }
}

