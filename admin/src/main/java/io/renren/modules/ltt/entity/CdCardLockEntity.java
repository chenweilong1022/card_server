package io.renren.modules.ltt.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * 卡锁
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-19 23:41:10
 */
@Data
@TableName("cd_card_lock")
@ApiModel("卡锁")
@Accessors(chain = true)
public class CdCardLockEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.AUTO)
	@ApiModelProperty(required=false,value="主键")
	private Integer id;
	/**
	 * 短信
	 */
	@ApiModelProperty(required=false,value="短信")
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private String code;
	/**
	 * 用户id
	 */
	@ApiModelProperty(required=false,value="用户id")
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private Integer userId;
	/**
	 * 项目id
	 */
	@ApiModelProperty(required=false,value="项目id")
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private Integer projectId;
	/**
	 * 设备id
	 */
	@ApiModelProperty(required=false,value="设备id")
	private String deviceId;
	/**
	 * 锁
	 */
	@ApiModelProperty(required=false,value="锁")
	@TableField(value = "`lock`")
	private Integer lock;
	/**
	 * 手机号
	 */
	@ApiModelProperty(required=false,value="手机号")
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private String phone;
	/**
	 * 卡的iccid
	 */
	@ApiModelProperty(required=false,value="卡的iccid")
	@TableField(updateStrategy = FieldStrategy.IGNORED)
	private String iccid;
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
	/**
	 *
	 */
	@ApiModelProperty(required=false,value="")
	private Date phoneGetTime;
}
