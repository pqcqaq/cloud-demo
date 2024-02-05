package online.zust.services.rpc.service;

import com.baomidou.mybatisplus.extension.service.IService;
import online.zust.services.rpc.entity.Demo;

/**
 * @author qcqcqc
 */
public interface DemoService extends IService<Demo> {

    /**
     * hello
     * @return hello
     */
    String hello();
}
