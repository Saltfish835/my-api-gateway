package org.example.authorization;

public interface IAuth {

    boolean validate(String id, String token);
}
