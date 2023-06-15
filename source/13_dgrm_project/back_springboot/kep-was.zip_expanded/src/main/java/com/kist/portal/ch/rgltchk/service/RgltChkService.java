package com.kist.portal.ch.rgltchk.service;

import java.util.List;
import java.util.Map;

public interface RgltChkService {
	
	public List<Map<String, ?>> selectRgltChkReqList(Map<String, String> param);
	
	public List<Map<String, ?>> selectRgltChkReqMstDtl(Map<String, String> param);
	
	public List<Map<String, ?>> selectRgltChkReqCnstList(Map<String, String> param);
	
	public List<Map<String, ?>> selectRgltChkReqSbstList(Map<String, String> param);
	
	public List<Map<String, ?>> selectRgltChkReqRgltList(Map<String, String> param);
	
	public List<Map<String, ?>> selectRgltChkReqGwsgnList(Map<String, String> param);
	
	public List<Map<String, ?>> selectRgltChkReqGwsgnPicList(Map<String, String> param);
	
	public List<Map<String, ?>> selectRgltChkReqRgltAddList(Map<String, String> param);
	
	public int insertRgltChkReqSbst(Map<String, String> param);
	
	public int updateRgltChkReqSbst(Map<String, String> param);
	
	public int updateRgltChkReqSbstAdd(Map<String, String> param);
	
	public int deleteRgltChkReqSbst(Map<String, String> param);
	
	public int deleteRgltChkReqRglt(Map<String, String> param);
	
	public int insertRgltChkReqRglt(Map<String, String> param);
	
	public Map<String, ?> selectRgltChkReqSbstChk(Map<String, String> param);
	
	public int updateRgltChkReqDtl(Map<String, String> param);
	
	public int updateRgltChkReqDtlApr(Map<String, String> param);
	
	public int insertRgltChkReqRgltSbst(Map<String, String> param);
	
	public int deleteRgltChkReqRgltSbst(Map<String, String> param);
	
	public int insertRgltChkReqGwsgn(Map<String, String> param);
	
	public int deleteRgltChkReqPic(Map<String, String> param);
	
	public int insertRgltChkReqPic(Map<String, String> param);
	
	public List<Map<String, ?>> selectRgltChkList(Map<String, String> param);
	
	public int insertRgltChkInfo(Map<String, String> param);
	
	public int updateRgltChkReqMtrl(Map<String, String> param);
	
	public int updateRgltChkMtrl(Map<String, String> param);
	
	public List<Map<String, ?>> selectRgltChkMstDtl(Map<String, String> param);
	
	public int insertRgltChkRgltList(Map<String, String> param);
	
	public int insertRgltChkRgltSbstF(Map<String, String> param);
	
	public int insertRgltChkPicF(Map<String, String> param);
	
	public int insertRgltChkGwsgnF(Map<String, String> param);
	
	public int insertRgltChkSbstF(Map<String, String> param);
	
	public int insertRgltChkMtrlF(Map<String, String> param);
	
	public List<Map<String, ?>> selectRgltChkRsltInfo(Map<String, String> param);
	
	public int deleteRgltChkRgltSbst(Map<String, String> param);
	
	public int insertRgltChkRgltSbstSave(Map<String, String> param);
	
	public int updateRgltChkStatChg(Map<String, String> param);
	
	public int updateRgltChkAprvChg(Map<String, String> param);
	
	public int insertRgltChkDomaRslt(Map<String, String> param);
	
	public int insertRgltChkCnstF(Map<String, String> param);
	
	public int updateRgltChkReqStatChg(Map<String, String> param);
	
	public int deleteRgltChkCnstAll(Map<String, String> param);
	
	public int deleteRgltChkSbstAll(Map<String, String> param);
	
	public int deleteRgltChkRgltAll(Map<String, String> param);
	
	public int deleteRgltChkRgltSbstAll(Map<String, String> param);
	
	public int deleteRgltChkGwsgnAll(Map<String, String> param);
	
	public int deleteRgltChkPicgAll(Map<String, String> param);
	
	public List<Map<String, ?>> selectRgltChkMsdsInfo(Map<String, String> param);
	
	public int insertRgltChkMsdsInfo(Map<String, String> param);
	
	public List<Map<String, ?>> selectRgltChkReqAuthChk(Map<String, String> param);
	
	public List<Map<String, ?>> selectRgltChkAuthChk(Map<String, String> param);
	
	public List<Map<String, ?>> selectRgltChkReqMailReceiver(Map<String, String> param);
	
	public List<Map<String, ?>> selectRgltChkBizUser(Map<String, String> param);

	public int updateRgltChkDomaRsltReject(Map<String, String> param);

	public int updateRgltChkDomaRsltRejectCancel(Map<String, String> param);

	public int insertRgltChkMsdsF(Map<String, String> param);
	
	public int deleteRgltChkMsdsAll(Map<String, String> param);
	
	public int updateRgltChkReject(Map<String, String> param);
	
	public List<Map<String, ?>> selectRgltChkAprvBasicInfo(Map<String, String> param);
	
	public int deleteRgltChkAprvSbstAll(Map<String, String> param);
	
	public int deleteRgltChkAprvRgltAll(Map<String, String> param);
	
	public int deleteRgltChkAprvRgltSbstAll(Map<String, String> param);
	
	public int deleteRgltChkAprvGwsgnAll(Map<String, String> param);
	
	public int deleteRgltChkAprvPicgAll(Map<String, String> param);
	
	public int deleteRgltChkAprvMsdsAll(Map<String, String> param);
	
	public int updateRgltChkAprvMtrlInfo(Map<String, String> param);
	
	public int updateRgltChkAprvChkInfo(Map<String, String> param);

	public int insertRgltChkAprvSbst(Map<String, String> param);
	
	public int insertRgltChkAprvRgltList(Map<String, String> param);
	
	public int insertRgltChkAprvRgltSbst(Map<String, String> param);
	
	public int insertRgltChkAprvMsds(Map<String, String> param);
	
	public int insertRgltChkAprvGwsgn(Map<String, String> param);
	
	public int insertRgltChkAprvPicg(Map<String, String> param);
	
	public String selectRgltChkVndrKey(Map<String, String> param);
	
	public int insertRgltChkVndrMstData(Map<String, String> param);
	
	public int insertRgltChkMtrlVndrData(Map<String, String> param);
	
	public int insertDngrMtrlMesSend(Map<String, String> param);
	
	public List<Map<String, ?>> selectRgltChkRgltSbstCnt(Map<String, String> param);
	
	public int insertMtrlGwsgnMesSend(Map<String, String> param);
	
	public int updateRgltChkRgltSave(Map<String, String> param);
	
	public int updateReqRgltChkRgltSave(Map<String, String> param);
	
	public int updateRgltChkAprvGwsgn(Map<String, String> param);

	public int updateRgltChkRejectCancel(Map<String, String> tempInfo);

	public int updateRgltChkReqStatChgRejectCancel(Map<String, String> tempInfo);

	public int updatetRgltChkReqException(Map<String, String> tempInfo);

	public int updateRgltExcMtrl(Map<String, String> tempInfo);

	public int updateRgltChkExcReject(Map<String, String> tempInfo);

	public int insertRgltChkException(Map<String, String> tempInfo);

}
