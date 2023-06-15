package com.kist.portal.st.stndinfr.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WkplChkListMgntMapper {
	
	public List<Map<String, ?>> selectWkplChkList(Map<String, String> param);
	
	public String selectWkplChkKeyPass(Map<String, String> param);
	
	public int insertWkplChkList(Map<String, String> param);
	
	public int deleteWkplChkList(Map<String, String> param);
}
