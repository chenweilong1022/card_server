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
public class CdCardLockVO implements Serializable {
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

}
