package org.chason.limter.config;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.chason.limter.algorithm.AbstractLimiterStrategy;
import org.chason.limter.algorithm.FixedWindow;

import java.util.Map;

/**
 * 限流器类型上下文
 * Author: chason
 * Date: 2019/5/4 18:07
 **/
@Slf4j
public class LimiterContext {

    /**
     * 策略类型上下文
     */
    private Map<String, Class> limiterTypeContext;

    {
        limiterTypeContext = Maps.newHashMapWithExpectedSize(4);
        limiterTypeContext.put("fixed-window", FixedWindow.class);
    }

    /**
     * 默认限流算法
     */
    private AbstractLimiterStrategy defaultLimiterStrategy = new FixedWindow();

    public AbstractLimiterStrategy getLimiterStrategy(String strategyType) {
        Class clazz = limiterTypeContext.get(strategyType);
        if (clazz == null) {
            log.info("设置对应的限流算法不存在或者没有设置，默认设置固定窗口算法");
            return defaultLimiterStrategy;
        }
        try {
            AbstractLimiterStrategy strategy = (AbstractLimiterStrategy) clazz.newInstance();
            return strategy;
        } catch (InstantiationException e) {
            log.warn("实例化策略类失败：{}", clazz);
            return defaultLimiterStrategy;
        } catch (IllegalAccessException e) {
            log.warn("无法访问该策略类：{}", clazz);
            return defaultLimiterStrategy;
        }
    }
}
