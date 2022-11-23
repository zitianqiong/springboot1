package pers.zitianqiong.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pers.zitianqiong.domain.CustomerAuthority;
import pers.zitianqiong.mapper.CustomerAuthorityMapper;
import pers.zitianqiong.service.CustomerAuthorityService;

/**
 *
 */
@Service
public class CustomerAuthorityServiceImpl extends ServiceImpl<CustomerAuthorityMapper, CustomerAuthority>
        implements CustomerAuthorityService {
    
}
