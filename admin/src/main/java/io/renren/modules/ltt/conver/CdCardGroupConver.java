package io.renren.modules.ltt.conver;

import io.renren.modules.ltt.dto.CdCardGroupDTO;
import io.renren.modules.ltt.entity.CdCardGroupEntity;
import io.renren.modules.ltt.vo.CdCardGroupVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2020-02-07 12:36
 */
@Mapper
public interface CdCardGroupConver {

    CdCardGroupConver MAPPER =  Mappers.getMapper(CdCardGroupConver.class);

    CdCardGroupEntity converDTO(CdCardGroupDTO cdCardGroupDTO);

    List<CdCardGroupEntity> converDTO(List<CdCardGroupDTO> cdCardGroupDTOs);

    CdCardGroupVO conver(CdCardGroupEntity cdCardGroupEntities);

    List<CdCardGroupVO> conver(List<CdCardGroupEntity> cdCardGroupEntities);

}
