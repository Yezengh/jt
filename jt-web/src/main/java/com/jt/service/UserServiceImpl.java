package com.jt.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.jt.pojo.User;
import com.jt.util.HttpClientService;
import com.jt.util.ObjectMapperUtil;
import com.jt.vo.SysResult;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private HttpClientService httpClient;

	/*
	 * @Override public void doRegister(User user) { //定义请求路径 String url =
	 * "http://sso.jt.com/user/register"; Map<String,String> params = new
	 * HashMap<>(); String password =
	 * DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
	 * user.setPassword(password); String userJSON = ObjectMapperUtil.toJson(user);
	 * params.put("userJSON", userJSON); String result =
	 * httpClient.doPost(url,params); //判断返回值是否正确 SysResult sysResult =
	 * ObjectMapperUtil.toObj(result, SysResult.class); if(sysResult.getStatus() ==
	 * 201) { //说明后台程序运行出错.业务停止 throw new RuntimeException(); } }
	 */

	}
	
