package pers.zitianqiong.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.zitianqiong.domain.User;
import pers.zitianqiong.mapper.UserMapper;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {

}

