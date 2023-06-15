package com.kist.portal.sh.opem.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OpemMgntMapper {
	
	public List<Map<String, ?>> selectOpemMgntList(Map<String, String> param);
	
	public Map<String, Object> selectOpemMgnt(Map<String, Object> param);
	
	public List<Map<String, ?>> selectOpemMgntRslt(Map<String, Object> param);

	public int insertOpemMgnt(Map<String, Object> param);
	
	public int insertOpemMgntRslt(Map<String, String> param);
	
	public int updateOpemMgnt(Map<String, Object> param);
	
	public int updateAppr(Map<String, Object> param);
	
	public int deleteOpemMgntRslt(Map<String, Object> param);
	
	public int deleteOpemMgnt(Map<String, String> param);

	public List<Map<String, ?>> selectOpemMgntAprvDtl(Map<String, Object> param);

	public int updateOpemMgntAprvAf(Map<String, Object> param);

}
