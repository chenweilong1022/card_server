package io.renren.modules.ltt.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.renren.datasources.annotation.Game;
import io.renren.modules.ltt.enums.DeleteFlag;
import io.renren.modules.ltt.service.CdCardGroupService;
import io.renren.modules.ltt.vo.CdCardGroupVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.ltt.dao.CdProjectStatisticsDao;
import io.renren.modules.ltt.entity.CdProjectStatisticsEntity;
import io.renren.modules.ltt.dto.CdProjectStatisticsDTO;
import io.renren.modules.ltt.vo.CdProjectStatisticsVO;
import io.renren.modules.ltt.service.CdProjectStatisticsService;
import io.renren.modules.ltt.conver.CdProjectStatisticsConver;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service("cdProjectStatisticsService")
@Game
public class CdProjectStatisticsServiceImpl extends ServiceImpl<CdProjectStatisticsDao, CdProjectStatisticsEntity> implements CdProjectStatisticsService {

    @Override
    public PageUtils<CdProjectStatisticsVO> queryPage(CdProjectStatisticsDTO cdProjectStatistics) {
        IPage<CdProjectStatisticsEntity> page = baseMapper.selectPage(
                new Query<CdProjectStatisticsEntity>(cdProjectStatistics).getPage(),
                new QueryWrapper<CdProjectStatisticsEntity>()
        );

        return PageUtils.<CdProjectStatisticsVO>page(page).setList(CdProjectStatisticsConver.MAPPER.conver(page.getRecords()));
    }
    @Override
    public CdProjectStatisticsVO getById(Integer id) {
        return CdProjectStatisticsConver.MAPPER.conver(baseMapper.selectById(id));
    }

    @Override
    public boolean save(CdProjectStatisticsDTO cdProjectStatistics) {
        CdProjectStatisticsEntity cdProjectStatisticsEntity = CdProjectStatisticsConver.MAPPER.converDTO(cdProjectStatistics);
        return this.save(cdProjectStatisticsEntity);
    }

    @Override
    public boolean updateById(CdProjectStatisticsDTO cdProjectStatistics) {
        CdProjectStatisticsEntity cdProjectStatisticsEntity = CdProjectStatisticsConver.MAPPER.converDTO(cdProjectStatistics);
        return this.updateById(cdProjectStatisticsEntity);
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> ids) {
        return super.removeByIds(ids);
    }

    @Override
    public List<CdProjectStatisticsVO> statistics(CdProjectStatisticsDTO cdProjectStatistics) {
        return baseMapper.statistics(cdProjectStatistics);
    }

    @Autowired
    private CdCardGroupService cdCardGroupService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean generateStatistics(Integer[] ids) {
        CdProjectStatisticsDTO cdProjectStatistics = new CdProjectStatisticsDTO();
        cdProjectStatistics.setGroupId(ids[0]);
        List<CdProjectStatisticsVO> statistics = this.statistics(cdProjectStatistics);
        List<CdProjectStatisticsEntity> cdProjectStatisticsEntities = new ArrayList<>();
        CdCardGroupVO cardGroupVO = cdCardGroupService.getById(cdProjectStatistics.getGroupId());
        for (CdProjectStatisticsVO statistic : statistics) {
            CdProjectStatisticsEntity cdProjectStatisticsEntity = CdProjectStatisticsConver.MAPPER.conver1(statistic);
            cdProjectStatisticsEntity.setGroupId(cardGroupVO.getId());
            cdProjectStatisticsEntity.setGroupName(cardGroupVO.getGroupName());
            cdProjectStatisticsEntity.setCreateTime(DateUtil.date());
            cdProjectStatisticsEntity.setDeleteFlag(DeleteFlag.NO.getKey());
            cdProjectStatisticsEntity.setSuccessCount(cdProjectStatisticsEntity.getTotalCount() - cdProjectStatisticsEntity.getErrorCount());
            if (StrUtil.isNotEmpty(cdProjectStatisticsEntity.getProjectName())) {
                cdProjectStatisticsEntities.add(cdProjectStatisticsEntity);
            }
        }

        return this.saveBatch(cdProjectStatisticsEntities);
    }

}
