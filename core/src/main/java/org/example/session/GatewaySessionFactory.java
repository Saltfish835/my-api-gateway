package org.example.session;

public interface GatewaySessionFactory {

    GatewaySession openSession(String uri);
}
