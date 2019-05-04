package org.chason.limter.algorithm;

import lombok.Builder;
import lombok.Data;

import java.util.concurrent.TimeUnit;

/**
 * 限流算法抽象类
 * Author: chason
 * Date: 2019/5/2 20:34
 **/
@Data
public abstract class AbstractLimiterStrategy implements LimiterStrategy {

    /**
     * 限流对应的key
     */
    protected String key;
    /**
     * 最大访问次数
     */
    protected int limitNum;
    /**
     * 单位时间
     */
    protected long intervalTime;
    /**
     * 时间单位
     */
    protected TimeUnit timeUnit;

    public AbstractLimiterStrategy() {
    }

    public void init(String key, int limitNum, long intervalTime, TimeUnit timeUnit) {
        this.key = key;
        this.limitNum = limitNum;
        this.intervalTime = intervalTime;
        this.timeUnit = timeUnit;
    }

    @Override
    public abstract boolean getRequestPermission();


}
