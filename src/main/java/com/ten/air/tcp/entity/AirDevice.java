package com.ten.air.tcp.entity;

/**
 * Atmospheric Sensor 大气监测设备详情
 */
public class AirDevice {
    /**
     * primary key
     */
    private Integer id;
    /**
     * SIM串号
     */
    private String imei;
    /**
     * 设备别名
     */
    private String alias;
    /**
     * 社区ID
     */
    private String communityId;
    /**
     * 设备状态
     * 0: 未激活
     * 1: 上线
     * 2: 离线
     */
    private Integer deviceStatus;
    /**
     * 激活时间
     */
    private String activateTime;
    /**
     * 最后登录时间
     */
    private String lastOnlineTime;
    /**
     * 最后离线时间
     */
    private String lastOfflineTime;
    /**
     * 创建时间 北京时间
     */
    private String bjCreateTime;
    /**
     * 更新时间 北京时间
     */
    private String bjUpdateTime;
    /**
     * 删除标记
     */
    private Integer isDeleted;

    @Override
    public String toString() {
        return "AirDevice{" +
                "id=" + id +
                ", imei='" + imei + '\'' +
                ", alias='" + alias + '\'' +
                ", communityId='" + communityId + '\'' +
                ", deviceStatus=" + deviceStatus +
                ", activateTime='" + activateTime + '\'' +
                ", lastOnlineTime='" + lastOnlineTime + '\'' +
                ", lastOfflineTime='" + lastOfflineTime + '\'' +
                ", bjCreateTime='" + bjCreateTime + '\'' +
                ", bjUpdateTime='" + bjUpdateTime + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public Integer getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(Integer deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getActivateTime() {
        return activateTime;
    }

    public void setActivateTime(String activateTime) {
        this.activateTime = activateTime;
    }

    public String getLastOnlineTime() {
        return lastOnlineTime;
    }

    public void setLastOnlineTime(String lastOnlineTime) {
        this.lastOnlineTime = lastOnlineTime;
    }

    public String getLastOfflineTime() {
        return lastOfflineTime;
    }

    public void setLastOfflineTime(String lastOfflineTime) {
        this.lastOfflineTime = lastOfflineTime;
    }

    public String getBjCreateTime() {
        return bjCreateTime;
    }

    public void setBjCreateTime(String bjCreateTime) {
        this.bjCreateTime = bjCreateTime;
    }

    public String getBjUpdateTime() {
        return bjUpdateTime;
    }

    public void setBjUpdateTime(String bjUpdateTime) {
        this.bjUpdateTime = bjUpdateTime;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }
}
