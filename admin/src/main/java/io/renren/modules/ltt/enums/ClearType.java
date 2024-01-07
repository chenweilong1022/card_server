package io.renren.modules.ltt.enums;


import io.renren.common.base.interfaces.BaseEnum;
import lombok.Getter;

@Getter
public enum ClearType implements BaseEnum {

    CLEAR_TYPE1(1,"未回码清空"),
    CLEAR_TYPE2(2,"全部清空"),
    ;

    ClearType(int key, String value) {
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
