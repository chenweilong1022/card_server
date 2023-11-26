package io.renren.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

/**
 * @author liuyuchan
 * @email liuyuchan286@gmail.com
 * @date 2023/11/22 00:43
 */
@Data
public class ProjectWorkEntity {

    private Integer userId;

    private Integer projectId;

    private String phonePre;

    private String codeApiUrl;


    private Integer platform;
}
