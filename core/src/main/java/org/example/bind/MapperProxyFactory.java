package org.example.bind;

import net.sf.cglib.core.Signature;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InterfaceMaker;
import org.example.mapping.HttpStatement;
import org.example.session.GatewaySession;
import org.objectweb.asm.Type;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapperProxyFactory {

    private String uri;

    /**
     * uri --> 代理对象
     */
    private final Map<String, IGenericReference> genericReferenceCache = new ConcurrentHashMap<>();

    public MapperProxyFactory(String uri) {
        this.uri = uri;
    }

    public IGenericReference newInstance(GatewaySession gatewaySession) {
        if(genericReferenceCache.containsKey(uri)) {
            return genericReferenceCache.get(uri);
        }else {
            final HttpStatement httpStatement = gatewaySession.getConfiguration().getHttpStatement(uri);
            final MapperProxy mapperProxy = new MapperProxy(gatewaySession, uri);
            final InterfaceMaker interfaceMaker = new InterfaceMaker();
            interfaceMaker.add(new Signature(httpStatement.getMethodName(), Type.getType(String.class), new Type[]{Type.getType(String.class)}), null);
            final Class interfaceClass = interfaceMaker.create();
            final Enhancer enhancer = new Enhancer();
            enhancer.setSuperclass(Object.class);
            enhancer.setInterfaces(new Class[]{IGenericReference.class, interfaceClass});
            enhancer.setCallback(mapperProxy);
            final IGenericReference instance = (IGenericReference) enhancer.create();
            genericReferenceCache.put(uri,instance);
            return instance;
        }
    }
}
