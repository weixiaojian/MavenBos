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
	
	
	public String listAjax(){
		List<Function> list = roleService.findAll();
		javaToJson(list, new String[]{"parentFunction","roles"});
		return NONE;
	}
	
}
