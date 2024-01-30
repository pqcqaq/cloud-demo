package online.zust.services.authservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.zust.services.authservice.entity.po.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author qcqcqc
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
