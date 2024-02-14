package com.example.gateway.center.infrastructure.dao;

import com.example.gateway.center.domain.operation.model.vo.ApplicationInterfaceMethodDataVO;
import com.example.gateway.center.infrastructure.common.OperationRequest;
import com.example.gateway.center.infrastructure.po.ApplicationInterfaceMethod;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IApplicationInterfaceMethodDao {

    void insert(ApplicationInterfaceMethod applicationInterfaceMethod);

    List<ApplicationInterfaceMethod> queryApplicationInterfaceMethodList(ApplicationInterfaceMethod applicationInterfaceMethod);

    List<ApplicationInterfaceMethod> queryApplicationInterfaceMethodListByPage(OperationRequest<ApplicationInterfaceMethodDataVO> request);

    int queryApplicationInterfaceMethodListCountByPage(OperationRequest<ApplicationInterfaceMethodDataVO> request);
}
