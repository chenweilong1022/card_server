package io.renren.modules.ltt.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.EnumUtil;
import io.renren.modules.ltt.enums.Online;
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
public class CdDevicesVO implements Serializable {
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
	private String iccid;
	/**
	 * 是否在线
	 */
	@ApiModelProperty(required=false,value="是否在线")
	private Integer online;
	/**
	 * 是否在线
	 */
	@ApiModelProperty(required=false,value="是否在线")
	private String onlineStr;
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

	public String getOnlineStr() {
		return EnumUtil.queryValueByKey(this.online,Online.values());
	}
}
