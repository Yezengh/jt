package com.jt.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

//系统返回值对象
@Data
@Accessors(chain=true)
@NoArgsConstructor
@AllArgsConstructor
public class SysResult {

	private Integer status;//200表示成功,
	private String msg; //后台返回值数据提示信息
	private Object data;//后台返回任意数据
	
	public static SysResult OK() {
		return new SysResult(200,null,null);
	} 
	
	public static SysResult OK(Object data) {
		return new SysResult(200,null,data);
	}
	public static SysResult OK(String msg, Object data) {
		return new SysResult(200,msg,data);
	}
	public static SysResult fail(String msg) {
		return new SysResult(201,msg,null);
	}
	public static SysResult fail() {
		return new SysResult(201,null,null);
	}

}
