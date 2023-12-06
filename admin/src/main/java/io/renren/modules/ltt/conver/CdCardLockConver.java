package io.renren.modules.ltt.conver;

import io.renren.modules.ltt.dto.CdCardLockDTO;
import io.renren.modules.ltt.entity.CdCardLockEntity;
import io.renren.modules.ltt.vo.CdCardLockVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2020-02-07 12:36
 */
@Mapper
public interface CdCardLockConver {

    CdCardLockConver MAPPER =  Mappers.getMapper(CdCardLockConver.class);

    CdCardLockEntity converDTO(CdCardLockDTO cdCardLockDTO);

    List<CdCardLockEntity> converDTO(List<CdCardLockDTO> cdCardLockDTOs);

    CdCardLockVO conver(CdCardLockEntity cdCardLockEntities);
    CdCardLockDTO conver1(CdCardLockEntity cdCardLockEntities);

    List<CdCardLockVO> conver(List<CdCardLockEntity> cdCardLockEntities);

}
