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
 * 卡的板子
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-06 23:26:10
 */
@Data
@TableName("cd_board")
@ApiModel("卡的板子")
@Accessors(chain = true)
public class CdBoardDTO extends PageParam implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键;主键
	 */
	@TableId(type = IdType.AUTO)
	@ApiModelProperty(required=false,value="主键;主键")
	private Integer id;
	/**
	 * 设备id
	 */
	@ApiModelProperty(required=false,value="设备id")
	private String deviceId;
	/**
	 * 板区
	 */
	@ApiModelProperty(required=false,value="板区")
	private Integer indexed;
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
