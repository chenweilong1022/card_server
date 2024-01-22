package io.renren.modules.ltt.service.impl;

import io.renren.datasources.annotation.Game;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.ltt.dao.CdRechargedPhoneDao;
import io.renren.modules.ltt.entity.CdRechargedPhoneEntity;
import io.renren.modules.ltt.dto.CdRechargedPhoneDTO;
import io.renren.modules.ltt.vo.CdRechargedPhoneVO;
import io.renren.modules.ltt.service.CdRechargedPhoneService;
import io.renren.modules.ltt.conver.CdRechargedPhoneConver;

import java.io.Serializable;
import java.util.Collection;


@Service("cdRechargedPhoneService")
@Game
public class CdRechargedPhoneServiceImpl extends ServiceImpl<CdRechargedPhoneDao, CdRechargedPhoneEntity> implements CdRechargedPhoneService {

    @Override
    public PageUtils<CdRechargedPhoneVO> queryPage(CdRechargedPhoneDTO cdRechargedPhone) {
        IPage<CdRechargedPhoneEntity> page = baseMapper.selectPage(
                new Query<CdRechargedPhoneEntity>(cdRechargedPhone).getPage(),
                new QueryWrapper<CdRechargedPhoneEntity>()
        );

        return PageUtils.<CdRechargedPhoneVO>page(page).setList(CdRechargedPhoneConver.MAPPER.conver(page.getRecords()));
    }
    @Override
    public CdRechargedPhoneVO getById(Integer id) {
        return CdRechargedPhoneConver.MAPPER.conver(baseMapper.selectById(id));
    }

    @Override
    public boolean save(CdRechargedPhoneDTO cdRechargedPhone) {
        CdRechargedPhoneEntity cdRechargedPhoneEntity = CdRechargedPhoneConver.MAPPER.converDTO(cdRechargedPhone);
        return this.save(cdRechargedPhoneEntity);
    }

    @Override
    public boolean updateById(CdRechargedPhoneDTO cdRechargedPhone) {
        CdRechargedPhoneEntity cdRechargedPhoneEntity = CdRechargedPhoneConver.MAPPER.converDTO(cdRechargedPhone);
        return this.updateById(cdRechargedPhoneEntity);
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
