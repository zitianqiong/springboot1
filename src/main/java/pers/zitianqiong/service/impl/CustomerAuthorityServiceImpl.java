package pers.zitianqiong.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import pers.zitianqiong.domain.CustomerAuthority;
import pers.zitianqiong.service.CustomerAuthorityService;
import pers.zitianqiong.mapper.CustomerAuthorityMapper;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class CustomerAuthorityServiceImpl extends ServiceImpl<CustomerAuthorityMapper, CustomerAuthority>
    implements CustomerAuthorityService{

}
