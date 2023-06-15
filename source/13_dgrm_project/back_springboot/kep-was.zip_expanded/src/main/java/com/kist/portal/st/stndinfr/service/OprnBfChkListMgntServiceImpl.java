package com.kist.portal.st.stndinfr.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.st.stndinfr.mapper.OprnBfChkListMgntMapper;

@Service
public class OprnBfChkListMgntServiceImpl implements OprnBfChkListMgntService{

	@Autowired
	OprnBfChkListMgntMapper mapper;
	
	@Override
	public List<Map<String, ?>> selectOprnBfChkList(Map<String, String> param) {
		return mapper.selectOprnBfChkList(param);
	}

	@Override
	public int insertOprnBfChkList(Map<String, Object> param) {
		return mapper.insertOprnBfChkList(param);
	}

	@Override
	public int updateOprnBfChkList(Map<String, Object> param) {
		return mapper.updateOprnBfChkList(param);
	}

	@Override
	public int deleteOprnBfChkList(Map<String, Object> param) {
		return mapper.deleteOprnBfChkList(param);
	}

}
