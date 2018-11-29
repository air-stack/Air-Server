package com.ten.air.tcp.bytesserver;

import com.ten.air.tcp.bean.BytesConnection;
import com.ten.air.tcp.utils.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartboot.socket.transport.AioSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 数据包处理器
 *
 * @author wshten
 * @date 2018/10/26
 */
public final class BytesServerHandler {
    private static final Logger logger = LoggerFactory.getLogger(BytesServerHandler.class);

    private static volatile BytesServerHandler instance = null;

    static BytesServerHandler getInstance() {
        if (instance == null) {
            synchronized (BytesServerHandler.class) {
                if (instance == null) {
                    instance = new BytesServerHandler();
                }
            }
        }
        return instance;
    }

    /**
     * @key imei
     * @value session
     */
    private ConcurrentHashMap<String, AioSession<byte[]>> imeiSession;
    /**
     * @key imei
     * @value 最后连接时间(long = > ms)
     */
    private ConcurrentHashMap<String, Long> imeiLastTime;

    /**
     * 当前连接数量
     */
    private AtomicInteger connectNum;
    /**
     * 当前在线数量
     */
    private AtomicInteger onlineNum;

    private BytesServerHandler() {
        this.imeiSession = new ConcurrentHashMap<>();
        this.imeiLastTime = new ConcurrentHashMap<>();
        this.onlineNum = new AtomicInteger(0);
        this.connectNum = new AtomicInteger(0);
    }

    /**
     * 获取当前在线数量
     */
    public AtomicInteger getOnlineNum() {
        return onlineNum;
    }

    /**
     * 获取当前连接数量
     */
    public AtomicInteger getConnectNum() {
        return connectNum;
    }

    /**
     * 机器连接：连接数 +1
     */
    public void addConnectNum() {
        this.connectNum.getAndAdd(1);
    }

    /**
     * 机器连接：在线数 +1
     */
    public void addOnlineNum() {
        this.onlineNum.getAndAdd(1);
    }

    /**
     * 机器断开连接：连接数 -1
     */
    public void disconnect() {
        this.connectNum.getAndAdd(-1);
    }

    /**
     * 机器下线：在线数 -1
     */
    public void offline(String key) {
        this.onlineNum.getAndAdd(-1);
    }

    /* ---------------------------- 数据包处理 -------------------------------- */

    public void receiveData(BytesConnection connection) {
        AioSession<byte[]> session = connection.getSession();
        byte[] data = connection.getData();

        // TODO 数据处理

        String imei = "1234";
        String temperature = "25";
        String pm25 = "125";

        // session 存在
        if (imeiSession.contains(imei)) {
            updateImeiTime(imei);
        }
        // session 不存在
        else {
            imeiSession.put(imei, session);
            updateImeiTime(imei);
        }

        String url = "http://localhost:8080/air";
        String params = "?imei=" + imei + "&temperature=" + temperature + "&pm25=" + pm25;

        String result = HttpRequest.sendGet(url, params);
    }

    /**
     * 根据Session获取Imei
     *
     * @param oldSession session对象
     * @return imei
     */
    private String getImeiBySession(AioSession<byte[]> oldSession) {
        for (Map.Entry<String, AioSession<byte[]>> entry : this.imeiSession.entrySet()) {
            if (entry.getValue() == oldSession) {
                return entry.getKey();
            }
        }
        return "";
    }

    /**
     * 更新Client心跳时间--1970.1.1开始的毫秒数
     */
    private void updateImeiTime(String imei) {
        long time = System.currentTimeMillis();
        imeiLastTime.put(imei, time);
    }

}
