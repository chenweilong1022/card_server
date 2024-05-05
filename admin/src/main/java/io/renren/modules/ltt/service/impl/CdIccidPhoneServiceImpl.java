package io.renren.modules.ltt.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.benmanes.caffeine.cache.Cache;
import io.renren.common.validator.Assert;
import io.renren.datasources.annotation.Game;
import io.renren.modules.ltt.entity.CdRechargedPhoneEntity;
import io.renren.modules.ltt.enums.ExpireTimeStatus;
import io.renren.modules.ltt.enums.ExportStatus;
import io.renren.modules.ltt.service.CdCardGroupService;
import io.renren.modules.ltt.service.CdDevicesNumberService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
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
import java.util.*;
import java.util.stream.Collectors;


@Service("cdIccidPhoneService")
@Game
@Slf4j
public class CdIccidPhoneServiceImpl extends ServiceImpl<CdIccidPhoneDao, CdIccidPhoneEntity> implements CdIccidPhoneService {

    @Resource
    private CdCardGroupService cdCardGroupService;

    @Resource
    private CdDevicesNumberService cdDevicesNumberService;

    @Override
    public PageUtils<CdIccidPhoneVO> queryPage(CdIccidPhoneDTO cdIccidPhone) {
        cdIccidPhone.setPageStart((cdIccidPhone.getPage() - 1) * cdIccidPhone.getLimit());
        Integer count = baseMapper.queryPageCount(cdIccidPhone);
        List<CdIccidPhoneVO> resultList = Collections.emptyList();
        if (count > 0) {
            resultList = baseMapper.queryPage(cdIccidPhone);
        }

        if (CollectionUtil.isNotEmpty(resultList)) {
            List<Integer> groupIdList = resultList.stream().filter(i -> i.getGroupId() != null)
                    .map(CdIccidPhoneVO::getGroupId).distinct().collect(Collectors.toList());

            //查询分组名称
            Map<Integer, String> groupNameMap = cdCardGroupService.getGroupNameById(groupIdList);

            //设备编号名称
            List<String> deviceIdList = resultList.stream().filter(i -> StringUtils.isNotEmpty(i.getDeviceId()))
                    .map(CdIccidPhoneVO::getDeviceId).distinct().collect(Collectors.toList());
            Map<String, String> deviceNumberMap = cdDevicesNumberService.getDeviceNumberById(deviceIdList);

            for (CdIccidPhoneVO cdIccidPhoneVO : resultList) {
                if (cdIccidPhoneVO.getGroupId()!=null){
                    cdIccidPhoneVO.setGroupName(groupNameMap.get(cdIccidPhoneVO.getGroupId()));
                }
                if (StringUtils.isNotEmpty(cdIccidPhoneVO.getDeviceId())){
                    cdIccidPhoneVO.setDeviceNumber(deviceNumberMap.get(cdIccidPhoneVO.getDeviceId()));
                }
            }

        }

        return new PageUtils(resultList, count, cdIccidPhone.getLimit(), cdIccidPhone.getPage());
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


    @Resource(name = "mapCache")
    private Cache<String, Map<String,String>> mapCache;
    @Resource(name = "mapDateCache")
    private Cache<String, Map<String,Date>> mapDateCache;

    //缓存所有已经充值过的手机号
    @EventListener
    @Order(value = 9998)
    public void handlerApplicationReadyEvent1(ApplicationReadyEvent event) {
        log.info("123");
        List<CdIccidPhoneEntity> list = this.list();
        log.info("456");
        Map<String, String> stringStringMap = list.stream().collect(Collectors.toMap(CdIccidPhoneEntity::getIccid, CdIccidPhoneEntity::getPhone,(a,b) -> a));
        mapCache.put("mapCache",stringStringMap);

        Map<String, Date> collect = list.stream().filter(item -> ObjectUtil.isNotNull(item.getExpireTime())).collect(Collectors.toMap(CdIccidPhoneEntity::getIccid, CdIccidPhoneEntity::getExpireTime,(a,b) -> a));
        mapDateCache.put("mapDateCache",collect);
    }

}
