package pers.zitianqiong.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author zitianqiong
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	/**设置主键生成策略
    AUTO  自动增长
    INPUT 自行输入
    NONE 无状态,自行输入
    UUID 32为uuid字符串
    ID_WORKER 分布式全局唯一ID 长整型(LONG)
    ID_WORKER_STR 分布式全局唯一ID 字符串型(STRING)
    */
	@TableId(type= IdType.AUTO)
	private Integer id;
	private String name;
	private Integer age;
	@TableLogic
	private Integer deleted;
	/**
	 * 乐观锁注解
	 */
	@Version
	private Integer version;
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;
	@TableField(fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;
}
