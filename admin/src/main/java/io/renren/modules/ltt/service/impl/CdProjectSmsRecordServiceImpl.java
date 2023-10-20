package io.renren.modules.ltt.service.impl;

import io.renren.datasources.annotation.Game;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.ltt.dao.CdProjectSmsRecordDao;
import io.renren.modules.ltt.entity.CdProjectSmsRecordEntity;
import io.renren.modules.ltt.dto.CdProjectSmsRecordDTO;
import io.renren.modules.ltt.vo.CdProjectSmsRecordVO;
import io.renren.modules.ltt.service.CdProjectSmsRecordService;
import io.renren.modules.ltt.conver.CdProjectSmsRecordConver;

import java.io.Serializable;
import java.util.Collection;


@Service("cdProjectSmsRecordService")
@Game
public class CdProjectSmsRecordServiceImpl extends ServiceImpl<CdProjectSmsRecordDao, CdProjectSmsRecordEntity> implements CdProjectSmsRecordService {

    @Override
    public PageUtils<CdProjectSmsRecordVO> queryPage(CdProjectSmsRecordDTO cdProjectSmsRecord) {
        IPage<CdProjectSmsRecordEntity> page = baseMapper.selectPage(
                new Query<CdProjectSmsRecordEntity>(cdProjectSmsRecord).getPage(),
                new QueryWrapper<CdProjectSmsRecordEntity>()
        );

        return PageUtils.<CdProjectSmsRecordVO>page(page).setList(CdProjectSmsRecordConver.MAPPER.conver(page.getRecords()));
    }
    @Override
    public CdProjectSmsRecordVO getById(Integer id) {
        return CdProjectSmsRecordConver.MAPPER.conver(baseMapper.selectById(id));
    }

    @Override
    public boolean save(CdProjectSmsRecordDTO cdProjectSmsRecord) {
        CdProjectSmsRecordEntity cdProjectSmsRecordEntity = CdProjectSmsRecordConver.MAPPER.converDTO(cdProjectSmsRecord);
        return this.save(cdProjectSmsRecordEntity);
    }

    @Override
    public boolean updateById(CdProjectSmsRecordDTO cdProjectSmsRecord) {
        CdProjectSmsRecordEntity cdProjectSmsRecordEntity = CdProjectSmsRecordConver.MAPPER.converDTO(cdProjectSmsRecord);
        return this.updateById(cdProjectSmsRecordEntity);
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
