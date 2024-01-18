package com.example.center.interfaces;

import com.example.center.application.IApiService;
import com.example.center.domain.model.ApiData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.List;

public class ApiGatewayController {

    private Logger logger = LoggerFactory.getLogger(ApiGatewayController.class);

    @Resource
    private IApiService apiService;

    @GetMapping(value = "list", produces = "application/json;charset=utf-8")
    public List<ApiData> getAnswerMap() {
        return apiService.queryHttpStatement();
    }
}
