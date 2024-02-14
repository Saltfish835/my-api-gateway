package com.example.gateway.center.interfaces;


import com.alibaba.fastjson.JSON;
import com.example.gateway.center.application.IDataOperationManageService;
import com.example.gateway.center.domain.operation.model.vo.*;
import com.example.gateway.center.infrastructure.common.OperationRequest;
import com.example.gateway.center.infrastructure.common.OperationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@CrossOrigin
@RestController
@RequestMapping("/wg/admin/data")
public class DataOperationManage {

    private Logger logger = LoggerFactory.getLogger(DataOperationManage.class);

    @Resource
    private IDataOperationManageService dataOperationManageService;

    @GetMapping(value = "queryGatewayServer", produces = "application/json;charset=utf-8")
    public OperationResult<GatewayServerDataVO> queryGatewayServer(@RequestParam String groupId, @RequestParam String page,
                                                                   @RequestParam String limit) {
        try {
            logger.info("查询网关服务数据开始 groupId: {} page: {} limit: {}", groupId, page, limit);
            final OperationRequest<String> request = new OperationRequest<>(page, limit);
            request.setData(groupId);
            final OperationResult<GatewayServerDataVO> operationResult = dataOperationManageService.queryGatewayServer(request);
            logger.info("查询网关服务数据完成 operationResult: {}", JSON.toJSONString(operationResult));
            return operationResult;
        }catch (Exception e) {
            logger.error("查询网关服务数据异常 groupId: {}", groupId, e);
            return new OperationResult<>(0, null);
        }
    }

    @GetMapping(value = "queryGatewayServerDetail", produces = "application/json;charset=utf-8")
    public OperationResult<GatewayServerDetailDataVO> queryGatewayServerDetail(@RequestParam String groupId, @RequestParam String gatewayId,
                                                                               @RequestParam String page, @RequestParam String limit) {
        try {
            logger.info("查询网关服务详情数据开始 groupId: {} gatewayId: {} page: {} limit: {}", groupId, gatewayId, page, limit);
            final OperationRequest<GatewayServerDetailDataVO> request = new OperationRequest<>(page, limit);
            request.setData(new GatewayServerDetailDataVO(groupId, gatewayId));
            final OperationResult<GatewayServerDetailDataVO> operationResult = dataOperationManageService.queryGatewayServerDetail(request);
            logger.info("查询网关服务详情数据完成 operationResult: {}", JSON.toJSONString(operationResult));
            return operationResult;
        }catch (Exception e) {
            logger.error("查询网关服务详情数据异常 groupId: {}", groupId, e);
            return new OperationResult<>(0, null);
        }
    }

    @GetMapping(value = "queryGatewayDistribution", produces = "application/json;charset=utf-8")
    public OperationResult<GatewayDistributionDataVO> queryGatewayDistribution(@RequestParam String groupId, @RequestParam String gatewayId,
                                                                               @RequestParam String page, @RequestParam String limit) {
        try {
            logger.info("查询网关分配数据开始 groupId： {} gatewayId: {} page: {} limit: {}", groupId, gatewayId, page, limit);
            final OperationRequest<GatewayDistributionDataVO> request = new OperationRequest<>(page, limit);
            request.setData(new GatewayDistributionDataVO(groupId, gatewayId));
            final OperationResult<GatewayDistributionDataVO> operationResult = dataOperationManageService.queryGatewayDistribution(request);
            logger.info("查询网关分配数据完成 operationResult: {}", JSON.toJSONString(operationResult));
            return operationResult;
        }catch (Exception e) {
            logger.error("查询网关分配数据异常 groupId: {}", groupId, e);
            return new OperationResult<>(0, null);
        }
    }

    @GetMapping(value="queryApplicationSystem", produces = "application/json;charset=utf-8")
    public OperationResult<ApplicationSystemDataVO> queryApplicationSystem(@RequestParam String systemId, @RequestParam String systemName,
                                                                           @RequestParam String page, @RequestParam String limit) {
        try {
            logger.info("查询应用系统信息开始 systemId: {} systemName: {} page: {} limit: {}", systemId, systemName, page, limit);
            OperationRequest<ApplicationSystemDataVO> request = new OperationRequest<>(page, limit);
            request.setData(new ApplicationSystemDataVO(systemId, systemName));
            final OperationResult<ApplicationSystemDataVO> operationResult = dataOperationManageService.queryApplicationSystem(request);
            logger.info("查询应用系统信息完成 operationResult: {}", JSON.toJSONString(operationResult));
            return operationResult;
        }catch (Exception e) {
            logger.error("查询应用系统信息完成 systemId: {} systemName: {}", systemId, systemName, e);
            return new OperationResult<>(0, null);
        }
    }

    @GetMapping(value = "queryApplicationInterface", produces = "application/json;charset=utf-8")
    public OperationResult<ApplicationInterfaceDataVO> queryApplicationInterface(@RequestParam String systemId, @RequestParam String interfaceId,
                                                                                 @RequestParam String page, @RequestParam String limit) {
        try {
            logger.info("查询应用接口信息开始 systemId: {} interfaceId: {} page: {} limit: {}", systemId, interfaceId, page, limit);
            OperationRequest<ApplicationInterfaceDataVO> request = new OperationRequest<>(page, limit);
            request.setData(new ApplicationInterfaceDataVO(systemId, interfaceId));
            final OperationResult<ApplicationInterfaceDataVO> operationResult = dataOperationManageService.queryApplicationInterface(request);
            logger.info("查询应用接口信息完成 operationResult: {}", JSON.toJSONString(operationResult));
            return operationResult;
        }catch (Exception e) {
            logger.error("查询应用接口信息异常 systemId: {} interfaceId: {}", systemId, interfaceId, e);
            return new OperationResult<>(0, null);
        }
    }

    @GetMapping(value = "queryApplicationInterfaceMethodList", produces = "application/json;charset=utf-8")
    public OperationResult<ApplicationInterfaceMethodDataVO> queryApplicationInterfaceMethodList(@RequestParam String systemId, @RequestParam String interfaceId,
                                                                                                 @RequestParam String page, @RequestParam String limit) {
        try {
            logger.info("查询应用接口方法信息开始 systemId: {} interfaceId: {} page: {} limit: {}", systemId, interfaceId, page, limit);
            OperationRequest<ApplicationInterfaceMethodDataVO> request = new OperationRequest<>(page, limit);
            request.setData(new ApplicationInterfaceMethodDataVO(systemId, interfaceId));
            final OperationResult<ApplicationInterfaceMethodDataVO> operationResult = dataOperationManageService.queryApplicationInterfaceMethod(request);
            logger.info("查询应用接口方法信息完成 operationResult: {}", JSON.toJSONString(operationResult));
            return operationResult;
        }catch (Exception e) {
            logger.error("查询应用接口方法信息异常 systemId; {} interfaceId: {}", systemId, interfaceId, e);
            return new OperationResult<>(0, null);
        }
    }



}
