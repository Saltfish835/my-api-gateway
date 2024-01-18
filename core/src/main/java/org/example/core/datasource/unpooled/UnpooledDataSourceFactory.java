package org.example.core.datasource.unpooled;

import org.example.core.datasource.DataSource;
import org.example.core.datasource.DataSourceFactory;
import org.example.core.datasource.DataSourceType;
import org.example.core.session.Configuration;

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
