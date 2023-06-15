package com.kist.portal.ch.csttstat.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONArray;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kist.portal.ch.csttstat.service.CsttStatService;
import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/ch/csttstat")
public class CsttStatController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private CsttStatService service;
	
	@GetMapping("/rglt-combo-list")
	public ResponseEntity<?> selectRgltComboList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		
		// 화평법
		param.put("law_cd", "L03");
		List<Map<String, ?>> tccaList = service.selectRgltComboList(param);
		// 화관법
		param.put("law_cd", "L02");
		List<Map<String, ?>> arecList = service.selectRgltComboList(param);
		// 산안법
		param.put("law_cd", "L01");
		List<Map<String, ?>> oshaList = service.selectRgltComboList(param);
		// 위험물안전관리법
		param.put("law_cd", "L04");
		List<Map<String, ?>> dngList = service.selectRgltComboList(param);
		
		Map<String, List<Map<String, ?>>> data = new HashMap<>();
		data.put("tcca", tccaList);
		data.put("arec", arecList);
		data.put("osha", oshaList);
		data.put("dng", dngList);
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	// 화학물질 현황 목록
	@PostMapping("/rglt-chk-total-list")
	public ResponseEntity<?> selectRgltChkTotalList(@RequestBody Map<String, Object> param) throws JSONException {
		// 언어 셋 지정
		param.put("SESS_LANG", "KO");
		
		String rgltKey = "";
		String rgltVal = "";
		String rgltChk = "";
		
		for(int i=1, j=0; i<=20 ; i++) {
			rgltKey = "rglt" + String.format("%02d", i) + "_yn";
			rgltChk = String.valueOf(param.get(rgltKey));
			if("Y".equals(rgltChk)) {
				j++;
				if(j>1) {
					rgltVal += " or " + rgltKey + "='Y'";
				}
				else {
					rgltVal += rgltKey + "='Y'";
				}
			}
		}
		
		List<String> dngrKindList = new ArrayList<>();
		List<String> dngrDtlKindList = new ArrayList<>();
		if(param.get("dngr_kind_cd_json") != null) {
			JSONArray dngr_kind_cd = new JSONArray(String.valueOf(param.get("dngr_kind_cd_json")));
			if(dngr_kind_cd.length() > 0) {
				for(int i = 0; i < dngr_kind_cd.length(); i++) {
					System.out.println(dngr_kind_cd.getJSONObject(i).get("cd"));
					dngrKindList.add(String.valueOf(dngr_kind_cd.getJSONObject(i).get("cd")));
				}
			}
			if(param.get("dngr_dtl_kind_cd_json") != null) {
				JSONArray dngr_dtl_kind_cd = new JSONArray(String.valueOf(param.get("dngr_dtl_kind_cd_json")));
				if(dngr_dtl_kind_cd.length() > 0) {
					for(int i = 0; i < dngr_dtl_kind_cd.length(); i++) {
						System.out.println(dngr_dtl_kind_cd.getJSONObject(i).get("cd"));
						dngrDtlKindList.add(String.valueOf(dngr_dtl_kind_cd.getJSONObject(i).get("cd")));
					}
				}
			}
		}
		
		param.put("RGLT_OBJ", rgltVal);
		param.put("RGLT_LIST", dngrKindList);
		param.put("RGLT_DTL_LIST", dngrDtlKindList);

		List<Map<String, ?>> list = service.selectRgltChkTotalList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	// 화학물질 통계조사 제품정보 목록
	@GetMapping("/stat-resch-list")
	public ResponseEntity<?> selectStatReschList(@RequestParam Map<String, String> param) {
		// 언어 셋 지정
		param.put("SESS_LANG", "KO");
		Map<String, List<Map<String, ?>>> data = new HashMap<>();
		
		// 화학물질 통계조사 제품정보
		List<Map<String, ?>> tab1 = service.selectStatReschListTab1(param);
		// 화학물질 통계조사 구성성부정보
		List<Map<String, ?>> tab2 = service.selectStatReschListTab2(param);
		
		data.put("tab1", tab1);
		data.put("tab2", tab2);
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	// 화학물질 베출량조사 목록	 
	@GetMapping("/chems-sqtt-resch-list")
	public ResponseEntity<?> selectChemsSqtRreschList(@RequestParam Map<String, String> param) {
		// 언어 셋 지정
		param.put("SESS_LANG", "KO");
		
		// 화학물질 베출량조사 목록	
		List<Map<String, ?>> list = service.selectChemsSqtRreschList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
}
