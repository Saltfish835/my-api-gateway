package com.example.center.domain.manage.repository;

import com.example.center.domain.manage.model.vo.GatewayServerDetailVO;
import com.example.center.domain.manage.model.vo.GatewayServerVO;

import java.util.List;

public interface IConfigManageRepository {

    List<GatewayServerVO> queryGatewayServerList();

    boolean registerGatewayServerNode(String groupId, String gatewayId, String gatewayName,String gatewayAddress, Integer available);

    GatewayServerDetailVO queryGatewayServerDetail(String gatewayId, String gatewayAddress);

    boolean updateGatewayStatus(String gatewayId, String gatewayAddress, Integer available);
}
