package online.zust.authservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import online.zust.authservice.entity.po.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author qcqcqc
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
