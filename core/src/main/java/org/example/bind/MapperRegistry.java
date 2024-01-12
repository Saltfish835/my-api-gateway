package org.example.bind;

import org.example.mapping.HttpStatement;
import org.example.session.Configuration;
import org.example.session.GatewaySession;

import java.util.HashMap;
import java.util.Map;

public class MapperRegistry {

    private final Configuration configuration;

    private final Map<String, MapperProxyFactory> knownMapperMap = new HashMap<>();

    public MapperRegistry(Configuration configuration) {
        this.configuration = configuration;
    }

    public IGenericReference getMapper(String uri, GatewaySession gatewaySession) {
        final MapperProxyFactory mapperProxyFactory = knownMapperMap.get(uri);
        if(mapperProxyFactory == null) {
            throw new RuntimeException("Uri "+ uri + "is not known to the MapperRegistry.");
        }
        try{
            return mapperProxyFactory.newInstance(gatewaySession);
        }catch (Exception e) {
            throw new RuntimeException("Error getting mapper instance. Cause: "+e, e);
        }
    }


    public boolean hashMapper(String uri) {
        return knownMapperMap.containsKey(uri);
    }

    public void addMapper(HttpStatement httpStatement) {
        final String uri = httpStatement.getUri();
        if(hashMapper(uri)) {
            return;
        }
        knownMapperMap.put(uri, new MapperProxyFactory(uri));
        configuration.addHttpStatement(httpStatement);
    }

}
