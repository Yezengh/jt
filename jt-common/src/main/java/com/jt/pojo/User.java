package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain=true)
@TableName("tb_user")
public class User extends BasePojo{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5470098609744488472L;
	@TableId(type = IdType.AUTO)
	private Long id;//主键自增
	private String username;
	private String password;
	private String phone;
	private String email;
}
