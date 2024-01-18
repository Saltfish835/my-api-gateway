package com.example.center.infrastructure.dao;

import com.example.center.infrastructure.po.HttpStatement;

import java.util.List;

public interface IHttpStatementDao {

    List<HttpStatement> queryHttpStatementList();
}
