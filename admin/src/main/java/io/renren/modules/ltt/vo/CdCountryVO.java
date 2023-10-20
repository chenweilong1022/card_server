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
 * 国家表
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-14 23:45:07
 */
@Data
@TableName("cd_country")
@ApiModel("国家表")
@Accessors(chain = true)
public class CdCountryVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id;主键
	 */
	@TableId(type = IdType.AUTO)
	@ApiModelProperty(required=false,value="id;主键")
	private Integer id;
	/**
	 * 名称
	 */
	@ApiModelProperty(required=false,value="名称")
	private String name;
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
