package com.imwj.bos.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imwj.bos.dao.RegionDao;
import com.imwj.bos.domain.Region;
import com.imwj.bos.domain.Subarea;
import com.imwj.bos.service.IRegionService;
import com.imwj.bos.utils.PageQuery;

@Service
@Transactional
public class RegionServiceImpl implements IRegionService{
	
	private String q;
	
	@Autowired
	private RegionDao regionDao;
	public void saveOrupdate(List<Region> reginList) {
		for (Region region : reginList) {
			regionDao.saveOrupdate(region);
		}
	}
	
	@Override
	public void findPageQuery(PageQuery pageQuery) {
		regionDao.findPageQuery(pageQuery);
	}

	@Override
	public List<Region> findAllByQ(String q) {
		return regionDao.findListByQ(q);
	}

	@Override
	public List<Region> finAll() {
		return regionDao.findAll();
	}
}
