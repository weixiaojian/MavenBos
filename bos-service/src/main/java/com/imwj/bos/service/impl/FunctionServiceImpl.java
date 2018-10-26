package com.imwj.bos.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imwj.bos.dao.FunctionDao;
import com.imwj.bos.domain.Function;
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

}
