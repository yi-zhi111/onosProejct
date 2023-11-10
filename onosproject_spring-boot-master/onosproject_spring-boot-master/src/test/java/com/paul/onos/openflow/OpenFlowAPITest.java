package com.paul.onos.openflow;

import com.paul.utils.sdn.OpenFlowUrlUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * @author pxj
 * @date 2022-11-28 15:19
 */
@SpringBootTest
public class OpenFlowAPITest {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OpenFlowUrlUtil openFlowUrlUtil;

    private String ofmCount_url = "http://172.20.249.201:5000/ofp_sniffer/ofp_stats/packet_totals";

    //ofp_sniffer接口测试
    @Test
    void ofMessageCount() {
        String s = restTemplate.getForObject(ofmCount_url, String.class);
        System.out.println(s);
    }

    //openflow消息统计测试
    @Test
    void ofmCount() {
        HashMap<String, HashMap<String, Integer>> ofmCounters = openFlowUrlUtil.ofmCount();
        for (int i = 1; i <= 5; i++) {
            HashMap<String, Integer> ofmCounter = ofmCounters.get("172.20.249.20" + i);
            System.out.println("172.20.249.20" + i + ":");
            for (Map.Entry<String, Integer> entry : ofmCounter.entrySet()) {
                System.out.println(entry.getKey() + ", count : " + entry.getValue());
            }
        }
    }

    //packet_in消息速率测试
    @Test
    void pkinRate() {
        HashMap<String, String> pkinRate = openFlowUrlUtil.packetInRate();
        for (Map.Entry<String, String> entry : pkinRate.entrySet()) {
            System.out.println(entry.getKey() + ", rate : " + entry.getValue());
        }
    }

}
