package com.imwj.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imwj.bos.dao.SubareaDao;
import com.imwj.bos.domain.Subarea;
import com.imwj.bos.service.SubareaService;
import com.imwj.bos.utils.PageQuery;

@Service
@Transactional
public class SubareaServiceImpl implements SubareaService{
	
	@Autowired
	private SubareaDao subareaDao;

	public void add(Subarea model) {
		subareaDao.save(model);
	}

	@Override
	public void pageQuery(PageQuery pageQuery) {
		subareaDao.findPageQuery(pageQuery);
	}
	
	
}
