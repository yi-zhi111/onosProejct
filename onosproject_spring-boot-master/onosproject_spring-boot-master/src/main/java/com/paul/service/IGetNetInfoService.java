package com.paul.service;

import com.paul.domain.Controller;
import com.paul.domain.Switch;
import com.paul.domain.Topology;

import java.util.HashMap;
import java.util.List;

/**
 * @author pxj
 * @date 2022-06-07 20:22
 * 初始化拓扑
 */
public interface IGetNetInfoService {
    Topology initTopologyInfo();

    List<Controller> getControllerInfo();

    List<HashMap<String, List<Switch>>> getControllerToSwitch(String[] controllerIP);

    List<Switch> getSwitchInfo();

    HashMap<String, HashMap<String, Integer>> getOfmCount();

    HashMap<String, String> getPkinRate();

    HashMap<String, String> getPkoutRate();
}
