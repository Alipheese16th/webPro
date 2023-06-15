package com.kist.portal.common.pub.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.common.pub.mapper.PublicMapper;

@Service
public class PublicServiceImpl implements PublicService{
	
	@Autowired
	PublicMapper mapper;

	@Override
	public List<Map<String, Object>> selectAponJsonData(Map<String, String> param) {
		return mapper.selectAponJsonData(param);
	}

	@Override
	public String selectAppVersion(Map<String, String> param) {
		// TODO Auto-generated method stub
		return mapper.selectAppVersion(param);
	}
}
