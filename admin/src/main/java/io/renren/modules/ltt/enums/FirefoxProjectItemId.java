package io.renren.modules.ltt.enums;


import io.renren.common.base.interfaces.BaseEnum;
import lombok.Getter;

@Getter
public enum FirefoxProjectItemId implements BaseEnum {
    Telegram(1000,"Telegram"),
    Instagram(1001,"Instagram"),
    Google(1002,"Google"),
    Yahoo(1003,"Yahoo"),
    Facebook(1006,"Facebook"),
    WhatsApp(1008,"WhatsApp"),
    WeChat(1016,"WeChat"),
    Amazon(1017,"Amazon"),
    Microsoft(1019,"Microsoft"),
    LINE(1027,"LINE"),
    ;

    FirefoxProjectItemId(int key, String value) {
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
