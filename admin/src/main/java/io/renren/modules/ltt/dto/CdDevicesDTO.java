package io.renren.modules.ltt.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.base.dto.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 *
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-11-20 00:37:04
 */
@Data
@TableName("cd_devices")
@ApiModel("")
@Accessors(chain = true)
public class CdDevicesDTO extends PageParam implements Serializable {
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
	private List<Integer> ids;
	/**
	 *
	 */
	@ApiModelProperty(required=false,value="")
	private String iccid;
	/**
	 *
	 */
	@ApiModelProperty(required=false,value="")
	private String phone;
	/**
	 *
	 */
	@ApiModelProperty(required=false,value="")
	private Integer online;
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
	 * 分组id
	 */
	@ApiModelProperty(required=false,value="")
	private Integer groupId;
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
	/**
	 * 板子
	 */
	@ApiModelProperty(required=false,value="板子")
	private Integer boardIndexed;
	/**
	 * 卡下标
	 */
	@ApiModelProperty(required=false,value="卡下标")
	private Integer indexed;
}
