package online.zust.services.utils;

import online.zust.services.entity.User;

/**
 * @author qcqcqc
 */
public class RequestHolder {

    private final static ThreadLocal<User> USER_THREAD_LOCAL = new ThreadLocal<>();
    private final static ThreadLocal<String> SERVICE_NAME_THREAD_LOCAL = new ThreadLocal<>();
    private final static ThreadLocal<String> JWT_THREAD_LOCAL = new ThreadLocal<>();


    public static void setUser(User user) {
        USER_THREAD_LOCAL.set(user);
    }

    public static User getUser() {
        return USER_THREAD_LOCAL.get();
    }

    public static void setServiceNameThreadLocal(String name) {
        SERVICE_NAME_THREAD_LOCAL.set(name);
    }

    public static String getServiceNameThreadLocal() {
        return SERVICE_NAME_THREAD_LOCAL.get();
    }

    public static void setJwt(String jwt) {
        JWT_THREAD_LOCAL.set(jwt);
    }

    public static String getJwt() {
        return JWT_THREAD_LOCAL.get();
    }

    public static void remove() {
        USER_THREAD_LOCAL.remove();
        SERVICE_NAME_THREAD_LOCAL.remove();
        JWT_THREAD_LOCAL.remove();
    }
}
