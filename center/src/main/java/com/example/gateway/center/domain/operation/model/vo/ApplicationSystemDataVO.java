package com.example.gateway.center.domain.operation.model.vo;

public class ApplicationSystemDataVO {

    private String systemId;
    private String systemName;
    private String systemType;
    private String systemRegistry;

    public ApplicationSystemDataVO() {
    }

    public ApplicationSystemDataVO(String systemId, String systemName) {
        this.systemId = systemId;
        this.systemName = systemName;
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

    public String getSystemType() {
        return systemType;
    }

    public void setSystemType(String systemType) {
        this.systemType = systemType;
    }

    public String getSystemRegistry() {
        return systemRegistry;
    }

    public void setSystemRegistry(String systemRegistry) {
        this.systemRegistry = systemRegistry;
    }
}
