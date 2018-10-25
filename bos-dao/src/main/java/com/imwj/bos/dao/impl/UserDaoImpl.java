package com.imwj.bos.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.imwj.bos.dao.IUserDao;
import com.imwj.bos.dao.base.impl.BaseDaoImpl;
import com.imwj.bos.domain.User;

@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao {

	/**
	 * 根据用户名和密码查询user
	 */
	public User findUserByUsernameAndPassword(String username, String password) {
		String hql = "FROM User u WHERE  u.username=? AND u.password=?";
		List<User> user = (List<User>) this.getHibernateTemplate().find(hql, username,password);
		if(user!=null && user.size()>0){
			return user.get(0);
		}
		return null;
	}

	/**
	 * shiro中根据用户名查询user
	 */
	public User findUsernameByUserName(String username) {
		String hql = "FROM User u WHERE  u.username=?";
		List<User> user = (List<User>) this.getHibernateTemplate().find(hql, username);
		if(user!=null && user.size()>0){
			return user.get(0);
		}
		return null;
	}

}
