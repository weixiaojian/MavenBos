package com.imwj.bos.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.imwj.bos.crm.Customer;
import com.imwj.bos.crm.ICustomerService;
import com.imwj.bos.domain.Decidedzone;
import com.imwj.bos.service.DecidedzoneService;
import com.imwj.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class DecidedzoneAction extends BaseAction<Decidedzone> {

	private String[] subareaid;
	
	@Autowired
	private DecidedzoneService decidedzoneService;
	
	@Autowired
	private ICustomerService proxy;
	
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
	
	/**
	 * 定区关联客户按钮的事件
	 * @param subareaid
	 */
	public String findListNotAssociation(){
		List<Customer> list = proxy.findListNotAssociation();
		this.javaToJson(list, new String[]{});
		return NONE;
	}
	public String findListHasAssociation(){
		List<Customer> list = proxy.findListHasAssociation(model.getId());
		this.javaToJson(list, new String[]{});
		return NONE;
	}
	private List<Integer> customerIds;
	public String assigncustomerstodecidedzone(){
		proxy.assigncustomerstodecidedzone(model.getId(), customerIds);
		return LIST;
	}
	
	/**
	 * 定区中双击表格查看所点击的定区关联的客户
	 * @param customerIds
	 */
	public void setCustomerIds(List<Integer> customerIds) {
		this.customerIds = customerIds;
	}
	public void setSubareaid(String[] subareaid) {
		this.subareaid = subareaid;
	}
}
