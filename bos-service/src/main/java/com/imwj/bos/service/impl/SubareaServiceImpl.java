package com.imwj.bos.service.impl;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
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

	@Override
	public List<Subarea> findAll() {
		return subareaDao.findAll();
	}

	@Override
	public List<Subarea> findAllNotDecidedzone() {
		//未关联到定区的分区：即decidedzone_id为null
		DetachedCriteria dc = DetachedCriteria.forClass(Subarea.class);
		dc.add(Restrictions.isNull("decidedzone"));
		return subareaDao.findBycriteria(dc);
	}
	
	
}
