package com.example.gateway.center.infrastructure.dao;


import com.example.gateway.center.infrastructure.common.OperationRequest;
import com.example.gateway.center.infrastructure.po.GatewayServer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IGatewayServerDao {

    List<GatewayServer> queryGatewayServerList();

    List<GatewayServer> queryGatewayServerListByPage(OperationRequest<String> request);

    int queryGatewayServerListCountByPage(OperationRequest<String> request);
}
