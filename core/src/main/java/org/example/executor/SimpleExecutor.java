package org.example.executor;

import org.example.datasource.Connection;
import org.example.session.Configuration;

public class SimpleExecutor extends BaseExecutor{

    public SimpleExecutor(Configuration configuration, Connection connection) {
        super(configuration, connection);
    }

    @Override
    protected Object doExec(String methodName, String[] parameterTypes, Object[] args) {
        final Object data = connection.execute(methodName, parameterTypes, new String[]{"ignore"}, args);
        return data;
    }
}
