package io.renren.modules.ltt.enums;


import io.renren.common.base.interfaces.BaseEnum;
import lombok.Getter;

@Getter
public enum CodeAcquisitionType implements BaseEnum {

    CodeAcquisitionType1(1,"指定项目"),
    CodeAcquisitionType2(2,"挂机模式"),
    ;

    CodeAcquisitionType(int key, String value) {
        this.key = key;
        this.value = value;
    }

    private final Integer key;
    private final String value;


    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
