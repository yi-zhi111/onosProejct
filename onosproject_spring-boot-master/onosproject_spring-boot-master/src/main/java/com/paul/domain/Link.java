package com.paul.domain;

import lombok.Data;
import lombok.ToString;
import org.springframework.lang.Nullable;


/**
 * @author pxj
 * @date 2022-06-07 16:55
 */
@Data
@ToString
public class Link {
    private String srcDevice;
    private String srcPort;
    private String dstDevice;
    @Nullable
    private String dstPort;

    @Override
    public boolean equals(Object o) {
        Link link = (Link) o;
        if (this.getDstDevice().equals(link.getSrcDevice()) && this.getSrcDevice().equals(link.getDstDevice()))//如果两个个link中源节点和目的节点交叉相同，则为同一个link
            return true;
        return false;
    }


}
