package online.zust.services.authservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import online.zust.services.authservice.entity.UserLogin;
import online.zust.services.authservice.entity.dto.LoginParam;
import online.zust.services.authservice.entity.dto.RegisterParam;
import online.zust.services.authservice.entity.po.User;
import online.zust.services.authservice.mapper.UserMapper;
import online.zust.services.authservice.service.UserService;
import online.zust.common.exception.ServiceException;
import online.zust.common.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author qcqcqc
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService, UserDetailsService {

    private final UserMapper userMapper;

    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //查询用户信息
        User user = userMapper.selectOne(
                new LambdaQueryWrapper<User>()
                        .eq(User::getUsername, username)
                        .last("limit 1")
        );
        //异常
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("用户名未发现");
        }
        //数据封装为UserDetails返回
        return new UserLogin(user);
    }

    @Override
    public boolean register(RegisterParam registerParam) {
        // 查询用户信息
        User user = userMapper.selectOne(
                new LambdaQueryWrapper<User>()
                        .eq(User::getUsername, registerParam.getUsername())
                        .last("limit 1")
        );
        if (Objects.nonNull(user)) {
            throw new ServiceException("该用户名已存在");
        }
        // 注册
        User newUser = new User();
        newUser.setUsername(registerParam.getUsername());
        // 密码是加密的
        String encode = passwordEncoder.encode(registerParam.getPassword());
        newUser.setPassword(encode);
        ArrayList<String> strings = new ArrayList<>(1);
        strings.add("ROLE_USER");
        newUser.setRoles(strings);
        // 保存
        return this.save(newUser);
    }

    @Override
    public String login(LoginParam loginParam) {
        //获取认证对象
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginParam.getUsername(), loginParam.getPassword());
        //认证
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        //认证失败
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("登录失败");
        }
        UserLogin user = (UserLogin) authenticate.getPrincipal();
        return JWTUtils.createJWT(user.getUser());
    }

    @Override
    public User verifyToken(String token) {
        Boolean b = JWTUtils.checkToken(token);
        if (!b) {
            throw new ServiceException("token验证失败");
        }
        return JWTUtils.getPlayLoad(token, User.class);
    }

    @Override
    public String refreshToken(String token) {
        return JWTUtils.refresh(token);
    }
}
