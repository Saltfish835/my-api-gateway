package org.example.core.session.defaults;

import org.example.core.datasource.DataSource;
import org.example.core.datasource.unpooled.UnpooledDataSourceFactory;
import org.example.core.executor.Executor;
import org.example.core.session.Configuration;
import org.example.core.session.GatewaySession;
import org.example.core.session.GatewaySessionFactory;

public class DefaultGatewaySessionFactory implements GatewaySessionFactory {

    private final Configuration configuration;

    public DefaultGatewaySessionFactory(Configuration configuration) {
        this.configuration = configuration;
    }


    @Override
    public GatewaySession openSession(String uri) {
        final UnpooledDataSourceFactory dataSourceFactory = new UnpooledDataSourceFactory();
        dataSourceFactory.setProperties(configuration, uri);
        final DataSource dataSource = dataSourceFactory.getDataSource();
        // 创建执行器
        final Executor executor = configuration.newExecutor(dataSource.getConnection());
        return new DefaultGatewaySession(configuration, uri, executor);
    }
}
