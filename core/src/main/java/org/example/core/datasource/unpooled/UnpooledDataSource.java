package org.example.core.datasource.unpooled;

import org.apache.dubbo.config.ApplicationConfig;
import org.apache.dubbo.config.ReferenceConfig;
import org.apache.dubbo.config.RegistryConfig;
import org.apache.dubbo.rpc.service.GenericService;
import org.example.core.datasource.Connection;
import org.example.core.datasource.DataSource;
import org.example.core.datasource.DataSourceType;
import org.example.core.datasource.connection.DubboConnection;
import org.example.core.mapping.HttpStatement;
import org.example.core.session.Configuration;

public class UnpooledDataSource implements DataSource {

    private Configuration configuration;
    private HttpStatement httpStatement;
    private DataSourceType dataSourceType;

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public void setHttpStatement(HttpStatement httpStatement) {
        this.httpStatement = httpStatement;
    }

    public void setDataSourceType(DataSourceType dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    @Override
    public Connection getConnection() {
        switch (dataSourceType) {
            case HTTP:
                break;
            case Dubbo:
                // 获取配置
                final String application = httpStatement.getApplication();
                final String interfaceName = httpStatement.getInterfaceName();
                // 获取服务
                final ApplicationConfig applicationConfig = configuration.getApplicationConfig(application);
                final RegistryConfig registryConfig = configuration.getRegistryConfig(application);
                final ReferenceConfig<GenericService> referenceConfig = configuration.getReferenceConfig(interfaceName);
                return new DubboConnection(applicationConfig, registryConfig, referenceConfig);
            default:
                break;
        }
        throw new RuntimeException("DataSourceType: "+ dataSourceType + "没有对应的数据源实现");
    }
}
