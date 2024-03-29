package org.example.gateway.core.executor;

import org.example.gateway.core.executor.result.SessionResult;
import org.example.gateway.core.mapping.HttpStatement;

import java.util.Map;

public interface Executor {

    SessionResult exec(HttpStatement httpStatement, Map<String, Object> params) throws Exception;
}
