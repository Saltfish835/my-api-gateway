package org.example.mapping;


/**
 * uri --> rpc
 */
public class HttpStatement {

    /**
     * 网关接口
     */
    private String uri;
    private HttpCommandType httpCommandType;

    /**
     * rpc接口
     */
    private String application;
    private String interfaceName;
    private String methodName;
    private String parameterType;

    /**
     * 接口是否需要授权
     */
    private boolean auth;


    public HttpStatement(String uri, HttpCommandType httpCommandType, String application, String interfaceName, String methodName, String parameterType, boolean auth) {
        this.uri = uri;
        this.httpCommandType = httpCommandType;
        this.application = application;
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.parameterType = parameterType;
        this.auth = auth;
    }

    public String getUri() {
        return uri;
    }

    public HttpCommandType getHttpCommandType() {
        return httpCommandType;
    }

    public String getApplication() {
        return application;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getParameterType() {
        return parameterType;
    }

    public boolean isAuth() {
        return auth;
    }
}
