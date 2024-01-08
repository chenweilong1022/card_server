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
 * @date 2023-11-22 03:25:15
 */
@Data
@TableName("cd_card_lock")
@ApiModel("")
@Accessors(chain = true)
public class CdCardLockDTO extends PageParam implements Serializable {
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
	@TableId(type = IdType.AUTO)
	@ApiModelProperty(required=false,value="")
	private List<Integer> ids;
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
	 *
	 */
	@ApiModelProperty(required=false,value="")
	private String deviceId;
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
	private String iccid;
	/**
	 * 指定号段
	 */
	@ApiModelProperty(required=false,value="指定号段")
	private String numberSegment;
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
	private Date phoneGetTime;
	/**
	 * 分组id
	 */
	@ApiModelProperty(required=false,value="")
	private Integer groupId;
	/**
	 * online
	 */
	@ApiModelProperty(required=false,value="")
	private Integer online;
	/**
	 * 分组id
	 */
	@ApiModelProperty(required=false,value="")
	private Integer workType;

}
