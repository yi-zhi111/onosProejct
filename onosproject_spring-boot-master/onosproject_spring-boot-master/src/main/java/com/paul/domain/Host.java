package com.paul.domain;

import lombok.Data;
import lombok.ToString;

/**
 * @author pxj
 * @date 2022-06-07 16:55
 */
@Data
@ToString
public class Host {
    private String Id;
    private String MAC;
    private String IP;
    private String switchId;
    private String switchPort;
}
