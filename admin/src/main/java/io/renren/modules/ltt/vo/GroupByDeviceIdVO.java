package io.renren.modules.ltt.vo;

import lombok.Data;

/**
 * @author liuyuchan
 * @email liuyuchan286@gmail.com
 * @date 2023/11/19 18:39
 */
@Data
public class GroupByDeviceIdVO {
    private Integer initSuccessNumber;

    private Integer initTotalNumber;

    private String deviceId;

    private Integer groupId;
}
