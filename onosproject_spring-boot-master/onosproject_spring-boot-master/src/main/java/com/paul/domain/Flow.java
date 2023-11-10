package com.paul.domain;

import lombok.Data;
import lombok.ToString;

/**
 * @author pxj
 * @date 2022-06-29 23:05
 */
@Data
@ToString
public class Flow {
    private String Id;
    private String tableId;
    private String gourId;
    private int priority;
    private String deviceId;
    private String state;
    private int packets;
    private String type;
    private String ethType;

}
