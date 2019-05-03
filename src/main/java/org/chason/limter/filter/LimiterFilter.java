package org.chason.limter.filter;

import com.alibaba.fastjson.JSON;
import org.chason.limter.core.DefaultRequestLimiter;
import org.chason.limter.core.LimiterResponse;
import org.chason.limter.core.RequestLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 限流器Fliter
 * Author: chason
 * Date: 2019/5/3 15:32
 **/
public class LimiterFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    private RequestLimiter requestLimiter;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        if (requestLimiter == null) {
            requestLimiter = new DefaultRequestLimiter();
            logger.info("初始化限流器配置:{}", requestLimiter);
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        boolean isPermission = requestLimiter.getRequestPermission(req);
        if (!isPermission) {
            LimiterResponse limiterResponse = LimiterResponse.getDefaultResponse();
            //指定返回的格式为JSON格式
            res.setContentType("application/json;charset=utf-8");
            res.setCharacterEncoding("UTF-8");
            String jsonStr = JSON.toJSONString(limiterResponse);
            PrintWriter out = null;
            out = response.getWriter();
            out.write(jsonStr);
            out.close();
            return;
        } else {
            chain.doFilter(request, response);
            return;
        }
    }

    @Override
    public void destroy() {

    }
}
