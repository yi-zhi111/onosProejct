package com.paul.utils.sdn;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.*;

/**
 * @author pxj
 * @date 2022-11-28 15:47
 */
@Component
public class OpenFlowUrlUtil {
    @Autowired
    private RestTemplate restTemplate;

    //openflow消息统计
    public HashMap<String, HashMap<String, Integer>> ofmCount() {
        HashMap<String, HashMap<String, Integer>> ofmCounters = new HashMap<>();
        for (int i = 1; i <= 5; i++) {
            String s = restTemplate.getForObject("http://172.20.249.20" + i + ":5000/ofp_sniffer/ofp_stats/packet_totals", String.class);
            //处理json字符串
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = null;
            try {
                rootNode = objectMapper.readTree(s);
            } catch (IOException e) {
                e.printStackTrace();
            }

            HashMap<String, Integer> ofmCounter = new HashMap<>();
            //获取节点信息
            JsonNode MessageInfoNode = rootNode.get("result").get("per_types").get("4");

            ofmCounter.put("OFPT_HELLO", MessageInfoNode.get("OFPT_HELLO").asInt());
            ofmCounter.put("OFPT_ERROR", MessageInfoNode.get("OFPT_ERROR").asInt());
            ofmCounter.put("OFPT_ECHO_REQUEST", MessageInfoNode.get("OFPT_ECHO_REQUEST").asInt());
            ofmCounter.put("OFPT_ECHO_REPLY", MessageInfoNode.get("OFPT_ECHO_REPLY").asInt());
            ofmCounter.put("OFPT_EXPERIMENTER", MessageInfoNode.get("OFPT_EXPERIMENTER").asInt());
            ofmCounter.put("OFPT_FEATURES_REQUEST", MessageInfoNode.get("OFPT_FEATURES_REQUEST").asInt());
            ofmCounter.put("OFPT_FEATURES_REPLY", MessageInfoNode.get("OFPT_FEATURES_REPLY").asInt());
            ofmCounter.put("OFPT_GET_CONFIG_REQUEST", MessageInfoNode.get("OFPT_GET_CONFIG_REQUEST").asInt());
            ofmCounter.put("OFPT_GET_CONFIG_REPLY", MessageInfoNode.get("OFPT_GET_CONFIG_REPLY").asInt());
            ofmCounter.put("OFPT_SET_CONFIG", MessageInfoNode.get("OFPT_SET_CONFIG").asInt());
            ofmCounter.put("OFPT_PACKET_IN", MessageInfoNode.get("OFPT_PACKET_IN").asInt());
            ofmCounter.put("OFPT_FLOW_REMOVED", MessageInfoNode.get("OFPT_FLOW_REMOVED").asInt());
            ofmCounter.put("OFPT_PORT_STATUS", MessageInfoNode.get("OFPT_PORT_STATUS").asInt());
            ofmCounter.put("OFPT_PACKET_OUT", MessageInfoNode.get("OFPT_PACKET_OUT").asInt());
            ofmCounter.put("OFPT_FLOW_MOD", MessageInfoNode.get("OFPT_FLOW_MOD").asInt());
            ofmCounter.put("OFPT_GROUP_MOD", MessageInfoNode.get("OFPT_GROUP_MOD").asInt());
            ofmCounter.put("OFPT_PORT_MOD", MessageInfoNode.get("OFPT_PORT_MOD").asInt());
            ofmCounter.put("OFPT_TABLE_MOD", MessageInfoNode.get("OFPT_TABLE_MOD").asInt());
            ofmCounter.put("OFPT_MULTIPART_REQUEST", MessageInfoNode.get("OFPT_MULTIPART_REQUEST").asInt());
            ofmCounter.put("OFPT_MULTIPART_REPLY", MessageInfoNode.get("OFPT_MULTIPART_REPLY").asInt());
            ofmCounter.put("OFPT_BARRIER_REQUEST", MessageInfoNode.get("OFPT_BARRIER_REQUEST").asInt());
            ofmCounter.put("OFPT_BARRIER_REPLY", MessageInfoNode.get("OFPT_BARRIER_REPLY").asInt());
            ofmCounter.put("OFPT_QUEUE_GET_CONFIG_REQUEST", MessageInfoNode.get("OFPT_QUEUE_GET_CONFIG_REQUEST").asInt());
            ofmCounter.put("OFPT_QUEUE_GET_CONFIG_REPLY", MessageInfoNode.get("OFPT_QUEUE_GET_CONFIG_REPLY").asInt());
            ofmCounter.put("OFPT_ROLE_REQUEST", MessageInfoNode.get("OFPT_ROLE_REQUEST").asInt());
            ofmCounter.put("OFPT_ROLE_REPLY", MessageInfoNode.get("OFPT_ROLE_REPLY").asInt());
            ofmCounter.put("OFPT_GET_ASYNC_REQUEST", MessageInfoNode.get("OFPT_GET_ASYNC_REQUEST").asInt());
            ofmCounter.put("OFPT_GET_ASYNC_REPLY", MessageInfoNode.get("OFPT_GET_ASYNC_REPLY").asInt());
            ofmCounter.put("OFPT_SET_ASYNC", MessageInfoNode.get("OFPT_METER_MOD").asInt());
            ofmCounters.put("172.20.249.20" + i, ofmCounter);
        }
        return ofmCounters;
    }

