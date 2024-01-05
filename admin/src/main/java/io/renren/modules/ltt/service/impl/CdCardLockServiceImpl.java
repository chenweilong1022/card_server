package io.renren.modules.ltt.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.google.common.collect.Lists;
import io.renren.common.utils.ConfigConstant;
import io.renren.common.validator.Assert;
import io.renren.datasources.annotation.Game;
import io.renren.modules.app.dto.TaskDto;
import io.renren.modules.ltt.entity.*;
import io.renren.modules.ltt.enums.*;
import io.renren.modules.ltt.firefox.*;
import io.renren.modules.ltt.service.*;
import io.renren.modules.ltt.vo.CdProjectVO;
import io.renren.modules.ltt.vo.GetListByIdsVO;
import io.renren.modules.netty.codec.Invocation;
import io.renren.modules.netty.message.changecard.ChangeCardResponse;
import io.renren.modules.netty.server.NettyChannelManager;
import io.renren.modules.sys.entity.ProjectWorkEntity;
import io.renren.modules.sys.entity.SysConfigEntity;
import io.renren.modules.sys.service.SysConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.ltt.dao.CdCardLockDao;
import io.renren.modules.ltt.dto.CdCardLockDTO;
import io.renren.modules.ltt.vo.CdCardLockVO;
import io.renren.modules.ltt.conver.CdCardLockConver;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Service("cdCardLockService")
@Game
@Slf4j
public class CdCardLockServiceImpl extends ServiceImpl<CdCardLockDao, CdCardLockEntity> implements CdCardLockService {


    @Resource(name = "caffeineCacheProjectWorkEntity")
    private Cache<String, ProjectWorkEntity> caffeineCacheProjectWorkEntity;
    @Autowired
    private CdProjectService cdProjectService;
    @Autowired
    private CdDevicesService cdDevicesService;
    @Autowired
    private CdCardService cdCardService;
    @Autowired
    private SysConfigService sysConfigService;

    @Autowired
    private CdProjectSmsRecordService cdProjectSmsRecordService;
    @Autowired
    private CdIccidPhoneService cdIccidPhoneService;
    @Autowired
    private NettyChannelManager nettyChannelManager;
    @Resource(name = "caffeineCacheCodeTaskDto")
    private Cache<String, TaskDto> caffeineCacheCodeTaskDto;
    @Autowired
    private CdUserService cdUserService;

    @Override
    public PageUtils<CdCardLockVO> queryPage(CdCardLockDTO cdCardLock) {
        IPage<CdCardLockEntity> page = baseMapper.selectPage(
                new Query<CdCardLockEntity>(cdCardLock).getPage(),
                new QueryWrapper<CdCardLockEntity>()
        );

        return PageUtils.<CdCardLockVO>page(page).setList(CdCardLockConver.MAPPER.conver(page.getRecords()));
    }
    @Override
    public CdCardLockVO getById(Integer id) {
        return CdCardLockConver.MAPPER.conver(baseMapper.selectById(id));
    }

    @Override
    public boolean save(CdCardLockDTO cdCardLock) {
        CdCardLockEntity cdCardLockEntity = CdCardLockConver.MAPPER.converDTO(cdCardLock);
        return this.save(cdCardLockEntity);
    }

