package com.kist.portal.ch.msds.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.ch.msds.mapper.msdsMapper;

@Service
public class msdsServiceImpl implements msdsService {
	
	@Autowired
	msdsMapper mapper;

	@Override
	public List<Map<String, ?>> selectMsdsList(Map<String, String> param) {
		return mapper.selectMsdsList(param);
	}

	@Override
	public List<Map<String, ?>> selectMsdsSubmitList(Map<String, String> param) {
		return mapper.selectMsdsSubmitList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectMsdsHstList(Map<String, String> param) {
		return mapper.selectMsdsHstList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectMsdsDtl(Map<String, String> param) {
		return mapper.selectMsdsDtl(param);
	}
	
	@Override
	public List<Map<String, ?>> selectMtrlDtl(Map<String, String> param) {
		return mapper.selectMtrlDtl(param);
	}
	
	@Override
	public List<Map<String, ?>> selectAlertDtl(Map<String, String> param) {
		return mapper.selectAlertDtl(param);
	}
	
	@Override
	public List<Map<String, ?>> selectRgltDtl(Map<String, String> param) {
		return mapper.selectRgltDtl(param);
	}
	
	@Override
	public List<Map<String, ?>> selectRevDtl(Map<String, String> param) {
		return mapper.selectRevDtl(param);
	}
	
	@Override
	public List<Map<String, ?>> selectPicDtl(Map<String, String> param) {
		return mapper.selectPicDtl(param);
	}
	
	@Override
	public List<Map<String, ?>> selectMsdsRevReqList(Map<String, String> param) {
		return mapper.selectMsdsRevReqList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectMsdsPopList(Map<String, String> param) {
		return mapper.selectMsdsPopList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectMsdsPopScList(Map<String, String> param) {
		return mapper.selectMsdsPopScList(param);
	}
	
	@Override
	public List<Map<String, ?>> sltAlMtrlDt(Map<String, String> param) {
		return mapper.sltAlMtrlDt(param);
	}
	
	@Override
	public List<Map<String, ?>> selectMsdsRevDtl(Map<String, String> param) {
		return mapper.selectMsdsRevDtl(param);
	}
	
	@Override
	public List<Map<String, ?>> selectMsdsChkRslt(Map<String, String> param) {
		return mapper.selectMsdsChkRslt(param);
	}
	
	@Override
	public List<Map<String, ?>> selectMsdsRevBfDtl(Map<String, String> param) {
		return mapper.selectMsdsRevBfDtl(param);
	}
	
	@Override
	public List<Map<String, ?>> selectAlertBfDtl(Map<String, String> param) {
		return mapper.selectAlertBfDtl(param);
	}
	
	@Override
	public List<Map<String, ?>> selectPicBfDtl(Map<String, String> param) {
		return mapper.selectPicBfDtl(param);
	}
	
	@Override
	public String selectChkDeptYn(Map<String, String> param) {
		return mapper.selectChkDeptYn(param);
	}
	
	@Override
	public String selectMsdsKey(Map<String, String> param) {
		return mapper.selectMsdsKey(param);
	}
	
	@Override
	public List<Map<String, ?>> selectRevChkDeptYn(Map<String, String> param) {
		return mapper.selectRevChkDeptYn(param);
	}
	
	@Override
	public int insertMsdsRev(Map<String, String> param) {
		return mapper.insertMsdsRev(param);
	}
	
	@Override
	public int insertMsdsRevAlert(Map<String, String> param) {
		return mapper.insertMsdsRevAlert(param);
	}
	
	@Override
	public int insertMsdsRevPic(Map<String, String> param) {
		return mapper.insertMsdsRevPic(param);
	}
	
	@Override
	public int updateMsdsRev(Map<String, String> param) {
		return mapper.updateMsdsRev(param);
	}

	@Override
	public int updateMsdsSubmit(Map<String, String> param) {
		return mapper.updateMsdsSubmit(param);
	}
	
	@Override
	public int updateMsdsRevAlert(Map<String, String> param) {
		return mapper.updateMsdsRevAlert(param);
	}
	
	@Override
	public int deleteMsdsRevPic(Map<String, String> param) {
		return mapper.deleteMsdsRevPic(param);
	}
	
	@Override
	public int updateMsdsRevPic(Map<String, String> param) {
		return mapper.updateMsdsRevPic(param);
	}
	
	@Override
	public int updateMsdsRevStt(Map<String, String> param) {
		return mapper.updateMsdsRevStt(param);
	}
	
	@Override
	public int upsertMsdsRevReq(Map<String, String> param) {
		return mapper.upsertMsdsRevReq(param);
	}
	
	@Override
	public int upsertMsdsRevReqAlert(Map<String, String> param) {
		return mapper.upsertMsdsRevReqAlert(param);
	}
	
	@Override
	public int upsertMsdsRevReqPic(Map<String, String> param) {
		return mapper.upsertMsdsRevReqPic(param);
	}
	
	@Override
	public int deleteMsdsRev(Map<String, String> param) {
		return mapper.deleteMsdsRev(param);
	}
	
	@Override
	public int deleteMsdsRevAlert(Map<String, String> param) {
		return mapper.deleteMsdsRevAlert(param);
	}
	
	@Override
	public List<Map<String, ?>> selectMsdsRevChkList(Map<String, String> param) {
		return mapper.selectMsdsRevChkList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectMsdsRevChkDtl(Map<String, String> param) {
		return mapper.selectMsdsRevChkDtl(param);
	}
	
	@Override
	public List<Map<String, ?>> selectAlertChkDtl(Map<String, String> param) {
		return mapper.selectAlertChkDtl(param);
	}
	
	@Override
	public List<Map<String, ?>> selectPicChkDtl(Map<String, String> param) {
		return mapper.selectPicChkDtl(param);
	}
	
	@Override
	public int updateMsdsChkRev(Map<String, String> param) {
		return mapper.updateMsdsChkRev(param);
	}
	
	@Override
	public int updateMsdsChkRevAlert(Map<String, String> param) {
		return mapper.updateMsdsChkRevAlert(param);
	}
	
	@Override
	public int updateMsdsChkRevPic(Map<String, String> param) {
		return mapper.updateMsdsChkRevPic(param);
	}
	
	@Override
	public int deleteMsdsChkRevPic(Map<String, String> param) {
		return mapper.deleteMsdsChkRevPic(param);
	}
	
	@Override
	public int updateMsdsRevRjtStt(Map<String, String> param) {
		return mapper.updateMsdsRevRjtStt(param);
	}
	
	@Override
	public int updateMsdsFinishStt(Map<String, String> param) {
		return mapper.updateMsdsFinishStt(param);
	}
	
	@Override
	public String selectVndrKey(Map<String, String> param) {
		return mapper.selectVndrKey(param);
	}
	
	@Override
	public int updateVndrMstData(Map<String, String> param) {
		return mapper.updateVndrMstData(param);
	}
	
	@Override
	public int insertVndrMstData(Map<String, String> param) {
		return mapper.insertVndrMstData(param);
	}
	
	@Override
	public int updateMsdsMstData(Map<String, String> param) {
		return mapper.updateMsdsMstData(param);
	}
	
	@Override
	public int updateMsdsRevChkStt(Map<String, String> param) {
		return mapper.updateMsdsRevChkStt(param);
	}
	
	@Override
	public int updateMsdsAlertMstData(Map<String, String> param) {
		return mapper.updateMsdsAlertMstData(param);
	}
	
	@Override
	public int deleteMsdsMstPic(Map<String, String> param) {
		return mapper.deleteMsdsMstPic(param);
	}
	
	@Override
	public int updateMsdsPicMstData(Map<String, String> param) {
		return mapper.updateMsdsPicMstData(param);
	}
	
	@Override
	public List<Map<String, ?>> selectMsdsLawCd(Map<String, String> param) {
		return mapper.selectMsdsLawCd(param);
	}
	
	@Override
	public List<Map<String, String>> selectMsdsChkUsrList(Map<String, String> param) {
		return mapper.selectMsdsChkUsrList(param);
	}
	
	@Override
	public String selectMtrlFileKey(Map<String, String> param) {
		return mapper.selectMtrlFileKey(param);
	}
	
	@Override
	public int insertMtrlVndrMstData(Map<String, String> param) {
		return mapper.insertMtrlVndrMstData(param);
	}
}
	