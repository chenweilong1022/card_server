package io.renren.modules.ltt.dao;

import io.renren.modules.ltt.entity.CdCardEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ltt.vo.GroupByDeviceIdVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 卡
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-05 23:22:42
 */
@Mapper
public interface CdCardDao extends BaseMapper<CdCardEntity> {

    List<GroupByDeviceIdVO> groupByDeviceId();

    List<GroupByDeviceIdVO> groupByGroupId();

    List<String> listPhoneByGroupId(@Param("groupId") Integer groupId);
}
