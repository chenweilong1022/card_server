package io.renren.modules.ltt.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.benmanes.caffeine.cache.Cache;
import io.renren.common.utils.ConfigConstant;
import io.renren.datasources.annotation.Game;
import io.renren.modules.ltt.dto.CdCardLockDTO;
import io.renren.modules.ltt.entity.CdUserEntity;
import io.renren.modules.ltt.enums.CodeAcquisitionType;
import io.renren.modules.ltt.service.CdCardLockService;
import io.renren.modules.ltt.vo.CdCardLockVO;
import io.renren.modules.ltt.vo.GetListByIdsVO;
import io.renren.modules.sys.entity.ProjectWorkEntity;
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
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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


    @Autowired
    private CdCardLockService cdCardLockService;

    @Resource(name = "caffeineCacheProjectWorkEntity")
    private Cache<String, ProjectWorkEntity> caffeineCacheProjectWorkEntity;
    @Override
    public CdCardLockVO getDeviceIdByProjectId(CdCardLockDTO cdCardLockDTO, CdUserEntity cdUserEntity) {
        List<GetListByIdsVO> getListByIdsVOS = cdCardLockService.getListByIds(null);
        Map<Integer, List<GetListByIdsVO>> integerListMap = getListByIdsVOS.stream().filter(x -> ObjectUtil.isNotNull(x.getGroupId())).collect(Collectors.groupingBy(GetListByIdsVO::getGroupId));

        for (Integer id : integerListMap.keySet()) {
            String cacheKey = String.format("%s_%s", ConfigConstant.PROJECT_WORK_KEY, id);
            ProjectWorkEntity projectWorkEntity = caffeineCacheProjectWorkEntity.getIfPresent(cacheKey);
            if (ObjectUtil.isNull(projectWorkEntity) || !projectWorkEntity.getProjectId().equals(cdCardLockDTO.getProjectId())) {
                continue;
            }
            if(CodeAcquisitionType.CodeAcquisitionType2.getKey().equals(projectWorkEntity.getCodeAcquisitionType())) {
                continue;
            }
            CdCardLockDTO cdCardLock = new CdCardLockDTO();
            cdCardLock.setProjectId(cdCardLockDTO.getProjectId());
            List<GetListByIdsVO> changeLocks = integerListMap.get(id);
            List<String> deviceIds = changeLocks.stream().map(GetListByIdsVO::getDeviceId).collect(Collectors.toList());
            CdCardLockVO mobile = cdCardLockService.getMobile(cdCardLock, cdUserEntity, null, deviceIds);
            return mobile;
        }
        return null;
    }

}
