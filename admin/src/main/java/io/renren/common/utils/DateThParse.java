package io.renren.common.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.PatternPool;
import cn.hutool.core.util.ReUtil;

import java.util.List;

/**
 * @author liuyuchan
 * @email liuyuchan286@gmail.com
 * @date 2024/1/19 01:30
 */
public class DateThParse {
    public static DateTime parse(String dateStr) {
        List<String> all = ReUtil.findAll(PatternPool.NUMBERS, dateStr, 0);
        if(CollUtil.isEmpty(all) || all.size() < 3) {
            return null;
        }
        int yearIndex = -1;
        int monthIndex = -1;
        int dayIndex = -1;
        for (int i = 0; i < all.size(); i++) {
            String s = all.get(i);
            if (s.length() == 4) {
                yearIndex = i;
                monthIndex = i - 1;
                dayIndex = i - 2;
            }
        }
        if (yearIndex > -1 && monthIndex > -1 && dayIndex > -1) {
            int i1 = Integer.parseInt(all.get(yearIndex));
            int i = 2023;
            if(i1 <= 2025) {
                i = i1;
            }else {
                i = i1 - 543;
            }
            String format = String.format("%s-%s-%s",String.valueOf(i), all.get(monthIndex), all.get(dayIndex));
            return DateUtil.parseDate(format);
        }
        return null;
    }
}
