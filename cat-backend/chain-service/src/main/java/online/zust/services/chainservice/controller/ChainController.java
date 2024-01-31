package online.zust.services.chainservice.controller;

import online.zust.common.entity.ResultData;
import online.zust.services.annotation.NoAuth;
import online.zust.services.chainservice.servcice.ChainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qcqcqc
 */
@RestController
@NoAuth
public class ChainController {

    private final ChainService chainService;

    @Autowired
    public ChainController(ChainService chainService) {
        this.chainService = chainService;
    }

    @GetMapping("/chainConfig")
    public ResultData< online.zust.services.chainservice.entity.ChainConfig> getChainConfig() {
        return ResultData.success(200, "success", chainService.getChainConfig());
    }
}
