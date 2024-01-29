package online.zust.authservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import online.zust.authservice.entity.dto.LoginParam;
import online.zust.authservice.entity.dto.RegisterParam;
import online.zust.authservice.entity.po.User;

/**
 * @author qcqcqc
 */
public interface UserService extends IService<User> {
    /**
     * 注册
     * @return 是否注册成功
     */
    boolean register(RegisterParam registerParam);

    String login(LoginParam loginParam);

    User verifyToken(String token);

    String refreshToken(String token);
}
