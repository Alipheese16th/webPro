package com.kist.portal.sh.sopmgnt.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.sh.sopmgnt.mapper.SopMgntMapper;

@Service
public class SopMgntServiceImpl implements SopMgntService {

	@Autowired(required=true)
	SopMgntMapper mapper;

	@Override
	public List<Map<String, ?>> selectSopMgntList(Map<String, String> param) {		
		return mapper.selectSopMgntList(param);
	}
	
	@Override
	public Map<String, ?> selectSopMgnt(Map<String, String> param) {
		Map<String, Object> map = mapper.selectSopMgnt(param);
		map.put("wkpl_list", mapper.selectSopMgntWkpl(param));
		
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int insertSopMgnt(Map<String, Object> param) {
		int n = mapper.insertSopMgnt(param);
		
		List<Map<String, String>> wkplList = (List<Map<String, String>>) param.get("wkpl_list");
		for(Map<String, String> wkpl : wkplList) {
			wkpl.put("sop_id", (String) param.get("sop_id"));
			wkpl.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));
			mapper.insertSopMgntWkpl(wkpl);
		}
		
		return n;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int updateSopMgnt(Map<String, Object> param) {
		int n = mapper.updateSopMgnt(param);
		
		mapper.deleteSopMgntWkpl(param);
		
		List<Map<String, String>> wkplList = (List<Map<String, String>>) param.get("wkpl_list");
		for(Map<String, String> wkpl : wkplList) {
			wkpl.put("sop_id", (String) param.get("sop_id"));
			wkpl.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));
			mapper.insertSopMgntWkpl(wkpl);
		}
		
		return n;
	}

	@Override
	public int deleteSopMgnt(Map<String, String> param) {
		return mapper.deleteSopMgnt(param);
	}

	@Override
	public String selectSopMgntId(Map<String, String> param) {
		return mapper.selectSopMgntId(param);
	}
	
	@Override
	public List<Map<String, ?>> selectSopMgntBaseWkpl(Map<String, String> param) {		
		return mapper.selectSopMgntBaseWkpl(param);
	}

	@Override
	public int delSop(Map<String, String> param) {
		return mapper.delSop(param);
	}

}
