package com.kist.portal.sh.cstprms.service;

import java.util.List;
import java.util.Map;

public interface CstprmsService {
	
	public List<Map<String, ?>> selectOtsrConBsenList(Map<String, String> param);
	
	public List<Map<String, ?>> selectOtsrConBsenDtl(Map<String, String> param);
	
	public List<Map<String, ?>> selectOtsrConBsenOprrList(Map<String, String> param);
	
	public List<Map<String, ?>> selectOtsrConBsenWkplFcstList(Map<String, String> param);
	
	public String selectOtsrConBsenKeyPass(Map<String, String> param);
	
	public String selectOtsrConBsenkey(Map<String, String> param);
	
	public int insertOtsrConBsen(Map<String, String> param);
	
	public int insertOtsrConBsenOprrInfo(Map<String, String> param);
	
	public int insertOtsrConBsenWkplFcstInfo(Map<String, String> param);
	
	public int deleteOtsrConBsenOprrInfo(Map<String, String> param);
	
	public int deleteOtsrConBsenWkplFcstInfo(Map<String, String> param);
	
	public List<Map<String, ?>> selectConOprnPrmsList(Map<String, String> param);
	
	public List<Map<String, ?>> selectConOprnPrmsDtl(Map<String, String> param);
	
	public List<Map<String, ?>> selectConOprnPrmsReqDtl(Map<String, String> param);
	
	public List<Map<String, ?>> selectConOprnPrmsOprn(Map<String, String> param);
	
	public List<Map<String, ?>> selectConOprnPrmsObey(Map<String, String> param);
	
	public List<Map<String, ?>> selectConOprnPrmsOprr(Map<String, String> param);
	
	public List<Map<String, ?>> selectConOprnPrmsOprrSave(Map<String, String> param);
	
	public String selectConOprnPrmsKey(Map<String, String> param);
	
	public String selectConOprnPrmsReqKey(Map<String, String> param);
	
	public int insertConOprnPrmsDtl(Map<String, String> param);
	
	public int insertConOprnPrmsReqDtl(Map<String, String> param);
	
	public int deleteConOprnPrmsOprn(Map<String, String> param);
	
	public int deleteConOprnPrmsObey(Map<String, String> param);
	
	public int deleteConOprnPrmsOprr(Map<String, String> param);
	
	public int insertConOprnPrmsOprn(Map<String, String> param);
	
	public int insertConOprnPrmsObey(Map<String, String> param);
	
	public int insertConOprnPrmsOprr(Map<String, String> param);
	
	public int updateConOprnPrmsReqPrint(Map<String, String> param);
	
	public int updateConOprnUndo(Map<String, String> param);
	
	public List<Map<String, ?>> selectConOprnPrmsOprrCurrent(Map<String, String> param);
	
	public List<Map<String, ?>> selectConOprnPrmsBeReqData(Map<String, String> param);
	
	public List<Map<String, ?>> selectConOprnPrmsCmplList(Map<String, String> param);
	
	public List<Map<String, ?>> selectConOprnPrmsCmplData(Map<String, String> param);
	
	public int insertConOprnPrmsCmplData(Map<String, String> param);
	
	public List<Map<String, ?>> selectConOprnPrmsCmplChk(Map<String, String> param);
	
	public List<Map<String, ?>> selectConOprnPrmsCmplChkDtl(Map<String, String> param);
	
	public List<Map<String, ?>> selectConOprnPrmsAuthChk(Map<String, String> param);
	
	public List<Map<String, ?>> selectConOprnPrmsMobileList(Map<String, String> param);
	
	public List<Map<String, ?>> selectConOprnPrmsDtlMobile(Map<String, String> param);
	
	public List<Map<String, ?>> selectConOprnPrmsDtlChkListMobile(Map<String, String> param);
	
	public int insertConOprnPrmsChkDataMobile(Map<String, String> param);
	
	public int insertConOprnPrmsChkListDataMobile(Map<String, String> param);
	
	public int insertConOprnPrmsChkNextDataMobile(Map<String, String> param);
	
	public int insertConOprnPrmsChkListNextDataMobile(Map<String, String> param);
	
	public int updateConOprnPrmsMainStatMobile(Map<String, String> param);
	
	public List<Map<String, ?>> selectConOprnPrmsMainMobile(Map<String, String> param);
	
	public int updateConOprnPrmsMainStat(Map<String, String> param);
	
	public int updateConOprnPrmsAprv(Map<String, String> param);
	
	public List<Map<String, ?>> selectConOprnPrmsAprvInfo(Map<String, String> param);
	
	public int updateConOprnPrmsCmplAprv(Map<String, String> param);
	
	public List<Map<String, ?>> selectConOprnPrmsCmplAprvInfo(Map<String, String> param);
	
	public List<Map<String, ?>> selectConOprnPrmsObeyCmpl(Map<String, String> param);
	
	public List<Map<String, ?>> selectConOprnPrmsCmplMultiAprvList(Map<String, Object> param);
	
	public int updateConOprnPrmsCmplMultiAprv(Map<String, Object> schParam);
	
	public int updateConOprnPrmsMainStatAprv(Map<String, String> param);
	
	public int updateConOprnPrmsCmplAprvAf(Map<String, String> param);
	
	public List<Map<String, ?>> selectConOprnPrmsMobileAuthChk(Map<String, String> param);
	
	public List<Map<String, ?>> selectDashTestList(Map<String, String> param);
	
	public int updateConOprnPrmsReqDel(Map<String, String> param);

	public String selectConOprnPrmsmobileDataExistYn(Map<String, String> param);
	
	public Map<String, String> selectHolidayChk(Map<String, String> param);
}
