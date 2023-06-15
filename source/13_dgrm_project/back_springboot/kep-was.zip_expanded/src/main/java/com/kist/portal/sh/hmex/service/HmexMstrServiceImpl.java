package com.kist.portal.sh.hmex.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.sh.hmex.mapper.HmexMstrMapper;

@Service
public class HmexMstrServiceImpl implements HmexMstrService {

	@Autowired(required=true)
	HmexMstrMapper mapper;

	@Override
	public List<Map<String, ?>> selectDeptList(Map<String, String> param) {	
		return mapper.selectDeptList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectUserList(Map<String, String> param) {	
		return mapper.selectUserList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectMtraList(Map<String, String> param) {	
		return mapper.selectMtraList(param);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int insertHmexMstr(Map<String, Object> param) {
		int n = mapper.deleteHmexMstr(param);
		List<Map<String, String>> trgpList = (List<Map<String, String>>) param.get("trgp_list");
		for(Map<String, String> trgp : trgpList) {
			trgp.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));
			n = mapper.insertHmexMstr(trgp);
		}
		return n;
	}
}
