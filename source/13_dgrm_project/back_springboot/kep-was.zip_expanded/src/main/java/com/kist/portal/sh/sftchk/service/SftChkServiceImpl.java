package com.kist.portal.sh.sftchk.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.sh.sftchk.mapper.SftChkMapper;

@Service
public class SftChkServiceImpl implements SftChkService {

	@Autowired
	SftChkMapper mapper;
	
	@Override
	public List<Map<String, ?>> selectOprbTgtChkList(Map<String, Object> param) {
		return mapper.selectOprbTgtChkList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectOprbChkList(Map<String, Object> param) {
		return mapper.selectOprbChkList(param);
	}

	@Override
	public String selectOprbChkKey(Map<String, Object> param) {
		return mapper.selectOprbChkKey(param);
	}

	@Override
	public int insertOprbChkTgt(Map<String, Object> param) {
		return mapper.insertOprbChkTgt(param);
	}

	@Override
	public int updateOprbChkTgt(Map<String, Object> param) {
		return mapper.updateOprbChkTgt(param);
	}

	@Override
	public int insertOprbChkTgtEqp(Map<String, Object> param) {
		return mapper.insertOprbChkTgtEqp(param);
	}

	@Override
	public int updateOprbChkTgtEqp(Map<String, Object> param) {
		return mapper.updateOprbChkTgtEqp(param);
	}

	@Override
	public int deleteOprbChkTgtEqp(Map<String, Object> param) {
		return mapper.deleteOprbChkTgtEqp(param);
	}

	@Override
	public List<Map<String, ?>> selectOprbChkTgtMgnt(Map<String, Object> param) {
		return mapper.selectOprbChkTgtMgnt(param);
	}

	@Override
	public List<Map<String, ?>> selectOprbChkTgtEqp(Map<String, Object> param) {
		return mapper.selectOprbChkTgtEqp(param);
	}
	
	@Override
	public List<Map<String, ?>> selectOprbChkMgnt(Map<String, Object> param) {
		return mapper.selectOprbChkMgnt(param);
	}

	@Override
	public List<Map<String, ?>> selectOprbChkEqp(Map<String, Object> param) {
		return mapper.selectOprbChkEqp(param);
	}

	@Override
	public List<Map<String, ?>> selectOprbChkTgtAuth(Map<String, Object> param) {
		return mapper.selectOprbChkTgtAuth(param);
	}

	@Override
	public List<Map<String, ?>> selectOprbChkAuth(Map<String, Object> param) {
		return mapper.selectOprbChkAuth(param);
	}

	@Override
	public List<Map<String, ?>> selectOprbChkBasicRglt(Map<String, Object> param) {
		return mapper.selectOprbChkBasicRglt(param);
	}

	@Override
	public List<Map<String, ?>> selectOprbChkCrinRslt(Map<String, Object> param) {
		return mapper.selectOprbChkCrinRslt(param);
	}

	@Override
	public List<Map<String, ?>> selectOprbChkRsltList(Map<String, Object> param) {
		return mapper.selectOprbChkRsltList(param);
	}

	@Override
	public String selectOprbCrinKey(Map<String, Object> param) {
		return mapper.selectOprbCrinKey(param);
	}

	@Override
	public int insertOprbChkCrinRslt(Map<String, Object> param) {
		return mapper.insertOprbChkCrinRslt(param);
	}

	@Override
	public int updateOprbChkCrinRslt(Map<String, Object> param) {
		return mapper.updateOprbChkCrinRslt(param);
	}

	@Override
	public int updateOprbChkBasicRglt(Map<String, Object> param) {
		return mapper.updateOprbChkBasicRglt(param);
	}

	@Override
	public int updateOprbChkRsltList(Map<String, Object> param) {
		return mapper.updateOprbChkRsltList(param);
	}

	@Override
	public int deleteOprbChkCrinRslt(Map<String, Object> param) {
		return mapper.deleteOprbChkCrinRslt(param);
	}

	@Override
	public List<Map<String, ?>> selectCircTgtChkList(Map<String, Object> param) {
		return mapper.selectCircTgtChkList(param);
	}

	@Override
	public List<Map<String, ?>> selectWkChkTgtAuth(Map<String, Object> param) {
		return mapper.selectWkChkTgtAuth(param);
	}

	@Override
	public List<Map<String, ?>> selectWknsChkTgtMgnt(Map<String, Object> param) {
		return mapper.selectWknsChkTgtMgnt(param);
	}

	@Override
	public List<Map<String, ?>> selectWknsChkTgtAtndList(Map<String, Object> param) {
		return mapper.selectWknsChkTgtAtndList(param);
	}

	@Override
	public int updateWknsChkTgt(Map<String, Object> param) {
		return mapper.updateWknsChkTgt(param);
	}

	@Override
	public int insertWknsTgtAtndList(Map<String, Object> param) {
		return mapper.insertWknsTgtAtndList(param);
	}

	@Override
	public int updateWknsTgtAtndList(Map<String, Object> param) {
		return mapper.updateWknsTgtAtndList(param);
	}

	@Override
	public int deleteWknsTgtAtndList(Map<String, Object> param) {
		return mapper.deleteWknsTgtAtndList(param);
	}

	@Override
	public String selectWknsChkTgtKey(Map<String, Object> param) {
		return mapper.selectWknsChkTgtKey(param);
	}

	@Override
	public int insertWknsChkTgt(Map<String, Object> param) {
		return mapper.insertWknsChkTgt(param);
	}

	@Override
	public List<Map<String, ?>> selectWknsChkList(Map<String, Object> param) {
		return mapper.selectWknsChkList(param);
	}

	@Override
	public List<Map<String, ?>> selectWkChkAuth(Map<String, Object> param) {
		return mapper.selectWkChkAuth(param);
	}

	@Override
	public List<Map<String, ?>> selectWknsChkMgnt(Map<String, Object> param) {
		return mapper.selectWknsChkMgnt(param);
	}

	@Override
	public List<Map<String, ?>> selectWknsChkAtndList(Map<String, Object> param) {
		return mapper.selectWknsChkAtndList(param);
	}

	@Override
	public List<Map<String, ?>> selectWknsChkResult(Map<String, Object> param) {
		return mapper.selectWknsChkResult(param);
	}

	@Override
	public int insertWknsChkCrinRslt(Map<String, Object> param) {
		return mapper.insertWknsChkCrinRslt(param);
	}

	@Override
	public int updateWknsChkRglt(Map<String, Object> param) {
		return mapper.updateWknsChkRglt(param);
	}

	@Override
	public int insertWknsAtndList(Map<String, Object> param) {
		return mapper.insertWknsAtndList(param);
	}

	@Override
	public int updateWknsAtndList(Map<String, Object> param) {
		return mapper.updateWknsAtndList(param);
	}

	@Override
	public int deleteWknsAtndList(Map<String, Object> param) {
		return mapper.deleteWknsAtndList(param);
	}

	@Override
	public int updateWknsChkRsltList(Map<String, Object> param) {
		return mapper.updateWknsChkRsltList(param);
	}

	@Override
	public List<Map<String, ?>> selectJntTgtChkList(Map<String, Object> param) {
		return mapper.selectJntTgtChkList(param);
	}

	@Override
	public List<Map<String, ?>> selectJnChkAuth(Map<String, Object> param) {
		return mapper.selectJnChkAuth(param);
	}

	@Override
	public List<Map<String, ?>> selectJntChkTgtMgnt(Map<String, Object> param) {
		return mapper.selectJntChkTgtMgnt(param);
	}

	@Override
	public List<Map<String, ?>> selectJntChkTgtAtndList(Map<String, Object> param) {
		return mapper.selectJntChkTgtAtndList(param);
	}

	@Override
	public List<Map<String, ?>> selectJntTgtDtList(Map<String, Object> param) {
		return mapper.selectJntTgtDtList(param);
	}

	@Override
	public int updateJntChkTgt(Map<String, Object> param) {
		return mapper.updateJntChkTgt(param);
	}

	@Override
	public String selectJntChkTgtKey(Map<String, Object> param) {
		return mapper.selectJntChkTgtKey(param);
	}

	@Override
	public int insertJntChkTgt(Map<String, Object> param) {
		return mapper.insertJntChkTgt(param);
	}

	@Override
	public int insertJntTgtAtndList(Map<String, Object> param) {
		return mapper.insertJntTgtAtndList(param);
	}

	@Override
	public int updateJntTgtAtndList(Map<String, Object> param) {
		return mapper.updateJntTgtAtndList(param);
	}

	@Override
	public int deleteJntTgtAtndList(Map<String, Object> param) {
		return mapper.deleteJntTgtAtndList(param);
	}

	@Override
	public int insertJntTgtDtList(Map<String, Object> param) {
		return mapper.insertJntTgtDtList(param);
	}

	@Override
	public int updateJntTgtDtList(Map<String, Object> param) {
		return mapper.updateJntTgtDtList(param);
	}

	@Override
	public int deleteJntTgtDtList(Map<String, Object> param) {
		return mapper.deleteJntTgtDtList(param);
	}

	@Override
	public List<Map<String, ?>> selectCoprChkList(Map<String, Object> param) {
		return mapper.selectCoprChkList(param);
	}

	@Override
	public List<Map<String, ?>> selectCoprChkAuth(Map<String, Object> param) {
		return mapper.selectCoprChkAuth(param);
	}
	
	@Override		
	public List<Map<String, ?>> selectCrinList(Map<String, String> param) {	
		return mapper.selectCrinList(param);
	}	
		
	@Override	
	public List<Map<String, ?>> selectCrinBasicDtl(Map<String, String> param) {	
		return mapper.selectCrinBasicDtl(param);
	}	
		
	@Override	
	public List<Map<String, ?>> selectCrinActnDtl(Map<String, String> param) {	
		return mapper.selectCrinActnDtl(param);
	}	
		
	@Override	
	public String selectCrinkey(Map<String, String> param) {	
		return mapper.selectCrinkey(param);
	}	
		
	@Override	
	public int insertCrinBasicDtl(Map<String, String> param) {	
		return mapper.insertCrinBasicDtl(param);
	}	
		
	@Override	
	public int insertCrinActnDtl(Map<String, String> param) {	
		return mapper.insertCrinActnDtl(param);
	}

	@Override
	public List<Map<String, ?>> selectWknsChkCrinRslt(Map<String, Object> param) {
		return mapper.selectWknsChkCrinRslt(param);
	}

	@Override
	public List<Map<String, ?>> selectSftChkListCnt(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.selectSftChkListCnt(param);
	}

	@Override
	public List<Map<String, ?>> selectCrinChkAuth(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.selectCrinChkAuth(param);
	}

	@Override
	public int updateOprbChkStatus(Map<String, String> param) {
		// TODO Auto-generated method stub
		return mapper.updateOprbChkStatus(param);
	}

	@Override
	public int updateWknsChkStatus(Map<String, String> param) {
		// TODO Auto-generated method stub
		return mapper.updateWknsChkStatus(param);
	}

	@Override
	public List<Map<String, ?>> selectCoprChkMgnt(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.selectCoprChkMgnt(param);
	}

	@Override
	public List<Map<String, ?>> selectJntChkAtndList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.selectJntChkAtndList(param);
	}

