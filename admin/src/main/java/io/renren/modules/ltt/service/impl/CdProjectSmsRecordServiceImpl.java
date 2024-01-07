package io.renren.modules.ltt.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Lists;
import io.renren.common.validator.Assert;
import io.renren.datasources.annotation.Admin;
import io.renren.datasources.annotation.Game;
import io.renren.modules.ltt.entity.CdDevicesEntity;
import io.renren.modules.ltt.enums.ClearType;
import io.renren.modules.ltt.firefox.PhoneList;
import io.renren.modules.ltt.service.CdCardGroupService;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


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

    @Autowired
    private CdCardGroupService cdCardGroupService;
    @Autowired
    private CdDevicesServiceImpl cdDevicesService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void noClearReplyCode(CdProjectSmsRecordDTO cdProjectSmsRecord) {
        List<CdDevicesEntity> list = cdDevicesService.list(new QueryWrapper<CdDevicesEntity>().lambda()
                .eq(CdDevicesEntity::getGroupId,cdProjectSmsRecord.getId())
        );

        Assert.isTrue(CollUtil.isEmpty(list),"该分组下没有设备");
        List<String> iccids = list.stream().map(CdDevicesEntity::getIccid).collect(Collectors.toList());


        List<List<String>> partition = Lists.partition(iccids, 59);
        for (List<String> lists : partition) {
            if (ClearType.CLEAR_TYPE1.getKey().equals(cdProjectSmsRecord.getClearType())) {
                this.remove(new QueryWrapper<CdProjectSmsRecordEntity>().lambda()
                        .eq(CdProjectSmsRecordEntity::getProjectId,cdProjectSmsRecord.getProjectId())
                        .eq(CdProjectSmsRecordEntity::getCode,"拉黑")
                        .in(CdProjectSmsRecordEntity::getDeviceId,lists)
                );
            }else if (ClearType.CLEAR_TYPE2.getKey().equals(cdProjectSmsRecord.getClearType())) {
                this.remove(new QueryWrapper<CdProjectSmsRecordEntity>().lambda()
                        .eq(CdProjectSmsRecordEntity::getProjectId,cdProjectSmsRecord.getProjectId())
                        .in(CdProjectSmsRecordEntity::getDeviceId,lists)
                );
            }
        }
    }

}
