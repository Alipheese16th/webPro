package com.kist.portal.ch.rgltdb.service;

import java.util.List;
import java.util.Map;

public interface RgltService {
	
	public List<Map<String, ?>> selectRgltList(Map<String, String> param);
	
	public Map<String, ?> selectRgltDtl(String id);
	
	public Map<String, ?> selectRgltDtlKey();
	
	public int insertRgltDtl(Map<String, String> param);
	
	public int insertRgltDtlHst(Map<String, String> param);
	
	public int updateRgltDtl(Map<String, String> param);
	
	public int deleteRgltDtl(Map<String, String> param);
	
	public List<Map<String, ?>> selectRgltHstList(Map<String, String> param);

}
