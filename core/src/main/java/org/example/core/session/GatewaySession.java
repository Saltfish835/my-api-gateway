package org.example.core.session;

import org.example.core.bind.IGenericReference;

import java.util.Map;

public interface GatewaySession {

    Object get(String methodName, Map<String, Object> params);

    Object post(String methodName, Map<String, Object> params);

    Configuration getConfiguration();

    IGenericReference getMapper();

}
