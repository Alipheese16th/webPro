package com.kist.portal.ch.csttstat.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.ch.csttstat.mapper.CsttStatMapper;

@Service
public class CsttStatServiceImpl implements CsttStatService {
	
	@Autowired
	CsttStatMapper mapper;

	@Override
	public List<Map<String, ?>> selectRgltComboList(Map<String, String> param) {
		return mapper.selectRgltComboList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectRgltChkTotalList(Map<String, Object> param) {
		return mapper.selectRgltChkTotalList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectStatReschListTab1(Map<String, String> param) {
		return mapper.selectStatReschListTab1(param);
	}
	
	@Override
	public List<Map<String, ?>> selectStatReschListTab2(Map<String, String> param) {
		return mapper.selectStatReschListTab2(param);
	}
	
	@Override
	public List<Map<String, ?>> selectChemsSqtRreschList(Map<String, String> param) {
		return mapper.selectChemsSqtRreschList(param);
	}
}
