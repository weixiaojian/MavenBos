package com.imwj.bos.web.action;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.imwj.bos.domain.Staff;
import com.imwj.bos.service.StaffService;
import com.imwj.bos.web.action.base.BaseAction;

import net.sf.json.JSONObject;

@Controller
@Scope("prototype")
public class StaffAction extends BaseAction<Staff>  {

	@Autowired
	private StaffService staffService;
	
	/**
	 * 添加取件员派件
	 */
	public String add() throws Exception {
		staffService.addStaff(model);
		return LIST;
	}
	
	/**
	 * 取派员列表分页显示(带查询功能)
	 */
	public String pageQuery() throws Exception {
		DetachedCriteria dc = pageQuery.getDetachedCriteria();
		//动态添加过滤条件
		String name = model.getName();
		String telephone = model.getTelephone();
		if(StringUtils.isNotBlank(name)){
			//添加过滤条件，根据地址name模糊查询
			dc.add(Restrictions.like("name", "%"+name+"%"));
		}
		if(StringUtils.isNotBlank(telephone)){
			//添加过滤条件，根据telephone模糊查询
			dc.add(Restrictions.like("telephone", "%"+telephone+"%"));
		}
		staffService.findPageQuery(pageQuery);
		this.javaToJson(pageQuery, new String[]{"currentPage","detachedCriteria","pageSize","decidedzones"});
		return NONE;
	}
	
	/**
	 * 取派员批量删除
	 * @return
	 */
	private String ids;
	@RequiresPermissions("staff")//执行这个方法需要当前的登陆用户拥有staff的权限
	public String deleteBatch(){
		staffService.deleteBatchByIdes(ids);
		return LIST;
	}
	
	/**
	 * 将取派员还原
	 * @return
	 */
	public String reduction(){
		staffService.reductionByIdes(ids);
		return LIST;
	}
	
	/**
	 * 取派员修改
	 * @return
	 */
	@RequiresPermissions("staff")
	public String edit(){
		//先根据id查询
		Staff staff = staffService.findStaffById(model.getId());
		//替换原先staff中的内容
		staff.setName(model.getName());
		staff.setTelephone(model.getTelephone());
		staff.setHaspda(model.getHaspda());
		staff.setStandard(model.getStandard());
		staff.setStation(model.getStation());
		//保存staff
		staffService.update(staff);
		
		return LIST;
	}
	
	/**
	 * 添加定区的取派员选择
	 * @return
	 * @throws Exception 
	 */
	public String listAjax() throws Exception{
		//得到所有数据
		List<Staff> list = staffService.finAllNoDelet();
		
		//将数据转换成json
		this.javaToJson(list, new String[]{"decidedzones"});
		return NONE;
	}
	
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	
}
