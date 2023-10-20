package io.renren.modules.ltt.conver;

import io.renren.modules.ltt.dto.CdUserDTO;
import io.renren.modules.ltt.entity.CdUserEntity;
import io.renren.modules.ltt.vo.CdUserInfoVO;
import io.renren.modules.ltt.vo.CdUserVO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2020-02-07 12:36
 */
@Mapper
public interface CdUserConver {

    CdUserConver MAPPER =  Mappers.getMapper(CdUserConver.class);

    CdUserEntity converDTO(CdUserDTO cdUserDTO);

    List<CdUserEntity> converDTO(List<CdUserDTO> cdUserDTOs);

    CdUserVO conver(CdUserEntity cdUserEntities);
    CdUserInfoVO conver1(CdUserEntity cdUserEntities);

    List<CdUserVO> conver(List<CdUserEntity> cdUserEntities);

}
