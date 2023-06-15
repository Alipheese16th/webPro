package com.kist.portal.ch.msds.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface msdsMapper {
	
	public List<Map<String, ?>> selectMsdsList(Map<String, String> param);

	public List<Map<String, ?>> selectMsdsSubmitList(Map<String, String> param);
	
	public List<Map<String, ?>> selectMsdsHstList(Map<String, String> param);

	public List<Map<String, ?>> selectMsdsDtl(Map<String, String> param);
	
	public List<Map<String, ?>> selectMtrlDtl(Map<String, String> param);
	
	public List<Map<String, ?>> selectAlertDtl(Map<String, String> param);
	
	public List<Map<String, ?>> selectRgltDtl(Map<String, String> param);
	
	public List<Map<String, ?>> selectRevDtl(Map<String, String> param);
	
	public List<Map<String, ?>> selectPicDtl(Map<String, String> param);
	
	public List<Map<String, ?>> selectMsdsRevReqList(Map<String, String> param);
	
	public List<Map<String, ?>> selectMsdsPopList(Map<String, String> param);
	
	public List<Map<String, ?>> selectMsdsPopScList(Map<String, String> param);
	
	public List<Map<String, ?>> sltAlMtrlDt(Map<String, String> param);
	
	public List<Map<String, ?>> selectMsdsRevDtl(Map<String, String> param);
	
	public List<Map<String, ?>> selectMsdsChkRslt(Map<String, String> param);
	
	public List<Map<String, ?>> selectMsdsRevBfDtl(Map<String, String> param);
	
	public List<Map<String, ?>> selectAlertBfDtl(Map<String, String> param);
	
	public List<Map<String, ?>> selectPicBfDtl(Map<String, String> param);
	
	public String selectChkDeptYn(Map<String, String> param);
	
	public List<Map<String, ?>> selectRevChkDeptYn(Map<String, String> param);
	
	public String selectChkSttYn(Map<String, String> param);
	
	public String selectMsdsKey(Map<String, String> param);
	
	public int insertMsdsRev(Map<String, String> param);
	
	public int insertMsdsRevAlert(Map<String, String> param);
	
	public int insertMsdsRevPic(Map<String, String> param);
	
	public int updateMsdsRev(Map<String, String> param);

	public int updateMsdsSubmit(Map<String, String> param);
	
	public int updateMsdsRevAlert(Map<String, String> param);
	
	public int deleteMsdsRevPic(Map<String, String> param);
	
	public int updateMsdsRevPic(Map<String, String> param);
	
	public int updateMsdsRevStt(Map<String, String> param);
	
	public int upsertMsdsRevReq(Map<String, String> param);
	
	public int upsertMsdsRevReqAlert(Map<String, String> param);
	
	public int upsertMsdsRevReqPic(Map<String, String> param);
	
	public int deleteMsdsRev(Map<String, String> param);
	
	public int deleteMsdsRevAlert(Map<String, String> param);
	
	public List<Map<String, ?>> selectMsdsRevChkList(Map<String, String> param);
	
	public List<Map<String, ?>> selectMsdsRevChkDtl(Map<String, String> param);
	
	public List<Map<String, ?>> selectAlertChkDtl(Map<String, String> param);
	
	public List<Map<String, ?>> selectPicChkDtl(Map<String, String> param);
	
	public int updateMsdsChkRev(Map<String, String> param);
	
	public int updateMsdsChkRevAlert(Map<String, String> param);
	
	public int updateMsdsChkRevPic(Map<String, String> param);
	
	public int deleteMsdsChkRevPic(Map<String, String> param);
	
	public int updateMsdsRevRjtStt(Map<String, String> param);
	
	public int updateMsdsFinishStt(Map<String, String> param);
	
	public String selectVndrKey(Map<String, String> param);
	
	public int updateVndrMstData(Map<String, String> param);
	
	public int insertVndrMstData(Map<String, String> param);
	
	public int updateMsdsMstData(Map<String, String> param);
	
	public int updateMsdsRevChkStt(Map<String, String> param);
	
	public int updateMsdsAlertMstData(Map<String, String> param);
	
	public int deleteMsdsMstPic(Map<String, String> param);
	
	public int updateMsdsPicMstData(Map<String, String> param);
	
	public List<Map<String, ?>> selectMsdsLawCd(Map<String, String> param);
	
	public List<Map<String, String>> selectMsdsChkUsrList(Map<String, String> param);
	
	public String selectMtrlFileKey(Map<String, String> param);
	
	public int insertMtrlVndrMstData(Map<String, String> param);
}
