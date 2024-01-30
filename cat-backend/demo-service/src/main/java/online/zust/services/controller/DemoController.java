package online.zust.services.controller;

import online.zust.common.entity.ResultData;
import online.zust.services.annotation.NoAuth;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qcqcqc
 */
@RestController
@NoAuth
public class DemoController {

    @GetMapping("/demo")
    public ResultData<String> demo() {
        return ResultData.success("Hello World!");
    }
}
