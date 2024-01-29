package online.zust.testservice.controller;

import online.zust.common.entity.ResultData;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qcqcqc
 */
@RestController
public class TestController {

    @GetMapping("/test")
    public ResultData<String> test() {
        return ResultData.success("test");
    }

    @GetMapping("/test2")
    public String test2() {
        throw new RuntimeException("test-exception");
    }

}
