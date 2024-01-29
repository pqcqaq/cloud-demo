package online.zust.authservice.controller;

import online.zust.authservice.entity.dto.LoginParam;
import online.zust.authservice.entity.dto.RegisterParam;
import online.zust.authservice.entity.po.User;
import online.zust.authservice.service.UserService;
import online.zust.common.entity.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/register")
    public ResultData<Boolean> register(@RequestBody RegisterParam registerParam) {
        return ResultData.success(200, "注册成功", userService.register(registerParam));
    }

    @PostMapping("/login")
    public ResultData<String> login(@RequestBody LoginParam loginParam) {
        return ResultData.success(200, "登录成功", userService.login(loginParam));
    }

    @GetMapping("/verifyToken")
    public ResultData<User> verifyToken(@RequestParam String token) {
        return ResultData.success(200, "验证成功", userService.verifyToken(token));
    }

    @GetMapping("/refreshToken")
    public ResultData<String> refreshToken(@RequestParam String token) {
        return ResultData.success(200, "刷新成功", userService.refreshToken(token));
    }
}
