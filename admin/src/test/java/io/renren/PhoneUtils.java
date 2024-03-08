package io.renren;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyuchan
 * @email liuyuchan286@gmail.com
 * @date 2024/1/22 20:59
 */
public class PhoneUtils {
    public static void main(String[] args) {

        ExcelReader reader = ExcelUtil.getReader("/Users/chenweilong/Downloads/TRUE-3.1- 1317.xlsx");


        List<String> sqls = new ArrayList<>();

        List<List<Object>> read = reader.read();
        for (List<Object> objects : read) {
            for (Object object : objects) {
                String format = String.format("INSERT INTO cd_recharged_phone (phone) VALUE ('%s');", object);
                if (ObjectUtil.isNull(object) || StrUtil.isEmpty(String.valueOf(object))) {
                    continue;
                }
                System.out.println(format);
                sqls.add(format);
            }
        }


        FileUtil.writeLines(sqls,"/Users/chenweilong/Desktop/java代码/card_server/admin/sql/phone5.txt","UTF-8");

    }
}
