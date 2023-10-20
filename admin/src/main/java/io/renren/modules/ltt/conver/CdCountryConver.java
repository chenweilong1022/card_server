package io.renren.modules.ltt.conver;

import io.renren.modules.ltt.dto.CdCountryDTO;
import io.renren.modules.ltt.entity.CdCountryEntity;
import io.renren.modules.ltt.vo.CdCountryVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2020-02-07 12:36
 */
@Mapper
public interface CdCountryConver {

    CdCountryConver MAPPER =  Mappers.getMapper(CdCountryConver.class);

    CdCountryEntity converDTO(CdCountryDTO cdCountryDTO);

    List<CdCountryEntity> converDTO(List<CdCountryDTO> cdCountryDTOs);

    CdCountryVO conver(CdCountryEntity cdCountryEntities);

    List<CdCountryVO> conver(List<CdCountryEntity> cdCountryEntities);

}
