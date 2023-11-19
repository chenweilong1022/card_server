package io.renren.modules.ltt.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.renren.modules.ltt.dto.CdDevicesDTO;
import io.renren.modules.ltt.entity.CdDevicesEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ltt.vo.CdDevicesVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 设备表
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-05 23:22:42
 */
@Mapper
public interface CdDevicesDao extends BaseMapper<CdDevicesEntity> {

    IPage<CdDevicesVO> listPage(Page<CdDevicesEntity> page, @Param("dto") CdDevicesDTO cdDevices);
}
