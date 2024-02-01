package online.zust.services.chainservice.controller;

import online.zust.common.entity.ResultData;
import online.zust.services.chainservice.entity.ChainConfig;
import online.zust.services.chainservice.entity.TxResponse;
import online.zust.services.chainservice.service.ChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qcqcqc
 */
@RestController
public class ChainController {

    private final ChainService chainService;

    @Autowired
    public ChainController(ChainService chainService) {
        this.chainService = chainService;
    }

    /**
     * 获取链配置
     *
     * @return 链配置
     */
    @GetMapping("/chainConfig")
    public ResultData<ChainConfig> getChainConfig() {
        return ResultData.success(200, "success", chainService.getChainConfig());
    }

    /**
     * 创建合约
     *
     * @return 交易响应
     */
    @GetMapping("/createContract")
    public ResultData<TxResponse> createContract() {
        return ResultData.success(200, "success", chainService.createContract());
    }

    /**
     * 调用合约
     *
     * @return 交易响应
     */
    @GetMapping("/invokeContract")
    public ResultData<TxResponse> invokeContract() {
        return ResultData.success(200, "success", chainService.invokeContract());
    }

    /**
     * 查询合约
     *
     * @return 交易响应
     */
    @GetMapping("/queryContract")
    public ResultData<TxResponse> queryContract() {
        return ResultData.success(200, "success", chainService.queryContract());
    }
}
