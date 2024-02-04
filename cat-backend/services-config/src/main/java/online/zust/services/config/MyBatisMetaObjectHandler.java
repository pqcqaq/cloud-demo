package online.zust.services.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import online.zust.services.entity.User;
import online.zust.services.utils.RequestHolder;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author qcqcqc
 */
@Component
@ConditionalOnMissingBean(value = MetaObjectHandler.class)
public class MyBatisMetaObjectHandler implements MetaObjectHandler {

    private Long getUserId() {
        User user = RequestHolder.getUser();
        return user == null ? 0L : user.getId();
    }

    /**
     * 自定义插入时填充规则
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        // 注意是类属性字段名称，不是表字段名称
        this.setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("create_by", getUserId(), metaObject);
    }

    /**
     * 自定义更新时填充规则
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        // 注意是类属性字段名称，不是表字段名称
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        this.setFieldValByName("updateBy", getUserId(), metaObject);
    }

}
