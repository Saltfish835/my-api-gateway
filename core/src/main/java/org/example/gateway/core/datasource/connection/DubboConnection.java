package org.example.gateway.core.datasource.connection;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.config.bootstrap.DubboBootstrap;
import org.apache.dubbo.config.utils.ReferenceConfigCache;
import org.apache.dubbo.rpc.service.GenericService;
import org.example.gateway.core.datasource.Connection;

public class DubboConnection implements Connection {


    private final GenericService genericService;


    public DubboConnection(ApplicationConfig applicationConfig, RegistryConfig registryConfig, ReferenceConfig<GenericService> referenceConfig) {
        // 连接远程服务
        final DubboBootstrap bootstrap = DubboBootstrap.getInstance();
        bootstrap.application(applicationConfig).registry(registryConfig).reference(referenceConfig).start();
        // 获取泛化调用接口
        final ReferenceConfigCache cache = ReferenceConfigCache.getCache();
        this.genericService = cache.get(referenceConfig);
    }


    @Override
    public Object execute(String method, String[] parameterTypes, String[] parameterNames, Object[] args) {
        // 执行泛化调用
        final Object result = genericService.$invoke(method, parameterTypes, args);
        return result;
    }
}
