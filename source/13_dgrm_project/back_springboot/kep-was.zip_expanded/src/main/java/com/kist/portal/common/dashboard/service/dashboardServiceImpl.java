package com.kist.portal.common.dashboard.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kist.portal.common.dashboard.mapper.dashboardMapper;

@Service
public class dashboardServiceImpl implements dashboardService {
	
	@Autowired
	dashboardMapper mapper;
	
	@Override
	public List<Map<String, ?>> selectOprnLocList(Map<String, String> param) {
		return mapper.selectOprnLocList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectOprnLocTot(Map<String, String> param) {
		return mapper.selectOprnLocTot(param);
	}
	
	@Override
	public String selectWeatRgn(Map<String, String> param) {
		return mapper.selectWeatRgn(param);
	}
	
	@Override
	public List<Map<String, ?>> selectWeatMsgList(Map<String, String> param) {
		return mapper.selectWeatMsgList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectWeatMsgChk(Map<String, String> param) {
		return mapper.selectWeatMsgChk(param);
	}
	
	@Override
	public List<Map<String, ?>> selectWeatMsgChk2(Map<String, String> param) {
		return mapper.selectWeatMsgChk2(param);
	}
	
	@Override
	public int insertWeatMsg(Map<String, String> param) {
		return mapper.insertWeatMsg(param);
	}
	
	@Override
	public int deleteWeatMsg(Map<String, String> param) {
		return mapper.deleteWeatMsg(param);
	}
	
	@Override
	public List<Map<String, ?>> selectPlanRsltList(Map<String, String> param) {
		return mapper.selectPlanRsltList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectYearConOprnAllList(Map<String, String> param) {
		return mapper.selectYearConOprnAllList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectTrnPerList(Map<String, String> param) {
		return mapper.selectTrnPerList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectYearConOprnTypeList(Map<String, String> param) {
		return mapper.selectYearConOprnTypeList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectYearConCheckList(Map<String, String> param) {
		return mapper.selectYearConCheckList(param);
	}

	@Override
	public List<Map<String, ?>> selectYearCrinCheckList(Map<String, String> param) {
		return mapper.selectYearCrinCheckList(param);
	}

	@Override
	public List<Map<String, ?>> selectYearConCheckGbList(Map<String, String> param) {
		return mapper.selectYearConCheckGbList(param);
	}

	@Override
	public List<Map<String, ?>> selectYearCrinGbCheckList(Map<String, String> param) {
		return mapper.selectYearCrinGbCheckList(param);
	}

	@Override
	public List<Map<String, ?>> selectAcdtChartList(Map<String, String> param) {
		return mapper.selectAcdtChartList(param);
	}

	@Override
	public List<Map<String, ?>> selectAcdtChartcntList(Map<String, String> param) {
		return mapper.selectAcdtChartcntList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectTrnFc1AllList(Map<String, String> param) {
		return mapper.selectTrnFc1AllList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectTrnFc2AllList(Map<String, String> param) {
		return mapper.selectTrnFc2AllList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectTrnFc3AllList(Map<String, String> param) {
		return mapper.selectTrnFc3AllList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectTrnFc6AllList(Map<String, String> param) {
		return mapper.selectTrnFc6AllList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectPlanRsltTot(Map<String, String> param) {
		return mapper.selectPlanRsltTot(param);
	}
	
	@Override
	public List<Map<String, ?>> selectAcdtChartTot(Map<String, String> param) {
		return mapper.selectAcdtChartTot(param);
	}
	
	@Override
	public List<Map<String, ?>> selectInvsPlanRsltList(Map<String, String> param) {
		return mapper.selectInvsPlanRsltList(param);
	}
	
	@Override
	public List<Map<String, ?>> selectInvsPlanRsltTot(Map<String, String> param) {
		return mapper.selectInvsPlanRsltTot(param);
	}

	@Override
	public List<Map<String, ?>> selectDashCrinList(Map<String, String> param) {
		return mapper.selectDashCrinList(param);
	}

	@Override
	public List<Map<String, ?>> selectDashOprnList(Map<String, String> param) {
		return mapper.selectDashOprnList(param);
	}

	@Override
	public List<Map<String, ?>> selectDashCircList(Map<String, String> param) {
		return mapper.selectDashCircList(param);
	}

	@Override
	public List<Map<String, ?>> selectDashJntList(Map<String, String> param) {
		return mapper.selectDashJntList(param);
	}

	@Override
	public List<Map<String, ?>> selectDashLabList(Map<String, String> param) {
		return mapper.selectDashLabList(param);
	}
}
