package com.example.gateway.center.domain.operation.model.vo;

import java.util.Date;

public class GatewayDistributionDataVO {

    private Integer id;
    private String groupId;
    private String gatewayId;
    private String systemId;
    private String systemName;
    private Date createTime;
    private Date updateTime;

    public GatewayDistributionDataVO() {
    }

    public GatewayDistributionDataVO(String groupId, String gatewayId) {
        this.groupId = groupId;
        this.gatewayId = gatewayId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getSystemName() {
        return systemName;
    }

    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
