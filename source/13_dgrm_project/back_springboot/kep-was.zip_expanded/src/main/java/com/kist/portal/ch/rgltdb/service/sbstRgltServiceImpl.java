package com.kist.portal.ch.rgltdb.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.ch.rgltdb.mapper.sbstRgltMapper;

@Service
public class sbstRgltServiceImpl implements sbstRgltService {
	
	@Autowired
	sbstRgltMapper mapper;

	@Override
	public List<Map<String, ?>> selectsbstRgltList(Map<String, String> param) {
		return mapper.selectsbstRgltList(param);
	}


}
