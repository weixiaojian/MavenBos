package com.imwj.bos.web.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.imwj.bos.domain.Region;
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
	private SubareaService subareaService;
	
	/**
	 * 添加分区
	 * @throws Exception
	 */
	public String add()throws Exception{
		subareaService.add(model);
		return LIST;
	}
	
	/**
	 * 分区分页列表
	 */
	public String pageQuery(){
		DetachedCriteria dc = pageQuery.getDetachedCriteria();
		//动态添加过滤条件
		String addresskey = model.getAddresskey();
		if(StringUtils.isNotBlank(addresskey)){
			//添加过滤条件，根据地址关键字模糊查询
			dc.add(Restrictions.like("addresskey", "%"+addresskey+"%"));
		}
		
		Region region = model.getRegion();
		if(region != null){
			String province = region.getProvince();
			String city = region.getCity();
			String district = region.getDistrict();
			dc.createAlias("region", "r");
			if(StringUtils.isNotBlank(province)){
				//添加过滤条件，根据省份模糊查询-----多表关联查询，使用别名方式实现
				//参数一：分区对象中关联的区域对象属性名称
				//参数二：别名，可以任意
				dc.add(Restrictions.like("r.province", "%"+province+"%"));
			}
			if(StringUtils.isNotBlank(city)){
				//添加过滤条件，根据市模糊查询-----多表关联查询，使用别名方式实现
				//参数一：分区对象中关联的区域对象属性名称
				//参数二：别名，可以任意
				dc.add(Restrictions.like("r.city", "%"+city+"%"));
			}
			if(StringUtils.isNotBlank(district)){
				//添加过滤条件，根据区模糊查询-----多表关联查询，使用别名方式实现
				//参数一：分区对象中关联的区域对象属性名称
				//参数二：别名，可以任意
				dc.add(Restrictions.like("r.district", "%"+district+"%"));
			}
		}
		subareaService.pageQuery(pageQuery);
		this.javaToJson(pageQuery, new String[]{"currentPage","detachedCriteria","pageSize",
						"decidedzone","subareas"});
		return NONE;
	}
}


