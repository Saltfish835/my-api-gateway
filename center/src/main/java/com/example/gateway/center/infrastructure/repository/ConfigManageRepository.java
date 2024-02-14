package com.example.gateway.center.infrastructure.repository;

import com.example.gateway.center.domain.manage.repository.IConfigManageRepository;
import com.example.gateway.center.domain.manage.model.vo.*;
import com.example.gateway.center.infrastructure.dao.*;
import com.example.gateway.center.infrastructure.po.*;
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

    @Resource
    private IGatewayDistributionDao gatewayDistributionDao;

    @Resource
    private IApplicationSystemDao applicationSystemDao;

    @Resource
    private IApplicationInterfaceDao applicationInterfaceDao;

    @Resource
    private IApplicationInterfaceMethodDao applicationInterfaceMethodDao;


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
    public List<GatewayServerDetailVO> queryGatewayServerDetailList() {
        final List<GatewayServerDetail> gatewayServerDetails = gatewayServerDetailDao.queryGatewayServerDetailList();
        List<GatewayServerDetailVO> gatewayServerDetailVOList = new ArrayList<>(gatewayServerDetails.size());
        for(GatewayServerDetail gatewayServerDetail : gatewayServerDetails) {
            final GatewayServerDetailVO gatewayServerDetailVO = new GatewayServerDetailVO();
            gatewayServerDetailVO.setId(gatewayServerDetail.getId());
            gatewayServerDetailVO.setGroupId(gatewayServerDetail.getGatewayId());
            gatewayServerDetailVO.setGatewayId(gatewayServerDetail.getGatewayId());
            gatewayServerDetailVO.setGatewayName(gatewayServerDetail.getGatewayName());
            gatewayServerDetailVO.setGatewayAddress(gatewayServerDetail.getGatewayAddress());
            gatewayServerDetailVO.setStatus(gatewayServerDetail.getStatus());
            gatewayServerDetailVO.setCreateTime(gatewayServerDetail.getCreateTime());
            gatewayServerDetailVO.setUpdateTime(gatewayServerDetail.getUpdateTime());
            gatewayServerDetailVOList.add(gatewayServerDetailVO);
        }
        return gatewayServerDetailVOList;
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

    @Override
    public List<String> queryGatewayDistributionSystemIdList(String gatewayId) {
        return gatewayDistributionDao.queryGatewayDistributionSystemIdList(gatewayId);
    }

    @Override
    public List<ApplicationSystemVO> queryApplicationSystemList(List<String> systemIdList) {
        final List<ApplicationSystem> applicationSystemList = applicationSystemDao.queryApplicationSystemList(systemIdList);
        final ArrayList<ApplicationSystemVO> applicationSystemVOList = new ArrayList<>(applicationSystemList.size());
        for(ApplicationSystem applicationSystem : applicationSystemList) {
            final ApplicationSystemVO applicationSystemVO = new ApplicationSystemVO();
            applicationSystemVO.setSystemId(applicationSystem.getSystemId());
            applicationSystemVO.setSystemName(applicationSystem.getSystemName());
            applicationSystemVO.setSystemType(applicationSystem.getSystemType());
            applicationSystemVO.setSystemRegistry(applicationSystem.getSystemRegistry());
            applicationSystemVOList.add(applicationSystemVO);
        }
        return applicationSystemVOList;
    }

    @Override
    public List<ApplicationInterfaceVO> queryApplicationInterfaceLis(String systemId) {
        final List<ApplicationInterface> applicationInterfaceList = applicationInterfaceDao.queryApplicationInterfaceList(systemId);
        List<ApplicationInterfaceVO> applicationInterfaceVOList = new ArrayList<>(applicationInterfaceList.size());
        for(ApplicationInterface applicationInterface : applicationInterfaceList) {
            final ApplicationInterfaceVO applicationInterfaceVO = new ApplicationInterfaceVO();
            applicationInterfaceVO.setSystemId(applicationInterface.getSystemId());
            applicationInterfaceVO.setInterfaceId(applicationInterface.getInterfaceId());
            applicationInterfaceVO.setInterfaceName(applicationInterface.getInterfaceName());
            applicationInterfaceVO.setInterfaceVersion(applicationInterface.getInterfaceVersion());
            applicationInterfaceVOList.add(applicationInterfaceVO);
        }
        return applicationInterfaceVOList;
    }

    @Override
    public List<ApplicationInterfaceMethodVO> queryApplicationInterfaceMethodList(String systemId, String interfaceId) {
        final ApplicationInterfaceMethod req = new ApplicationInterfaceMethod();
        req.setSystemId(systemId);
        req.setInterfaceId(interfaceId);
        final List<ApplicationInterfaceMethod> applicationInterfaceMethodList = applicationInterfaceMethodDao.queryApplicationInterfaceMethodList(req);
        List<ApplicationInterfaceMethodVO> applicationInterfaceMethodVOList = new ArrayList<>(applicationInterfaceMethodList.size());
        for(ApplicationInterfaceMethod applicationInterfaceMethod : applicationInterfaceMethodList) {
            final ApplicationInterfaceMethodVO applicationInterfaceMethodVO = new ApplicationInterfaceMethodVO();
            applicationInterfaceMethodVO.setSystemId(applicationInterfaceMethod.getSystemId());
            applicationInterfaceMethodVO.setInterfaceId(applicationInterfaceMethod.getInterfaceId());
            applicationInterfaceMethodVO.setMethodId(applicationInterfaceMethod.getMethodId());
            applicationInterfaceMethodVO.setMethodName(applicationInterfaceMethod.getMethodName());
            applicationInterfaceMethodVO.setParameterType(applicationInterfaceMethod.getParameterType());
            applicationInterfaceMethodVO.setUri(applicationInterfaceMethod.getUri());
            applicationInterfaceMethodVO.setHttpCommandType(applicationInterfaceMethod.getHttpCommandType());
            applicationInterfaceMethodVO.setAuth(applicationInterfaceMethod.getAuth());
            applicationInterfaceMethodVOList.add(applicationInterfaceMethodVO);
        }
        return applicationInterfaceMethodVOList;
    }

    @Override
    public String queryGatewayDistribution(String systemId) {
        return gatewayDistributionDao.queryGatewayDistribution(systemId);
    }

    @Override
    public List<GatewayDistributionVO> queryGatewayDistributionList() {
        final List<GatewayDistribution> gatewayDistributions = gatewayDistributionDao.queryGatewayDistributionList();
        List<GatewayDistributionVO> gatewayDistributionVOList = new ArrayList<>(gatewayDistributions.size());
        for(GatewayDistribution gatewayDistribution : gatewayDistributions) {
            final GatewayDistributionVO gatewayDistributionVO = new GatewayDistributionVO();
            gatewayDistributionVO.setId(gatewayDistribution.getId());
            gatewayDistributionVO.setGroupId(gatewayDistribution.getGatewayId());
            gatewayDistributionVO.setGatewayId(gatewayDistribution.getSystemId());
            gatewayDistributionVO.setSystemName(gatewayDistribution.getSystemName());
            gatewayDistributionVO.setCreateTime(gatewayDistribution.getCreateTime());
            gatewayDistributionVO.setUpdateTime(gatewayDistribution.getUpdateTime());
            gatewayDistributionVOList.add(gatewayDistributionVO);
        }
        return gatewayDistributionVOList;
    }
}
