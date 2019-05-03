package org.chason.limter.config;

import lombok.Builder;
import lombok.Data;
import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 请求过滤规则
 * Author: chason
 * Date: 2019/5/3 14:15
 **/
@Data
@Builder
public class LimiterConfig {

    /**
     * 应用名称
     */
    private String appName;

    /**
     * 限流信息存储类型：momery/redis
     */
    private String storageType;

    private String redisHost;

    private String redisUsername;

    private String redisPass;

    private List<Limiter> limiterList;

    private boolean enable;

    private static final String LIMITER_CONFIG_NAME = "limiter-config.yaml";
    private static final String REDIS_TYPE = "redis";
    private static final String SPLIT_FLAG = ";";

    public static LimiterConfig loadConfig(String yamlFile) {
        if (yamlFile == null) {
            yamlFile = LIMITER_CONFIG_NAME;
        }
        Yaml yaml = new Yaml();
        InputStream inputStream = LimiterConfig.class.getClassLoader()
                .getResourceAsStream(yamlFile);
        Map<String, Object> config = yaml.loadAs(inputStream, Map.class);
        Map<String, Object> limiterConfigMap = (Map<String, Object>) config.get("limter4j");

        List<String> limiters = (List<String>) limiterConfigMap.get("limiters");

        LimiterConfig limiterConfig = LimiterConfig.builder()
                .appName((String) limiterConfigMap.get("app-name"))
                .storageType((String) limiterConfigMap.get("storage-type"))
                .enable((Boolean) limiterConfigMap.get("enable"))
                .build();
        if (REDIS_TYPE.equals(limiterConfig.getStorageType())) {
            if (limiterConfigMap.get("redis") == null) {
                throw new RuntimeException("Redis配置不能为空");
            }
            limiterConfig.setRedisHost((String) limiterConfigMap.get("host"));
            limiterConfig.setRedisUsername((String) limiterConfigMap.get("username"));
            limiterConfig.setRedisPass((String) limiterConfigMap.get("password"));
        }
        List<Limiter> limiterList = limiters.stream().map(str -> {
            String[] arr = str.split(SPLIT_FLAG);
            Limiter limiter = new Limiter(arr[0], Integer.parseInt(arr[1]),
                    Long.parseLong(arr[2]), TimeUnit.SECONDS, arr[3]);
            return limiter;
        }).collect(Collectors.toList());
        limiterConfig.setLimiterList(limiterList);
        return limiterConfig;
    }

}
