package io.renren.modules.ltt.dao;

import io.renren.modules.ltt.entity.CdUserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 卡池用户表
 * 
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-18 23:26:34
 */
@Mapper
public interface CdUserDao extends BaseMapper<CdUserEntity> {
	
}
