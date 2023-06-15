package com.kist.portal.common.email.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface sendMailMapper {

	public int insertMailSenderLogList(Map<String, String> param);
	
	public int insertMailReciverLogList(Map<String, String> param);
	
	public List<Map<String, ?>> selectEmailData(Map<String, String> param);
	
	public int selectEmailKey(Map<String, String> param);
	
}
