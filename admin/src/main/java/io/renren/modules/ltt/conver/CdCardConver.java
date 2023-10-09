package io.renren.modules.ltt.conver;

import io.renren.modules.ltt.dto.CdCardDTO;
import io.renren.modules.ltt.entity.CdCardEntity;
import io.renren.modules.ltt.vo.CdCardVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2020-02-07 12:36
 */
@Mapper
public interface CdCardConver {

    CdCardConver MAPPER =  Mappers.getMapper(CdCardConver.class);

    CdCardEntity converDTO(CdCardDTO cdCardDTO);

    List<CdCardEntity> converDTO(List<CdCardDTO> cdCardDTOs);

    CdCardVO conver(CdCardEntity cdCardEntities);

    List<CdCardVO> conver(List<CdCardEntity> cdCardEntities);

}
