package com.imwj.bos.dao;

import com.imwj.bos.dao.base.IBaseDao;
import com.imwj.bos.domain.User;

public interface IUserDao extends IBaseDao<User> {

	User findUserByUsernameAndPassword(String username, String password);

}
