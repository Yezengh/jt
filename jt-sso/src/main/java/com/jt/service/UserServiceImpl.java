package com.jt.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper userMapper;

	@Override
	public boolean checkUser(String param, Integer type) {
		QueryWrapper<User> queryWrapper = new QueryWrapper<>();
		String column = type==1? "username" :(type==2?"phone":"email");
		queryWrapper.eq(column, param);
		Integer row = userMapper.selectCount(queryWrapper);
		boolean flag = row == 1?true : false;
		return flag;
	}

	/*
	 * @Override
	 * 
	 * @Transactional public void doRegister(User user) {
	 * user.setEmail(user.getPhone()) .setCreated(new Date())
	 * .setUpdated(user.getCreated()); userMapper.insert(user); }
	 * 
	 */
	
}
