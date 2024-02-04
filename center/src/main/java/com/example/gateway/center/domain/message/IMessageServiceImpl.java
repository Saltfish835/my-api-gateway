package com.example.gateway.center.domain.message;

import com.example.gateway.center.application.IMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Primary
@Service
public class IMessageServiceImpl implements IMessageService {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private Integer port;

    @Resource
    private Publisher publisher;


    @Override
    public Map<String, String> queryRedisConfig() {
        final HashMap<String, String> conf = new HashMap<>();
        conf.put("host", host);
        conf.put("port", String.valueOf(port));
        return conf;
    }

    @Override
    public void pushMessage(String gatewayId, Object message) {
        publisher.pushMessage(gatewayId, message);
    }
}
