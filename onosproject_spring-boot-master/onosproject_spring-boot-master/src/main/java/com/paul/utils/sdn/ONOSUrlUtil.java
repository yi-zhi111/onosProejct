package com.paul.utils.sdn;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paul.domain.Controller;
import com.paul.domain.Host;
import com.paul.domain.Link;
import com.paul.domain.Switch;
import com.paul.utils.portListener.PortOccupy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author pxj
 * @date 2022-06-07 16:53
 * 从onos中获取信息
 */
@Component
public class ONOSUrlUtil {
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PortOccupy portOccupy;

    @Value("${requestURL.host}")
    private String host_url;

    @Value("${requestURL.link}")
    private String link_url;

    @Value("${requestURL.device}")
    private String device_url;

    @Value("${requestURL.port}")
    private String port;


    //获取交换机节点信息
    public List<Switch> getSwitchNodeInfo() {
        //获取网络拓扑的json（字符串形式）
        String url = restTemplate.getForObject(device_url, String.class);
        //处理json字符串
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = objectMapper.readTree(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //switch节点数组
        List<Switch> switchList = new ArrayList<>();

        //获取节点信息
        JsonNode networkTopologyNode = rootNode.get("devices");
        if (networkTopologyNode != null && networkTopologyNode.size() > 0) {
            Switch switchNode;
            for (int i = 0; i < networkTopologyNode.size(); i++) {
                switchNode = new Switch();
                //获取交换机id
                String switchNodeId = networkTopologyNode.get(i).get("id").asText();
                switchNode.setId(switchNodeId);
                //获取交换机活跃状态
                boolean switchNodeAvailable = networkTopologyNode.get(i).get("available").asBoolean();
                switchNode.setAvailable(switchNodeAvailable);
                //获取角色
                String switchNodeRole = networkTopologyNode.get(i).get("role").asText();
                switchNode.setRole(switchNodeRole);
                //获取交换机类型
                String switchNodeSwitchType = networkTopologyNode.get(i).get("hw").asText();
                switchNode.setSwitchType(switchNodeSwitchType);
                //获取协议
                String switchNodeProtocol = networkTopologyNode.get(i).get("annotations").get("protocol").asText();
                switchNode.setProtocol(switchNodeProtocol);
                //添加到节点List中
                switchList.add(switchNode);
            }
        }
        return switchList;
    }

    //获取连接信息
    public List<Link> getLinkInfo() {
        String url1 = restTemplate.getForObject(device_url, String.class);
        String url2 = restTemplate.getForObject(link_url, String.class);
        String url3 = restTemplate.getForObject(host_url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNodeDevice = null;
        JsonNode rootNodeLink = null;
        JsonNode rootNodeHost = null;
        try {
            rootNodeDevice = objectMapper.readTree(url1);
            rootNodeLink = objectMapper.readTree(url2);
            rootNodeHost = objectMapper.readTree(url3);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Link> linkList = new ArrayList<>();
        //获取连接信息
        JsonNode networkTopologyLink = rootNodeLink.get("links");
        JsonNode networkTopologyHost = rootNodeHost.get("hosts");
        JsonNode networkTopologyDevice = rootNodeDevice.get("devices");
        //用于添加topoId
        if (networkTopologyLink != null && networkTopologyLink.size() > 0) {
            Link link;
            for (int i = 0; i < networkTopologyLink.size(); i++) {
                link = new Link();
                //获取连接srcDevice
                String srcDevice = networkTopologyLink.get(i).get("src").get("device").asText();
                link.setSrcDevice(srcDevice);
                //获取连接srcPort
                String srcPort = networkTopologyLink.get(i).get("src").get("port").asText();
                link.setSrcPort(srcPort);
                //获取连接dstDevice
                String dstDevice = networkTopologyLink.get(i).get("dst").get("device").asText();
                link.setDstDevice(dstDevice);
                //获取连接dstPort
                String dstPort = networkTopologyLink.get(i).get("dst").get("port").asText();
                link.setDstPort(dstPort);

                //测试中发现一条链路会生成两个link，只是源地址和目的地址调换了，为了前端画拓扑方便，在这里合二为一，并重写link里面的equals()方法
                if (linkList.isEmpty()) {
                    linkList.add(link);
                } else {
                    int j;
                    for (j = 0; j < linkList.size(); j++)
                        if (linkList.get(j).equals(link))
                            break;
                    if (j == linkList.size())
                        //添加到连接List中
                        linkList.add(link);
                }

            }
        }
        //从主机信息中获取主机与交换机的连接
        if (networkTopologyHost != null && networkTopologyHost.size() > 0) {
            Link link;
            for (int i = 0; i < networkTopologyHost.size(); i++) {
                link = new Link();
                //获取连接srcDevice
                String srcDevice = networkTopologyHost.get(i).get("locations").get(0).get("elementId").asText();
                link.setSrcDevice(srcDevice);
                //获取连接srcPort
                String srcPort = networkTopologyHost.get(i).get("locations").get(0).get("port").asText();
                link.setSrcPort(srcPort);
                //获取连接dstDevice
                String dstDevice = networkTopologyHost.get(i).get("id").asText();
                link.setDstDevice(dstDevice);
                linkList.add(link);
            }

        }
        return linkList;
    }

    //获取主机信息
    public List<Host> getHostNodeInfo() {
        String url = restTemplate.getForObject(host_url, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = objectMapper.readTree(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //host节点数组
        List<Host> hostNodeList = new ArrayList<>();
        //获取节点信息
        JsonNode networkTopologyNode = rootNode.get("hosts");
        if (networkTopologyNode != null && networkTopologyNode.size() > 0) {

            Host hostNode;
            for (int i = 0; i < networkTopologyNode.size(); i++) {
                hostNode = new Host();
                //获取节点id
                String hostNodeId = networkTopologyNode.get(i).get("id").asText();
                hostNode.setId(hostNodeId);
                //获取节点mac帧地址
                String hostNodeMac = networkTopologyNode.get(i).get("mac").asText();
                hostNode.setMAC(hostNodeMac);
                //获取节点IP
                String hostNodeIP = networkTopologyNode.get(i).get("ipAddresses").get(0).asText();
                hostNode.setIP(hostNodeIP);
                //获取switch：与其相连的交换机id
                String SwitchId = networkTopologyNode.get(i).get("locations").get(0).get("elementId").asText();
                hostNode.setSwitchId(SwitchId);
                //获取switch端口：与其相连的交换机端口
                String port = networkTopologyNode.get(i).get("locations").get(0).get("port").asText();
                hostNode.setSwitchPort(port);
                //添加到节点List中
                hostNodeList.add(hostNode);
            }
        }
        return hostNodeList;
    }

    //获得控制器角色为master的交换机信息
    public List<HashMap<String, List<Switch>>> getControllerToSwitch(String[] controllerIP) {
        List<HashMap<String, List<Switch>>> switchesToControllerList = new ArrayList<>();
        HashMap<String, List<Switch>> switchToControllerMap = new HashMap<>();
        List<String> requestIP = new ArrayList<>();
        for (int i = 0; i < controllerIP.length; i++) {
            requestIP.add("http://" + controllerIP[i] + ":" + port + "/onos/v1/devices");
        }

        for (int i = 0; i < requestIP.size(); i++) {
            String url = restTemplate.getForObject(requestIP.get(i), String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = null;
            try {
                rootNode = objectMapper.readTree(url);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //switch节点数组
            List<Switch> switchList = new ArrayList<>();
            //获取节点信息
            JsonNode relatedSwitch = rootNode.get("devices");
            if (relatedSwitch != null && relatedSwitch.size() > 0) {
                Switch switchNode;
                for (int j = 0; j < relatedSwitch.size(); j++) {
                    switchNode = new Switch();
                    if (relatedSwitch.get(j).get("role").asText().equals("MASTER")) {
                        //获取交换机id
                        String switchNodeId = relatedSwitch.get(j).get("id").asText();
                        switchNode.setId(switchNodeId);
                        //获取交换机活跃状态
                        boolean switchNodeAvailable = relatedSwitch.get(j).get("available").asBoolean();
                        switchNode.setAvailable(switchNodeAvailable);
                        //获取角色
                        String switchNodeRole = relatedSwitch.get(j).get("role").asText();
                        switchNode.setRole(switchNodeRole);
                        //获取交换机类型
                        String switchNodeSwitchType = relatedSwitch.get(j).get("hw").asText();
                        switchNode.setSwitchType(switchNodeSwitchType);
                        //获取协议
                        String switchNodeProtocol = relatedSwitch.get(j).get("annotations").get("protocol").asText();
                        switchNode.setProtocol(switchNodeProtocol);
                        //添加到节点List中
                        switchList.add(switchNode);
                    }

                }
            }
            switchToControllerMap.put("ONOS" + (i + 1), switchList);
        }
        switchesToControllerList.add(switchToControllerMap);

        return switchesToControllerList;
    }

    public List<Controller> getControllerInfo() {
        List<Controller> controllerList = new ArrayList<>();
        HashMap<String, Boolean> flag = portOccupy.isRunning();

            for (Map.Entry<String, Boolean> entry : flag.entrySet()) {
                Controller controller = new Controller();
                controller.setName("ONOS" + entry.getKey().substring(entry.getKey().length() - 1));
                controller.setIp(entry.getKey());
                controller.setRunning(entry.getValue());
                controllerList.add(controller);
            }
        return controllerList;
    }
}
