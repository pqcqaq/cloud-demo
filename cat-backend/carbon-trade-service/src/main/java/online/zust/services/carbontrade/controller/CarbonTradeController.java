package online.zust.services.carbontrade.controller;

import online.zust.services.rpc.service.CarbonTradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qcqcqc
 */
@RestController
public class CarbonTradeController {
    @Autowired
    private CarbonTradeService carbonTradeService;
}
