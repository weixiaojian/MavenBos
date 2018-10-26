package com.imwj.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imwj.bos.dao.FunctionDao;
import com.imwj.bos.domain.Function;
import com.imwj.bos.service.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private FunctionDao functionDao;

	@Override
	public List<Function> findAll() {
		return functionDao.findAll();
	}
	
	
}
