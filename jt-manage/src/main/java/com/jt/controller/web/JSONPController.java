package com.jt.controller.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;

@RestController
public class JSONPController {
	 @RequestMapping("web/testJSONP") public String testJSONP(String callback) {
	 User user = new User(); user.setUsername("我是你baba"); String
	  json = ObjectMapperUtil.toJson(user); 
	 System.out.println(callback);
	 return callback +"("+json+")"; }
	 
	/*
	 * @RequestMapping("web/testJSONP") public JSONPObject jsonp(String callback) {
	 * User user = new User(); user.setUsername("我是你爹"); JSONPObject obj = new
	 * JSONPObject(callback, user); return obj; }
	 */
}
