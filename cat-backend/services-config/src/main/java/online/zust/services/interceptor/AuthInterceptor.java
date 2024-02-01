package online.zust.services.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import online.zust.common.entity.ResultData;
import online.zust.common.utils.JWTUtils;
import online.zust.services.annotation.AuthNeed;
import online.zust.services.annotation.NoAuth;
import online.zust.services.entity.User;
import online.zust.services.utils.RequestHolder;
import org.jetbrains.annotations.Nullable;
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

    private void writeResponse(HttpServletResponse response, String msg, int code) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        ResultData<String> error = ResultData.error(code, msg);
        response.getWriter().write(objectMapper.writeValueAsString(error));
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }
        if (authNeed(handlerMethod)) {
            return authNeed(request, response);
        }
        if (noAuth(handlerMethod)) {
            return true;
        }
        return authNeed(request, response);
    }

    private static boolean authNeed(HandlerMethod handlerMethod) {
        if (handlerMethod.getMethodAnnotation(AuthNeed.class) != null) {
            return true;
        }
        //如果方法名上有指定注解或者类型上有指定注解，就不需要验证
        if (handlerMethod.getBeanType().getAnnotation(AuthNeed.class) != null) {
            return true;
        }
        return false;
    }

    private static boolean noAuth(HandlerMethod handlerMethod) {
        if (handlerMethod.getMethodAnnotation(NoAuth.class) != null) {
            return true;
        }
        //如果方法名上有指定注解或者类型上有指定注解，就不需要验证
        if (handlerMethod.getBeanType().getAnnotation(NoAuth.class) != null) {
            return true;
        }
        return false;
    }

    private boolean authNeed(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 从请求头中获取jwt，service-name两个字段
        String token = request.getHeader("token");
        String jwt = request.getHeader("jwt");
        String serviceName = request.getHeader("service-name");
        // 如果jwt为空，或者service-name为空，或者service-name不是本服务名，返回false
        if (jwt == null && token == null) {
            writeResponse(response, "未找到登录信息！", 404);
            return false;
        }
        if (serviceName == null) {
            serviceName = "__self";
        }

        User playLoad;
        playLoad = getUser(response, token, jwt);
        if (playLoad == null) {
            return false;
        }
        RequestHolder.setJwt(jwt == null ? token : jwt);
        RequestHolder.setUser(playLoad);
        RequestHolder.setServiceNameThreadLocal(serviceName);
        return true;
    }

    @Nullable
    private User getUser(HttpServletResponse response, String token, String jwt) throws IOException {
        User playLoad;
        try {
            playLoad = JWTUtils.getPlayLoad(token, User.class);
            if (playLoad == null) {
                writeResponse(response, "登录信息已过期！", 401);
                return null;
            }
        } catch (Exception e) {
            playLoad = getUserByJwt(response, jwt);
            if (playLoad == null) {
                return null;
            }
        }
        return playLoad;
    }

    @Nullable
    private User getUserByJwt(HttpServletResponse response, String jwt) throws IOException {
        User playLoad;
        try {
            playLoad = JWTUtils.getPlayLoad(jwt, User.class);
            if (playLoad == null) {
                writeResponse(response, "登录信息无效！", 403);
                return null;
            }
        } catch (Exception ex) {
            writeResponse(response, "登录信息无效！", 403);
            return null;
        }
        return playLoad;
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
