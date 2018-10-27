package com.imwj.bos.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imwj.bos.dao.FunctionDao;
import com.imwj.bos.dao.RoleDao;
import com.imwj.bos.domain.Function;
import com.imwj.bos.domain.Role;
import com.imwj.bos.service.RoleService;
import com.imwj.bos.utils.PageQuery;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private FunctionDao functionDao;
	
	@Autowired
	private RoleDao roleDao;

	public List<Function> findAll() {
		return functionDao.findAll();
	}

	/**
	 * 保存一个角色，同时需要关联权限
	 */
	public void save(Role role, String functionIds) {
		roleDao.save(role);
		if(StringUtils.isNotBlank(functionIds)){
			String[] fids = functionIds.split(",");
			for (String fid : fids) {
				//手动构造一个权限对象，设置id，对象状态为托管状态
				Function function = new Function(fid);
				//角色关联权限
				role.getFunctions().add(function);
			}
		}
	}

	public void pageQuery(PageQuery pageQuery) {
		functionDao.findPageQuery(pageQuery);
	}

	public List<Role> findAll2() {
		return roleDao.findAll();
	}
	
	
}
