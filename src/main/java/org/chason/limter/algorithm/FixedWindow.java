package org.chason.limter.algorithm;

import lombok.Data;
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
@Data
@Slf4j
public class FixedWindow implements LimiterStrategy {


    private String url;
    private long startTime;
    private long timeInterval;
    private TimeUnit timeUnit;
    private final int maxReqNum;
    private int curReqNum;

    private Semaphore semaphore;

    public FixedWindow(String url, long timeInterval, TimeUnit timeUnit, int maxReqNum) {
        this.url = url;
        this.timeInterval = timeInterval;
        this.timeUnit = timeUnit;
        this.maxReqNum = maxReqNum;
        startTime = System.currentTimeMillis();
        semaphore = new Semaphore(maxReqNum, true);
    }

    @Override
    public boolean getRequestPermission() {

        long now = System.currentTimeMillis();
        // 超出时间间隔，重新计时 TODO 线程非安全
        if ((startTime + timeUnit.toMillis(timeInterval)) < now) {
            startTime = now;
            curReqNum = 0;
            semaphore = new Semaphore(maxReqNum, true);
        }
        if (semaphore.tryAcquire()) {
            log.info("起始时间为:{},结束时间为:{},当前时间为：{},当前请求数目为:{},最大请求数为:{}",
                    getDateStrFromLong(startTime).format(TIME_FORMATTER_2),
                    getDateStrFromLong(startTime + timeUnit.toMillis(timeInterval)).format(TIME_FORMATTER_2),
                    getDateStrFromLong(now).format(TIME_FORMATTER_2), maxReqNum - semaphore.availablePermits(), maxReqNum);
            return true;
        } else {
            log.info("已经限流中,起始时间为:{},结束时间为:{},当前时间为：{},当前请求数目为:{},最大请求数为:{}",
                    getDateStrFromLong(startTime).format(TIME_FORMATTER_2),
                    getDateStrFromLong(startTime + timeUnit.toMillis(timeInterval)).format(TIME_FORMATTER_2),
                    getDateStrFromLong(now).format(TIME_FORMATTER_2), maxReqNum - semaphore.availablePermits(), maxReqNum);
            return false;
        }

    }

}
