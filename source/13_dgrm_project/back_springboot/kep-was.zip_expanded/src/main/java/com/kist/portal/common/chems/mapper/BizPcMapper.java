package com.kist.portal.common.chems.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BizPcMapper {

	public List<Map<String, ?>> selectBizPcGrpList(Map<String, String> param);
	
	public List<Map<String, ?>> selectBizPcDtlList(Map<String, String> param);

	public String selectBizPckey(Map<String, String> param);
	
	public int saveBizPcGrp(Map<String, String> param);
	
	public int deleteBizPcGrp(Map<String, String> param);
	
	public int saveBizPc(Map<String, String> param);
	
	public int deleteBizPc(Map<String, String> param);

	
}
