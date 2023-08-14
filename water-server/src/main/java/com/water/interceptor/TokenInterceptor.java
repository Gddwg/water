package com.water.interceptor;

import com.water.constans.BaseConstants;
import com.water.constans.ExceptionConstants;
import com.water.context.WaterContext;
import com.water.entity.TokenBind;
import com.water.exception.LoginFailException;
import com.water.redis.TokenRedis;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.security.auth.login.LoginException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        /**
         * 登录校验
         */
        String token = request.getHeader(BaseConstants.TOKEN);
        if (token == null){
            throw new LoginFailException(BaseConstants.NOT_LOGIN);
        }
        TokenBind tokenBind = refreshToken(token);
        if (tokenBind == null){
            throw new LoginFailException(ExceptionConstants.TOKEN_ERROR);
        }
        WaterContext.setTokenBind(tokenBind);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        WaterContext.remove();
    }

    private TokenBind refreshToken(String token){
        TokenBind tokenBind = TokenRedis.getTokenBind(token);
        if(tokenBind != null) {
            TokenRedis.refreshToken(token);
        }
        return tokenBind;
    }

}
