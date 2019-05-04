package org.chason.limter.config;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.concurrent.TimeUnit;

@Data
@AllArgsConstructor
public class Limiter {
    /**
     * 限流对应的key
     */
    private String key;
    /**
     * 最大访问次数
     */
    private int limitNum;
    /**
     * 单位时间
     */
    private long intervalTime;
    /**
     * 时间单位
     */
    private TimeUnit timeUnit;
    /**
     * 算法
     */
    private String limitAlgorithm;
}