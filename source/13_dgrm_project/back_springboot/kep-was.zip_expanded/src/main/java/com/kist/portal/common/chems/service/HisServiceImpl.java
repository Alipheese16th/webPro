package com.kist.portal.common.chems.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.common.chems.dto.LoginLogDto;
import com.kist.portal.common.chems.mapper.HisMapper;

@Service
public class HisServiceImpl implements HisService {

	@Autowired
	HisMapper mapper;
	
	@Override
	public List<LoginLogDto> selectLoginHisList(LoginLogDto param) {
		return mapper.selectLoginHisList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectApiHisList(Map<String, String> param) {
		return mapper.selectApiHisList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectBatHisList(Map<String, String> param) {
		return mapper.selectBatHisList(param);
	}
	
	@Override
	public String selectDetailBatHis(String id) {
		return mapper.selectDetailBatHis(id);
	}

	@Override
	public List<Map<String, ?>> selectBatchList(Map<String, String> param) {
		return mapper.selectBatchList(param);
	}
}
