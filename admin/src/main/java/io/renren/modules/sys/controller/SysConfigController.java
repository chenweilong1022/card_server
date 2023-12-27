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

package io.renren.modules.sys.controller;


import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.benmanes.caffeine.cache.Cache;
import io.renren.common.annotation.SysLog;
import io.renren.common.utils.ConfigConstant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.sys.entity.ProjectWorkEntity;
import io.renren.modules.sys.entity.SysConfigEntity;
import io.renren.modules.sys.service.SysConfigService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 系统配置信息
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2019-01-29 14:59:19
 */
@RestController
@RequestMapping("/sys/config")
@Validated
public class SysConfigController extends AbstractController {
	@Autowired
	private SysConfigService sysConfigService;

	/**
	 * 所有配置列表
	 */
	@GetMapping("/list")
	@RequiresPermissions("sys:config:list")
	public R list(SysConfigEntity sysConfigEntity){
		PageUtils page = sysConfigService.queryPage(sysConfigEntity);

		return R.ok().put("page", page);
	}

	@Resource(name = "caffeineCacheProjectWorkEntity")
	private Cache<String, ProjectWorkEntity> caffeineCacheProjectWorkEntity;

	/**
	 * 配置信息
	 */
	@GetMapping("/info/{id}")
	@RequiresPermissions("sys:config:info")
	public R info(@PathVariable("id") Long id){
		//查询config
		String cacheKey = String.format("%s_%s", ConfigConstant.PROJECT_WORK_KEY, id);
		SysConfigEntity config = sysConfigService.getOne(new QueryWrapper<SysConfigEntity>().lambda()
				.eq(SysConfigEntity::getParamKey,cacheKey)
		);
		//项目
		ProjectWorkEntity projectWorkEntity = caffeineCacheProjectWorkEntity.getIfPresent(cacheKey);
		if (ObjectUtil.isNotNull(config)) {
			config.setKey(cacheKey);
			config.setProjectId(projectWorkEntity.getProjectId());;
			config.setPhonePre(projectWorkEntity.getPhonePre());
			config.setUserId(projectWorkEntity.getUserId());
			config.setCodeApiUrl(projectWorkEntity.getCodeApiUrl());
			config.setPlatform(projectWorkEntity.getPlatform());
			config.setCodeAcquisitionType(projectWorkEntity.getCodeAcquisitionType());
		}else {
			ProjectWorkEntity p2 = caffeineCacheProjectWorkEntity.getIfPresent(ConfigConstant.PROJECT_WORK_KEY);
			SysConfigEntity configNew = new SysConfigEntity();
			configNew.setUserId(p2.getUserId());
			configNew.setProjectId(p2.getProjectId());
			configNew.setPlatform(p2.getPlatform());
			configNew.setPhonePre(p2.getPhonePre());
			configNew.setCodeApiUrl(p2.getCodeApiUrl());
			configNew.setCodeAcquisitionType(p2.getCodeAcquisitionType());
			configNew.setType(2);
			configNew.setKey(cacheKey);
			sysConfigService.save(configNew);
			config = configNew;
		}
		return R.ok().put("config", config);
	}

	/**
	 * 保存配置
	 */
	@SysLog("保存配置")
	@PostMapping("/save")
	@RequiresPermissions("sys:config:save")
	public R save(@Valid @RequestBody SysConfigEntity config){

		sysConfigService.save(config);

		return R.ok();
	}

	/**
	 * 修改配置
	 */
	@SysLog("修改配置")
	@PostMapping("/update")
	@RequiresPermissions("sys:config:update")
	public R update(@RequestBody SysConfigEntity config){
		ValidatorUtils.validateEntity(config);
		//查询config
		String cacheKey = String.format("%s_%s", ConfigConstant.PROJECT_WORK_KEY, config.getId());
		config.setKey(cacheKey);
		SysConfigEntity config2 = sysConfigService.getOne(new QueryWrapper<SysConfigEntity>().lambda()
				.eq(SysConfigEntity::getParamKey,cacheKey)
		);
		config.setId(config2.getId());
		sysConfigService.update(config);

		return R.ok();
	}

	/**
	 * 删除配置
	 */
	@SysLog("删除配置")
	@PostMapping("/delete")
	@RequiresPermissions("sys:config:delete")
	public R delete(@RequestBody Long[] ids){
		sysConfigService.deleteBatch(ids);

		return R.ok();
	}

}
