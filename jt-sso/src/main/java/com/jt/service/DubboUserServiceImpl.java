package com.jt.service;

import java.beans.DesignMode;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import com.jt.util.ObjectMapperUtil;

import redis.clients.jedis.JedisCluster;
//该类是dubbo的实现类
@Service(timeout=3000)
public class DubboUserServiceImpl implements DubboUserService {
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private JedisCluster jedisCluster;
	@Override
	public void doRegister(User user) {
		//1.将密码加密,
		String md5Pwd = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pwd).setEmail(user.getPhone())
													  .setCreated(new Date())
													  .setUpdated(user.getCreated());
		//2.补齐入库数据 email暂时使用电话代替
		userMapper.insert(user);
	}
		/**
		 * 1.校验用户名或密码是否正确
		 * 2.如果数据不正确 返回null
		 * 3.如果数据正确 
		 * 		3.1 生成加密秘钥 : (JT_TICKET_+ username+当前毫秒数)再将此数据进行md5加密
		 * 		3.2 将userDB数据转化为userJSON
		 * 		3.3.将数据保存到redis中 7天超时
		 * 		3.4.返回token
		 */
	@Override
	public String findUserByUp(User user) {
		String token = null;
		//1.将密码进行加密
		String md5Pwd = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
		user.setPassword(md5Pwd);
		QueryWrapper<User> queryWrapper = new QueryWrapper<User>(user);
		User userDB = userMapper.selectOne(queryWrapper);
		System.out.println(userDB);
		//2.判断数据正确
		if(userDB != null) {
			token = "JT_TICKET_"+userDB.getUsername()+System.currentTimeMillis();
			token = DigestUtils.md5DigestAsHex(token.getBytes());
			//脱敏处理
			userDB.setPassword("我是你爹");
			String userJSON = ObjectMapperUtil.toJson(userDB);
			//写入缓存
			jedisCluster.setex(token, 7*24*3600, userJSON);
			
		}
		return token;
	}
}
