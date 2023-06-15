package com.kist.portal.common.bbs.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BbsMapper {

	public List<Map<String, ?>> selectNotiList(Map<String, String> param);

	public int saveNoti(Map<String, String> param);
	
	public int updateNoti(Map<String, String> param);
	
	public int updateNotiCnt(Map<String, String> param);
	
	public Map<String, ?> selectNotiDtl(Map<String, String> param);
	
	public int deleteNoti(Map<String, String> param);
	
	public List<Map<String, ?>> selectFaqsList(Map<String, String> param);

	public int saveFaqs(Map<String, String> param);
	
	public int updateFaqs(Map<String, String> param);
	
	public Map<String, ?> selectFaqsDtl(Map<String, String> param);

	public int deleteFaqs(Map<String, String> param);
	
	public List<Map<String, ?>> selectArchList(Map<String, String> param);

	public int saveArch(Map<String, String> param);
	
	public int updateArch(Map<String, String> param);
	
	public Map<String, ?> selectArchDtl(Map<String, String> param);
	
	public int deleteArch(Map<String, String> param);
	
	public List<Map<String, ?>> selectNotiYList(Map<String, String> param);
	
	public List<Map<String, ?>> selectManuList(Map<String, String> param);

	public int saveManu(Map<String, String> param);
	
	public int updateManu(Map<String, String> param);
	
	public Map<String, ?> selectManuDtl(Map<String, String> param);
	
	public int deleteManu(Map<String, String> param);
	
	public String selectBbskey(Map<String, String> param);


}
