package online.zust.services.authservice.controller;

import online.zust.common.entity.ResultData;
import online.zust.services.annotation.AuthNeed;
import online.zust.services.annotation.NoAuth;
import online.zust.services.authservice.entity.po.User;
import online.zust.services.authservice.service.UserService;
import online.zust.services.entity.dto.RegisterParam;
import online.zust.services.entity.dto.LoginParams;
import online.zust.services.utils.RequestHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author qcqcqc
 */
@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @NoAuth
    @PostMapping("/register")
    public ResultData<Boolean> register(@RequestBody @Validated RegisterParam registerParam) {
        return ResultData.success(200, "注册成功", userService.register(registerParam));
    }

    @NoAuth
    @PostMapping("/login")
    public ResultData<String> login(@RequestBody @Validated LoginParams loginParams) {
        return ResultData.success(200, "登录成功", userService.login(loginParams));
    }

    @NoAuth
    @GetMapping("/verifyToken")
    public ResultData<User> verifyToken(@RequestParam String token) {
        return ResultData.success(200, "验证成功", userService.verifyToken(token));
    }

    @GetMapping("/refreshToken")
    public ResultData<String> refreshToken(@RequestParam String token) {
        return ResultData.success(200, "刷新成功", userService.refreshToken(token));
    }

    @AuthNeed
    @GetMapping("/test/jwtHeader")
    public ResultData<online.zust.services.entity.User> testJwtHeader(@RequestHeader("jwt") String jwt,
                                                                      @RequestHeader("service-name") String serviceName) {
        return ResultData.success(200, serviceName, RequestHolder.getUserOrThrow());
    }

    @NoAuth
    @PostMapping("/checkToken")
    public boolean checkToken(@RequestBody String token) {
        return userService.checkToken(token);
    }
}

