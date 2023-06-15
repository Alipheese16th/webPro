package com.kist.portal.sh.opem.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OpemActnMapper {
	
	public List<Map<String, ?>> selectOpemActnList(Map<String, String> param);
	
	public Map<String, Object> selectOpemActn(Map<String, Object> param);
	
	public int insertOpemActn(Map<String, Object> param);
	
	public int updateOpemActn(Map<String, Object> param);
	
	public int updateAppr(Map<String, Object> param);
	
	public int deleteOpemActn(Map<String, String> param);
	
	public String selectOpemActnId(Map<String, String> param);

	public List<Map<String, ?>> selectOpemActnAprvDtl(Map<String, Object> param);

	public int updateOpemActnAprvAf(Map<String, Object> param);
}
