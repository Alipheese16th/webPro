package com.kist.portal.ch.rgltchk.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.ch.rgltchk.mapper.RgltChkMapper;

@Service
public class RgltChkServiceImpl implements RgltChkService {
	
	@Autowired
	RgltChkMapper mapper;

	@Override
	public List<Map<String, ?>> selectRgltChkReqList(Map<String, String> param) {
		return mapper.selectRgltChkReqList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectRgltChkReqMstDtl(Map<String, String> param) {
		return mapper.selectRgltChkReqMstDtl(param);
	}
	
	@Override
	public List<Map<String, ?>> selectRgltChkReqCnstList(Map<String, String> param) {
		return mapper.selectRgltChkReqCnstList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectRgltChkReqSbstList(Map<String, String> param) {
		return mapper.selectRgltChkReqSbstList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectRgltChkReqRgltList(Map<String, String> param) {
		return mapper.selectRgltChkReqRgltList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectRgltChkReqGwsgnList(Map<String, String> param) {
		return mapper.selectRgltChkReqGwsgnList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectRgltChkReqGwsgnPicList(Map<String, String> param) {
		return mapper.selectRgltChkReqGwsgnPicList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectRgltChkReqRgltAddList(Map<String, String> param) {
		return mapper.selectRgltChkReqRgltAddList(param);
	}
	
	@Override
	public int insertRgltChkReqSbst(Map<String, String> param) {
		return mapper.insertRgltChkReqSbst(param);
	}
	
	@Override
	public int updateRgltChkReqSbst(Map<String, String> param) {
		return mapper.updateRgltChkReqSbst(param);
	}
	
	@Override
	public int updateRgltChkReqSbstAdd(Map<String, String> param) {
		return mapper.updateRgltChkReqSbstAdd(param);
	}
	
	@Override
	public int deleteRgltChkReqSbst(Map<String, String> param) {
		return mapper.deleteRgltChkReqSbst(param);
	}
	
	@Override
	public int deleteRgltChkReqRglt(Map<String, String> param) {
		return mapper.deleteRgltChkReqRglt(param);
	}
	
	@Override
	public int insertRgltChkReqRglt(Map<String, String> param) {
		return mapper.insertRgltChkReqRglt(param);
	}
	
	@Override
	public Map<String, ?> selectRgltChkReqSbstChk(Map<String, String> param) {
		return mapper.selectRgltChkReqSbstChk(param);
	}
	
	@Override
	public int updateRgltChkReqDtl(Map<String, String> param) {
		return mapper.updateRgltChkReqDtl(param);
	}
	
	@Override
	public int updateRgltChkReqDtlApr(Map<String, String> param) {
		return mapper.updateRgltChkReqDtlApr(param);
	}
	
	@Override
	public int insertRgltChkReqRgltSbst(Map<String, String> param) {
		return mapper.insertRgltChkReqRgltSbst(param);
	}
	
	@Override
	public int deleteRgltChkReqRgltSbst(Map<String, String> param) {
		return mapper.deleteRgltChkReqRgltSbst(param);
	}
	
	@Override
	public int insertRgltChkReqGwsgn(Map<String, String> param) {
		return mapper.insertRgltChkReqGwsgn(param);
	}
	
	@Override
	public int deleteRgltChkReqPic(Map<String, String> param) {
		return mapper.deleteRgltChkReqPic(param);
	}
	
	@Override
	public int insertRgltChkReqPic(Map<String, String> param) {
		return mapper.insertRgltChkReqPic(param);
	}
	
	@Override
	public List<Map<String, ?>> selectRgltChkList(Map<String, String> param) {
		return mapper.selectRgltChkList(param);
	}
	
	@Override
	public int insertRgltChkInfo(Map<String, String> param) {
		return mapper.insertRgltChkInfo(param);
	}
	
	@Override
	public int updateRgltChkReqMtrl(Map<String, String> param) {
		return mapper.updateRgltChkReqMtrl(param);
	}
	
	@Override
	public int updateRgltChkMtrl(Map<String, String> param) {
		return mapper.updateRgltChkMtrl(param);
	}
	
	@Override
	public List<Map<String, ?>> selectRgltChkMstDtl(Map<String, String> param) {
		return mapper.selectRgltChkMstDtl(param);
	}
	
	@Override
	public int insertRgltChkRgltList(Map<String, String> param) {
		return mapper.insertRgltChkRgltList(param);
	}
	
	@Override
	public int insertRgltChkRgltSbstF(Map<String, String> param) {
		return mapper.insertRgltChkRgltSbstF(param);
	}
	
	@Override
	public int insertRgltChkPicF(Map<String, String> param) {
		return mapper.insertRgltChkPicF(param);
	}
	
	@Override
	public int insertRgltChkGwsgnF(Map<String, String> param) {
		return mapper.insertRgltChkGwsgnF(param);
	}
	
	@Override
	public int insertRgltChkSbstF(Map<String, String> param) {
		return mapper.insertRgltChkSbstF(param);
	}
	
	@Override
	public int insertRgltChkMtrlF(Map<String, String> param) {
		return mapper.insertRgltChkMtrlF(param);
	}
	
	@Override
	public List<Map<String, ?>> selectRgltChkRsltInfo(Map<String, String> param) {
		return mapper.selectRgltChkRsltInfo(param);
	}
	
	@Override
	public int deleteRgltChkRgltSbst(Map<String, String> param) {
		return mapper.deleteRgltChkRgltSbst(param);
	}
	
	@Override
	public int insertRgltChkRgltSbstSave(Map<String, String> param) {
		return mapper.insertRgltChkRgltSbstSave(param);
	}
	
	@Override
	public int updateRgltChkStatChg(Map<String, String> param) {
		return mapper.updateRgltChkStatChg(param);
	}
	
	@Override
	public int updateRgltChkAprvChg(Map<String, String> param) {
		return mapper.updateRgltChkAprvChg(param);
	}
	
	@Override
	public int insertRgltChkDomaRslt(Map<String, String> param) {
		return mapper.insertRgltChkDomaRslt(param);
	}
	
	@Override
	public int insertRgltChkCnstF(Map<String, String> param) {
		return mapper.insertRgltChkCnstF(param);
	}
	
	@Override
	public int updateRgltChkReqStatChg(Map<String, String> param) {
		return mapper.updateRgltChkReqStatChg(param);
	}

	@Override
	public int updateRgltChkReqStatChgRejectCancel(Map<String, String> param) {
		return mapper.updateRgltChkReqStatChgRejectCancel(param);
	}
	
	@Override
	public int deleteRgltChkCnstAll(Map<String, String> param) {
		return mapper.deleteRgltChkCnstAll(param);
	}
	
	@Override
	public int deleteRgltChkSbstAll(Map<String, String> param) {
		return mapper.deleteRgltChkSbstAll(param);
	}
	
	@Override
	public int deleteRgltChkRgltAll(Map<String, String> param) {
		return mapper.deleteRgltChkRgltAll(param);
	}
	
	@Override
	public int deleteRgltChkRgltSbstAll(Map<String, String> param) {
		return mapper.deleteRgltChkRgltSbstAll(param);
	}
	
	@Override
	public int deleteRgltChkGwsgnAll(Map<String, String> param) {
		return mapper.deleteRgltChkGwsgnAll(param);
	}
	
	@Override
	public int deleteRgltChkPicgAll(Map<String, String> param) {
		return mapper.deleteRgltChkPicgAll(param);
	}
	
	@Override
	public List<Map<String, ?>> selectRgltChkMsdsInfo(Map<String, String> param) {
		return mapper.selectRgltChkMsdsInfo(param);
	}
	
	@Override
	public int insertRgltChkMsdsInfo(Map<String, String> param) {
		return mapper.insertRgltChkMsdsInfo(param);
	}
	
	@Override
	public List<Map<String, ?>> selectRgltChkReqAuthChk(Map<String, String> param) {
		return mapper.selectRgltChkReqAuthChk(param);
	}
	
	@Override
	public List<Map<String, ?>> selectRgltChkAuthChk(Map<String, String> param) {
		return mapper.selectRgltChkAuthChk(param);
	}
	
	@Override
	public List<Map<String, ?>> selectRgltChkReqMailReceiver(Map<String, String> param) {
		return mapper.selectRgltChkReqMailReceiver(param);
	}
	
	@Override
	public List<Map<String, ?>> selectRgltChkBizUser(Map<String, String> param) {
		return mapper.selectRgltChkBizUser(param);
	}
	
	@Override
	public int updateRgltChkDomaRsltReject(Map<String, String> param) {
		return mapper.updateRgltChkDomaRsltReject(param);
	}

	@Override
	public int updateRgltChkDomaRsltRejectCancel(Map<String, String> param) {
		return mapper.updateRgltChkDomaRsltRejectCancel(param);
	}

	@Override
	public int insertRgltChkException(Map<String, String> param) {
		mapper.insertRgltChkExceptionLang(param);
		return mapper.insertRgltChkExceptionCd(param);
	}

	@Override
	public int insertRgltChkMsdsF(Map<String, String> param) {
		return mapper.insertRgltChkMsdsF(param);
	}
	
	@Override
	public int deleteRgltChkMsdsAll(Map<String, String> param) {
		return mapper.deleteRgltChkMsdsAll(param);
	}
	
	@Override
	public int updateRgltChkReject(Map<String, String> param) {
		return mapper.updateRgltChkReject(param);
	}

	@Override
	public int updateRgltChkRejectCancel(Map<String, String> param) {
		return mapper.updateRgltChkRejectCancel(param);
	}
	
	@Override
	public List<Map<String, ?>> selectRgltChkAprvBasicInfo(Map<String, String> param) {
		return mapper.selectRgltChkAprvBasicInfo(param);
	}
	
	@Override
	public int deleteRgltChkAprvSbstAll(Map<String, String> param) {
		return mapper.deleteRgltChkAprvSbstAll(param);
	}
	
	@Override
	public int deleteRgltChkAprvRgltAll(Map<String, String> param) {
		return mapper.deleteRgltChkAprvRgltAll(param);
	}
	
	@Override
	public int deleteRgltChkAprvRgltSbstAll(Map<String, String> param) {
		return mapper.deleteRgltChkAprvRgltSbstAll(param);
	}
	
	@Override
	public int deleteRgltChkAprvGwsgnAll(Map<String, String> param) {
		return mapper.deleteRgltChkAprvGwsgnAll(param);
	}
	
	@Override
	public int deleteRgltChkAprvPicgAll(Map<String, String> param) {
		return mapper.deleteRgltChkAprvPicgAll(param);
	}
	
	@Override
	public int deleteRgltChkAprvMsdsAll(Map<String, String> param) {
		return mapper.deleteRgltChkAprvMsdsAll(param);
	}
	
	@Override
	public int updateRgltChkAprvMtrlInfo(Map<String, String> param) {
		return mapper.updateRgltChkAprvMtrlInfo(param);
	}
	
	@Override
	public int updateRgltChkAprvChkInfo(Map<String, String> param) {
		return mapper.updateRgltChkAprvChkInfo(param);
	}
	
	@Override
	public int insertRgltChkAprvSbst(Map<String, String> param) {
		return mapper.insertRgltChkAprvSbst(param);
	}
	
	@Override
	public int insertRgltChkAprvRgltList(Map<String, String> param) {
		return mapper.insertRgltChkAprvRgltList(param);
	}
	
	@Override
	public int insertRgltChkAprvRgltSbst(Map<String, String> param) {
		return mapper.insertRgltChkAprvRgltSbst(param);
	}
	
	@Override
	public int insertRgltChkAprvMsds(Map<String, String> param) {
		return mapper.insertRgltChkAprvMsds(param);
	}
	
	@Override
	public int insertRgltChkAprvGwsgn(Map<String, String> param) {
		return mapper.insertRgltChkAprvGwsgn(param);
	}
	
	@Override
	public int insertRgltChkAprvPicg(Map<String, String> param) {
		return mapper.insertRgltChkAprvPicg(param);
	}

	@Override
	public String selectRgltChkVndrKey(Map<String, String> param) {
		return mapper.selectRgltChkVndrKey(param);
	}

	@Override
	public int insertRgltChkVndrMstData(Map<String, String> param) {
		return mapper.insertRgltChkVndrMstData(param);
	}
	
	@Override
	public int insertRgltChkMtrlVndrData(Map<String, String> param) {
		return mapper.insertRgltChkMtrlVndrData(param);
	}

	@Override
	public int insertDngrMtrlMesSend(Map<String, String> param) {
		return mapper.insertDngrMtrlMesSend(param);
	}

	@Override
	public List<Map<String, ?>> selectRgltChkRgltSbstCnt(Map<String, String> param) {
		return mapper.selectRgltChkRgltSbstCnt(param);
	}
	
	@Override
	public int insertMtrlGwsgnMesSend(Map<String, String> param) {
		return mapper.insertMtrlGwsgnMesSend(param);
	}
	
	@Override
	public int updateRgltChkRgltSave(Map<String, String> param) {
		return mapper.updateRgltChkRgltSave(param);
	}

	@Override
	public int updateReqRgltChkRgltSave(Map<String, String> param) {
		return mapper.updateReqRgltChkRgltSave(param);
	}
	
	@Override
	public int updateRgltChkAprvGwsgn(Map<String, String> param) {
		return mapper.updateRgltChkAprvGwsgn(param);
	}

	@Override
	public int updatetRgltChkReqException(Map<String, String> tempInfo) {
		return mapper.updatetRgltChkReqException(tempInfo);
	}

	@Override
	public int updateRgltExcMtrl(Map<String, String> tempInfo) {
		return mapper.updateRgltExcMtrl(tempInfo);
	}

	@Override
	public int updateRgltChkExcReject(Map<String, String> tempInfo) {
		return mapper.updateRgltChkExcReject(tempInfo);
	}
	
}
