package com.imwj.bos.dao.base.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.imwj.bos.dao.base.IBaseDao;
import com.imwj.bos.utils.PageQuery;
/**
 *	持久层的通用实现
 * @author SpongBob
 *
 * @param <T>
 */
public class BaseDaoImpl<T> extends HibernateDaoSupport implements IBaseDao<T> {
	
	//代表的是某个实体的类型
	private Class<T> entityClass;
	
	@Resource//根据类型注入spring工厂中的会话工厂对象sessionFactory
	public void setMySessionFactory(SessionFactory sessionFactory){
		this.setSessionFactory(sessionFactory);
	}
	
	//在父类（BaseDaoImpl）的构造方法中动态获得entityClass
	public BaseDaoImpl(){
		ParameterizedType superclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		entityClass = (Class<T>) superclass.getActualTypeArguments()[0];
	}
	
	@Override
	public void save(T entity) {
		this.getHibernateTemplate().save(entity);
	}

	@Override
	public void delete(T entity) {
		this.getHibernateTemplate().delete(entity);
	}

	@Override
	public void update(T entity) {
		this.getHibernateTemplate().update(entity);
	}

	@Override
	public T findById(Serializable id) {
		return this.getHibernateTemplate().get(entityClass, id);
	}

	@Override
	public List<T> findAll() {
		String hql = "from "+entityClass.getSimpleName()+"";
		return (List<T>) this.getHibernateTemplate().find(hql);
	}

	@Override
	public void excuteUpdate(String queryName, Object... objects) {
		Session session = this.getSessionFactory().getCurrentSession();
		Query query = session.getNamedQuery(queryName);
		int i = 0;
		for (Object object : objects) {
			//为hql语句中的？赋值
			query.setParameter(i++, object);
		}
		//执行更新
		query.executeUpdate();
	}

	@Override
	public void findPageQuery(PageQuery pageQuery) {
		int currentPage = pageQuery.getCurrentPage();
		int pageSize = pageQuery.getPageSize();
		DetachedCriteria dc = pageQuery.getDetachedCriteria();
		
		//查询总记录数
		dc.setProjection(Projections.rowCount());
		List<Long> list = (List<Long>) this.getHibernateTemplate().findByCriteria(dc);
		int total = list.get(0).intValue();
		pageQuery.setTotal(total);
		
		//查询分页数据
		dc.setProjection(null);//清除之前的查询条件
		int firstResult = (currentPage-1)*pageSize;
		int maxResults = pageSize;
		List rows = this.getHibernateTemplate().findByCriteria(dc, firstResult, maxResults);
		pageQuery.setRows(rows);
	}

	@Override
	public void saveOrupdate(T entity) {
		this.getHibernateTemplate().saveOrUpdate(entity);
	}

}
