package com.kist.portal.common.chems.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.common.chems.mapper.ApiMapper;

@Service
public class ApiServiceImpl implements ApiService {

	@Autowired
	ApiMapper mapper;
	
	@Override
	public List<Map<String, ?>> selectApiList(Map<String, String> param) {
		return mapper.selectApiList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectApiRoleList(Map<String, String> param) {
		return mapper.selectApiRoleList(param);
	}
	
	@Override
	public String selectApiKey(Map<String, String> param) {
		return mapper.selectApiKey(param);
	}
	
	@Override
	public int insertApiList(Map<String, String> param) {
		return mapper.insertApiList(param);
	}
	
	@Override
	public int updateApiList(Map<String, String> param) {
		return mapper.updateApiList(param);
	}
	
	@Override
	public int deleteApiList(Map<String, String> param) {
		return mapper.deleteApiList(param);
	}
	
	@Override
	public int deleteApiRole(Map<String, String> param) {
		return mapper.deleteApiRole(param);
	}
	
	@Override
	public int insertApiRole(Map<String, String> param) {
		return mapper.insertApiRole(param);
	}
	
	@Override
	public int updateApiRole(Map<String, String> param) {
		return mapper.updateApiRole(param);
	}
}
