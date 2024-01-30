package online.zust.services.authservice.utils;

import online.zust.services.authservice.entity.po.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * @author qcqcqc
 */
@Component
public class ContextUtil {
    public static User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!(principal instanceof User user)) {
            return null;
        }
        return user;
    }

    public static User getCurrentUserOrThrow() {
        User currentUser = getCurrentUser();
        if (currentUser == null) {
            throw new RuntimeException("用户未登录");
        }
        return currentUser;
    }
}
