package com.kist.portal.common.aprv.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AprvMapper {

	public int insertAprv(Map<String, Object> param);
	
	public String selectAprvkey();

	
}
