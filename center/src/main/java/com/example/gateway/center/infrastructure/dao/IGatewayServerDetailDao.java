package com.example.gateway.center.infrastructure.dao;

import com.example.gateway.center.domain.operation.model.vo.GatewayServerDetailDataVO;
import com.example.gateway.center.infrastructure.common.OperationRequest;
import com.example.gateway.center.infrastructure.po.GatewayServerDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IGatewayServerDetailDao {

    void insert(GatewayServerDetail gatewayServerDetail);

    GatewayServerDetail queryGatewayServerDetail(GatewayServerDetail gatewayServerDetail);

    boolean updateGatewayStatus(GatewayServerDetail gatewayServerDetail);

    List<GatewayServerDetail> queryGatewayServerDetailList();

    List<GatewayServerDetail> queryGatewayServerDetailListByPage(OperationRequest<GatewayServerDetailDataVO> request);

    int queryGatewayServerDetailListCountByPage(OperationRequest<GatewayServerDetailDataVO> request);
}
