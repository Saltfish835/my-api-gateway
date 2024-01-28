package org.example.gateway.core.authorization;

public interface IAuth {

    boolean validate(String id, String token);
}
