package io.renren.modules.ltt.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import io.renren.common.validator.Assert;
import io.renren.datasources.annotation.Game;
import io.renren.modules.app.dto.TaskDto;
import io.renren.modules.ltt.entity.*;
import io.renren.modules.ltt.enums.DeleteFlag;
import io.renren.modules.ltt.enums.Lock;
import io.renren.modules.ltt.enums.Online;
import io.renren.modules.ltt.enums.WorkType;
import io.renren.modules.ltt.firefox.PhoneAddBatch;
import io.renren.modules.ltt.firefox.PhoneDeleteAllResponse;
import io.renren.modules.ltt.firefox.PhoneList;
import io.renren.modules.ltt.firefox.Root;
import io.renren.modules.ltt.service.*;
import io.renren.modules.ltt.vo.CdProjectVO;
import io.renren.modules.ltt.vo.CdUserVO;
import io.renren.modules.netty.codec.Invocation;
import io.renren.modules.netty.message.changecard.ChangeCardResponse;
import io.renren.modules.netty.server.NettyChannelManager;
import jodd.util.StringUtil;
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
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;


@Service("cdCardLockService")
@Game
public class CdCardLockServiceImpl extends ServiceImpl<CdCardLockDao, CdCardLockEntity> implements CdCardLockService {


    @Autowired
    private CdProjectService cdProjectService;
    @Autowired
    private CdDevicesService cdDevicesService;
    @Autowired
    private CdCardService cdCardService;

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

    Integer userId = 2;
    Integer projectId = 7;
    String phonePre = "+855";

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
    public CdCardLockVO getMobile(CdCardLockDTO cdCardLock, CdUserEntity cdUserEntity,String deviceId) {
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
            );
            //获取这个设备下这个项目所有记录
            List<CdProjectSmsRecordEntity> cdProjectSmsRecordEntities = cdProjectSmsRecordService.list(new QueryWrapper<CdProjectSmsRecordEntity>().lambda()
                    .eq(CdProjectSmsRecordEntity::getProjectId,cdCardLock.getProjectId())
                    .eq(CdProjectSmsRecordEntity::getDeviceId,cdCardLockEntity.getDeviceId())
            );

