package com.base.user.server.filter;

import java.lang.reflect.Method;
import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;

import com.base.common.constant.Result;
import com.base.common.util.DateUtil;
import com.base.user.client.common.UserConstant;
import com.base.user.client.model.TokenFilter;
import com.base.user.server.common.Constant.ErrorCode;
import com.base.user.server.manager.UserManager;
import com.base.user.server.model.UserConvertor;
import com.base.user.server.model.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author:小M
 * @date:2019/1/12 12:40 PM
 */
public class TokenInterceptor extends HandlerInterceptorAdapter implements HandlerInterceptor {

    @Value("${local:true}")
    private Boolean enableTokenFilter;

    @Value("${local:7}")
    private Integer tokenExpireDay;

    @Autowired
    private UserManager userManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {

        /**
         * 不开启拦截的，直接返回
         */
        if(!enableTokenFilter) {
            return true;
        }
        this.setHeader(request, response, handler);

        String uri = request.getRequestURI().toString();
        System.out.println("uri:"+uri);

        // 如果不是映射到方法里，直接通过
        if(!(handler instanceof HandlerMethod)) {
            //System.out.println("uri:"+uri + " 不是方法，跳过");
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod)handler;
        Method method = handlerMethod.getMethod();

        // 打了注解的方法才需要拦截
        TokenFilter tokenFilter = method.getAnnotation(TokenFilter.class);
        if(tokenFilter == null) {
            return true;
        }

        String token = request.getParameter("token");
        if(StringUtils.isEmpty(token)) {
            token = request.getHeader("token");
        }
        if(StringUtils.isEmpty(token)) {
            Cookie[] cookies = request.getCookies();
            if(cookies != null) {
                for(Cookie cookie : cookies) {
                    if(cookie.getName().equals("token")) {
                        token = cookie.getValue();
                    }
                }
            }
        }
        // 不存在 token
        if(StringUtils.isEmpty(token)) {
            System.out.println("uri:"+uri + " , token为空");
            Result wxLoginResult = Result.error(ErrorCode.TOKEN_NULL.getCode(),ErrorCode.TOKEN_NULL.getDesc());
            response.getWriter().write(JSON.toJSONString(wxLoginResult));
            return false;
        }
        // 根据 token 查询不到用户
        UserDTO userDTO = userManager.findByToken(token);
        if(userDTO == null) {
            System.out.println("uri:"+uri + " , token查不到用户");
            Result wxLoginResult = Result.error(ErrorCode.TOKEN_ERROR.getCode(),ErrorCode.TOKEN_ERROR.getDesc());
            response.getWriter().write(JSON.toJSONString(wxLoginResult));
            return false;
        }
        // 根据token查询得到用户，但是 token 过期
        if(DateUtil.addDays(new Date(), -tokenExpireDay).getTime() > userDTO.getGmtModified().getTime()) {
            System.out.println("uri:"+uri + " , token过期");
            Result wxLoginResult = Result.error(ErrorCode.TOKEN_EXPIRE.getCode(),ErrorCode.TOKEN_EXPIRE.getDesc());
            response.getWriter().write(JSON.toJSONString(wxLoginResult));
            return false;
        }

        // 把用户放在attribute中, 在 controller 中获取
        request.setAttribute(UserConstant.REQUEST_USER, UserConvertor.dto2VO(userDTO));
        System.out.println("uri:"+uri + " , token校验成功");
        return true;
    }

    private void setHeader(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{
        response.setHeader("Content-Type", "text/html;charset=UTF-8");
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        response.setContentType("text/html;charset=utf-8");
        response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        response.setHeader("Access-Control-Allow-Origin", "http://192.168.0.107:8080");
        response.setHeader("Cache-Control","no-cache");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Connection, User-Agent, Cookie, Authorization");
        super.preHandle(request, response, handler);
    }


    public void setEnableTokenFilter(Boolean enableTokenFilter) {
        this.enableTokenFilter = enableTokenFilter;
    }

    public void setUserManager(UserManager userManager) {
        this.userManager = userManager;
    }

    public void setTokenExpireDay(Integer tokenExpireDay) {
        this.tokenExpireDay = tokenExpireDay;
    }
}
