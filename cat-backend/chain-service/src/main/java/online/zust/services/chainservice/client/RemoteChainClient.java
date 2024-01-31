package online.zust.services.chainservice.client;

import online.zust.common.entity.ResultData;
import online.zust.services.chainservice.entity.ChainConfig;
import online.zust.services.chainservice.entity.TxResponse;
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
    ResultData<ChainConfig> getChainConfig();

    /**
     * 创建合约
     *
     * @return 创建合约结果
     */
    @GetMapping("/createContract")
    ResultData<TxResponse> createContract();

    /**
     * 调用合约
     *
     * @return 调用合约结果
     */
    @GetMapping("/invokeContract")
    ResultData<TxResponse> invokeContract();

    /**
     * 查询合约
     *
     * @return 查询合约结果
     */
    @GetMapping("/queryContract")
    ResultData<TxResponse> queryContract();
}
