package com.ten.air.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import com.ten.air.bean.AirRecord;

/**
 * MySQL Mapper
 *
 * @author wshten
 * @date 2018/10/26
 */
public interface AirRecordDao {

    int insert(@Param("pojo") AirRecord pojo);

    int insertList(@Param("pojos") List<AirRecord> pojo);

    List<AirRecord> select(@Param("pojo") AirRecord pojo);

    int update(@Param("pojo") AirRecord pojo);

}
