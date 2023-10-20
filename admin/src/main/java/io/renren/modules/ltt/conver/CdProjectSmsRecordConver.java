package io.renren.modules.ltt.conver;

import io.renren.modules.ltt.dto.CdProjectSmsRecordDTO;
import io.renren.modules.ltt.entity.CdProjectSmsRecordEntity;
import io.renren.modules.ltt.vo.CdProjectSmsRecordVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2020-02-07 12:36
 */
@Mapper
public interface CdProjectSmsRecordConver {

    CdProjectSmsRecordConver MAPPER =  Mappers.getMapper(CdProjectSmsRecordConver.class);

    CdProjectSmsRecordEntity converDTO(CdProjectSmsRecordDTO cdProjectSmsRecordDTO);

    List<CdProjectSmsRecordEntity> converDTO(List<CdProjectSmsRecordDTO> cdProjectSmsRecordDTOs);

    CdProjectSmsRecordVO conver(CdProjectSmsRecordEntity cdProjectSmsRecordEntities);

    List<CdProjectSmsRecordVO> conver(List<CdProjectSmsRecordEntity> cdProjectSmsRecordEntities);

}
