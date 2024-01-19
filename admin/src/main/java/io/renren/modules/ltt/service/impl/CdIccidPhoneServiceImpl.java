package io.renren.modules.ltt.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import io.renren.common.validator.Assert;
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
import java.util.List;
import java.util.stream.Collectors;


@Service("cdIccidPhoneService")
@Game
public class CdIccidPhoneServiceImpl extends ServiceImpl<CdIccidPhoneDao, CdIccidPhoneEntity> implements CdIccidPhoneService {

    @Override
    public PageUtils<CdIccidPhoneVO> queryPage(CdIccidPhoneDTO cdIccidPhone) {
        IPage<CdIccidPhoneEntity> page = baseMapper.selectPage(
                new Query<CdIccidPhoneEntity>(cdIccidPhone).getPage(),
                new QueryWrapper<CdIccidPhoneEntity>().lambda()
                        .lt(ObjectUtil.isNotNull(cdIccidPhone.getEndTime()),CdIccidPhoneEntity::getExpireTime,cdIccidPhone.getEndTime())
                        .orderByAsc(CdIccidPhoneEntity::getExpireTime)
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

    @Override
    public byte[] export(CdIccidPhoneDTO cdIccidPhone) {

        Assert.isTrue(ObjectUtil.isNull(cdIccidPhone.getEndTime()),"结束时间不能为空");
        List<CdIccidPhoneEntity> list = this.list(new QueryWrapper<CdIccidPhoneEntity>().lambda()
                .lt(ObjectUtil.isNotNull(cdIccidPhone.getEndTime()), CdIccidPhoneEntity::getExpireTime, cdIccidPhone.getEndTime())
                .orderByAsc(CdIccidPhoneEntity::getExpireTime));

        List<String> phones = list.stream().filter(item -> StrUtil.isNotEmpty(item.getPhone()) && item.getPhone() != "无卡").map(item -> item.getPhone().replaceFirst("66", "0")).collect(Collectors.toList());
        String collect = phones.stream().map(phone -> phone + "\n").collect(Collectors.joining());

        return StrUtil.bytes(collect);
    }

}
