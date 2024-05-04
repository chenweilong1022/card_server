package io.renren.modules.ltt.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.benmanes.caffeine.cache.Cache;
import io.renren.common.utils.ConfigConstant;
import io.renren.common.utils.StrUtils;
import io.renren.common.validator.Assert;
import io.renren.datasources.annotation.Game;
import io.renren.modules.ltt.dao.CdCardDao;
import io.renren.modules.ltt.dto.CdCardGroupExportPhoneTxtDTO;
import io.renren.modules.ltt.dto.CdCardLockDTO;
import io.renren.modules.ltt.entity.CdUserEntity;
import io.renren.modules.ltt.enums.CodeAcquisitionType;
import io.renren.modules.ltt.enums.Lock;
import io.renren.modules.ltt.service.CdCardLockService;
import io.renren.modules.ltt.vo.CdCardLockVO;
import io.renren.modules.ltt.vo.GroupByDeviceIdVO;
import io.renren.modules.sys.entity.ProjectWorkEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("cdCardGroupService")
@Game
@Slf4j
public class CdCardGroupServiceImpl extends ServiceImpl<CdCardGroupDao, CdCardGroupEntity> implements CdCardGroupService {


    @Resource(name = "caffeineCacheProjectWorkEntity")
    private Cache<String, ProjectWorkEntity> caffeineCacheProjectWorkEntity;

    @Autowired
    private CdCardDao cdCardDao;

    @Override
    public PageUtils<CdCardGroupVO> queryPage(CdCardGroupDTO cdCardGroup) {
        IPage<CdCardGroupEntity> page = baseMapper.selectPage(
                new Query<CdCardGroupEntity>(cdCardGroup).getPage(),
                new QueryWrapper<CdCardGroupEntity>()
        );
        List<CdCardGroupVO> cardGroupVOS = CdCardGroupConver.MAPPER.conver(page.getRecords());
        List<GroupByDeviceIdVO> groupByDeviceIdVOS = cdCardDao.groupByGroupId();
        Map<Integer, GroupByDeviceIdVO> collect = groupByDeviceIdVOS.stream().collect(Collectors.toMap(GroupByDeviceIdVO::getGroupId, x -> x));
        for (CdCardGroupVO cardGroupVO : cardGroupVOS) {
            String cacheKey = String.format("%s_%s", ConfigConstant.PROJECT_WORK_KEY, cardGroupVO.getId());
            ProjectWorkEntity projectWorkEntity = caffeineCacheProjectWorkEntity.getIfPresent(cacheKey);
            if (ObjectUtil.isNotNull(projectWorkEntity)) {
                cardGroupVO.setProjectWorkEntity(projectWorkEntity);
            }
            GroupByDeviceIdVO groupByDeviceIdVO = collect.get(cardGroupVO.getId());
            if (ObjectUtil.isNotNull(groupByDeviceIdVO)) {
                cardGroupVO.setGroupByDeviceIdVO(groupByDeviceIdVO);
            }
        }
        return PageUtils.<CdCardGroupVO>page(page).setList(cardGroupVOS);
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


    @Autowired
    private CdCardLockService cdCardLockService;

    @Override
    public CdCardLockVO getDeviceIdByProjectId(CdCardLockDTO cdCardLockDTO, CdUserEntity cdUserEntity) {
        Map<String, ProjectWorkEntity> configMap = caffeineCacheProjectWorkEntity.asMap();
        Assert.isNull(configMap, "未加载完成，请稍后再试");

        Integer groupId;
        String cacheKey;
        List<String> deviceIds;
        ProjectWorkEntity projectWorkEntity;
        for (Map.Entry<String, ProjectWorkEntity> config : configMap.entrySet()) {
            cacheKey = config.getKey();
            projectWorkEntity = config.getValue();
            if (!projectWorkEntity.getProjectId().equals(cdCardLockDTO.getProjectId())) {
                continue;
            }
            //判断类型为：自己注册
            if (CodeAcquisitionType.CodeAcquisitionType2.getKey().equals(projectWorkEntity.getCodeAcquisitionType())
                    || CodeAcquisitionType.CodeAcquisitionType1.getKey().equals(projectWorkEntity.getCodeAcquisitionType())) {
                continue;
            }

            groupId = StrUtils.strToNumber(StrUtils.subStr(cacheKey, ConfigConstant.PROJECT_WORK_KEY + "_"));
            if (ObjectUtil.isNull(groupId)) {
                log.error("getDeviceIdByProjectId_error_cache {}, {}", cacheKey, projectWorkEntity);
                continue;
            }

            //获取分组下的设备id
            deviceIds = cdCardLockService.getDeviceByGroupId(groupId, Lock.NO.getKey());
            if (CollUtil.isEmpty(deviceIds)) {
                log.error("getDeviceIdByProjectId_error_deviceIsNull {}", groupId);
                continue;
            }

            //获取手机号
            CdCardLockDTO cdCardLock = new CdCardLockDTO();
            cdCardLock.setProjectId(cdCardLockDTO.getProjectId());
            cdCardLock.setNumberSegment(cdCardLockDTO.getNumberSegment());
            CdCardLockVO mobile = cdCardLockService.getMobile(cdCardLock, cdUserEntity, null, deviceIds);
            if (ObjectUtil.isNotNull(mobile)) {
                return mobile;
            }
        }
        log.error("getDeviceIdByProjectId_error_noMobile {}, {}", cdCardLockDTO, configMap);
        return null;
    }

    @Override
    public byte[] export(CdCardGroupExportPhoneTxtDTO exportPhoneTxtDTO) {
        List<String> strings = cdCardDao.listPhoneByGroupId(exportPhoneTxtDTO.getId());
        String collect = strings.stream().map(phone -> phone + "\n").collect(Collectors.joining());
        return StrUtil.bytes(collect);
    }

    @Override
    public Map<Integer, String> getGroupNameById(List<Integer> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyMap();
        }
        return baseMapper.selectList(new QueryWrapper<CdCardGroupEntity>().lambda()
                .in(CdCardGroupEntity::getId, ids)).stream().collect(Collectors
                .toMap(CdCardGroupEntity::getId, CdCardGroupEntity::getGroupName));
    }

}
