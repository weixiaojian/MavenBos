package com.imwj.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.junit.Test;

import com.imwj.bos.dao.impl.RegionDaoImpl;
import com.imwj.bos.domain.Region;
import com.imwj.bos.service.impl.RegionServiceImpl;



public class TestPoi {
	
	//@Test
	public void fun1() throws Exception, IOException{
		//测试数据路径
		String filePath = "F:\\北京javaee就业班32期\\[www.javaxxz.com]项目一：物流BOS系统（58-71天）\\BOS-day05\\资料\\区域导入测试数据.xls";
		//包装一个Excel文件对象
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(new FileInputStream(new File(filePath)));
		//读取文件中第一个Sheet标签页
		HSSFSheet sheetAt = hssfWorkbook.getSheetAt(0);
		//遍历标签页中所有的行
		for (Row row : sheetAt) {
			int rowNum = row.getRowNum();//得到行号，去掉标题行
			if(rowNum == 0){
				continue;
			}
			for (Cell cell : row) {
				String value = cell.getStringCellValue();//得到每一个单元格数据
				System.out.print(value);
			}
			System.out.println();
		}
	}
	
	@Test
	public void fun2(){
		RegionDaoImpl rs = new RegionDaoImpl();
		List<Region> list = rs.findListByQ("北京");
		for (Region region : list) {
			System.out.println(region);
		}
	}
}
