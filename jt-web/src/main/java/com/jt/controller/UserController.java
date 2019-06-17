package com.jt.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.service.UserService;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/user")
public class UserController {
	/*
	 * @Autowired private UserService userService;
	 */
	@Reference(timeout = 3000, check = false)
	private DubboUserService userService;
	
	@Autowired
	private JedisCluster jedisCluster;
	@RequestMapping("/{moduleName}")
	public String index(@PathVariable String moduleName) {
		return moduleName;
	}

	//注册用户

	@RequestMapping("/doRegister")
	@ResponseBody 
		public SysResult doRegister(User user) { 
		try {
			userService.doRegister(user); 
			return SysResult.OK(); 
		} catch (Exception e) {
			e.printStackTrace(); 
			return SysResult.fail(); } 
		}
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult login(User user,HttpServletResponse response) {
		try {
			String token = userService.findUserByUp(user);
			System.out.println(token);
			//cookie中的key固定值JT_TICKET
			if(!StringUtils.isEmpty(token)) {
				Cookie cookie = new Cookie("JT_TICKET", token);
				cookie.setDomain("jt.com");//二级域名 实现cookie共享
				cookie.setMaxAge(7*24*3600);//生命周期 (0) 立即删除 (>0) 存活多少秒 (<0) 会话结束时删除
				cookie.setPath("/");//cookie的权限
				//利用response对象将cookie数据写入客户端
				response.addCookie(cookie);
				return SysResult.OK();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SysResult.fail();
	}
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response) { 
	Cookie[] cookies = request.getCookies();
	String token = null;
	if(cookies.length !=0) {
		for (Cookie cookie : cookies) {
			String cookieName = "JT_TICKET";
			if(cookieName.equals(cookie.getName())) {
				//证明Cookie成功获取
				token = cookie.getValue();
				break;
			}
		}
	}
	
	//2.根据token删除redis数据
	jedisCluster.del(token);
	//3.删除Cookie  null个别浏览器可能会有问题
	Cookie cookie = new Cookie("JT_TICKET","");
	cookie.setMaxAge(0);
	cookie.setPath("/");
	cookie.setDomain("jt.com");
	response.addCookie(cookie);
	
	//4.重定向到系统首页   
	//重定向:本次业务结束,开始新的业务
	//转发:  在一个业务中完成
	return "redirect:/"; //重定向到根目录

		 
	
}


}
