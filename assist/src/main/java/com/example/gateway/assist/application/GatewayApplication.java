package com.example.gateway.assist.application;

import com.example.gateway.assist.config.GatewayServiceProperties;
import com.example.gateway.assist.domain.aggregates.ApplicationSystemRichInfo;
import com.example.gateway.assist.domain.service.GatewayCenterService;
import com.example.gateway.assist.domain.vo.ApplicationInterfaceMethodVO;
import com.example.gateway.assist.domain.vo.ApplicationInterfaceVO;
import com.example.gateway.assist.domain.vo.ApplicationSystemVO;
import io.netty.channel.Channel;
import org.example.gateway.core.mapping.HttpCommandType;
import org.example.gateway.core.mapping.HttpStatement;
import org.example.gateway.core.session.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import java.util.List;

public class GatewayApplication implements ApplicationContextAware, ApplicationListener<ContextClosedEvent> {

    private Logger logger = LoggerFactory.getLogger(GatewayApplication.class);

    private GatewayServiceProperties properties;
    private GatewayCenterService gatewayCenterService;
    private Configuration configuration;
    private Channel gatewaySocketServerChannel;


    public GatewayApplication(GatewayServiceProperties properties, GatewayCenterService gatewayCenterService, Configuration configuration, Channel gatewaySocketServerChannel) {
        this.properties = properties;
        this.gatewayCenterService = gatewayCenterService;
        this.configuration = configuration;
        this.gatewaySocketServerChannel = gatewaySocketServerChannel;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        try{
            // 注册网关服务
            gatewayCenterService.doRegister(properties.getAddress(), properties.getGroupId(), properties.getGatewayId(),
                    properties.getGatewayName(), properties.getGatewayAddress());
            // 拉去网关配置
            addMappers("");
        }catch (Exception e) {
            logger.error("网关服务启动失败, 停止服务, {}", e.getMessage(), e);
            throw e;
        }
    }


    public void addMappers(String systemId) {
        final ApplicationSystemRichInfo systemRichInfo = gatewayCenterService.pullApplicationSystemRichInfo(properties.getAddress(), properties.getGatewayId(), systemId);
        final List<ApplicationSystemVO> applicationSystemVOList = systemRichInfo.getApplicationSystemVOList();
        if(applicationSystemVOList.isEmpty()) {
            logger.warn("网关{}服务注册映射为空，请排查gatewayCenterService.pullApplicationSystemRichInfo是否检索到次网关算力需要拉取的配置数据", systemId);
            return;
        }
        for(ApplicationSystemVO systemVO: applicationSystemVOList) {
            final List<ApplicationInterfaceVO> interfaceVOList = systemVO.getInterfaceVOList();
            for(ApplicationInterfaceVO interfaceVO : interfaceVOList) {
                configuration.registryConfig(systemVO.getSystemId(), systemVO.getSystemRegistry(), interfaceVO.getInterfaceId(), interfaceVO.getInterfaceVersion());
                final List<ApplicationInterfaceMethodVO> methodVOList = interfaceVO.getMethodVOList();
                for(ApplicationInterfaceMethodVO methodVO : methodVOList) {
                    final HttpStatement httpStatement = new HttpStatement(methodVO.getUri(),
                            HttpCommandType.valueOf(methodVO.getHttpCommandType()),
                            systemVO.getSystemId(),
                            interfaceVO.getInterfaceId(),
                            methodVO.getMethodId(),
                            methodVO.getParameterType(),
                            methodVO.isAuth());
                    configuration.addMapper(httpStatement);
                    logger.info("网关服务注册映射 系统: {}, 接口: {}, 方法: {}", systemVO.getSystemId(), interfaceVO.getInterfaceId(), methodVO.getMethodId());
                }
            }
        }

    }


    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        try{
            if(gatewaySocketServerChannel.isActive()) {
                logger.info("IoC容器关闭, API网关服务关闭, localAddress: {}", gatewaySocketServerChannel.localAddress());
                gatewaySocketServerChannel.close();
            }
        }catch (Exception e) {
            logger.error("IoC容器关闭, API网关关闭失败", e);
        }
    }


    public void receiveMessage(Object message) {
        logger.info("【事件通知】接收注册中心推送消息 message: {}", message);
        addMappers(message.toString().substring(1, message.toString().length() - 1));
    }
}
