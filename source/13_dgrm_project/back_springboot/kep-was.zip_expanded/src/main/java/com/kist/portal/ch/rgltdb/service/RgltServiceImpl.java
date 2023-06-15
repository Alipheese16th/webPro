package com.kist.portal.ch.rgltdb.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.ch.rgltdb.mapper.RgltMapper;

@Service
public class RgltServiceImpl implements RgltService {
	
	@Autowired
	RgltMapper mapper;

	@Override
	public List<Map<String, ?>> selectRgltList(Map<String, String> param) {
		return mapper.selectRgltList(param);
	}

	@Override
	public Map<String, ?> selectRgltDtl(String id) {
		return mapper.selectRgltDtl(id);
	}
	
	@Override
	public Map<String, ?> selectRgltDtlKey() {
		return mapper.selectRgltDtlKey();
	}

	@Override
	public int insertRgltDtl(Map<String, String> param) {
		return mapper.insertRgltDtl(param);
	}
	
	@Override
	public int insertRgltDtlHst(Map<String, String> param) {
		return mapper.insertRgltDtlHst(param);
	}

	@Override
	public int updateRgltDtl(Map<String, String> param) {
		return mapper.updateRgltDtl(param);
	}

	@Override
	public int deleteRgltDtl(Map<String, String> param) {
		return mapper.deleteRgltDtl(param);
	}
	
	@Override
	public List<Map<String, ?>> selectRgltHstList(Map<String, String> param) {
		return mapper.selectRgltHstList(param);
	}

}
