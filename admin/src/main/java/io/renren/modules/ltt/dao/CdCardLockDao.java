package io.renren.modules.ltt.dao;

import io.renren.modules.ltt.entity.CdCardLockEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.renren.modules.ltt.vo.GetListByIdsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 卡锁
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-19 02:22:45
 */
@Mapper
public interface CdCardLockDao extends BaseMapper<CdCardLockEntity> {

    List<GetListByIdsVO> getListByIds(@Param("ids") List<Integer> ids);

    List<String> getDeviceByGroupId(@Param("groupId") Integer groupId,
                                    @Param("lock") Integer lock);
}
