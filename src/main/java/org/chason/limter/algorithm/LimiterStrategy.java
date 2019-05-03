package org.chason.limter.algorithm;

/**
 * 限流算法接口
 * Author: chason
 * Date: 2019/5/3 14:39
 **/
public interface LimiterStrategy {

    boolean getRequestPermission();

}
