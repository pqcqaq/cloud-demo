package online.zust.services.testservice.service;

import online.zust.common.entity.ResultData;
import online.zust.services.testservice.entity.LoginParam;
import online.zust.services.testservice.entity.RegisterParam;
import online.zust.services.testservice.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
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

    @GetMapping("/test/jwtHeader")
    ResultData<String> testJwtHeader(@RequestHeader("jwt") String jwt);

}
