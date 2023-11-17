package io.renren.modules.ltt.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import io.renren.datasources.annotation.Game;
import io.renren.modules.app.dto.AppCdBoardUpdateBoardDTO;
import io.renren.modules.app.dto.AppCdCardUpdateIccidDTO;
import io.renren.modules.ltt.entity.CdBoardEntity;
import io.renren.modules.ltt.entity.CdIccidPhoneEntity;
import io.renren.modules.ltt.enums.DeleteFlag;
import io.renren.modules.ltt.service.CdIccidPhoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.ltt.dao.CdCardDao;
import io.renren.modules.ltt.entity.CdCardEntity;
import io.renren.modules.ltt.dto.CdCardDTO;
import io.renren.modules.ltt.vo.CdCardVO;
import io.renren.modules.ltt.service.CdCardService;
import io.renren.modules.ltt.conver.CdCardConver;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;


@Service("cdCardService")
@Game
public class CdCardServiceImpl extends ServiceImpl<CdCardDao, CdCardEntity> implements CdCardService {

    @Autowired
    private CdIccidPhoneService cdIccidPhoneService;


    @Override
    public PageUtils<CdCardVO> queryPage(CdCardDTO cdCard) {
        IPage<CdCardEntity> page = baseMapper.selectPage(
                new Query<CdCardEntity>(cdCard).getPage(),
                new QueryWrapper<CdCardEntity>()
        );

        return PageUtils.<CdCardVO>page(page).setList(CdCardConver.MAPPER.conver(page.getRecords()));
    }
    @Override
    public CdCardVO getById(Integer id) {
        return CdCardConver.MAPPER.conver(baseMapper.selectById(id));
    }

    @Override
    public boolean save(CdCardDTO cdCard) {
        CdCardEntity cdCardEntity = CdCardConver.MAPPER.converDTO(cdCard);
        return this.save(cdCardEntity);
    }

    @Override
    public boolean updateById(CdCardDTO cdCard) {
        CdCardEntity cdCardEntity = CdCardConver.MAPPER.converDTO(cdCard);
        return this.updateById(cdCardEntity);
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
    public void uploadCard(AppCdBoardUpdateBoardDTO boardDTO) {
        this.remove(new QueryWrapper<CdCardEntity>().lambda()
                .eq(CdCardEntity::getDeviceId,boardDTO.getDeviceId())
        );
        List<CdCardEntity> cdCardEntities = new ArrayList<>();
        for (int i = 0; i < boardDTO.getCount(); i++) {
            for (int i1 = 0; i1 < 12; i1++) {
                CdCardEntity init = new CdCardEntity();
                init.setDeviceId(boardDTO.getDeviceId());
                init.setBoardIndexed(i);
                init.setIndexed(i1);
                init.setDeleteFlag(DeleteFlag.NO.getKey());
                init.setCreateTime(DateUtil.date());
                cdCardEntities.add(init);
            }
        }
        boolean b = this.saveBatch(cdCardEntities);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void uploadIccid(AppCdCardUpdateIccidDTO iccidDTO) {

        CdCardEntity update = new CdCardEntity();
        update.setIccid(iccidDTO.getIccid());
        update.setPhone(iccidDTO.getPhone());
        this.update(update,new QueryWrapper<CdCardEntity>()
                .lambda().eq(CdCardEntity::getDeviceId,iccidDTO.getDeviceId())
                .eq(CdCardEntity::getBoardIndexed,iccidDTO.getBoardIndexed())
                .eq(CdCardEntity::getIndexed,iccidDTO.getIndexed())
        );

        CdIccidPhoneEntity one = cdIccidPhoneService.getOne(new QueryWrapper<CdIccidPhoneEntity>().lambda()
                .eq(CdIccidPhoneEntity::getIccid,iccidDTO.getIccid())
        );
        if (ObjectUtil.isNull(one)) {
            CdIccidPhoneEntity save = new CdIccidPhoneEntity();
            save.setIccid(iccidDTO.getIccid());
            save.setPhone(iccidDTO.getPhone());
            cdIccidPhoneService.save(save);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void uploadIccids(Map<String, String> params) {
        String deviceId = params.get("deviceId");
        for (String s : params.keySet()) {
            if (s.contains("deviceId")){
                continue;
            }else if (s.contains("phoneNumber")) {
                continue;
            }
            String[] rs = s.split("R");
            int boardIndexed = Integer.parseInt(rs[0], 16);
            int indexed = Integer.parseInt(rs[1], 16);
            String iccid = params.get(s);
            String phoneNumber = params.get(s + "phoneNumber");

            AppCdCardUpdateIccidDTO appCdCardUpdateIccidDTO = new AppCdCardUpdateIccidDTO();
            appCdCardUpdateIccidDTO.setDeviceId(deviceId);
            appCdCardUpdateIccidDTO.setBoardIndexed(boardIndexed - 1);
            appCdCardUpdateIccidDTO.setIndexed(indexed - 1);
            appCdCardUpdateIccidDTO.setIccid(iccid);
            CdIccidPhoneEntity one = cdIccidPhoneService.getOne(new QueryWrapper<CdIccidPhoneEntity>().lambda()
                    .eq(CdIccidPhoneEntity::getIccid,iccid)
            );
            appCdCardUpdateIccidDTO.setPhone("");
            if (ObjectUtil.isNotNull(one)) {
                appCdCardUpdateIccidDTO.setPhone(one.getPhone());
            }
            if (StrUtil.isNotEmpty(phoneNumber)) {
                appCdCardUpdateIccidDTO.setPhone(phoneNumber);
            }
            uploadIccid(appCdCardUpdateIccidDTO);
        }
    }

}
