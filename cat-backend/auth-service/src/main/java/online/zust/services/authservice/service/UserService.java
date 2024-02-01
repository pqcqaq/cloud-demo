package online.zust.services.authservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import online.zust.services.authservice.entity.po.User;
import online.zust.services.entity.dto.LoginParams;
import online.zust.services.entity.dto.RegisterParam;

/**
 * @author qcqcqc
 */
public interface UserService extends IService<User> {
    /**
     * 注册
     * @return 是否注册成功
     */
    boolean register(RegisterParam registerParam);

    String login(LoginParams loginParams);

    User verifyToken(String token);

    String refreshToken(String token);

    boolean checkToken(String token);
}
