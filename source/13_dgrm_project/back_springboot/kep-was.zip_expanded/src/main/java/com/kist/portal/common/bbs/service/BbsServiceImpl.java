package com.kist.portal.common.bbs.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.common.bbs.mapper.BbsMapper;

@Service
public class BbsServiceImpl implements BbsService {

	@Autowired
	BbsMapper mapper;

	@Override
	public List<Map<String, ?>> selectNotiList(Map<String, String> param) {
		return mapper.selectNotiList(param);
	}

	@Override
	public int saveNoti(Map<String, String> param) {
		return mapper.saveNoti(param);
	}

	@Override
	public int updateNoti(Map<String, String> param) {
		return mapper.updateNoti(param);
	}
	
	@Override
	public int updateNotiCnt(Map<String, String> param) {
		return mapper.updateNotiCnt(param);
	}

	@Override
	public Map<String, ?> selectNotiDtl(Map<String, String> param){
		return mapper.selectNotiDtl(param);

	}
	
	@Override
	public int deleteNoti(Map<String, String> param) {
		return mapper.deleteNoti(param);
	}
	
	@Override
	public List<Map<String, ?>> selectFaqsList(Map<String, String> param) {
		return mapper.selectFaqsList(param);
	}

	@Override
	public int saveFaqs(Map<String, String> param) {
		return mapper.saveFaqs(param);
	}

	@Override
	public int updateFaqs(Map<String, String> param) {
		return mapper.updateFaqs(param);
	}

	@Override
	public Map<String, ?> selectFaqsDtl(Map<String, String> param){
		return mapper.selectFaqsDtl(param);

	}
	
	@Override
	public int deleteFaqs(Map<String, String> param) {
		return mapper.deleteFaqs(param);
	}
	
	@Override
	public List<Map<String, ?>> selectArchList(Map<String, String> param) {
		return mapper.selectArchList(param);
	}

	@Override
	public int saveArch(Map<String, String> param) {
		return mapper.saveArch(param);
	}

	@Override
	public int updateArch(Map<String, String> param) {
		return mapper.updateArch(param);
	}

	@Override
	public Map<String, ?> selectArchDtl(Map<String, String> param){
		return mapper.selectArchDtl(param);

	}
	
	@Override
	public int deleteArch(Map<String, String> param) {
		return mapper.deleteArch(param);
	}
	
	@Override
	public List<Map<String, ?>> selectNotiYList(Map<String, String> param) {
		return mapper.selectNotiYList(param);
	}

	@Override
	public List<Map<String, ?>> selectManuList(Map<String, String> param) {
		return mapper.selectManuList(param);
	}

	@Override
	public int saveManu(Map<String, String> param) {
		return mapper.saveManu(param);
	}

	@Override
	public int updateManu(Map<String, String> param) {
		return mapper.updateManu(param);
	}

	@Override
	public Map<String, ?> selectManuDtl(Map<String, String> param) {
		return mapper.selectManuDtl(param);
	}
	
	@Override
	public int deleteManu(Map<String, String> param) {
		return mapper.deleteManu(param);
	}

	@Override
	public String selectBbskey(Map<String, String> param) {
		return mapper.selectBbskey(param);

	}

}
