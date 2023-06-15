package com.kist.portal.common.chems.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CdMapper {

	public List<Map<String, ?>> selectCdGrpList(Map<String, String> param);
	
	public List<Map<String, ?>> selectCdList(Map<String, String> param);
	
	public String selectBizCdKey(Map<String, String> param);
	
	public int insertCdGrpList(Map<String, String> param);
	
	public int updateCdGrpList(Map<String, String> param);
	
	public int deleteCdGrpList(Map<String, String> param);

	public int deleteCdList(Map<String, String> param);
	
	public int insertCdList(Map<String, String> param);
	
	public int updateCdList(Map<String, String> param);
	
	public int deleteCd(Map<String, String> param);
	
	public int insertCdMlangList(Map<String, String> param);
	
	public String selectMlangCdGrpKey(Map<String, String> param);
	
	public int updateCdMlangList(Map<String, String> param);
	
	public int deleteCdMlangList(Map<String, String> param);
	
	public int deleteMlangAllCdList(Map<String, String> param);
	
	public Map<String, ?> selectCdDetail(Map<String, String> param);
	
	public Map<String, ?> selectCdMlangNoInfo(Map<String, String> param);
}
