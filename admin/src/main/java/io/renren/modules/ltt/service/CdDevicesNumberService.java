package io.renren.modules.ltt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.ltt.dto.CdDevicesNumberDTO;
import io.renren.modules.ltt.vo.CdDevicesNumberVO;
import io.renren.modules.ltt.entity.CdDevicesNumberEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * 设备编号对应表
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-11-14 21:40:08
 */
public interface CdDevicesNumberService extends IService<CdDevicesNumberEntity> {

    /**
     * 分页查询
     * @param cdDevicesNumber
     * @return
     */
    PageUtils queryPage(CdDevicesNumberDTO cdDevicesNumber);
    /**
     * 根据id查询
     * @param id
     * @return
     */
    CdDevicesNumberVO getById(Integer id);
    /**
     * 保存
     * @param cdDevicesNumber
     * @return
     */
    boolean save(CdDevicesNumberDTO cdDevicesNumber);
    /**
     * 根据id修改
     * @param cdDevicesNumber
     * @return
     */
    boolean updateById(CdDevicesNumberDTO cdDevicesNumber);
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

    Map<String, String> getDeviceNumberById(List<String> deviceIdList);
}

