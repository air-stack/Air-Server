package com.ten.air.server.bytesserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartboot.socket.transport.AioQuickServer;
import org.springframework.stereotype.Component;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.io.Serializable;

/**
 * servlet
 *
 * @author wshten
 * @date 2018/10/26
 */
@Component
public class BytesServerServlet extends HttpServlet implements Serializable {

    private static final int PORT = 2759;

    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(BytesServerServlet.class);

    private AioQuickServer<byte[]> server = new AioQuickServer<>(PORT, new BytesProtocol(), new BytesServerProcessor());

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            server.start();
            logger.info("============[startup]============");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        logger.info("============BytesServerServlet[destroy]ready============");
        server.shutdown();
        logger.info("============BytesServerServlet[destroy]end============");
    }
}
