package com.example.gateway.center.infrastructure.repository;

import com.example.gateway.center.domain.operation.model.vo.*;
import com.example.gateway.center.domain.operation.repository.IDataOperationManageRepository;
import com.example.gateway.center.infrastructure.common.OperationRequest;
import com.example.gateway.center.infrastructure.dao.*;
import com.example.gateway.center.infrastructure.po.*;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Primary
@Component
public class DataOperationManageRepository implements IDataOperationManageRepository {

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
    public List<GatewayServerDataVO> queryGatewayServerListByPage(OperationRequest<String> request) {
        final List<GatewayServer> gatewayServers = gatewayServerDao.queryGatewayServerListByPage(request);
        List<GatewayServerDataVO> gatewayServerDataVOList = new ArrayList<>(gatewayServers.size());
        for(GatewayServer gatewayServer : gatewayServers) {
            final GatewayServerDataVO gatewayServerDataVO = new GatewayServerDataVO();
            gatewayServerDataVO.setId(gatewayServer.getId());
            gatewayServerDataVO.setGroupId(gatewayServer.getGroupId());
            gatewayServerDataVO.setGroupName(gatewayServer.getGroupName());
            gatewayServerDataVOList.add(gatewayServerDataVO);
        }
        return gatewayServerDataVOList;
    }

    @Override
    public int queryGatewayServerListCountByPage(OperationRequest<String> request) {
        return gatewayServerDao.queryGatewayServerListCountByPage(request);
    }

    @Override
    public List<ApplicationSystemDataVO> queryApplicationSystemListByPage(OperationRequest<ApplicationSystemDataVO> request) {
        final List<ApplicationSystem> applicationSystems = applicationSystemDao.queryApplicationSystemListByPage(request);
        List<ApplicationSystemDataVO> applicationSystemDataVOList = new ArrayList<>(applicationSystems.size());
        for(ApplicationSystem applicationSystem : applicationSystems) {
            ApplicationSystemDataVO applicationSystemDataVO = new ApplicationSystemDataVO();
            applicationSystemDataVO.setSystemId(applicationSystem.getSystemId());
            applicationSystemDataVO.setSystemName(applicationSystem.getSystemName());
            applicationSystemDataVO.setSystemType(applicationSystem.getSystemType());
            applicationSystemDataVO.setSystemRegistry(applicationSystem.getSystemRegistry());
            applicationSystemDataVOList.add(applicationSystemDataVO);
        }
        return applicationSystemDataVOList;
    }

    @Override
    public int queryApplicationSystemListCountByPage(OperationRequest<ApplicationSystemDataVO> request) {
        return applicationSystemDao.queryApplicationSystemListCountByPage(request);
    }

    @Override
    public List<ApplicationInterfaceDataVO> queryApplicationInterfaceListByPage(OperationRequest<ApplicationInterfaceDataVO> request) {
        final List<ApplicationInterface> applicationInterfaces = applicationInterfaceDao.queryApplicationInterfaceListByPage(request);
        List<ApplicationInterfaceDataVO> applicationInterfaceDataVOList = new ArrayList<>(applicationInterfaces.size());
        for(ApplicationInterface applicationInterface : applicationInterfaces) {
            final ApplicationInterfaceDataVO applicationInterfaceDataVO = new ApplicationInterfaceDataVO();
            applicationInterfaceDataVO.setSystemId(applicationInterface.getSystemId());
            applicationInterfaceDataVO.setInterfaceId(applicationInterface.getInterfaceId());
            applicationInterfaceDataVO.setInterfaceName(applicationInterface.getInterfaceName());
            applicationInterfaceDataVO.setInterfaceVersion(applicationInterface.getInterfaceVersion());
            applicationInterfaceDataVOList.add(applicationInterfaceDataVO);
        }
        return applicationInterfaceDataVOList;
    }

    @Override
    public int queryApplicationInterfaceListCountByPage(OperationRequest<ApplicationInterfaceDataVO> request) {
        return applicationInterfaceDao.queryApplicationInterfaceListCountByPage(request);
    }

