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

/**
 * 卡
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-06 23:26:10
 */
@Data
@TableName("cd_card")
@ApiModel("卡")
@Accessors(chain = true)
public class CdCardDTO extends PageParam implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.AUTO)
	@ApiModelProperty(required=false,value="主键")
	private Integer id;
	/**
	 * 设备id
	 */
	@ApiModelProperty(required=false,value="设备id")
	private String deviceId;
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
	/**
	 * 手机号
	 */
	@ApiModelProperty(required=false,value="手机号")
	private String phone;
	/**
	 * 卡的iccid
	 */
	@ApiModelProperty(required=false,value="卡的iccid")
	private String iccid;
	/**
	 * 删除标志
	 */
	@ApiModelProperty(required=false,value="删除标志")
	private Integer deleteFlag;
	/**
	 * 使用状态
	 */
	@ApiModelProperty(required=false,value="使用状态")
	private Integer useStatus;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(required=false,value="创建时间")
	private Date createTime;

}
