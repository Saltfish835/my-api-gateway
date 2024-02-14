package com.example.gateway.center.domain.manage.service;

import com.example.gateway.center.application.IConfigManageService;
import com.example.gateway.center.domain.manage.model.aggregates.ApplicationSystemRichInfo;
import com.example.gateway.center.domain.manage.model.vo.*;
import com.example.gateway.center.domain.manage.repository.IConfigManageRepository;
import com.example.gateway.center.infrastructure.common.Constants;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConfigManageService implements IConfigManageService {

    @Resource
    private IConfigManageRepository configManageRepository;

    @Override
    public List<GatewayServerVO> queryGatewayServerList() {
        return configManageRepository.queryGatewayServerList();
    }

    @Override
    public List<GatewayServerDetailVO> queryGatewayServerDetailList() {
        return configManageRepository.queryGatewayServerDetailList();
    }

    @Override
    public List<GatewayDistributionVO> queryGatewayDistributionList() {
        return configManageRepository.queryGatewayDistributionList();
    }

    @Override
    public boolean registerGatewayServerNode(String groupId, String gatewayId, String gatewayName, String gatewayAddress) {
        final GatewayServerDetailVO detailVO = configManageRepository.queryGatewayServerDetail(gatewayId, gatewayAddress);
        if(detailVO == null) {
            try {
                return configManageRepository.registerGatewayServerNode(groupId, gatewayId,gatewayName,gatewayAddress, Constants.GatewayStatus.Available);
            }catch (DuplicateKeyException e) {
                return configManageRepository.updateGatewayStatus(gatewayId,gatewayAddress, Constants.GatewayStatus.Available);
            }
        }else {
            return configManageRepository.updateGatewayStatus(gatewayId,gatewayAddress,Constants.GatewayStatus.Available);
        }
    }

    @Override
    public ApplicationSystemRichInfo queryApplicationSystemRichInfo(String gatewayId, String systemId) {
        List<String> systemIdList = new ArrayList<>();
        if(systemId == null || "".equals(systemId)) {
            systemIdList = configManageRepository.queryGatewayDistributionSystemIdList(gatewayId);
        }else {
            systemIdList.add(systemId);
        }
        final List<ApplicationSystemVO> applicationSystemVOList = configManageRepository.queryApplicationSystemList(systemIdList);
        for(ApplicationSystemVO applicationSystemVO : applicationSystemVOList) {
            final List<ApplicationInterfaceVO> applicationInterfaceVOList = configManageRepository.queryApplicationInterfaceLis(applicationSystemVO.getSystemId());
            for(ApplicationInterfaceVO applicationInterfaceVO : applicationInterfaceVOList) {
                final List<ApplicationInterfaceMethodVO> applicationInterfaceMethodVOList = configManageRepository.queryApplicationInterfaceMethodList(
                        applicationSystemVO.getSystemId(), applicationInterfaceVO.getInterfaceId());
                applicationInterfaceVO.setMethodVOList(applicationInterfaceMethodVOList);
            }
            applicationSystemVO.setInterfaceVOList(applicationInterfaceVOList);
        }
        return new ApplicationSystemRichInfo(gatewayId, applicationSystemVOList);
    }

    @Override
    public String queryGatewayDistribution(String systemId) {
        return configManageRepository.queryGatewayDistribution(systemId);
    }

    @Override
    public List<ApplicationSystemVO> queryApplicationSystemList() {
        return configManageRepository.queryApplicationSystemList(null);
    }

    @Override
    public List<ApplicationInterfaceVO> queryApplicationInterfaceList() {
        return configManageRepository.queryApplicationInterfaceLis(null);
    }

    @Override
    public List<ApplicationInterfaceMethodVO> queryApplicationInterfaceMethodList() {
        return configManageRepository.queryApplicationInterfaceMethodList(null, null);
    }
}
