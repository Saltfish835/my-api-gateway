package com.example.center.interfaces;

import com.example.center.application.IRegisterManageService;
import com.example.center.domain.register.model.vo.ApplicationInterfaceMethodVO;
import com.example.center.domain.register.model.vo.ApplicationInterfaceVO;
import com.example.center.domain.register.model.vo.ApplicationSystemVO;
import com.example.center.infrastructure.common.ResponseCode;
import com.example.center.infrastructure.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/wg/admin/register")
public class RpcRegisterManage {

    private Logger logger = LoggerFactory.getLogger(RpcRegisterManage.class);

    @Resource
    private IRegisterManageService registerManageService;

    @PostMapping(value = "registerApplication", produces = "application/json;charset=utf-8")
    public Result<Boolean> registerApplication(@RequestParam String systemId, @RequestParam String systemName,
                                               @RequestParam String systemType, @RequestParam String systemRegistry) {
        try {
            logger.info("注册应用服务 systemId: {}", systemId);
            final ApplicationSystemVO applicationSystemVO = new ApplicationSystemVO();
            applicationSystemVO.setSystemId(systemId);
            applicationSystemVO.setSystemName(systemName);
            applicationSystemVO.setSystemType(systemType);
            applicationSystemVO.setSystemRegistry(systemRegistry);
            registerManageService.registerApplication(applicationSystemVO);
            return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getInfo(), true);
        }catch (DuplicateKeyException e) {
            logger.warn("注册应用服务重复 systemId: {}", systemId, e);
            return new Result<>(ResponseCode.INDEX_DUP.getCode(), e.getMessage(), true);
        }catch (Exception e) {
            logger.error("注册应用服务失败 systemId: {}", systemId, e);
            return new Result<>(ResponseCode.UN_ERROR.getCode(), e.getMessage(), false);
        }
    }


    @PostMapping(value = "registerApplicationInterface", produces = "application/json;charset=utf-8")
    public Result<Boolean> registerApplicationInterface(@RequestParam String systemId, @RequestParam String interfaceId,
                                                        @RequestParam String interfaceName, @RequestParam String interfaceVersion) {
        try{
            logger.info("注册应用接口 systemId: {}, interfaceId: {}",systemId, interfaceId);
            final ApplicationInterfaceVO applicationInterfaceVO = new ApplicationInterfaceVO();
            applicationInterfaceVO.setSystemId(systemId);
            applicationInterfaceVO.setInterfaceId(interfaceId);
            applicationInterfaceVO.setInterfaceName(interfaceName);
            applicationInterfaceVO.setInterfaceVersion(interfaceVersion);
            registerManageService.registerApplicationInterface(applicationInterfaceVO);
            return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getInfo(), true);
        }catch (DuplicateKeyException e) {
            logger.warn("注册应用接口重复 systemId: {}, interfaceId: {}", systemId, interfaceId);
            return new Result<>(ResponseCode.INDEX_DUP.getCode(), e.getMessage(), true);
        }catch (Exception e) {
            logger.error("注册应用接口失败 systemId: {}", systemId, e);
            return new Result<>(ResponseCode.UN_ERROR.getCode(),e.getMessage(), false);
        }
    }


    @PostMapping(value = "registerApplicationInterfaceMethod", produces = "application/json;charset=utf-8")
    public Result<Boolean> registerApplicationInterfaceMethod(@RequestParam String systemId, @RequestParam String interfaceId,
                                                              @RequestParam String methodId, @RequestParam String methodName,
                                                              @RequestParam String parameterType, @RequestParam String uri,
                                                              @RequestParam String httpCommandType, @RequestParam Integer auth) {
        try {
            logger.info("注册应用接口方法 systemId: {}, interfaceId: {}, methodId: {}", systemId, interfaceId, methodId);
            final ApplicationInterfaceMethodVO applicationInterfaceMethodVO = new ApplicationInterfaceMethodVO();
            applicationInterfaceMethodVO.setSystemId(systemId);
            applicationInterfaceMethodVO.setInterfaceId(interfaceId);
            applicationInterfaceMethodVO.setMethodId(methodId);
            applicationInterfaceMethodVO.setMethodName(methodName);
            applicationInterfaceMethodVO.setParameterType(parameterType);
            applicationInterfaceMethodVO.setUri(uri);
            applicationInterfaceMethodVO.setHttpCommandType(httpCommandType);
            applicationInterfaceMethodVO.setAuth(auth);
            registerManageService.registerApplicationInterfaceMethod(applicationInterfaceMethodVO);
            return new Result<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getInfo(), true);
        }catch (DuplicateKeyException e) {
            logger.warn("注册应用接口方法重复 systemId: {}, interfaceId: {}", systemId, interfaceId);
            return new Result<>(ResponseCode.INDEX_DUP.getCode(), e.getMessage(), true);
        }catch (Exception e) {
            logger.error("注册应用接口方法失败 systemId: {}", systemId, e);
            return new Result<>(ResponseCode.UN_ERROR.getCode(), e.getMessage(), false);
        }
    }

}
