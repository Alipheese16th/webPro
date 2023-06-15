package com.kist.portal.sh.sftchk.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SftChkMapper {

	public List<Map<String, ?>> selectOprbTgtChkList(Map<String, Object> param);
	
	public List<Map<String, ?>> selectOprbChkList(Map<String, Object> param);
	
	public String selectOprbChkKey(Map<String, Object> param);
	
	public int insertOprbChkTgt(Map<String, Object> param);
	
	public int updateOprbChkTgt(Map<String, Object> param);

	public int insertOprbChkTgtEqp(Map<String, Object> param);
	
	public int updateOprbChkTgtEqp(Map<String, Object> param);
	
	public int deleteOprbChkTgtEqp(Map<String, Object> param);
	
	public List<Map<String, ?>> selectOprbChkTgtMgnt(Map<String, Object> param);
	
	public List<Map<String, ?>> selectOprbChkTgtEqp(Map<String, Object> param);
	
	public List<Map<String, ?>> selectOprbChkMgnt(Map<String, Object> param);
	
	public List<Map<String, ?>> selectOprbChkEqp(Map<String, Object> param);
	
	public List<Map<String, ?>> selectOprbChkTgtAuth(Map<String, Object> param);
	
	public List<Map<String, ?>> selectOprbChkAuth(Map<String, Object> param);
	
	public List<Map<String, ?>> selectOprbChkBasicRglt(Map<String, Object> param);
	
	public List<Map<String, ?>> selectOprbChkCrinRslt(Map<String, Object> param);
	
	public List<Map<String, ?>> selectOprbChkRsltList(Map<String, Object> param);
	
	public String selectOprbCrinKey(Map<String, Object> param);
	
	public int insertOprbChkCrinRslt(Map<String, Object> param);
	
	public int updateOprbChkCrinRslt(Map<String, Object> param);
	
	public int updateOprbChkBasicRglt(Map<String, Object> param);
	
	public int updateOprbChkRsltList(Map<String, Object> param);
	
	public int deleteOprbChkCrinRslt(Map<String, Object> param);
	
	public List<Map<String, ?>> selectCircTgtChkList(Map<String, Object> param);
	
	public List<Map<String, ?>> selectWkChkTgtAuth(Map<String, Object> param);
	
	public List<Map<String, ?>> selectWknsChkTgtMgnt(Map<String, Object> param);
	
	public List<Map<String, ?>> selectWknsChkTgtAtndList(Map<String, Object> param);
	
	public int updateWknsChkTgt(Map<String, Object> param);
	
	public int insertWknsTgtAtndList(Map<String, Object> param);
	
	public int updateWknsTgtAtndList(Map<String, Object> param);
	
	public int deleteWknsTgtAtndList(Map<String, Object> param);
	
	public String selectWknsChkTgtKey(Map<String, Object> param);
	
	public int insertWknsChkTgt(Map<String, Object> param);
	
	public List<Map<String, ?>> selectWknsChkList(Map<String, Object> param);
	
	public List<Map<String, ?>> selectWkChkAuth(Map<String, Object> param);
	
	public List<Map<String, ?>> selectWknsChkMgnt(Map<String, Object> param);
	
	public List<Map<String, ?>> selectWknsChkAtndList(Map<String, Object> param);
	
	public List<Map<String, ?>> selectWknsChkResult(Map<String, Object> param);
	
	public int insertWknsChkCrinRslt(Map<String, Object> param);
	
	public int updateWknsChkRglt(Map<String, Object> param);
	
	public int insertWknsAtndList(Map<String, Object> param);
	
	public int updateWknsAtndList(Map<String, Object> param);
	
	public int deleteWknsAtndList(Map<String, Object> param);
	
	public int updateWknsChkRsltList(Map<String, Object> param);
	
	public List<Map<String, ?>> selectJntTgtChkList(Map<String, Object> param);
	
	public List<Map<String, ?>> selectJnChkAuth(Map<String, Object> param);
	
	public List<Map<String, ?>> selectJntChkTgtMgnt(Map<String, Object> param);
	
	public List<Map<String, ?>> selectJntChkTgtAtndList(Map<String, Object> param);
	
	public List<Map<String, ?>> selectJntTgtDtList(Map<String, Object> param);
	
	public int updateJntChkTgt(Map<String, Object> param);
	
	public String selectJntChkTgtKey(Map<String, Object> param);
	
	public int insertJntChkTgt(Map<String, Object> param);
	
	public int insertJntTgtAtndList(Map<String, Object> param);
	
	public int updateJntTgtAtndList(Map<String, Object> param);
	
	public int deleteJntTgtAtndList(Map<String, Object> param);
	
	public int insertJntTgtDtList(Map<String, Object> param);
	
	public int updateJntTgtDtList(Map<String, Object> param);
	
	public int deleteJntTgtDtList(Map<String, Object> param);
	
	public List<Map<String, ?>> selectCoprChkList(Map<String, Object> param);
	
	public List<Map<String, ?>> selectCoprChkAuth(Map<String, Object> param);
	
	public List<Map<String, ?>> selectCrinList(Map<String, String> param);	
	
	public List<Map<String, ?>> selectCrinBasicDtl(Map<String, String> param);
	
	public List<Map<String, ?>> selectCrinActnDtl(Map<String, String> param);
	
	public String selectCrinkey(Map<String, String> param);
	
	public int insertCrinBasicDtl(Map<String, String> param);
	
	public int insertCrinActnDtl(Map<String, String> param);
	
	public List<Map<String, ?>> selectWknsChkCrinRslt(Map<String, Object> param);
	
	public List<Map<String, ?>> selectSftChkListCnt(Map<String, Object> param);
	
	public List<Map<String, ?>> selectCrinChkAuth(Map<String, Object> param);

	public int updateOprbChkStatus(Map<String, String> param);
	
	public int updateWknsChkStatus(Map<String, String> param);
	
	public List<Map<String, ?>> selectCoprChkMgnt(Map<String, Object> param);
	
	public List<Map<String, ?>> selectJntChkAtndList(Map<String, Object> param);
	
	public int updateJntChkMgnt(Map<String, Object> param);
	
	public int insertJntAtndList(Map<String, Object> param);
	
	public int updateJntAtndList(Map<String, Object> param);
	
	public int deleteJntAtndList(Map<String, Object> param);
	
	public int insertCrrDtCrinMb(Map<String, Object> param);
	
	public int updateCoprAprvInfo(Map<String, Object> param);
	
	public List<Map<String, ?>> selectCoprChkAprvDtl(Map<String, Object> param);
	
	public int updateCoprChkAprvAf(Map<String, Object> param);
	
	public int updateTourAprvInfo(Map<String, Object> param);
	
	public int updateTourChkAprvAf(Map<String, Object> param);
	
	public List<Map<String, ?>> selectTourChkAprvDtl(Map<String, Object> param);

	public List<Map<String, ?>> selectLabssaftyChkMgntList(Map<String, Object> param);

	public List<Map<String, ?>> selectLabsSaftyChkDtl(Map<String, String> param);

	public String selectLabsSaftyChkDtlkey(Map<String, String> param);

	public int insertLabsSaftyChkDtl(Map<String, String> param);
	
	public String selectLabssftChkKey(Map<String, Object> param);
	
	public int insertLabssftChk(Map<String, Object> param);
	
	public int updateLabsChkTgt(Map<String, Object> param);
	
	public int insertLabsTgtList(Map<String, Object> param);
	
	public int updateLabsTgtList(Map<String, Object> param);
	
	public int deleteLabsTgtList(Map<String, Object> param);
	
	public List<Map<String, ?>> selectlabsChkMgnt(Map<String, Object> param);
	
	public List<Map<String, ?>> selectLabsChkMgntList(Map<String, Object> param);
	
	public List<Map<String, ?>> selectlabsChkTgt(Map<String, Object> param);
	
	public String selectSftOprnChkKeyVal(Map<String, Object> param);
	
	public String selectSftWknsChkKeyVal(Map<String, Object> param);
	
	public int updateOprnExcept(Map<String, Object> param);
	
	public String selectMbKeyChk(Map<String, Object> param);
	
}
