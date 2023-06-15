package com.kist.portal.sh.htcrrm.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HtcrRmMgntMapper {
	
	public List<Map<String, ?>> selectHtcrRmList(Map<String, String> param);
	
	public int insertHtcrRm(Map<String, String> param);
	
	public int updateHtcrRm(Map<String, String> param);
	
	public int deleteHtcrRm(Map<String, String> param);

}
