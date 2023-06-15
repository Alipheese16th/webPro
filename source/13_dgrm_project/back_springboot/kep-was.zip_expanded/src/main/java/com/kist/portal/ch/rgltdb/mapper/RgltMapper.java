package com.kist.portal.ch.rgltdb.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RgltMapper {
	
	public List<Map<String, ?>> selectRgltList(Map<String, String> param);
	
	public Map<String, ?> selectRgltDtl(String id);
	
	public Map<String, ?> selectRgltDtlKey();
	
	public int insertRgltDtl(Map<String, String> param);
	
	public int insertRgltDtlHst(Map<String, String> param);
	
	public int updateRgltDtl(Map<String, String> param);
	
	public int deleteRgltDtl(Map<String, String> param);
	
	public List<Map<String, ?>> selectRgltHstList(Map<String, String> param);

}
