package com.ten.air.server.bytesserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartboot.socket.MessageProcessor;
import org.smartboot.socket.StateMachineEnum;
import org.smartboot.socket.transport.AioSession;

/**
 * processor
 *
 * @author wshten
 * @date 2018/10/26
 */
public class BytesServerProcessor implements MessageProcessor<byte[]> {

    private static BytesServerHandler bytesHelperInstance = BytesServerHandler.getInstance();

    private static Logger logger = LoggerFactory.getLogger(BytesServerProcessor.class);

    private static final int DATA_LENGTH = 39;

    @Override
    public void process(AioSession<byte[]> session, byte[] data) {
        logger.info("receive message");
        // TODO 数据包处理
    }

    @Override
    public void stateEvent(AioSession<byte[]> session, StateMachineEnum stateMachineEnum, Throwable throwable) {
        String sessionId = session.getSessionID();
        switch (stateMachineEnum) {
            case NEW_SESSION:
                bytesHelperInstance.addConnectNum();
                logger.info("NEW_SESSION:客户端ID:" + sessionId + "连接了,当前连接数(已连接)：" + bytesHelperInstance.getConnectNum());
                break;

            case INPUT_SHUTDOWN:
                logger.warn("INPUT_SHUTDOWN:客户端ID:" + sessionId + "【断开连接】");
                break;

            case INPUT_EXCEPTION:
                logger.warn("INPUT_EXCEPTION:客户端ID:" + sessionId + "【输入异常】");
                break;

            case OUTPUT_EXCEPTION:
                logger.warn("OUTPUT_EXCEPTION:向客户端ID:" + sessionId + "【输出异常】");
                break;

            case SESSION_CLOSED:
                logger.warn("SESSION_CLOSED:客户端ID:" + sessionId + "【已关闭】");
                break;

            default:
                logger.warn("DEFAULT:处理客户端ID:" + sessionId + "请求出现异常");
                break;
        }
    }

}
