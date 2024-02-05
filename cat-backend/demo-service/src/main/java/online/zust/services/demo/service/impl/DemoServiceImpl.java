package online.zust.services.demo.service.impl;

import online.zust.common.exception.ServiceException;
import online.zust.services.EnhanceService;
import online.zust.services.demo.mapper.DemoMapper;
import online.zust.services.rpc.entity.Demo;
import online.zust.services.rpc.service.DemoService;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Service;

/**
 * @author qcqcqc
 */
@Service
@DubboService
public class DemoServiceImpl extends EnhanceService<DemoMapper, Demo> implements DemoService {

    @Override
    public String hello() {
        return "Hello World!";
    }

    @Override
    public String testError() {
        throw new ServiceException("test error");
    }
}
