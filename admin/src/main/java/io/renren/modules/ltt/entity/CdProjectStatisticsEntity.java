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
 *
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2024-01-01 15:32:55
 */
@Data
@TableName("cd_project_statistics")
@ApiModel("")
@Accessors(chain = true)
public class CdProjectStatisticsEntity implements Serializable {
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
	private String projectName;
	/**
	 *
	 */
	@ApiModelProperty(required=false,value="")
	private Integer projectId;
	/**
	 *
	 */
	@ApiModelProperty(required=false,value="")
	private Integer groupId;
	/**
	 *
	 */
	@ApiModelProperty(required=false,value="")
	private String groupName;
	/**
	 *
	 */
	@ApiModelProperty(required=false,value="")
	private Integer totalCount;
	/**
	 *
	 */
	@ApiModelProperty(required=false,value="")
	private Integer successCount;
	/**
	 *
	 */
	@ApiModelProperty(required=false,value="")
	private Integer errorCount;
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

}
