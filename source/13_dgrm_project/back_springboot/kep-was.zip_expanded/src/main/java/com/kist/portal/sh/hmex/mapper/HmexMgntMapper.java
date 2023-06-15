package com.kist.portal.sh.hmex.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HmexMgntMapper {
	
	public List<Map<String, ?>> selectMstrList(Map<String, String> param);
	
	public List<Map<String, ?>> selectTrgpList(Map<String, String> param);
	
	public List<Map<String, ?>> selectCurrTrgpList(Map<String, String> param);
	
	public int insertHmexMgnt(Map<String, String> param);
	
	public int deleteHmexMgnt(Map<String, String> param);

	public int insertHmexMgnt2(Map<String, String> trgp);

}
