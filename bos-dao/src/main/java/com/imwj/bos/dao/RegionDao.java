package com.imwj.bos.dao;

import java.util.List;

import com.imwj.bos.dao.base.IBaseDao;
import com.imwj.bos.domain.Region;

public interface RegionDao extends IBaseDao<Region>{
	
	public List<Region> findListByQ(String q);
}
