package io.renren.modules.ltt.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.github.benmanes.caffeine.cache.Cache;
import io.renren.common.utils.ConfigConstant;
import io.renren.datasources.annotation.Game;
import io.renren.modules.app.dto.TaskDto;
import io.renren.modules.ltt.dto.CdCardLockDTO;
import io.renren.modules.ltt.dto.CdDevicesInitDTO;
import io.renren.modules.ltt.dto.CdDevicesUpdateAppDTO;
import io.renren.modules.ltt.entity.*;
import io.renren.modules.ltt.enums.*;
import io.renren.modules.ltt.firefox.PhoneList;
import io.renren.modules.ltt.service.*;
import io.renren.modules.ltt.vo.GroupByDeviceIdVO;
import io.renren.modules.netty.codec.Invocation;
import io.renren.modules.netty.message.changecard.ChangeCardResponse;
import io.renren.modules.netty.message.heartbeat.HeartbeatRequest;
import io.renren.modules.netty.message.initcard.InitCard2Response;
import io.renren.modules.netty.message.initcard.InitCardResponse;
import io.renren.modules.netty.message.reboot.RebootResponse;
import io.renren.modules.netty.message.updateapp.UpdateappResponse;
import io.renren.modules.netty.server.NettyChannelManager;
import io.renren.modules.sys.entity.ProjectWorkEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.ltt.dao.CdDevicesDao;
import io.renren.modules.ltt.dto.CdDevicesDTO;
import io.renren.modules.ltt.vo.CdDevicesVO;
import io.renren.modules.ltt.conver.CdDevicesConver;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.*;
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
    @Autowired
    private CdCardService cdCardService;
    @Autowired
    private CdUserService cdUserService;
    @Resource(name = "caffeineCacheProjectWorkEntity")
    private Cache<String, ProjectWorkEntity> caffeineCacheProjectWorkEntity;
    @Resource(name = "stringHeartbeatRequestCache")
    private Cache<String, HeartbeatRequest> stringHeartbeatRequestCache;


    @Override
    public PageUtils<CdDevicesVO> queryPage(CdDevicesDTO cdDevices) {

        IPage<CdDevicesVO> page = baseMapper.listPage(new Query<CdDevicesEntity>(cdDevices).getPage(),cdDevices);
        List<GroupByDeviceIdVO> groupByDeviceIdVOS = cdCardService.groupByDeviceId();
        Map<String, GroupByDeviceIdVO> collect1 = groupByDeviceIdVOS.stream().collect(Collectors.toMap(GroupByDeviceIdVO::getDeviceId, x -> x));

        List<CdDevicesVO> cdDevicesVOS = page.getRecords();
        //设置number
        for (CdDevicesVO cdDevicesVO : cdDevicesVOS) {
            GroupByDeviceIdVO groupByDeviceIdVO = collect1.get(cdDevicesVO.getDeviceId());
            if (ObjectUtil.isNotNull(groupByDeviceIdVO)) {
                cdDevicesVO.setInitSuccessNumber(groupByDeviceIdVO.getInitSuccessNumber());
                cdDevicesVO.setInitTotalNumber(groupByDeviceIdVO.getInitTotalNumber());
                HeartbeatRequest heartbeatRequest = stringHeartbeatRequestCache.getIfPresent(cdDevicesVO.getDeviceId());
                if (ObjectUtil.isNotNull(heartbeatRequest)) {
                    cdDevicesVO.setHeartbeatRequest(heartbeatRequest);
                }
            }
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
            update.setWorkType(WorkType.WorkType1.getKey());
            this.save(update);
            one = update;
        }else {
            CdDevicesEntity update = new CdDevicesEntity();
            update.setId(one.getId());
            update.setPackageVersion(cdDevices.getPackageVersion());
            update.setCreateTime(DateUtil.date());
            if (StrUtil.isEmpty(update.getPackageVersion())) {
                update.setPackageVersion(one.getPackageVersion());
            }
            if (StrUtil.isEmpty(update.getPackageVersion())) {
                update.setPackageVersion("");
            }
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
    public boolean initCard(CdDevicesInitDTO initDTO) {
        List<CdDevicesEntity> cdDevicesEntities = this.listByIds(initDTO.getIds());
        List<CdDevicesEntity> updates = new ArrayList<>();
        for (CdDevicesEntity cdDevicesEntity : cdDevicesEntities) {
            InitCardResponse response = new InitCardResponse();
            response.setDeviceId(cdDevicesEntity.getIccid());
            response.setUssd(initDTO.getUssd());
            if (InitType.InitType1.getKey().equals(initDTO.getType())) {
                nettyChannelManager.send(cdDevicesEntity.getIccid(),new Invocation(InitCardResponse.TYPE, response));
            }else if (InitType.InitType2.getKey().equals(initDTO.getType())) {
                nettyChannelManager.send(cdDevicesEntity.getIccid(),new Invocation(InitCard2Response.TYPE, response));
            }
            CdDevicesEntity update = new CdDevicesEntity();
            update.setId(cdDevicesEntity.getId());
            update.setWorkType(WorkType.WorkType2.getKey());
            updates.add(update);
        }
        //批量修改为初始化状态
        this.updateBatchById(updates);
        return true;
    }

//    @Override
//    public boolean initCard2(Integer[] ids) {
//        List<CdDevicesEntity> cdDevicesEntities = this.listByIds(Arrays.asList(ids));
//        List<CdDevicesEntity> updates = new ArrayList<>();
//        for (CdDevicesEntity cdDevicesEntity : cdDevicesEntities) {
//            InitCardResponse response = new InitCardResponse();
//            response.setDeviceId(cdDevicesEntity.getIccid());
//            nettyChannelManager.send(cdDevicesEntity.getIccid(),new Invocation(InitCard2Response.TYPE, response));
//            CdDevicesEntity update = new CdDevicesEntity();
//            update.setId(cdDevicesEntity.getId());
//            update.setWorkType(WorkType.WorkType2.getKey());
//            updates.add(update);
//        }
//        //批量修改为初始化状态
//        this.updateBatchById(updates);
//        return true;
//    }

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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean initCard3(Integer[] ids) {
        try{
            cdCardLockService.init3(ids);
        }catch (Exception e) {

        }
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean withBlack(Integer[] ids) {
        if (ArrayUtil.isEmpty(ids)) {
            return false;
        }
        ProjectWorkEntity projectWorkEntity = caffeineCacheProjectWorkEntity.getIfPresent(ConfigConstant.PROJECT_WORK_KEY);
        CdUserEntity userEntity = cdUserService.getById((Serializable) projectWorkEntity.getUserId());
        List<CdCardLockEntity> changeLocks = cdCardLockService.list(new QueryWrapper<CdCardLockEntity>().lambda()
                .in(CdCardLockEntity::getId,ids)
        );
        for (CdCardLockEntity cdCardLockEntity : changeLocks) {
            CdCardLockDTO cdCardLock = new CdCardLockDTO();
            cdCardLock.setCode("拉黑");
            cdCardLock.setDeviceId(cdCardLockEntity.getDeviceId());
            cdCardLock.setProjectId(cdCardLockEntity.getProjectId());
            boolean b = cdCardLockService.uploadSms(cdCardLock, userEntity);
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void phoneDeleteAll(Integer[] ids) {
        ProjectWorkEntity projectWorkEntity = caffeineCacheProjectWorkEntity.getIfPresent(ConfigConstant.PROJECT_WORK_KEY);
        Integer userId = projectWorkEntity.getUserId();
        List<PhoneList> phoneLists = new ArrayList<>();
        List<CdCardLockEntity> list = cdCardLockService.list(new QueryWrapper<CdCardLockEntity>().lambda()
                .in(CdCardLockEntity::getId,ids)
        );
        CdUserEntity userEntity = cdUserService.getById((Serializable) userId);
        for (CdCardLockEntity cdCardLockEntity : list) {
            if (ObjectUtil.isNotNull(cdCardLockEntity) && StrUtil.isNotEmpty(cdCardLockEntity.getIccid())) {
                PhoneList phoneList = new PhoneList("tha",cdCardLockEntity.getPhone().replace(projectWorkEntity.getPhonePre(),""));
                phoneLists.add(phoneList);
                CdCardLockDTO cdCardLockDTO = new CdCardLockDTO();
                cdCardLockDTO.setProjectId(cdCardLockEntity.getProjectId());
                cdCardLockDTO.setIccid(cdCardLockEntity.getIccid());
                boolean b = cdCardLockService.releaseMobile(cdCardLockDTO, userEntity);
            }
        }
        //火狐狸把当前项目的号码释放掉
        cdCardLockService.extracted(phoneLists,"PhoneDeleteBatch");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBatch(CdDevicesDTO cdDevices) {
        List<CdDevicesEntity> cdDevicesEntities = new ArrayList<>();
        //设备id
        List<Integer> ids = cdDevices.getIds();
        for (Integer id : ids) {
            CdDevicesEntity cdDevicesEntity = new CdDevicesEntity();
            cdDevicesEntity.setId(id);
            cdDevicesEntity.setWorkType(cdDevices.getWorkType());
            cdDevicesEntities.add(cdDevicesEntity);
        }
        this.updateBatchById(cdDevicesEntities);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean changeGroup(CdDevicesDTO cdDevices) {
        List<Integer> ids = cdDevices.getIds();
        List<CdDevicesEntity> cdDevicesEntities = new ArrayList<>();
        for (Integer id : ids) {
            CdDevicesEntity cdDevicesEntity = new CdDevicesEntity();
            cdDevicesEntity.setId(id);
            cdDevicesEntity.setGroupId(cdDevices.getGroupId());
            cdDevicesEntities.add(cdDevicesEntity);
        }
        return this.updateBatchById(cdDevicesEntities);
    }

}
