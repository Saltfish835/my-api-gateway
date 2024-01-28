package org.example.gateway.core.socket.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import org.example.gateway.core.mapping.HttpStatement;
import org.example.gateway.core.session.Configuration;
import org.example.gateway.core.socket.BaseHandler;
import org.example.gateway.core.socket.agreement.AgreementConstants;
import org.example.gateway.core.socket.agreement.GatewayResultMessage;
import org.example.gateway.core.socket.agreement.ResponseParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AuthorizationHandler extends BaseHandler<FullHttpRequest> {

    private final Logger logger = LoggerFactory.getLogger(AuthorizationHandler.class);

    private final Configuration configuration;

    public AuthorizationHandler(Configuration configuration) {
        this.configuration = configuration;
    }


    @Override
    protected void session(ChannelHandlerContext ctx, Channel channel, FullHttpRequest request) {
        logger.info("网关接收请求【鉴权】 uri: {} method: {}",request.uri(), request.method());
        try{
            final HttpStatement httpStatement = channel.attr(AgreementConstants.HTTP_STATEMENT).get();
            // uri是否需要鉴权
            if(httpStatement.isAuth()) {
                try{
                    final String uId = request.headers().get("uId");
                    final String token = request.headers().get("token");
                    // 如果token为空
                    if(token == null || "".equals(token)) {
                        final GatewayResultMessage resultMessage = GatewayResultMessage.buildError(AgreementConstants.ResponseCode._400.getCode(), "token不合法");
                        final DefaultFullHttpResponse response = new ResponseParser().parse(resultMessage);
                        channel.writeAndFlush(response);
                    }
                    // 鉴权
                    final boolean authValidate = configuration.authValidate(uId, token);
                    //鉴权成功,直接放行
                    if(authValidate) {
                        request.retain();
                        ctx.fireChannelRead(request);
                    }else { // 鉴权失败
                        final GatewayResultMessage resultMessage = GatewayResultMessage.buildError(AgreementConstants.ResponseCode._403.getCode(), "非法用户");
                        final DefaultFullHttpResponse response = new ResponseParser().parse(resultMessage);
                        channel.writeAndFlush(response);
                    }
                }catch (Exception e) {
                    final GatewayResultMessage resultMessage = GatewayResultMessage.buildError(AgreementConstants.ResponseCode._403.getCode(), "鉴权失败");
                    final DefaultFullHttpResponse response = new ResponseParser().parse(resultMessage);
                    channel.writeAndFlush(response);
                }
            }else { // uri不需要鉴权
                request.retain();
                ctx.fireChannelRead(request);
            }
        }catch (Exception e) {
            final GatewayResultMessage resultMessage = GatewayResultMessage.buildError(AgreementConstants.ResponseCode._500.getCode(), "网关协议调用失败！" + e.getMessage());
            final DefaultFullHttpResponse response = new ResponseParser().parse(resultMessage);
            channel.writeAndFlush(response);
        }
    }
}
