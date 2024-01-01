package io.renren.modules.ltt.dao;

import io.renren.modules.ltt.dto.CdProjectStatisticsDTO;
import io.renren.modules.ltt.entity.CdProjectStatisticsEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ltt.vo.CdProjectStatisticsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2024-01-01 15:32:55
 */
@Mapper
public interface CdProjectStatisticsDao extends BaseMapper<CdProjectStatisticsEntity> {

    List<CdProjectStatisticsVO> statistics(@Param("dto") CdProjectStatisticsDTO cdProjectStatistics);
}
