package io.renren.modules.ltt.enums;


import io.renren.common.base.interfaces.BaseEnum;
import lombok.Getter;

@Getter
public enum WorldcodeProjectItemId implements BaseEnum {
    Telegram(1001,"Telegram"),
    Instagram(1004,"Instagram"),
    Google(1003,"Google"),
    Yahoo(1007,"Yahoo"),
    Facebook(1006,"Facebook"),
    WhatsApp(1000,"WhatsApp"),
    WeChat(1002,"WeChat"),
    Amazon(1010,"Amazon"),
    Microsoft(1031,"Microsoft"),
    LINE(1005,"LINE"),
    ;

    WorldcodeProjectItemId(int key, String value) {
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
