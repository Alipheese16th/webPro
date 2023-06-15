package com.kist.portal.common.batch.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BatchMapper {
	
	public int insertBatch(Map<String, String> param);
	
	public int insertTestBatch(Map<String, String> param);
	
	public int updateBatch(Map<String, String> param);
	
	public List<Map<String, ?>> selectUdrusrList(Map<String, String> param);
	
	public List<Map<String, ?>> selectUdrDeptList(Map<String, String> param);
	
	public List<Map<String, ?>> selectUdrAuthList(Map<String, String> param);
	
	public int mergeUdrusrList(Map<String, ?> param);
	
	public int mergeUdrDeptList(Map<String, ?> param);
	
	public int mergeUdrAuthList(Map<String, ?> param);
	
	public int insertUdrauthList(Map<String, ?> param);
	
	public int insertUdrauthListAC(Map<String, ?> param);
	
	public int updateUdrrsltList(Map<String, ?> param);
	
	public int updateUdrDeptRsltList(Map<String, ?> param);
	
	public int updateUdrAuthRsltList(Map<String, ?> param);
	
	public List<Map<String, ?>> selectMtrlMasterList(Map<String, String> param);
	
	public int insertMtrlCd(Map<String, ?> param);
	
	public int insertKoMtrlMlang(Map<String, ?> param);
	
	public int insertEnMtrlMlang(Map<String, ?> param);
	
	public int insertZhMtrlMlang(Map<String, ?> param);
	
	public int insertMtrlMstMtrl(Map<String, ?> param);
	
	public int updateMtrlMstMtrl(Map<String, ?> param);
	
	public int mergeMtrlSiteInfo(Map<String, ?> param);
	
	public String selectMtrlChkKey(Map<String, ?> param);
	
	public int updateMtrlSucc(Map<String, ?> param);
	
	public List<Map<String, String>> selectRgltMtrlList(Map<String, String> param);
	
	public List<Map<String, String>> selectImkAtfileNo(Map<String, String> param);
	
	public int insertRgltMtrl(Map<String, String> param);
	
	public int insertRgltReqMtrl(Map<String, String> param);
	
	public int insertRgltChkMtrl(Map<String, String> param);
	
	public int updateRgltSucc(Map<String, ?> param);
	
	public List<Map<String, String>> selectRgltReqUserList(Map<String, String> param);
	
	public List<Map<String, String>> selectRgltDataList(Map<String, String> param);
	
	public int updateRgltReqBatStt(Map<String, String> param);
	
	public List<Map<String, String>> selectRgltChkUserList(Map<String, Object> param);
	
	public List<Map<String, String>> selectRgltChkDataList(Map<String, String> param);
	
	public int updateRgltChkBatStt(Map<String, String> param);
	
	public List<Map<String, ?>> selectNewMsdsMtrlList(Map<String, String> param);
	
	public String selectAtfileKey(Map<String, Object> param);
	
	public int insertAtfileKoMetaData(Map<String, Object> param);
	
	public int insertAtfileEnMetaData(Map<String, Object> param);
	
	public int insertRgltChkMsds(Map<String, Object> param);
	
	public List<Map<String, ?>> selectMsdsReqList(Map<String, String> param);
	
	public String selectMsdsReqKey(Map<String, Object> param);

	public int updateMtrlMstMsds(Map<String, Object> param);
	
	public int insertRgltMsdsReq(Map<String, Object> param);
	
	public int insertAlertMsds(Map<String, Object> param);
	
	public int insertPicMsds(Map<String, Object> param);

	public int insertRgltMsdsReqTwo(Map<String, Object> param);
	
	public int insertAlertMsdsTwo(Map<String, Object> param);
	
	public int insertPicMsdsTwo(Map<String, Object> param);
	
	public int updateRgltChkSucc(Map<String, Object> param);
	
	public int updateWkplTrn();
	
	public List<Map<String, String>> selectMsdsRgltUsrList(Map<String, String> param);
	
	public List<Map<String, ?>> selectRgltList(Map<String, String> param);

	public List<Map<String, ?>> selectRgltList2(Map<String, String> param);
	
	public List<Map<String, ?>> selectMsdsList(Map<String, String> param);

	public List<Map<String, ?>> selectMsdsList2(Map<String, String> param);
	
	public List<Map<String, Object>> selectWkplElcTrnMng(Map<String, Object> exprInfo);
	
	public List<Map<String, Object>> selectRgltDbList(Map<String, String> param);
	
	public int upsertRgltDbList(Map<String, Object> param);
	
	public int insertRgltDbHist(Map<String, Object> param);
	
	public List<Map<String, Object>> selectRgltChList(Map<String, String> param);
	
	public int upsertRgltChList(Map<String, Object> param);
	
	public int insertRgltChHist(Map<String, Object> param);
	
	public List<Map<String, Object>> selectAponAprvList(Map<String, String> param);
	
	public String selectAprvMethod(Map<String, Object> param);
	
	public int updateAprvStt(Map<String, Object> param);
	
	public int updateAprvRcvStt(Map<String, Object> param);
	
	public List<Map<String, String>> selectAponDelList(Map<String, String> param);
	
	public int deleteAprvData(Map<String, String> param);
	
	public List<Map<String, String>> selectAponAprvdata(Map<String, Object> param);

	public List<Map<String, Object>> selectVndrSapList(Map<String, String> param);
	
	public int upsertVndrList(Map<String, Object> param);
	
	public int updateVndrSapStt(Map<String, Object> param);
	
	public List<Map<String, String>> selectUpsertMtrlList(Map<String, Object> param);
	
	public List<Map<String, String>> selectUpdateMtrlList(Map<String, Object> param);
	
	public List<Map<String, String>> selectDeleteMtrlList(Map<String, Object> param);
	
	public int insertRgltMtrlRechk(Map<String, String> param);
	
	public int insertRgltChkMtrlRechk(Map<String, String> param);
	
	public int insertRgltChkRgltRechk(Map<String, String> param);
	
	public int insertRgltChkRgltSbstRechk(Map<String, String> param);
	
	public int insertRgltChkSbstRechk(Map<String, String> param);
	
	public int insertRgltChkGwsgnRechk(Map<String, String> param);
	
	public int insertRgltChkGwsgnPicg(Map<String, String> param);
	
	public int insertRgltMtrlMsdsRechk(Map<String, String> param);
	
	public List<Map<String, String>> selectMtrlDataList(Map<String, Object> param);
	
	public List<Map<String, Object>> selectMsdstiumData(Map<String, Object> param);
	
	public List<Map<String, Object>> selectMtrlVndrSapList(Map<String, String> param);
	
	public int upsertMtrlVndrList(Map<String, Object> param);
	
	public int updateVndrStt(Map<String, Object> param);

	public int updateMtrlVndrStt(Map<String, Object> param);
	
	public int insertRgltReqMsds(Map<String, String> param);
	
	public int insertImkRgltReqMsds(Map<String, String> param);
	
	public List<Map<String, Object>> selectMsdsTiumList(Map<String, String> param);
	
	public String selectMsdsTiumKoFileKey(Map<String, String> param);
	
	public String selectMsdsTiumEnFileKey(Map<String, String> param);
	
	public int insertMsdsTiumfileMeta(Map<String, String> param);
	
	public int updateMsdsTiumStt(Map<String, String> param);
	
	public String selectMtrlCnstSapKey(Map<String, Object> param);
	
	public int insertMtrlCnstSap(Map<String, Object> param);
	
	public int insertMtrlCnst(Map<String, Object> param);
	
	public int deleteMtrlCnst(Map<String, ?> param);
	
	public int insertRgltChkCnst(Map<String, String> param);
	
	public int insertRgltChkSbst(Map<String, String> param);
	
	public int insertWrkAreaCdGrp(Map<String, Object> param);
	
	public int insertWrkAreaCdGrpMlang(Map<String, Object> param);
	
	public int insertWrkAreaCd(Map<String, Object> param);
	
	public int insertWrkAreaCdMlang(Map<String, Object> param);
	
	public int insertEqupCd(Map<String, Object> param);
	
	public int insertEqupCdMlang(Map<String, Object> param);
	
	public int insertEqupWrkAreaRel(Map<String, Object> param);
	
	public int insertEqupWrkAreaRelT(Map<String, Object> param);
	
	public int insertDlDdData(Map<String, Object> param);
	
	public int insertDlDdEtcData(Map<String, Object> param);
	
	public int updateRohMaTrn(Map<String, Object> param);
	
	public int updateEtcMaTrn(Map<String, Object> param);
	
	public int deleteDlMmData(Map<String, Object> param);
	
	public int insertDlMmData(Map<String, Object> param);
	
	public int insertSbstDlDd(Map<String, Object> param);
	
	public int deleteSbstDlMm(Map<String, Object> param);
	
	public int insertSbstDlMm(Map<String, Object> param);
	
	public int insertMtrlDl(Map<String, Object> param);
	
	public int insertWhWrkAreaCdGrp(Map<String, Object> param);
	
	public int insertWhWrkAreaCdGrpMlang(Map<String, Object> param);
	
	public int insertWhWrkAreaCd(Map<String, Object> param);
	
	public int insertWhWrkAreaCdMlang(Map<String, Object> param);
	
	public int insertWhEqupCd(Map<String, Object> param);
	
	public int insertWhEqupCdMlang(Map<String, Object> param);
	
	public int insertWhEqupWrkAreaRel(Map<String, Object> param);
	
	public int insertWhEqupWrkAreaRelT(Map<String, Object> param);
	
	public int insertWhDdRohData(Map<String, Object> param);
	
	public int insertWhDdHalData(Map<String, Object> param);
	
	public int deleteWhMmData(Map<String, Object> param);
	
	public int insertWhMmData(Map<String, Object> param);
	
	public int insertSbstWhDd(Map<String, Object> param);
	
	public int deleteSbstWhMm(Map<String, Object> param);
	
	public int insertSbstWhMm(Map<String, Object> param);
	
	public int updateWhRohMaTrn(Map<String, Object> param);
	
	public int updateWhHalMaTrn(Map<String, Object> param);
	
	public int insertMtrlWhDl(Map<String, Object> param);
	
	public int insertMtrlSk(Map<String, Object> param);
	
	public int insertSbstSk(Map<String, Object> param);
	
	public int updateMtrlTrnStt(Map<String, Object> param);
	
	public List<Map<String, Object>> selectMtrlDl(Map<String, Object> param);
	
	public List<Map<String, Object>> selectMtrlEtcDl(Map<String, Object> param);
	
	public List<Map<String, Object>> selectMtrlRohWh(Map<String, Object> param);
	
	public List<Map<String, Object>> selectMtrlHalWh(Map<String, Object> param);
	
	public int insertImkRecvData(Map<String, Object> param);
	
	public List<Map<String, Object>> selectImkMtrlList(Map<String, Object> param);
	
	public List<Map<String, String>> selectImkMstYn(Map<String, Object> param);
	
	public int insertImkMtrlMstData(Map<String, Object> param);
	
	public String selectImkMtrlKey(Map<String, Object> param);
	
	public int updateImkMtrlMstTrn(Map<String, Object> param);
	
	public String selectFileKey(Map<String, Object> param);
	
	public int insertImkFileMetaInfo(Map<String, Object> param);
	
	public int updateImkFileMtrlInfo(Map<String, Object> param);
	
	public List<Map<String, String>> selectimkAtfileNoCount(Map<String, String> param);
	
	public List<Map<String, Object>> selectImkMtrlWhList(Map<String, Object> param);
	
	public List<Map<String, Object>> selectImkWhData(Map<String, Object> param);
	
	public List<Map<String, Object>> selectWkplElcTrnExpire(Map<String, String> param);
	
	public int updateImkWhDdData(Map<String, Object> param);
	
	public int deleteWhImkMmData(Map<String, Object> param);
	
	public int insertWhImkMmData(Map<String, Object> param);
	
	public List<Map<String, Object>> selectImkSbstList(Map<String, Object> param);
	
	public int updateImkSbstDlData(Map<String, Object> param);
	
	public int deleteSbstImkWhMm(Map<String, Object> param);
	
	public int insertSbstImkWhMm(Map<String, Object> param);
	
	public int insertImkWhDdData(Map<String, Object> param);
	
	public int insertImkSbstDdData(Map<String, Object> param);
	
	public int updateImkMtrlTrnYn(Map<String, Object> param);
	
	public int insertImkMstData(Map<String, Object> param);
	
	public int insertMroWhDl(Map<String, Object> param);
	
	public List<Map<String, Object>> selectMroMtrlWhList(Map<String, Object> param);
	
	public int insertWhDdMroData(Map<String, Object> param);
	
	public int updateWhMroTrn(Map<String, Object> param);
	
	public List<Map<String, Object>> selectNewSbstList(Map<String, Object> param);
	
	public List<Map<String, String>> selectNewSbstUserList(Map<String, Object> param);
	
	public int insertRgltActAlrm(Map<String, Object> param);
	
	public String selectRgltAlrmKey(Map<String, Object> param);
	
	public int insertRgltActAlrmRecv(Map<String, Object> param);
	
	public int insertRgltActMtrl(Map<String, Object> param);
	
	public List<Map<String, Object>> selectCmrSbstList(Map<String, Object> param);
	
	public List<Map<String, Object>> selectCmrMtrlNo(Map<String, Object> param);
	
	public int insertRgltActAlrmMtrl(Map<String, Object> param);
	
	public List<Map<String, Object>> selectEtcAlrmSbstList(Map<String, Object> param);
	
	public List<Map<String, Object>> selectTonAlrmSbstList(Map<String, Object> param);
	
	public List<Map<String, Object>> selectWhRohYn(Map<String, Object> param);
	
	public int updateCreateWhDd(Map<String, Object> param);

	public int insertCreateWhRohDd(Map<String, Object> param);
	
	public List<Map<String, Object>> selectWhRohMmYn(Map<String, Object> param);
	
	public int updateCreateWhMm(Map<String, Object> param);
	
	public int insertCreateWhRohMm(Map<String, Object> param);
	
	public int updateCreateWhSbDd(Map<String, Object> param);
	
	public List<Map<String, Object>> selectWhRohSbstList(Map<String, Object> param);
	
	public int updateCreateWhSbMm(Map<String, Object> param);
	
	public List<Map<String, Object>> selectWhUpdateList(Map<String, Object> param);
	
	public int updateUpdateWhDd(Map<String, Object> param);
	
	public int updateUpdateWhMm(Map<String, Object> param);
	
	public int updateUpdateWhSbDd(Map<String, Object> param);
	
	public int updateUpdateWhSbMm(Map<String, Object> param);
	
	public int updateDeleteWhDd(Map<String, Object> param);
	
	public int updateDeleteWhMm(Map<String, Object> param);
	
	public int updateDeleteWhSbDd(Map<String, Object> param);
	
	public int updateDeleteWhSbMm(Map<String, Object> param);
	
	public int updateCreateWhHalDd(Map<String, Object> param);
	
	public List<Map<String, Object>> selectWhHalSbstList(Map<String, Object> param);
	
	public int updateCreateWhMroDd(Map<String, Object> param);
	
	public int updateCreateWhMroMm(Map<String, Object> param);
	
	public List<Map<String, Object>> selectWhMroSbstList(Map<String, Object> param);
	
	public int updateCreateWhSbMroDd(Map<String, Object> param);
	
	public int updateCreateWhSbMroMm(Map<String, Object> param);
	
	public int updateImkWhMmData(Map<String, Object> param);
	
	public int updateImkSbstMmData(Map<String, Object> param);
	
	public int updateCreateDlDd(Map<String, Object> param);
	
	public int updateCreateDlMm(Map<String, Object> param);
	
	public List<Map<String, Object>> selectDlRohSbstList(Map<String, Object> param);
	
	public int updateCreateDlSbDd(Map<String, Object> param);
	
	public int updateCreateDlSbMm(Map<String, Object> param);
	
	public List<Map<String, Object>> selectDlUpdateList(Map<String, Object> param);
	
	public int updateUpdateDlDd(Map<String, Object> param);
	
	public int updateUpdateDlMm(Map<String, Object> param);
	
	public int updateUpdateDlSbDd(Map<String, Object> param);
	
	public int updateUpdateDlSbMm(Map<String, Object> param);
	
	public int updateDeleteDlDd(Map<String, Object> param);
	
	public int updateDeleteDlMm(Map<String, Object> param);
	
	public int updateDeleteDlSbDd(Map<String, Object> param);
	
	public int updateDeleteDlSbMm(Map<String, Object> param);
	
	public int updateCreateEtcDlDd(Map<String, Object> param);
	
	public int updateCreateEtcDlMm(Map<String, Object> param);
	
	public List<Map<String, Object>> selectDlEtcSbstList(Map<String, Object> param);
	
	public int updateCreateEtcDlSbDd(Map<String, Object> param);
	
	public int updateCreateEtcDlSbMm(Map<String, Object> param);
	
	public int updateDeleteEtcDlDd(Map<String, Object> param);
	
	public int updateDeleteEtcDlMm(Map<String, Object> param);
	
	public int updateDeleteEtcDlSbDd(Map<String, Object> param);
	
	public int updateDeleteEtcDlSbMm(Map<String, Object> param);

	public List<Map<String, Object>> selectMsdsChkUsrList(Map<String, Object> param);
	
	public List<Map<String, Object>> selectOprbBgnChkTgtList(Map<String, String> param);
	
	public String selectOprbBgnChkKey(Map<String, Object> param);
	
	public int insertOprbBgnChkBasic(Map<String, Object> param);
	
	public int insertOprbBgnChkEqp(Map<String, Object> param);
	
	public List<Map<String, Object>> selectCircChkTgtList(Map<String, String> param);
	
	public String selectCircChkKey(Map<String, Object> param);
	
	public List<Map<String, Object>> selectWeekInfo(Map<String, String> param);
	
	public int insertCircChkBasic(Map<String, Object> param);
	
	public int insertCircChkAtnd(Map<String, Object> param);
	
	public int insertCircChkRslt(Map<String, Object> param);
	
	public List<Map<String, Object>> selectJntChkTgtList(Map<String, String> param);
	
	public List<Map<String, Object>> selectJntPlanChk(Map<String, Object> param);
	
	public String selectJntChkKey(Map<String, Object> param);
	
	public int insertJntChkBasic(Map<String, Object> param);
	
	public int insertJntChkAtnd(Map<String, Object> param);
	
	public List<Map<String, Object>> selectCrinOverDateList(Map<String, String> param);
	
	public List<Map<String, Object>> selectCircOverDateList(Map<String, String> param);
	
	public List<Map<String, Object>> selectJntOverDateList(Map<String, String> param);
	
	public List<Map<String, Object>> selectHmflTgtList(Map<String, Object> param);
	
	public List<Map<String, String>> selectHmflTgtEmpList(Map<String, Object> param);
	
	public List<Map<String, String>> selectHmflMcnDtlBatch(Map<String, Object> param);
	
	public List<Map<String, Object>> selectConOprnPrmsDataBatch(Map<String, Object> param);
	
	public List<Map<String, ?>> selectConOprnPrmsDtlBatch(Map<String, Object> param);
	
	public List<Map<String, ?>> selectConOprnPrmsReqDtlBatch(Map<String, Object> param);
	
	public List<Map<String, ?>> selectConOprnPrmsOprnBatch(Map<String, Object> param);
	
	public List<Map<String, ?>> selectUserInfo(Map<String, Object> param);
	
	public List<Map<String, Object>> selectCircMailList(Map<String, Object> param);
	
	public List<Map<String, Object>> selectJntMailList(Map<String, Object> param);
	
	public List<Map<String, ?>> selectConOprnPrmsOprrSaveBatch(Map<String, Object> param);
	
	public List<Map<String, String>> selectExeHistBatch(Map<String, Object> param);
	
	public int deleteHmexRsltDataT(Map<String, ?> param);
	
	public int deleteHmexTrgpDataT(Map<String, ?> param);
	
	public int deleteHmexRsltDataF(Map<String, ?> param);
	
	public int deleteHmexTrgpDataF(Map<String, ?> param);
	
	public List<Map<String, ?>> selectHolyList(Map<String, String> param);
	
	public int updateHolyList(Map<String, ?> param);
	
	public int mergeHolyList(Map<String, ?> param);
	
	public List<Map<String, Object>> selectAcdtTempSave(Map<String, String> param);	// 사고 보고 및 조사 임시저장 조회
	
	public int updateAcdtTempSave(Map<String, Object> param);	// 사고 보고 및 조사 임시저장 수정처리
	
	public List<Map<String, Object>> selectAcdtInvestReportComple(Map<String, String> param);	// 사고 보고 및 조사 조사보고완료 요청데이터 조회

	public List<Map<String, Object>> selectAcdtDelayList(Map<String, String> param);	// 사고 보고 및 조사 조치 지연 여부 조회

	public int updateAcdtDelayList(Map<String, Object> param);	// 사고 보고 및 조사 조치지연건 수정처리
}
