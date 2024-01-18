package com.example.center.application;

import com.example.center.domain.model.ApiData;

import java.util.List;

public interface IApiService {

    List<ApiData> queryHttpStatement();
}
