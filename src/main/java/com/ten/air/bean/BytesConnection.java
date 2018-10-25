package com.ten.air.bean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartboot.socket.transport.AioSession;

/**
 * Client连接对象 data transfer object
 */
public class BytesConnection {

    private static Logger logger = LoggerFactory.getLogger(BytesConnection.class);

    private String ipAndPort;
    private String imei;
    private AioSession<byte[]> session;

    private BytesConnection() {
    }

    public static BytesConnection newInstance(String ipAndPort, String imei, AioSession<byte[]> session) {
        BytesConnection instance = new BytesConnection();
        instance.ipAndPort = ipAndPort;
        instance.imei = imei;
        instance.session = session;
        logger.info("receive new connecition : " + instance);
        return instance;
    }

    @Override
    public String toString() {
        return "Connection@{IpAndPort:" + ipAndPort + ",Imei:" + imei + ",session:" + session + "}";
    }

    public String getIpAndPort() {
        return ipAndPort;
    }

    public void setIpAndPort(String ipAndPort) {
        this.ipAndPort = ipAndPort;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public AioSession<byte[]> getSession() {
        return session;
    }

    public void setSession(AioSession<byte[]> session) {
        this.session = session;
    }

}
