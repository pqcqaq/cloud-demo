package online.zust.testservice.service;

import feign.Body;
import feign.Headers;
import feign.Param;
import online.zust.common.entity.ResultData;
import online.zust.testservice.entity.LoginParam;
import online.zust.testservice.entity.RegisterParam;
import online.zust.testservice.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author qcqcqc
 */
@FeignClient("auth-service")
public interface AuthService {

    @PostMapping("/register")
    ResultData<Boolean> register(RegisterParam registerParam);

    @PostMapping("/login")
    ResultData<String> login(LoginParam loginParam);

    @GetMapping("/verifyToken")
    ResultData<User> verifyToken(@RequestParam("token") String token);

    @GetMapping("/refreshToken")
    ResultData<String> refreshToken(@RequestParam("token") String token);

}
