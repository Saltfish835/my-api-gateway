package org.example.core.authorization.service;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.example.core.authorization.GatewayAuthorizingToken;
import org.example.core.authorization.IAuth;

public class AuthService implements IAuth {

    private Subject subject;

    /**
     * 初始化subject
     */
    public AuthService() {
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        final SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        this.subject = SecurityUtils.getSubject();
    }


    /**
     * 身份验证
     * @param id
     * @param token
     * @return
     */
    @Override
    public boolean validate(String id, String token) {
        try{
            subject.login(new GatewayAuthorizingToken(id,token));
            return subject.isAuthenticated();
        }finally {
            subject.logout();
        }
    }
}
