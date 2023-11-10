package com.paul.utils.portListener;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

/**
 * @author pxj
 * @date 2022-06-24 15:35
 */
@Component
public class PortOccupy {
    public HashMap<String, Boolean> isRunning() {
        HashMap<String, Boolean> flag = new HashMap<>();
        Socket socket;
        for (int i = 1; i <= 5; i++) {
            String ip = "172.20.249.20" + i;
            try {
                socket = new Socket(ip, 8181);
                if (socket != null)
                    flag.put(ip, true);
                else{
                    flag.put(ip,false);
                }
            } catch (IOException e) {
                flag.put(ip,false);
            }
        }
        return flag;
    }
}
