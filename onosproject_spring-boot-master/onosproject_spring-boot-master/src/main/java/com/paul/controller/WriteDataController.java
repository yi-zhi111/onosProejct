package com.paul.controller;

import com.paul.utils.sdn.OpenFlowUrlUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author pxj
 * @date 2023-01-04 18:23
 */
@RestController
public class WriteDataController {
    @Autowired
    private OpenFlowUrlUtil util;

    @GetMapping("/writePkin")
    @CrossOrigin
    public void writePkin(){
        String head = "ONOS1,ONOS2,ONOS3,ONOS4,ONOS5";
        String filePath = "./src/main/resources/data/packet_in.csv";
        util.writeDataStringToCsvWithHead(head, filePath, true);
        util.writePkinDataInCsv();
    }

    @GetMapping("/writePkout")
    @CrossOrigin
    public void writePkout(){
        String head = "ONOS1,ONOS2,ONOS3,ONOS4,ONOS5";
        String filePath = "./src/main/resources/data/packet_out.csv";
        util.writeDataStringToCsvWithHead(head, filePath, true);
        util.writePkoutDataInCsv();
    }
}
