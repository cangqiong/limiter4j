# 项目描述
使用Java实现的限流器，可以防止外部请求将系统压垮，对流量进行整形，让流量尽量平滑访问系统。

# 适用场景
适用于微服务应用对外提供接口，也可以是普通JavaWeb应用。

# 项目架构
JDK8+Servlet3.0实现

# 实现算法
基于滑动窗口算法、令牌桶算法、漏桶算法等实现

# 使用方式
引入当前jar包，添加Filter配置(可以参考Demo配置)或者切面配置。

```java
 @Bean
    public FilterRegistrationBean testFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean(new LimiterFilter());
        registration.addUrlPatterns("/*");
        registration.setName("limiterFilter");
        return registration;
    }
```
# 配置方式

## YAML文件配置
```yaml
limter4j:
  app-name: limter4j-demo #系统应用的名称，唯一
  storage-type: momery
  enable: true
  redis:
    host:
    username:
    password:
  limiters:
    - ALL;200;1;fixed-window #url路径/ALL(全体);流量个数 ;时间单位；限流算法(可以省略)
    - /say;200;1;slide-window
```
## Filter配置

## 切面配置

# 分布式
本项目可以进行分布式限流，可以将多台服务节点作为整体对访问请求进行限流。
