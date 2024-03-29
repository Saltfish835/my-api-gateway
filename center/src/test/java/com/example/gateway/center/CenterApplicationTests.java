package com.example.gateway.center;

import com.alibaba.fastjson.JSON;
import com.example.gateway.center.application.IConfigManageService;
import com.example.gateway.center.application.IRegisterManageService;
import com.example.gateway.center.domain.manage.model.aggregates.ApplicationSystemRichInfo;
import com.example.gateway.center.domain.manage.model.vo.GatewayServerVO;
import com.example.gateway.center.domain.register.model.vo.ApplicationInterfaceMethodVO;
import com.example.gateway.center.domain.register.model.vo.ApplicationInterfaceVO;
import com.example.gateway.center.domain.register.model.vo.ApplicationSystemVO;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class CenterApplicationTests {

    private Logger logger = LoggerFactory.getLogger(CenterApplicationTests.class);

    @Resource
    private IConfigManageService configManageService;


    @Resource
    private IRegisterManageService registerManageService;

    @Test
    public void test_queryGatewayServerList() {
        final List<GatewayServerVO> gatewayServerVOS = configManageService.queryGatewayServerList();
        logger.info(JSON.toJSONString(gatewayServerVOS));
    }


    @Test
    public void test_registerGatewayServerNode() {
        configManageService.registerGatewayServerNode("10001","api-gateway-g1", "Kafka网关", "127.0.0.196");
        configManageService.registerGatewayServerNode("10001","api-gateway-g2", "ES网关","127.0.0.198");
        configManageService.registerGatewayServerNode("10001","api-gateway-g3","系统告警网关","1278.0.0.198");
    }

    @Test
    public void test_registerApplication() {
        ApplicationSystemVO applicationSystemVO = new ApplicationSystemVO();
        applicationSystemVO.setSystemId("api-gateway-test");
        applicationSystemVO.setSystemName("网关测试系统");
        applicationSystemVO.setSystemType("RPC");
        applicationSystemVO.setSystemRegistry("127.0.0.1");
        registerManageService.registerApplication(applicationSystemVO);
    }

    @Test
    public void test_registerApplicationInterface() {
        ApplicationInterfaceVO applicationInterfaceVO = new ApplicationInterfaceVO();
        applicationInterfaceVO.setSystemId("api-gateway-test");
        applicationInterfaceVO.setInterfaceId("cn.bugstack.gateway.rpc.IActivityBooth");
        applicationInterfaceVO.setInterfaceName("活动平台");
        applicationInterfaceVO.setInterfaceVersion("v1.0.0");
        registerManageService.registerApplicationInterface(applicationInterfaceVO);
    }

    @Test
    public void test_registerApplicationInterfaceMethod() {
        ApplicationInterfaceMethodVO applicationInterfaceVO01 = new ApplicationInterfaceMethodVO();
        applicationInterfaceVO01.setSystemId("api-gateway-test");
        applicationInterfaceVO01.setInterfaceId("cn.bugstack.gateway.rpc.IActivityBooth");
        applicationInterfaceVO01.setMethodId("sayHi");
        applicationInterfaceVO01.setMethodName("测试方法");
        applicationInterfaceVO01.setParameterType("java.lang.String");
        applicationInterfaceVO01.setUri("/wg/activity/sayHi");
        applicationInterfaceVO01.setHttpCommandType("GET");
        applicationInterfaceVO01.setAuth(0);
        registerManageService.registerApplicationInterfaceMethod(applicationInterfaceVO01);

        ApplicationInterfaceMethodVO applicationInterfaceVO02 = new ApplicationInterfaceMethodVO();
        applicationInterfaceVO02.setSystemId("api-gateway-test");
        applicationInterfaceVO02.setInterfaceId("cn.bugstack.gateway.rpc.IActivityBooth");
        applicationInterfaceVO02.setMethodId("insert");
        applicationInterfaceVO02.setMethodName("插入方法");
        applicationInterfaceVO02.setParameterType("cn.bugstack.gateway.rpc.dto.XReq");
        applicationInterfaceVO02.setUri("/wg/activity/insert");
        applicationInterfaceVO02.setHttpCommandType("POST");
        applicationInterfaceVO02.setAuth(1);
        registerManageService.registerApplicationInterfaceMethod(applicationInterfaceVO02);
    }

    @Test
    public void test_queryApplicationSystemRichInfo(){
        ApplicationSystemRichInfo result = configManageService.queryApplicationSystemRichInfo("api-gateway-g4","api-gateway-test-provider");
        logger.info("测试结果：{}", JSON.toJSONString(result));
    }

}
