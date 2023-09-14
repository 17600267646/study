package com.person.study.entity.resp;

import lombok.Builder;
import lombok.Data;

import java.util.Map;
import java.util.Set;

@Data
@Builder
public class MyRedisModel {
    /**
     * 所有keys
     */
    private Set<String> stringSet;
    /**
     * 指定hash
     */
    private Map map;
}
