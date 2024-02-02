package online.zust.services.annotation;

import com.baomidou.mybatisplus.annotation.TableField;
import online.zust.services.service.EnhanceService;

import java.lang.annotation.*;

/**
 * @author qcqcqc
 * 深度搜索注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@TableField(exist = false)
public @interface DeepSearch {
    String field();

    Class<? extends EnhanceService> service();
}
