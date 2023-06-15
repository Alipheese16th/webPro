package com.kist.portal.ch.rgltact.service;

import java.util.List;
import java.util.Map;

public interface LncsService {

	public List<Map<String, ?>> selectLncsList(Map<String, String> param);

	public int saveLncs(Map<String, String> param);
	
	public int updateLncs(Map<String, String> param);
	
	public Map<String, ?> selectLncsDtl(Map<String, String> param);
	
	public String selectBbskey(Map<String, String> param);
	
	public int deleteLncs(Map<String, String> param);
	
	public List<Map<String, ?>> selectRgltActList(Map<String, String> param);

	public List<Map<String, ?>> selectRgltActDetail(Map<String, String> param);
}
	
