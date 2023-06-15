package com.kist.portal.ch.rgltdb.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.ch.rgltdb.mapper.RgltSbstMapper;

@Service
public class RgltSbstServiceImpl implements RgltSbstService {
	
	@Autowired
	RgltSbstMapper mapper;

	@Override
	public List<Map<String, ?>> selectSbstList(Map<String, String> param) {
		return mapper.selectSbstList(param);
	}
	
	@Override
	public Map<String, ?> selectSbstDtl(String id) {
		return mapper.selectSbstDtl(id);
	}

	@Override
	public Map<String, ?> selectSbstKeyChk(String id) {
		return mapper.selectSbstKeyChk(id);
	}
	
	@Override
	public int insertSbstDtl(Map<String, String> param) {
		return mapper.insertSbstDtl(param);
	}
	
	@Override
	public int insertSbstDtlHst(Map<String, String> param) {
		return mapper.insertSbstDtlHst(param);
	}

	@Override
	public int updateSbstDtl(Map<String, String> param) {
		return mapper.updateSbstDtl(param);
	}

	@Override
	public List<Map<String, ?>> selectSbstHstList(Map<String, String> param) {
		return mapper.selectSbstHstList(param);
	}
	
	@Override
	public Map<String, ?> selectSbstRgltDtlInfo(Map<String, String> param) {
		return mapper.selectSbstRgltDtlInfo(param);
	}
	
	@Override
	public List<Map<String, ?>> selectSbstRgltDtlList(Map<String, String> param) {
		return mapper.selectSbstRgltDtlList(param);
	}
	
	@Override
	public int insertSbstRgltDtlList(Map<String, String> param) {
		return mapper.insertSbstRgltDtlList(param);
	}
	
	@Override
	public int deleteSbstRgltDtlList(Map<String, String> param) {
		return mapper.deleteSbstRgltDtlList(param);
	}
	
	@Override
	public int insertSbstRgltDtlHst(Map<String, String> param) {
		return mapper.insertSbstRgltDtlHst(param);
	}
	
	@Override
	public Map<String, ?> selectSbstRgltDtlChk(Map<String, String> param) {
		return mapper.selectSbstRgltDtlChk(param);
	}
	
	@Override
	public Map<String, ?> selectRgltSbstDtlInfo(Map<String, String> param) {
		return mapper.selectRgltSbstDtlInfo(param);
	}
	
	@Override
	public List<Map<String, ?>> selectRgltSbstDtlList(Map<String, String> param) {
		return mapper.selectRgltSbstDtlList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectRgltSbstList(Map<String, String> param) {
		return mapper.selectRgltSbstList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectRgltSbstChgHstList(Map<String, String> param) {
		return mapper.selectRgltSbstChgHstList(param);
	}
}
