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
 * 设备表
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-06 23:26:10
 */
@Data
@TableName("cd_devices")
@ApiModel("设备表")
@Accessors(chain = true)
public class CdDevicesUpdateAppDTO extends PageParam implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.AUTO)
	@ApiModelProperty(required=false,value="主键")
	private Integer[] ids;
	/**
	 * 下载路径
	 */
	@ApiModelProperty(required=false,value="下载路径")
	private String httpUrl;
	/**
	 * 更新类型 1:更新选择机器 2:更新全部机器
	 */
	@ApiModelProperty(required=false,value="更新类型")
	private Integer updateType;
}
