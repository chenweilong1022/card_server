package io.renren.modules.ltt.service.impl;

import io.renren.datasources.annotation.Game;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.ltt.dao.CdIccidPhoneDao;
import io.renren.modules.ltt.entity.CdIccidPhoneEntity;
import io.renren.modules.ltt.dto.CdIccidPhoneDTO;
import io.renren.modules.ltt.vo.CdIccidPhoneVO;
import io.renren.modules.ltt.service.CdIccidPhoneService;
import io.renren.modules.ltt.conver.CdIccidPhoneConver;

import java.io.Serializable;
import java.util.Collection;


@Service("cdIccidPhoneService")
@Game
public class CdIccidPhoneServiceImpl extends ServiceImpl<CdIccidPhoneDao, CdIccidPhoneEntity> implements CdIccidPhoneService {

    @Override
    public PageUtils<CdIccidPhoneVO> queryPage(CdIccidPhoneDTO cdIccidPhone) {
        IPage<CdIccidPhoneEntity> page = baseMapper.selectPage(
                new Query<CdIccidPhoneEntity>(cdIccidPhone).getPage(),
                new QueryWrapper<CdIccidPhoneEntity>()
        );

        return PageUtils.<CdIccidPhoneVO>page(page).setList(CdIccidPhoneConver.MAPPER.conver(page.getRecords()));
    }
    @Override
    public CdIccidPhoneVO getById(Integer id) {
        return CdIccidPhoneConver.MAPPER.conver(baseMapper.selectById(id));
    }

    @Override
    public boolean save(CdIccidPhoneDTO cdIccidPhone) {
        CdIccidPhoneEntity cdIccidPhoneEntity = CdIccidPhoneConver.MAPPER.converDTO(cdIccidPhone);
        return this.save(cdIccidPhoneEntity);
    }

    @Override
    public boolean updateById(CdIccidPhoneDTO cdIccidPhone) {
        CdIccidPhoneEntity cdIccidPhoneEntity = CdIccidPhoneConver.MAPPER.converDTO(cdIccidPhone);
        return this.updateById(cdIccidPhoneEntity);
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
