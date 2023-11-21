package io.renren.modules.ltt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.app.dto.TaskDto;
import io.renren.modules.ltt.dto.CdCardLockDTO;
import io.renren.modules.ltt.entity.CdUserEntity;
import io.renren.modules.ltt.firefox.PhoneList;
import io.renren.modules.ltt.vo.CdCardLockVO;
import io.renren.modules.ltt.entity.CdCardLockEntity;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;


/**
 * 卡锁
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-19 02:22:45
 */
public interface CdCardLockService extends IService<CdCardLockEntity> {

    /**
     * 分页查询
     * @param cdCardLock
     * @return
     */
    PageUtils queryPage(CdCardLockDTO cdCardLock);
    /**
     * 根据id查询
     * @param id
     * @return
     */
    CdCardLockVO getById(Integer id);
    /**
     * 保存
     * @param cdCardLock
     * @return
     */
    boolean save(CdCardLockDTO cdCardLock);
    /**
     * 根据id修改
     * @param cdCardLock
     * @return
     */
    boolean updateById(CdCardLockDTO cdCardLock);
    /**
     * 根据id删除
     * @param id
     * @return
     */
    @Override
    boolean removeById(Serializable id);

    /**
     * 根据id批量删除
     * @param ids
     * @return
     */
    @Override
    boolean removeByIds(Collection<? extends Serializable> ids);

    /**
     * 获取手机号
     * @param cdCardLock
     * @return
     */
    CdCardLockVO getMobile(CdCardLockDTO cdCardLock, CdUserEntity cdUserEntity,String deviceId);

    /**
     * 释放手机号
     * @param cdCardLock
     * @param cdUserEntity
     * @return
     */
    boolean releaseMobile(CdCardLockDTO cdCardLock, CdUserEntity cdUserEntity);
    /**
     * 释放用户的所有号码
     * @param cdCardLock
     * @param cdUserEntity
     * @return
     */
    boolean releaseMobileAll(CdCardLockDTO cdCardLock, CdUserEntity cdUserEntity);
    /**
     * 获取短信
     * @param cdCardLock
     * @param cdUserEntity
     * @return
     */
    String getSms(CdCardLockDTO cdCardLock, CdUserEntity cdUserEntity);

    /**
     * 上传短信
     * @param cdCardLock
     * @param cdUserEntity
     * @return
     */
    boolean uploadSms(CdCardLockDTO cdCardLock, CdUserEntity cdUserEntity);

    TaskDto deviceTaskGet(CdCardLockDTO cdCardLock);

    void init3() throws IOException;

    public void extracted(List<PhoneList> phoneLists, String act);
}

