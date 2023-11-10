package com.paul.csv;

import org.junit.platform.commons.util.StringUtils;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;

/**
 * @author pxj
 * @date 2022-12-02 14:00
 */
@SpringBootTest
public class CsvTest {
    public static void main(String[] args) {
        getCsvDataStr getCsvDataStr = new getCsvDataStr();
        String head = "ONOS1,ONOS2,ONOS3,ONOS4,ONOS5";
        String filePath = "./src/main/resources/data/packet_in.csv";
        writeDataStringToCsvWithHead(head, filePath, true);
        Thread thread = new Thread(getCsvDataStr);
        thread.start();
    }

    public static void writeDataStringToCsvWithHead(String head, String csvFilePath, boolean append) {
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

    public static void writeDataSTringToCsvWithoutHead(String dataStr, String csvFilePath, boolean append) {
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

    /**
     * 获取写入CSV的数据
     *
     * @return String 写入CSV的内容拼装成一个字符串的
     */
    private static class getCsvDataStr implements Runnable {
        @Override
        public void run() {
            int i = 1;
            String filePath1 = "./src/main/resources/data/packet_in.csv";
            while (true) {
                StringBuilder sb = new StringBuilder();
                String dataStr = sb.append(String.format("%d,%d,%d,%d,%d", i, i, i, i, i)).append('\r').append('\n').toString();
                writeDataSTringToCsvWithoutHead(dataStr, filePath1, true);
                i++;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

        }
    }


}
