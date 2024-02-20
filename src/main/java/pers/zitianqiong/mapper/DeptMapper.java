package pers.zitianqiong.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import pers.zitianqiong.domain.Dept;

/**
 * @author zitianqiong
 * @description 针对表【dept(部门)】的数据库操作Mapper
 * @createDate 2022-07-07 11:20:50
 * @Entity pers.zitianqiong.domain.Dept
 */
@Repository
public interface DeptMapper extends BaseMapper<Dept> {

}




