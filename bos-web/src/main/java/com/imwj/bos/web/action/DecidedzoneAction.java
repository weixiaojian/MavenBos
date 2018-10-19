package com.imwj.bos.web.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.imwj.bos.domain.Decidedzone;
import com.imwj.bos.service.DecidedzoneService;
import com.imwj.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone> {

	private String[] subareaid;
	
	@Autowired
	private DecidedzoneService decidedzoneService;
	
	/**
	 * 定区添加操作
	 * @return
	 */
	public String add(){
		decidedzoneService.save(model,subareaid);
		return LIST;
	}
	
	/**
	 * 定区分页列表显示
	 * @return
	 */
	public String pageQuery(){
		decidedzoneService.pageQuery(pageQuery);
		this.javaToJson(pageQuery, new String[]{"currentPage","detachedCriteria","pageSize","subareas","decidedzones"});
		return NONE;
	}

	public void setSubareaid(String[] subareaid) {
		this.subareaid = subareaid;
	}
}
