package online.zust.services.authservice.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import online.zust.common.entity.ResultCode;
import online.zust.common.entity.ResultData;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author qcqcqc
 */
@Component
@Slf4j
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        String message = authException.getMessage();
        if (message.contains("失效")) {
            response.setStatus(ResultCode.BANDED);
        } else {
            response.setStatus(ResultCode.UNAUTHORIZED);
        }
        String msg = "身份验证失败，无法访问系统资源";
        response.setContentType("application/json;charset=UTF-8");
        ResultData<String> error = ResultData.error(ResultCode.UNAUTHORIZED, msg, message);
        String s = new ObjectMapper().writeValueAsString(error);
        response.getWriter().println(s);
    }

}
