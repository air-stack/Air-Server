package com.ten.air.server.service;

import com.alibaba.fastjson.JSON;
import com.ten.air.server.dao.AirRecordDao;
import com.ten.air.server.utils.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.List;

import com.ten.air.server.entity.AirRecord;

public class AirRecordService {
    private static final Logger logger = LoggerFactory.getLogger(AirRecordService.class);
    private static final AirRecordService INSTANCE = new AirRecordService();

    public static AirRecordService getInstance() {
        return INSTANCE;
    }

    private HttpRequest http;

    private AirRecordService() {
        http = new HttpRequest();
    }

    public String insert(AirRecord pojo) {
        String params = JSON.toJSONString(pojo);
    }

    public String update(AirRecord pojo) {
        String params = JSON.toJSONString(pojo);
    }

}
