package com.paul.onos.controller;

import com.paul.domain.Switch;
import com.paul.utils.sdn.ONOSUrlUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

/**
 * @author pxj
 * @date 2022-11-28 14:59
 */
@SpringBootTest
public class ONOSAPITest {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ONOSUrlUtil util;

    @Value("${requestURL.host}")
    private String host_url;

    @Value("${requestURL.link}")
    private String link_url;

    @Value("${requestURL.device}")
    private String device_url;

    //主机信息
    @Test
    void hostAPI() {
        String s = restTemplate.getForObject(host_url, String.class);
        System.out.println(s);
    }

    //链路信息
    @Test
    void linkAPI() {
        String s = restTemplate.getForObject(link_url, String.class);
        System.out.println(s);
    }

    //交换机信息
    @Test
    void switchAPI() {
        String s = restTemplate.getForObject(device_url, String.class);
        System.out.println(s);
    }

    //拓扑信息
    @Test
    void topoInfo() {
        System.out.println("拓扑主机数量：" + util.getHostNodeInfo().size());
        for (int i = 0; i < util.getHostNodeInfo().size(); i++)
            System.out.println(util.getHostNodeInfo().get(i));

        System.out.println("拓扑交换机数量：" + util.getSwitchNodeInfo().size());
        for (int i = 0; i < util.getSwitchNodeInfo().size(); i++)
            System.out.println(util.getSwitchNodeInfo().get(i));

        System.out.println("拓扑连接数量：" + util.getLinkInfo().size());
        for (int i = 0; i < util.getLinkInfo().size(); i++)
            System.out.println(util.getLinkInfo().get(i));
    }

    //主控制器下的交换机信息
    @Test
    public void switchInfoManagedByMasterController() {
        List<HashMap<String, List<Switch>>> masterSwitch = util.getControllerToSwitch(new String[]{"172.20.249.201","172.20.249.202","172.20.249.203","172.20.249.204","172.20.249.205",});
        for (int i = 0; i < masterSwitch.size(); i++) {
            for (String key:masterSwitch.get(i).keySet()){//遍历key
                System.out.println(key);
                for (int j = 0; j < masterSwitch.get(i).get(key).size(); j++) {
                    System.out.println(masterSwitch.get(i).get(key).get(j));
                }

            }
        }
    }




}
