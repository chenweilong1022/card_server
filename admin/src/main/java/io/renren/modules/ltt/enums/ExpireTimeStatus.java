package io.renren.modules.ltt.enums;

import io.renren.common.base.interfaces.BaseEnum;
import lombok.Getter;

@Getter
public enum ExpireTimeStatus implements BaseEnum{

    YES(1,"有时间"),
    NO(2,"没有时间"),
    ;

    ExpireTimeStatus(int key, String value) {
        this.key = key;
        this.value = value;
    }

    private final int key;
    private final String value;

    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
