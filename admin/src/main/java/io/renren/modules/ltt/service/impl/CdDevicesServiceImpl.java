package io.renren.modules.ltt.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.github.benmanes.caffeine.cache.Cache;
import io.renren.datasources.annotation.Game;
import io.renren.modules.app.dto.TaskDto;
import io.renren.modules.ltt.dto.CdDevicesUpdateAppDTO;
import io.renren.modules.ltt.entity.CdCardLockEntity;
import io.renren.modules.ltt.entity.CdDevicesNumberEntity;
import io.renren.modules.ltt.enums.DeleteFlag;
import io.renren.modules.ltt.enums.Lock;
import io.renren.modules.ltt.enums.Online;
import io.renren.modules.ltt.service.CdCardLockService;
import io.renren.modules.ltt.service.CdDevicesNumberService;
import io.renren.modules.netty.codec.Invocation;
import io.renren.modules.netty.message.changecard.ChangeCardResponse;
import io.renren.modules.netty.message.initcard.InitCard2Response;
import io.renren.modules.netty.message.initcard.InitCardResponse;
import io.renren.modules.netty.message.reboot.RebootResponse;
import io.renren.modules.netty.message.updateapp.UpdateappResponse;
import io.renren.modules.netty.server.NettyChannelManager;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("cdDevicesService")
@Game
public class CdDevicesServiceImpl extends ServiceImpl<CdDevicesDao, CdDevicesEntity> implements CdDevicesService {


    @Resource(name = "caffeineCacheCodeTaskDto")
    private Cache<String, TaskDto> caffeineCacheCodeTaskDto;
    @Autowired
    private CdCardLockService cdCardLockService;
    @Autowired
    private CdDevicesService cdDevicesService;
    @Autowired
    private CdDevicesNumberService cdDevicesNumberService;
    @Autowired
    private NettyChannelManager nettyChannelManager;

    @Override
    public PageUtils<CdDevicesVO> queryPage(CdDevicesDTO cdDevices) {
        IPage<CdDevicesEntity> page = baseMapper.selectPage(
                new Query<CdDevicesEntity>(cdDevices).getPage(),
                new QueryWrapper<CdDevicesEntity>().lambda()
                        .orderByAsc(CdDevicesEntity::getNumber)
                        .eq(ObjectUtil.isNotNull(cdDevices.getOnline()),CdDevicesEntity::getOnline,cdDevices.getOnline())
        );
        //获取number
        List<CdDevicesNumberEntity> list = cdDevicesNumberService.list();
        //转为map
        Map<String, String> collect = list.stream().collect(Collectors.toMap(CdDevicesNumberEntity::getDeviceId, CdDevicesNumberEntity::getNumber));
        List<CdDevicesVO> cdDevicesVOS = CdDevicesConver.MAPPER.conver(page.getRecords());
        //设置number
        for (CdDevicesVO cdDevicesVO : cdDevicesVOS) {
            cdDevicesVO.setNumber(collect.get(cdDevicesVO.getIccid()));
        }
        return PageUtils.<CdDevicesVO>page(page).setList(cdDevicesVOS);
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
            update.setPackageVersion(cdDevices.getPackageVersion());
            update.setOnline(Online.YES.getKey());
            update.setDeleteFlag(DeleteFlag.NO.getKey());
            update.setCreateTime(DateUtil.date());
            this.save(update);
            one = update;
        }else {
            CdDevicesEntity update = new CdDevicesEntity();
            update.setId(one.getId());
            update.setPackageVersion(cdDevices.getPackageVersion());
            this.updateById(update);
        }


        CdCardLockEntity cdCardLockEntity = cdCardLockService.getOne(new QueryWrapper<CdCardLockEntity>().lambda()
                .eq(CdCardLockEntity::getDeviceId,cdDevices.getIccid())
        );
        //如果不存在上传设备id
        if (ObjectUtil.isNull(cdCardLockEntity)) {
            CdCardLockEntity update = new CdCardLockEntity();
            update.setDeviceId(cdDevices.getIccid());
            update.setLock(Lock.NO.getKey());
            update.setDeleteFlag(DeleteFlag.NO.getKey());
            update.setCreateTime(DateUtil.date());
            cdCardLockService.save(update);
        }
        return one;
    }

    @Override
    public boolean initCard(Integer[] ids) {
        List<CdDevicesEntity> cdDevicesEntities = this.listByIds(Arrays.asList(ids));
        for (CdDevicesEntity cdDevicesEntity : cdDevicesEntities) {
            InitCardResponse response = new InitCardResponse();
            response.setDeviceId(cdDevicesEntity.getIccid());
            nettyChannelManager.send(cdDevicesEntity.getIccid(),new Invocation(InitCardResponse.TYPE, response));
        }
        return true;
    }

    @Override
    public boolean initCard2(Integer[] ids) {
        List<CdDevicesEntity> cdDevicesEntities = this.listByIds(Arrays.asList(ids));
        for (CdDevicesEntity cdDevicesEntity : cdDevicesEntities) {
            InitCardResponse response = new InitCardResponse();
            response.setDeviceId(cdDevicesEntity.getIccid());
            nettyChannelManager.send(cdDevicesEntity.getIccid(),new Invocation(InitCard2Response.TYPE, response));
        }
        return true;
    }

    @Override
    public boolean changeCard(CdDevicesDTO cdDevices) {
        //通知客戶端修改卡
        ChangeCardResponse taskDto = new ChangeCardResponse();
        taskDto.setBoardIndexed(cdDevices.getBoardIndexed());
        taskDto.setIndexed(cdDevices.getIndexed());
        taskDto.setDeviceId(cdDevices.getIccid());
        nettyChannelManager.send(cdDevices.getIccid(),new Invocation(ChangeCardResponse.TYPE, taskDto));
        return true;
    }

    @Override
    public boolean updateApp(CdDevicesUpdateAppDTO updateAppDTO) {
        List<CdDevicesEntity> list = null;
        if (1 == updateAppDTO.getUpdateType()) {
            list = cdDevicesService.listByIds(Arrays.asList(updateAppDTO.getIds()));
        }else if (2 == updateAppDTO.getUpdateType()) {
            list = cdDevicesService.list();
        }

        for (CdDevicesEntity cdDevicesEntity : list) {
            //通知客戶端修改卡
            UpdateappResponse taskDto = new UpdateappResponse();
            taskDto.setDeviceId(cdDevicesEntity.getIccid());
            taskDto.setHttpUrl(updateAppDTO.getHttpUrl());
            nettyChannelManager.send(cdDevicesEntity.getIccid(),new Invocation(UpdateappResponse.TYPE, taskDto));
        }
        return true;
    }

    @Override
    public boolean reboot(Integer[] ids) {
        List<CdDevicesEntity> cdDevicesEntities = this.listByIds(Arrays.asList(ids));
        for (CdDevicesEntity cdDevicesEntity : cdDevicesEntities) {
            //手机重启
            RebootResponse taskDto = new RebootResponse();
            taskDto.setDeviceId(cdDevicesEntity.getIccid());
            nettyChannelManager.send(cdDevicesEntity.getIccid(),new Invocation(RebootResponse.TYPE, taskDto));
        }
        return true;
    }

}
