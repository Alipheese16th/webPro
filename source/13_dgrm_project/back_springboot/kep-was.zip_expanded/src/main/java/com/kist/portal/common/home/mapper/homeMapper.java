package com.kist.portal.common.home.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface homeMapper {
	
	public List<Map<String, ?>> selectNotiList(Map<String, String> param);
	
	public List<Map<String, ?>> selectRgltAlrmList(Map<String, String> param);
	
	public List<Map<String, ?>> selectMtrlCount(Map<String, String> param);
	
	public List<Map<String, ?>> selectRgltCount(Map<String, String> param);
	
	public List<Map<String, ?>> selectUseBizList(Map<String, String> param);
	
	public List<Map<String, ?>> selectWhList(Map<String, String> param);
	
	public List<Map<String, ?>> selectRgltSbstList(Map<String, String> param);
	
	public List<Map<String, ?>> selectUseSafeList(Map<String, String> param);
	
}
