package com.kist.portal.sh.rgltvltn.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RgltVltnMapper {
	
	public List<Map<String, ?>> selectRgltVltnList(Map<String, String> param);
	
	public Map<String, Object> selectRgltVltn(Map<String, String> param);
	
	public int insertRgltVltn(Map<String, Object> param);
	
	public int updateRgltVltn(Map<String, Object> param);
	
	public int updateRgltVltnComplete(Map<String, Object> param);
	
	public int deleteRgltVltn(Map<String, String> param);
	
	public String selectRgltVltnId(Map<String, String> param);

	public Map<String, ?> selectRgltVltnComplete(Map<String, Object> param);

	public List<Map<String, ?>> selectRgltVltnAprvDtl(Map<String, Object> param);

	public int updateRgltVltnAprvAf(Map<String, Object> param);

	public int delRglt(Map<String, String> param);
}
