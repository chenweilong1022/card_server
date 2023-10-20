package io.renren.modules.ltt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 卡池用户表
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-19 01:16:03
 */
@Data
@TableName("cd_user")
@ApiModel("卡池用户表")
@Accessors(chain = true)
public class CdUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id;主键
	 */
	@TableId(type = IdType.AUTO)
	@ApiModelProperty(required=false,value="id;主键")
	private Integer id;
	/**
	 * 秘钥
	 */
	@ApiModelProperty(required=false,value="秘钥")
	private String token;
	/**
	 * 余额
	 */
	@ApiModelProperty(required=false,value="余额")
	private BigDecimal balance;
	/**
	 * 密码
	 */
	@ApiModelProperty(required=false,value="密码")
	private String password;
	/**
	 * 账号
	 */
	@ApiModelProperty(required=false,value="账号")
	private String account;
	/**
	 * 删除标志
	 */
	@ApiModelProperty(required=false,value="删除标志")
	private Integer deleteFlag;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(required=false,value="创建时间")
	private Date createTime;

}
