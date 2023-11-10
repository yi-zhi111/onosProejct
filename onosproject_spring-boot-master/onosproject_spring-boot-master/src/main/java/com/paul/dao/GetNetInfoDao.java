package com.paul.dao;

import com.paul.domain.Controller;
import com.paul.domain.Host;
import com.paul.domain.Link;
import com.paul.domain.Switch;

import java.util.HashMap;
import java.util.List;

/**
 * @author pxj
 * @date 2022-06-07 20:26
 */
public interface GetNetInfoDao {
    List<Controller> getControllerList();

    List<Switch> getSwitchList();

    List<Link> getLinkList();

    List<Host> getHostList();

    List<HashMap<String, List<Switch>>> getControllerToSwitch(String[] controllerIP);

    HashMap<String, HashMap<String, Integer>> getOfmCount();

    HashMap<String, String> getPkinRate();

    HashMap<String, String> getPkoutRate();
}
