package com.example.gateway.sdk.domain.service;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.example.gateway.sdk.GatewayException;
import com.example.gateway.sdk.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class GatewayCenterService {

    private Logger logger = LoggerFactory.getLogger(GatewayCenterService.class);

    private int timeout;
    private int interval;
    private int retry;

    public GatewayCenterService(int timeout, int interval, int retry) {
        this.timeout = timeout;
        this.interval = interval;
        this.retry = retry;
    }

    public void doRegisterApplication(String address, String systemId, String systemName, String systemType, String systemRegistry) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("systemId", systemId);
        paramMap.put("systemName", systemName);
        paramMap.put("systemType", systemType);
        paramMap.put("systemRegistry", systemRegistry);
        String resultStr = null;
        int count = 0;
        while(count <= retry) {
            try{
                resultStr = HttpUtil.post(address + "/wg/admin/register/registerApplication", paramMap, timeout);
                break;
            }catch (Exception e) {
                count++;
                logger.error("应用服务注册异常，连接资源不可用:{}, 重试{}次", address + "/wg/admin/register/registerApplication", count);
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException interruptedException) {
                    logger.error(interruptedException.getMessage());
                }
            }
        }
        if(resultStr == null) {
            throw new GatewayException("注册应用服务异常 [systemId：" + systemId + "] 、[systemRegistry：" + systemRegistry + "]");
        }
        final Result<Boolean> result = JSON.parseObject(resultStr, new TypeReference<Result<Boolean>>() {
        });
        logger.info("向网关中心注册应用服务 systemId：{} systemName：{} 注册结果：{}", systemId, systemName, resultStr);
        if(!"0000".equals(result.getCode()) && !"0003".equals(result.getCode())) {
            throw new GatewayException("注册应用服务异常 [systemId：" + systemId + "] 、[systemRegistry：" + systemRegistry + "]");
        }
    }


    public void doRegisterApplicationInterface(String address, String systemId, String interfaceId, String interfaceName, String interfaceVersion) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("systemId", systemId);
        paramMap.put("interfaceId", interfaceId);
        paramMap.put("interfaceName", interfaceName);
        paramMap.put("interfaceVersion", interfaceVersion);
        String resultStr = null;
        int count = 0;
        while(count <= retry) {
            try {
                resultStr = HttpUtil.post(address + "/wg/admin/register/registerApplicationInterface", paramMap, timeout);
                break;
            }catch (Exception e) {
                count++;
                logger.error("应用服务接口注册异常，链接资源不可用：{}, 重试{}次", address + "/wg/admin/register/registerApplicationInterface", count);
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException interruptedException) {
                    logger.error(interruptedException.getMessage());
                }
            }
        }
        if(resultStr == null) {
            throw new GatewayException("向网关中心注册应用接口服务异常 [systemId：" + systemId + "] 、[interfaceId：" + interfaceId + "]");
        }
        final Result<Boolean> result = JSON.parseObject(resultStr, new TypeReference<Result<Boolean>>() {
        });
        logger.info("向网关中心注册应用接口服务 systemId：{} interfaceId：{} interfaceName：{} 注册结果：{}", systemId, interfaceId, interfaceName, resultStr);
        if (!"0000".equals(result.getCode()) && !"0003".equals(result.getCode())) {
            throw new GatewayException("向网关中心注册应用接口服务异常 [systemId：" + systemId + "] 、[interfaceId：" + interfaceId + "]");
        }
    }


    public void doRegisterApplicationInterfaceMethod(String address, String systemId, String interfaceId, String methodId,
                                                     String methodName, String parameterType, String uri, String httpCommandType, Integer auth) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("systemId", systemId);
        paramMap.put("interfaceId", interfaceId);
        paramMap.put("methodId", methodId);
        paramMap.put("methodName", methodName);
        paramMap.put("parameterType", parameterType);
        paramMap.put("uri", uri);
        paramMap.put("httpCommandType", httpCommandType);
        paramMap.put("auth", auth);
        String resultStr = null;
        int count = 0;
        while (count <= retry) {
            try{
                resultStr = HttpUtil.post(address + "/wg/admin/register/registerApplicationInterfaceMethod", paramMap, timeout);
                break;
            }catch (Exception e) {
                count++;
                logger.error("应用服务接口注册方法异常，链接资源不可用：{}, 重试{}次", address + "/wg/admin/register/registerApplicationInterfaceMethod", count);
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException interruptedException) {
                    logger.error(interruptedException.getMessage());
                }
            }
        }
        if(resultStr == null) {
            throw new GatewayException("向网关中心注册应用接口方法服务异常 [systemId：" + systemId + "] 、[interfaceId：" + interfaceId + "]、[methodId：]" + methodId + "]");
        }
        final Result<Boolean> result = JSON.parseObject(resultStr, new TypeReference<Result<Boolean>>() {
        });
        logger.info("向网关中心注册应用接口方法服务 systemId：{} interfaceId：{} methodId：{} 注册结果：{}", systemId, interfaceId, methodId, resultStr);
        if (!"0000".equals(result.getCode()) && !"0003".equals(result.getCode()))
            throw new GatewayException("向网关中心注册应用接口方法服务异常 [systemId：" + systemId + "] 、[interfaceId：" + interfaceId + "]、[methodId：]" + methodId + "]");
    }


    public void doRegisterEvent(String address, String systemId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("systemId", systemId);
        String resultStr = null;
        int count = 0;
        while(count <= retry) {
            try {
                resultStr = HttpUtil.post(address + "/wg/admin/register/registerEvent", paramMap, timeout);
                break;
            }catch (Exception e) {
                count++;
                logger.error("应用服务接口事件方法异常，链接资源不可用：{}, 重试{}次", address + "/wg/admin/register/registerEvent", count);
                try {
                    Thread.sleep(interval);
                } catch (InterruptedException interruptedException) {
                    logger.error(interruptedException.getMessage());
                }
            }
        }
        if(resultStr == null) {
            throw new GatewayException("向网关中心注册应用接口服务异常 [systemId：" + systemId + "] ");
        }
        Result<Boolean> result = JSON.parseObject(resultStr, new TypeReference<Result<Boolean>>() {
        });
        logger.info("应用服务接口事件方法 systemId：{} 注册结果：{}", systemId, resultStr);
        if (!"0000".equals(result.getCode()))
            throw new GatewayException("向网关中心注册应用接口服务异常 [systemId：" + systemId + "] ");
    }

}
