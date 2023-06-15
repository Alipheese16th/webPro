package com.kist.portal.sh.mtng.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.sh.mtng.mapper.MtngMapper;

@Service
public class MtngServiceImpl implements MtngService {

	@Autowired(required=true)
	MtngMapper mapper;

	@Override
	public List<Map<String, ?>> selectMtngList(Map<String, String> param) {
//		param.put("sDate", param.get("sDate").toString().replaceAll("-", ""));
//		param.put("eDate", param.get("eDate").toString().replaceAll("-", ""));
		
		return mapper.selectMtngList(param);
	}
	
	@Override
	public Map<String, ?> selectMtng(Map<String, Object> param) {
		Map<String, Object> map = mapper.selectMtng(param);
		map.put("atnd_list", mapper.selectMtngAtnd(param));
		map.put("sbjc_list", mapper.selectMtngSbjc(param));
		
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int insertMtng(Map<String, Object> param) {
		param.put("mtng_dt", param.get("mtng_dt").toString().replaceAll("-", ""));
		param.put("mtng_bgn_hm", param.get("mtng_bgn_hm").toString().replaceAll(":", ""));
		param.put("mtng_end_hm", param.get("mtng_end_hm").toString().replaceAll(":", ""));
		int n = mapper.insertMtng(param);
		
		int i = 1;
		List<Map<String, String>> atndList = (List<Map<String, String>>) param.get("atnd_list");
		for(Map<String, String> atnd : atndList) {
			atnd.put("mtng_id", (String) param.get("mtng_id"));
			atnd.put("mtng_atnd_sn", String.valueOf(i++));
			atnd.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));
			mapper.insertMtngAtnd(atnd);
		}
		
		i = 1;
		List<Map<String, String>> sbjcList = (List<Map<String, String>>) param.get("sbjc_list");
		for(Map<String, String> sbjc : sbjcList) {
			sbjc.put("mtng_id", (String) param.get("mtng_id"));
			sbjc.put("mtng_sbjc_sn", String.valueOf(i++));
			sbjc.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));
			mapper.insertMtngSbjc(sbjc);
		}
		
		return n;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int updateMtng(Map<String, Object> param) {
		param.put("mtng_dt", param.get("mtng_dt").toString().replaceAll("-", ""));
		param.put("mtng_bgn_hm", param.get("mtng_bgn_hm").toString().replaceAll(":", ""));
		param.put("mtng_end_hm", param.get("mtng_end_hm").toString().replaceAll(":", ""));
		int n = mapper.updateMtng(param);
		
		mapper.deleteMtngAtnd(param);
		mapper.deleteMtngSbjc(param);
		
		int i = 1;
		List<Map<String, String>> atndList = (List<Map<String, String>>) param.get("atnd_list");
		for(Map<String, String> atnd : atndList) {
			atnd.put("mtng_id", (String) param.get("mtng_id"));
			atnd.put("mtng_atnd_sn", String.valueOf(i++));
			atnd.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));
			mapper.insertMtngAtnd(atnd);
		}
		
		i = 1;
		List<Map<String, String>> sbjcList = (List<Map<String, String>>) param.get("sbjc_list");
		for(Map<String, String> sbjc : sbjcList) {
			sbjc.put("mtng_id", (String) param.get("mtng_id"));
			sbjc.put("mtng_sbjc_sn", String.valueOf(i++));
			sbjc.put("SESS_USR_ID", (String) param.get("SESS_USR_ID"));
			mapper.insertMtngSbjc(sbjc);
		}
		
		return n;
	}
	
	@Override
	public int updateAppr(Map<String, Object> param) {
		return mapper.updateAppr(param);
	}

	@Override
	public int deleteMtng(Map<String, String> param) {
		return mapper.deleteMtng(param);
	}

	@Override
	public String selectMtngId(Map<String, String> param) {
		return mapper.selectMtngId(param);
	}

	@Override
	public List<Map<String, ?>> selectMtngAprvDtl(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.selectMtngAprvDtl(param);
	}

	@Override
	public int updateMtngAprvAf(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.updateMtngAprvAf(param);
	}

	@Override
	public int delMtng(Map<String, String> param) {
		return mapper.delMtng(param);
	}
}
