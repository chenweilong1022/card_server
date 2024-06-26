package io.renren.modules.ltt.dao;

import io.renren.modules.ltt.dto.CdIccidPhoneDTO;
import io.renren.modules.ltt.entity.CdIccidPhoneEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ltt.vo.CdIccidPhoneVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * iccid手机对接表
 * 
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-30 00:10:56
 */
@Mapper
public interface CdIccidPhoneDao extends BaseMapper<CdIccidPhoneEntity> {


    Integer queryPageCount(CdIccidPhoneDTO paramPageDto);

    List<CdIccidPhoneVO> queryPage(CdIccidPhoneDTO paramPageDto);

}
