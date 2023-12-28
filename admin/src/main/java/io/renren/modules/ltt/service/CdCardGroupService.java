package io.renren.modules.ltt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.ltt.dto.CdCardGroupDTO;
import io.renren.modules.ltt.dto.CdCardLockDTO;
import io.renren.modules.ltt.entity.CdUserEntity;
import io.renren.modules.ltt.vo.CdCardGroupVO;
import io.renren.modules.ltt.entity.CdCardGroupEntity;
import io.renren.modules.ltt.vo.CdCardLockVO;

import java.io.Serializable;
import java.util.Collection;


/**
 *
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-12-17 13:42:55
 */
public interface CdCardGroupService extends IService<CdCardGroupEntity> {

    /**
     * 分页查询
     * @param cdCardGroup
     * @return
     */
    PageUtils queryPage(CdCardGroupDTO cdCardGroup);
    /**
     * 根据id查询
     * @param id
     * @return
     */
    CdCardGroupVO getById(Integer id);
    /**
     * 保存
     * @param cdCardGroup
     * @return
     */
    boolean save(CdCardGroupDTO cdCardGroup);
    /**
     * 根据id修改
     * @param cdCardGroup
     * @return
     */
    boolean updateById(CdCardGroupDTO cdCardGroup);
    /**
     * 根据id删除
     * @param id
     * @return
     */
    @Override
    boolean removeById(Serializable id);

    /**
     * 根据id批量删除
     * @param ids
     * @return
     */
    @Override
    boolean removeByIds(Collection<? extends Serializable> ids);

    /**
     * 获取设备id根据项目id
     * @param cdCardLockDTO
     * @return
     */
    CdCardLockVO getDeviceIdByProjectId(CdCardLockDTO cdCardLockDTO, CdUserEntity cdUserEntity);
}

