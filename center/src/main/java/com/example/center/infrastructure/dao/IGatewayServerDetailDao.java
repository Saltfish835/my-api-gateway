package com.example.center.infrastructure.dao;

import com.example.center.infrastructure.po.GatewayServerDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IGatewayServerDetailDao {

    void insert(GatewayServerDetail gatewayServerDetail);

    GatewayServerDetail queryGatewayServerDetail(GatewayServerDetail gatewayServerDetail);

    boolean updateGatewayStatus(GatewayServerDetail gatewayServerDetail);
}
