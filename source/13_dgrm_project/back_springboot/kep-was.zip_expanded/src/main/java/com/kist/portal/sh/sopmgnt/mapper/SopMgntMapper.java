package com.kist.portal.sh.sopmgnt.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SopMgntMapper {
	
	public List<Map<String, ?>> selectSopMgntList(Map<String, String> param);
	
	public Map<String, Object> selectSopMgnt(Map<String, String> param);
	
	public List<Map<String, ?>> selectSopMgntWkpl(Map<String, String> param);

	public int insertSopMgnt(Map<String, Object> param);
	
	public int insertSopMgntWkpl(Map<String, String> param);
	
	public int updateSopMgnt(Map<String, Object> param);
	
	public int deleteSopMgntWkpl(Map<String, Object> param);
	
	public int deleteSopMgnt(Map<String, String> param);
	
	public String selectSopMgntId(Map<String, String> param);
	
	public List<Map<String, ?>> selectSopMgntBaseWkpl(Map<String, String> param);

	public int delSop(Map<String, String> param);

}
