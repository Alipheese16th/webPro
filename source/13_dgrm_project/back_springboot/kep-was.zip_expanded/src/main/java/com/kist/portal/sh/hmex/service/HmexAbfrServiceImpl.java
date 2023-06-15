package com.kist.portal.sh.hmex.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.sh.hmex.mapper.HmexAbfrMapper;

@Service
public class HmexAbfrServiceImpl implements HmexAbfrService {

	@Autowired(required=true)
	HmexAbfrMapper mapper;

	@Override
	public List<Map<String, ?>> selectAbfrList(Map<String, String> param) {	
		return mapper.selectAbfrList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectAbfrDtlList(Map<String, String> param) {	
		return mapper.selectAbfrDtlList(param);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int insertAbfr(Map<String, Object> param) {
		int n = 0;
		List<Map<String, String>> abfrList = (List<Map<String, String>>) param.get("insert_list");
		for(Map<String, String> abfr : abfrList) {
			abfr.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));
			n = mapper.insertAbfr(abfr);
		}
		
		abfrList = (List<Map<String, String>>) param.get("update_list");
		for(Map<String, String> abfr : abfrList) {
			abfr.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));
			n = mapper.updateAbfr(abfr);
		}
		return n;
	}
	
	@Override
	public int deleteAbfr(Map<String, String> param) {
		return mapper.deleteAbfr(param);
	}
}
