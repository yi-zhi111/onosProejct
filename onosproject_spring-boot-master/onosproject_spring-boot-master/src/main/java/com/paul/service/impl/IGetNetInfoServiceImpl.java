package com.paul.service.impl;

import com.paul.dao.GetNetInfoDao;
import com.paul.domain.Controller;
import com.paul.domain.Switch;
import com.paul.domain.Topology;
import com.paul.service.IGetNetInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 * @author pxj
 * @date 2022-06-07 20:23
 */
@Service
public class IGetNetInfoServiceImpl implements IGetNetInfoService {
    @Autowired
    private GetNetInfoDao getNetInfoDao;


    @Override
    public Topology initTopologyInfo() {
        Topology topology = new Topology();
        topology.setSwitchList(getNetInfoDao.getSwitchList());
        topology.setLinkList(getNetInfoDao.getLinkList());
        topology.setHostList(getNetInfoDao.getHostList());
        return topology;
    }

    @Override
    public List<Controller> getControllerInfo() {
        return getNetInfoDao.getControllerList();
    }

    @Override
    public List<HashMap<String, List<Switch>>> getControllerToSwitch(String[] controllerIP) {
        return getNetInfoDao.getControllerToSwitch(controllerIP);
    }

    @Override
    public List<Switch> getSwitchInfo() {
        return getNetInfoDao.getSwitchList();
    }

    @Override
    public HashMap<String, HashMap<String, Integer>> getOfmCount() {
        return getNetInfoDao.getOfmCount();
    }

    @Override
    public HashMap<String, String> getPkinRate() {
        return getNetInfoDao.getPkinRate();
    }

    @Override
    public HashMap<String, String> getPkoutRate() {
        return getNetInfoDao.getPkoutRate();
    }

}
