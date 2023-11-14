package io.renren.modules.ltt.conver;

import io.renren.modules.ltt.dto.CdDevicesNumberDTO;
import io.renren.modules.ltt.entity.CdDevicesNumberEntity;
import io.renren.modules.ltt.vo.CdDevicesNumberVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2020-02-07 12:36
 */
@Mapper
public interface CdDevicesNumberConver {

    CdDevicesNumberConver MAPPER =  Mappers.getMapper(CdDevicesNumberConver.class);

    CdDevicesNumberEntity converDTO(CdDevicesNumberDTO cdDevicesNumberDTO);

    List<CdDevicesNumberEntity> converDTO(List<CdDevicesNumberDTO> cdDevicesNumberDTOs);

    CdDevicesNumberVO conver(CdDevicesNumberEntity cdDevicesNumberEntities);

    List<CdDevicesNumberVO> conver(List<CdDevicesNumberEntity> cdDevicesNumberEntities);

}
