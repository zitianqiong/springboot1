package pers.zitianqiong.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import pers.zitianqiong.domain.Authority;
import pers.zitianqiong.service.AuthorityService;
import pers.zitianqiong.mapper.AuthorityMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, Authority>
    implements AuthorityService{

}