    @Override
    public List<ApplicationInterfaceMethodDataVO> queryApplicationInterfaceMethodListByPage(OperationRequest<ApplicationInterfaceMethodDataVO> request) {
        final List<ApplicationInterfaceMethod> applicationInterfaceMethods = applicationInterfaceMethodDao.queryApplicationInterfaceMethodListByPage(request);
        List<ApplicationInterfaceMethodDataVO> applicationInterfaceMethodDataVOList = new ArrayList<>(applicationInterfaceMethods.size());
        for(ApplicationInterfaceMethod applicationInterfaceMethod : applicationInterfaceMethods) {
            final ApplicationInterfaceMethodDataVO applicationInterfaceMethodDataVO = new ApplicationInterfaceMethodDataVO();
            applicationInterfaceMethodDataVO.setSystemId(applicationInterfaceMethod.getSystemId());
            applicationInterfaceMethodDataVO.setInterfaceId(applicationInterfaceMethod.getInterfaceId());
            applicationInterfaceMethodDataVO.setMethodId(applicationInterfaceMethod.getMethodId());
            applicationInterfaceMethodDataVO.setMethodName(applicationInterfaceMethod.getMethodName());
            applicationInterfaceMethodDataVO.setParameterType(applicationInterfaceMethod.getParameterType());
            applicationInterfaceMethodDataVO.setUri(applicationInterfaceMethod.getUri());
            applicationInterfaceMethodDataVO.setHttpCommandType(applicationInterfaceMethod.getHttpCommandType());
            applicationInterfaceMethodDataVO.setAuth(applicationInterfaceMethod.getAuth());
            applicationInterfaceMethodDataVOList.add(applicationInterfaceMethodDataVO);
        }
        return applicationInterfaceMethodDataVOList;
    }

    @Override
    public int queryApplicationInterfaceMethodListCountByPage(OperationRequest<ApplicationInterfaceMethodDataVO> request) {
        return applicationInterfaceMethodDao.queryApplicationInterfaceMethodListCountByPage(request);
    }

    @Override
    public List<GatewayServerDetailDataVO> queryGatewayServerDetailListByPage(OperationRequest<GatewayServerDetailDataVO> request) {
        final List<GatewayServerDetail> gatewayServerDetails = gatewayServerDetailDao.queryGatewayServerDetailListByPage(request);
        List<GatewayServerDetailDataVO> gatewayServerDetailDataVOList = new ArrayList<>(gatewayServerDetails.size());
        for(GatewayServerDetail gatewayServerDetail : gatewayServerDetails) {
            final GatewayServerDetailDataVO gatewayServerDetailDataVO = new GatewayServerDetailDataVO();
            gatewayServerDetailDataVO.setId(gatewayServerDetail.getId());
            gatewayServerDetailDataVO.setGroupId(gatewayServerDetail.getGroupId());
            gatewayServerDetailDataVO.setGatewayId(gatewayServerDetail.getGatewayId());
            gatewayServerDetailDataVO.setGatewayName(gatewayServerDetail.getGatewayName());
            gatewayServerDetailDataVO.setGatewayAddress(gatewayServerDetail.getGatewayAddress());
            gatewayServerDetailDataVO.setStatus(gatewayServerDetail.getStatus());
            gatewayServerDetailDataVO.setCreateTime(gatewayServerDetail.getCreateTime());
            gatewayServerDetailDataVO.setUpdateTime(gatewayServerDetail.getUpdateTime());
            gatewayServerDetailDataVOList.add(gatewayServerDetailDataVO);
        }
        return gatewayServerDetailDataVOList;
    }

    @Override
    public int queryGatewayServerDetailListCountByPage(OperationRequest<GatewayServerDetailDataVO> request) {
        return gatewayServerDetailDao.queryGatewayServerDetailListCountByPage(request);
    }

    @Override
    public List<GatewayDistributionDataVO> queryGatewayDistributionListByPage(OperationRequest<GatewayDistributionDataVO> request) {
        final List<GatewayDistribution> gatewayDistributions = gatewayDistributionDao.queryGatewayDistributionListByPage(request);
        List<GatewayDistributionDataVO> gatewayDistributionDataVOList = new ArrayList<>(gatewayDistributions.size());
        for(GatewayDistribution gatewayDistribution : gatewayDistributions) {
            final GatewayDistributionDataVO gatewayDistributionDataVO = new GatewayDistributionDataVO();
            gatewayDistributionDataVO.setId(gatewayDistribution.getId());
            gatewayDistributionDataVO.setGroupId(gatewayDistribution.getGroupId());
            gatewayDistributionDataVO.setGatewayId(gatewayDistribution.getGatewayId());
            gatewayDistributionDataVO.setSystemId(gatewayDistribution.getSystemId());
            gatewayDistributionDataVO.setSystemName(gatewayDistribution.getSystemName());
            gatewayDistributionDataVO.setCreateTime(gatewayDistribution.getCreateTime());
            gatewayDistributionDataVO.setUpdateTime(gatewayDistribution.getUpdateTime());
            gatewayDistributionDataVOList.add(gatewayDistributionDataVO);
        }
        return gatewayDistributionDataVOList;
    }

    @Override
    public int queryGatewayDistributionListCountByPage(OperationRequest<GatewayDistributionDataVO> request) {
        return gatewayDistributionDao.queryGatewayDistributionListCountByPage(request);
    }
}
