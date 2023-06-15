package com.kist.portal.common.email.service;

import java.util.List;
import java.util.Map;

public interface sendMailService {

	public int insertMailSenderLogList(Map<String, String> param);
	
	public int insertMailReciverLogList(Map<String, String> param);
	
	public List<Map<String, ?>> selectEmailData(Map<String, String> param);
	
	public int selectEmailKey(Map<String, String> param);
	
}
