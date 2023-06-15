package com.kist.portal.sh.opem.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.sh.opem.mapper.OpemActnMapper;

@Service
public class OpemActnServiceImpl implements OpemActnService {

	@Autowired(required=true)
	OpemActnMapper mapper;

	@Override
	public List<Map<String, ?>> selectOpemActnList(Map<String, String> param) {	
		return mapper.selectOpemActnList(param);
	}
	
	@Override
	public Map<String, ?> selectOpemActn(Map<String, Object> param) {
		return mapper.selectOpemActn(param);
	}

	@Override
	public int insertOpemActn(Map<String, Object> param) {
		return mapper.insertOpemActn(param);
	}

	@Override
	public int updateOpemActn(Map<String, Object> param) {
		return  mapper.updateOpemActn(param);
	}
	
	@Override
	public int updateAppr(Map<String, Object> param) {
		return mapper.updateAppr(param);
	}

	@Override
	public int deleteOpemActn(Map<String, String> param) {
		return mapper.deleteOpemActn(param);
	}

	@Override
	public String selectOpemActnId(Map<String, String> param) {
		return mapper.selectOpemActnId(param);
	}

	@Override
	public List<Map<String, ?>> selectOpemActnAprvDtl(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.selectOpemActnAprvDtl(param);
	}

	@Override
	public int updateOpemActnAprvAf(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.updateOpemActnAprvAf(param);
	}
}
