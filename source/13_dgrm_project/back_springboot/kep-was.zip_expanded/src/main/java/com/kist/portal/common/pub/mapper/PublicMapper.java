package com.kist.portal.common.pub.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PublicMapper {

	public List<Map<String, Object>> selectAponJsonData(Map<String, String> param);
	
	public String selectAppVersion(Map<String, String> param);
}
