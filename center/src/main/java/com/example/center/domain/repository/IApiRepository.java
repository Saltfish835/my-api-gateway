package com.example.center.domain.repository;

import com.example.center.domain.model.ApiData;

import java.util.List;

public interface IApiRepository {

    List<ApiData> queryHttpStatementList();
}
