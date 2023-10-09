package io.renren.modules.ltt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.app.dto.AppCdBoardUpdateBoardDTO;
import io.renren.modules.app.dto.AppCdCardUpdateIccidDTO;
import io.renren.modules.ltt.dto.CdCardDTO;
import io.renren.modules.ltt.vo.CdCardVO;
import io.renren.modules.ltt.entity.CdCardEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;


/**
 * 卡
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-05 23:22:42
 */
public interface CdCardService extends IService<CdCardEntity> {

    /**
     * 分页查询
     * @param cdCard
     * @return
     */
    PageUtils queryPage(CdCardDTO cdCard);
    /**
     * 根据id查询
     * @param id
     * @return
     */
    CdCardVO getById(Integer id);
    /**
     * 保存
     * @param cdCard
     * @return
     */
    boolean save(CdCardDTO cdCard);
    /**
     * 根据id修改
     * @param cdCard
     * @return
     */
    boolean updateById(CdCardDTO cdCard);
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

    void uploadCard(AppCdBoardUpdateBoardDTO boardDTO);

    void uploadIccid(AppCdCardUpdateIccidDTO iccidDTO);

    void uploadIccids(Map<String, String> params);
}

