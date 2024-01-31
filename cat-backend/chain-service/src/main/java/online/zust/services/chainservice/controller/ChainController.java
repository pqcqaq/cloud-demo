package online.zust.services.chainservice.controller;

import online.zust.common.entity.ResultData;
import online.zust.services.annotation.NoAuth;
import online.zust.services.chainservice.client.RemoteChainClient;
import online.zust.services.chainservice.entity.TxResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qcqcqc
 */
@RestController
@NoAuth
public class ChainController {

    private final RemoteChainClient remoteChainClient;

    @Autowired
    public ChainController(RemoteChainClient remoteChainClient) {
        this.remoteChainClient = remoteChainClient;
    }

    @GetMapping("/chainConfig")
    public ResultData<online.zust.services.chainservice.entity.ChainConfig> getChainConfig() {
        return remoteChainClient.getChainConfig();
    }

    /**
     * 创建合约
     *
     * @return 创建合约结果
     */
    @GetMapping("/createContract")
    ResultData<TxResponse> createContract() {
        return remoteChainClient.createContract();
    }

    /**
     * 调用合约
     *
     * @return 调用合约结果
     */
    @GetMapping("/invokeContract")
    ResultData<TxResponse> invokeContract() {
        return remoteChainClient.invokeContract();
    }

    /**
     * 查询合约
     *
     * @return 查询合约结果
     */
    @GetMapping("/queryContract")
    ResultData<TxResponse> queryContract() {
        return remoteChainClient.queryContract();
    }

}
