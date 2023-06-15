package com.kist.portal.ch.rgltact.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.ch.rgltact.mapper.LncsMapper;

@Service
public class LncsServiceImpl implements LncsService {

	@Autowired
	LncsMapper mapper;

	@Override
	public List<Map<String, ?>> selectLncsList(Map<String, String> param) {
		return mapper.selectLncsList(param);
	}

	@Override
	public int saveLncs(Map<String, String> param) {
		return mapper.saveLncs(param);
	}

	@Override
	public int updateLncs(Map<String, String> param) {
		return mapper.updateLncs(param);
	}

	@Override
	public Map<String, ?> selectLncsDtl(Map<String, String> param) {
		return mapper.selectLncsDtl(param);
	}

	@Override
	public String selectBbskey(Map<String, String> param) {
		return mapper.selectBbskey(param);

	}
	
	@Override
	public int deleteLncs(Map<String, String> param) {
		return mapper.deleteLncs(param);
	}
	
	@Override
	public List<Map<String, ?>> selectRgltActList(Map<String, String> param) {
		return mapper.selectRgltActList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectRgltActDetail(Map<String, String> param) {
		return mapper.selectRgltActDetail(param);
	}
}
