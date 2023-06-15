package com.kist.portal.common.test.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper {
	
	public List<Map<String, ?>> selectBbsList(Map<String, String> param);
	
	public Map<String, ?> selectBbs(String id);
	
	public int insertBbs(Map<String, String> param);
	
	public int updateBbs(Map<String, String> param);
	
	public int deleteBbs(Map<String, String> param);

}
