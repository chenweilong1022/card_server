package io.renren.modules.app.interceptor;


import cn.hutool.core.util.ObjectUtil;
import io.jsonwebtoken.Claims;
import io.renren.common.exception.RRException;
import io.renren.common.validator.Assert;
import io.renren.modules.app.annotation.Login;
import io.renren.modules.app.utils.JwtUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 权限(Token)验证
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-03-23 15:38
 */
@Component
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {
    @Autowired
    private JwtUtils jwtUtils;

    public static final String USER_KEY = "memberId";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "*");
        response.setHeader("Access-Control-Allow-Credentials", "true");


        Login annotation;
        if(handler instanceof HandlerMethod) {
            annotation = ((HandlerMethod) handler).getMethodAnnotation(Login.class);
        }else{
            return true;
        }

        String token = request.getHeader(jwtUtils.getHeader());
        if(StringUtils.isBlank(token)){
            token = request.getParameter(jwtUtils.getHeader());
        }
//
//        if(annotation == null){
//            /**
//             * token不为null
//             */
//            if (StringUtils.isNotBlank(token)) {
//                check(token,request);
//            }
//            return true;
//        }
//
//        //凭证为空
        if(StringUtils.isBlank(token) && ObjectUtil.isNotNull(annotation)){
//            throw new RRException(jwtUtils.getHeader() + "不能为空", HttpStatus.UNAUTHORIZED.value());
            Assert.isTrue(505,true,"PleaseLogIn");
            return false;
        }
        request.setAttribute(USER_KEY, token);
        return true;
    }

//    private void check(String token,HttpServletRequest request) {
//        Claims claims = jwtUtils.getClaimByToken(token);
//        if(claims == null || jwtUtils.isTokenExpired(claims.getExpiration())){
//            throw new RRException(jwtUtils.getHeader() + "失效，请重新登录", HttpStatus.UNAUTHORIZED.value());
//        }
//
//        //设置userId到request里，后续根据userId，获取用户信息
//        request.setAttribute(USER_KEY, Long.parseLong(claims.getSubject()));
//    }
}
