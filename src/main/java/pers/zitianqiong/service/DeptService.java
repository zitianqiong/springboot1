package pers.zitianqiong.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pers.zitianqiong.domain.Dept;

/**
* @author zitianqiong
* @description 针对表【dept(部门)】的数据库操作Service
* @createDate 2022-07-07 11:20:50
*/
public interface DeptService extends IService<Dept> {

    void trans();
}
