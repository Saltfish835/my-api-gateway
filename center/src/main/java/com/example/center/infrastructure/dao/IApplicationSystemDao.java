package com.example.center.infrastructure.dao;

import com.example.center.infrastructure.po.ApplicationSystem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IApplicationSystemDao {

    void insert(ApplicationSystem applicationSystem);

}
