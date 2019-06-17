package com.jt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
	
	
	/**RestFul结构/风格实现通用页面
	 * 1.要求参数必须使用/分割
	 * 参数位置必须固定
	 * 使用 特定的注解 
	 * 并且名称最好一致
	 * */
	@RequestMapping("/page/{moduleName}")
	public String module(@PathVariable String moduleName) {
		
		return moduleName;
	}
}
