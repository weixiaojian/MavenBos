package com.imwj.bos.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.imwj.bos.domain.Function;
import com.imwj.bos.service.FunctionService;
import com.imwj.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class FunctionAction extends BaseAction<Function> {
	
	@Autowired
	private FunctionService functionService;
	
	/**
	 * 添加权限页面，选择父功能下来列表ajax请求
	 * @return
	 */
	public String ajaxList(){
		List<Function> list = functionService.findAll();
		javaToJson(list, new String[]{"parentFunction","roles","children"});
		return NONE;
	}
	
	/**
	 * 添加权限
	 * @return
	 */
	public String add(){
		functionService.save(model);
		return LIST;
	}
	
	/**
	 * 权限分页列表显示
	 * @return
	 */
	public String pageQuery(){
		pageQuery.setCurrentPage(Integer.parseInt(model.getPage()));
		functionService.pageQuery(pageQuery);
		javaToJson(pageQuery, new String[]{"parentFunction","roles","children"});
		return NONE;
	}
	
	/**
	 * 菜单列表显示
	 */
	public String findMenu(){
		List<Function> list = functionService.findMenuAll();
		this.javaToJson(list, new String[]{"roles","children"});
		return NONE;
	}
}
