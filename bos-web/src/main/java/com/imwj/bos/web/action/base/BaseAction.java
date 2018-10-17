package com.imwj.bos.web.action.base;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import com.imwj.bos.utils.PageQuery;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {
	
	/**
	 * 分页重构代码
	 */
	protected PageQuery pageQuery = new PageQuery();
	//创建离线提交查询对象
	DetachedCriteria detachedCriteria = null;
	public void setPage(int page) {
		pageQuery.setCurrentPage(page);
	}
	public void setRows(int rows) {
		pageQuery.setPageSize(rows);
	}
	
	public void javaToJson(Object o,String[] str){
		//将pageQuery转换为json数据:
		//JSONObject---将单一对象转为json  JSONArray----将数组或者集合对象转为json
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(str);
		String json = JSONObject.fromObject(o,jsonConfig).toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		try {
			ServletActionContext.getResponse().getWriter().print(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static final java.lang.String HOME = "home";
	public static final java.lang.String LIST = "list";
	
	//模型对象
	protected T model;
	
	@Override
	public T getModel() {
		return model;
	}
	
	//在构造方法中动态获取实体类型，通过反射创建model对象
	public BaseAction(){
		ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		//获得BaseAction上声明的泛型数组
		Type[] type = genericSuperclass.getActualTypeArguments();
		Class<T> entityClass = (Class<T>) type[0];
		detachedCriteria = DetachedCriteria.forClass(entityClass);
		pageQuery.setDetachedCriteria(detachedCriteria);
		
		//通过反射创建对象
		try {
			model = entityClass.newInstance();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}
}
