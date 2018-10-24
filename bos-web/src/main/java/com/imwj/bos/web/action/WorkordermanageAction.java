package com.imwj.bos.web.action;

import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.imwj.bos.domain.Workordermanage;
import com.imwj.bos.service.WorkordermanageService;
import com.imwj.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class WorkordermanageAction extends BaseAction<Workordermanage>{
	
	@Autowired
	private WorkordermanageService workordermanageService;
	
	/**
	 * 工作单快速录入添加方法
	 * @return
	 * @throws Exception
	 */
	public String add()throws Exception{
		String f = "1";
		try{
			workordermanageService.save(model);
		}catch(Exception e){
			e.printStackTrace();
			f = "0";
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(f);
		return NONE;
	}
	
	
}
