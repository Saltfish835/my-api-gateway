package com.example.gateway.sdk.application;

import com.alibaba.fastjson.JSON;
import com.example.gateway.sdk.GatewayException;
import com.example.gateway.sdk.annotation.ApiProducerClazz;
import com.example.gateway.sdk.annotation.ApiProducerMethod;
import com.example.gateway.sdk.config.GatewaySDKServiceProperties;
import com.example.gateway.sdk.domain.service.GatewayCenterService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;

public class GatewaySDKApplication implements BeanPostProcessor {

    private Logger logger = LoggerFactory.getLogger(GatewaySDKApplication.class);

    private GatewaySDKServiceProperties properties;
    private GatewayCenterService gatewayCenterService;

    public GatewaySDKApplication(GatewaySDKServiceProperties properties, GatewayCenterService gatewayCenterService) {
        this.properties = properties;
        this.gatewayCenterService = gatewayCenterService;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        // 注册应用
        final ApiProducerClazz apiProducerClazz = bean.getClass().getAnnotation(ApiProducerClazz.class);
        if(apiProducerClazz == null) {
            return bean;
        }
        logger.info("\n应用注册：系统信息 \nsystemId: {} \nsystemName: {} \nsystemType: {} \nsystemRegistry: {}",
                properties.getSystemId(), properties.getSystemName(), "RPC", properties.getSystemRegistry());
        gatewayCenterService.doRegisterApplication(properties.getAddress(), properties.getSystemId(), properties.getSystemName(),
                "RPC", properties.getSystemRegistry());
        // 注册接口
        final Class<?>[] interfaces = bean.getClass().getInterfaces();
        if(interfaces.length != 1) {
            throw new GatewayException(bean.getClass().getName() + "interfaces not one this is " + JSON.toJSONString(interfaces));
        }
        final String interfaceId = interfaces[0].getName();
        logger.info("\n应用注册：接口信息 \nsystemId: {} \ninterfaceId: {} \ninterfaceName: {} \ninterfaceVersion: {}",
                properties.getSystemId(), interfaceId, apiProducerClazz.interfaceName(), apiProducerClazz.interfaceVersion());
        gatewayCenterService.doRegisterApplicationInterface(properties.getAddress(), properties.getSystemId(),interfaceId,
                apiProducerClazz.interfaceName(), apiProducerClazz.interfaceVersion());
        // 注册方法
        final Method[] methods = bean.getClass().getMethods();
        for(Method method: methods) {
            final ApiProducerMethod apiProducerMethod = method.getAnnotation(ApiProducerMethod.class);
            if(apiProducerMethod == null) {
                continue;
            }
            final Class<?>[] parameterTypes = method.getParameterTypes();
            final StringBuilder parameters = new StringBuilder();
            for(Class<?> clazz : parameterTypes) {
                parameters.append(clazz.getName()).append(",");
            }
            final String parameterType = parameters.substring(0, parameters.lastIndexOf(","));
            logger.info("\n应用注册：方法信息 \nsystemId: {} \ninterfaceId: {} \nmethodId: {} \nmethodName: {} \nparameterType: {} \nuri: {} \nhttpCommandType: {} \nauth: {}",
                    properties.getSystemId(), interfaceId, method.getName(), apiProducerMethod.methodName(), parameterType, apiProducerMethod.uri(), apiProducerMethod.httpCommandType(), apiProducerMethod.auth());
            gatewayCenterService.doRegisterApplicationInterfaceMethod(properties.getAddress(), properties.getSystemId(),interfaceId,method.getName(),
                    apiProducerMethod.methodName(), parameterType, apiProducerMethod.uri(), apiProducerMethod.httpCommandType(), apiProducerMethod.auth());
        }
        // 注册完成，执行事件通知
        gatewayCenterService.doRegisterEvent(properties.getAddress(), properties.getSystemId());
        return bean;
    }
}
