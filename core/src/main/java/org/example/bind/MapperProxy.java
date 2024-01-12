package org.example.bind;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.example.session.GatewaySession;

import java.lang.reflect.Method;
import java.util.Map;

public class MapperProxy implements MethodInterceptor {

    private GatewaySession gatewaySession;
    private final String uri;

    public MapperProxy(GatewaySession gatewaySession, String uri) {
        this.gatewaySession = gatewaySession;
        this.uri = uri;
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        final MapperMethod mapperMethod = new MapperMethod(uri, method, gatewaySession.getConfiguration());
        // 暂时只获取第0个参数
        return mapperMethod.execute(gatewaySession, (Map<String, Object>) objects[0]);
    }
}
