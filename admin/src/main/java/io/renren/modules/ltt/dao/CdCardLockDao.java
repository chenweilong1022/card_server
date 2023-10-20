package io.renren.modules.ltt.dao;

import io.renren.modules.ltt.entity.CdCardLockEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 卡锁
 * 
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-19 02:22:45
 */
@Mapper
public interface CdCardLockDao extends BaseMapper<CdCardLockEntity> {
	
}
