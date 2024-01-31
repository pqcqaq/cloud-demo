package online.zust.services.chainservice.controller;

import online.zust.services.annotation.NoAuth;
import online.zust.services.chainservice.entity.ChainConfig;
import online.zust.services.chainservice.entity.ResultData;
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

    @GetMapping("/chainConfig")
    public ResultData<ChainConfig> getChainConfig() {
        return ResultData.success(200, "success", chainService.getChainConfig());
    }

    @GetMapping("/createContract")
    public ResultData<TxResponse> createContract() {
        return ResultData.success(200, "success", chainService.createContract());
    }

    @GetMapping("/invokeContract")
    public ResultData<TxResponse> invokeContract() {
        return ResultData.success(200, "success", chainService.invokeContract());
    }

    @GetMapping("/queryContract")
    public ResultData<TxResponse> queryContract() {
        return ResultData.success(200, "success", chainService.queryContract());
    }
}
