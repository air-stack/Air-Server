package com.ten.air.server.server;

import com.alibaba.fastjson.JSON;
import com.ten.air.protocol.ProtocolDecode;
import com.ten.air.protocol.ProtocolEncode;
import com.ten.air.protocol.bean.AirRecord;
import com.ten.air.protocol.dht11.DHT11DataType;
import com.ten.air.protocol.gp2y.GP2YDataType;
import com.ten.air.server.bean.BytesConnection;
import com.ten.air.server.utils.CodeConvertUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartboot.socket.MessageProcessor;
import org.smartboot.socket.StateMachineEnum;
import org.smartboot.socket.transport.AioSession;

public class BytesServerProcessor implements MessageProcessor<byte[]> {
    private static Logger logger = LoggerFactory.getLogger(BytesServerProcessor.class);

    private BytesServerHandler bytesHelperInstance = BytesServerHandler.getInstance();

    private static final int DATA_LENGTH = 36;

    @Override
    public void process(AioSession<byte[]> session, byte[] bytes) {
        // TODO 和校验

        String protocol;

        // byte[] -> string
        String data = new String(bytes);

        // 数据格式一 :JSON数据 -> 正确数据信息
        if (data.charAt(1) == '{') {
            // JSON字符串
            String jsonData = data.substring(1, data.length());

            logger.info("{}", jsonData);

            // DHT11模块数据
            if (data.charAt(0) == 'D') {
                DHT11DataType dataType = JSON.parseObject(jsonData, DHT11DataType.class);

                // 编码协议
                protocol = ProtocolEncode.toHexProtocol(dataType);
            }
            // GP2Y模块数据
            else if (data.charAt(0) == 'G') {
                GP2YDataType dataType = JSON.parseObject(jsonData, GP2YDataType.class);

                // 编码协议
                protocol = ProtocolEncode.toHexProtocol(dataType);

            }
            // 错误信息
            else {
                logger.warn("Error Json Data String : {}", jsonData);
                return;
            }
        }
        // 数据格式二 :十六进制字符串 -> 暂未使用
        else if (data.charAt(0) == 'A' && data.charAt(1) == '0') {
            // 长度校验
            if (data.length() < DATA_LENGTH) {
                logger.info("长度校验失败 {}, {}", data.length(), data);
                return;
            }

            protocol = CodeConvertUtil.bytesToHexString(bytes);
            if (protocol == null) {
                logger.error("数据转换 失败");
                return;
            }
        }
        // 数据格式三 :字符串信息 -> 监测失败信息
        else {
            logger.info("Failure {}", data);
            return;
        }

        logger.info("接收到的基础数据为 {}", protocol);

        // 解析协议字符串
        AirRecord airRecord = ProtocolDecode.parseProtocol(protocol);

        logger.info("当前进入数据 --> {}", airRecord);

        BytesConnection connection = BytesConnection.newInstance(airRecord, session);

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
                bytesHelperInstance.disconnect();
                logger.warn("SESSION_CLOSED:客户端ID:" + sessionId + "【已关闭】");
                break;

            default:
                logger.warn("DEFAULT:处理客户端ID:" + sessionId + "请求出现异常");
                break;
        }
    }

}
