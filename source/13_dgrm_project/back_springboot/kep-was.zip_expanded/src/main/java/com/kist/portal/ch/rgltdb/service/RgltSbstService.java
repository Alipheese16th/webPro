package com.kist.portal.ch.rgltdb.service;

import java.util.List;
import java.util.Map;

public interface RgltSbstService {
	
	public List<Map<String, ?>> selectSbstList(Map<String, String> param);
	
	public Map<String, ?> selectSbstDtl(String id);
	
	public Map<String, ?> selectSbstKeyChk(String id);
	
	public int insertSbstDtl(Map<String, String> param);
	
	public int insertSbstDtlHst(Map<String, String> param);
	
	public int updateSbstDtl(Map<String, String> param);
	
	public List<Map<String, ?>> selectSbstHstList(Map<String, String> param);
	
	public Map<String, ?> selectSbstRgltDtlInfo(Map<String, String> param);
	
	public List<Map<String, ?>> selectSbstRgltDtlList(Map<String, String> param);
	
	public int insertSbstRgltDtlList(Map<String, String> param);
	
	public int deleteSbstRgltDtlList(Map<String, String> param);
	
	public int insertSbstRgltDtlHst(Map<String, String> param);
	
	public Map<String, ?> selectSbstRgltDtlChk(Map<String, String> param);
	
	public Map<String, ?> selectRgltSbstDtlInfo(Map<String, String> param);
	
	public List<Map<String, ?>> selectRgltSbstDtlList(Map<String, String> param);
	
	public List<Map<String, ?>> selectRgltSbstList(Map<String, String> param);
	
	public List<Map<String, ?>> selectRgltSbstChgHstList(Map<String, String> param);
	
}
