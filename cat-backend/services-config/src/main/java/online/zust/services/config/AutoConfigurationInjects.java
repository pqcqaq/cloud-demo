package online.zust.services.config;

import online.zust.services.exception.GlobalExceptionHandler;
import online.zust.services.utils.SpringContextUtil;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

/**
 * @author qcqcqc
 */
@Configuration
@EnableDiscoveryClient
@EnableAsync
public class AutoConfigurationInjects {
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public FeignRequestConfiguration feignRequestConfiguration() {
        return new FeignRequestConfiguration();
    }

    @Bean
    public FeignResponseErrorDecoder feignResponseErrorDecoder() {
        return new FeignResponseErrorDecoder();
    }

    @Bean
    public WebMvcConfiguer webMvcConfiguer() {
        return new WebMvcConfiguer();
    }

    @Bean
    public MyBatisMetaObjectHandler myBatisMetaObjectHandler() {
        return new MyBatisMetaObjectHandler();
    }

    @Bean
    public MyBatisPlusConfig myBatisPlusConfig() {
        return new MyBatisPlusConfig();
    }

    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Bean
    public SpringContextUtil springContextUtil() {
        return new SpringContextUtil();
    }

    @Bean
    public ContextRefreshListener contextRefreshListener() {
        return new ContextRefreshListener();
    }

}
