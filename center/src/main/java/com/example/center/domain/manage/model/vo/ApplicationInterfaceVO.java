package com.example.center.domain.manage.model.vo;

import java.util.List;

public class ApplicationInterfaceVO {

    private String systemId;
    private String interfaceId;
    private String interfaceName;
    private String interfaceVersion;
    private List<ApplicationInterfaceMethodVO> methodList;

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    public String getInterfaceId() {
        return interfaceId;
    }

    public void setInterfaceId(String interfaceId) {
        this.interfaceId = interfaceId;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getInterfaceVersion() {
        return interfaceVersion;
    }

    public void setInterfaceVersion(String interfaceVersion) {
        this.interfaceVersion = interfaceVersion;
    }

    public List<ApplicationInterfaceMethodVO> getMethodList() {
        return methodList;
    }

    public void setMethodList(List<ApplicationInterfaceMethodVO> methodList) {
        this.methodList = methodList;
    }
}
