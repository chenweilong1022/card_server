package io.renren.modules.ltt.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import io.renren.datasources.annotation.Game;
import io.renren.modules.app.dto.AppCdBoardUpdateBoardDTO;
import io.renren.modules.app.dto.AppCdCardUpdateIccidDTO;
import io.renren.modules.ltt.entity.CdBoardEntity;
import io.renren.modules.ltt.entity.CdDevicesEntity;
import io.renren.modules.ltt.entity.CdIccidPhoneEntity;
import io.renren.modules.ltt.enums.DeleteFlag;
import io.renren.modules.ltt.enums.WorkType;
import io.renren.modules.ltt.service.CdDevicesService;
import io.renren.modules.ltt.service.CdIccidPhoneService;
import io.renren.modules.ltt.vo.GroupByDeviceIdVO;
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
import java.util.stream.Collectors;


@Service("cdCardService")
@Game
public class CdCardServiceImpl extends ServiceImpl<CdCardDao, CdCardEntity> implements CdCardService {

    @Autowired
    private CdIccidPhoneService cdIccidPhoneService;
    @Autowired
    private CdDevicesService cdDevicesService;


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

//        CdCardEntity update = new CdCardEntity();
//        update.setIccid(iccidDTO.getIccid());
//        update.setPhone(iccidDTO.getPhone());
//        this.update(update,new QueryWrapper<CdCardEntity>()
//                .lambda().eq(CdCardEntity::getDeviceId,iccidDTO.getDeviceId())
//                .eq(CdCardEntity::getBoardIndexed,iccidDTO.getBoardIndexed())
//                .eq(CdCardEntity::getIndexed,iccidDTO.getIndexed())
//        );
//
//        CdIccidPhoneEntity one = cdIccidPhoneService.getOne(new QueryWrapper<CdIccidPhoneEntity>().lambda()
//                .eq(CdIccidPhoneEntity::getIccid,iccidDTO.getIccid())
//        );
//        if (ObjectUtil.isNull(one)) {
//            CdIccidPhoneEntity save = new CdIccidPhoneEntity();
//            save.setIccid(iccidDTO.getIccid());
//            save.setPhone(iccidDTO.getPhone());
//            cdIccidPhoneService.save(save);
//        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void uploadIccids(Map<String, String> params) {
        String deviceId = params.get("deviceId");
        //获取设备信息
        CdDevicesEntity one = cdDevicesService.getOne(new QueryWrapper<CdDevicesEntity>().lambda()
                .eq(CdDevicesEntity::getIccid,deviceId)
        );
        List<CdCardEntity> list = this.list(new QueryWrapper<CdCardEntity>().lambda()
                .eq(CdCardEntity::getDeviceId,deviceId)
        );
        List<AppCdCardUpdateIccidDTO> appCdCardUpdateIccidDTOS = new ArrayList<>();
        for (String s : params.keySet()) {
            if (s.contains("deviceId")){
                continue;
            }else if (s.contains("phoneNumber")) {
                continue;
            }
            String[] rs = s.split("R");
            int boardIndexed = Integer.parseInt(rs[0], 10);
            int indexed = Integer.parseInt(rs[1], 16);
            String iccid = params.get(s);
            String phoneNumber = params.get(s + "phoneNumber");

            AppCdCardUpdateIccidDTO appCdCardUpdateIccidDTO = new AppCdCardUpdateIccidDTO();
            appCdCardUpdateIccidDTO.setDeviceId(deviceId);
            appCdCardUpdateIccidDTO.setBoardIndexed(boardIndexed - 1);
            appCdCardUpdateIccidDTO.setIndexed(indexed - 1);
            appCdCardUpdateIccidDTO.setIccid(iccid);

            appCdCardUpdateIccidDTO.setPhone("");
            if (StrUtil.isNotEmpty(phoneNumber)) {
                appCdCardUpdateIccidDTO.setPhone(phoneNumber);
            }
            appCdCardUpdateIccidDTOS.add(appCdCardUpdateIccidDTO);
        }
        //上报的卡数据更新
        Map<String, AppCdCardUpdateIccidDTO> collect = appCdCardUpdateIccidDTOS.stream().collect(Collectors.toMap(x -> x.getBoardIndexed() + "-" + x.getIndexed(), y -> y));
        List<CdCardEntity> updates = new ArrayList<>();
        for (CdCardEntity cdCardEntity : list) {
            String x = cdCardEntity.getBoardIndexed() + "-" + cdCardEntity.getIndexed();
            AppCdCardUpdateIccidDTO appCdCardUpdateIccidDTO = collect.get(x);
            CdCardEntity update = new CdCardEntity();
            update.setId(cdCardEntity.getId());
            update.setIccid(appCdCardUpdateIccidDTO.getIccid());
            update.setPhone(appCdCardUpdateIccidDTO.getPhone());
            updates.add(update);
        }
        //修改卡信息
        this.updateBatchById(updates);
        //修改卡片为工作状态
        if (ObjectUtil.isNotNull(one)) {
            one.setWorkType(WorkType.WorkType3.getKey());
            cdDevicesService.updateById(one);
        }
    }


    @Override
    public List<GroupByDeviceIdVO> groupByDeviceId() {
        return baseMapper.groupByDeviceId();
    }

}
