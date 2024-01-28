package com.example.gateway.assist.config;

import com.example.gateway.assist.application.GatewayApplication;
import com.example.gateway.assist.domain.service.GatewayCenterService;
import io.netty.channel.Channel;
import org.example.gateway.core.session.GatewaySessionFactory;
import org.example.gateway.core.session.defaults.DefaultGatewaySessionFactory;
import org.example.gateway.core.socket.GatewaySocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Configuration
@EnableConfigurationProperties(GatewayServiceProperties.class)
public class GatewayAutoConfig {

    private Logger logger = LoggerFactory.getLogger(GatewayAutoConfig.class);

    @Bean
    public GatewayCenterService registerGatewayService() {
        return new GatewayCenterService();
    }

    @Bean
    public GatewayApplication gatewayApplication(GatewayServiceProperties properties, GatewayCenterService registerGatewayService,
                                                 org.example.gateway.core.session.Configuration configuration, Channel gatewaySocketServerChannel) {
        return new GatewayApplication(properties,registerGatewayService, configuration, gatewaySocketServerChannel);
    }

    @Bean
    public org.example.gateway.core.session.Configuration gatewayCoreConfiguration(GatewayServiceProperties properties) {
        final org.example.gateway.core.session.Configuration configuration = new org.example.gateway.core.session.Configuration();
        final String[] gatewayAddressStrArr = properties.getGatewayAddress().split(":");
        configuration.setHostName(gatewayAddressStrArr[0].trim());
        configuration.setPort(Integer.parseInt(gatewayAddressStrArr[1].trim()));
        return configuration;
    }

    @Bean("gatewaySocketServerChannel")
    public Channel initGateway(org.example.gateway.core.session.Configuration configuration) throws ExecutionException, InterruptedException {
        GatewaySessionFactory gatewaySessionFactory = new DefaultGatewaySessionFactory(configuration);
        final GatewaySocketServer socketServer = new GatewaySocketServer(configuration, gatewaySessionFactory);
        final Future<Channel> future = Executors.newFixedThreadPool(2).submit(socketServer);
        final Channel channel = future.get();
        if(channel == null) {
            throw new RuntimeException("api gateway core server start error, channel is null");
        }
        while(!channel.isActive()) {
            logger.info("api gateway core server starting...");
            Thread.sleep(500);
        }
        logger.info("api gateway core server start done! {}", channel.localAddress());
        return channel;
    }

}
