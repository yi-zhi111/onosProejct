package com.paul.controller;


import com.paul.domain.Controller;
import com.paul.domain.Switch;
import com.paul.domain.Topology;
import com.paul.service.IGetNetInfoService;
import com.paul.utils.portListener.PortOccupy;
import com.paul.utils.result.Result;
import com.paul.utils.result.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

/**
 * 收集相关网络参数，并传到前端
 *
 * @author pxj
 * @date 2022-06-07 20:18
 */
@RestController
public class GetNetInfoController {
    @Autowired
    private IGetNetInfoService iGetNetInfoService;
    @Autowired
    private Result result;
    @Autowired
    private PortOccupy portOccupy;

    @GetMapping("/getTopology")
    @CrossOrigin
    public Result<Topology> getTopology() {
        //初始化拓扑
        Topology topology = iGetNetInfoService.initTopologyInfo();
        //将拓扑转化成json并返回前端

        try {
            return result.success(Status.OK, topology);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.internalServerError(Status.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/controllerInfo")
    @CrossOrigin
    public Result<List<Controller>> getControllerInfo(){
        List<Controller> controllerList = iGetNetInfoService.getControllerInfo();
        try {
            return result.success(Status.OK, controllerList);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result.internalServerError(Status.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/masterSwitch")
    @CrossOrigin
    public Result<List<HashMap<String, List<Switch>>>> getMasterSwitch() {
        String[] controllerIP = new String[]{"172.20.249.201", "172.20.249.202", "172.20.249.203", "172.20.249.204", "172.20.249.205"};
        List<HashMap<String, List<Switch>>> masterSwitch = iGetNetInfoService.getControllerToSwitch(controllerIP);
        try {
            return result.success(Status.OK, masterSwitch);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.internalServerError(Status.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/switchInfo")
    @CrossOrigin
    public Result<List<Switch>> getSwitchInfo(){
        List<Switch> switchInfo = iGetNetInfoService.getSwitchInfo();
        try {
            return result.success(Status.OK, switchInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.internalServerError(Status.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/ofmCount")
    @CrossOrigin
    public Result<HashMap<String, HashMap<String, Integer>>> getOfmCount(){
        HashMap<String, HashMap<String, Integer>> ofmCount = iGetNetInfoService.getOfmCount();
        try {
            return result.success(Status.OK, ofmCount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.internalServerError(Status.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/pkinRate")
    @CrossOrigin
    public Result<HashMap<String, String>> getPkinRate(){
        HashMap<String, String> pkinRate = iGetNetInfoService.getPkinRate();
        try {
            return result.success(Status.OK, pkinRate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.internalServerError(Status.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/pkoutRate")
    @CrossOrigin
    public Result<HashMap<String, String>> getPkoutRate(){
        HashMap<String, String> pkoutRate = iGetNetInfoService.getPkoutRate();
        try {
            return result.success(Status.OK,pkoutRate);
        }catch (Exception e){
            e.printStackTrace();
        }
        return result.internalServerError(Status.INTERNAL_SERVER_ERROR);
    }

}
