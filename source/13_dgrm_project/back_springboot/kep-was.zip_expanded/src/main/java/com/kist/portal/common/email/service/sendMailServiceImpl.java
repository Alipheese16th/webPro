package com.kist.portal.common.email.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.common.email.mapper.sendMailMapper;

@Service
public class sendMailServiceImpl implements sendMailService {
	
	@Autowired
	sendMailMapper mapper;
	
	@Override
	public int insertMailSenderLogList(Map<String, String> param) {
		return mapper.insertMailSenderLogList(param);
	}
	
	@Override
	public int insertMailReciverLogList(Map<String, String> param) {
		return mapper.insertMailReciverLogList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectEmailData(Map<String, String> param) {
		return mapper.selectEmailData(param);
	}
	
	@Override
	public int selectEmailKey(Map<String, String> param) {
		return mapper.selectEmailKey(param);
	}

}
