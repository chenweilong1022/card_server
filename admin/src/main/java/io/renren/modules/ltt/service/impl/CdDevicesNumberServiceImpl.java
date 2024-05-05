package io.renren.modules.ltt.service.impl;

import cn.hutool.core.collection.CollUtil;
import io.renren.datasources.annotation.Game;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.ltt.dao.CdDevicesNumberDao;
import io.renren.modules.ltt.entity.CdDevicesNumberEntity;
import io.renren.modules.ltt.dto.CdDevicesNumberDTO;
import io.renren.modules.ltt.vo.CdDevicesNumberVO;
import io.renren.modules.ltt.service.CdDevicesNumberService;
import io.renren.modules.ltt.conver.CdDevicesNumberConver;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("cdDevicesNumberService")
@Game
public class CdDevicesNumberServiceImpl extends ServiceImpl<CdDevicesNumberDao, CdDevicesNumberEntity> implements CdDevicesNumberService {

    @Override
    public PageUtils<CdDevicesNumberVO> queryPage(CdDevicesNumberDTO cdDevicesNumber) {
        IPage<CdDevicesNumberEntity> page = baseMapper.selectPage(
                new Query<CdDevicesNumberEntity>(cdDevicesNumber).getPage(),
                new QueryWrapper<CdDevicesNumberEntity>()
        );

        return PageUtils.<CdDevicesNumberVO>page(page).setList(CdDevicesNumberConver.MAPPER.conver(page.getRecords()));
    }
    @Override
    public CdDevicesNumberVO getById(Integer id) {
        return CdDevicesNumberConver.MAPPER.conver(baseMapper.selectById(id));
    }

    @Override
    public boolean save(CdDevicesNumberDTO cdDevicesNumber) {
        CdDevicesNumberEntity cdDevicesNumberEntity = CdDevicesNumberConver.MAPPER.converDTO(cdDevicesNumber);
        return this.save(cdDevicesNumberEntity);
    }

    @Override
    public boolean updateById(CdDevicesNumberDTO cdDevicesNumber) {
        CdDevicesNumberEntity cdDevicesNumberEntity = CdDevicesNumberConver.MAPPER.converDTO(cdDevicesNumber);
        return this.updateById(cdDevicesNumberEntity);
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
    public Map<String, String> getDeviceNumberById(List<String> deviceIdList) {
        if (CollUtil.isEmpty(deviceIdList)) {
            return Collections.emptyMap();
        }
        return baseMapper.selectList(new QueryWrapper<CdDevicesNumberEntity>().lambda()
                .in(CdDevicesNumberEntity::getDeviceId, deviceIdList)).stream().collect(Collectors
                .toMap(CdDevicesNumberEntity::getDeviceId, CdDevicesNumberEntity::getNumber, (a, b) -> b));
    }
}
