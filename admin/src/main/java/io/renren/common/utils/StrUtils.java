package io.renren.common.utils;

import static org.apache.commons.lang3.StringUtils.isNumeric;

/**
 * @author huyan
 * @date 2024/4/17
 */
public class StrUtils {


    public static String subStr(String str, String prefix) {
        // 如果字符串以指定字符开头，则去除开头部分
        if (str.startsWith(prefix)) {
            return str.substring(prefix.length());
        }
        return null;
    }

    public static Integer strToNumber(String str) {
        if (org.apache.commons.lang3.StringUtils.isEmpty(str)) {
            return null;
        }
        if (isNumeric(str)) {
            return Integer.valueOf(str);
        }
        return null;
    }
}
