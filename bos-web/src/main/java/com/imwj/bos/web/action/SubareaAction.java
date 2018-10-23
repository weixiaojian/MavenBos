package com.imwj.bos.web.action;

import java.util.List;

import javax.servlet.ServletOutputStream;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.imwj.bos.domain.Region;
import com.imwj.bos.domain.Subarea;
import com.imwj.bos.service.SubareaService;
import com.imwj.bos.utils.FileUtils;
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
	
	/**
	 * 分区数据导出
	 * @return
	 * @throws Exception
	 */
	public String exprotXls()throws Exception{
		//1.查询所有的分区数据
		List<Subarea> list = subareaService.findAll();
		
		//2.使用POI将数据写入到excel中
		//在内存中创建一个excel文件
		HSSFWorkbook workbook = new HSSFWorkbook();
		//创建一个标签页
		HSSFSheet sheet = workbook.createSheet("分区数据");
		//创建标题行
		HSSFRow headRow = sheet.createRow(0);
		headRow.createCell(0).setCellValue("分区编号");
		headRow.createCell(1).setCellValue("开始编号");
		headRow.createCell(2).setCellValue("结束编号");
		headRow.createCell(3).setCellValue("位置信息");
		headRow.createCell(4).setCellValue("省市区");
		
		for(Subarea subarea : list){
			HSSFRow dataRow = sheet.createRow(sheet.getLastRowNum()+1);
			dataRow.createCell(0).setCellValue(subarea.getId());
			dataRow.createCell(1).setCellValue(subarea.getStartnum());
			dataRow.createCell(2).setCellValue(subarea.getEndnum());
			dataRow.createCell(3).setCellValue(subarea.getPosition());
			dataRow.createCell(4).setCellValue(subarea.getRegion().getName());
		}
		
		//3.使用输出流下载一个文件
		String fileName = "分区数据.xls";
		String mimeType = ServletActionContext.getServletContext().getMimeType(fileName);
		ServletOutputStream outputStream = ServletActionContext.getResponse().getOutputStream();
		ServletActionContext.getResponse().setContentType(mimeType);
		
		//获取客户端的类型
		String agent = ServletActionContext.getRequest().getHeader("User-Agent");
		fileName = FileUtils.encodeDownloadFilename(fileName, agent);
		ServletActionContext.getResponse().setHeader("content-disposition", "attachment;filename="+fileName);
		workbook.write(outputStream);
		return	NONE;
	}
	
	/**
	 * 添加定区的关联分区ajx请求
	 * @return
	 */
	public String listAjax(){
		//得到list数据
		List<Subarea> list = subareaService.findAllNotDecidedzone();
		//转换成json字符串
		javaToJson(list, new String[]{"region"});
		return NONE;
	}
	
	/**
	 * 定区中双击定区查看他关联的分区
	 */
	private String decidedzoneId;
	public String findListByDecidedzoneId(){
		List<Subarea> list = subareaService.finAllBydecidedzoneId(decidedzoneId);
		javaToJson(list, new String[]{"decidedzone","subareas"});
		return NONE;
	}

	public void setDecidedzoneId(String decidedzoneId) {
		this.decidedzoneId = decidedzoneId;
	}
}


