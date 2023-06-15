package com.kist.portal.st.stndinfr.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.st.stndinfr.mapper.WkplChkListMgntMapper;

@Service
public class WkplChkListMgntServiceImpl implements WkplChkListMgntService {
	
	@Autowired
	WkplChkListMgntMapper mapper;

	@Override
	public List<Map<String, ?>> selectWkplChkList(Map<String, String> param) {
		return mapper.selectWkplChkList(param);
	}
	
	@Override
	public String selectWkplChkKeyPass(Map<String, String> param) {
		return mapper.selectWkplChkKeyPass(param);
	}
	
	@Override
	public int insertWkplChkList(Map<String, String> param) {
		return mapper.insertWkplChkList(param);
	}
	
	@Override
	public int deleteWkplChkList(Map<String, String> param) {
		return mapper.deleteWkplChkList(param);
	}
}
