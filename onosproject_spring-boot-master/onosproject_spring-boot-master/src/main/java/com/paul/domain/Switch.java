package com.paul.domain;

import lombok.Data;
import lombok.ToString;

/**
 * @author pxj
 * @date 2022-06-07 16:55
 */
@Data
@ToString
public class Switch {
    private String Id;
    private boolean available;
    private String role;
    private String switchType;//普通交换机还是ovs交换机
    private String protocol;
}
