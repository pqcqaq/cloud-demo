package online.zust.services.utils;

/**
 * @author qcqcqc
 */
public class JwtHolder {
    private final static ThreadLocal<String> JWT_THREAD_LOCAL = new ThreadLocal<>();

    public static void setJwt(String jwt) {
        JWT_THREAD_LOCAL.set(jwt);
    }

    public static String getJwt() {
        return JWT_THREAD_LOCAL.get();
    }

    public static void remove() {
        JWT_THREAD_LOCAL.remove();
    }
}
