package com.example.assist.service;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.example.assist.GatewayException;
import com.example.assist.common.Result;
import com.sun.corba.se.spi.orbutil.fsm.Guard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class RegisterGatewayService {

    private Logger logger = LoggerFactory.getLogger(RegisterGatewayService.class);

    public void doRegister(String address, String groupId, String gatewayId, String gatewayName, String gatewayAddress) {
        final HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("groupId", groupId);
        paramMap.put("gatewayId", gatewayId);
        paramMap.put("gatewayName", gatewayName);
        paramMap.put("gatewayAddress", gatewayAddress);
        final String resultStr = HttpUtil.post(address, paramMap, 350);
        final Result result = JSON.parseObject(resultStr, Result.class);
        logger.info("向网关中心注册网关服务 gatewayId: {}, gatewayName: {}, gatewayAddress: {}, 注册结果: {}", gatewayId,
                gatewayName, gatewayAddress, result);
        if(!"0000".equals(result.getCode())) {
            throw new GatewayException("网关服务注册异常 [gatewayId: "+gatewayId+"]、[gatewayAddress: "+gatewayAddress+"]");
        }
    }
}
