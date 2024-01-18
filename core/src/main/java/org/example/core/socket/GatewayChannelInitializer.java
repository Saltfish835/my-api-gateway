package org.example.core.socket;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import org.example.core.session.Configuration;
import org.example.core.session.GatewaySessionFactory;
import org.example.core.socket.handler.AuthorizationHandler;
import org.example.core.socket.handler.GatewayServerHandler;
import org.example.core.socket.handler.ProtocolDataHandler;

public class GatewayChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final Configuration configuration;
    private final GatewaySessionFactory gatewaySessionFactory;

    public GatewayChannelInitializer(Configuration configuration, GatewaySessionFactory gatewaySessionFactory) {
        this.configuration = configuration;
        this.gatewaySessionFactory = gatewaySessionFactory;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        final ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast(new HttpRequestDecoder());
        pipeline.addLast(new HttpResponseEncoder());
        pipeline.addLast(new HttpObjectAggregator(1024*1024));
        pipeline.addLast(new GatewayServerHandler(configuration));
        pipeline.addLast(new AuthorizationHandler(configuration));
        pipeline.addLast(new ProtocolDataHandler(gatewaySessionFactory));
    }
}
