package com.kist.portal.sh.rgltvltn.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.sh.rgltvltn.mapper.RgltVltnMapper;

@Service
public class RgltVltnServiceImpl implements RgltVltnService {

	@Autowired(required=true)
	RgltVltnMapper mapper;

	@Override
	public List<Map<String, ?>> selectRgltVltnList(Map<String, String> param) {	
//		param.put("sDate", param.get("sDate").toString().replaceAll("-", ""));
//		param.put("eDate", param.get("eDate").toString().replaceAll("-", ""));
		
		return mapper.selectRgltVltnList(param);
	}
	
	@Override
	public Map<String, ?> selectRgltVltn(Map<String, String> param) {
		return mapper.selectRgltVltn(param);
	}

	@Override
	public int insertRgltVltn(Map<String, Object> param) {
		param.put("rglt_vltn_chk_dt", param.get("rglt_vltn_chk_dt").toString().replaceAll("-", ""));
		return mapper.insertRgltVltn(param);
	}

	@Override
	public int updateRgltVltn(Map<String, Object> param) {
		param.put("rglt_vltn_chk_dt", param.get("rglt_vltn_chk_dt").toString().replaceAll("-", ""));
		return  mapper.updateRgltVltn(param);
	}
	
	@Override
	public int updateRgltVltnComplete(Map<String, Object> param) {
		return  mapper.updateRgltVltnComplete(param);
	}

	@Override
	public int deleteRgltVltn(Map<String, String> param) {
		return mapper.deleteRgltVltn(param);
	}

	@Override
	public String selectRgltVltnId(Map<String, String> param) {
		return mapper.selectRgltVltnId(param);
	}

	@Override
	public Map<String, ?> selectRgltVltnComplete(Map<String, Object> param) {
		return mapper.selectRgltVltnComplete(param);
	}

	@Override
	public List<Map<String, ?>> selectRgltVltnAprvDtl(Map<String, Object> param) {
		return mapper.selectRgltVltnAprvDtl(param);
	}

	@Override
	public int updateRgltVltnAprvAf(Map<String, Object> param) {
		return mapper.updateRgltVltnAprvAf(param);
	}

	@Override
	public int delRglt(Map<String, String> param) {
		return mapper.delRglt(param);
	}
}
