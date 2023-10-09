package io.renren.modules.ltt.enums;

import io.renren.common.base.interfaces.BaseEnum;
import lombok.Getter;


public enum DeleteFlag {

    YES(0,"删除"),
    NO(1,"有效"),
    ;

    DeleteFlag(int key, String value) {
        this.key = key;
        this.value = value;
    }

    private final int key;
    private final String value;


    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
