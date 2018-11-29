package com.ten.air.tcp.bytesserver;

import com.ten.air.tcp.bean.BytesConnection;
import com.ten.air.tcp.utils.CodeConvertUtil;
import com.ten.air.tcp.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartboot.socket.MessageProcessor;
import org.smartboot.socket.StateMachineEnum;
import org.smartboot.socket.transport.AioSession;

public class BytesServerProcessor implements MessageProcessor<byte[]> {
    private static Logger logger = LoggerFactory.getLogger(BytesServerProcessor.class);

    private static BytesServerHandler bytesHelperInstance = BytesServerHandler.getInstance();

    private static final int DATA_LENGTH = 31;

    @Override
    public void process(AioSession<byte[]> session, byte[] data) {
        BytesConnection connection = BytesConnection.newInstance(session, data);
        logger.info("receive new " + connection);

        // 和校验
        if (!CommonUtils.checkDataGram(data)) {
            logger.warn("和校验 失败");
            return;
        }

        // 长度校验
        if (data.length != DATA_LENGTH) {
            logger.info("长度校验 失败 ");
            return;
        }

        // 接收到客户端传输的数据 data
        String dataString = CodeConvertUtil.bytesToHexString(data);
        if (dataString == null) {
            logger.error("数据转换 失败");
            return;
        }

        logger.info("接收到的基础数据为 " + dataString);

        // 11~25
        // 获取imei
        String imei = dataString.substring(20, 50);
        logger.info("当前进入设备 --> imei=" + imei);

        // 进入信息接收处理流程
        bytesHelperInstance.receiveData(connection);
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
