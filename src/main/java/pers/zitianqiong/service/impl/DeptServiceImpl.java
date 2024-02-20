package pers.zitianqiong.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pers.zitianqiong.domain.Dept;
import pers.zitianqiong.mapper.DeptMapper;
import pers.zitianqiong.service.DeptService;

/**
 * @author zitianqiong
 * @description 针对表【dept(部门)】的数据库操作Service实现
 * @createDate 2022-07-07 11:20:50
 */
@Service
@Slf4j
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept>
        implements DeptService {

}




