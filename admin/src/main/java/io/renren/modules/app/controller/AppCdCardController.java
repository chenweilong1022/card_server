package io.renren.modules.app.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.benmanes.caffeine.cache.Cache;
import io.renren.common.base.dto.PageParam;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.app.dto.AppCdBoardUpdateBoardDTO;
import io.renren.modules.app.dto.AppCdCardUpdateIccidDTO;
import io.renren.modules.ltt.dto.CdCardDTO;
import io.renren.modules.ltt.entity.CdCardEntity;
import io.renren.modules.ltt.service.CdCardService;
import io.renren.modules.ltt.vo.CdCardVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


/**
 * 卡
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-05 23:22:42
 */
@RestController
@RequestMapping("app/cdcard")
public class AppCdCardController extends PageParam implements Serializable {
    @Autowired
    private CdCardService cdCardService;

    /**
     * 獲取device所有的card
     */
    @RequestMapping("/cardList")
    public R cardList(@RequestBody AppCdBoardUpdateBoardDTO boardDTO){
        List<CdCardEntity> list = cdCardService.list(new QueryWrapper<CdCardEntity>().lambda()
                .eq(CdCardEntity::getDeviceId,boardDTO.getDeviceId())
        );
        return R.data(list);
    }

    /**
     * 保存
     */
    @RequestMapping("/uploadCard")
    public R uploadCard(@RequestBody AppCdBoardUpdateBoardDTO boardDTO){
		cdCardService.uploadCard(boardDTO);
        return R.ok();
    }


    /**
     * 上传
     */
    @RequestMapping("/uploadIccid")
    public R uploadIccid(@RequestBody AppCdCardUpdateIccidDTO iccidDTO){
        cdCardService.uploadIccid(iccidDTO);
        return R.ok();
    }

    /**
     * 上传
     */
    @RequestMapping("/uploadIccids")
    public R uploadIccids(@RequestBody Map<String,String> params){
        cdCardService.uploadIccids(params);
        return R.ok();
    }

}