    //packet_in速率统计
    public HashMap<String, String> packetInRate() {
        HashMap<String, String> pkinRate = new HashMap<>();
        //通过线程池工厂获取线程池对象
        packetRateService thread1 = new packetRateService("172.20.249.201", "OFPT_PACKET_IN");
        packetRateService thread2 = new packetRateService("172.20.249.202", "OFPT_PACKET_IN");
        packetRateService thread3 = new packetRateService("172.20.249.203", "OFPT_PACKET_IN");
        packetRateService thread4 = new packetRateService("172.20.249.204", "OFPT_PACKET_IN");
        packetRateService thread5 = new packetRateService("172.20.249.205", "OFPT_PACKET_IN");
        //提交线程任务
        ExecutorService service = Executors.newFixedThreadPool(5);
        Future<Integer> rate1 = service.submit(thread1);
        Future<Integer> rate2 = service.submit(thread2);
        Future<Integer> rate3 = service.submit(thread3);
        Future<Integer> rate4 = service.submit(thread4);
        Future<Integer> rate5 = service.submit(thread5);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String format = simpleDateFormat.format(date);

        try {
            pkinRate.put("ONOS1", String.valueOf(Math.multiplyExact(rate1.get(), 20)));
            pkinRate.put("ONOS2", String.valueOf(Math.multiplyExact(rate2.get(), 20)));
            pkinRate.put("ONOS3", String.valueOf(Math.multiplyExact(rate3.get(), 20)));
            pkinRate.put("ONOS4", String.valueOf(Math.multiplyExact(rate4.get(), 20)));
            pkinRate.put("ONOS5", String.valueOf(Math.multiplyExact(rate5.get(), 20)));
            pkinRate.put("time", format);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return pkinRate;
    }

    //packet_out速率统计
    public HashMap<String, String> packetOutRate() {
        HashMap<String, String> pkoutRate = new HashMap<>();
        //通过线程池工厂获取线程池对象
        packetRateService thread1 = new packetRateService("172.20.249.201", "OFPT_PACKET_OUT");
        packetRateService thread2 = new packetRateService("172.20.249.202", "OFPT_PACKET_OUT");
        packetRateService thread3 = new packetRateService("172.20.249.203", "OFPT_PACKET_OUT");
        packetRateService thread4 = new packetRateService("172.20.249.204", "OFPT_PACKET_OUT");
        packetRateService thread5 = new packetRateService("172.20.249.205", "OFPT_PACKET_OUT");
        //提交线程任务
        ExecutorService service = Executors.newFixedThreadPool(5);
        Future<Integer> rate1 = service.submit(thread1);
        Future<Integer> rate2 = service.submit(thread2);
        Future<Integer> rate3 = service.submit(thread3);
        Future<Integer> rate4 = service.submit(thread4);
        Future<Integer> rate5 = service.submit(thread5);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        String format = simpleDateFormat.format(date);
        try {
            pkoutRate.put("ONOS1", String.valueOf(Math.multiplyExact(rate1.get(), 16)));
            pkoutRate.put("ONOS2", String.valueOf(Math.multiplyExact(rate2.get(), 16)));
            pkoutRate.put("ONOS3", String.valueOf(Math.multiplyExact(rate3.get(), 16)));
            pkoutRate.put("ONOS4", String.valueOf(Math.multiplyExact(rate4.get(), 16)));
            pkoutRate.put("ONOS5", String.valueOf(Math.multiplyExact(rate5.get(), 16)));
            pkoutRate.put("time", format);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return pkoutRate;
    }

    //写入csv
    @Async
    public void writePkinDataInCsv(){
        String head = "ONOS1,ONOS2,ONOS3,ONOS4,ONOS5";
        String filePath = "./src/main/resources/data/packet_in.csv";
        writeDataStringToCsvWithHead(head, filePath, true);
        while (true) {
            StringBuilder sb = new StringBuilder();
            HashMap<String,String> pktInRate = packetInRate();
            String pkt1 = pktInRate.get("ONOS1");
            String pkt2 = pktInRate.get("ONOS2");
            String pkt3 = pktInRate.get("ONOS3");
            String pkt4 = pktInRate.get("ONOS4");
            String pkt5 = pktInRate.get("ONOS5");
            String dataStr = sb.append(String.format("%s,%s,%s,%s,%s", pkt1, pkt2, pkt3, pkt4, pkt5)).append('\r').append('\n').toString();
            writeDataStringToCsvWithoutHead(dataStr, filePath, true);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Async
    public void writePkoutDataInCsv(){
        String head = "ONOS1,ONOS2,ONOS3,ONOS4,ONOS5";
        String filePath = "./src/main/resources/data/packet_out.csv";
        writeDataStringToCsvWithHead(head, filePath, true);
        while (true) {
            StringBuilder sb = new StringBuilder();
            HashMap<String, String> packetOutRate = packetOutRate();
            String pkt1 = packetOutRate.get("ONOS1");
            String pkt2 = packetOutRate.get("ONOS2");
            String pkt3 = packetOutRate.get("ONOS3");
            String pkt4 = packetOutRate.get("ONOS4");
            String pkt5 = packetOutRate.get("ONOS5");
            String dataStr = sb.append(String.format("%s,%s,%s,%s,%s", pkt1, pkt2, pkt3, pkt4, pkt5)).append('\r').append('\n').toString();
            writeDataStringToCsvWithoutHead(dataStr, filePath, true);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Data
    private class packetRateService implements Callable<Integer> {
        private String ip;
        private String ofType;

        public packetRateService(String ip, String ofType) {
            this.ip = ip;
            this.ofType = ofType;
        }

        @Override
        public Integer call() {
            int time1 = ofmCount().get(this.getIp()).get(this.getOfType());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            int time2 = ofmCount().get(this.getIp()).get(this.getOfType());

            int rate = Math.abs(time1 - time2);
            return rate;
        }
    }

    public void writeDataStringToCsvWithHead(String head, String csvFilePath, boolean append) {
        // 将流写在try里面，当try执行完之后，流会自动关闭
        try (FileOutputStream fileOutputStream = new FileOutputStream(csvFilePath, append);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "GBK");
             BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)) {
            if (StringUtils.isNotBlank(head)) {
                bufferedWriter.write(head);
                bufferedWriter.newLine();
            }
            //把缓存中的数据输出到CSV文件
            bufferedWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeDataStringToCsvWithoutHead(String dataStr, String csvFilePath, boolean append) {
        // 将流写在try里面，当try执行完之后，流会自动关闭
        try (FileOutputStream fileOutputStream = new FileOutputStream(csvFilePath, append);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "GBK");
             BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter)) {
            // 将数据写入到文件中
            if (StringUtils.isNotBlank(dataStr)) {
                bufferedWriter.write(dataStr);
            }

            //把缓存中的数据输出到CSV文件
            bufferedWriter.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}


