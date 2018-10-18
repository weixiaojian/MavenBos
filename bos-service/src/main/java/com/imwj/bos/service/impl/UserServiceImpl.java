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


	//用户登陆
	public User login(User model) {
		//MD5加密密码
		String password = MD5Utils.md5(model.getPassword());
		return userDao.findUserByUsernameAndPassword(model.getUsername(),password);
	}
	
	//用户修改密码
	public void editPasswod(String id, String password) {
		//使用md5加密密码
		String newPassword = MD5Utils.md5(password);
		userDao.excuteUpdate("user.editpassword",newPassword,id);
	}
	
}
