package com.imwj.bos.web.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.imwj.bos.domain.Subarea;
import com.imwj.bos.service.SubareaService;
import com.imwj.bos.web.action.base.BaseAction;

/**
 * 管理分区action
 * @author SpongBob
 *
 */
@Controller
@Scope("prototype")
public class SubareaAction extends BaseAction<Subarea> {
	
	@Autowired
	private SubareaService subareaServicce;
	
	/**
	 * 添加分区
	 * @throws Exception
	 */
	public String add()throws Exception{
		subareaServicce.add(model);
		return LIST;
	}
}


