package io.renren.modules.app.controller;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.modules.app.dto.AppCdBoardUpdateBoardDTO;
import io.renren.modules.ltt.dto.CdBoardDTO;
import io.renren.modules.ltt.service.CdBoardService;
import io.renren.modules.ltt.vo.CdBoardVO;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;


/**
 * 卡的板子
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-05 23:22:42
 */
@RestController
@RequestMapping("app/cdboard")
public class AppCdBoardController {
    @Autowired
    private CdBoardService cdBoardService;

    /**
     * 上传板子数量
     */
    @RequestMapping("/uploadBoard")
    public R uploadBoard(@RequestBody AppCdBoardUpdateBoardDTO boardDTO){
		cdBoardService.uploadBoard(boardDTO);
        return R.ok();
    }
}
