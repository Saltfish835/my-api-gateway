package org.example.core.session.defaults;

import org.example.core.bind.IGenericReference;
import org.example.core.executor.Executor;
import org.example.core.mapping.HttpStatement;
import org.example.core.session.Configuration;
import org.example.core.session.GatewaySession;

import java.util.Map;

public class DefaultGatewaySession implements GatewaySession {

    private Configuration configuration;
    private String uri;
    private Executor executor;

    public DefaultGatewaySession(Configuration configuration, String uri, Executor executor) {
        this.configuration = configuration;
        this.uri = uri;
        this.executor = executor;
    }

    @Override
    public Object get(String methodName, Map<String, Object> params) {
        // 根据uri， 拿到该uri对应的rpc接口信息
        final HttpStatement httpStatement = configuration.getHttpStatement(uri);
        try{
            return executor.exec(httpStatement, params);
        }catch (Exception e) {
            throw new RuntimeException("Error exec get. Cause: "+e);
        }
    }

    @Override
    public Object post(String methodName, Map<String, Object> params) {
        return get(methodName,params);
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public IGenericReference getMapper() {
        return configuration.getMapper(uri,this);
    }
}