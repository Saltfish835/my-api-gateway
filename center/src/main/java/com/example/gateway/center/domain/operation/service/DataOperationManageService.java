package com.example.gateway.center.domain.operation.service;

import com.example.gateway.center.application.IDataOperationManageService;
import com.example.gateway.center.domain.operation.model.vo.*;
import com.example.gateway.center.domain.operation.repository.IDataOperationManageRepository;
import com.example.gateway.center.infrastructure.common.OperationRequest;
import com.example.gateway.center.infrastructure.common.OperationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DataOperationManageService implements IDataOperationManageService {

    private Logger logger = LoggerFactory.getLogger(DataOperationManageService.class);

    @Resource
    private IDataOperationManageRepository repository;

    @Override
    public OperationResult<GatewayServerDataVO> queryGatewayServer(OperationRequest<String> request) {
        List<GatewayServerDataVO> list = repository.queryGatewayServerListByPage(request);
        final int count = repository.queryGatewayServerListCountByPage(request);
        return new OperationResult<>(count, list);
    }

    @Override
    public OperationResult<ApplicationSystemDataVO> queryApplicationSystem(OperationRequest<ApplicationSystemDataVO> request) {
        final List<ApplicationSystemDataVO> list = repository.queryApplicationSystemListByPage(request);
        final int count = repository.queryApplicationSystemListCountByPage(request);
        return new OperationResult<>(count, list);
    }

    @Override
    public OperationResult<ApplicationInterfaceDataVO> queryApplicationInterface(OperationRequest<ApplicationInterfaceDataVO> request) {
        final List<ApplicationInterfaceDataVO> list = repository.queryApplicationInterfaceListByPage(request);
        final int count = repository.queryApplicationInterfaceListCountByPage(request);
        return new OperationResult<>(count, list);
    }

    @Override
    public OperationResult<ApplicationInterfaceMethodDataVO> queryApplicationInterfaceMethod(OperationRequest<ApplicationInterfaceMethodDataVO> request) {
        final List<ApplicationInterfaceMethodDataVO> list = repository.queryApplicationInterfaceMethodListByPage(request);
        final int count = repository.queryApplicationInterfaceMethodListCountByPage(request);
        return new OperationResult<>(count, list);
    }

    @Override
    public OperationResult<GatewayServerDetailDataVO> queryGatewayServerDetail(OperationRequest<GatewayServerDetailDataVO> request) {
        final List<GatewayServerDetailDataVO> list = repository.queryGatewayServerDetailListByPage(request);
        final int count = repository.queryGatewayServerDetailListCountByPage(request);
        return new OperationResult<>(count, list);
    }

    @Override
    public OperationResult<GatewayDistributionDataVO> queryGatewayDistribution(OperationRequest<GatewayDistributionDataVO> request) {
        final List<GatewayDistributionDataVO> list = repository.queryGatewayDistributionListByPage(request);
        final int count = repository.queryGatewayDistributionListCountByPage(request);
        return new OperationResult<>(count, list);
    }
}
