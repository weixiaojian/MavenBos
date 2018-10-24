package com.imwj.bos.service.impl;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imwj.bos.crm.ICustomerService;
import com.imwj.bos.dao.DecidedzoneDao;
import com.imwj.bos.dao.NoticebillDao;
import com.imwj.bos.dao.WorkbillDao;
import com.imwj.bos.domain.Decidedzone;
import com.imwj.bos.domain.Noticebill;
import com.imwj.bos.domain.Staff;
import com.imwj.bos.domain.User;
import com.imwj.bos.domain.Workbill;
import com.imwj.bos.service.NoticebillService;
import com.imwj.bos.utils.BOSUtils;

@Service
@Transactional
public class NoticebillServiceImpl implements NoticebillService{
	
	@Autowired
	private NoticebillDao noticebillDao;
	@Autowired
	private ICustomerService proxy;
	@Autowired
	private DecidedzoneDao decidedzoneDao;
	@Autowired
	private WorkbillDao WorkbillDao;

	public void save(Noticebill model) {
		//封装一个Noticebill，并保存
		User user = BOSUtils.getLoginUser();
		model.setUser(user);
		noticebillDao.save(model);
		//根据客户回显地址去查询在那个定区id
		String decidedzone_id = proxy.findDecidedzoneIdByAddress(model.getPickaddress());
		//根据是否有定区id来判断是否自动分单：有》自动分单，没有》人工分单
		if(decidedzone_id!=null){
			Decidedzone decidedzone = decidedzoneDao.findById(decidedzone_id);
			Staff staff = decidedzone.getStaff();
			model.setStaff(staff);
			//设置自动分单
			model.setOrdertype(Noticebill.ORDERTYPE_AUTO);
			//封装一个工单Workbill对象（自动生成）
			Workbill workbill = new Workbill();
			workbill.setAttachbilltimes(0);//追单次数
			workbill.setBuildtime(new Timestamp(System.currentTimeMillis()));//工单生成时间
			workbill.setNoticebill(model);//工单关联页面通知单
			workbill.setPickstate(Workbill.PICKSTATE_NO);//工单状态：未取件
			workbill.setRemark(model.getRemark());//备注信息
			workbill.setStaff(staff);//工单关联取派员
			workbill.setType(Workbill.TYPE_1);//工单类型：新单
			WorkbillDao.save(workbill);
			
			//短信通知
			
		}else{
			//设置人工分单
			model.setOrdertype(Noticebill.ORDERTYPE_MAN);
		}
	}
}
