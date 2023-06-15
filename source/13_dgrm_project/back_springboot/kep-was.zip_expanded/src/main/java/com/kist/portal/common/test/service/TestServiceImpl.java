package com.kist.portal.common.test.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.common.test.mapper.TestMapper;

@Service
public class TestServiceImpl implements TestService {
	
	@Autowired
	TestMapper mapper;

	@Override
	public List<Map<String, ?>> selectBbsList(Map<String, String> param) {
		return mapper.selectBbsList(param);
	}

	@Override
	public Map<String, ?> selectBbs(String id) {
		return mapper.selectBbs(id);
	}

	@Override
	public int insertBbs(Map<String, String> param) {
		return mapper.insertBbs(param);
	}

	@Override
	public int updateBbs(Map<String, String> param) {
		return mapper.updateBbs(param);
	}

	@Override
	public int deleteBbs(Map<String, String> param) {
		return mapper.deleteBbs(param);
	}

}
