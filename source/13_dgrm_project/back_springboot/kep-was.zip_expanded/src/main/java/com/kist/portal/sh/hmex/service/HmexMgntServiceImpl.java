package com.kist.portal.sh.hmex.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.sh.hmex.mapper.HmexMgntMapper;

@Service
public class HmexMgntServiceImpl implements HmexMgntService {

	@Autowired(required=true)
	HmexMgntMapper mapper;

	@Override
	public List<Map<String, ?>> selectMstrList(Map<String, String> param) {	
		return mapper.selectMstrList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectTrgpList(Map<String, String> param) {	
		return mapper.selectTrgpList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectCurrTrgpList(Map<String, String> param) {	
		return mapper.selectCurrTrgpList(param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int insertHmexMgnt(Map<String, Object> param) {
		int n = 0;
		List<Map<String, String>> trgpList = (List<Map<String, String>>) param.get("trgp_list");
		for(Map<String, String> trgp : trgpList) {
			trgp.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));
			n = mapper.insertHmexMgnt(trgp);
		}
		return n;
	}
	
	@Override
	public int deleteHmexMgnt(Map<String, String> param) {
		return mapper.deleteHmexMgnt(param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int insertHmexMgnt2(Map<String, Object> param) {
		int n = 0;
		List<Map<String, String>> trgpList = (List<Map<String, String>>) param.get("trgp_list");
		for(Map<String, String> trgp : trgpList) {
			trgp.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));
			n = mapper.insertHmexMgnt2(trgp);
		}
		return n;
	}
}
