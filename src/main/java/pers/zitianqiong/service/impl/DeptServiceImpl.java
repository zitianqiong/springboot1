package pers.zitianqiong.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import pers.zitianqiong.domain.Dept;
import pers.zitianqiong.service.DeptService;
import pers.zitianqiong.mapper.DeptMapper;
import org.springframework.stereotype.Service;

/**
* @author zitianqiong
* @description 针对表【dept(部门)】的数据库操作Service实现
* @createDate 2022-07-07 11:20:50
*/
@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept>
    implements DeptService{

}




