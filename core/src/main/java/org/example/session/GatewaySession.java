package org.example.session;

import org.example.bind.IGenericReference;

import java.util.Map;

public interface GatewaySession {

    Object get(String methodName, Map<String, Object> params);

    Object post(String methodName, Map<String, Object> params);

    Configuration getConfiguration();

    IGenericReference getMapper();

}
