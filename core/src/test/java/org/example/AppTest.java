package org.example;

import static org.junit.Assert.assertTrue;

import io.jsonwebtoken.Claims;
import io.netty.channel.Channel;
import org.example.gateway.core.authorization.IAuth;
import org.example.gateway.core.authorization.JwtUtil;
import org.example.gateway.core.authorization.service.AuthService;
import org.example.gateway.core.mapping.HttpCommandType;
import org.example.gateway.core.mapping.HttpStatement;
import org.example.gateway.core.session.Configuration;
import org.example.gateway.core.session.defaults.DefaultGatewaySessionFactory;
import org.example.gateway.core.socket.GatewaySocketServer;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Unit test for simple App.
 */
public class AppTest 
{

    private static final Logger logger = LoggerFactory.getLogger(AppTest.class);


    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }


    @Test
    public void testJwt() {
        // 用户
        String issuer = "hahaha";
        // 过期时间
        long ttlMillis = 7*24*60*60*1000L;
        final HashMap<String, Object> claims = new HashMap<>();
        claims.put("key","hahaha");

        // 生成jwt tokenn
        final String token = JwtUtil.encode(issuer, ttlMillis, claims);
        System.out.println(token);

        final Claims c = JwtUtil.decode(token);
        System.out.println(c.getSubject());
    }

    @Test
    public void testAuth() {
        final IAuth auth = new AuthService();
        final boolean result = auth.validate("hahaha", "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJoYWhhaGEiLCJleHAiOjE3MDYxMDM0ODEsImlhdCI6MTcwNTQ5ODY4MSwia2V5IjoiaGFoYWhhIn0.jLP2Fsay3u2bTYY8jYVSG3Du96zDX4ZVJMXHTepgMrU");
        System.out.println(result);
    }

    @Test
    public static void testMain( String[] args ) throws Exception
    {

        // 创建配置信息
        final Configuration configuration = new Configuration();
        configuration.setHostName("127.0.0.1");
        configuration.setPort(7397);


        // 注册rpc服务地址
        configuration.registryConfig("api-gateway-test",
                "zookeeper://127.0.0.1:2181",
                "cn.bugstack.gateway.rpc.IActivityBooth",
                "1.0.0");

        // 注册接口
        final HttpStatement sayHi = new HttpStatement("/wg/activity/sayHi",
                HttpCommandType.GET,
                "api-gateway-test",
                "cn.bugstack.gateway.rpc.IActivityBooth",
                "sayHi",
                "java.lang.String",
                false);
        final HttpStatement insert = new HttpStatement("/wg/activity/insert",
                HttpCommandType.POST,
                "api-gateway-test",
                "cn.bugstack.gateway.rpc.IActivityBooth",
                "insert",
                "cn.bugstack.gateway.rpc.dto.XReq",
                true);
        configuration.addMapper(sayHi);
        configuration.addMapper(insert);

        // 创建会话工厂
        final DefaultGatewaySessionFactory sessionFactory = new DefaultGatewaySessionFactory(configuration);

        // 启动网关服务
        final GatewaySocketServer socketServer = new GatewaySocketServer(configuration, sessionFactory);
        final Future<Channel> future = Executors.newFixedThreadPool(2).submit(socketServer);
        final Channel channel = future.get();
        if(channel == null) {
            throw new RuntimeException("socket sever start error channel is null");
        }
        while(!channel.isActive()) {
            logger.info("socket server stating...");
            Thread.sleep(500);
        }
        logger.info("socket server start done! {}",channel.localAddress());

        Thread.sleep(Long.MAX_VALUE);
    }
}
