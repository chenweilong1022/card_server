package io.renren.modules.ltt.conver;

import io.renren.modules.ltt.dto.CdBoardDTO;
import io.renren.modules.ltt.entity.CdBoardEntity;
import io.renren.modules.ltt.vo.CdBoardVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2020-02-07 12:36
 */
@Mapper
public interface CdBoardConver {

    CdBoardConver MAPPER =  Mappers.getMapper(CdBoardConver.class);

    CdBoardEntity converDTO(CdBoardDTO cdBoardDTO);

    List<CdBoardEntity> converDTO(List<CdBoardDTO> cdBoardDTOs);

    CdBoardVO conver(CdBoardEntity cdBoardEntities);

    List<CdBoardVO> conver(List<CdBoardEntity> cdBoardEntities);

}
