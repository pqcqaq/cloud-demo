package online.zust.services.interceptor;

import online.zust.common.utils.JWTUtils;
import online.zust.services.annotation.NoAuth;
import online.zust.services.entity.User;
import online.zust.services.utils.RequestHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author qcqcqc
 */
public class AuthInterceptor implements HandlerInterceptor {

    private void writeResponse(HttpServletResponse response, String msg) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(msg);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (handlerMethod.getMethodAnnotation(NoAuth.class) != null) {
            return true;
        }
        //如果方法名上有指定注解或者类型上有指定注解，就不需要验证
        if (handlerMethod.getBeanType().getAnnotation(NoAuth.class) != null) {
            return true;
        }

        // 从请求头中获取jwt，service-name两个字段
        String token = request.getHeader("token");
        String jwt = request.getHeader("jwt");
        String serviceName = request.getHeader("service-name");
        // 如果jwt为空，或者service-name为空，或者service-name不是本服务名，返回false
        if (jwt == null && token == null) {
            writeResponse(response, "jwt is null");
            return false;
        }
        if (serviceName == null) {
            serviceName = "__self";
        }

        User playLoad = JWTUtils.getPlayLoad(jwt, User.class);
        if (playLoad == null) {
            playLoad = JWTUtils.getPlayLoad(token, User.class);
            if (playLoad == null) {
                writeResponse(response, "jwt is invalid");
                return false;
            }
        }
        RequestHolder.setJwt(jwt == null ? token : jwt);
        RequestHolder.setUser(playLoad);
        RequestHolder.setServiceNameThreadLocal(serviceName);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        RequestHolder.remove();
    }
}
