package com.ten.air.server.bytesserver;

import com.ten.air.server.bean.BytesConnection;
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

    public AioSession<byte[]> getSessionForImei(String imei) {
        return imeiSession.get(imei);
    }

    public ConcurrentHashMap<String, Long> getImeiLastTime() {
        return imeiLastTime;
    }

    public void receiveData(BytesConnection connection){

    }

    /**
     * 获取当前在线数量
     *
     * @return AtomicInteger
     */
    public AtomicInteger getOnlineNum() {
        return onlineNum;
    }

    /**
     * 获取当前连接数量
     *
     * @return AtomicInteger
     */
    AtomicInteger getConnectNum() {
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
     * 机器断开连接
     */
    void disconnect() {
        this.connectNum.getAndAdd(-1);
    }

    /**
     * 机器下线
     *
     * @param key key
     */
    public void offline(String key) {
        this.onlineNum.getAndAdd(-1);
    }

    /* ---------------------------- 闸机发送心跳包 -------------------------------- */

    /**
     * 删除IpAndPort池旧连接
     */
    private void removeIpAndPortSession(AioSession<byte[]> oldImeiSession) {
        for (Map.Entry<String, AioSession<byte[]>> entry : this.ipAndPortSession.entrySet()) {
            if (entry.getValue() == oldImeiSession) {
                ipAndPortSession.remove(entry.getKey());
                break;
            }
        }
    }

    /**
     * 根据Session获取IpAndPort
     *
     * @param oldSession session对象
     * @return IpAndPort
     */
    private String getIpAndPortBySession(AioSession<byte[]> oldSession) {
        for (Map.Entry<String, AioSession<byte[]>> entry : this.ipAndPortSession.entrySet()) {
            if (entry.getValue() == oldSession) {
                return entry.getKey();
            }
        }
        return "";
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
