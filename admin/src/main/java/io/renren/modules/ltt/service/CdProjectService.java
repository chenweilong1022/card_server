package io.renren.modules.ltt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.ltt.dto.CdProjectDTO;
import io.renren.modules.ltt.vo.CdProjectVO;
import io.renren.modules.ltt.entity.CdProjectEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;


/**
 * 项目表
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-14 23:45:07
 */
public interface CdProjectService extends IService<CdProjectEntity> {

    /**
     * 分页查询
     * @param cdProject
     * @return
     */
    PageUtils queryPage(CdProjectDTO cdProject);
    /**
     * 根据id查询
     * @param id
     * @return
     */
    CdProjectVO getById(Integer id);
    /**
     * 根据id查询
     * @param
     * @return
     */
    List<CdProjectEntity> listByCache();
    /**
     * 保存
     * @param cdProject
     * @return
     */
    boolean save(CdProjectDTO cdProject);
    /**
     * 根据id修改
     * @param cdProject
     * @return
     */
    boolean updateById(CdProjectDTO cdProject);
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

    void audit(CdProjectDTO cdProject);

    void auditAll(CdProjectDTO cdProject);
}

