package org.example.core.socket.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import org.example.core.mapping.HttpStatement;
import org.example.core.session.Configuration;
import org.example.core.socket.BaseHandler;
import org.example.core.socket.agreement.AgreementConstants;
import org.example.core.socket.agreement.GatewayResultMessage;
import org.example.core.socket.agreement.RequestParser;
import org.example.core.socket.agreement.ResponseParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GatewayServerHandler extends BaseHandler<FullHttpRequest> {

    private final Logger logger = LoggerFactory.getLogger(GatewayServerHandler.class);

    private final Configuration configuration;

    public GatewayServerHandler(Configuration configuration) {
        this.configuration = configuration;
    }


    @Override
    protected void session(ChannelHandlerContext ctx, Channel channel, FullHttpRequest request) {
        logger.info("网关接收请求【全局】 uri: {} method: {}", request.uri(), request.method());
        try {
            final RequestParser parser = new RequestParser(request);
            final String uri = parser.getUri();

            final HttpStatement httpStatement = configuration.getHttpStatement(uri);
            channel.attr(AgreementConstants.HTTP_STATEMENT).set(httpStatement);

            request.retain();
            ctx.fireChannelRead(request);
        }catch (Exception e){
            final GatewayResultMessage resultMessage = GatewayResultMessage.buildError(AgreementConstants.ResponseCode._500.getCode(), "网关调用失败！" + e.getMessage());
            final DefaultFullHttpResponse response = new ResponseParser().parse(resultMessage);
            channel.writeAndFlush(response);
        }
    }
}
