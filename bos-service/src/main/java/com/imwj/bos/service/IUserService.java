package com.imwj.bos.service;

import com.imwj.bos.domain.User;

public interface IUserService {

	User login(User model);

	void editPasswod(String id, String password);

	void save(User model, String[] roleIds);

}
