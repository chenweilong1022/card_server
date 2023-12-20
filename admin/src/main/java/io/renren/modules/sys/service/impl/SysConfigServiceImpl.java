/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.renren.modules.sys.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.benmanes.caffeine.cache.Cache;
import com.google.gson.Gson;
import io.renren.common.base.vo.EnumVo;
import io.renren.common.exception.RRException;
import io.renren.common.utils.ConfigConstant;
import io.renren.common.utils.EnumUtil;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.ltt.entity.CdProjectEntity;
import io.renren.modules.ltt.enums.FirefoxProjectItemId;
import io.renren.modules.ltt.enums.WorldcodeProjectItemId;
import io.renren.modules.ltt.service.CdProjectService;
import io.renren.modules.ltt.vo.CdProjectVO;
import io.renren.modules.sys.dao.SysConfigDao;
import io.renren.modules.sys.entity.ProjectWorkEntity;
import io.renren.modules.sys.entity.SysConfigEntity;
import io.renren.modules.sys.redis.SysConfigRedis;
import io.renren.modules.sys.service.SysConfigService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Service("sysConfigService")
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfigEntity> implements SysConfigService {
	@Autowired
	private SysConfigRedis sysConfigRedis;
	@Resource(name = "caffeineCacheProjectWorkEntity")
	private Cache<String, ProjectWorkEntity> caffeineCacheProjectWorkEntity;
	@Autowired
	private CdProjectService cdProjectService;

	@Override
	public PageUtils queryPage(SysConfigEntity sysConfigEntity) {
		IPage<SysConfigEntity> page = baseMapper.selectPage(
				new Query<SysConfigEntity>(sysConfigEntity).getPage(),
				new QueryWrapper<SysConfigEntity>().lambda()
					.like(StringUtils.isNotBlank(sysConfigEntity.getParamKey()),SysConfigEntity::getParamKey, sysConfigEntity.getParamKey())
		);

		return new PageUtils(page);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean save(SysConfigEntity config) {
//		获取类型
		if (2 == config.getType()) {
			ProjectWorkEntity projectWorkEntity = new ProjectWorkEntity();
			projectWorkEntity.setProjectId(config.getProjectId());
			projectWorkEntity.setPhonePre(config.getPhonePre());
			projectWorkEntity.setUserId(config.getUserId());
			projectWorkEntity.setCodeApiUrl(config.getCodeApiUrl());
			projectWorkEntity.setPlatform(config.getPlatform());
			projectWorkEntity.setCodeAcquisitionType(config.getCodeAcquisitionType());
			String jsonStr = JSONUtil.toJsonStr(projectWorkEntity);
			config.setParamKey(ConfigConstant.PROJECT_WORK_KEY);
			config.setParamValue(jsonStr);
			caffeineCacheProjectWorkEntity.put(ConfigConstant.PROJECT_WORK_KEY,projectWorkEntity);

			//项目
			CdProjectVO cdProjectVO = cdProjectService.getById(config.getProjectId());
			if (ObjectUtil.isNotNull(cdProjectVO)) {
				Integer itemId = null;
				if (1 == config.getPlatform()) {
					Map<String, Integer> collect = EnumUtil.enumToVo(FirefoxProjectItemId.values()).stream().collect(Collectors.toMap(EnumVo::getValue, EnumVo::getKey));
					itemId = collect.get(cdProjectVO.getName());
				}else if (2 == config.getPlatform()) {
					Map<String, Integer> collect = EnumUtil.enumToVo(WorldcodeProjectItemId.values()).stream().collect(Collectors.toMap(EnumVo::getValue, EnumVo::getKey));
					itemId = collect.get(cdProjectVO.getName());
				}
				if (ObjectUtil.isNotNull(itemId)) {
					cdProjectService.update(new CdProjectEntity().setItemId(-1),new QueryWrapper<CdProjectEntity>());
					CdProjectEntity cdProjectEntity = new CdProjectEntity();
					cdProjectEntity.setId(cdProjectVO.getId());
					cdProjectEntity.setItemId(itemId);
					cdProjectService.updateById(cdProjectEntity);
				}
			}
		}
		baseMapper.insert(config);





		return false;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void update(SysConfigEntity config) {
		if (2 == config.getType()) {
			ProjectWorkEntity projectWorkEntity = new ProjectWorkEntity();
			projectWorkEntity.setProjectId(config.getProjectId());
			projectWorkEntity.setPhonePre(config.getPhonePre());
			projectWorkEntity.setUserId(config.getUserId());
			projectWorkEntity.setCodeApiUrl(config.getCodeApiUrl());
			projectWorkEntity.setPlatform(config.getPlatform());
			projectWorkEntity.setCodeAcquisitionType(config.getCodeAcquisitionType());
			String jsonStr = JSONUtil.toJsonStr(projectWorkEntity);
			config.setParamKey(ConfigConstant.PROJECT_WORK_KEY);
			config.setParamValue(jsonStr);
			caffeineCacheProjectWorkEntity.put(ConfigConstant.PROJECT_WORK_KEY,projectWorkEntity);

			//项目
			CdProjectVO cdProjectVO = cdProjectService.getById(config.getProjectId());
			if (ObjectUtil.isNotNull(cdProjectVO)) {
				Integer itemId = null;
				if (1 == config.getPlatform()) {
					Map<String, Integer> collect = EnumUtil.enumToVo(FirefoxProjectItemId.values()).stream().collect(Collectors.toMap(EnumVo::getValue, EnumVo::getKey));
					itemId = collect.get(cdProjectVO.getName());
				}else if (2 == config.getPlatform()) {
					Map<String, Integer> collect = EnumUtil.enumToVo(WorldcodeProjectItemId.values()).stream().collect(Collectors.toMap(EnumVo::getValue, EnumVo::getKey));
					itemId = collect.get(cdProjectVO.getName());
				}
				if (ObjectUtil.isNotNull(itemId)) {
					cdProjectService.update(new CdProjectEntity().setItemId(-1),new QueryWrapper<CdProjectEntity>());
					CdProjectEntity cdProjectEntity = new CdProjectEntity();
					cdProjectEntity.setId(cdProjectVO.getId());
					cdProjectEntity.setItemId(itemId);
					cdProjectService.updateById(cdProjectEntity);
				}
			}
		}
		this.updateById(config);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateValueByKey(String key, String value) {
		baseMapper.updateValueByKey(key, value);
		sysConfigRedis.delete(key);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteBatch(Long[] ids) {
		for(Long id : ids){
			SysConfigEntity config = this.getById(id);
			sysConfigRedis.delete(config.getParamKey());
		}

		this.removeByIds(Arrays.asList(ids));
	}

	@Override
	public String getValue(String key) {
		SysConfigEntity config = sysConfigRedis.get(key);
		if(config == null){
			config = baseMapper.queryByKey(key);
			sysConfigRedis.saveOrUpdate(config);
		}

		return config == null ? null : config.getParamValue();
	}

	@Override
	public <T> T getConfigObject(String key, Class<T> clazz) {
		String value = getValue(key);
		if(StringUtils.isNotBlank(value)){
			return new Gson().fromJson(value, clazz);
		}

		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new RRException("获取参数失败");
		}
	}
}
