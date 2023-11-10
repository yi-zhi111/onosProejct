package com.paul.domain;

import lombok.Data;
import lombok.ToString;

/**
 * @author pxj
 * @date 2022-12-06 20:37
 */
@Data
@ToString
public class Controller {
    private String ip;
    private String name;
    private boolean isRunning;
}
