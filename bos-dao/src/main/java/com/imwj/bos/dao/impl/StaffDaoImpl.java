package com.imwj.bos.dao.impl;

import org.springframework.stereotype.Repository;

import com.imwj.bos.dao.StaffDao;
import com.imwj.bos.dao.base.IBaseDao;
import com.imwj.bos.dao.base.impl.BaseDaoImpl;
import com.imwj.bos.domain.Staff;
import com.imwj.bos.domain.User;

@Repository
public class StaffDaoImpl extends BaseDaoImpl<Staff> implements  StaffDao{

}
