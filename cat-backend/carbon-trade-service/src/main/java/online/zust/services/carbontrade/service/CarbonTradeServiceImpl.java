package online.zust.services.carbontrade.service;

import online.zust.services.rpc.service.CarbonTradeService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author qcqcqc
 */
@Service
@DubboService
public class CarbonTradeServiceImpl implements CarbonTradeService {
    @Autowired
    private CompanyInfoService companyInfoService;
    @Autowired
    private IndustryService industryService;
    @Autowired
    private ProductInfoService productInfoService;
    @Autowired
    private OrderInfoService orderInfoService;
}
