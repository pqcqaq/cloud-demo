package online.zust.services.testservice.controller;

import online.zust.common.entity.ResultData;
import online.zust.common.utils.JWTUtils;
import online.zust.services.annotation.AuthNeed;
import online.zust.services.annotation.NoAuth;
import online.zust.services.entity.User;
import online.zust.services.entity.dto.LoginParams;
import online.zust.services.entity.dto.RegisterParam;
import online.zust.services.feignclient.clients.AuthClient;
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

    /**
     * 测试
     *
     * @return test
     */
    @GetMapping("/test")
    public ResultData<String> test() {
        return ResultData.success("test");
    }

    /**
     * 测试异常
     *
     * @return test
     */
    @GetMapping("/test2")
    public String test2() {
        throw new RuntimeException("test-exception");
    }

    /**
     * 测试注册
     *
     * @return test
     */
    @GetMapping("/testRegister")
    public ResultData<Boolean> testRegister() {
        RegisterParam registerParam = new RegisterParam();
        registerParam.setUsername("test");
        registerParam.setPassword("test");
        return authClient.register(registerParam);
    }

    /**
     * 测试登录
     *
     * @return test
     */
    @GetMapping("/testLogin")
    public ResultData<String> testLogin() {
        LoginParams loginParam = new LoginParams();
        loginParam.setUsername("test");
        loginParam.setPassword("test");
        return authClient.login(loginParam);
    }

    /**
     * 测试验证token-获取用户信息
     *
     * @param token token
     * @return test
     */
    @GetMapping("/testVerifyToken")
    public ResultData<User> testVerifyToken(@RequestParam String token) {
        return authClient.verifyToken(token);
    }

    /**
     * 测试刷新token
     *
     * @param token token
     * @return test
     */
    @GetMapping("/testRefreshToken")
    public ResultData<String> testRefreshToken(@RequestParam String token) {
        return authClient.refreshToken(token);
    }

    /**
     * 测试本地验证token-获取用户信息
     *
     * @param token token
     * @return test
     */
    @GetMapping("/testVerifyTokenLocal")
    public ResultData<User> testVerifyTokenLocal(@RequestParam String token) {
        return ResultData.success(JWTUtils.getPlayLoad(token, User.class));
    }

    /**
     * 测试feign请求头携带jwt
     *
     * @return test
     */
    @AuthNeed
    @GetMapping("/testJwtHeader")
    public ResultData<User> testJwtHeader() {
        return authClient.testJwtHeader();
    }
}
