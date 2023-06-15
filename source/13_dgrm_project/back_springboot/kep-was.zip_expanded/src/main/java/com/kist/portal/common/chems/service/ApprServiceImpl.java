package com.kist.portal.common.chems.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.common.chems.mapper.ApprMapper;

@Service
public class ApprServiceImpl implements ApprService {

	@Autowired
	ApprMapper mapper;
	
	@Override
	public List<Map<String, ?>> selectApprList(Map<String, String> param) {
		return mapper.selectApprList(param);
	}

	@Override
	public List<Map<String, ?>> selectApprDtl(Map<String, String> param) {
		return mapper.selectApprDtl(param);
	}
	
	@Override
	public List<Map<String, ?>> selectApprDtlRecv(Map<String, String> param) {
		return mapper.selectApprDtlRecv(param);
	}

	@Override
	public int insertApprDtlRecv(Map<String, String> param) {
		return mapper.insertApprDtlRecv(param);
	}
	
	@Override
	public int updateApprDtl(Map<String, String> param) {
		return mapper.updateApprDtl(param);
	}

	@Override
	public int updateApprRecvDtl(Map<String, String> param) {
		return mapper.updateApprRecvDtl(param);
	}
}
