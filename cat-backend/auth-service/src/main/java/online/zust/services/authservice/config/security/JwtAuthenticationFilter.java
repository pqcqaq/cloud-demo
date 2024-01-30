package online.zust.services.authservice.config.security;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.extern.slf4j.Slf4j;
import online.zust.services.authservice.entity.UserLogin;
import online.zust.services.authservice.entity.po.User;
import online.zust.services.authservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

/**
 * @author qcqcqc
 * <p>
 * 这个过滤器的主要功能：如果token有效，把用户信息存入Security上下文中
 */

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserService userService;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 从请求头中获取token
        String token = request.getHeader("Authorization");
        // 如果token为空，则放行
        if (Objects.equals(token, "null") || StringUtils.isBlank(token)) {
            filterChain.doFilter(request, response);
            // 不再执行下面的代码
            return;
        }
        // 去redis中查询token是否存在 如果不存在，则抛出异常

        // 如果存在，则获取用户信息
        User user = userService.verifyToken(token);
        // 如果用户信息为空，则抛出异常
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        // 封装成UserLogin对象
        UserLogin userLogin = new UserLogin(user);
        // 存入Security上下文中
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(userLogin.getUser(), null, userLogin.getAuthorities());
        // 存入上下文中
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        // 放行
        filterChain.doFilter(request, response);
    }
}
