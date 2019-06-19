package com.jt.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.jt.util.UserThreadLocal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.druid.util.StringUtils;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.JedisCluster;
@Component //将拦截器交给spring容器管理
public class UserInterceptor implements HandlerInterceptor{
			/**
			 * 在spring4版本中要求必须重写3个方法,不管是否需要
			 * 在spring5版本中在接口添加default属性, 有默认值可以不写,需要时再加
			 */
		//目标方法执行之前
		//true:拦截放行
		//false:请求拦截 重定向到登陆页面
		/**
		 * 业务逻辑
		 * 1.获取cookie数据
		 * 2.从cookie中获取token
		 * 3.判断redis缓存服务器中是否有数据
		 */
		@Autowired(required = false)
		private JedisCluster jedisCluster;
		@Override
		public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
				throws Exception {
				String token = null;
				Cookie[] cookies = request.getCookies();
				for (Cookie cookie : cookies) {
					if("JT_TICKET".equals(cookie.getName())) {
						token = cookie.getValue();
						break;
					}
				}	
					if(!StringUtils.isEmpty(token)) {
						String userJSON = jedisCluster.get(token);
						if(!StringUtils.isEmpty(userJSON)) {
							User user = ObjectMapperUtil.toObj(userJSON, User.class);
							UserThreadLocal.set(user);
							request.setAttribute("user", user);
							return true;
						}
					}
					//重定向到用户登陆页面
					response.sendRedirect("http://www.jt.com/user/login");
					return false;//表示拦截
		}
		@Override
		public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
		ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
		}
		@Override
		public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
		throws Exception {
		// TODO Auto-generated method stub
		HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
		}
}
