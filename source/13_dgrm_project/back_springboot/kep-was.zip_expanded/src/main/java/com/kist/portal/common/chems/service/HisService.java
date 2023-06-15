package com.kist.portal.common.chems.service;

import java.util.List;
import java.util.Map;

import com.kist.portal.common.chems.dto.LoginLogDto;

public interface HisService {

	public List<LoginLogDto> selectLoginHisList(LoginLogDto param);
	
	public List<Map<String, ?>> selectApiHisList(Map<String, String> param);
	
	public List<Map<String, ?>> selectBatHisList(Map<String, String> param);
	
	public String selectDetailBatHis(String id);
	
	public List<Map<String, ?>> selectBatchList(Map<String, String> param);
}
