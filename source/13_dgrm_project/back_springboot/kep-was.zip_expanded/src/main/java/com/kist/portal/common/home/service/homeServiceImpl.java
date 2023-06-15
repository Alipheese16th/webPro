package com.kist.portal.common.home.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.common.home.mapper.homeMapper;

@Service
public class homeServiceImpl implements homeService {
	
	@Autowired
	homeMapper mapper;
	
	@Override
	public List<Map<String, ?>> selectNotiList(Map<String, String> param) {
		return mapper.selectNotiList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectRgltAlrmList(Map<String, String> param) {
		return mapper.selectRgltAlrmList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectMtrlCount(Map<String, String> param) {
		return mapper.selectMtrlCount(param);
	}
	
	@Override
	public List<Map<String, ?>> selectRgltCount(Map<String, String> param) {
		return mapper.selectRgltCount(param);
	}
	
	@Override
	public List<Map<String, ?>> selectUseBizList(Map<String, String> param) {
		return mapper.selectUseBizList(param);
	}

	@Override
	public List<Map<String, ?>> selectWhList(Map<String, String> param) {
		return mapper.selectWhList(param);
	}

	@Override
	public List<Map<String, ?>> selectRgltSbstList(Map<String, String> param) {
		return mapper.selectRgltSbstList(param);
	}

	@Override
	public List<Map<String, ?>> selectUseSafeList(Map<String, String> param) {
		return mapper.selectUseSafeList(param);
	}
}
