package io.renren.modules.ltt.conver;

import io.renren.modules.ltt.dto.CdProjectStatisticsDTO;
import io.renren.modules.ltt.entity.CdProjectStatisticsEntity;
import io.renren.modules.ltt.vo.CdProjectStatisticsVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2020-02-07 12:36
 */
@Mapper
public interface CdProjectStatisticsConver {

    CdProjectStatisticsConver MAPPER =  Mappers.getMapper(CdProjectStatisticsConver.class);

    CdProjectStatisticsEntity converDTO(CdProjectStatisticsDTO cdProjectStatisticsDTO);

    List<CdProjectStatisticsEntity> converDTO(List<CdProjectStatisticsDTO> cdProjectStatisticsDTOs);

    CdProjectStatisticsVO conver(CdProjectStatisticsEntity cdProjectStatisticsEntities);

    CdProjectStatisticsEntity conver1(CdProjectStatisticsVO cdProjectStatisticsEntities);

    List<CdProjectStatisticsVO> conver(List<CdProjectStatisticsEntity> cdProjectStatisticsEntities);

}
