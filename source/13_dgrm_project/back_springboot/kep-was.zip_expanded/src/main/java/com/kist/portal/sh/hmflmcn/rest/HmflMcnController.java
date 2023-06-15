package com.kist.portal.sh.hmflmcn.rest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kist.portal.common.chems.service.CdService;
import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.sh.hmflmcn.service.HmflMcnService;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/she/hmflmcn")
public class HmflMcnController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private HmflMcnService service;
	
	// 유해위험기계기구 관리 목록
	@GetMapping("/hmfl-mcn-list")
	public ResponseEntity<?> selectHmflMcnList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		List<Map<String, ?>> list = service.selectHmflMcnList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	// 유해위험기계기구 관리 사업장 콤보 조회
	@GetMapping("/hmfl-mcn-wkpl-combo")
	public ResponseEntity<?> selectHmflMcnWkplCombo(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		
		List<Map<String, ?>> cdInfo = service.selectHmflMcnWkplCombo(param);
		
		Map<String, List<Map<String, ?>>> data = new HashMap<>();
		data.put("list", cdInfo);
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	// 유해위험기계기구 관리 상세 조회
	@GetMapping("/hmfl-mcn-dtl/{hrmId}")
	public ResponseEntity<?> selectHmflMcnDtl(@PathVariable String hrmId, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		Map<String, String> param = new HashMap<>();
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		param.put("hrm_id", hrmId);
		
		// 기본정보
		List<Map<String, ?>> basicInfo = service.selectHmflMcnDtl(param);
		// 안전점검
		List<Map<String, ?>> chk = service.selectHmflMcnChkList(param);
		
		Map<String, List<Map<String, ?>>> data = new HashMap<>();
		data.put("basic", basicInfo);
		data.put("chk", chk);
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	// 유해위험기계기구 관리 상세 저장
	@PutMapping("/hmfl-mcn-dtl")
	@Transactional
	public ResponseEntity<?> insertHmflMcn(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date dt = new Date();
		
		String dtT = formatter.format(dt);
		
		Map<String, String> temp = new HashMap<>();
		
		int n = 0;
		int m = 0;
		int j = 0;
		int k = 0;
		int l = 0;
		int o = 0;
		
		// 기본정보
		List<Map<String, String>> infoRow = (List<Map<String, String>>)param.get("infoRow");
		
		// 선임정보
		//insert, update, delete 데이터 초기화
		List<Map<String, String>> insertList = (List<Map<String, String>>)param.get("insertRow");
		List<Map<String, String>> updateList = (List<Map<String, String>>)param.get("updateRow");
		List<Map<String, String>> deleteList = (List<Map<String, String>>)param.get("deleteRow");
		
		Map<String, String> tempInfo = infoRow.get(0);
		// 키 채번
		String key = "";
		if (!"".equals(tempInfo.get("hrm_id")) && null != tempInfo.get("hrm_id")) {
			key = tempInfo.get("hrm_id");
		} else {
			key = service.selectHmflMcnkey(temp);
		}
		
		// 기본정보 저장
		tempInfo.put("hrm_id", key);
		tempInfo.put("username", userInfo.getUsrId());
		tempInfo.put("crt_dt", dtT);
		tempInfo.put("upt_dt", dtT);
		m = service.insertHmflMcn(tempInfo);
		
		// 안전점검
		if (insertList != null) {
			// 인서트
			for(int i = 0; i < insertList.size(); i++) {
				Map<String, String> tempList = insertList.get(i);
				tempList.put("hrm_id", key);
				tempList.put("username", userInfo.getUsrId());
				
				n = service.insertHmflMcnDtlChkInfo(tempList);
			}
		}
		
		if (updateList != null) {
			// 업데이트
			for (int i = 0; i < updateList.size(); i++) {
				Map<String, String> tempList = updateList.get(i);
				tempList.put("username", userInfo.getUsrId());
				n = service.insertHmflMcnDtlChkInfo(tempList);
			}
		}
		
		if (deleteList != null) {
			// 삭제
			for(int i = 0; i < deleteList.size(); i++) {
				Map<String, String> tempList = deleteList.get(i);
				n = service.deleteHmflMcnDtlChkInfo(tempList);
			}
		}
		
		Map<String, String> resultMap = new HashMap<>();
		
		String result;
		result = (n > -1? (m > -1? (j > -1? (k > -1? (l > -1? (o > -1? Constant.SUCCESS : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL);
		resultMap.put("result", result);
		resultMap.put("key1", tempInfo.get("wkpl_id"));
		resultMap.put("key2", key);
		
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
	
	// 유해위험기계기구 점검일 저장
	@PutMapping("/hmfl-mcn-list")
	@Transactional
	public ResponseEntity<?> insertHmflMcnChkDt(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		int n = 0;
		
		// 선임정보
		//insert, update, delete 데이터 초기화
		List<Map<String, String>> updateList = (List<Map<String, String>>)param.get("updateRow");
		
		if (updateList != null) {
			// 업데이트
			String passYn = "Y";
			for (int i = 0; i < updateList.size(); i++) {
				Map<String, String> tempList = updateList.get(i);
				passYn = service.selectHmflMcnChkDupChk(tempList);
				
				// 중복된 키가 있으면 패스
				if ("N".equals(passYn)) { 
					return new ResponseEntity<>("OverlapFail", HttpStatus.OK);
				}
				
				tempList.put("username", userInfo.getUsrId());
				n = service.insertHmflMcnDtlChkListInfo(tempList);
			}
		}
		
		Map<String, String> resultMap = new HashMap<>();
		
		String result;
		result = (n > -1? Constant.SUCCESS : Constant.FAIL);
		resultMap.put("result", result);
		
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
	
	// 안전검사 결과 관리 목록
	@GetMapping("/sft-insp-rslt-list")
	public ResponseEntity<?> selectSftInspRsltList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		List<Map<String, ?>> list = service.selectSftInspRsltList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	// 안전검사 결과 관리 상세 조회
	@GetMapping("/sft-insp-rslt-dtl/{hrmSfipNo}")
	public ResponseEntity<?> selectSftInspRsltDtl(@PathVariable String hrmSfipNo, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		Map<String, String> param = new HashMap<>();
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		param.put("hrm_sfip_no", hrmSfipNo);
		
		// 기본정보
		List<Map<String, ?>> basicInfo = service.selectSftInspRsltDtl(param);
		
		Map<String, List<Map<String, ?>>> data = new HashMap<>();
		data.put("basic", basicInfo);
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	// 안전검사 결과 관리 상세 저장
	@PutMapping("/sft-insp-rslt-dtl")
	@Transactional
	public ResponseEntity<?> insertSftInspRsltDtl(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date dt = new Date();
		
		String dtT = formatter.format(dt);
		
		Map<String, String> temp = new HashMap<>();
		
		int n = 0;
		
		// 기본정보
		List<Map<String, String>> infoRow = (List<Map<String, String>>)param.get("infoRow");
		
		
		Map<String, String> tempInfo = infoRow.get(0);
		// 키 채번
		String key = "";
		if (!"".equals(tempInfo.get("hrm_sfip_no")) && null != tempInfo.get("hrm_sfip_no")) {
			key = tempInfo.get("hrm_sfip_no");
		} else {
			key = service.selectSftInspRsltkey(temp);
		}
		
		// 기본정보 저장
		tempInfo.put("hrm_sfip_no", key);
		tempInfo.put("username", userInfo.getUsrId());
		tempInfo.put("crt_dt", dtT);
		tempInfo.put("upt_dt", dtT);
		n = service.insertSftInspRsltDtl(tempInfo);
		
		
		Map<String, String> resultMap = new HashMap<>();
		
		String result;
		result = (n > -1?  Constant.SUCCESS : Constant.FAIL);
		resultMap.put("result", result);
		resultMap.put("key1", tempInfo.get("wkpl_id"));
		resultMap.put("key2", key);
		
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
}
