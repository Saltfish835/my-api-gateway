package com.example.center.infrastructure.repository;

import com.example.center.domain.model.ApiData;
import com.example.center.domain.repository.IApiRepository;
import com.example.center.infrastructure.dao.IHttpStatementDao;
import com.example.center.infrastructure.po.HttpStatement;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class ApiRepository implements IApiRepository {

    @Resource
    private IHttpStatementDao httpStatementDao;

    @Override
    public List<ApiData> queryHttpStatementList() {
        final List<HttpStatement> httpStatements = httpStatementDao.queryHttpStatementList();
        final ArrayList<ApiData> apiDataList = new ArrayList<>(httpStatements.size());
        for(HttpStatement httpStatement: httpStatements) {
            final ApiData apiData = new ApiData();
            apiData.setApplication(httpStatement.getApplication());
            apiData.setInterfaceName(httpStatement.getInterfaceName());
            apiData.setMethodName(httpStatement.getMethodName());
            apiData.setParameterType(httpStatement.getParameterType());
            apiData.setUri(httpStatement.getUri());
            apiData.setHttpCommandType(httpStatement.getHttpCommandType());
            apiData.setAuth(httpStatement.getAuth());
            apiDataList.add(apiData);
        }
        return apiDataList;
    }
}
