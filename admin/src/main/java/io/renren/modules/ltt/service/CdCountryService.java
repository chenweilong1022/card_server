package io.renren.modules.ltt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.ltt.dto.CdCountryDTO;
import io.renren.modules.ltt.vo.CdCountryVO;
import io.renren.modules.ltt.entity.CdCountryEntity;

import java.io.Serializable;
import java.util.Collection;


/**
 * 国家表
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2023-10-14 23:45:07
 */
public interface CdCountryService extends IService<CdCountryEntity> {

    /**
     * 分页查询
     * @param cdCountry
     * @return
     */
    PageUtils queryPage(CdCountryDTO cdCountry);
    /**
     * 根据id查询
     * @param id
     * @return
     */
    CdCountryVO getById(Integer id);
    /**
     * 保存
     * @param cdCountry
     * @return
     */
    boolean save(CdCountryDTO cdCountry);
    /**
     * 根据id修改
     * @param cdCountry
     * @return
     */
    boolean updateById(CdCountryDTO cdCountry);
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

