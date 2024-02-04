package online.zust.services.carbon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author qcqcqc
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "online.zust.services.feignclient")
public class CarbonServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(CarbonServiceApplication.class, args);
    }
}
