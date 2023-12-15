package io.renren.modules.ltt.dto;

import lombok.Data;

/**
 * @author liuyuchan
 * @email liuyuchan286@gmail.com
 * @date 2023/12/15 15:48
 */
@Data
public class CdDevicesInitDTO {
    private Integer[] ids;
    private Integer type;
    private String ussd;
}
