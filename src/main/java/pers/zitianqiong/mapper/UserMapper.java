package pers.zitianqiong.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import pers.zitianqiong.pojo.User;

/**
 * @author zitianqiong
 */
@Mapper
@Repository
public interface UserMapper extends BaseMapper<User> {

}
