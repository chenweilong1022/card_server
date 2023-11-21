package io.renren.modules.ltt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.ltt.dto.CdDevicesDTO;
import io.renren.modules.ltt.dto.CdDevicesUpdateAppDTO;
import io.renren.modules.ltt.vo.CdDevicesVO;
import io.renren.modules.ltt.entity.CdDevicesEntity;

import java.io.Serializable;
import java.util.Collection;


/**
 * 设备表
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-05 23:22:42
 */
public interface CdDevicesService extends IService<CdDevicesEntity> {

    /**
     * 分页查询
     * @param cdDevices
     * @return
     */
    PageUtils queryPage(CdDevicesDTO cdDevices);
    /**
     * 根据id查询
     * @param id
     * @return
     */
    CdDevicesVO getById(Integer id);
    /**
     * 保存
     * @param cdDevices
     * @return
     */
    boolean save(CdDevicesDTO cdDevices);
    /**
     * 根据id修改
     * @param cdDevices
     * @return
     */
    boolean updateById(CdDevicesDTO cdDevices);
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
     * 上传设备
     * @param cdDevices
     */
    CdDevicesEntity uploadDevice(CdDevicesDTO cdDevices);

    boolean initCard(Integer[] ids);

    boolean initCard2(Integer[] ids);

    /**
     * 通知前端切换卡
     * @param cdDevices
     * @return
     */
    boolean changeCard(CdDevicesDTO cdDevices);

    boolean updateApp(CdDevicesUpdateAppDTO updateAppDTO);

    boolean reboot(Integer[] ids);


    boolean initCard3(Integer[] ids);
}

