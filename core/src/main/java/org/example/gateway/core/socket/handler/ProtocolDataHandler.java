package org.example.gateway.core.socket.handler;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import org.example.gateway.core.bind.IGenericReference;
import org.example.gateway.core.executor.result.SessionResult;
import org.example.gateway.core.session.GatewaySession;
import org.example.gateway.core.session.GatewaySessionFactory;
import org.example.gateway.core.socket.BaseHandler;
import org.example.gateway.core.socket.agreement.AgreementConstants;
import org.example.gateway.core.socket.agreement.GatewayResultMessage;
import org.example.gateway.core.socket.agreement.RequestParser;
import org.example.gateway.core.socket.agreement.ResponseParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class ProtocolDataHandler extends BaseHandler<FullHttpRequest> {

    private final Logger logger = LoggerFactory.getLogger(ProtocolDataHandler.class);

    private final GatewaySessionFactory gatewaySessionFactory;

    public ProtocolDataHandler(GatewaySessionFactory gatewaySessionFactory) {
        this.gatewaySessionFactory = gatewaySessionFactory;
    }

    @Override
    protected void session(ChannelHandlerContext ctx, Channel channel, FullHttpRequest request) {
        logger.info("网关接收请求【消息】 uri: {} method: {}", request.uri(), request.method());
        try{
            final RequestParser parser = new RequestParser(request);
            final String uri = parser.getUri();
            if(uri == null) {
                return;
            }
            final Map<String, Object> parameter = parser.parse();
            final GatewaySession gatewaySession = gatewaySessionFactory.openSession(uri);
            final IGenericReference mapper = gatewaySession.getMapper();
            final SessionResult sessionResult = mapper.$invoke(parameter);
            // 3. 封装返回结果
            DefaultFullHttpResponse response = new ResponseParser().parse("0000".equals(sessionResult.getCode()) ?
                    GatewayResultMessage.buildSuccess(sessionResult.getData()) : GatewayResultMessage.buildError(AgreementConstants.ResponseCode._404.getCode(), "网关协议调用失败！"));
            channel.writeAndFlush(response);
        }catch (Exception e) {
            final GatewayResultMessage resultMessage = GatewayResultMessage.buildError(AgreementConstants.ResponseCode._502.getCode(), "网关调用失败！" + e.getMessage());
            final DefaultFullHttpResponse response = new ResponseParser().parse(resultMessage);
            channel.writeAndFlush(response);
        }
    }

}
