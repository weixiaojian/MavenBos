package com.imwj.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imwj.bos.dao.IUserDao;
import com.imwj.bos.domain.User;
import com.imwj.bos.service.IUserService;
import com.imwj.bos.utils.MD5Utils;

@Service
@Transactional
public class UserServiceImpl implements IUserService{
	
	@Autowired
	private IUserDao userDao;
	public void setUserDao(IUserDao userDao) {
		this.userDao = userDao;
	}


	//用户登陆
	public User login(User model) {
		//MD5加密密码
		String password = MD5Utils.md5(model.getPassword());
		return userDao.findUserByUsernameAndPassword(model.getUsername(),password);
	}
	
}
