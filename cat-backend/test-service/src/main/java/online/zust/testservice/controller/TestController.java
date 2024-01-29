package online.zust.testservice.controller;

import online.zust.common.entity.ResultData;
import online.zust.testservice.entity.LoginParam;
import online.zust.testservice.entity.RegisterParam;
import online.zust.testservice.entity.User;
import online.zust.testservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qcqcqc
 */
@RestController
public class TestController {
    @Autowired
    private AuthService authService;

    @GetMapping("/test")
    public ResultData<String> test() {
        return ResultData.success("test");
    }

    @GetMapping("/test2")
    public String test2() {
        throw new RuntimeException("test-exception");
    }

    @GetMapping("/testRegister")
    public ResultData<Boolean> testRegister() {
        RegisterParam registerParam = new RegisterParam();
        registerParam.setUsername("test");
        registerParam.setPassword("test");
        return authService.register(registerParam);
    }

    @GetMapping("/testLogin")
    public ResultData<String> testLogin() {
        LoginParam loginParam = new LoginParam();
        loginParam.setUsername("test");
        loginParam.setPassword("test");
        return authService.login(loginParam);
    }

    @GetMapping("/testVerifyToken")
    public ResultData<User> testVerifyToken(@RequestParam String token) {
        return authService.verifyToken(token);
    }

    @GetMapping("/testRefreshToken")
    public ResultData<String> testRefreshToken(@RequestParam String token) {
        return authService.refreshToken(token);
    }
}
