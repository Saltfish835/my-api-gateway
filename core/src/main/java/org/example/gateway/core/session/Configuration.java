package org.example.gateway.core.session;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;
import org.example.gateway.core.authorization.IAuth;
import org.example.gateway.core.authorization.service.AuthService;
import org.example.gateway.core.bind.IGenericReference;
import org.example.gateway.core.bind.MapperRegistry;
import org.example.gateway.core.datasource.Connection;
import org.example.gateway.core.executor.Executor;
import org.example.gateway.core.executor.SimpleExecutor;
import org.example.gateway.core.mapping.HttpStatement;

import java.util.HashMap;
import java.util.Map;

public class Configuration {

    /**
     * netty配置
     */
    private String hostName = "127.0.0.1";
    private int port = 7739;
    private int bossNThreads = 1;
    private int workNThreads = 4;


    /**
     * 泛化服务配置
     */
    private final Map<String, ApplicationConfig> applicationConfigMap = new HashMap<>();
    private final Map<String, RegistryConfig> registryConfigMap = new HashMap<>();
    private final Map<String, ReferenceConfig<GenericService>> referenceConfigMap = new HashMap<>();

    /**
     * uri --> httpStatement
     */
    private final Map<String, HttpStatement> httpStatementMap = new HashMap<>();

    /**
     * 注册uri --> mapper
     */
    private final MapperRegistry mapperRegistry = new MapperRegistry(this);

    /**
     * 授权服务
     */
    private final IAuth auth = new AuthService();

    /**
     * 注册泛化调用所需要的配置
     * @param applicationName
     * @param address
     * @param interfaceName
     * @param version
     */
    public synchronized void registryConfig(String applicationName, String address, String interfaceName, String version) {
        if(applicationConfigMap.get(applicationName) == null) {
            final ApplicationConfig applicationConfig = new ApplicationConfig();
            applicationConfig.setName(applicationName);
            applicationConfig.setQosEnable(false);
            applicationConfigMap.put(applicationName, applicationConfig);
        }

        if(registryConfigMap.get(applicationName) == null) {
            final RegistryConfig registryConfig = new RegistryConfig();
            registryConfig.setAddress(address);
            registryConfig.setRegister(false);
            registryConfigMap.put(applicationName, registryConfig);
        }

        if(registryConfigMap.get(interfaceName) == null) {
            final ReferenceConfig<GenericService> referenceConfig = new ReferenceConfig<>();
            referenceConfig.setInterface(interfaceName);
            referenceConfig.setVersion(version);
            referenceConfig.setGeneric("true");
            referenceConfigMap.put(interfaceName, referenceConfig);
        }
    }

    public ApplicationConfig getApplicationConfig(String applicationName) {
        return applicationConfigMap.get(applicationName);
    }

    public RegistryConfig getRegistryConfig(String applicationName) {
        return registryConfigMap.get(applicationName);
    }

    public ReferenceConfig<GenericService> getReferenceConfig(String interfaceName) {
        return referenceConfigMap.get(interfaceName);
    }

    public void addHttpStatement(HttpStatement httpStatement) {
        httpStatementMap.put(httpStatement.getUri(), httpStatement);
    }

    public HttpStatement getHttpStatement(String uri) {
        return httpStatementMap.get(uri);
    }

    public Executor newExecutor(Connection connection) {
        return new SimpleExecutor(this, connection);
    }

    public void addMapper(HttpStatement httpStatement) {
        mapperRegistry.addMapper(httpStatement);
    }

    public IGenericReference getMapper(String uri, GatewaySession gatewaySession) {
        return mapperRegistry.getMapper(uri, gatewaySession);
    }

    public boolean authValidate(String uId, String token) {
        return auth.validate(uId,token);
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getBossNThreads() {
        return bossNThreads;
    }

    public void setBossNThreads(int bossNThreads) {
        this.bossNThreads = bossNThreads;
    }

    public int getWorkNThreads() {
        return workNThreads;
    }

    public void setWorkNThreads(int workNThreads) {
        this.workNThreads = workNThreads;
    }
}
