package io.renren.modules.ltt.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.base.dto.PageParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.math.BigDecimal;
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
@TableName("cd_project")
@ApiModel("")
@Accessors(chain = true)
public class CdProjectDTO extends PageParam implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId(type = IdType.AUTO)
	@ApiModelProperty(required=false,value="")
	private Integer id;
	/**
	 * id;主键
	 */
	@TableId(type = IdType.AUTO)
	@ApiModelProperty(required=false,value="id;主键")
	private Integer[] ids;
	/**
	 *
	 */
	@ApiModelProperty(required=false,value="")
	private Integer auditStatus;
	/**
	 *
	 */
	@ApiModelProperty(required=false,value="")
	private String name;
	/**
	 *
	 */
	@ApiModelProperty(required=false,value="")
	private Integer countryId;
	/**
	 *
	 */
	@ApiModelProperty(required=false,value="")
	private BigDecimal price;
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
	private Integer itemId;

}
