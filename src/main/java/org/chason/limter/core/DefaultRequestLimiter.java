package org.chason.limter.core;

import lombok.ToString;
import org.chason.limter.algorithm.LimiterStrategy;
import org.chason.limter.config.Limiter;
import org.chason.limter.config.LimiterConfig;
import org.chason.limter.config.LimiterConstans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 默认请求限制器
 * 当请求发送过来的时候，通过限流器对请求进行限制
 * Author: chason
 * Date: 2019/5/3 14:06
 **/
@ToString
public class DefaultRequestLimiter extends RequestLimiter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private LimiterConfig limiterConfig;

    private LimiterInstance limiterInstance;

    public DefaultRequestLimiter() {
        this.limiterConfig = LimiterConfig.loadConfig(null);
        init(limiterConfig);
    }

    public DefaultRequestLimiter(LimiterConfig limiterConfig) {
        this.limiterConfig = limiterConfig;
        init(limiterConfig);
    }

    private void init(LimiterConfig limiterConfig) {
        if (limiterConfig.isEnable()) {
            List<Limiter> limiterList = limiterConfig.getLimiterList();
            if (limiterList != null && !limiterList.isEmpty()) {
                limiterInstance = new LimiterInstance(limiterConfig);
            }
        }
    }

    /**
     * 获取请求权限
     *
     * @param request
     * @return
     */
    public boolean getRequestPermission(HttpServletRequest request) {

        if (!limiterConfig.isEnable() || limiterConfig.getLimiterList() == null || limiterConfig.getLimiterList().isEmpty()) {
            logger.info("限流器配置未生效:{}", limiterConfig);
            return true;
        }

        // 判断全局是否超出限制
        boolean globalResult = tryGlobalRequestPermission(request);

        if (!globalResult) {
            logger.info("系统总体访问量已经超出了最大限定值");
            return false;
        }
        // 对当前请求进行限流
        return tryCurrentRequestPermission(request);
    }

    private boolean tryCurrentRequestPermission(HttpServletRequest request) {
        LimiterStrategy limiterStrategy = limiterInstance.getLimiterStrategy(limiterConfig.getAppName()+request.getRequestURI());
        if (limiterStrategy == null) {
            logger.debug("该请求路径并未加入限流配置:{}", request.getRequestURI());
            return true;
        }

        return limiterStrategy.getRequestPermission();
    }

    private boolean tryGlobalRequestPermission(HttpServletRequest request) {
        LimiterStrategy limiterStrategy = limiterInstance.getLimiterStrategy(LimiterConstans.ALL);
        if (limiterStrategy == null) {
            logger.debug("整体应用并未加入限流配置:ALL");
            return true;
        }

        return limiterStrategy.getRequestPermission();
    }

}
