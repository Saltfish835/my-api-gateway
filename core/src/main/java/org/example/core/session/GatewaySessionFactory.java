package org.example.core.session;

public interface GatewaySessionFactory {

    GatewaySession openSession(String uri);
}
