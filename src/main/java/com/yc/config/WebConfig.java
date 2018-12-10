package com.yc.config;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                //排除拦截
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/logout")

                //拦截路径
                .addPathPatterns("/**");
    }


    public class LoginInterceptor implements HandlerInterceptor{
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
//            HttpSession session = request.getSession();
//            if (session.getAttribute(session.getId()) != null){
//                return true;
//            }
//            response.getWriter().write(JSON.toJSONString(
//                    new BaseResponse(){{
//                        setOk(false);
//                        setMessage("please login first");
//                    }}
//            ));
//            return false;
            return true;
        }
    }
}
