package io.renren.modules.ltt.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.benmanes.caffeine.cache.Cache;
import io.renren.common.validator.Assert;
import io.renren.datasources.annotation.Game;
import io.renren.modules.ltt.enums.ExpireTimeStatus;
import io.renren.modules.ltt.enums.ExportStatus;
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
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


@Service("cdIccidPhoneService")
@Game
public class CdIccidPhoneServiceImpl extends ServiceImpl<CdIccidPhoneDao, CdIccidPhoneEntity> implements CdIccidPhoneService {

    @Override
    public PageUtils<CdIccidPhoneVO> queryPage(CdIccidPhoneDTO cdIccidPhone) {

        LambdaQueryWrapper<CdIccidPhoneEntity> cdIccidPhoneEntityLambdaQueryWrapper = new QueryWrapper<CdIccidPhoneEntity>().lambda()
                .eq(ObjectUtil.isNotNull(cdIccidPhone.getExportStatus()), CdIccidPhoneEntity::getExportStatus, cdIccidPhone.getExportStatus())
                .lt(ObjectUtil.isNotNull(cdIccidPhone.getEndTime()), CdIccidPhoneEntity::getExpireTime, cdIccidPhone.getEndTime())
                .orderByAsc(CdIccidPhoneEntity::getExpireTime);
        //如果是没有时间查询没有时间的
        if (ExpireTimeStatus.NO.getKey().equals(cdIccidPhone.getExpireTimeStatus())) {
            cdIccidPhoneEntityLambdaQueryWrapper.isNull(CdIccidPhoneEntity::getExpireTime);
        } else if (ExpireTimeStatus.YES.getKey().equals(cdIccidPhone.getExpireTimeStatus())) {
            cdIccidPhoneEntityLambdaQueryWrapper.isNotNull(CdIccidPhoneEntity::getExpireTime);
        }
        IPage<CdIccidPhoneEntity> page = baseMapper.selectPage(
                new Query<CdIccidPhoneEntity>(cdIccidPhone).getPage(),
                cdIccidPhoneEntityLambdaQueryWrapper
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


    @Resource(name = "caffeineCacheSet")
    private Cache<String, HashSet<String>> caffeineCacheSet;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public byte[] export(CdIccidPhoneDTO cdIccidPhone) {
        if (ExpireTimeStatus.YES.getKey().equals(cdIccidPhone.getExpireTimeStatus())) {
            Assert.isTrue(ObjectUtil.isNull(cdIccidPhone.getEndTime()),"结束时间不能为空");
        }
        LambdaQueryWrapper<CdIccidPhoneEntity> cdIccidPhoneEntityLambdaQueryWrapper = new QueryWrapper<CdIccidPhoneEntity>().lambda()
                .eq(ObjectUtil.isNotNull(cdIccidPhone.getExportStatus()), CdIccidPhoneEntity::getExportStatus, cdIccidPhone.getExportStatus())
                .lt(ObjectUtil.isNotNull(cdIccidPhone.getEndTime()), CdIccidPhoneEntity::getExpireTime, cdIccidPhone.getEndTime())
                .orderByAsc(CdIccidPhoneEntity::getExpireTime);
        //如果是没有时间查询没有时间的
        if (ExpireTimeStatus.NO.getKey().equals(cdIccidPhone.getExpireTimeStatus())) {
            cdIccidPhoneEntityLambdaQueryWrapper.isNull(CdIccidPhoneEntity::getExpireTime);
        } else if (ExpireTimeStatus.YES.getKey().equals(cdIccidPhone.getExpireTimeStatus())) {
            cdIccidPhoneEntityLambdaQueryWrapper.isNotNull(CdIccidPhoneEntity::getExpireTime);
        }

        List<CdIccidPhoneEntity> list = this.list(cdIccidPhoneEntityLambdaQueryWrapper);

        List<CdIccidPhoneEntity> updates = new ArrayList<>();
        for (CdIccidPhoneEntity cdIccidPhoneEntity : list) {
            CdIccidPhoneEntity update = new CdIccidPhoneEntity();
            update.setId(cdIccidPhoneEntity.getId());
            update.setExportStatus(ExportStatus.ExportStatus2.getKey());
            updates.add(update);
        }

        this.updateBatchById(updates);
        List<String> exportList = new ArrayList<>();
        //剔除已经充值过的手机号
        extracted(list, exportList);
        String collect = exportList.stream().map(phone -> phone + "\n").collect(Collectors.joining());
        return StrUtil.bytes(collect);
    }

    private void extracted(List<CdIccidPhoneEntity> list, List<String> exportList) {
        List<String> phones = list.stream().filter(item -> StrUtil.isNotEmpty(item.getPhone()) && item.getPhone() != "无卡").map(item -> item.getPhone().replaceFirst("66", "0")).collect(Collectors.toList());
        HashSet<String> cacheSet = caffeineCacheSet.getIfPresent("caffeineCacheSet");
        for (String phone : phones) {
            String newPhone = phone.replaceFirst("0", "");
            boolean contains = cacheSet.contains(newPhone);
            if (!contains) {
                exportList.add(phone);
            }
        }
    }

}
