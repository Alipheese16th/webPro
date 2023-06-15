package com.kist.portal.sh.hmex.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HmexRsltMapper {
	
	public List<Map<String, ?>> selectRsltList(Map<String, String> param);
	
	public Map<String, Object> selectUser(Map<String, String> param);
	
	public List<Map<String, ?>> selectHmexRslt(Map<String, String> param);
	
	public String selectHmexSn(Map<String, String> param);
	
	public int insertHmexRslt(Map<String, String> param);
	
	public int insertNewHmexMgnt(Map<String, String> param);
	
	public int insertNewHmexMgnt2(Map<String, String> param);
	
	public int insertHmexRslt2(Map<String, String> param);
	
	public int deleteHmexRslt(Map<String, String> param);

}
