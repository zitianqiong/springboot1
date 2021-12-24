package pers.zitianqiong.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户表
 */
@ApiModel(value = "用户表")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "mybatis-plus.`user`")
public class User implements Serializable {

	/**
	 * 用户id唯一
	 */
	@TableId(value = "id", type = IdType.AUTO)
	@ApiModelProperty(value = "用户id唯一")
	private Long id;

	/**
	 * 昵称
	 */
	@TableField(value = "`name`")
	@ApiModelProperty(value = "昵称")
	private String name;

	/**
	 * 年龄
	 */
	@TableField(value = "age")
	@ApiModelProperty(value = "年龄")
	private Integer age;

	/**
	 * 创建时间
	 */
	@TableField(value = "create_time")
	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	/**
	 * 更新时间
	 */
	@TableField(value = "update_time")
	@ApiModelProperty(value = "更新时间")
	private Date updateTime;

	/**
	 * 乐观锁版本
	 */
	@TableField(value = "version")
	@ApiModelProperty(value = "乐观锁版本")
	private Integer version;

	/**
	 * 是否被删除0：正常，1：删除
	 */
	@TableField(value = "deleted")
	@ApiModelProperty(value = "是否被删除0：正常，1：删除")
	private Integer deleted;

	private static final long serialVersionUID = 1L;

	public int addten(int i){
		return i+10;
	}

	public static double del(double i){
		return i-1.1;
	}

}
