package org.example.gateway.core.authorization;

import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

public class GatewayAuthorizingRealm extends AuthorizingRealm {


    @Override
    public Class getAuthenticationTokenClass() {
        return GatewayAuthorizingToken.class;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        try{
            final String jwtToken = ((GatewayAuthorizingToken) authenticationToken).getJwt();
            final Claims claims = JwtUtil.decode(jwtToken);
            // 当前用户是否是token中记录的用户
            if(!authenticationToken.getPrincipal().equals(claims.getSubject())) {
                throw new AuthenticationException("无效令牌");
            }
        }catch (Exception e) {
            // 当前jwt token解码失败
            throw new AuthenticationException("无效令牌");
        }
        return new SimpleAuthenticationInfo(authenticationToken.getPrincipal(), authenticationToken.getCredentials(), this.getName());
    }
}
