package com.kist.portal.st.stndinfr.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OprnBfChkListMgntMapper {

	public List<Map<String, ?>> selectOprnBfChkList(Map<String, String> param);

	public int insertOprnBfChkList(Map<String, Object> param);
	
	public int updateOprnBfChkList(Map<String, Object> param);
	
	public int deleteOprnBfChkList(Map<String, Object> param);
	
}
