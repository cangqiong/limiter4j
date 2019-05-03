package org.chason.limter.core;

import lombok.Builder;
import lombok.Data;

/**
 * 限流器返回值
 * Author: chason
 * Date: 2019/5/3 16:58
 **/
@Data
@Builder
public class LimiterResponse {
    private String code;
    private String msg;

    public static LimiterResponse getDefaultResponse(){
        return LimiterResponse.builder()
                .code("9999")
                .msg("请求被限流，请稍候访问")
                .build();
    }
}
