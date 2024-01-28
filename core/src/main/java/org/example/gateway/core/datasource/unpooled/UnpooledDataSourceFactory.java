package org.example.gateway.core.datasource.unpooled;

import org.example.gateway.core.datasource.DataSource;
import org.example.gateway.core.datasource.DataSourceFactory;
import org.example.gateway.core.datasource.DataSourceType;
import org.example.gateway.core.session.Configuration;

public class UnpooledDataSourceFactory implements DataSourceFactory {

    protected UnpooledDataSource dataSource;

    public UnpooledDataSourceFactory() {
        dataSource = new UnpooledDataSource();
    }

    @Override
    public void setProperties(Configuration configuration, String uri) {
        dataSource.setConfiguration(configuration);
        dataSource.setDataSourceType(DataSourceType.Dubbo);
        dataSource.setHttpStatement(configuration.getHttpStatement(uri));
    }

    @Override
    public DataSource getDataSource() {
        return dataSource;
    }
}
