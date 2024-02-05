package online.zust.services.carboncal;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author qcqcqc
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "online.zust.services.feignclient")
@EnableDubbo
public class CarbonCalServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarbonCalServiceApplication.class, args);
    }
}
