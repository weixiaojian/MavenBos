package com.imwj.bos.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.imwj.bos.dao.FunctionDao;
import com.imwj.bos.dao.base.impl.BaseDaoImpl;
import com.imwj.bos.domain.Function;
import com.imwj.bos.utils.PageQuery;

@Repository
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements FunctionDao {

	public List<Function> findAll(){
		String hql = "FROM Function f WHERE f.parentFunction IS NULL";
		List<Function> list = (List<Function>) this.getHibernateTemplate().find(hql);
		return list;
	}
}
