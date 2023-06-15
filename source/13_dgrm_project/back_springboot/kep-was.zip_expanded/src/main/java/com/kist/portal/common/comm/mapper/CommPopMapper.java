package com.kist.portal.common.comm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommPopMapper {
	
	public List<Map<String, ?>> selectSbstCommPopList(Map<String, String> param);
	
	public List<Map<String, ?>> selectMtrlCommPopList(Map<String, String> param);
	
	public List<Map<String, ?>> selectVndrCommPopList(Map<String, String> param);
	
	public List<Map<String, ?>> selectMtrlMultiCommPopList(Map<String, String> param);
	
	public List<Map<String, ?>> selectBsendCommPopList(Map<String, String> param);
	
	public List<Map<String, ?>> selectBsendUserCommPopList(Map<String, String> param);

}
