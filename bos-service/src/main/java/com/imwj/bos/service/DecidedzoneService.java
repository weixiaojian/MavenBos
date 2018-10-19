package com.imwj.bos.service;

import com.imwj.bos.domain.Decidedzone;
import com.imwj.bos.utils.PageQuery;

public interface DecidedzoneService {

	void save(Decidedzone model, String[] subareaid);

	void pageQuery(PageQuery pageQuery);

}
