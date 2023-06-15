package com.kist.portal.common.comm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommMapper {
	
	public List<Map<String, ?>> selectCdList(Map<String, String> param);
	
	public List<Map<String, ?>> selectCdGrpAllList(Map<String, String> param);

	public List<Map<String, ?>> selectCdAllList(Map<String, String> param);

	public int updateConfig(Map<String, String> param);
	
	public List<Map<String, ?>> selectMultiLanguageList(Map<String, String> param);
	
	public List<Map<String, ?>> selectTzCode(Map<String, String> param);
	
	public List<Map<String, ?>> selectWkplCode(Map<String, String> param);
	
	public int insertPrivacyLog(Map<String, String> param);
	
}
