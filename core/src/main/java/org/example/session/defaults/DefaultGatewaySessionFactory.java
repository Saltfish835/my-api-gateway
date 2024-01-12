package org.example.session.defaults;

import org.example.datasource.DataSource;
import org.example.datasource.unpooled.UnpooledDataSourceFactory;
import org.example.executor.Executor;
import org.example.session.Configuration;
import org.example.session.GatewaySession;
import org.example.session.GatewaySessionFactory;

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
