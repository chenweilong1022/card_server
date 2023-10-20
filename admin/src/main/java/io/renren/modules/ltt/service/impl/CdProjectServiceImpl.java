package io.renren.modules.ltt.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import io.renren.datasources.annotation.Game;
import io.renren.modules.ltt.entity.CdCountryEntity;
import io.renren.modules.ltt.enums.AuditStatus;
import io.renren.modules.ltt.enums.DeleteFlag;
import io.renren.modules.ltt.service.CdCountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.ltt.dao.CdProjectDao;
import io.renren.modules.ltt.entity.CdProjectEntity;
import io.renren.modules.ltt.dto.CdProjectDTO;
import io.renren.modules.ltt.vo.CdProjectVO;
import io.renren.modules.ltt.service.CdProjectService;
import io.renren.modules.ltt.conver.CdProjectConver;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service("cdProjectService")
@Game
public class CdProjectServiceImpl extends ServiceImpl<CdProjectDao, CdProjectEntity> implements CdProjectService {


    @Autowired
    private CdCountryService cdCountryService;


    @Override
    public PageUtils<CdProjectVO> queryPage(CdProjectDTO cdProject) {
        IPage<CdProjectEntity> page = baseMapper.selectPage(
                new Query<CdProjectEntity>(cdProject).getPage(),
                new QueryWrapper<CdProjectEntity>().lambda()
                        .eq(ObjectUtil.isNotNull(cdProject.getCountryId()),CdProjectEntity::getCountryId,cdProject.getCountryId())
                        .eq(ObjectUtil.isNotNull(cdProject.getAuditStatus()),CdProjectEntity::getAuditStatus,cdProject.getAuditStatus())
                        .eq(StrUtil.isNotEmpty(cdProject.getName()),CdProjectEntity::getName,cdProject.getName())
        );

        List<CdProjectVO> conver = CdProjectConver.MAPPER.conver(page.getRecords());
        setCountryName(conver);
        return PageUtils.<CdProjectVO>page(page).setList(conver);
    }

    private void setCountryName(List<CdProjectVO> conver) {
        List<CdCountryEntity> list = cdCountryService.list();
        for (CdProjectVO cdProjectVO : conver) {
            for (CdCountryEntity cdCountryEntity : list) {
                if (cdProjectVO.getCountryId().equals(cdCountryEntity.getId())) {
                    cdProjectVO.setCountryName(cdCountryEntity.getName());
                }
            }
        }
    }

    @Override
    public CdProjectVO getById(Integer id) {
        return CdProjectConver.MAPPER.conver(baseMapper.selectById(id));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean save(CdProjectDTO cdProject) {
        cdProject.setDeleteFlag(DeleteFlag.NO.getKey());
        cdProject.setCreateTime(DateUtil.date());
        cdProject.setAuditStatus(AuditStatus.AuditStatus1.getKey());
        CdProjectEntity cdProjectEntity = CdProjectConver.MAPPER.converDTO(cdProject);
        return this.save(cdProjectEntity);
    }

    @Override
    public boolean updateById(CdProjectDTO cdProject) {
        CdProjectEntity cdProjectEntity = CdProjectConver.MAPPER.converDTO(cdProject);
        return this.updateById(cdProjectEntity);
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
    @Transactional(rollbackFor = Exception.class)
    public void audit(CdProjectDTO cdProject) {
        //审核
        CdProjectEntity audit = new CdProjectEntity();
        audit.setId(cdProject.getId());
        audit.setAuditStatus(cdProject.getAuditStatus());
        this.updateById(audit);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void auditAll(CdProjectDTO cdProject) {
        Integer[] ids = cdProject.getIds();

        List<CdProjectEntity> cdProjectEntities = new ArrayList<>();
        for (Integer id : ids) {
            CdProjectEntity audit = new CdProjectEntity();
            audit.setId(id);
            audit.setAuditStatus(cdProject.getAuditStatus());
            cdProjectEntities.add(audit);
        }
        this.updateBatchById(cdProjectEntities);
    }

}
