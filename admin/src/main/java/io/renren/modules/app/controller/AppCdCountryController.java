package io.renren.modules.app.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.ltt.dto.CdCountryDTO;
import io.renren.modules.ltt.service.CdCountryService;
import io.renren.modules.ltt.vo.CdCountryVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * 国家表
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-14 23:45:07
 */
@RestController
@RequestMapping("app/cdcountry")
public class AppCdCountryController {
    @Autowired
    private CdCountryService cdCountryService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(CdCountryDTO cdCountry){
        return R.data(cdCountryService.list());
    }

}
