package io.renren.modules.ltt.conver;

import io.renren.modules.ltt.dto.CdRechargedPhoneDTO;
import io.renren.modules.ltt.entity.CdRechargedPhoneEntity;
import io.renren.modules.ltt.vo.CdRechargedPhoneVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2020-02-07 12:36
 */
@Mapper
public interface CdRechargedPhoneConver {

    CdRechargedPhoneConver MAPPER =  Mappers.getMapper(CdRechargedPhoneConver.class);

    CdRechargedPhoneEntity converDTO(CdRechargedPhoneDTO cdRechargedPhoneDTO);

    List<CdRechargedPhoneEntity> converDTO(List<CdRechargedPhoneDTO> cdRechargedPhoneDTOs);

    CdRechargedPhoneVO conver(CdRechargedPhoneEntity cdRechargedPhoneEntities);

    List<CdRechargedPhoneVO> conver(List<CdRechargedPhoneEntity> cdRechargedPhoneEntities);

}
