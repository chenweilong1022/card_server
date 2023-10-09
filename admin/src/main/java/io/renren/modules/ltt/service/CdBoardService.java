package io.renren.modules.ltt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.app.dto.AppCdBoardUpdateBoardDTO;
import io.renren.modules.ltt.dto.CdBoardDTO;
import io.renren.modules.ltt.vo.CdBoardVO;
import io.renren.modules.ltt.entity.CdBoardEntity;

import java.io.Serializable;
import java.util.Collection;


/**
 * 卡的板子
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-05 23:22:42
 */
public interface CdBoardService extends IService<CdBoardEntity> {

    /**
     * 分页查询
     * @param cdBoard
     * @return
     */
    PageUtils queryPage(CdBoardDTO cdBoard);
    /**
     * 根据id查询
     * @param id
     * @return
     */
    CdBoardVO getById(Integer id);
    /**
     * 保存
     * @param cdBoard
     * @return
     */
    boolean save(CdBoardDTO cdBoard);
    /**
     * 根据id修改
     * @param cdBoard
     * @return
     */
    boolean updateById(CdBoardDTO cdBoard);
    /**
     * 根据id删除
     * @param id
     * @return
     */
    @Override
    boolean removeById(Serializable id);

    /**
     * 根据id批量删除
     * @param ids
     * @return
     */
    @Override
    boolean removeByIds(Collection<? extends Serializable> ids);

    void uploadBoard(AppCdBoardUpdateBoardDTO boardDTO);
}

