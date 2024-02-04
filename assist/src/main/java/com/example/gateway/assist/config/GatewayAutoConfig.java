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
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@Configuration
@EnableConfigurationProperties(GatewayServiceProperties.class)
public class GatewayAutoConfig {

    private Logger logger = LoggerFactory.getLogger(GatewayAutoConfig.class);


    @Bean
    public RedisConnectionFactory redisConnectionFactory(GatewayServiceProperties properties, GatewayCenterService centerService) {
        final Map<String, String> redisConfig = centerService.queryRedisConfig(properties.getAddress());
        final RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisConfig.get("host"));
        redisStandaloneConfiguration.setPort(Integer.parseInt(redisConfig.get("port")));
        final JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxTotal(100);
        jedisPoolConfig.setMaxWaitMillis(30 * 10000);
        jedisPoolConfig.setMinIdle(20);
        jedisPoolConfig.setMaxIdle(40);
        jedisPoolConfig.setTestWhileIdle(true);
        final JedisClientConfiguration jedisClientConfiguration = JedisClientConfiguration.builder().connectTimeout(Duration.ofSeconds(2))
                .clientName("api-gateway-assist-redis-" + properties.getGatewayId())
                .usePooling().poolConfig(jedisPoolConfig).build();
        return new JedisConnectionFactory(redisStandaloneConfiguration, jedisClientConfiguration);
    }


    @Bean
    public RedisMessageListenerContainer container(GatewayServiceProperties properties, RedisConnectionFactory redisConnectionFactory, MessageListenerAdapter messageListenerAdapter) {
        final RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(messageListenerAdapter, new PatternTopic(properties.getGatewayId()));
        return container;
    }


    @Bean
    public MessageListenerAdapter messageListenerAdapter(GatewayApplication gatewayApplication) {
        return new MessageListenerAdapter(gatewayApplication, "receiveMessage");
    }


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
