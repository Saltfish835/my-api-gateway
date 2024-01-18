package org.example.core;

import io.netty.channel.Channel;
import org.example.core.mapping.HttpCommandType;
import org.example.core.mapping.HttpStatement;
import org.example.core.session.Configuration;
import org.example.core.session.defaults.DefaultGatewaySessionFactory;
import org.example.core.socket.GatewaySocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Hello world!
 *
 */
public class App 
{
    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main( String[] args ) throws Exception
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
