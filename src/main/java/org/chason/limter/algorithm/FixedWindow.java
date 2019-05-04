package org.chason.limter.algorithm;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import static org.chason.limter.DataUtils.TIME_FORMATTER_2;
import static org.chason.limter.DataUtils.getDateStrFromLong;

/**
 * 固定窗口算法
 * Author: chason
 * Date: 2019/5/2 20:34
 **/
@Slf4j
public class FixedWindow extends AbstractLimiterStrategy {

    /**
     * 开始时间
     */
    private long startTime;

    /**
     * 当前请求数
     */
    private int curReqNum;

    /**
     * 采用信号量作为线程的处理
     */
    private Semaphore semaphore;

    public FixedWindow() {
    }

    @Override
    public boolean getRequestPermission() {

        long now = System.currentTimeMillis();
        // 超出时间间隔，重新计时
        if ((startTime + timeUnit.toMillis(intervalTime)) < now) {
            startTime = now;
            curReqNum = 0;
            semaphore = new Semaphore(limitNum, true);
        }
        // 获取限流允许的超时时间 TODO优化点
        if (semaphore.tryAcquire()) {
            log.info("起始时间为:{},结束时间为:{},当前时间为：{},当前请求数目为:{},最大请求数为:{}",
                    getDateStrFromLong(startTime).format(TIME_FORMATTER_2),
                    getDateStrFromLong(startTime + timeUnit.toMillis(intervalTime)).format(TIME_FORMATTER_2),
                    getDateStrFromLong(now).format(TIME_FORMATTER_2), limitNum - semaphore.availablePermits(), limitNum);
            return true;
        } else {
            log.info("已经限流中,起始时间为:{},结束时间为:{},当前时间为：{},当前请求数目为:{},最大请求数为:{}",
                    getDateStrFromLong(startTime).format(TIME_FORMATTER_2),
                    getDateStrFromLong(startTime + timeUnit.toMillis(intervalTime)).format(TIME_FORMATTER_2),
                    getDateStrFromLong(now).format(TIME_FORMATTER_2), limitNum - semaphore.availablePermits(), limitNum);
            return false;
        }

    }

}
