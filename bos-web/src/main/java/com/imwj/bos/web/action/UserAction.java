package com.imwj.bos.web.action;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.imwj.bos.domain.User;
import com.imwj.bos.service.IUserService;
import com.imwj.bos.utils.BOSUtils;
import com.imwj.bos.utils.MD5Utils;
import com.imwj.bos.web.action.base.BaseAction;

@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User>  {

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
	/**
	 * @return
	 * @throws Exception
	 */
	public String login() throws Exception {
		//得到session域中的验证码
		String validatecode = (String) ServletActionContext.getRequest().getSession().getAttribute("key");
		//先判断验证码是否输入正确
		if(StringUtils.isNotBlank(checkcode) && validatecode.equals(checkcode)){
			//输入的验证码正确
			//使用shiro框架提供的方式进行认证
			Subject subject = SecurityUtils.getSubject();//获得当前登陆的用户对象，现在的状态未“未认证”
			//用户名密码令牌
			AuthenticationToken token = new UsernamePasswordToken(model.getUsername(), MD5Utils.md5(model.getPassword()));
			try {
				subject.login(token);//此处会跳转到我们所创建的realm
			} catch (UnknownAccountException e) {
				this.addActionError("用户名不存在");
				return LOGIN;
			} catch (IncorrectCredentialsException e) {
				this.addActionError("密码输入错误");
				return LOGIN;
			}
			User user = (User) subject.getPrincipal();
			ServletActionContext.getRequest().getSession().setAttribute("loginUser", user);
			return HOME;
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
	
	/**
	 * 添加用户
	 */
	private String[] roleIds;
	public String add(){
		userService.save(model,roleIds);
		return LIST;
	}
	
	/**
	 * 用户列表分页显示
	 * @param roleIds
	 */
	public String pageQuery(){
		userService.pageQuery(pageQuery);
		this.javaToJson(pageQuery, new String[]{"noticebills","roles"});
		return NONE;
	}

	public void setRoleIds(String[] roleIds) {
		this.roleIds = roleIds;
	}
}
