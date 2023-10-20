package io.renren.modules.ltt.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.github.benmanes.caffeine.cache.Cache;
import io.renren.common.validator.Assert;
import io.renren.datasources.annotation.Game;
import io.renren.modules.ltt.entity.*;
import io.renren.modules.ltt.enums.DeleteFlag;
import io.renren.modules.ltt.enums.Lock;
import io.renren.modules.ltt.service.*;
import io.renren.modules.ltt.vo.CdProjectVO;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.io.Serializable;
import java.util.Collection;
import java.util.List;


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
    public String getMobile(CdCardLockDTO cdCardLock, CdUserEntity cdUserEntity) {
        //获取项目
        CdProjectVO cdProjectVO = cdProjectService.getById(cdCardLock.getProjectId());
        Assert.isNull(cdProjectVO,"ProjectDoesNotExist");
        //判断用户余额是否充足
        Assert.isTrue(cdProjectVO.getPrice().doubleValue() > cdUserEntity.getBalance().doubleValue(),"InsufficientBalance");
        //获取可以使用的设备
        List<CdCardLockEntity> list = this.list(new QueryWrapper<CdCardLockEntity>().lambda()
                .eq(CdCardLockEntity::getLock, Lock.NO.getKey())
        );
        //没有可用的设备
        Assert.isTrue(CollUtil.isEmpty(list),"NoEquipmentAvailable");
        //获取第一个可用的设备
        CdCardLockEntity first = CollUtil.getFirst(list);
        //获取设备下所有的信息列表
        List<CdCardEntity> cdDevicesEntities = cdCardService.list(new QueryWrapper<CdCardEntity>().lambda()
                .eq(CdCardEntity::getDeviceId,first.getDeviceId())
        );
        //设备没有初始化
        Assert.isTrue(CollUtil.isEmpty(list),"DeviceIsNotInitialized");
        //获取第一个可以用的卡
        CdCardEntity first1 = CollUtil.getFirst(cdDevicesEntities);
        //将当前手机上锁
        CdCardLockEntity update = new CdCardLockEntity();
        update.setId(first.getId());
        update.setUserId(cdUserEntity.getId());
        update.setProjectId(cdCardLock.getProjectId());
        update.setDeviceId(first1.getDeviceId());
        update.setLock(Lock.YES.getKey());
        update.setPhone(first1.getPhone());
        update.setIccid(first1.getIccid());
        update.setDeleteFlag(DeleteFlag.NO.getKey());
        update.setCreateTime(DateUtil.date());
        this.updateById(update);
        return update.getIccid();
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
    public boolean uploadSms(CdCardLockDTO cdCardLock, CdUserEntity cdUserEntity) {
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
        cdProjectSmsRecordEntity.setUserId(cdCardLockEntity.getUserId());
        cdProjectSmsRecordEntity.setProjectId(cdCardLockEntity.getProjectId());
        cdProjectSmsRecordEntity.setDeviceId(cdCardLockEntity.getDeviceId());
        cdProjectSmsRecordEntity.setPhone(cdCardLockEntity.getPhone());
        cdProjectSmsRecordEntity.setIccid(cdCardLockEntity.getIccid());
        cdProjectSmsRecordEntity.setDeleteFlag(DeleteFlag.YES.getKey());
        cdProjectSmsRecordEntity.setCreateTime(DateUtil.date());


        cdCardLockEntity.setId(cdCardLockEntity.getId());
        cdCardLockEntity.setCode(cdCardLock.getCode());
        this.updateById(cdCardLockEntity);
        return cdProjectSmsRecordService.save(cdProjectSmsRecordEntity);
    }


}
