package io.renren.modules.ltt.conver;

import io.renren.modules.ltt.dto.CdDevicesDTO;
import io.renren.modules.ltt.entity.CdDevicesEntity;
import io.renren.modules.ltt.vo.CdDevicesVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2020-02-07 12:36
 */
@Mapper
public interface CdDevicesConver {

    CdDevicesConver MAPPER =  Mappers.getMapper(CdDevicesConver.class);

    CdDevicesEntity converDTO(CdDevicesDTO cdDevicesDTO);

    List<CdDevicesEntity> converDTO(List<CdDevicesDTO> cdDevicesDTOs);

    CdDevicesVO conver(CdDevicesEntity cdDevicesEntities);

    List<CdDevicesVO> conver(List<CdDevicesEntity> cdDevicesEntities);

}
