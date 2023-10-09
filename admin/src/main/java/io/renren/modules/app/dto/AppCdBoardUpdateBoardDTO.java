package io.renren.modules.app.dto;

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
 * @date 2023-10-05 23:22:42
 */
@Data
@TableName("cd_board")
@ApiModel("卡的板子")
@Accessors(chain = true)
public class AppCdBoardUpdateBoardDTO extends PageParam implements Serializable {

	/**
	 * 设备id
	 */
	@ApiModelProperty(required=false,value="设备id")
	private String deviceId;
	/**
	 * 数量
	 */
	@ApiModelProperty(required=false,value="数量")
	private Integer count;

}
