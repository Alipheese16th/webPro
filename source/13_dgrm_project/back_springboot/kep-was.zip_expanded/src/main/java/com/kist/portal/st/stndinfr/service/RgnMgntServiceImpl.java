package com.kist.portal.st.stndinfr.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.st.stndinfr.mapper.RgnMgntMapper;

@Service
public class RgnMgntServiceImpl implements RgnMgntService {
	
	@Autowired
	RgnMgntMapper mapper;

	@Override
	public List<Map<String, ?>> selectRgnmgntList(Map<String, Object> param) {
		return mapper.selectRgnmgntList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectRgnmgntCdList(Map<String, Object> param) {
		return mapper.selectRgnmgntCdList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectRgnmgntPopList(Map<String, Object> param) {
		return mapper.selectRgnmgntPopList(param);
	}

	@Override
	public int insertRgnmgntList(Map<String, Object> param) {
		return mapper.insertRgnmgntList(param);
	}

	@Override
	public int updateRgnmgntList(Map<String, Object> param) {
		return mapper.updateRgnmgntList(param);
	}

	@Override
	public int deleteRgnmgntList(Map<String, Object> param) {
		return mapper.deleteRgnmgntList(param);
	}

	@Override
	public String selectRgnmgntFKey(Map<String, Object> param) {
		return mapper.selectRgnmgntFKey(param);
	}
	
	@Override
	public String selectRgnmgntBKey(Map<String, Object> param) {
		return mapper.selectRgnmgntBKey(param);
	}

	@Override
	public List<Map<String, ?>> selectCatMapgtList(Map<String, Object> param) {
		return mapper.selectCatMapgtList(param);
	}

	@Override
	public List<Map<String, ?>> selectRgnmgntLoc(Map<String, Object> param) {
		return mapper.selectRgnmgntLoc(param);
	}

	@Override
	public void updateRgnmgntLoc(Map<String, Object> param) {
		mapper.updateRgnmgntLoc(param);
	}

	@Override
	public void saveLocPoint(Map<String, Object> param) {
		mapper.deleteRgnmgntLoc(param);

		List<Map<String, Object>> locPinList = (List<Map<String, Object>>) param.get("locPinList");
		for (int i = 0; i < locPinList.size(); i++) {
			locPinList.get(i).put("point_reg_sn", i);
			locPinList.get(i).put("crt_usr_id", param.get("crt_usr_id"));
			locPinList.get(i).put("upt_usr_id", param.get("upt_usr_id"));
			mapper.insertRgnmgntLoc(locPinList.get(i));
		}
	}
}
