package io.renren.modules.ltt.vo;

import lombok.Data;

/**
 * @author liuyuchan
 * @email liuyuchan286@gmail.com
 * @date 2023/12/27 15:31
 */
@Data
public class UpdateAppVO {
    private String httpUrl;
    private String deviceId;
    private String currentVersion;
    private Integer id;
    private Integer count;
}
