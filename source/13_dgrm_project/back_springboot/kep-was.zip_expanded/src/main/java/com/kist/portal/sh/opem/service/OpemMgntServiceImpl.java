package com.kist.portal.sh.opem.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.sh.opem.mapper.OpemMgntMapper;

@Service
public class OpemMgntServiceImpl implements OpemMgntService {

	@Autowired(required=true)
	OpemMgntMapper mapper;

	@Override
	public List<Map<String, ?>> selectOpemMgntList(Map<String, String> param) {		
		return mapper.selectOpemMgntList(param);
	}
	
	@Override
	public Map<String, ?> selectOpemMgnt(Map<String, Object> param) {
		Map<String, Object> map = mapper.selectOpemMgnt(param);
		map.put("rslt_list", mapper.selectOpemMgntRslt(param));
		
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int insertOpemMgnt(Map<String, Object> param) {
		int n = mapper.insertOpemMgnt(param);
		
		List<Map<String, String>> rsltList = (List<Map<String, String>>) param.get("rslt_list");
		for(Map<String, String> rslt : rsltList) {
			rslt.put("wkpl_id", (String) param.get("wkpl_id"));
			rslt.put("wem_year", (String) param.get("wem_year"));
			rslt.put("wem_sn", String.valueOf(param.get("wem_sn")));
			rslt.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));
			mapper.insertOpemMgntRslt(rslt);
		}
		
		return n;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int updateOpemMgnt(Map<String, Object> param) {
		int n = mapper.updateOpemMgnt(param);
		
		mapper.deleteOpemMgntRslt(param);
		
		List<Map<String, String>> rsltList = (List<Map<String, String>>) param.get("rslt_list");
		for(Map<String, String> rslt : rsltList) {
			rslt.put("wkpl_id", (String) param.get("wkpl_id"));
			rslt.put("wem_year", (String) param.get("wem_year"));
			rslt.put("wem_sn", String.valueOf(param.get("wem_sn")));
			rslt.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));
			mapper.insertOpemMgntRslt(rslt);
		}
		
		return n;
	}
	
	@Override
	public int updateAppr(Map<String, Object> param) {
		return mapper.updateAppr(param);
	}

	@Override
	public int deleteOpemMgnt(Map<String, String> param) {
		return mapper.deleteOpemMgnt(param);
	}

	@Override
	public List<Map<String, ?>> selectOpemMgntAprvDtl(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.selectOpemMgntAprvDtl(param);
	}

	@Override
	public int updateOpemMgntAprvAf(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.updateOpemMgntAprvAf(param);
	}

}
