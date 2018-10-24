package com.imwj.bos.web.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.imwj.bos.crm.Customer;
import com.imwj.bos.crm.ICustomerService;
import com.imwj.bos.domain.Noticebill;
import com.imwj.bos.service.NoticebillService;
import com.imwj.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class NoticebillAction extends BaseAction<Noticebill> {

	@Autowired
	private ICustomerService proxy;
	@Autowired
	private NoticebillService noticebillService;
	
	/**
	 * 根据用户填写的电话号码，ajax回显数据
	 * @return
	 */
	public String findCustomerByTelephone(){
		Customer customer = proxy.findCustomerByTelephone(model.getTelephone());
		javaToJson(customer, new String[]{});
		return NONE;
	}
	
	/**
	 * 保存用户填写的业务单，并在service尝试自动分单
	 * @return
	 */
	public String add(){
		noticebillService.save(model);
		return "noticebill_add";
	}
	
}
