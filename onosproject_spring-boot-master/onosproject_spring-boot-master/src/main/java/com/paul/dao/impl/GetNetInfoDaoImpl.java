package com.paul.dao.impl;

import com.paul.dao.GetNetInfoDao;
import com.paul.domain.Controller;
import com.paul.domain.Host;
import com.paul.domain.Link;
import com.paul.domain.Switch;
import com.paul.utils.sdn.ONOSUrlUtil;
import com.paul.utils.sdn.OpenFlowUrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

/**
 * @author pxj
 * @date 2022-06-07 20:26
 */
@Repository
public class GetNetInfoDaoImpl implements GetNetInfoDao {
    @Autowired
    private ONOSUrlUtil onosUrlUtil;

    @Autowired
    private OpenFlowUrlUtil openFlowUrlUtil;

    @Override
    public List<Controller> getControllerList() {
        return onosUrlUtil.getControllerInfo();
    }

    @Override
    public List<Switch> getSwitchList() {
        return onosUrlUtil.getSwitchNodeInfo();
    }

    @Override
    public List<Link> getLinkList() {
        return onosUrlUtil.getLinkInfo();
    }

    @Override
    public List<Host> getHostList() {
        return onosUrlUtil.getHostNodeInfo();
    }

    @Override
    public List<HashMap<String, List<Switch>>> getControllerToSwitch(String[] controllerIP) {
        return onosUrlUtil.getControllerToSwitch(controllerIP);
    }

    @Override
    public HashMap<String, HashMap<String, Integer>> getOfmCount() {
        return openFlowUrlUtil.ofmCount();
    }

    @Override
    public HashMap<String, String> getPkinRate() {
        return openFlowUrlUtil.packetInRate();
    }

    @Override
    public HashMap<String, String> getPkoutRate() {
        return openFlowUrlUtil.packetOutRate();
    }
}
