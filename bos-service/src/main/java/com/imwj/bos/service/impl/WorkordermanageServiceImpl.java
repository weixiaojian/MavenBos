package com.imwj.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imwj.bos.dao.WorkordermanageDao;
import com.imwj.bos.domain.Workordermanage;

@Service
@Transactional
public class WorkordermanageServiceImpl implements com.imwj.bos.service.WorkordermanageService {

	@Autowired
	private WorkordermanageDao workordermanageDao;
	
	public void save(Workordermanage model) {
		workordermanageDao.save(model);
	}

}
