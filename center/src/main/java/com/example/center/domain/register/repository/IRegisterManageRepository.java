package com.example.center.domain.register.repository;

import com.example.center.domain.register.model.vo.ApplicationInterfaceMethodVO;
import com.example.center.domain.register.model.vo.ApplicationInterfaceVO;
import com.example.center.domain.register.model.vo.ApplicationSystemVO;

public interface IRegisterManageRepository {

    void registerApplication(ApplicationSystemVO applicationSystemVO);

    void registerApplicationInterface(ApplicationInterfaceVO applicationInterfaceVO);

    void registerApplicationInterfaceMethod(ApplicationInterfaceMethodVO applicationInterfaceMethodVO);;;
}
