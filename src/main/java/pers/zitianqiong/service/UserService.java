package pers.zitianqiong.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.zitianqiong.domain.User;
import pers.zitianqiong.mapper.UserMapper;

import java.util.List;

/**
 * @author zitianqiong
 */
@Service
public class UserService extends ServiceImpl<UserMapper, User> {

	private final UserMapper userMapper;

	@Autowired
	public UserService(UserMapper userMapper){
		this.userMapper = userMapper;
	}

	public List<User> findDeleted(){
		return userMapper.findDeleted();
	}

}
