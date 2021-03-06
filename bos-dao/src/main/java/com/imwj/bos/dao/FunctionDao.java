package com.imwj.bos.dao;

import java.util.List;

import com.imwj.bos.dao.base.IBaseDao;
import com.imwj.bos.domain.Function;

public interface FunctionDao extends IBaseDao<Function>{
	
	public List<Function> findAll();

	public List<Function> findFunctionByUserId(String id);

	public List<Function> findAllMenu();

	public List<Function> findAllMenuByUserId(String userId);
}
