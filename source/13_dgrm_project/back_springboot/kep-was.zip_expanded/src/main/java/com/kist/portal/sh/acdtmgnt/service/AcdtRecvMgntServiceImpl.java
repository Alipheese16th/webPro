package com.kist.portal.sh.acdtmgnt.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.sh.acdtmgnt.mapper.AcdtRecvMgntMapper;

@Service
public class AcdtRecvMgntServiceImpl implements AcdtRecvMgntService {
	
	@Autowired(required=true)
	AcdtRecvMgntMapper mapper;

	// 기본정보 조회
	@Override
	public List<Map<String, Object>> selectAcdtRecvMgntBasic(Map<String, String> param) {
		return mapper.selectAcdtRecvMgntBasic(param);
	}

	// 수신처 부서 목록조회
	@Override
	public List<Map<String, Object>> selectAcdtRecvMgntRcvDeptList(Map<String, String> param) {
		return mapper.selectAcdtRecvMgntRcvDeptList(param);
	}

	// 수신처 인원 목록조회
	@Override
	public List<Map<String, Object>> selectAcdtRecvMgntRcvEmpList(Map<String, String> param) {
		return mapper.selectAcdtRecvMgntRcvEmpList(param);
	}

	// 참조처 부서 목록조회
	@Override
	public List<Map<String, Object>> selectAcdtRecvMgntRefDeptList(Map<String, String> param) {
		return mapper.selectAcdtRecvMgntRefDeptList(param);
	}

	// 참조처 인원 목록조회
	@Override
	public List<Map<String, Object>> selectAcdtRecvMgntRefEmpList(Map<String, String> param) {
		return mapper.selectAcdtRecvMgntRefEmpList(param);
	}
	
	@Override
	public int insertAcdtRcv(Map<String, Object> param) {
		return mapper.insertAcdtRcv(param);
	}
	
	@Override
	public int deleteAcdtRcv(Map<String, Object> param) {
		return mapper.deleteAcdtRcv(param);
	}
}
