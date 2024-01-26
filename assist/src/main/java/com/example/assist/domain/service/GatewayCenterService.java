package com.example.assist.domain.service;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.assist.GatewayException;
import com.example.assist.common.Result;
import com.example.assist.domain.aggregates.ApplicationSystemRichInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class GatewayCenterService {

    private Logger logger = LoggerFactory.getLogger(GatewayCenterService.class);

    public void doRegister(String address, String groupId, String gatewayId, String gatewayName, String gatewayAddress) {
        final HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("groupId", groupId);
        paramMap.put("gatewayId", gatewayId);
        paramMap.put("gatewayName", gatewayName);
        paramMap.put("gatewayAddress", gatewayAddress);
        final String resultStr = HttpUtil.post(address+ "/wg/admin/config/registerGateway", paramMap, 350);
        final Result result = JSON.parseObject(resultStr, Result.class);
        logger.info("向网关中心注册网关服务 gatewayId: {}, gatewayName: {}, gatewayAddress: {}, 注册结果: {}", gatewayId,
                gatewayName, gatewayAddress, result);
        if(!"0000".equals(result.getCode())) {
            throw new GatewayException("网关服务注册异常 [gatewayId: "+gatewayId+"]、[gatewayAddress: "+gatewayAddress+"]");
        }
    }

    public ApplicationSystemRichInfo pullApplicationSystemRichInfo(String address, String gatewayId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("gatewayId", gatewayId);
        String resultStr = HttpUtil.post(address + "/wg/admin/config/queryApplicationSystemRichInfo", paramMap, 350);
        final Result result = JSON.parseObject(resultStr, new TypeReference<Result<ApplicationSystemRichInfo>>() {
        });
        logger.info("从网关中心拉取应用服务和接口配置信息到本地完成注册， gatewayId: {}", gatewayId);
        if(!"0000".equals(result.getCode())) {
            throw new GatewayException("从网关中心拉取应用服务和接口的配置信息到本地异常 [gatewayId: "+gatewayId+" ]");
        }
        return (ApplicationSystemRichInfo) result.getData();
    }
}
