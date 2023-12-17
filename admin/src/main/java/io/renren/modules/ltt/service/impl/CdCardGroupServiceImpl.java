package io.renren.modules.ltt.service.impl;

import cn.hutool.core.date.DateUtil;
import io.renren.datasources.annotation.Game;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.ltt.dao.CdCardGroupDao;
import io.renren.modules.ltt.entity.CdCardGroupEntity;
import io.renren.modules.ltt.dto.CdCardGroupDTO;
import io.renren.modules.ltt.vo.CdCardGroupVO;
import io.renren.modules.ltt.service.CdCardGroupService;
import io.renren.modules.ltt.conver.CdCardGroupConver;

import java.io.Serializable;
import java.util.Collection;


@Service("cdCardGroupService")
@Game
public class CdCardGroupServiceImpl extends ServiceImpl<CdCardGroupDao, CdCardGroupEntity> implements CdCardGroupService {

    @Override
    public PageUtils<CdCardGroupVO> queryPage(CdCardGroupDTO cdCardGroup) {
        IPage<CdCardGroupEntity> page = baseMapper.selectPage(
                new Query<CdCardGroupEntity>(cdCardGroup).getPage(),
                new QueryWrapper<CdCardGroupEntity>()
        );

        return PageUtils.<CdCardGroupVO>page(page).setList(CdCardGroupConver.MAPPER.conver(page.getRecords()));
    }
    @Override
    public CdCardGroupVO getById(Integer id) {
        return CdCardGroupConver.MAPPER.conver(baseMapper.selectById(id));
    }

    @Override
    public boolean save(CdCardGroupDTO cdCardGroup) {
        cdCardGroup.setCreateTime(DateUtil.date());
        CdCardGroupEntity cdCardGroupEntity = CdCardGroupConver.MAPPER.converDTO(cdCardGroup);
        return this.save(cdCardGroupEntity);
    }

    @Override
    public boolean updateById(CdCardGroupDTO cdCardGroup) {
        CdCardGroupEntity cdCardGroupEntity = CdCardGroupConver.MAPPER.converDTO(cdCardGroup);
        return this.updateById(cdCardGroupEntity);
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> ids) {
        return super.removeByIds(ids);
    }

}
