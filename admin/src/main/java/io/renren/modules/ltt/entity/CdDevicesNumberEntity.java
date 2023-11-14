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
 * 设备编号对应表
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-11-14 21:40:08
 */
@Data
@TableName("cd_devices_number")
@ApiModel("设备编号对应表")
@Accessors(chain = true)
public class CdDevicesNumberEntity implements Serializable {
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
	 * 编号
	 */
	@ApiModelProperty(required=false,value="编号")
	private String number;
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
