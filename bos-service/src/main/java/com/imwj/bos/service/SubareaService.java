package com.imwj.bos.service;

import java.util.List;

import com.imwj.bos.domain.Subarea;
import com.imwj.bos.utils.PageQuery;

public interface SubareaService {

	void add(Subarea model);

	void pageQuery(PageQuery pageQuery);


}
