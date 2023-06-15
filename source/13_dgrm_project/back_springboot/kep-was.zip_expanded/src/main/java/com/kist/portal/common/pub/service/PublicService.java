package com.kist.portal.common.pub.service;

import java.util.List;
import java.util.Map;

public interface PublicService {

	public List<Map<String, Object>> selectAponJsonData(Map<String, String> param);
	
	public String selectAppVersion(Map<String, String> param);
}
