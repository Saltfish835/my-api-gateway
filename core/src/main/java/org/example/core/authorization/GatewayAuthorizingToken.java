package org.example.core.authorization;

import org.apache.shiro.authc.AuthenticationToken;

public class GatewayAuthorizingToken implements AuthenticationToken {


    private static final long serialVersionUID = 1L;

    /**
     * 用户名
     */
    private String uid;

    /**
     * 密码
     */
    private String jwt;


    public GatewayAuthorizingToken() {
    }

    public GatewayAuthorizingToken(String uid, String jwt) {
        this.uid = uid;
        this.jwt = jwt;
    }

    /**
     * 返回用户名
     * @return
     */
    @Override
    public Object getPrincipal() {
        return this.uid;
    }


    /**
     * 返回密码
     * @return
     */
    @Override
    public Object getCredentials() {
        return this.jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }
}
