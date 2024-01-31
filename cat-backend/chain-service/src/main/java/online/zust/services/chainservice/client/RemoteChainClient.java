package online.zust.services.chainservice.client;

import online.zust.services.chainservice.entity.ChainConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author qcqcqc
 */
@FeignClient(value = "remote-chain-service", name = "remote-chain-service", url = "${chain.remote-address}")
public interface RemoteChainClient {
    /**
     * 获取链配置
     *
     * @return 链配置
     */
    @GetMapping("/chainConfig")
    ChainConfig getChainConfig();
}
