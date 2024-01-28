package com.example.gateway.center.application;

import com.example.gateway.center.domain.register.model.vo.ApplicationInterfaceMethodVO;
import com.example.gateway.center.domain.register.model.vo.ApplicationInterfaceVO;
import com.example.gateway.center.domain.register.model.vo.ApplicationSystemVO;

public interface IRegisterManageService {

    void registerApplication(ApplicationSystemVO applicationSystemVO);

    void registerApplicationInterface(ApplicationInterfaceVO applicationInterfaceVO);

    void registerApplicationInterfaceMethod(ApplicationInterfaceMethodVO applicationInterfaceMethodVO);
}
