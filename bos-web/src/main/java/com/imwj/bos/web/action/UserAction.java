package com.imwj.bos.web.action;

import com.imwj.bos.crm.Customer;
import com.imwj.bos.crm.ICustomerService;
import com.imwj.bos.domain.User;
import com.imwj.bos.service.IUserService;
import com.imwj.bos.utils.BOSUtils;
import com.imwj.bos.web.action.base.BaseAction;
import com.opensymphony.xwork2.ActionContext;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;


import org.springframework.stereotype.Controller;
import org.apache.poi.util.SystemOutLogger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;

@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User>  {

	@Autowired
	private ICustomerService porxy;
	
	//属性驱动，接收页面输入的验证码
	private String checkcode;
	public void setCheckcode(String checkcode) {
		this.checkcode = checkcode;
	}

	@Autowired
	private IUserService userService;
	
	
	/**
	 * 用户登陆
	 * @return
	 * @throws Exception
	 */
	public String login() throws Exception {
//		WebService连接测试
//		List<Customer> list = porxy.findAll();
//		System.out.println(list);
		
		//得到session域中的验证码
		String validatecode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		//先判断验证码是否输入正确
		if(StringUtils.isNotBlank(checkcode) && validatecode.equals(checkcode)){
			//输入的验证码正确
			User user = userService.login(model);
			//判断用户名和密码是否正确
			if(user!=null){
				//登陆成功,将user放入session域跳转到首页
				ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
				return HOME;
			}else{
				//登陆失败
				this.addActionError("用户名或密码错误");
				return LOGIN;
			}
		}else{
			//验证码输入不正确
			this.addActionError("验证码输入错误");
			return LOGIN;
		}
	}
	
	/**
	 * 用户注销
	 * @return
	 * @throws Exception
	 */
	public String logout(){
		ServletActionContext.getRequest().getSession().invalidate();
		return LOGIN;
	}
	
	/**
	 * 用户修改密码
	 * @return
	 * @throws Exception 
	 */
	public String editPassword() throws Exception{
		String f = "1";
		//获取当前用户
		User loginUser = BOSUtils.getLoginUser();
		try{
			userService.editPasswod(loginUser.getId(),model.getPassword());
		}catch(Exception e){
			f = "0";
			e.printStackTrace();
		}
		ServletActionContext.getResponse().setContentType("text/html;charset=utf-8");
		ServletActionContext.getResponse().getWriter().print(f);
		return NONE;
	}
	
}
