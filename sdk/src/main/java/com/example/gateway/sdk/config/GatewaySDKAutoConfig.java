package com.example.gateway.sdk.config;

import com.example.gateway.sdk.application.GatewaySDKApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(GatewaySDKServiceProperties.class)
public class GatewaySDKAutoConfig {

    private Logger logger = LoggerFactory.getLogger(GatewaySDKAutoConfig.class);

    public GatewaySDKApplication gatewaySDKApplication(GatewaySDKServiceProperties properties) {
        return new GatewaySDKApplication(properties);
    }
}
