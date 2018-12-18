package com.ten.air.server.server;

import com.ten.air.protocol.bean.AirRecord;
import com.ten.air.server.bean.BytesConnection;
import com.ten.air.server.bean.HttpResponse;
import com.ten.air.server.entity.AirDevice;
import com.ten.air.server.service.AirDeviceService;
import com.ten.air.server.service.AirRecordService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smartboot.socket.transport.AioSession;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 数据包处理器
 */
class BytesServerHandler {
    private static final Logger logger = LoggerFactory.getLogger(BytesServerHandler.class);

    private static final BytesServerHandler INSTANCE = new BytesServerHandler();

    static BytesServerHandler getInstance() {
        return INSTANCE;
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
    AtomicInteger getOnlineNum() {
        return onlineNum;
    }

    /**
     * 获取当前连接数量
     */
    AtomicInteger getConnectNum() {
        return connectNum;
    }

    /**
     * 机器连接：连接数 +1
     */
    void addConnectNum() {
        this.connectNum.getAndAdd(1);
    }

    /**
     * 机器连接：在线数 +1
     */
    void addOnlineNum() {
        this.onlineNum.getAndAdd(1);
    }

    /**
     * 机器断开连接：连接数 -1
     */
    void disconnect() {
        this.connectNum.getAndAdd(-1);
    }

    /**
     * 机器下线：在线数 -1
     */
    void offline(String key) {
        this.onlineNum.getAndAdd(-1);
    }

    /* ---------------------------- 数据包处理 -------------------------------- */

    private AirDeviceService airDeviceService = AirDeviceService.getInstance();
    private AirRecordService airRecordService = AirRecordService.getInstance();

    /**
     * 数据包处理逻辑
     */
    void receiveData(BytesConnection connection) {
        AirRecord airRecord = connection.getAirRecord();
        String imei = airRecord.getImei();

        // 获取session
        Optional<AioSession<byte[]>> oldImeiSession = Optional.ofNullable(this.imeiSession.get(imei));

        // 注册新设备 or 更新心跳
        Boolean result = oldImeiSession
                // 若值存在，更新心跳
                .map((session) -> updateImeiTime(imei))
                // 若值为空，注册设备
                .orElse(registerNewConnect(connection));

        // HTTP请求
        if (result) {
            HttpResponse response = airRecordService.insert(airRecord);
            if (response.getCode() == 0) {
                logger.info("数据写入成功");
            } else {
                logger.warn("数据写入失败");
            }
        } else {
            logger.warn("连接本地注册失败，请检查！");
        }

    }

    /**
     * 注册新连接
     */
    private Boolean registerNewConnect(BytesConnection connection) {
        logger.info("开始注册新连接...");

        String imei = connection.getAirRecord().getImei();

        AirDevice device = new AirDevice();
        device.setImei(imei);
        device.setIsDeleted(0);

        // HTTP请求 注册设备信息
        HttpResponse response = airDeviceService.insert(device);
        if (response.getCode() == 0) {
            // 新连接写入session池
            this.imeiSession.put(imei, connection.getSession());
            // 更新心跳时间
            updateImeiTime(imei);
            logger.info("创建新连接成功 " + connection);
            return true;
        } else {
            logger.warn("创建新连接失败 " + connection);
            return false;
        }
    }

    /**
     * 更新心跳 时间: 1970.1.1开始的毫秒数
     */
    private Boolean updateImeiTime(String imei) {
        logger.info("心跳刷新 : " + imei);
        long time = System.currentTimeMillis();
        imeiLastTime.put(imei, time);
        return true;
    }
}
