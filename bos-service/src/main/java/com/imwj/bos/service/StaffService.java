package com.imwj.bos.service;

import com.imwj.bos.domain.Staff;
import com.imwj.bos.utils.PageQuery;

public interface StaffService {

	void addStaff(Staff model);

	void findPageQuery(PageQuery pageQuery);

	void deleteBatchByIdes(String ids);

	Staff findStaffById(String id);

	void update(Staff staff);

}
