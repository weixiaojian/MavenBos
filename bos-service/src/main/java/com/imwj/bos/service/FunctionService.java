package com.imwj.bos.service;

import java.util.List;

import com.imwj.bos.domain.Function;
import com.imwj.bos.utils.PageQuery;

public interface FunctionService {

	List<Function> findAll();

	void save(Function model);

	void pageQuery(PageQuery pageQuery);

	List<Function> findMenuAll();

}