    @Override
    public boolean updateById(CdCardLockDTO cdCardLock) {
        CdCardLockEntity cdCardLockEntity = CdCardLockConver.MAPPER.converDTO(cdCardLock);
        return this.updateById(cdCardLockEntity);
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
    public CdCardLockVO getMobile(CdCardLockDTO cdCardLock, CdUserEntity cdUserEntity,String deviceId,List<String> deviceIds) {
        //获取项目
        CdProjectVO cdProjectVO = cdProjectService.getById(cdCardLock.getProjectId());
        Assert.isNull(cdProjectVO,"ProjectDoesNotExist");
        Assert.isNull(cdUserEntity,"PleaseLogIn");
        //判断用户余额是否充足
        Assert.isTrue(cdProjectVO.getPrice().doubleValue() > cdUserEntity.getBalance().doubleValue(),"InsufficientBalance");
        //获取可以使用的设备
        List<CdCardLockEntity> list = this.list(new QueryWrapper<CdCardLockEntity>().lambda()
                .eq(CdCardLockEntity::getLock, Lock.NO.getKey())
                .eq(StrUtil.isNotEmpty(deviceId),CdCardLockEntity::getDeviceId,deviceId)
                .in(CollUtil.isNotEmpty(deviceIds),CdCardLockEntity::getDeviceId,deviceIds)
        );
        //没有可用的设备
        Assert.isTrue(CollUtil.isEmpty(list),"NoEquipmentAvailable");
        //设备没有初始化
        Assert.isTrue(CollUtil.isEmpty(list),"DeviceIsNotInitialized");
        //循环可以使用的设备
        for (CdCardLockEntity cdCardLockEntity : list) {

            CdDevicesEntity one1 = cdDevicesService.getOne(new QueryWrapper<CdDevicesEntity>().lambda()
                    .eq(CdDevicesEntity::getIccid,cdCardLockEntity.getDeviceId())
            );
            if (ObjectUtil.isNull(one1)) {
                continue;
            }
            if (ObjectUtil.isNotNull(one1) && Online.NO.getKey().equals(one1.getOnline())) {
                continue;
            }

            if (!WorkType.WorkType3.getKey().equals(one1.getWorkType())) {
                continue;
            }

            //获取设备下所有的信息列表 所有卡的信息
            List<CdCardEntity> cdCardEntities = cdCardService.list(new QueryWrapper<CdCardEntity>().lambda()
                    .eq(CdCardEntity::getDeviceId,cdCardLockEntity.getDeviceId())
                    .notIn(CdCardEntity::getIccid,"无卡")
                    .notIn(CdCardEntity::getPhone,"无卡")
            );
            //获取这个设备下这个项目所有记录
            List<CdProjectSmsRecordEntity> cdProjectSmsRecordEntities = cdProjectSmsRecordService.list(new QueryWrapper<CdProjectSmsRecordEntity>().lambda()
                    .eq(CdProjectSmsRecordEntity::getProjectId,cdCardLock.getProjectId())
                    .eq(CdProjectSmsRecordEntity::getDeviceId,cdCardLockEntity.getDeviceId())
            );

            //已经使用的id
            List<String> iccids = cdProjectSmsRecordEntities.stream().map(CdProjectSmsRecordEntity::getIccid).collect(Collectors.toList());

            if (StrUtil.isNotEmpty(cdCardLock.getNumberSegment())) {
                cdCardEntities = cdCardEntities.stream().filter(cdCardEntity -> cdCardEntity.getPhone().startsWith(cdCardLock.getNumberSegment())).collect(Collectors.toList());
                if (CollUtil.isNotEmpty(cdCardEntities) && 1 == cdCardEntities.size()) {
                    CdCardEntity cdCardEntity = cdCardEntities.get(0);
                    if (cdCardEntity.getPhone().equals(cdCardLock.getNumberSegment())) {
                        iccids = CollUtil.newArrayList();
                    }
                }
            }

            long count = iccids.stream().distinct().count();
            if (cdCardEntities.size() <= count) {
                continue;
            }

            for (CdCardEntity cdDevicesEntity : cdCardEntities) {
                if (iccids.contains(cdDevicesEntity.getIccid()) || StrUtil.isEmpty(cdDevicesEntity.getPhone())) {
                    continue;
                }else {
                    //将当前手机上锁
                    CdCardLockEntity update = new CdCardLockEntity();
                    if (StrUtil.isEmpty(cdDevicesEntity.getPhone())) {
                        CdIccidPhoneEntity one = cdIccidPhoneService.getOne(new QueryWrapper<CdIccidPhoneEntity>().lambda()
                                .eq(CdIccidPhoneEntity::getIccid,cdDevicesEntity.getIccid())
                        );
                        update.setPhone(one.getPhone());
                    }else {
                        update.setPhone(cdDevicesEntity.getPhone());
                    }
                    //直接扣除金额
                    BigDecimal after = cdUserEntity.getBalance().subtract(cdProjectVO.getPrice());
                    cdUserEntity.setBalance(after);
                    cdUserService.updateById(cdUserEntity);

                    update.setId(cdCardLockEntity.getId());
                    update.setUserId(cdUserEntity.getId());
                    update.setProjectId(cdCardLock.getProjectId());
                    update.setDeviceId(cdDevicesEntity.getDeviceId());
                    update.setLock(Lock.YES.getKey());
                    update.setIccid(cdDevicesEntity.getIccid());
                    update.setDeleteFlag(DeleteFlag.NO.getKey());
                    update.setCreateTime(DateUtil.date());
                    this.updateById(update);
                    //通知客戶端修改卡
                    ChangeCardResponse taskDto = new ChangeCardResponse();
                    taskDto.setBoardIndexed(cdDevicesEntity.getBoardIndexed());
                    taskDto.setIndexed(cdDevicesEntity.getIndexed());
                    taskDto.setDeviceId(cdDevicesEntity.getIccid());
                    nettyChannelManager.send(cdDevicesEntity.getDeviceId(),new Invocation(ChangeCardResponse.TYPE, taskDto));

                    return new CdCardLockVO().setPhone(update.getPhone()).setIccid(update.getIccid());
                }
            }
        }
        return null;
    }

    @Override
    public CdCardLockVO getMobile2(CdCardLockDTO cdCardLock, CdUserEntity cdUserEntity, String deviceId) {

        //获取设备
        List<CdCardLockEntity> list = this.list(new QueryWrapper<CdCardLockEntity>().lambda()
                .eq(StrUtil.isNotEmpty(deviceId),CdCardLockEntity::getDeviceId,deviceId)
        );
        //如果设备为空返回null
        if (CollUtil.isEmpty(list)) {
            return null;
        }
        //如果设备不在线，跳出循环
        for (CdCardLockEntity cdCardLockEntity : list) {
            log.info("cdCardLockEntity = {}",JSONUtil.toJsonStr(cdCardLockEntity));
            CdDevicesEntity one1 = cdDevicesService.getOne(new QueryWrapper<CdDevicesEntity>().lambda()
                    .eq(CdDevicesEntity::getIccid,cdCardLockEntity.getDeviceId())
            );
            if (ObjectUtil.isNull(one1)) {
                continue;
            }
            log.info("one1 = {}",JSONUtil.toJsonStr(one1));
            if (ObjectUtil.isNotNull(one1) && Online.NO.getKey().equals(one1.getOnline())) {
                continue;
            }
            if (!WorkType.WorkType3.getKey().equals(one1.getWorkType())) {
                continue;
            }
            //获取设备下所有的信息列表 所有卡的信息
            List<CdCardEntity> cdCardEntities = cdCardService.list(new QueryWrapper<CdCardEntity>().lambda()
                    .eq(CdCardEntity::getDeviceId,cdCardLockEntity.getDeviceId())
                    .eq(CdCardEntity::getUseStatus, UseStatus.UseStatus1.getKey())
                    .notIn(CdCardEntity::getIccid,"无卡")
                    .notIn(CdCardEntity::getPhone,"无卡")
            );
            log.info("cdCardEntities = {}",cdCardEntities.size());
            CdCardEntity cdCardEntity = null;
            if (CollUtil.isEmpty(cdCardEntities)) {
                //获取设备下所有的信息列表 所有卡的信息
                List<CdCardEntity> useCdCardEntities = cdCardService.list(new QueryWrapper<CdCardEntity>().lambda()
                        .eq(CdCardEntity::getDeviceId,cdCardLockEntity.getDeviceId())
                        .eq(CdCardEntity::getUseStatus, UseStatus.UseStatus2.getKey())
                        .notIn(CdCardEntity::getIccid,"无卡")
                        .notIn(CdCardEntity::getPhone,"无卡")
                );
                log.info("cdCardEntities = {}",cdCardEntities.size());
                //修改卡
                for (int i = 0; i < useCdCardEntities.size(); i++) {
                    if (i == 0) {
                        cdCardEntity = useCdCardEntities.get(i);
                    }else {
                        CdCardEntity currentCdCardEntity = useCdCardEntities.get(i);
                        currentCdCardEntity.setUseStatus(UseStatus.UseStatus1.getKey());
                    }
                }
                cdCardService.updateBatchById(useCdCardEntities);
            }else {
                //修改当前卡为已使用
                cdCardEntity = cdCardEntities.get(0);
                cdCardEntity.setUseStatus(UseStatus.UseStatus2.getKey());
                cdCardService.updateById(cdCardEntity);
            }

            if (ObjectUtil.isNull(cdCardEntity)) {
                return null;
            }
            //将当前手机上锁
            CdCardLockEntity update = new CdCardLockEntity();
            update.setId(cdCardLockEntity.getId());
            update.setUserId(cdUserEntity.getId());
            update.setProjectId(cdCardLock.getProjectId());
            update.setDeviceId(cdCardLockEntity.getDeviceId());
            update.setPhone(cdCardEntity.getPhone());
            update.setLock(Lock.YES.getKey());
            update.setIccid(cdCardEntity.getIccid());
            update.setDeleteFlag(DeleteFlag.NO.getKey());
            update.setCreateTime(DateUtil.date());
            update.setPhoneGetTime(DateUtil.offsetMinute(DateUtil.date(),20));
            this.updateById(update);
            //通知客戶端修改卡
            ChangeCardResponse taskDto = new ChangeCardResponse();
            taskDto.setBoardIndexed(cdCardEntity.getBoardIndexed());
            taskDto.setIndexed(cdCardEntity.getIndexed());
            taskDto.setDeviceId(cdCardEntity.getIccid());
            nettyChannelManager.send(cdCardLockEntity.getDeviceId(),new Invocation(ChangeCardResponse.TYPE, taskDto));
            return new CdCardLockVO().setPhone(update.getPhone()).setIccid(update.getIccid());
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean releaseMobiles(List<CdCardLockDTO> cdCardLocks, CdUserEntity cdUserEntity) {


        List<Integer> ids = cdCardLocks.stream().map(CdCardLockDTO::getId).collect(Collectors.toList());


        List<CdCardLockEntity> cdCardLockEntities = null;
        if (CollUtil.isNotEmpty(ids)) {
            cdCardLockEntities = this.listByIds(ids);
        }
        if (CollUtil.isEmpty(cdCardLockEntities)) {
            //获取当前手机号占用的设备
            List<String> iccids = cdCardLocks.stream().map(CdCardLockDTO::getIccid).collect(Collectors.toList());
            List<Integer> projectIds = cdCardLocks.stream().map(CdCardLockDTO::getProjectId).collect(Collectors.toList());
            cdCardLockEntities = this.list(new QueryWrapper<CdCardLockEntity>().lambda()
                    .in(CollUtil.isNotEmpty(iccids),CdCardLockEntity::getIccid, iccids)
                    .in(CollUtil.isNotEmpty(projectIds),CdCardLockEntity::getProjectId, projectIds)
            );
        }

        List<CdCardLockEntity> cdCardLockEntityNews = new ArrayList<>();

        for (CdCardLockEntity cdCardLockEntityEach : cdCardLockEntities) {
            Assert.isNull(cdCardLockEntityEach,"NoAssociatedDevices");
            Assert.isNull(cdCardLockEntityEach.getUserId(),"NoPermissionReleaseDevice");
            Assert.isTrue(!cdCardLockEntityEach.getUserId().equals(cdUserEntity.getId()),"NoPermissionReleaseDevice");

            CdCardLockEntity cdCardLockEntity = new CdCardLockEntity();
            cdCardLockEntity.setId(cdCardLockEntityEach.getId());
            cdCardLockEntity.setUserId(null);
            cdCardLockEntity.setProjectId(null);
            cdCardLockEntity.setCode(null);
            cdCardLockEntity.setLock(Lock.NO.getKey());
            cdCardLockEntity.setPhone(null);
            cdCardLockEntity.setPhoneGetTime(null);
            cdCardLockEntity.setIccid(null);
            cdCardLockEntity.setDeleteFlag(DeleteFlag.NO.getKey());
            cdCardLockEntity.setCreateTime(DateUtil.date());
            cdCardLockEntityNews.add(cdCardLockEntity);
        }
        return this.updateBatchById(cdCardLockEntityNews);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean releaseMobile(CdCardLockDTO cdCardLock, CdUserEntity cdUserEntity) {

        CdCardLockEntity cdCardLockEntity = null;
        if (ObjectUtil.isNotNull(cdCardLock.getId())) {
            cdCardLockEntity = this.getById((Serializable) cdCardLock.getId());
        }
        if (ObjectUtil.isNull(cdCardLockEntity)) {
            //获取当前手机号占用的设备
            cdCardLockEntity = this.getOne(new QueryWrapper<CdCardLockEntity>().lambda()
                    .eq(StrUtil.isNotEmpty(cdCardLock.getIccid()),CdCardLockEntity::getIccid, cdCardLock.getIccid())
                    .eq(ObjectUtil.isNotNull(cdCardLock.getProjectId()),CdCardLockEntity::getProjectId, cdCardLock.getProjectId())
            );
        }

        Assert.isNull(cdCardLockEntity,"NoAssociatedDevices");
        Assert.isNull(cdCardLockEntity.getUserId(),"NoPermissionReleaseDevice");
        Assert.isTrue(!cdCardLockEntity.getUserId().equals(cdUserEntity.getId()),"NoPermissionReleaseDevice");

        cdCardLockEntity.setId(cdCardLockEntity.getId());
        cdCardLockEntity.setUserId(null);
        cdCardLockEntity.setProjectId(null);
        cdCardLockEntity.setCode(null);
        cdCardLockEntity.setLock(Lock.NO.getKey());
        cdCardLockEntity.setPhone(null);
        cdCardLockEntity.setPhoneGetTime(null);
        cdCardLockEntity.setIccid(null);
        cdCardLockEntity.setDeleteFlag(DeleteFlag.NO.getKey());
        cdCardLockEntity.setCreateTime(DateUtil.date());
        return this.updateById(cdCardLockEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean releaseMobileAll(CdCardLockDTO cdCardLock, CdUserEntity cdUserEntity) {
        List<CdCardLockEntity> list = null;
        if (CollUtil.isNotEmpty(cdCardLock.getIds())) {
            list = this.listByIds(cdCardLock.getIds());
        }else {
            list = this.list(new QueryWrapper<CdCardLockEntity>().lambda()
                    .eq(CdCardLockEntity::getUserId,cdUserEntity.getId())
            );
        }
        if (CollUtil.isEmpty(list)) {
            return false;
        }
        for (CdCardLockEntity cdCardLockEntity : list) {
            cdCardLockEntity.setId(cdCardLockEntity.getId());
            cdCardLockEntity.setUserId(null);
            cdCardLockEntity.setProjectId(null);
            cdCardLockEntity.setCode(null);
            cdCardLockEntity.setLock(Lock.NO.getKey());
            cdCardLockEntity.setPhone(null);
            cdCardLockEntity.setIccid(null);
            cdCardLockEntity.setPhoneGetTime(null);
            cdCardLockEntity.setDeleteFlag(DeleteFlag.NO.getKey());
            cdCardLockEntity.setCreateTime(DateUtil.date());
        }
        return this.updateBatchById(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String getSms(CdCardLockDTO cdCardLock, CdUserEntity cdUserEntity) {
        //获取当前手机号占用的设备
        CdCardLockEntity cdCardLockEntity = this.getOne(new QueryWrapper<CdCardLockEntity>().lambda()
                .eq(CdCardLockEntity::getIccid, cdCardLock.getIccid())
                .eq(CdCardLockEntity::getProjectId, cdCardLock.getProjectId())
        );
        //判断设备是否存在
        Assert.isNull(cdCardLockEntity,"NoAssociatedDevices");
        //此用户是否有权限
        Assert.isNull(cdCardLockEntity.getUserId(),"NoPermissionReleaseDevice");
        Assert.isTrue(!cdCardLockEntity.getUserId().equals(cdUserEntity.getId()),"NoPermissionReleaseDevice");
        //获取验证码的key
        String key = String.format("code_%s_%s", cdCardLock.getIccid(), cdCardLock.getProjectId());
        String code = cdCardLockEntity.getCode();
        //成功获取短信 释放手机号
        if (StrUtil.isNotEmpty(code)) {
            CdCardLockDTO cdCardLockDTO = new CdCardLockDTO();
            cdCardLockDTO.setProjectId(cdCardLockEntity.getProjectId());
            cdCardLockDTO.setIccid(cdCardLockEntity.getIccid());
            releaseMobile(cdCardLockDTO,cdUserEntity);
        }
        return code;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean uploadSms(CdCardLockDTO cdCardLock, CdUserEntity cdUserEntity) {
        //获取当前的设备
        CdCardLockEntity cdCardLockEntity = this.getOne(new QueryWrapper<CdCardLockEntity>().lambda()
                .eq(CdCardLockEntity::getDeviceId, cdCardLock.getDeviceId())
        );
        CdDevicesEntity cdDevicesEntity = cdDevicesService.getById((Serializable) cdCardLockEntity.getId());
        Assert.isNull(cdCardLockEntity,"NoAssociatedDevices");
        Assert.isNull(cdDevicesEntity,"NoAssociatedDevices");

        String cacheKey = String.format("%s_%s", ConfigConstant.PROJECT_WORK_KEY, cdDevicesEntity.getGroupId());

        ProjectWorkEntity projectWorkEntity = caffeineCacheProjectWorkEntity.getIfPresent(cacheKey);
        //如果存在配置 并且是挂机接码状态
        if (ObjectUtil.isNotNull(projectWorkEntity) && CodeAcquisitionType.CodeAcquisitionType2.getKey().equals(projectWorkEntity.getCodeAcquisitionType())) {
            return uploadSms2(cdCardLock,cdUserEntity,projectWorkEntity);
        }
        Integer userId = projectWorkEntity.getUserId();
        Integer projectId = projectWorkEntity.getProjectId();

        //获取验证码的key
        String key = String.format("code_%s_%s", cdCardLockEntity.getIccid(), cdCardLockEntity.getProjectId());

        CdProjectVO cdProjectVO = cdProjectService.getById(cdCardLockEntity.getProjectId());
        //保存短信的记录
        CdProjectSmsRecordEntity cdProjectSmsRecordEntity = new CdProjectSmsRecordEntity();
        cdProjectSmsRecordEntity.setKey(key);
        cdProjectSmsRecordEntity.setCode(cdCardLock.getCode());
        if (ObjectUtil.isNotNull(cdProjectVO) && cdCardLock.getCode().contains(cdProjectVO.getName()) || cdCardLock.getCode().contains("拉黑")) {
            cdProjectSmsRecordEntity.setUserId(cdCardLockEntity.getUserId());
            cdProjectSmsRecordEntity.setProjectId(cdCardLockEntity.getProjectId());
        }
        cdProjectSmsRecordEntity.setDeviceId(cdCardLockEntity.getDeviceId());
        cdProjectSmsRecordEntity.setPhone(cdCardLockEntity.getPhone());
        cdProjectSmsRecordEntity.setIccid(cdCardLockEntity.getIccid());
        cdProjectSmsRecordEntity.setDeleteFlag(DeleteFlag.YES.getKey());
        cdProjectSmsRecordEntity.setCreateTime(DateUtil.date());

        boolean save = cdProjectSmsRecordService.save(cdProjectSmsRecordEntity);

        if ((ObjectUtil.isNotNull(cdProjectVO) && cdCardLock.getCode().contains(cdProjectVO.getName())) || cdCardLock.getCode().contains("拉黑")) {
            cdCardLockEntity.setId(cdCardLockEntity.getId());
            cdCardLockEntity.setCode(cdCardLock.getCode());
            cdCardLockEntity.setPhoneGetTime(null);
            this.updateById(cdCardLockEntity);
        }

        if (ObjectUtil.isNotNull(projectWorkEntity) && CodeAcquisitionType.CodeAcquisitionType3.getKey().equals(projectWorkEntity.getCodeAcquisitionType())) {
            return save;
        }

        //上传短信
        if (ObjectUtil.isNotNull(cdProjectVO) && cdCardLock.getCode().contains(cdProjectVO.getName()) || cdCardLock.getCode().contains("拉黑")) {
            String deviceId = cdCardLockEntity.getDeviceId();
            if (userId.equals(cdCardLockEntity.getUserId()) && projectId.equals(cdCardLockEntity.getProjectId())) {
                if (cdCardLock.getCode().contains(cdProjectVO.getName())){
                    uploadSms(cdCardLock, cdCardLockEntity,projectWorkEntity);
                }
                CdCardLockDTO cdCardLockDTO = new CdCardLockDTO();
                cdCardLockDTO.setProjectId(projectId);
                cdCardLockDTO.setIccid(cdCardLockEntity.getIccid());
                CdUserEntity cdUserEntity1 = cdUserService.getById((Serializable) userId);
                boolean b = releaseMobile(cdCardLockDTO, cdUserEntity1);
                CdCardLockVO mobile = getMobile(cdCardLockDTO, cdUserEntity1, deviceId,null);
                if (ObjectUtil.isNotNull(mobile)) {
                    //获取新的
                    List<PhoneList> phoneLists = new ArrayList<>();
                    String replace = mobile.getPhone().replaceFirst(projectWorkEntity.getPhonePre(), "");
                    PhoneList phoneList = new PhoneList("tha",replace);
                    phoneLists.add(phoneList);
                    extracted(phoneLists,"",projectWorkEntity.getCodeApiUrl());
                }
            }
        }

        //如果存在信息 火狐狸删除手机
        if (ObjectUtil.isNotNull(cdProjectSmsRecordEntity) && ObjectUtil.isNotNull(cdProjectSmsRecordEntity.getUserId()) && StrUtil.isNotEmpty(cdProjectSmsRecordEntity.getPhone())) {
            if (userId.equals(cdCardLockEntity.getUserId()) && projectId.equals(cdCardLockEntity.getProjectId())) {
                //获取新的
                List<PhoneList> phoneLists = new ArrayList<>();
                String replace = cdProjectSmsRecordEntity.getPhone().replaceFirst(projectWorkEntity.getPhonePre(), "");
                PhoneList phoneList = new PhoneList("tha",replace);
                phoneLists.add(phoneList);
                extracted(phoneLists,"PhoneDeleteBatch",projectWorkEntity.getCodeApiUrl());
            }
        }

        return save;
    }

    @Override
    public List<GetListByIdsVO> getListByIds(List<Integer> ids) {
        return baseMapper.getListByIds(ids);
    }

    @Override
    public boolean uploadSms2(CdCardLockDTO cdCardLock, CdUserEntity cdUserEntity,ProjectWorkEntity projectWorkEntity) {

        //获取当前的设备
        CdCardLockEntity cdCardLockEntity = this.getOne(new QueryWrapper<CdCardLockEntity>().lambda()
                .eq(CdCardLockEntity::getDeviceId, cdCardLock.getDeviceId())
        );
        Assert.isNull(cdCardLockEntity,"NoAssociatedDevices");
        //获取验证码的key
        String key = String.format("code_%s_%s", cdCardLockEntity.getIccid(), cdCardLockEntity.getProjectId());
        //保存短信的记录
        CdProjectSmsRecordEntity cdProjectSmsRecordEntity = new CdProjectSmsRecordEntity();
        cdProjectSmsRecordEntity.setKey(key);
        cdProjectSmsRecordEntity.setCode(cdCardLock.getCode());
        cdProjectSmsRecordEntity.setDeviceId(cdCardLockEntity.getDeviceId());
        cdProjectSmsRecordEntity.setPhone(cdCardLockEntity.getPhone());
        cdProjectSmsRecordEntity.setIccid(cdCardLockEntity.getIccid());
        cdProjectSmsRecordEntity.setDeleteFlag(DeleteFlag.YES.getKey());
        cdProjectSmsRecordEntity.setCreateTime(DateUtil.date());
        boolean save = cdProjectSmsRecordService.save(cdProjectSmsRecordEntity);
        uploadSms(cdCardLock, cdCardLockEntity,projectWorkEntity);
        return save;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TaskDto deviceTaskGet(CdCardLockDTO cdCardLock) {
        TaskDto taskDto = caffeineCacheCodeTaskDto.getIfPresent(cdCardLock.getDeviceId());
        //清除任務
        if (ObjectUtil.isNotNull(taskDto)) {
            caffeineCacheCodeTaskDto.invalidate(cdCardLock.getDeviceId());
        }
        CdDevicesEntity cdDevicesEntity = new CdDevicesEntity();
        cdDevicesEntity.setOnline(Online.YES.getKey());
        cdDevicesService.update(cdDevicesEntity,new QueryWrapper<CdDevicesEntity>().lambda()
                .eq(CdDevicesEntity::getIccid,cdCardLock.getDeviceId())
        );
        return taskDto;
    }



    @Autowired
    private CdCardGroupService cdCardGroupService;

    @EventListener
    @Order(value = 9999)
    public void handlerApplicationReadyEvent(ApplicationReadyEvent event) {

        List<CdCardGroupEntity> list = cdCardGroupService.list();
        for (CdCardGroupEntity cdCardGroupEntity : list) {
            Integer id = cdCardGroupEntity.getId();

            String cacheKey = String.format("%s_%s", ConfigConstant.PROJECT_WORK_KEY, id);
            SysConfigEntity sysConfigEntity = sysConfigService.getOne(new QueryWrapper<SysConfigEntity>().lambda()
                    .eq(SysConfigEntity::getParamKey, cacheKey)
            );
//            //项目
//            ProjectWorkEntity projectWorkEntity = caffeineCacheProjectWorkEntity.getIfPresent(cacheKey);
            if (ObjectUtil.isNotNull(sysConfigEntity)) {
                sysConfigEntity.setKey(cacheKey);
                ProjectWorkEntity bean = JSONUtil.toBean(sysConfigEntity.getParamValue(), ProjectWorkEntity.class);
                if (ObjectUtil.isNotNull(bean) && ObjectUtil.isNotNull(bean.getUserId())) {
                    SysConfigEntity config = new SysConfigEntity();
                    config.setKey(sysConfigEntity.getParamKey());
                    config.setId(sysConfigEntity.getId());
                    config.setType(2);
                    config.setProjectId(bean.getProjectId());
                    config.setPhonePre(bean.getPhonePre());
                    config.setUserId(bean.getUserId());
                    config.setCodeApiUrl(bean.getCodeApiUrl());
                    config.setPlatform(bean.getPlatform());
                    config.setCodeAcquisitionType(bean.getCodeAcquisitionType());
                    if (ObjectUtil.isNull(bean.getPlatform())) {
                        config.setPlatform(1);
                    }
                    if (StrUtil.isEmpty(bean.getCodeApiUrl())) {
                        config.setCodeApiUrl("https://www.firefox.fun/ksapi.ashx?key=76082377BDE44F99");
                    }
                    sysConfigService.update(config);
                }
            }

//        SysConfigEntity one = sysConfigService.getOne(new QueryWrapper<SysConfigEntity>().lambda()
//                .eq(SysConfigEntity::getParamKey,ConfigConstant.PROJECT_WORK_KEY)
//        );
//        List<SysConfigEntity> list = sysConfigService.list();
//        for (SysConfigEntity sysConfigEntity : list) {

//            }
//        }


//        if (ObjectUtil.isNull(one)) {
//            SysConfigEntity config = new SysConfigEntity();
//            config.setType(2);
//            config.setProjectId(198);
//            config.setPhonePre("+855");
//            config.setUserId(2);
//            config.setCodeApiUrl("https://www.firefox.fun/ksapi.ashx?key=76082377BDE44F99");
//            config.setPlatform(1);
//            config.setCodeAcquisitionType(CodeAcquisitionType.CodeAcquisitionType1.getKey());
//            sysConfigService.save(config);
//        }else {
//        }
        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void init3(Integer[] ids){
        List<GetListByIdsVO> getListByIdsVOS = this.getListByIds(Arrays.asList(ids));

        Map<Integer, List<GetListByIdsVO>> integerListMap = getListByIdsVOS.stream().filter(x -> ObjectUtil.isNotNull(x.getGroupId())).collect(Collectors.groupingBy(GetListByIdsVO::getGroupId));

        for (Integer id : integerListMap.keySet()) {
            String cacheKey = String.format("%s_%s", ConfigConstant.PROJECT_WORK_KEY, id);
            ProjectWorkEntity projectWorkEntity = caffeineCacheProjectWorkEntity.getIfPresent(cacheKey);
            if (ObjectUtil.isNull(projectWorkEntity)) {
                continue;
            }
            Integer userId = projectWorkEntity.getUserId();
            Integer projectId = projectWorkEntity.getProjectId();
            CdUserEntity userEntity = cdUserService.getById((Serializable) userId);
            List<GetListByIdsVO> list = integerListMap.get(id);
            if (!list.isEmpty()) {
                List<PhoneList> phoneLists = new ArrayList<PhoneList>();
                //获取所有的手机
                for (GetListByIdsVO cdCardLockEntity : list) {
                    if (ObjectUtil.isNull(userEntity)) {
                        continue;
                    }
                    CdCardLockDTO cdCardLockDTO = new CdCardLockDTO();
                    cdCardLockDTO.setProjectId(projectId);
                    CdCardLockVO mobile = getMobile(cdCardLockDTO, userEntity, cdCardLockEntity.getDeviceId(),null);
                    if (ObjectUtil.isNotNull(mobile)) {
                        String replace = mobile.getPhone().replaceFirst(projectWorkEntity.getPhonePre(), "");
                        PhoneList phoneList = new PhoneList("tha",replace);
                        phoneLists.add(phoneList);
                    }
                }

                if (CollUtil.isNotEmpty(phoneLists)) {
                    List<List<PhoneList>> partition = Lists.partition(phoneLists, 49);
                    for (List<PhoneList> lists : partition) {
                        extracted(lists,"",projectWorkEntity.getCodeApiUrl());
                    }
                }
            }
        }
    }

    public void extracted(List<PhoneList> phoneLists,String act,String codeApiUrl){
        try{
            if (StrUtil.isEmpty(act)) {
                act = "PhoneAddBatch";
            }
            PhoneAddBatch phoneAddBatch = new PhoneAddBatch(act, phoneLists);
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(phoneAddBatch);
            String response = firefoxPost(codeApiUrl,json);
            PhoneDeleteAllResponse phoneDeleteAllResponse = objectMapper.readValue(response, PhoneDeleteAllResponse.class);
            if ("1".equals(phoneDeleteAllResponse.getCode())) {
                log.error("添加成功");
            }
        }catch (Exception e) {

        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void withBlackMobile(CdCardLockDTO cdCardLock, CdUserEntity cdUserEntity) {
        //获取当前手机号占用的设备
        CdCardLockEntity cdCardLockEntity = this.getOne(new QueryWrapper<CdCardLockEntity>().lambda()
                .eq(CdCardLockEntity::getIccid, cdCardLock.getIccid())
                .eq(CdCardLockEntity::getProjectId, cdCardLock.getProjectId())
        );
        Assert.isNull(cdCardLockEntity,"NoAssociatedDevices");
        Assert.isNull(cdCardLockEntity.getUserId(),"NoPermissionReleaseDevice");
        Assert.isTrue(!cdCardLockEntity.getUserId().equals(cdUserEntity.getId()),"NoPermissionReleaseDevice");

        CdCardLockDTO cdCardLockDTO = CdCardLockConver.MAPPER.conver1(cdCardLockEntity);

        cdCardLockDTO.setCode("拉黑");
        cdCardLockDTO.setDeviceId(cdCardLockEntity.getDeviceId());
        cdCardLockDTO.setProjectId(cdCardLockEntity.getProjectId());
        boolean b = this.uploadSms(cdCardLockDTO, cdUserEntity);
        this.releaseMobile(cdCardLock,cdUserEntity);
    }

    private void uploadSms(CdCardLockDTO cdCardLock, CdCardLockEntity cdCardLockEntity,ProjectWorkEntity projectWorkEntity){
        try {
            String replace = cdCardLockEntity.getPhone().replaceFirst(projectWorkEntity.getPhonePre(), "");
            UploadSms phoneAddBatch = new UploadSms("UploadSms", "tha",replace, cdCardLock.getCode());
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(phoneAddBatch);
            String response = firefoxPost(projectWorkEntity.getCodeApiUrl(),json);
            PhoneDeleteAllResponse phoneDeleteAllResponse = objectMapper.readValue(response, PhoneDeleteAllResponse.class);
            if ("1".equals(phoneDeleteAllResponse.getCode())) {
                log.error("添加成功");
            }
        }catch (Exception e) {

        }
    }


    private String firefoxPost(String codeApiUrl,String json) {
        return HttpUtil.post(codeApiUrl, json);
    }


}
