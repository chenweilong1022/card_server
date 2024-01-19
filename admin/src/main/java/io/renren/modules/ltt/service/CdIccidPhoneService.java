package io.renren.modules.ltt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.ltt.dto.CdIccidPhoneDTO;
import io.renren.modules.ltt.vo.CdIccidPhoneVO;
import io.renren.modules.ltt.entity.CdIccidPhoneEntity;

import java.io.Serializable;
import java.util.Collection;


/**
 * iccid手机对接表
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-30 00:10:56
 */
public interface CdIccidPhoneService extends IService<CdIccidPhoneEntity> {

    /**
     * 分页查询
     * @param cdIccidPhone
     * @return
     */
    PageUtils queryPage(CdIccidPhoneDTO cdIccidPhone);
    /**
     * 根据id查询
     * @param id
     * @return
     */
    CdIccidPhoneVO getById(Integer id);
    /**
     * 保存
     * @param cdIccidPhone
     * @return
     */
    boolean save(CdIccidPhoneDTO cdIccidPhone);
    /**
     * 根据id修改
     * @param cdIccidPhone
     * @return
     */
    boolean updateById(CdIccidPhoneDTO cdIccidPhone);
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

    byte[] export(CdIccidPhoneDTO cdIccidPhone);
}

