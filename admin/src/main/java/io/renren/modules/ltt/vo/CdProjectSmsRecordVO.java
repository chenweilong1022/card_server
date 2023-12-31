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
 * 项目短信记录
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-20 00:54:08
 */
@Data
@TableName("cd_project_sms_record")
@ApiModel("项目短信记录")
@Accessors(chain = true)
public class CdProjectSmsRecordVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.AUTO)
	@ApiModelProperty(required=false,value="主键")
	private Integer id;
	/**
	 * 缓存的key
	 */
	@ApiModelProperty(required=false,value="缓存的key")
	private String key;
	/**
	 * 短信
	 */
	@ApiModelProperty(required=false,value="短信")
	private String code;
	/**
	 * 用户id
	 */
	@ApiModelProperty(required=false,value="用户id")
	private Integer userId;
	/**
	 * 项目id
	 */
	@ApiModelProperty(required=false,value="项目id")
	private Integer projectId;
	/**
	 * 设备id
	 */
	@ApiModelProperty(required=false,value="设备id")
	private String deviceId;
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
