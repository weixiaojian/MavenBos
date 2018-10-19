package com.imwj.bos.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imwj.bos.dao.DecidedzoneDao;
import com.imwj.bos.dao.SubareaDao;
import com.imwj.bos.domain.Decidedzone;
import com.imwj.bos.domain.Subarea;
import com.imwj.bos.service.DecidedzoneService;

@Service
@Transactional
public class DecidedzoneServiceImpl implements DecidedzoneService{

	@Autowired
	private DecidedzoneDao decidedzoneDao;
	@Autowired
	private SubareaDao subareaDao;
	
	/**
	 * 添加定区，同时关联分区
	 */
	public void save(Decidedzone model, String[] subareaid) {
		decidedzoneDao.save(model);
		for(String id : subareaid){
			Subarea subarea = subareaDao.findById(id);
			//model.getSubareas().add(subarea);只需要一方关联：（一方）定区已经放弃维护外键权利，（多方）负责维护
			subarea.setDecidedzone(model);//分区关联定区
		}
	}

}
