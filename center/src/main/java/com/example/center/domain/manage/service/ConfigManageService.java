package com.example.center.domain.manage.service;

import com.example.center.application.IConfigManageService;
import com.example.center.domain.manage.model.vo.GatewayServerDetailVO;
import com.example.center.domain.manage.model.vo.GatewayServerVO;
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
}
