package io.renren.modules.ltt.enums;


import io.renren.common.base.interfaces.BaseEnum;
import lombok.Getter;

@Getter
public enum WorkType implements BaseEnum {

    WorkType1(1,"闲置"),
    WorkType2(2,"初始化"),
    WorkType3(3,"接码"),
    ;

    WorkType(int key, String value) {
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
