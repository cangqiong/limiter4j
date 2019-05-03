package org.chason.limter.core;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求限制器
 * 当请求发送过来的时候，通过限流器对请求进行限制
 * Author: chason
 * Date: 2019/5/3 14:06
 **/
public abstract class RequestLimiter {

    /**
     * 获取请求权限
     * @param request
     * @return
     */
    public abstract boolean getRequestPermission(HttpServletRequest request);

}
