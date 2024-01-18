package com.example.center.domain.service;

import com.example.center.application.IApiService;
import com.example.center.domain.model.ApiData;
import com.example.center.domain.repository.IApiRepository;

import javax.annotation.Resource;
import java.util.List;

public class ApiServiceImpl implements IApiService {

    @Resource
    private IApiRepository apiRepository;

    @Override
    public List<ApiData> queryHttpStatement() {
        return apiRepository.queryHttpStatementList();
    }
}
