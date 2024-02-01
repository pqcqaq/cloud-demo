package online.zust.services.testservice.controller;

import online.zust.common.entity.ResultData;
import online.zust.common.utils.JWTUtils;
import online.zust.services.annotation.AuthNeed;
import online.zust.services.annotation.NoAuth;
import online.zust.services.entity.User;
import online.zust.services.feignclient.clients.AuthClient;
import online.zust.services.entity.dto.LoginParams;
import online.zust.services.entity.dto.RegisterParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qcqcqc
 */
@RestController
@NoAuth
public class TestController {
    @Autowired
    private AuthClient authClient;

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
        return authClient.register(registerParam);
    }

    @GetMapping("/testLogin")
    public ResultData<String> testLogin() {
        LoginParams loginParam = new LoginParams();
        loginParam.setUsername("test");
        loginParam.setPassword("test");
        return authClient.login(loginParam);
    }

    @GetMapping("/testVerifyToken")
    public ResultData<User> testVerifyToken(@RequestParam String token) {
        return authClient.verifyToken(token);
    }

    @GetMapping("/testRefreshToken")
    public ResultData<String> testRefreshToken(@RequestParam String token) {
        return authClient.refreshToken(token);
    }

    @GetMapping("/testVerifyTokenLocal")
    public ResultData<User> testVerifyTokenLocal(@RequestParam String token) {
        return ResultData.success(JWTUtils.getPlayLoad(token, User.class));
    }

    @AuthNeed
    @GetMapping("/testJwtHeader")
    public ResultData<User> testJwtHeader(@RequestParam String jwt) {
        return authClient.testJwtHeader(jwt);
    }
}
