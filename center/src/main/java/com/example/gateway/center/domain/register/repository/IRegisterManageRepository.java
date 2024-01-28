package com.example.gateway.center.domain.register.repository;

import com.example.gateway.center.domain.register.model.vo.ApplicationInterfaceMethodVO;
import com.example.gateway.center.domain.register.model.vo.ApplicationInterfaceVO;
import com.example.gateway.center.domain.register.model.vo.ApplicationSystemVO;

public interface IRegisterManageRepository {

    void registerApplication(ApplicationSystemVO applicationSystemVO);

    void registerApplicationInterface(ApplicationInterfaceVO applicationInterfaceVO);

    void registerApplicationInterfaceMethod(ApplicationInterfaceMethodVO applicationInterfaceMethodVO);;;
}
