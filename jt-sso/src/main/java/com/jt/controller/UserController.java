package com.jt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.druid.util.StringUtils;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.User;
import com.jt.service.UserService;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

@RestController
@RequestMapping("/user")
public class UserController {
	/**业务说明
	 * 校验用户是否存在
	 * 返回值SysResult
	 * 由于是跨域的请求,所以返回值必须特殊处理callback(json)
	 * */
	@Autowired
	private UserService userService;
	@Autowired
	private JedisCluster jedisCluster;

	@RequestMapping("/check/{param}/{type}")
	public JSONPObject checkUser(@PathVariable String param,
			@PathVariable Integer type,
			String callback) {
		JSONPObject object = null;
		try {
			boolean flag = userService.checkUser(param,type);
			object = new JSONPObject(callback, SysResult.OK(flag));
		} catch (Exception e) {
			e.printStackTrace();
			object = new JSONPObject(callback, SysResult.fail());
		}
		return  object;
	}
	/*
	 * @RequestMapping("/register") public SysResult doRegister(String userJSON) {
	 * try { User user = ObjectMapperUtil.toObj(userJSON, User.class);
	 * userService.doRegister(user); return SysResult.OK(); }catch (Exception e) {
	 * e.printStackTrace(); return SysResult.fail(); } }
	 */
	@RequestMapping("/query/{token}")
	public JSONPObject queryUsername(@PathVariable String token,String callback) {
		String user = jedisCluster.get(token);
		if(!StringUtils.isEmpty(user)) {
			return new JSONPObject(callback, SysResult.OK(user));
		}else {
			return new JSONPObject(callback,SysResult.fail());
		}
	}
	
}
