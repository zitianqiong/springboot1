package pers.zitianqiong.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pers.zitianqiong.domain.Dept;
import pers.zitianqiong.domain.status;
import pers.zitianqiong.mapper.DeptMapper;
import pers.zitianqiong.service.DeptService;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @author zitianqiong
 * @description 针对表【dept(部门)】的数据库操作Service实现
 * @createDate 2022-07-07 11:20:50
 */
@Service
@Slf4j
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept>
        implements DeptService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void trans(){
        Dept dept = new Dept();
        dept.setDeptName("test");
        dept.setDeptAccont(BigDecimal.ONE);
        dept.setStuts(status.NORMAL);
        dept.setDatestate(LocalDate.now());
        save(dept);
        long count = count();
        log.info("添加后数：{}，然后抛异常", count);
        throw new NullPointerException();
    }
}




