package com.example.assist.application;

import com.example.assist.config.GatewayServiceProperties;
import com.example.assist.domain.service.GatewayCenterService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class GatewayApplication implements ApplicationListener<ContextRefreshedEvent> {

    private GatewayServiceProperties properties;
    private GatewayCenterService registerGatewayService;


    public GatewayApplication(GatewayServiceProperties properties, GatewayCenterService registerGatewayService) {
        this.properties = properties;
        this.registerGatewayService = registerGatewayService;
    }

    /**
     * ApplicationListener<ContextRefreshedEvent>中的方法
     * 所有bean都加载完成后会自动调用此方法
     * @param event
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        // 注册网关服务
        registerGatewayService.doRegister(properties.getAddress(), properties.getGroupId(), properties.getGatewayId(),
                properties.getGatewayName(), properties.getGatewayAddress());
        // 拉去网关配置

    }
}
