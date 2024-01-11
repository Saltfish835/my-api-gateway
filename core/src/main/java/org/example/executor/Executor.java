package org.example.executor;

import org.example.executor.result.SessionResult;
import org.example.mapping.HttpStatement;

import java.util.Map;

public interface Executor {

    SessionResult exec(HttpStatement httpStatement, Map<String, Object> params) throws Exception;
}
