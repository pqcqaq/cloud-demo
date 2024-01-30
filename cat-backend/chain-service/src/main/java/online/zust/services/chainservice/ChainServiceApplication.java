package online.zust.services.chainservice;

import online.zust.services.chainservice.client.RemoteChainClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author qcqcqc
 */
@SpringBootApplication
@EnableFeignClients(basePackages = "online.zust.services.feignclient", clients = RemoteChainClient.class)
public class ChainServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChainServiceApplication.class, args);
    }
}
