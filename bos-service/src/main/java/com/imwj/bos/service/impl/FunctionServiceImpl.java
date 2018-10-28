package com.imwj.bos.service.impl;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imwj.bos.dao.FunctionDao;
import com.imwj.bos.domain.Function;
import com.imwj.bos.domain.User;
import com.imwj.bos.utils.PageQuery;

@Service
@Transactional
public class FunctionServiceImpl implements com.imwj.bos.service.FunctionService {
	
	@Autowired
	private FunctionDao functionDao;

	public List<Function> findAll() {
		return functionDao.findAll();
	}

	public void save(Function model) {
		Function parentFunction = model.getParentFunction();
		if(parentFunction != null && parentFunction.getId().equals("")){
			model.setParentFunction(null);
		}
		functionDao.save(model);
	}

	public void pageQuery(PageQuery pageQuery) {
		functionDao.findPageQuery(pageQuery);
	}

	public List<Function> findMenuAll() {
		//得到当前登陆用户
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		List<Function> list = null;
		if("admin".equals(user.getUsername())){
			//如果登陆用户是admin，获取所有菜单
			list = functionDao.findAllMenu();
		}else{
			//不是admin，根据userid获取指定菜单
			list = functionDao.findAllMenuByUserId(user.getId());
		}
		return list;
	}

}
