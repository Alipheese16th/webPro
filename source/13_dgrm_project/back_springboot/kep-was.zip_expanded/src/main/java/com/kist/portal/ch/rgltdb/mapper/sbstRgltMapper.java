package com.kist.portal.ch.rgltdb.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface sbstRgltMapper {
	
	public List<Map<String, ?>> selectsbstRgltList(Map<String, String> param);
	

}
