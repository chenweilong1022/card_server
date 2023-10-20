package io.renren.modules.sys.controller;

import io.renren.modules.app.annotation.LoginUser;
import io.renren.modules.ltt.entity.CdUserEntity;
import io.renren.modules.sys.entity.SysUserEntity;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Controller公共组件
 *
 * @author chenweilong
 * @email chenweilong@qq.com
 * @date 2019-01-29 14:59:19
 */
public abstract class AbstractController {
	protected Logger logger = LoggerFactory.getLogger(getClass());


	protected HttpServletRequest request;
	protected HttpServletResponse response;
	protected HttpSession session;
	protected CdUserEntity cdUserEntity;


	/**
	 * 注入程序参数
	 * @param request
	 * @param response
	 */
	@ModelAttribute
	public void init(
			HttpServletRequest request,
			HttpServletResponse response,
			@LoginUser CdUserEntity cdUserEntity
			) {
		this.request = request;
		this.response = response;
		this.session = request.getSession();
		this.cdUserEntity = cdUserEntity;
	}

	protected SysUserEntity getUser() {
		return (SysUserEntity) SecurityUtils.getSubject().getPrincipal();
	}

	protected Long getUserId() {
		return getUser().getUserId();
	}
}
