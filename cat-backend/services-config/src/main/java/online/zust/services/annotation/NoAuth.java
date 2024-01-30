package online.zust.services.annotation;

import java.lang.annotation.*;

/**
 * @author qcqcqc
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface NoAuth {
}
