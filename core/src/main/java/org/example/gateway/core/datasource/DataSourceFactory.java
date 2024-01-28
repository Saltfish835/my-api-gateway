package org.example.gateway.core.datasource;

import org.example.gateway.core.session.Configuration;

public interface DataSourceFactory {

    void setProperties(Configuration configuration, String uri);

    DataSource getDataSource();
}
