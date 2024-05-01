package io.renren.common.utils;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.PatternPool;
import cn.hutool.core.util.ReUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author liuyuchan
 * @email liuyuchan286@gmail.com
 * @date 2024/1/19 01:30
 */
public class DateThParse {

    public static void main(String[] args) {
        DateTime parse = DateThParse.parse("Your remaining balance is 0.00THBplease top up before 06/02/2025 to continue the service.");
        System.out.println(parse);
    }
    public static DateTime parse(String dateStr) {
        Pattern datePattern = Pattern.compile("\\d{2}/\\d{2}/\\d{4}");
        Matcher matcher = datePattern.matcher(dateStr);

        if (matcher.find()) {
            String dateString = matcher.group();
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                Date date = dateFormat.parse(dateString);
                int year = DateUtil.year(date);
                if (year > 2100) {
                    DateTime offset = DateUtil.offset(date, DateField.YEAR, -543);
                    return offset;
                }
                return DateUtil.date(date);
            } catch (Exception e) {
                System.err.println("Error parsing the date: " + e.getMessage());
            }
        }
        datePattern = Pattern.compile("\\d{2}/\\d{2}/\\d{2}");
        matcher = datePattern.matcher(dateStr);
        if (matcher.find()) {
            String dateString = matcher.group();
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
                Date date = dateFormat.parse(dateString);
                return DateUtil.date(date);
            } catch (Exception e) {
                System.err.println("Error parsing the date: " + e.getMessage());
            }
        }

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
