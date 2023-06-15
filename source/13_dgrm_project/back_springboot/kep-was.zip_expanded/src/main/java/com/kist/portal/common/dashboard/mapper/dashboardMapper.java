package com.kist.portal.common.dashboard.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface dashboardMapper {
	
	public List<Map<String, ?>> selectOprnLocList(Map<String, String> param);
	
	public List<Map<String, ?>> selectOprnLocTot(Map<String, String> param);
	
	public String selectWeatRgn(Map<String, String> param);
	
	public List<Map<String, ?>> selectWeatMsgList(Map<String, String> param);
	
	public List<Map<String, ?>> selectWeatMsgChk(Map<String, String> param);
	
	public List<Map<String, ?>> selectWeatMsgChk2(Map<String, String> param);
	
	public int insertWeatMsg(Map<String, String> param);
	
	public int deleteWeatMsg(Map<String, String> param);
	
	public List<Map<String, ?>> selectPlanRsltList(Map<String, String> param);
	
	public List<Map<String, ?>> selectYearConOprnAllList(Map<String, String> param);
	
	public List<Map<String, ?>> selectTrnPerList(Map<String, String> param);
	
	public List<Map<String, ?>> selectYearConOprnTypeList(Map<String, String> param);
	
	public List<Map<String, ?>> selectYearConCheckList(Map<String, String> param);
	
	public List<Map<String, ?>> selectYearCrinCheckList(Map<String, String> param);
	
	public List<Map<String, ?>> selectYearConCheckGbList(Map<String, String> param);
	
	public List<Map<String, ?>> selectYearCrinGbCheckList(Map<String, String> param);

	public List<Map<String, ?>> selectAcdtChartList(Map<String, String> param);

	public List<Map<String, ?>> selectAcdtChartcntList(Map<String, String> param);
	
	public List<Map<String, ?>> selectTrnFc1AllList(Map<String, String> param);
	
	public List<Map<String, ?>> selectTrnFc2AllList(Map<String, String> param);
	
	public List<Map<String, ?>> selectTrnFc3AllList(Map<String, String> param);
	
	public List<Map<String, ?>> selectTrnFc6AllList(Map<String, String> param);
	
	public List<Map<String, ?>> selectPlanRsltTot(Map<String, String> param);
	
	public List<Map<String, ?>> selectAcdtChartTot(Map<String, String> param);
	
	public List<Map<String, ?>> selectInvsPlanRsltList(Map<String, String> param);
	
	public List<Map<String, ?>> selectInvsPlanRsltTot(Map<String, String> param);
	
	public List<Map<String, ?>> selectDashCrinList(Map<String, String> param);
	
	public List<Map<String, ?>> selectDashOprnList(Map<String, String> param);
	
	public List<Map<String, ?>> selectDashCircList(Map<String, String> param);
	
	public List<Map<String, ?>> selectDashJntList(Map<String, String> param);
	
	public List<Map<String, ?>> selectDashLabList(Map<String, String> param);
}
