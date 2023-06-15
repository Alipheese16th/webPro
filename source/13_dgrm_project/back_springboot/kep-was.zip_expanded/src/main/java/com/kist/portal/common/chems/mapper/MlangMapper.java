package com.kist.portal.common.chems.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MlangMapper {

	public List<Map<String, ?>> selectMlangList(Map<String, String> param);
	
	public String selectMlangkey(Map<String, String> param);
	
	public int insertMlangList(Map<String, String> param);
	
	public int updateMlangList(Map<String, String> param);
	
	public int deleteMlangList(Map<String, String> param);
	
	public int updateRoleMlangList(Map<String, String> param);
	
	public String selectMlangInfo(Map<String, String> param);
	
	public List<Map<String, String>> selectMlangListInfo(Map<String, Object> param);
}
