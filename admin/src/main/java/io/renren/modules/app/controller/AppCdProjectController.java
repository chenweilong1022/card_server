package io.renren.modules.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.ltt.conver.CdProjectConver;
import io.renren.modules.ltt.dto.CdProjectDTO;
import io.renren.modules.ltt.entity.CdProjectEntity;
import io.renren.modules.ltt.service.CdProjectService;
import io.renren.modules.ltt.vo.CdProjectVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;


/**
 * 项目表
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-14 23:45:07
 */
@RestController
@RequestMapping("app/cdproject")
public class AppCdProjectController {
    @Autowired
    private CdProjectService cdProjectService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestBody CdProjectDTO cdProject){
        List<CdProjectEntity> list = cdProjectService.list(new QueryWrapper<CdProjectEntity>().lambda()
                .eq(CdProjectEntity::getCountryId,cdProject.getCountryId())
        );
        return R.data(CdProjectConver.MAPPER.conver(list));
    }

}
