package io.renren.common.utils;


import io.renren.common.base.interfaces.BaseEnum;
import lombok.Getter;

@Getter
public enum LockMapKeyResource implements BaseEnum {

    LockMapKeyResource1(1,"CdCardEntity"),
    ;

    LockMapKeyResource(int key, String value) {
        this.key = key;
        this.value = value;
    }

    private final int key;
    private final String value;


    public Integer getKey() {
        return key;
    }


    public static String getKeyByResource(LockMapKeyResource lockMapKeyResource,Integer id) {
        return String.format("%s_%s",lockMapKeyResource.getValue(),id);
    }

    public static String getKeyByResource(LockMapKeyResource lockMapKeyResource,String id) {
        return String.format("%s_%s",lockMapKeyResource.getValue(),id);
    }

    public String getValue() {
        return value;
    }
}
