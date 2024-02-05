package online.zust.services.rpc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import online.zust.common.entity.ResultData;
import online.zust.services.rpc.entity.Demo;

/**
 * @author qcqcqc
 */
public interface DemoService extends IService<Demo> {

    String hello();
}
