package com.kist.portal.common.aprv.service;

import java.util.Map;

public interface AprvService {

	public int insertAprv(Map<String, Object> param);
	
	public String selectAprvkey();

	public String getaprvCSS();
}
