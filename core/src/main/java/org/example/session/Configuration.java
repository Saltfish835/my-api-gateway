package org.example.session;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;
import org.example.mapping.HttpStatement;

import java.util.HashMap;
import java.util.Map;

public class Configuration {

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
}