	@Override
	public int updateJntChkMgnt(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.updateJntChkMgnt(param);
	}

	@Override
	public int insertJntAtndList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.insertJntAtndList(param);
	}

	@Override
	public int updateJntAtndList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.updateJntAtndList(param);
	}

	@Override
	public int deleteJntAtndList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.deleteJntAtndList(param);
	}

	@Override
	public int insertCrrDtCrinMb(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.insertCrrDtCrinMb(param);
	}

	@Override
	public int updateCoprAprvInfo(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.updateCoprAprvInfo(param);
	}

	@Override
	public List<Map<String, ?>> selectCoprChkAprvDtl(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.selectCoprChkAprvDtl(param);
	}

	@Override
	public int updateCoprChkAprvAf(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.updateCoprChkAprvAf(param);
	}

	@Override
	public int updateTourAprvInfo(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.updateTourAprvInfo(param);
	}

	@Override
	public int updateTourChkAprvAf(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.updateTourChkAprvAf(param);
	}

	@Override
	public List<Map<String, ?>> selectTourChkAprvDtl(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return mapper.selectTourChkAprvDtl(param);
	}

	@Override
	public List<Map<String, ?>> selectLabssaftyChkMgntList(Map<String, Object> param) {
		return mapper.selectLabssaftyChkMgntList(param);
	}

	@Override
	public List<Map<String, ?>> selectLabsSaftyChkDtl(Map<String, String> param) {
		return mapper.selectLabsSaftyChkDtl(param);
	}

	@Override
	public String selectLabsSaftyChkDtlkey(Map<String, String> param) {
		return mapper.selectLabsSaftyChkDtlkey(param);
	}

	@Override
	public int insertLabsSaftyChkDtl(Map<String, String> param) {
		return mapper.insertLabsSaftyChkDtl(param);
	}
	
	@Override
	public String selectLabssftChkKey(Map<String, Object> param) {
		return mapper.selectLabssftChkKey(param);
	}
	@Override
	public int insertLabssftChk(Map<String, Object> param) {
		return mapper.insertLabssftChk(param);
	}
	@Override
	public int updateLabsChkTgt(Map<String, Object> param) {
		return mapper.updateLabsChkTgt(param);
	}
	@Override
	public int insertLabsTgtList(Map<String, Object> param) {
		return mapper.insertLabsTgtList(param);
	}
	@Override
	public int updateLabsTgtList(Map<String, Object> param) {
		return mapper.updateLabsTgtList(param);
	}
	@Override
	public int deleteLabsTgtList(Map<String, Object> param) {
		return mapper.deleteLabsTgtList(param);
	}
	@Override
	public List<Map<String, ?>> selectlabsChkMgnt(Map<String, Object> param) {
		return mapper.selectlabsChkMgnt(param);
	}
	@Override
	public List<Map<String, ?>> selectLabsChkMgntList(Map<String, Object> param) {
		return mapper.selectLabsChkMgntList(param);
	}
	@Override
	public List<Map<String, ?>> selectlabsChkTgt(Map<String, Object> param) {
		return mapper.selectlabsChkTgt(param);
	}

	@Override
	public String selectSftOprnChkKeyVal(Map<String, Object> param) {
		return mapper.selectSftOprnChkKeyVal(param);
	}
	
	@Override
	public String selectSftWknsChkKeyVal(Map<String, Object> param) {
		return mapper.selectSftWknsChkKeyVal(param);
	}

	@Override
	public int updateOprnExcept(Map<String, Object> param) {
		return mapper.updateOprnExcept(param);
	}

	@Override
	public String selectMbKeyChk(Map<String, Object> param) {
		return mapper.selectMbKeyChk(param);
	}

}
