package com.imwj.bos.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Repository;

import com.imwj.bos.dao.WorkbillDao;
import com.imwj.bos.dao.base.impl.BaseDaoImpl;
import com.imwj.bos.domain.Workbill;
import com.imwj.bos.utils.PageQuery;

@Repository
public class WorkbillDaoImpl extends BaseDaoImpl<Workbill> implements WorkbillDao {

}
