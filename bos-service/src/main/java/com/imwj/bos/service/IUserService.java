package com.imwj.bos.service;

import com.imwj.bos.domain.User;
import com.imwj.bos.utils.PageQuery;

public interface IUserService {

	User login(User model);

	void editPasswod(String id, String password);

	void save(User model, String[] roleIds);

	void pageQuery(PageQuery pageQuery);

}
