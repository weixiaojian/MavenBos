package com.imwj.bos.web.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.imwj.bos.crm.Customer;
import com.imwj.bos.crm.ICustomerService;
import com.imwj.bos.domain.Noticebill;
import com.imwj.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class NoticebillAction extends BaseAction<Noticebill> {

	@Autowired
	private ICustomerService proxy;
	
	public String findCustomerByTelephone(){
		Customer customer = proxy.findCustomerByTelephone(model.getTelephone());
		javaToJson(customer, new String[]{});
		return NONE;
	}
	
}
