package org.example.core.datasource;

public interface Connection {

    Object execute(String method, String[] parameterTypes, String[] parameterNames, Object[] args);
}
