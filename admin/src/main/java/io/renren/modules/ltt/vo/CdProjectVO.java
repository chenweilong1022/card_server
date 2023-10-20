package io.renren.modules.ltt.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.renren.common.utils.EnumUtil;
import io.renren.modules.ltt.enums.AuditStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;

/**
 * 项目表
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-15 00:41:11
 */
@Data
@TableName("cd_project")
@ApiModel("项目表")
@Accessors(chain = true)
public class CdProjectVO implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id;主键
	 */
	@TableId(type = IdType.AUTO)
	@ApiModelProperty(required=false,value="id;主键")
	private Integer id;
	/**
	 * 审核状态
	 */
	@ApiModelProperty(required=false,value="审核状态")
	private Integer auditStatus;
	/**
	 * 审核状态
	 */
	@ApiModelProperty(required=false,value="审核状态")
	private String auditStatusStr;
	/**
	 * 项目名称
	 */
	@ApiModelProperty(required=false,value="项目名称")
	private String name;
	/**
	 * 国家id
	 */
	@ApiModelProperty(required=false,value="国家id")
	private Integer countryId;
	/**
	 * 国家
	 */
	@ApiModelProperty(required=false,value="国家")
	private String countryName;
	/**
	 * 价格
	 */
	@ApiModelProperty(required=false,value="价格")
	private BigDecimal price;
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

	public String getAuditStatusStr() {
		return EnumUtil.queryValueByKey(this.auditStatus, AuditStatus.values());
	}
}
