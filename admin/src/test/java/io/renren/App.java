package io.renren;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author liuyuchan
 * @email liuyuchan286@gmail.com
 * @date 2023/11/22 02:55
 */
public class App {

    public static void main(String[] args) throws ParseException {

        TimeZone.setDefault( TimeZone.getTimeZone("UTC"));


        Instant instant = Instant.parse("2023-11-22T03:53:16Z");
        java.util.Date date = java.util.Date.from( instant );
        System.out.println(date);
    }
}
