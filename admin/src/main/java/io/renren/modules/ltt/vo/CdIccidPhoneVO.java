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
 * @date 2023-10-30 00:10:56
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
	 * 创建时间
	 */
	@ApiModelProperty(required=false,value="创建时间")
	private Date createTime;

}
