package io.renren.modules.ltt.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.benmanes.caffeine.cache.Cache;
import io.renren.datasources.annotation.Game;
import io.renren.modules.app.dto.TaskDto;
import io.renren.modules.ltt.enums.DeleteFlag;
import io.renren.modules.ltt.enums.Online;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.ltt.dao.CdDevicesDao;
import io.renren.modules.ltt.entity.CdDevicesEntity;
import io.renren.modules.ltt.dto.CdDevicesDTO;
import io.renren.modules.ltt.vo.CdDevicesVO;
import io.renren.modules.ltt.service.CdDevicesService;
import io.renren.modules.ltt.conver.CdDevicesConver;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


@Service("cdDevicesService")
@Game
public class CdDevicesServiceImpl extends ServiceImpl<CdDevicesDao, CdDevicesEntity> implements CdDevicesService {


    @Resource(name = "caffeineCacheCodeTaskDto")
    private Cache<String, TaskDto> caffeineCacheCodeTaskDto;

    @Override
    public PageUtils<CdDevicesVO> queryPage(CdDevicesDTO cdDevices) {
        IPage<CdDevicesEntity> page = baseMapper.selectPage(
                new Query<CdDevicesEntity>(cdDevices).getPage(),
                new QueryWrapper<CdDevicesEntity>()
        );

        return PageUtils.<CdDevicesVO>page(page).setList(CdDevicesConver.MAPPER.conver(page.getRecords()));
    }
    @Override
    public CdDevicesVO getById(Integer id) {
        return CdDevicesConver.MAPPER.conver(baseMapper.selectById(id));
    }

    @Override
    public boolean save(CdDevicesDTO cdDevices) {
        CdDevicesEntity cdDevicesEntity = CdDevicesConver.MAPPER.converDTO(cdDevices);
        return this.save(cdDevicesEntity);
    }

    @Override
    public boolean updateById(CdDevicesDTO cdDevices) {
        CdDevicesEntity cdDevicesEntity = CdDevicesConver.MAPPER.converDTO(cdDevices);
        return this.updateById(cdDevicesEntity);
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
    public CdDevicesEntity uploadDevice(CdDevicesDTO cdDevices) {
        //查询iccid
        CdDevicesEntity one = this.getOne(new QueryWrapper<CdDevicesEntity>().lambda()
                .eq(CdDevicesEntity::getIccid,cdDevices.getIccid())
        );
        //如果不存在上传设备id
        if (ObjectUtil.isNull(one)) {
            CdDevicesEntity update = new CdDevicesEntity();
            update.setIccid(cdDevices.getIccid());
            update.setOnline(Online.YES.getKey());
            update.setDeleteFlag(DeleteFlag.NO.getKey());
            update.setCreateTime(DateUtil.date());
            this.save(update);
            one = update;
        }
        return one;
    }

    @Override
    public boolean initCard(Integer[] ids) {
        List<CdDevicesEntity> cdDevicesEntities = this.listByIds(Arrays.asList(ids));
        for (CdDevicesEntity cdDevicesEntity : cdDevicesEntities) {
            //通知客戶端修改卡
            TaskDto taskDto = new TaskDto();
            taskDto.setType("initCard");
            taskDto.setDeviceId(cdDevicesEntity.getIccid());
            caffeineCacheCodeTaskDto.put(cdDevicesEntity.getIccid(),taskDto);
        }
        return true;
    }

}
