package com.paul.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author pxj
 * @date 2022-06-07 20:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Topology {
    private List<Link> linkList;
    private List<Host> hostList;
    private List<Switch> switchList;
}
