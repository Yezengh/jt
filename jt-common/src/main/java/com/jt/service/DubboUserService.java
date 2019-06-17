package com.jt.service;

import com.jt.pojo.User;

public interface DubboUserService {

	void doRegister(User user);

	String findUserByUp(User user);

}
