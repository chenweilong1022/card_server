package io.renren.modules.ltt.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.EnumUtil;
import io.renren.modules.ltt.enums.Online;
import io.renren.modules.ltt.enums.WorkType;
import io.renren.modules.netty.message.heartbeat.HeartbeatRequest;
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
 * @date 2023-11-18 00:24:28
 */
@Data
@TableName("cd_devices")
@ApiModel("")
@Accessors(chain = true)
public class CdDevicesVO implements Serializable {
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
	private String deviceId;
	/**
	 *
	 */
	@ApiModelProperty(required=false,value="")
	private Integer online;
	/**
	 * 是否在线
	 */
	@ApiModelProperty(required=false,value="是否在线")
	private String onlineStr;
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

	private Integer initSuccessNumber;

	private Integer initTotalNumber;

	/**
	 *
	 */
	@ApiModelProperty(required=false,value="")
	private Integer workType;

	/**
	 *
	 */
	@ApiModelProperty(required=false,value="")
	private String code;
	/**
	 *
	 */
	@ApiModelProperty(required=false,value="")
	private Integer userId;
	/**
	 *
	 */
	@ApiModelProperty(required=false,value="")
	private Integer projectId;
	/**
	 * 分组id
	 */
	@ApiModelProperty(required=false,value="")
	private Integer groupId;
	/**
	 *
	 */
	@ApiModelProperty(required=false,value="")
	private Integer lock;
	/**
	 *
	 */
	@ApiModelProperty(required=false,value="")
	private String phone;
	/**
	 *
	 */
	@ApiModelProperty(required=false,value="")
	private Date phoneGetTime;
	/**
	 *
	 */
	@ApiModelProperty(required=false,value="")
	private HeartbeatRequest heartbeatRequest;

	public String getOnlineStr() {
		return EnumUtil.queryValueByKey(this.online, Online.values());
	}
	public String getWorkTypeStr() {
		return EnumUtil.queryValueByKey(this.workType, WorkType.values());
	}



}