            if (StrUtil.isNotEmpty(cdCardLock.getNumberSegment())) {
                cdCardEntities = cdCardEntities.stream().filter(cdCardEntity -> cdCardEntity.getPhone().startsWith(cdCardLock.getNumberSegment())).collect(Collectors.toList());
            }
            //已经使用的id
            List<String> iccids = cdProjectSmsRecordEntities.stream().map(CdProjectSmsRecordEntity::getIccid).collect(Collectors.toList());
            for (CdCardEntity cdDevicesEntity : cdCardEntities) {
                if (iccids.contains(cdDevicesEntity.getIccid())) {
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
    @Transactional(rollbackFor = Exception.class)
    public boolean releaseMobile(CdCardLockDTO cdCardLock, CdUserEntity cdUserEntity) {
        //获取当前手机号占用的设备
        CdCardLockEntity cdCardLockEntity = this.getOne(new QueryWrapper<CdCardLockEntity>().lambda()
                .eq(CdCardLockEntity::getIccid, cdCardLock.getIccid())
                .eq(CdCardLockEntity::getProjectId, cdCardLock.getProjectId())
        );
        Assert.isNull(cdCardLockEntity,"NoAssociatedDevices");
        Assert.isNull(cdCardLockEntity.getUserId(),"NoPermissionReleaseDevice");
        Assert.isTrue(!cdCardLockEntity.getUserId().equals(cdUserEntity.getId()),"NoPermissionReleaseDevice");


        cdCardLockEntity.setId(cdCardLockEntity.getId());
        cdCardLockEntity.setUserId(null);
        cdCardLockEntity.setProjectId(null);
        cdCardLockEntity.setCode(null);
        cdCardLockEntity.setLock(Lock.NO.getKey());
        cdCardLockEntity.setPhone(null);
        cdCardLockEntity.setIccid(null);
        cdCardLockEntity.setDeleteFlag(DeleteFlag.NO.getKey());
        cdCardLockEntity.setCreateTime(DateUtil.date());
        return this.updateById(cdCardLockEntity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean releaseMobileAll(CdCardLockDTO cdCardLock, CdUserEntity cdUserEntity) {
        List<CdCardLockEntity> list = this.list(new QueryWrapper<CdCardLockEntity>().lambda()
                .eq(CdCardLockEntity::getUserId,cdUserEntity.getId())
        );
        for (CdCardLockEntity cdCardLockEntity : list) {
            cdCardLockEntity.setId(cdCardLockEntity.getId());
            cdCardLockEntity.setUserId(null);
            cdCardLockEntity.setProjectId(null);
            cdCardLockEntity.setCode(null);
            cdCardLockEntity.setLock(Lock.NO.getKey());
            cdCardLockEntity.setPhone(null);
            cdCardLockEntity.setIccid(null);
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
        Assert.isNull(cdCardLockEntity,"NoAssociatedDevices");
        //获取验证码的key
        String key = String.format("code_%s_%s", cdCardLockEntity.getIccid(), cdCardLockEntity.getProjectId());

        CdProjectVO cdProjectVO = cdProjectService.getById(cdCardLockEntity.getProjectId());

        //保存短信的记录
        CdProjectSmsRecordEntity cdProjectSmsRecordEntity = new CdProjectSmsRecordEntity();
        cdProjectSmsRecordEntity.setKey(key);
        cdProjectSmsRecordEntity.setCode(cdCardLock.getCode());
        if (cdCardLock.getCode().contains(cdProjectVO.getName())) {
            cdProjectSmsRecordEntity.setUserId(cdCardLockEntity.getUserId());
            cdProjectSmsRecordEntity.setProjectId(cdCardLockEntity.getProjectId());
        }
        cdProjectSmsRecordEntity.setDeviceId(cdCardLockEntity.getDeviceId());
        cdProjectSmsRecordEntity.setPhone(cdCardLockEntity.getPhone());
        cdProjectSmsRecordEntity.setIccid(cdCardLockEntity.getIccid());
        cdProjectSmsRecordEntity.setDeleteFlag(DeleteFlag.YES.getKey());
        cdProjectSmsRecordEntity.setCreateTime(DateUtil.date());

        boolean save = cdProjectSmsRecordService.save(cdProjectSmsRecordEntity);

        if (cdCardLock.getCode().contains(cdProjectVO.getName())) {
            cdCardLockEntity.setId(cdCardLockEntity.getId());
            cdCardLockEntity.setCode(cdCardLock.getCode());
            this.updateById(cdCardLockEntity);
        }

        //上传短信
        if (cdCardLock.getCode().contains(cdProjectVO.getName())) {
            String deviceId = cdCardLockEntity.getDeviceId();
            if (userId.equals(cdCardLockEntity.getUserId()) && projectId.equals(cdCardLockEntity.getProjectId())) {
                //删除老的手机号
                List<PhoneList> phoneListsRemove = new ArrayList<>();
                PhoneList phoneListRemove = new PhoneList("khm",cdCardLockEntity.getPhone().replace(phonePre,""));
                phoneListsRemove.add(phoneListRemove);
                try{
                    extracted(phoneListsRemove,"PhoneDeleteBatch");
                }catch (Exception e) {

                }

                CdCardLockDTO cdCardLockDTO = new CdCardLockDTO();
                cdCardLockDTO.setProjectId(projectId);
                CdUserEntity cdUserEntity1 = new CdUserEntity().setId(userId);
                boolean b = releaseMobile(cdCardLockDTO, cdUserEntity1);
                CdCardLockVO mobile = getMobile(cdCardLockDTO, cdUserEntity1, deviceId);
                //获取新的
                List<PhoneList> phoneLists = new ArrayList<>();
                PhoneList phoneList = new PhoneList("khm",mobile.getPhone().replace(phonePre,""));
                phoneLists.add(phoneList);
                try{
                    extracted(phoneLists,"");
                }catch (Exception e) {

                }
            }
        }
        return save;
    }

    @Override
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




    @EventListener
    @Order(value = 9999)
    public void handlerApplicationReadyEvent(ApplicationReadyEvent event) throws IOException {
        //用户
        CdUserEntity userEntity = cdUserService.getById((Serializable) userId);
        //判断火狐狸上有几个用户
        List<CdCardLockEntity> list = this.list(new QueryWrapper<CdCardLockEntity>());

        if (!list.isEmpty()) {
            List<PhoneList> phoneLists = new ArrayList<PhoneList>();
            //获取所有的手机
            for (CdCardLockEntity cdCardLockEntity : list) {
                if (ObjectUtil.isNull(userEntity.getId())) {
                    continue;
                }
                CdCardLockDTO cdCardLockDTO = new CdCardLockDTO();
                cdCardLockDTO.setProjectId(projectId);
                CdCardLockVO mobile = getMobile(cdCardLockDTO, userEntity, cdCardLockEntity.getDeviceId());
                PhoneList phoneList = new PhoneList("khm",mobile.getPhone().replace(phonePre,""));
                phoneLists.add(phoneList);
            }

            extracted(phoneLists,"");
        }
    }

    private void extracted(List<PhoneList> phoneLists,String act) throws IOException {
        if (StrUtil.isEmpty(act)) {
            act = "PhoneAddBatch";
        }
        PhoneAddBatch phoneAddBatch = new PhoneAddBatch(act, phoneLists);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(phoneAddBatch);
        String response = HttpUtil.post("https://www.firefox.fun/ksapi.ashx?key=76082377BDE44F99", json);
        PhoneDeleteAllResponse phoneDeleteAllResponse = objectMapper.readValue(response, PhoneDeleteAllResponse.class);
        if ("1".equals(phoneDeleteAllResponse.getCode())) {
            log.error("添加成功");
        }
    }


}
