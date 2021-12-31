package pers.zitianqiong.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import pers.zitianqiong.domain.User;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
	@Select("select * from user where deleted = 1")
	List<User> findDeleted();
}
