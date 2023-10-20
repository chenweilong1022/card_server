package io.renren.modules.ltt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.ltt.dto.CdUserDTO;
import io.renren.modules.ltt.vo.CdUserVO;
import io.renren.modules.ltt.entity.CdUserEntity;

import java.io.Serializable;
import java.util.Collection;


/**
 * 卡池用户表
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-18 23:26:34
 */
public interface CdUserService extends IService<CdUserEntity> {

    /**
     * 分页查询
     * @param cdUser
     * @return
     */
    PageUtils queryPage(CdUserDTO cdUser);
    /**
     * 根据id查询
     * @param id
     * @return
     */
    CdUserVO getById(Integer id);
    /**
     * 保存
     * @param cdUser
     * @return
     */
    boolean save(CdUserDTO cdUser);
    /**
     * 根据id修改
     * @param cdUser
     * @return
     */
    boolean updateById(CdUserDTO cdUser);
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
     * 注册
     * @param cdUser
     */
    void register(CdUserDTO cdUser);

    String login(CdUserDTO cdUser);
}

