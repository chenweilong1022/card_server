package io.renren.modules.ltt.service.impl;

import cn.hutool.core.date.DateUtil;
import io.renren.datasources.annotation.Game;
import io.renren.modules.app.dto.AppCdBoardUpdateBoardDTO;
import io.renren.modules.ltt.enums.DeleteFlag;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.ltt.dao.CdBoardDao;
import io.renren.modules.ltt.entity.CdBoardEntity;
import io.renren.modules.ltt.dto.CdBoardDTO;
import io.renren.modules.ltt.vo.CdBoardVO;
import io.renren.modules.ltt.service.CdBoardService;
import io.renren.modules.ltt.conver.CdBoardConver;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service("cdBoardService")
@Game
public class CdBoardServiceImpl extends ServiceImpl<CdBoardDao, CdBoardEntity> implements CdBoardService {

    @Override
    public PageUtils<CdBoardVO> queryPage(CdBoardDTO cdBoard) {
        IPage<CdBoardEntity> page = baseMapper.selectPage(
                new Query<CdBoardEntity>(cdBoard).getPage(),
                new QueryWrapper<CdBoardEntity>()
        );

        return PageUtils.<CdBoardVO>page(page).setList(CdBoardConver.MAPPER.conver(page.getRecords()));
    }
    @Override
    public CdBoardVO getById(Integer id) {
        return CdBoardConver.MAPPER.conver(baseMapper.selectById(id));
    }

    @Override
    public boolean save(CdBoardDTO cdBoard) {
        CdBoardEntity cdBoardEntity = CdBoardConver.MAPPER.converDTO(cdBoard);
        return this.save(cdBoardEntity);
    }

    @Override
    public boolean updateById(CdBoardDTO cdBoard) {
        CdBoardEntity cdBoardEntity = CdBoardConver.MAPPER.converDTO(cdBoard);
        return this.updateById(cdBoardEntity);
    }

    @Override
    public boolean removeById(Serializable id) {
        return super.removeById(id);
    }

    @Override
    public boolean removeByIds(Collection<? extends Serializable> ids) {
        return super.removeByIds(ids);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void uploadBoard(AppCdBoardUpdateBoardDTO boardDTO) {
        this.remove(new QueryWrapper<CdBoardEntity>().lambda()
                .eq(CdBoardEntity::getDeviceId,boardDTO.getDeviceId())
        );
        List<CdBoardEntity> cdBoardEntities = new ArrayList<>();
        for (int i = 0; i < boardDTO.getCount(); i++) {
            CdBoardEntity init = new CdBoardEntity();
            init.setDeviceId(boardDTO.getDeviceId());
            init.setIndexed(i);
            init.setDeleteFlag(DeleteFlag.NO.getKey());
            init.setCreateTime(DateUtil.date());
            cdBoardEntities.add(init);
        }
        boolean b = this.saveBatch(cdBoardEntities);
    }

}
