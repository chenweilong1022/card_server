package io.renren.modules.ltt.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 *
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-11-20 00:37:04
 */
@Data
@TableName("cd_devices")
@ApiModel("设备表")
@Accessors(chain = true)
public class CdDevicesEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId(type = IdType.AUTO)
	@ApiModelProperty(required=false,value="")
	private Integer id;
	/**
	 *
	 */
	@ApiModelProperty(required=false,value="")
	private String iccid;
	/**
	 *
	 */
	@ApiModelProperty(required=false,value="")
	private Integer online;
	/**
	 * 分组id
	 */
	@ApiModelProperty(required=false,value="")
	private Integer groupId;
	/**
	 *
	 */
	@ApiModelProperty(required=false,value="")
	private Integer workType;
	/**
	 *
	 */
	@ApiModelProperty(required=false,value="")
	private Integer deleteFlag;
	/**
	 *
	 */
	@ApiModelProperty(required=false,value="")
	private Date createTime;
	/**
	 *
	 */
	@ApiModelProperty(required=false,value="")
	private String packageVersion;
	/**
	 *
	 */
	@ApiModelProperty(required=false,value="")
	private String number;

}
