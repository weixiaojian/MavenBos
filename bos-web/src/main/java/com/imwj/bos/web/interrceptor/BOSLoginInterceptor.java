package com.imwj.bos.web.interrceptor;

import com.imwj.bos.domain.User;
import com.imwj.bos.utils.BOSUtils;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

/**
 * 自定义拦截器类，实现用户未登录自动跳转到登录页面
 * @author SpongBob
 *
 */
public class BOSLoginInterceptor extends MethodFilterInterceptor {
	//拦截方法
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		//从session中获得用户对象
		User loginUser = BOSUtils.getLoginUser();
		if(loginUser ==null){
			//没有登陆，跳转到登陆界面
			return "login";
		}
		//放行
		return invocation.invoke();
	}
}
