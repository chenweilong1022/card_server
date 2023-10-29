package io.renren.modules.ltt.conver;

import io.renren.modules.ltt.dto.CdIccidPhoneDTO;
import io.renren.modules.ltt.entity.CdIccidPhoneEntity;
import io.renren.modules.ltt.vo.CdIccidPhoneVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2020-02-07 12:36
 */
@Mapper
public interface CdIccidPhoneConver {

    CdIccidPhoneConver MAPPER =  Mappers.getMapper(CdIccidPhoneConver.class);

    CdIccidPhoneEntity converDTO(CdIccidPhoneDTO cdIccidPhoneDTO);

    List<CdIccidPhoneEntity> converDTO(List<CdIccidPhoneDTO> cdIccidPhoneDTOs);

    CdIccidPhoneVO conver(CdIccidPhoneEntity cdIccidPhoneEntities);

    List<CdIccidPhoneVO> conver(List<CdIccidPhoneEntity> cdIccidPhoneEntities);

}
