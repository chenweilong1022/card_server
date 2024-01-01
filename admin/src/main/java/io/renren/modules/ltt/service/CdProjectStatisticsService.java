package io.renren.modules.ltt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.ltt.dto.CdProjectStatisticsDTO;
import io.renren.modules.ltt.vo.CdProjectStatisticsVO;
import io.renren.modules.ltt.entity.CdProjectStatisticsEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


/**
 *
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2024-01-01 15:32:55
 */
public interface CdProjectStatisticsService extends IService<CdProjectStatisticsEntity> {

    /**
     * 分页查询
     * @param cdProjectStatistics
     * @return
     */
    PageUtils queryPage(CdProjectStatisticsDTO cdProjectStatistics);
    /**
     * 根据id查询
     * @param id
     * @return
     */
    CdProjectStatisticsVO getById(Integer id);
    /**
     * 保存
     * @param cdProjectStatistics
     * @return
     */
    boolean save(CdProjectStatisticsDTO cdProjectStatistics);
    /**
     * 根据id修改
     * @param cdProjectStatistics
     * @return
     */
    boolean updateById(CdProjectStatisticsDTO cdProjectStatistics);
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

    /**
     * 统计
     * @param cdProjectStatistics
     * @return
     */
    List<CdProjectStatisticsVO> statistics(CdProjectStatisticsDTO cdProjectStatistics);

    /**
     * 生成统计
     * @param cdProjectStatistics
     * @return
     */
    boolean generateStatistics(Integer[] ids);
}

