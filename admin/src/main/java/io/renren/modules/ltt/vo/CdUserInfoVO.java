package io.renren.modules.ltt.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain = true)
public class CdUserInfoVO  implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id;主键
     */
    @TableId(type = IdType.AUTO)
    @ApiModelProperty(required=false,value="id;主键")
    private Integer id;
    /**
     * 余额
     */
    @ApiModelProperty(required=false,value="余额")
    private BigDecimal balance;
    /**
     * 账号
     */
    @ApiModelProperty(required=false,value="账号")
    private String account;
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
