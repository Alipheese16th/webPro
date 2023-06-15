package com.kist.portal.common.comm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.common.comm.mapper.CommPopMapper;

@Service
public class CommPopServiceImpl implements CommPopService {
	
	@Autowired
	CommPopMapper mapper;

	@Override
	public List<Map<String, ?>> selectSbstCommPopList(Map<String, String> param) {
		return mapper.selectSbstCommPopList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectMtrlCommPopList(Map<String, String> param) {
		return mapper.selectMtrlCommPopList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectVndrCommPopList(Map<String, String> param) {
		return mapper.selectVndrCommPopList(param);
	}

	@Override
	public List<Map<String, ?>> selectMtrlMultiCommPopList(Map<String, String> param) {
		return mapper.selectMtrlMultiCommPopList(param);

	}
	
	@Override
	public List<Map<String, ?>> selectBsendCommPopList(Map<String, String> param) {
		return mapper.selectBsendCommPopList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectBsendUserCommPopList(Map<String, String> param) {
		return mapper.selectBsendUserCommPopList(param);
	}
}
