package com.example.gateway.sdk.application;

import com.example.gateway.sdk.annotation.ApiProducerClazz;
import com.example.gateway.sdk.annotation.ApiProducerMethod;
import com.example.gateway.sdk.config.GatewaySDKServiceProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.reflect.Method;

public class GatewaySDKApplication implements BeanPostProcessor {

    private Logger logger = LoggerFactory.getLogger(GatewaySDKApplication.class);

    private GatewaySDKServiceProperties properties;

    public GatewaySDKApplication(GatewaySDKServiceProperties properties) {
        this.properties = properties;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        final ApiProducerClazz apiProducerClazz = bean.getClass().getAnnotation(ApiProducerClazz.class);
        if(apiProducerClazz == null) {
            return bean;
        }
        logger.info("\n应用注册：系统信息 \nsystemId: {} \nsystemName: {} \nsystemType: {} \nsystemRegistry: {}", properties.getSystemId(), properties.getSystemName(), "RPC", properties.getSystemRegistry());
        logger.info("\n应用注册：接口信息 \nsystemId: {} \ninterfaceId: {} \ninterfaceName: {} \ninterfaceVersion: {}", properties.getSystemId(), bean.getClass().getName(), apiProducerClazz.interfaceName(), apiProducerClazz.interfaceVersion());
        final Method[] methods = bean.getClass().getMethods();
        for (Method method : methods) {
            ApiProducerMethod apiProducerMethod = method.getAnnotation(ApiProducerMethod.class);
            if (apiProducerMethod == null) continue;
            // 解析参数
            Class<?>[] parameterTypes = method.getParameterTypes();
            StringBuilder parameters = new StringBuilder();
            for (Class<?> clazz : parameterTypes) {
                parameters.append(clazz.getName()).append(",");
            }
            String parameterType = parameters.toString().substring(0, parameters.toString().lastIndexOf(","));
            logger.info("\n应用注册：方法信息 \nsystemId: {} \ninterfaceId: {} \nmethodId: {} \nmethodName: {} \nparameterType: {} \nuri: {} \nhttpCommandType: {} \nauth: {}",
                    properties.getSystemId(),
                    bean.getClass().getName(),
                    method.getName(),
                    apiProducerMethod.methodName(),
                    parameterType,
                    apiProducerMethod.uri(),
                    apiProducerMethod.httpCommandType(),
                    apiProducerMethod.auth());
        }
        return bean;
    }
}
