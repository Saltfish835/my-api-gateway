package com.example.center.domain.manage.service;

import com.example.center.application.IConfigManageService;
import com.example.center.domain.manage.model.aggregates.ApplicationSystemRichInfo;
import com.example.center.domain.manage.model.vo.*;
import com.example.center.domain.manage.repository.IConfigManageRepository;
import com.example.center.infrastructure.common.Constants;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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
    public ApplicationSystemRichInfo queryApplicationSystemRichInfo(String gatewayId) {
        final List<String> systemIdList = configManageRepository.queryGatewayDistributionSystemIdList(gatewayId);
        if(systemIdList == null || systemIdList.isEmpty()) {
            return null;
        }
        final List<ApplicationSystemVO> applicationSystemVOList = configManageRepository.queryApplicationSystemList(systemIdList);
        for(ApplicationSystemVO applicationSystemVO : applicationSystemVOList) {
            final List<ApplicationInterfaceVO> applicationInterfaceVOList = configManageRepository.queryApplicationInterfaceLis(applicationSystemVO.getSystemId());
            for(ApplicationInterfaceVO applicationInterfaceVO : applicationInterfaceVOList) {
                final List<ApplicationInterfaceMethodVO> applicationInterfaceMethodVOList = configManageRepository.queryApplicationInterfaceMethodList(applicationInterfaceVO.getSystemId(), applicationInterfaceVO.getInterfaceId());
                applicationInterfaceVO.setMethodList(applicationInterfaceMethodVOList);
            }
            applicationSystemVO.setInterfaceList(applicationSystemVOList);
        }
        return new ApplicationSystemRichInfo(gatewayId, applicationSystemVOList);
    }
}
