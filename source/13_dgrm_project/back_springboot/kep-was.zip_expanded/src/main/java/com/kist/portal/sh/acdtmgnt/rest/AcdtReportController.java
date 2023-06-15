package com.kist.portal.sh.acdtmgnt.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.sh.acdtmgnt.service.AcdtReportService;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/she/sfty")
public class AcdtReportController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SessionManager sessionManager;
			
	@Autowired
	private AcdtReportService service;
	
	@GetMapping("/acdtrpt")
	public ResponseEntity<?> selectAcdtRptList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		
		Map<String, List<Map<String, ?>>> data = new HashMap<>();
		String kind = param.get("kind");
		logger.debug("kind is " + kind);
		if("select".equals(kind)) {
			param.put("now", "Y");
			List<Map<String, ?>> acdtTot1 = service.selectAcdtTot1(param);
			List<Map<String, ?>> acdtTot2 = service.selectAcdtTot2(param);
			param.put("now", "N");
			List<Map<String, ?>> acdtTot3 = service.selectAcdtTot1(param);
			List<Map<String, ?>> acdtTot4 = service.selectAcdtTot2(param);
			
			List<Map<String, ?>> apTot = service.selectApTot(param);
			param.put("wkpl_list", "S03"); // 오산뷰티
			List<Map<String, ?>> apWkpl1 = service.selectApWkpl(param);
			param.put("wkpl_list", "S04"); // 데일리뷰티
			List<Map<String, ?>> apWkpl2 = service.selectApWkpl(param);
			param.put("wkpl_list", "S19"); // 헬스케어생산
			List<Map<String, ?>> apWkpl3 = service.selectApWkpl(param);
			param.put("wkpl_list", "S20"); // 오설록생산
			List<Map<String, ?>> apWkpl4 = service.selectApWkpl(param);
			param.put("wkpl_list", "S02"); // R&I Center
			List<Map<String, ?>> apWkpl5 = service.selectApWkpl(param);
			param.put("wkpl_list", "S16,S22,S23,S24,S25,S26,S27,S28,S21"); // 물류
			List<Map<String, ?>> apWkpl6 = service.selectApWkpl(param);
			param.put("wkpl_list", "S35"); // 세계본사
			List<Map<String, ?>> apWkpl7 = service.selectApWkpl(param);
			param.put("wkpl_list", "S36,S37,S38,S39,S40"); // 지역사업부
			List<Map<String, ?>> apWkpl8 = service.selectApWkpl(param);
			List<Map<String, ?>> apCat = service.selectCat(param);
			List<Map<String, ?>> apWkpl = service.selectWkpl(param);
			List<Map<String, ?>> apCatWkpl = service.selecCatWkpl(param);
			List<Map<String, ?>> acdtPerQt = service.selecAcdtPerQt(param);
			List<Map<String, ?>> acdtPerMon = service.selecAcdtPerMon(param);
			
			List<Map<String, ?>> acdtType = service.selecAcdtType(param);
			List<Map<String, ?>> acdtOprn = service.selecAcdtOprn(param);
			List<Map<String, ?>> acdtUsftTxt = service.selecAcdtUsftTxt(param);
			List<Map<String, ?>> acdtUsftSt = service.selecAcdtUsftSt(param);
			List<Map<String, ?>> acdtCausTgt = service.selecAcdtCausTgt(param);
			List<Map<String, ?>> acdtPrst = service.selecAcdtPrst(param);
			List<Map<String, ?>> acdtDept = service.selecAcdtDept(param);
			List<Map<String, ?>> acdtPeriod = service.selecAcdtPeriod(param);
			List<Map<String, ?>> acdtHh = service.selecAcdtHh(param);
			List<Map<String, ?>> acdtMm = service.selecAcdtMm(param);
			List<Map<String, ?>> acdtDow = service.selecAcdtDow(param);
			List<Map<String, ?>> acdtCareDays = service.selecAcdtCareDays(param);
			List<Map<String, ?>> acdtAge = service.selecAcdtAge(param);
			List<Map<String, ?>> acdtSex = service.selecAcdtSex(param);
			
			data.put("acdtTot1", acdtTot1);
			data.put("acdtTot2", acdtTot2);
			data.put("acdtTot3", acdtTot3);
			data.put("acdtTot4", acdtTot4);
			data.put("apTot", apTot);
			data.put("apWkpl1", apWkpl1);
			data.put("apWkpl2", apWkpl2);
			data.put("apWkpl3", apWkpl3);
			data.put("apWkpl4", apWkpl4);
			data.put("apWkpl5", apWkpl5);
			data.put("apWkpl6", apWkpl6);
			data.put("apWkpl7", apWkpl7);
			data.put("apWkpl8", apWkpl8);
			data.put("apCat", apCat);
			data.put("apWkpl", apWkpl);
			data.put("apCatWkpl", apCatWkpl);
			data.put("acdtPerQt", acdtPerQt);
			data.put("acdtPerMon", acdtPerMon);
			data.put("acdtType", acdtType);
			data.put("acdtOprn", acdtOprn);
			data.put("acdtUsftTxt", acdtUsftTxt);
			data.put("acdtUsftSt", acdtUsftSt);
			data.put("acdtCausTgt", acdtCausTgt);
			data.put("acdtPrst", acdtPrst);
			data.put("acdtDept", acdtDept);
			data.put("acdtPeriod", acdtPeriod);
			data.put("acdtHh", acdtHh);
			data.put("acdtMm", acdtMm);
			data.put("acdtDow", acdtDow);
			data.put("acdtCareDays", acdtCareDays);
			data.put("acdtAge", acdtAge);
			data.put("acdtSex", acdtSex);
		} else if("select1".equals(kind)) {
			List<Map<String, ?>> acdtPerQt = service.selecAcdtPerQt(param);
			List<Map<String, ?>> acdtPerMon = service.selecAcdtPerMon(param);
			
			data.put("acdtPerQt", acdtPerQt);
			data.put("acdtPerMon", acdtPerMon);
		} else if("select2".equals(kind)) {
			List<Map<String, ?>> acdtType = service.selecAcdtType(param);
			List<Map<String, ?>> acdtOprn = service.selecAcdtOprn(param);
			List<Map<String, ?>> acdtUsftTxt = service.selecAcdtUsftTxt(param);
			List<Map<String, ?>> acdtUsftSt = service.selecAcdtUsftSt(param);
			List<Map<String, ?>> acdtCausTgt = service.selecAcdtCausTgt(param);
			List<Map<String, ?>> acdtPrst = service.selecAcdtPrst(param);
			List<Map<String, ?>> acdtDept = service.selecAcdtDept(param);
			List<Map<String, ?>> acdtPeriod = service.selecAcdtPeriod(param);
			List<Map<String, ?>> acdtHh = service.selecAcdtHh(param);
			List<Map<String, ?>> acdtMm = service.selecAcdtMm(param);
			List<Map<String, ?>> acdtDow = service.selecAcdtDow(param);
			List<Map<String, ?>> acdtCareDays = service.selecAcdtCareDays(param);
			List<Map<String, ?>> acdtAge = service.selecAcdtAge(param);
			List<Map<String, ?>> acdtSex = service.selecAcdtSex(param);
			
			data.put("acdtType", acdtType);
			data.put("acdtOprn", acdtOprn);
			data.put("acdtUsftTxt", acdtUsftTxt);
			data.put("acdtUsftSt", acdtUsftSt);
			data.put("acdtCausTgt", acdtCausTgt);
			data.put("acdtPrst", acdtPrst);
			data.put("acdtDept", acdtDept);
			data.put("acdtPeriod", acdtPeriod);
			data.put("acdtHh", acdtHh);
			data.put("acdtMm", acdtMm);
			data.put("acdtDow", acdtDow);
			data.put("acdtCareDays", acdtCareDays);
			data.put("acdtAge", acdtAge);
			data.put("acdtSex", acdtSex);
		}
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@GetMapping("/acdtrpt/{kind}")
	public ResponseEntity<?> selectAcdtRpt(@PathVariable String kind, HttpSession session) {
		Map<String, List<Map<String, ?>>> data = new HashMap<>();
				
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
}
