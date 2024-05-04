package io.renren.modules.ltt.vo;

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
 * iccid手机对接表
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2024-01-16 17:38:19
 */
@Data
@TableName("cd_iccid_phone")
@ApiModel("iccid手机对接表")
@Accessors(chain = true)
public class CdIccidPhoneVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.AUTO)
	@ApiModelProperty(required=false,value="主键")
	private Integer id;
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
	 * 导出状态
	 */
	@ApiModelProperty(required=false,value="导出状态")
	private Integer exportStatus;
	/**
	 * 创建时间
	 */
	@ApiModelProperty(required=false,value="创建时间")
	private Date createTime;
	/**
	 * 拨号信息
	 */
	@ApiModelProperty(required=false,value="拨号信息")
	private String ussdMsg;
	/**
	 * 到期时间
	 */
	@ApiModelProperty(required=false,value="到期时间")
	private Date expireTime;

	/**
	 * 分组id
	 */
	@ApiModelProperty(required=false,value="")
	private Integer groupId;

	/**
	 * 分组id
	 */
	@ApiModelProperty(required=false,value="")
	private String groupName;

	/**
	 * 设备id
	 */
	@ApiModelProperty(required=false,value="设备id")
	private String deviceId;
	/**
	 * 编号
	 */
	@ApiModelProperty(required=false,value="设备编号")
	private String deviceNumber;
}
