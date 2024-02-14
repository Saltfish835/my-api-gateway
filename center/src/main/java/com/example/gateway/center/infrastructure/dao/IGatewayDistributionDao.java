package com.example.gateway.center.infrastructure.dao;

import com.example.gateway.center.domain.operation.model.vo.GatewayDistributionDataVO;
import com.example.gateway.center.infrastructure.common.OperationRequest;
import com.example.gateway.center.infrastructure.po.GatewayDistribution;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IGatewayDistributionDao {

    List<String> queryGatewayDistributionSystemIdList(String gatewayId);

    String queryGatewayDistribution(String systemId);

    List<GatewayDistribution> queryGatewayDistributionList();

    List<GatewayDistribution> queryGatewayDistributionListByPage(OperationRequest<GatewayDistributionDataVO> request);

    int queryGatewayDistributionListCountByPage(OperationRequest<GatewayDistributionDataVO> request);
}
