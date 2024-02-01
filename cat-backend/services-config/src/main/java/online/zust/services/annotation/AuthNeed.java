package online.zust.services.annotation;

import java.lang.annotation.*;

/**
 * @author qcqcqc
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthNeed {
}
