package com.kist.portal.sh.hmex.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HmexAbfrMapper {
	
	public List<Map<String, ?>> selectAbfrList(Map<String, String> param);
	
	public List<Map<String, ?>> selectAbfrDtlList(Map<String, String> param);
	
	public int insertAbfr(Map<String, String> param);
	
	public int updateAbfr(Map<String, String> param);
	
	public int deleteAbfr(Map<String, String> param);

}
