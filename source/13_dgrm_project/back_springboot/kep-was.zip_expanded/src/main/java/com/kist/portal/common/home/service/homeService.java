package com.kist.portal.common.home.service;

import java.util.List;
import java.util.Map;

public interface homeService {

	public List<Map<String, ?>> selectNotiList(Map<String, String> param);
	
	public List<Map<String, ?>> selectRgltAlrmList(Map<String, String> param);
	
	public List<Map<String, ?>> selectMtrlCount(Map<String, String> param);
	
	public List<Map<String, ?>> selectRgltCount(Map<String, String> param);

	public List<Map<String, ?>> selectUseBizList(Map<String, String> param);

	public List<Map<String, ?>> selectWhList(Map<String, String> param);

	public List<Map<String, ?>> selectRgltSbstList(Map<String, String> param);
	
	public List<Map<String, ?>> selectUseSafeList(Map<String, String> param);
}
