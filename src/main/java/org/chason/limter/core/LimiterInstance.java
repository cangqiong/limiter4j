package org.chason.limter.core;

import org.chason.limter.algorithm.AbstractLimiterStrategy;
import org.chason.limter.algorithm.LimiterStrategy;
import org.chason.limter.config.Limiter;
import org.chason.limter.config.LimiterConfig;
import org.chason.limter.config.LimiterContext;

import java.util.HashMap;
import java.util.Map;

/**
 * 限流器实例状态，如：总体访问情况，限流器配置的URL对应的访问量
 * Author: chason
 * Date: 2019/5/3 14:55
 **/
public class LimiterInstance {

    private LimiterContext limiterContext = new LimiterContext();

    /**
     * 限流实例上下文
     */
    private Map<String, AbstractLimiterStrategy> strategyInstanceContext = new HashMap<>();

    public LimiterInstance(LimiterConfig limiterConfig) {
        limiterConfig.getLimiterList().forEach((Limiter limiter) -> {
            AbstractLimiterStrategy limiterStrategy = limiterContext.getLimiterStrategy(limiter.getLimitAlgorithm());
            limiterStrategy.init(limiter.getKey(), limiter.getLimitNum(), limiter.getIntervalTime(), limiter.getTimeUnit());
            strategyInstanceContext.put(limiterConfig.getAppName() + limiter.getKey(),
                    limiterStrategy);
        });
    }

    public LimiterStrategy getLimiterStrategy(String requestURI) {
        return strategyInstanceContext.get(requestURI);
    }
}
