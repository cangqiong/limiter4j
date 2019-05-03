package org.chason.limter.core;

import org.chason.limter.algorithm.FixedWindow;
import org.chason.limter.algorithm.LimiterStrategy;
import org.chason.limter.config.Limiter;
import org.chason.limter.config.LimiterConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * 限流器实例状态，如：总体访问情况，限流器配置的URL对应的访问量
 * Author: chason
 * Date: 2019/5/3 14:55
 **/
public class LimiterInstance {

    private Map<String, LimiterStrategy> strategyContext = new HashMap<>();

    public LimiterInstance(LimiterConfig limiterConfig) {
        limiterConfig.getLimiterList().forEach((Limiter limiter) -> {
            // TODO 先直接用默认算法
            LimiterStrategy limiterStrategy = new FixedWindow(limiter.getUrl(), limiter.getTime(), limiter.getTimeUnit(), limiter.getLimitNum());
            strategyContext.put(limiterConfig.getAppName() + limiter.getUrl(), limiterStrategy);
        });
    }

    public LimiterStrategy getLimiterStrategy(String requestURI) {
        return strategyContext.get(requestURI);
    }
}
