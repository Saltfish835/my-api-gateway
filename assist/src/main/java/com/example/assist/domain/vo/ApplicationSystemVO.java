package com.example.assist.domain.vo;

import java.util.List;

public class ApplicationSystemVO {

    private String systemId;
    private String systemName;
    private String systemType;
    private String systemRegistry;
    private List<ApplicationInterfaceVO> interfaceVOList;

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

    public List<ApplicationInterfaceVO> getInterfaceVOList() {
        return interfaceVOList;
    }

    public void setInterfaceVOList(List<ApplicationInterfaceVO> interfaceVOList) {
        this.interfaceVOList = interfaceVOList;
    }
}
