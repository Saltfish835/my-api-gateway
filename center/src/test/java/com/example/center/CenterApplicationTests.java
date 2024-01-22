package com.example.center;

import com.alibaba.fastjson.JSON;
import com.example.center.application.IConfigManageService;
import com.example.center.domain.manage.model.vo.GatewayServerVO;
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
}
