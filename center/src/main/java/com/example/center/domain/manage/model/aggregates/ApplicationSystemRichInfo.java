package com.example.center.domain.manage.model.aggregates;


import com.example.center.domain.manage.model.vo.ApplicationSystemVO;

import java.util.List;

public class ApplicationSystemRichInfo {

    private String gatewayId;

    private List<ApplicationSystemVO> applicationSystemVOList;

    public ApplicationSystemRichInfo() {

    }

    public ApplicationSystemRichInfo(String gatewayId, List<ApplicationSystemVO> applicationSystemVOList) {
        this.gatewayId = gatewayId;
        this.applicationSystemVOList = applicationSystemVOList;
    }

    public String getGatewayId() {
        return gatewayId;
    }

    public void setGatewayId(String gatewayId) {
        this.gatewayId = gatewayId;
    }

    public List<ApplicationSystemVO> getApplicationSystemVOList() {
        return applicationSystemVOList;
    }

    public void setApplicationSystemVOList(List<ApplicationSystemVO> applicationSystemVOList) {
        this.applicationSystemVOList = applicationSystemVOList;
    }
}
