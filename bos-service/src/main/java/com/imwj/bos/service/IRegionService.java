package com.imwj.bos.service;

import java.util.List;

import com.imwj.bos.domain.Region;
import com.imwj.bos.domain.Subarea;
import com.imwj.bos.utils.PageQuery;

public interface IRegionService {

	void saveOrupdate(List<Region> reginList);

	void findPageQuery(PageQuery pageQuery);

	List<Region> findAllByQ(String q);

	List<Region> finAll();

	void add(Region model);

}
