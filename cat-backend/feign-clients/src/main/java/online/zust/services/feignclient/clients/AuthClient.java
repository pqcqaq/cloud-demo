package online.zust.services.feignclient.clients;

import online.zust.common.entity.ResultData;
import online.zust.services.entity.User;
import online.zust.services.entity.dto.LoginParams;
import online.zust.services.entity.dto.RegisterParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * @author qcqcqc
 */
@FeignClient("auth-service")
public interface AuthClient {

    /**
     * 注册
     *
     * @param registerParam 注册参数
     * @return 注册结果
     */
    @PostMapping("/register")
    ResultData<Boolean> register(@RequestBody RegisterParam registerParam);

    /**
     * 登录
     *
     * @param loginParam 登录参数
     * @return 登录结果
     */
    @PostMapping("/login")
    ResultData<String> login(@RequestBody LoginParams loginParam);

    /**
     * 验证token
     *
     * @param token token
     * @return 验证结果
     */
    @GetMapping("/verifyToken")
    ResultData<User> verifyToken(@RequestParam("token") String token);

    /**
     * 刷新token
     *
     * @param token token
     * @return 刷新结果
     */
    @GetMapping("/refreshToken")
    ResultData<String> refreshToken(@RequestParam("token") String token);

    /**
     * 测试jwtHeader
     *
     * @param jwt jwt
     * @return 测试结果
     */
    @GetMapping("/test/jwtHeader")
    ResultData<User> testJwtHeader();

    /**
     * 检查token
     *
     * @param token token
     * @return 检查结果
     */
    @PostMapping("/checkToken")
    boolean checkToken(@RequestBody String token);

}
