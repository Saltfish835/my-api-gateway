package org.example.gateway.core.session;

public interface GatewaySessionFactory {

    GatewaySession openSession(String uri);
}
