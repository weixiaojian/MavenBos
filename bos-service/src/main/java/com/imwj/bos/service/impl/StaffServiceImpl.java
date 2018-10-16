package com.imwj.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imwj.bos.dao.StaffDao;
import com.imwj.bos.domain.Staff;
import com.imwj.bos.service.StaffService;
import com.imwj.bos.utils.PageQuery;

@Service
@Transactional
public class StaffServiceImpl implements StaffService {

	@Autowired
	private StaffDao staffDao;
	
	public void addStaff(Staff model) {
		staffDao.save(model);
	}

	
	public void findPageQuery(PageQuery pageQuery) {
		staffDao.findPageQuery(pageQuery);
	}

}
