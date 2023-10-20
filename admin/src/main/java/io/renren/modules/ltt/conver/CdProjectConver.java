package io.renren.modules.ltt.conver;

import io.renren.modules.ltt.dto.CdProjectDTO;
import io.renren.modules.ltt.entity.CdProjectEntity;
import io.renren.modules.ltt.vo.CdProjectVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2020-02-07 12:36
 */
@Mapper
public interface CdProjectConver {

    CdProjectConver MAPPER =  Mappers.getMapper(CdProjectConver.class);

    CdProjectEntity converDTO(CdProjectDTO cdProjectDTO);

    List<CdProjectEntity> converDTO(List<CdProjectDTO> cdProjectDTOs);

    CdProjectVO conver(CdProjectEntity cdProjectEntities);

    List<CdProjectVO> conver(List<CdProjectEntity> cdProjectEntities);

}
