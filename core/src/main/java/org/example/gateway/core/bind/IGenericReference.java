package org.example.gateway.core.bind;

import org.example.gateway.core.executor.result.SessionResult;

import java.util.Map;

public interface IGenericReference {

    SessionResult $invoke(Map<String, Object> params);
}
