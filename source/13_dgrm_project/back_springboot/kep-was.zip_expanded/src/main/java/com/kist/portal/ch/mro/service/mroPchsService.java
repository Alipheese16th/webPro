package com.kist.portal.ch.mro.service;

import java.util.List;
import java.util.Map;

public interface mroPchsService {

	public List<Map<String, ?>> selectMroList(Map<String, String> param);

	public List<Map<String, ?>> selectMroPkgList(Map<String, String> param);
	
	public String selectChkDeptYnMro(Map<String, String> param);
	
	public String selectMroPchskey(Map<String, String> param);

	public int insertMroPchs(Map<String, String> param);

	public int insertMroPchsMtrl(Map<String, String> param);
	
	public int updateMroPchsMtrl(Map<String, String> param);

	public int insertMroPchsMtrlSbst(Map<String, String> param);

	public int insertMroPchsGwsgn(Map<String, String> param);

	public int deleteMroPic(Map<String, String> param);

	public int insertMroPic(Map<String, String> param);
	
	public Map<String, String> selectMroPchsDtl(Map<String, String> param);

	public List<Map<String, ?>> selectMroPchsMtrlList(Map<String, String> param);
	
	public Map<String, String> selectMroPchsMtrlDtl(Map<String, String> param);
	
	public List<Map<String, String>> selectMroPchsMtrlSbstDtlList(Map<String, String> param);
	
	public Map<String, String> selectMroPchsGwsgnDtl(Map<String, String> param);
	
	public List<Map<String, String>> selectMroPicDtlList(Map<String, String> param);
	
	public int deleteMroPchs(Map<String, String> param);
	
	public int deleteMroPchsMtrl(Map<String, String> param);
	
	public int deleteMroPchsMtrlSbst(Map<String, String> param);
	
	public int deleteMroPchsGwsgn(Map<String, String> param);
	
	public int insertMroPchsMultiMtrl(Map<String, String> param);
	
	public int deleteMroPchsMultiMtrl(Map<String, String> param);
	
	public List<Map<String, String>> selectMroPchsMultiMtrlList(Map<String, String> param);
	
	public int MroPchsAprvIng(Map<String, Object> param);

	
	//결제 후처리 
	public Map<String, String> selectMroPchsAprvNo(Map<String, String> param);

	//결제 후처리(일괄)
	public List<Map<String, String>> selectMroPchsAprvNoList(Map<String, String> param);
	
	public Map<String, String> selectMroMtrlNo(Map<String, String> param);
	
	public int MroPchsAprvSucs(Map<String, String> param);
	
	public int MroPchsAprvFail(Map<String, String> param);
	
	public int MroPchsAprvCancel(Map<String, String> param);
	
	public Map<String, String> selectRgltChkNo(Map<String, String> param);

	public int MrpPchsAprvSucsInsertMtrl(Map<String, String> param);
	
	public int MrpPchsAprvSucsInsertRgltChk(Map<String, String> param);

	public int MrpPchsAprvSucsInsertRgltChkMtrl(Map<String, String> param);

	public int MrpPchsAprvSucsInsertRgltChkMsds(Map<String, String> param);

	public int MrpPchsAprvSucsInsertRgltChkSbst(Map<String, String> param);
	
	public int MroPchsAprvSucsInsertRgltChkRglt(Map<String, String> param);

	public int MrpPchsAprvSucsInsertRgltChkGwsgn(Map<String, String> param);

	public int MrpPchsAprvSucsInsertRgltChkGwsgnPicg(Map<String, String> param);
	
	public int MrpPchsAprvSucsInsertRgltChkMtrlSite(Map<String, String> param);
	
	public int MrpPchsAprvSucsUpdateRgltChkPchsMtrl(Map<String, String> param);

}
