package com.kist.portal.sh.cstprms.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.sh.cstprms.mapper.CstprmsMapper;

@Service
public class CstprmsServiceImpl implements CstprmsService {
	
	@Autowired
	CstprmsMapper mapper;

	@Override
	public List<Map<String, ?>> selectOtsrConBsenList(Map<String, String> param) {
		return mapper.selectOtsrConBsenList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectOtsrConBsenDtl(Map<String, String> param) {
		return mapper.selectOtsrConBsenDtl(param);
	}
	
	@Override
	public List<Map<String, ?>> selectOtsrConBsenOprrList(Map<String, String> param) {
		return mapper.selectOtsrConBsenOprrList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectOtsrConBsenWkplFcstList(Map<String, String> param) {
		return mapper.selectOtsrConBsenWkplFcstList(param);
	}
	
	@Override
	public String selectOtsrConBsenKeyPass(Map<String, String> param) {
		return mapper.selectOtsrConBsenKeyPass(param);
	}
	
	@Override
	public String selectOtsrConBsenkey(Map<String, String> param) {
		return mapper.selectOtsrConBsenkey(param);
	}
	
	@Override
	public int insertOtsrConBsen(Map<String, String> param) {
		return mapper.insertOtsrConBsen(param);
	}
	
	@Override
	public int insertOtsrConBsenOprrInfo(Map<String, String> param) {
		return mapper.insertOtsrConBsenOprrInfo(param);
	}
	
	@Override
	public int insertOtsrConBsenWkplFcstInfo(Map<String, String> param) {
		return mapper.insertOtsrConBsenWkplFcstInfo(param);
	}
	
	@Override
	public int deleteOtsrConBsenOprrInfo(Map<String, String> param) {
		return mapper.deleteOtsrConBsenOprrInfo(param);
	}
	
	@Override
	public int deleteOtsrConBsenWkplFcstInfo(Map<String, String> param) {
		return mapper.deleteOtsrConBsenWkplFcstInfo(param);
	}
	
	@Override
	public List<Map<String, ?>> selectConOprnPrmsList(Map<String, String> param) {
		return mapper.selectConOprnPrmsList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectConOprnPrmsDtl(Map<String, String> param) {
		return mapper.selectConOprnPrmsDtl(param);
	}
	
	@Override
	public List<Map<String, ?>> selectConOprnPrmsReqDtl(Map<String, String> param) {
		return mapper.selectConOprnPrmsReqDtl(param);
	}
	
	@Override
	public List<Map<String, ?>> selectConOprnPrmsOprn(Map<String, String> param) {
		return mapper.selectConOprnPrmsOprn(param);
	}
	
	@Override
	public List<Map<String, ?>> selectConOprnPrmsObey(Map<String, String> param) {
		return mapper.selectConOprnPrmsObey(param);
	}
	
	@Override
	public List<Map<String, ?>> selectConOprnPrmsOprr(Map<String, String> param) {
		return mapper.selectConOprnPrmsOprr(param);
	}
	
	@Override
	public List<Map<String, ?>> selectConOprnPrmsOprrSave(Map<String, String> param) {
		return mapper.selectConOprnPrmsOprr(param);
	}
	
	@Override
	public String selectConOprnPrmsKey(Map<String, String> param) {
		return mapper.selectConOprnPrmsKey(param);
	}
	
	@Override
	public String selectConOprnPrmsReqKey(Map<String, String> param) {
		return mapper.selectConOprnPrmsReqKey(param);
	}
	
	@Override
	public int insertConOprnPrmsDtl(Map<String, String> param) {
		return mapper.insertConOprnPrmsDtl(param);
	}
	
	@Override
	public int insertConOprnPrmsReqDtl(Map<String, String> param) {
		return mapper.insertConOprnPrmsReqDtl(param);
	}
	
	@Override
	public int deleteConOprnPrmsOprn(Map<String, String> param) {
		return mapper.deleteConOprnPrmsOprn(param);
	}
	
	@Override
	public int deleteConOprnPrmsObey(Map<String, String> param) {
		return mapper.deleteConOprnPrmsObey(param);
	}
	
	@Override
	public int deleteConOprnPrmsOprr(Map<String, String> param) {
		return mapper.deleteConOprnPrmsOprr(param);
	}
	
	@Override
	public int insertConOprnPrmsOprn(Map<String, String> param) {
		return mapper.insertConOprnPrmsOprn(param);
	}
	
	@Override
	public int insertConOprnPrmsObey(Map<String, String> param) {
		return mapper.insertConOprnPrmsObey(param);
	}
	
	@Override
	public int insertConOprnPrmsOprr(Map<String, String> param) {
		return mapper.insertConOprnPrmsOprr(param);
	}
	
	@Override
	public int updateConOprnPrmsReqPrint(Map<String, String> param) {
		return mapper.updateConOprnPrmsReqPrint(param);
	}
	
	@Override
	public int updateConOprnUndo(Map<String, String> param) {
		return mapper.updateConOprnUndo(param);
	}
	
	@Override
	public List<Map<String, ?>> selectConOprnPrmsOprrCurrent(Map<String, String> param) {
		return mapper.selectConOprnPrmsOprrCurrent(param);
	}
	
	@Override
	public List<Map<String, ?>> selectConOprnPrmsBeReqData(Map<String, String> param) {
		return mapper.selectConOprnPrmsBeReqData(param);
	}
	
	@Override
	public List<Map<String, ?>> selectConOprnPrmsCmplList(Map<String, String> param) {
		return mapper.selectConOprnPrmsCmplList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectConOprnPrmsCmplData(Map<String, String> param) {
		return mapper.selectConOprnPrmsCmplData(param);
	}
	
	@Override
	public int insertConOprnPrmsCmplData(Map<String, String> param) {
		return mapper.insertConOprnPrmsCmplData(param);
	}
	
	@Override
	public List<Map<String, ?>> selectConOprnPrmsCmplChk(Map<String, String> param) {
		return mapper.selectConOprnPrmsCmplChk(param);
	}
	
	@Override
	public List<Map<String, ?>> selectConOprnPrmsCmplChkDtl(Map<String, String> param) {
		return mapper.selectConOprnPrmsCmplChkDtl(param);
	}
	
	@Override
	public List<Map<String, ?>> selectConOprnPrmsAuthChk(Map<String, String> param) {
		return mapper.selectConOprnPrmsAuthChk(param);
	}
	
	@Override
	public List<Map<String, ?>> selectConOprnPrmsMobileList(Map<String, String> param) {
		return mapper.selectConOprnPrmsMobileList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectConOprnPrmsDtlMobile(Map<String, String> param) {
		return mapper.selectConOprnPrmsDtlMobile(param);
	}
	
	@Override
	public List<Map<String, ?>> selectConOprnPrmsDtlChkListMobile(Map<String, String> param) {
		return mapper.selectConOprnPrmsDtlChkListMobile(param);
	}
	
	@Override
	public int insertConOprnPrmsChkDataMobile(Map<String, String> param) {
		return mapper.insertConOprnPrmsChkDataMobile(param);
	}
	
	@Override
	public int insertConOprnPrmsChkListDataMobile(Map<String, String> param) {
		return mapper.insertConOprnPrmsChkListDataMobile(param);
	}
	
	@Override
	public int insertConOprnPrmsChkNextDataMobile(Map<String, String> param) {
		return mapper.insertConOprnPrmsChkNextDataMobile(param);
	}
	
	@Override
	public int insertConOprnPrmsChkListNextDataMobile(Map<String, String> param) {
		return mapper.insertConOprnPrmsChkListNextDataMobile(param);
	}
	
	@Override
	public int updateConOprnPrmsMainStatMobile(Map<String, String> param) {
		return mapper.updateConOprnPrmsMainStatMobile(param);
	}
	
	@Override
	public List<Map<String, ?>> selectConOprnPrmsMainMobile(Map<String, String> param) {
		return mapper.selectConOprnPrmsMainMobile(param);
	}
	
	@Override
	public int updateConOprnPrmsMainStat(Map<String, String> param) {
		return mapper.updateConOprnPrmsMainStat(param);
	}
	
	@Override
	public int updateConOprnPrmsAprv(Map<String, String> param) {
		return mapper.updateConOprnPrmsAprv(param);
	}
	
	@Override
	public List<Map<String, ?>> selectConOprnPrmsAprvInfo(Map<String, String> param) {
		return mapper.selectConOprnPrmsAprvInfo(param);
	}
	
	@Override
	public int updateConOprnPrmsCmplAprv(Map<String, String> param) {
		return mapper.updateConOprnPrmsCmplAprv(param);
	}
	
	@Override
	public List<Map<String, ?>> selectConOprnPrmsCmplAprvInfo(Map<String, String> param) {
		return mapper.selectConOprnPrmsCmplAprvInfo(param);
	}
	
	@Override
	public List<Map<String, ?>> selectConOprnPrmsObeyCmpl(Map<String, String> param) {
		return mapper.selectConOprnPrmsObeyCmpl(param);
	}
	
	@Override
	public List<Map<String, ?>> selectConOprnPrmsCmplMultiAprvList(Map<String, Object> param) {
		return mapper.selectConOprnPrmsCmplMultiAprvList(param);
	}
	
	@Override
	public int updateConOprnPrmsCmplMultiAprv(Map<String, Object> param) {
		return mapper.updateConOprnPrmsCmplMultiAprv(param);
	}
	
	@Override
	public int updateConOprnPrmsMainStatAprv(Map<String, String> param) {
		return mapper.updateConOprnPrmsMainStatAprv(param);
	}
	
	@Override
	public int updateConOprnPrmsCmplAprvAf(Map<String, String> param) {
		return mapper.updateConOprnPrmsCmplAprvAf(param);
	}
	
	@Override
	public List<Map<String, ?>> selectConOprnPrmsMobileAuthChk(Map<String, String> param) {
		return mapper.selectConOprnPrmsMobileAuthChk(param);
	}
	
	@Override
	public List<Map<String, ?>> selectDashTestList(Map<String, String> param) {
		return mapper.selectDashTestList(param);
	}
	
	@Override
	public int updateConOprnPrmsReqDel(Map<String, String> param) {
		return mapper.updateConOprnPrmsReqDel(param);
	}
	
	@Override
	public String selectConOprnPrmsmobileDataExistYn(Map<String, String> param) {
		return mapper.selectConOprnPrmsmobileDataExistYn(param);
	}
	
	@Override
	public Map<String, String> selectHolidayChk(Map<String, String> param) {
		return mapper.selectHolidayChk(param);
	}
}
