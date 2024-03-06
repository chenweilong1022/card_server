package io.renren.modules.ltt.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import io.renren.common.validator.Assert;
import io.renren.datasources.annotation.Game;
import io.renren.modules.ltt.enums.DeleteFlag;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.ltt.dao.CdUserDao;
import io.renren.modules.ltt.entity.CdUserEntity;
import io.renren.modules.ltt.dto.CdUserDTO;
import io.renren.modules.ltt.vo.CdUserVO;
import io.renren.modules.ltt.service.CdUserService;
import io.renren.modules.ltt.conver.CdUserConver;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;


@Service("cdUserService")
@Game
public class CdUserServiceImpl extends ServiceImpl<CdUserDao, CdUserEntity> implements CdUserService {

    @Override
    public PageUtils<CdUserVO> queryPage(CdUserDTO cdUser) {
        IPage<CdUserEntity> page = baseMapper.selectPage(
                new Query<CdUserEntity>(cdUser).getPage(),
                new QueryWrapper<CdUserEntity>()
        );

        return PageUtils.<CdUserVO>page(page).setList(CdUserConver.MAPPER.conver(page.getRecords()));
    }
    @Override
    public CdUserVO getById(Integer id) {
        return CdUserConver.MAPPER.conver(baseMapper.selectById(id));
    }

    @Override
    public boolean save(CdUserDTO cdUser) {
        CdUserEntity cdUserEntity = CdUserConver.MAPPER.converDTO(cdUser);
        return this.save(cdUserEntity);
    }

    @Override
    public boolean updateById(CdUserDTO cdUser) {
        CdUserEntity cdUserEntity = CdUserConver.MAPPER.converDTO(cdUser);
        return this.updateById(cdUserEntity);
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
    public void register(CdUserDTO cdUser) {
        //根据账号查询
        CdUserEntity getOne = this.getOne(new QueryWrapper<CdUserEntity>().lambda()
                .eq(CdUserEntity::getAccount,cdUser.getAccount())
        );
        Assert.isTrue(ObjectUtil.isNotNull(getOne),"AccountAlreadyExists");

        CdUserEntity update = new CdUserEntity();
        update.setBalance(BigDecimal.ZERO);
        update.setPassword(cdUser.getPassword());
        update.setAccount(cdUser.getAccount());
        update.setDeleteFlag(DeleteFlag.NO.getKey());
        update.setCreateTime(DateUtil.date());
        this.save(update);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public String login(CdUserDTO cdUser) {
        //根据账号查询
        CdUserEntity getOne = this.getOne(new QueryWrapper<CdUserEntity>().lambda()
                .eq(CdUserEntity::getAccount,cdUser.getAccount())
        );
        Assert.isTrue(ObjectUtil.isNull(getOne),"AccountDoesNotExist");
        Assert.isTrue(!getOne.getPassword().equals(cdUser.getPassword()),"PasswordError");
        String uuid = getOne.getToken();
        //获取uuid
        if (StrUtil.isEmpty(getOne.getToken())) {
            uuid = RandomUtil.simpleUUID();
            CdUserEntity update = new CdUserEntity();
            update.setId(getOne.getId());
            update.setToken(uuid);
            this.updateById(update);
            return uuid;
        }
        return uuid;
    }

}
