package com.kist.portal.sh.hmex.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HmexMstrMapper {
	
	public List<Map<String, ?>> selectDeptList(Map<String, String> param);
	
	public List<Map<String, ?>> selectUserList(Map<String, String> param);
	
	public List<Map<String, ?>> selectMtraList(Map<String, String> param);
	
	public int insertHmexMstr(Map<String, String> param);
	
	public int deleteHmexMstr(Map<String, Object> param);

}
