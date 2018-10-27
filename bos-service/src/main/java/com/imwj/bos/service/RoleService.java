package com.imwj.bos.service;

import java.util.List;

import com.imwj.bos.domain.Function;
import com.imwj.bos.domain.Role;
import com.imwj.bos.utils.PageQuery;

public interface RoleService {

	List<Function> findAll();

	void save(Role model, String functionIds);

	void pageQuery(PageQuery pageQuery);

	List<Role> findAll2();
	
}
