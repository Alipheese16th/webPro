package com.kist.portal.sh.htcrrm.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.sh.htcrrm.mapper.HtcrRmMgntMapper;

@Service
public class HtcrRmMgntServiceImpl implements HtcrRmMgntService {

	@Autowired(required=true)
	HtcrRmMgntMapper mapper;

	@Override
	public List<Map<String, ?>> selectHtcrRmList(Map<String, String> param) {	
		return mapper.selectHtcrRmList(param);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int insertHtcrRm(Map<String, Object> param) {
		int n = 0;
		List<Map<String, String>> maofList = (List<Map<String, String>>) param.get("insert_list");
		for(Map<String, String> maof : maofList) {
			maof.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));
			n = mapper.insertHtcrRm(maof);
		}
		
		maofList = (List<Map<String, String>>) param.get("update_list");
		for(Map<String, String> maof : maofList) {
			maof.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));
			n = mapper.updateHtcrRm(maof);
		}
		return n;
	}
	
	@Override
	public int deleteHtcrRm(Map<String, String> param) {
		return mapper.deleteHtcrRm(param);
	}
}
