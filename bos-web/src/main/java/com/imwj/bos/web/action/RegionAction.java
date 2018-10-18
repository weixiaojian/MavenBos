package com.imwj.bos.web.action;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.imwj.bos.domain.Region;
import com.imwj.bos.domain.Subarea;
import com.imwj.bos.service.IRegionService;
import com.imwj.bos.utils.PinYin4jUtils;
import com.imwj.bos.web.action.base.BaseAction;

/**
 * 区域管理
 * @author SpongBob
 *
 */
@Controller
@Scope("prototype")
public class RegionAction extends BaseAction<Region> {
	
	private File regionFile;
	
	private String q;
	
	@Autowired
	private IRegionService regionService;
	
	/**
	 * 导入excel数据操作
	 */
	public String importXls() throws Exception{
		//集合数组
		List<Region> reginList = new ArrayList<Region>();
		//得到workbook对象
		HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(regionFile));
		//得到excel中sheet1的数据
		HSSFSheet sheet = workbook.getSheet("Sheet1");
		//得到每一行的数据
		for (Row row : sheet) {
			int rowNum = row.getRowNum();//得到行号，去掉标题行
			if(rowNum == 0){
				continue;
			}
			String id = row.getCell(0).getStringCellValue();
			String province = row.getCell(1).getStringCellValue();
			String city = row.getCell(2).getStringCellValue();
			String district = row.getCell(3).getStringCellValue();
			String postcode = row.getCell(4).getStringCellValue();
			Region region = new Region(id, province, city, district, postcode, null, null, null);
			
			//简码---->>HBSJZQX：河北石家庄桥西
			province = province.substring(0, province.length() - 1);
			city = city.substring(0, city.length() - 1);
			district = district.substring(0, district.length() - 1);
			String info = province + city + district;
			String[] headByString = PinYin4jUtils.getHeadByString(info);
			String shortcode = StringUtils.join(headByString);
			//城市编码---->>shijiazhuang
			String citycode = PinYin4jUtils.hanziToPinyin(city, "");
			
			region.setShortcode(shortcode);
			region.setCitycode(citycode);
			reginList.add(region);
		}
		
		//保存操作
		regionService.saveOrupdate(reginList);
		return NONE;
	}
	
	/**
	 * 区域列表分页显示
	 */
	public String pageQuery() throws Exception {
		regionService.findPageQuery(pageQuery);
		this.javaToJson(pageQuery, new String[]{"currentPage","detachedCriteria","pageSize","subareas"});
		return NONE;
	}
	
	/**
	 * 查询所有数据 回写json
	 * @return
	 * @throws Exception
	 */
	public String listAjax()throws Exception{
		List<Region> list = null;
		if(StringUtils.isNotBlank(q)){
			list = regionService.findAllByQ(q);
		}else{
			list = regionService.finAll();
		}
		this.javaToJson(list, new String[]{"subareas"});
		return NONE;
	}
	
	public void setRegionFile(File regionFile) {
		this.regionFile = regionFile;
	}
	public void setQ(String q) {
		this.q = q;
	}
	
}
