package com.ten.air.server.service;

import com.alibaba.fastjson.JSON;
import com.ten.air.protocol.bean.AirRecord;
import com.ten.air.server.bean.HttpResponse;
import com.ten.air.server.utils.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AirRecordService {
    private static final Logger logger = LoggerFactory.getLogger(AirRecordService.class);
    private static final AirRecordService INSTANCE = new AirRecordService();

    public static AirRecordService getInstance() {
        return INSTANCE;
    }

    private static final String RECORD_URL = "http://localhost:8080/air/record";

    private HttpRequest http;

    private AirRecordService() {
        http = new HttpRequest();
    }

    public HttpResponse insert(AirRecord pojo) {
        String params = JSON.toJSONString(pojo);
        logger.info("HTTP RECORD POST:" + params);
        String response = http.sendPost(RECORD_URL, params);
        return JSON.parseObject(response, HttpResponse.class);
    }

    public HttpResponse update(AirRecord pojo) {
        String params = JSON.toJSONString(pojo);
        logger.info("HTTP RECORD UPDATE:" + params);
        String response = http.sendUpdate(RECORD_URL, params);
        return JSON.parseObject(response, HttpResponse.class);
    }
}
