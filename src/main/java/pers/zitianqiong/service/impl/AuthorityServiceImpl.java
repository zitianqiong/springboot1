package pers.zitianqiong.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.zitianqiong.domain.Authority;
import pers.zitianqiong.mapper.AuthorityMapper;
import pers.zitianqiong.service.AuthorityService;

/**
 *
 */
@Service
public class AuthorityServiceImpl extends ServiceImpl<AuthorityMapper, Authority>
        implements AuthorityService {
    
}
