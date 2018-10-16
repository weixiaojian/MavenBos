package com.imwj.bos.web.action;

import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.imwj.bos.domain.Staff;
import com.imwj.bos.service.StaffService;
import com.imwj.bos.utils.PageQuery;
import com.imwj.bos.web.action.base.BaseAction;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff>  {

	@Autowired
	private StaffService staffService;
	
	/**
	 * 添加取件员派件
	 */
	public String add() throws Exception {
		staffService.addStaff(model);
		return LIST;
	}
	
	/**
	 * 取派员列表分页显示
	 */
	private int page;
	private int rows;
	public String pageQuery() throws Exception {
		PageQuery pageQuery = new PageQuery();
		pageQuery.setCurrentPage(page);
		pageQuery.setPageSize(rows);
		
		//创建离线提交查询对象
		DetachedCriteria dc = DetachedCriteria.forClass(Staff.class);
		pageQuery.setDetachedCriteria(dc);
		staffService.findPageQuery(pageQuery);
		
		//将pageQuery转换为json数据:
		//JSONObject---将单一对象转为json  JSONArray----将数组或者集合对象转为json
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setExcludes(new String[]{"currentPage","detachedCriteria","pageSize"});
		String json = JSONObject.fromObject(pageQuery).toString();
		ServletActionContext.getResponse().setContentType("text/json;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(json);
		return NONE;
	}
	
	
	
	
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
}
