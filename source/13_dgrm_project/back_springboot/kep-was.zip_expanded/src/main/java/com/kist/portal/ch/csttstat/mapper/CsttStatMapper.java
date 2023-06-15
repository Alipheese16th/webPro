package com.kist.portal.ch.csttstat.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CsttStatMapper {
	
	public List<Map<String, ?>> selectRgltComboList(Map<String, String> param);
	
	public List<Map<String, ?>> selectRgltChkTotalList(Map<String, Object> param);
	
	public List<Map<String, ?>> selectStatReschListTab1(Map<String, String> param);
	
	public List<Map<String, ?>> selectStatReschListTab2(Map<String, String> param);
	
	public List<Map<String, ?>> selectChemsSqtRreschList(Map<String, String> param);
	
}
