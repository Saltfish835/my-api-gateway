package org.example.core.datasource;

import org.example.core.session.Configuration;

public interface DataSourceFactory {

    void setProperties(Configuration configuration, String uri);

    DataSource getDataSource();
}
