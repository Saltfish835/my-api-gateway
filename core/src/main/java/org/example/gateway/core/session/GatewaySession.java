package org.example.gateway.core.session;

import org.example.gateway.core.bind.IGenericReference;

import java.util.Map;

public interface GatewaySession {

    Object get(String methodName, Map<String, Object> params);

    Object post(String methodName, Map<String, Object> params);

    Configuration getConfiguration();

    IGenericReference getMapper();

}
