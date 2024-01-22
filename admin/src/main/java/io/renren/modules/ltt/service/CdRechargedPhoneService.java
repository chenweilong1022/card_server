package io.renren.modules.ltt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.ltt.dto.CdRechargedPhoneDTO;
import io.renren.modules.ltt.vo.CdRechargedPhoneVO;
import io.renren.modules.ltt.entity.CdRechargedPhoneEntity;

import java.io.Serializable;
import java.util.Collection;


/**
 * 已充值手机
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2024-01-22 21:38:09
 */
public interface CdRechargedPhoneService extends IService<CdRechargedPhoneEntity> {

    /**
     * 分页查询
     * @param cdRechargedPhone
     * @return
     */
    PageUtils queryPage(CdRechargedPhoneDTO cdRechargedPhone);
    /**
     * 根据id查询
     * @param id
     * @return
     */
    CdRechargedPhoneVO getById(Integer id);
    /**
     * 保存
     * @param cdRechargedPhone
     * @return
     */
    boolean save(CdRechargedPhoneDTO cdRechargedPhone);
    /**
     * 根据id修改
     * @param cdRechargedPhone
     * @return
     */
    boolean updateById(CdRechargedPhoneDTO cdRechargedPhone);
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

