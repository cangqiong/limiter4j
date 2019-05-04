# 项目描述
限流器(RateLimiter)是限制请求的流量以达到保护系统的目的。一个系统的吞吐量是有阈值的，通过限流器设定阈值，当系统的吞吐量到达这个阈值，采用一些策略来限制流量。比如拒绝处理，延迟处理，甚至部分延迟处理。  
本系统使用Java实现限流器，基于对请求数进行限制，直接拒绝处理该请求。采用多种算法对流量进行削峰，让流量尽量平滑访问系统。充分保证系统的可用性。

# 适用场景
适用于微服务应用对外提供接口，也可以是普通JavaWeb应用。

# 项目架构
JDK8+Servlet3.0实现

# 实现算法
基于固定窗口算法、滑动窗口算法、令牌桶算法、漏桶算法等实现。

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
    - key
```
## Filter配置

## 切面配置

# 分布式
本项目可以进行分布式限流，可以将多台服务节点作为整体对访问请求进行限流。

# RoadMap
- 实现不同算法实现限流
- 可以通过Filter配置，切面配置或者Spring boot引入的方式
- 基于Redis实现分布式限流
