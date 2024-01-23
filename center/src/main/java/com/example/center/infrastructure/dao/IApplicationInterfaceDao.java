package com.example.center.infrastructure.dao;

import com.example.center.infrastructure.po.ApplicationInterface;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IApplicationInterfaceDao {

    void insert(ApplicationInterface applicationInterface);

}
