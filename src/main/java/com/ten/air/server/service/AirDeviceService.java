package com.ten.air.server.service;

import com.alibaba.fastjson.JSON;
import com.ten.air.server.dao.AirDeviceDao;
import com.ten.air.server.dao.AirRecordDao;
import com.ten.air.server.entity.AirDevice;
import com.ten.air.server.entity.AirRecord;
import com.ten.air.server.utils.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class AirDeviceService {
    private static final Logger logger = LoggerFactory.getLogger(AirDeviceService.class);
    private static final AirDeviceService INSTANCE = new AirDeviceService();

    public static AirDeviceService getInstance() {
        return INSTANCE;
    }

    private HttpRequest http;

    private AirDeviceService() {
        http = new HttpRequest();
    }

    public String insert(AirRecord pojo) {
        String params = JSON.toJSONString(pojo);
        http.sendGet();
    }

    public String update(AirRecord pojo) {
        String params = JSON.toJSONString(pojo);
    }
}
