package com.imwj.bos.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.imwj.bos.domain.Function;
import com.imwj.bos.domain.Role;
import com.imwj.bos.service.RoleService;
import com.imwj.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role>{

	@Autowired
	private RoleService roleService;
	
	/**
	 * 角色添加页面，ajax请求权限数据
	 */
	public String listAjax(){
		List<Function> list = roleService.findAll();
		javaToJson(list, new String[]{"parentFunction","roles"});
		return NONE;
	}
	
	/**
	 * 添加角色方法
	 */
	private String functionIds;
	public String add(){
		roleService.save(model,functionIds);
		return LIST;
	}
	
	/**
	 * 角色列表分页显示
	 * @param functionIds
	 */
	public String pageQuery(){
		roleService.pageQuery(pageQuery);
		javaToJson(pageQuery, new String[]{"functions","users"});
		return NONE;
	}

	/**
	 * 用户添加页面，角色ajax请求
	 * @param functionIds
	 */
	public String listAjax2(){
		List<Role> list = roleService.findAll2();
		this.javaToJson(list, new String[]{"functions","users"});
		return NONE;
	}
	
	public void setFunctionIds(String functionIds) {
		this.functionIds = functionIds;
	}
}
