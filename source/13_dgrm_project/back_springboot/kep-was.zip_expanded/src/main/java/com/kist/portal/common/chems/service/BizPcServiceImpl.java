package com.kist.portal.common.chems.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.common.chems.mapper.BizPcMapper;

@Service
public class BizPcServiceImpl implements BizPcService {

	@Autowired
	BizPcMapper mapper;
	@Override
	public List<Map<String, ?>> selectBizPcGrpList(Map<String, String> param) {
		return mapper.selectBizPcGrpList(param);
	}

	@Override
	public List<Map<String, ?>> selectBizPcDtlList(Map<String, String> param) {
		return mapper.selectBizPcDtlList(param);

	}

	@Override
	public String selectBizPckey(Map<String, String> param) {
		return mapper.selectBizPckey(param);
	}

	@Override
	public int saveBizPcGrp(Map<String, String> param) {
		return mapper.saveBizPcGrp(param);
	}

	@Override
	public int deleteBizPcGrp(Map<String, String> param) {
		return mapper.deleteBizPcGrp(param);
	}

	@Override
	public int saveBizPc(Map<String, String> param) {
		return mapper.saveBizPc(param);
	}

	@Override
	public int deleteBizPc(Map<String, String> param) {
		return mapper.deleteBizPc(param);
	}

}
