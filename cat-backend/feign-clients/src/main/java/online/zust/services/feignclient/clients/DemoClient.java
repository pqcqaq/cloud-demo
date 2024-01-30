package online.zust.services.feignclient.clients;

import online.zust.common.entity.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author qcqcqc
 */
@FeignClient("demo-service")
public interface DemoClient {
    /**
     * 测试
     *
     * @return 测试结果
     */
    @GetMapping("/demo")
    ResultData<String> demo();
}
