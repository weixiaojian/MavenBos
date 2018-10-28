package com.imwj.bos.realm;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;

import com.imwj.bos.dao.FunctionDao;
import com.imwj.bos.dao.IUserDao;
import com.imwj.bos.domain.Function;
import com.imwj.bos.domain.User;

public class BOSRealm extends AuthorizingRealm{
	
	@Autowired
	private IUserDao userDao;
	@Autowired
	private FunctionDao functionDao;
	
	//认证方法
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		UsernamePasswordToken mytoken = (UsernamePasswordToken) token;
		String username = mytoken.getUsername();
		//根据用户名查询数据库中的密码
		User user = userDao.findUsernameByUserName(username);
		if(user == null){
			//用户名不存在
			return null;
		}
		//如果能够查询到，再由框架比对数据中查询到的密码和页面提交的密码是否一致
		AuthenticationInfo info = new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
		return info;
	}
	
	//授权方法
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection arg0) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//获取当前登陆用户
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		//根据当前登录用户查询数据库，获取实际对应的权限
		List<Function> list = null;
		if("admin".equals(user.getUsername())){
			//超级管理员，赋予admin所有权限关键字
			DetachedCriteria criteria = DetachedCriteria.forClass(Function.class);
			list = functionDao.findBycriteria(criteria);
		}else{
			//不是超级管理员 根据user.id去查询对应的权限关键字，涉及到User、Role、function、还有两张关系表，共五张表
			list = functionDao.findFunctionByUserId(user.getId());
		}
		for (Function function : list) {
			//授权
			info.addStringPermission(function.getCode());
		}
		return info;
	}
}
