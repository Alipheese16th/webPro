package com.kist.portal.sh.mtng.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MtngMapper {
	
	public List<Map<String, ?>> selectMtngList(Map<String, String> param);
	
	public Map<String, Object> selectMtng(Map<String, Object> param);
	
	public List<Map<String, ?>> selectMtngAtnd(Map<String, Object> param);
	
	public List<Map<String, ?>> selectMtngSbjc(Map<String, Object> param);

	public int insertMtng(Map<String, Object> param);
	
	public int insertMtngAtnd(Map<String, String> param);
	
	public int insertMtngSbjc(Map<String, String> param);
	
	public int updateMtng(Map<String, Object> param);
	
	public int updateAppr(Map<String, Object> param);
	
	public int deleteMtngAtnd(Map<String, Object> param);
	
	public int deleteMtngSbjc(Map<String, Object> param);
	
	public int deleteMtng(Map<String, String> param);
	
	public String selectMtngId(Map<String, String> param);

	public List<Map<String, ?>> selectMtngAprvDtl(Map<String, Object> param);

	public int updateMtngAprvAf(Map<String, Object> param);

	public int delMtng(Map<String, String> param);
}
