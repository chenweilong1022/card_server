package io.renren.modules.ltt.service.impl;

import io.renren.datasources.annotation.Game;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.ltt.dao.CdCountryDao;
import io.renren.modules.ltt.entity.CdCountryEntity;
import io.renren.modules.ltt.dto.CdCountryDTO;
import io.renren.modules.ltt.vo.CdCountryVO;
import io.renren.modules.ltt.service.CdCountryService;
import io.renren.modules.ltt.conver.CdCountryConver;

import java.io.Serializable;
import java.util.Collection;


@Service("cdCountryService")
@Game
public class CdCountryServiceImpl extends ServiceImpl<CdCountryDao, CdCountryEntity> implements CdCountryService {

    @Override
    public PageUtils<CdCountryVO> queryPage(CdCountryDTO cdCountry) {
        IPage<CdCountryEntity> page = baseMapper.selectPage(
                new Query<CdCountryEntity>(cdCountry).getPage(),
                new QueryWrapper<CdCountryEntity>()
        );

        return PageUtils.<CdCountryVO>page(page).setList(CdCountryConver.MAPPER.conver(page.getRecords()));
    }
    @Override
    public CdCountryVO getById(Integer id) {
        return CdCountryConver.MAPPER.conver(baseMapper.selectById(id));
    }

    @Override
    public boolean save(CdCountryDTO cdCountry) {
        CdCountryEntity cdCountryEntity = CdCountryConver.MAPPER.converDTO(cdCountry);
        return this.save(cdCountryEntity);
    }

    @Override
    public boolean updateById(CdCountryDTO cdCountry) {
        CdCountryEntity cdCountryEntity = CdCountryConver.MAPPER.converDTO(cdCountry);
        return this.updateById(cdCountryEntity);
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
