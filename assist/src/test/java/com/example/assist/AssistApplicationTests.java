package com.example.assist;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.example.assist.common.Result;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;

class AssistApplicationTests {

    @Test
    void contextLoads() {
        final HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("groupId", "10001");
        paramMap.put("gatewayId", "api-gateway-g4");
        paramMap.put("gatewayName", "电商配送网关");
        paramMap.put("gatewayAddress", "127.0.0.1");

        final String resultStr = HttpUtil.post("http://localhost:8001/wg/admin/config/registerGateway", paramMap, 350);
        final Result result = JSONObject.parseObject(resultStr, Result.class);
        System.out.println(result.getCode());
    }

}
