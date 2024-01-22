package com.example.center.infrastructure.repository;

import com.example.center.domain.manage.model.ApiData;
import com.example.center.domain.manage.model.vo.GatewayServerDetailVO;
import com.example.center.domain.manage.model.vo.GatewayServerVO;
import com.example.center.domain.manage.repository.IConfigManageRepository;
import com.example.center.infrastructure.dao.IGatewayServerDao;
import com.example.center.infrastructure.dao.IGatewayServerDetailDao;
import com.example.center.infrastructure.po.GatewayServer;
import com.example.center.infrastructure.po.GatewayServerDetail;
import org.springframework.stereotype.Component;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Component
public class ConfigManageRepository implements IConfigManageRepository {

    @Resource
    private IGatewayServerDao gatewayServerDao;

    @Resource
    private IGatewayServerDetailDao gatewayServerDetailDao;


    @Override
    public List<GatewayServerVO> queryGatewayServerList() {
        final List<GatewayServer> gatewayServers = gatewayServerDao.queryGatewayServerList();
        final ArrayList<GatewayServerVO> gatewayServerVOList = new ArrayList<>(gatewayServers.size());
        for(GatewayServer gatewayServer:gatewayServers) {
            final GatewayServerVO gatewayServerVO = new GatewayServerVO();
            gatewayServerVO.setGroupId(gatewayServer.getGroupId());
            gatewayServerVO.setGroupName(gatewayServer.getGroupName());
            gatewayServerVOList.add(gatewayServerVO);
        }
        return gatewayServerVOList;
    }

    @Override
    public boolean registerGatewayServerNode(String groupId, String gatewayId, String gatewayName, String gatewayAddress, Integer available) {
        final GatewayServerDetail gatewayServerDetail = new GatewayServerDetail();
        gatewayServerDetail.setGroupId(groupId);
        gatewayServerDetail.setGatewayId(gatewayId);
        gatewayServerDetail.setGatewayName(gatewayName);
        gatewayServerDetail.setGatewayAddress(gatewayAddress);
        gatewayServerDetail.setStatus(available);
        gatewayServerDetailDao.insert(gatewayServerDetail);
        return true;
    }

    @Override
    public GatewayServerDetailVO queryGatewayServerDetail(String gatewayId, String gatewayAddress) {
        final GatewayServerDetail gatewayServerDetail = new GatewayServerDetail();
        gatewayServerDetail.setGatewayId(gatewayId);
        gatewayServerDetail.setGatewayAddress(gatewayAddress);
        final GatewayServerDetail queryGatewayServerDetail = gatewayServerDetailDao.queryGatewayServerDetail(gatewayServerDetail);
        if(queryGatewayServerDetail == null) {
            return null;
        }
        final GatewayServerDetailVO gatewayServerDetailVO = new GatewayServerDetailVO();
        gatewayServerDetailVO.setGatewayId(queryGatewayServerDetail.getGatewayId());
        gatewayServerDetailVO.setGatewayName(queryGatewayServerDetail.getGatewayName());
        gatewayServerDetailVO.setGatewayAddress(queryGatewayServerDetail.getGatewayAddress());
        gatewayServerDetailVO.setStatus(gatewayServerDetail.getStatus());
        return gatewayServerDetailVO;
    }

    @Override
    public boolean updateGatewayStatus(String gatewayId, String gatewayAddress, Integer available) {
        final GatewayServerDetail gatewayServerDetail = new GatewayServerDetail();
        gatewayServerDetail.setGatewayId(gatewayId);
        gatewayServerDetail.setGatewayAddress(gatewayAddress);
        gatewayServerDetail.setStatus(available);
        return gatewayServerDetailDao.updateGatewayStatus(gatewayServerDetail);
    }
}
