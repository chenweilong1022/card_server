package io.renren.modules.ltt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.ltt.dto.CdProjectSmsRecordDTO;
import io.renren.modules.ltt.vo.CdProjectSmsRecordVO;
import io.renren.modules.ltt.entity.CdProjectSmsRecordEntity;

import java.io.Serializable;
import java.util.Collection;


/**
 * 项目短信记录
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-20 00:54:08
 */
public interface CdProjectSmsRecordService extends IService<CdProjectSmsRecordEntity> {

    /**
     * 分页查询
     * @param cdProjectSmsRecord
     * @return
     */
    PageUtils queryPage(CdProjectSmsRecordDTO cdProjectSmsRecord);
    /**
     * 根据id查询
     * @param id
     * @return
     */
    CdProjectSmsRecordVO getById(Integer id);
    /**
     * 保存
     * @param cdProjectSmsRecord
     * @return
     */
    boolean save(CdProjectSmsRecordDTO cdProjectSmsRecord);
    /**
     * 根据id修改
     * @param cdProjectSmsRecord
     * @return
     */
    boolean updateById(CdProjectSmsRecordDTO cdProjectSmsRecord);
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
}

