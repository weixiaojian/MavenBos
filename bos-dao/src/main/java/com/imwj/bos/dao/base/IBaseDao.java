package com.imwj.bos.dao.base;

import java.io.Serializable;
import java.util.List;

public interface IBaseDao<T> {
	
	 public void save(T entity);
	 public void delete(T entity);
	 public void update(T entity);
	 public T findById(Serializable id);
	 public List<T> findAll();
	 public void excuteUpdate(String queryName, Object... objects);
}
