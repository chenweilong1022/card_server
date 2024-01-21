package io.renren.modules.ltt.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.base.dto.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

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
public class CdIccidPhoneDTO extends PageParam implements Serializable {
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
	 * 结束时间
	 */
	@ApiModelProperty(required=false,value="结束时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endTime;

}
