package com.ten.air.bytesserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartboot.socket.transport.AioSession;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public final class BytesServerHelper {

    private static Logger logger = LoggerFactory.getLogger(BytesServerHelper.class);

    /**
     * key:imei;value:session
     * 扫码后根据imei向对应的session发送指令
     */
    private ConcurrentHashMap<String, AioSession<byte[]>> imeiSession;

    /**
     * key:ip+port;value:session
     */
    private ConcurrentHashMap<String, AioSession<byte[]>> ipAndPortSession;

    /**
     * key:imei;value:最后连接时间(long => ms)
     * 保存最后一次连接的时间
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

    private static volatile BytesServerHelper instance = null;

    private BytesServerHelper() {
        this.ipAndPortSession = new ConcurrentHashMap<>();
        this.imeiSession = new ConcurrentHashMap<>();
        this.imeiLastTime = new ConcurrentHashMap<>();
        this.onlineNum = new AtomicInteger(0);
        this.connectNum = new AtomicInteger(0);
    }

    public static BytesServerHelper getInstance() {
        if (instance == null) {
            synchronized (BytesServerHelper.class) {
                if (instance == null) {
                    instance = new BytesServerHelper();
                }
            }
        }
        return instance;
    }

    public AioSession<byte[]> getSessionForImei(String imei) {
        return imeiSession.get(imei);
    }

    /**
     * 获取所有的ip-session
     *
     * @return ConcurrentHashMap
     */
    public ConcurrentHashMap<String, AioSession<byte[]>> getIpAndPortSession() {

        return ipAndPortSession;
    }

    public ConcurrentHashMap<String, Long> getImeiLastTime() {
        return imeiLastTime;
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
