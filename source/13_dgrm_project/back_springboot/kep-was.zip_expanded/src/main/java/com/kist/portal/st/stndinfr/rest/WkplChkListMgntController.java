package com.kist.portal.st.stndinfr.rest;

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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.st.stndinfr.service.WkplChkListMgntService;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/st/stndinfr")
public class WkplChkListMgntController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private WkplChkListMgntService service;
	
	// 사업장 작업점검 체크리스 목록
	@GetMapping("/wkpl-chk-list")
	public ResponseEntity<?> selectWkplChkList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		List<Map<String, ?>> list = service.selectWkplChkList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	
	@PutMapping("/wkpl-chk-list")
	@Transactional
	public ResponseEntity<?> insertWkplChkList(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date dt = new Date();
		
		String dtT = formatter.format(dt);
		
		int n = 0;
		int m = 0;
		int j = 0;
		
		//insert, update, delete 데이터 초기화
		List<Map<String, String>> insertList = (List<Map<String, String>>)param.get("insertRow");
		List<Map<String, String>> updateList = (List<Map<String, String>>)param.get("updateRow");
		List<Map<String, String>> deleteList = (List<Map<String, String>>)param.get("deleteRow");
		
		
		if (insertList != null) {
			// 인서트
			for(int i = 0; i < insertList.size(); i++) {
				Map<String, String> temp = insertList.get(i);
				
				String passYn = service.selectWkplChkKeyPass(temp);
				
				// 중복된 내용이 있으면 패스
				if ("N".equals(passYn)) {
					return new ResponseEntity<>("OverlapFail", HttpStatus.OK);
				}
				temp.put("username", userInfo.getUsrId());
				if ("".equals(temp.get("wkpl_chec_sn")) || null == temp.get("wkpl_chec_sn")) {
					temp.put("wkpl_chec_sn", "0");
				}
				temp.put("username", userInfo.getUsrId());
				
				n = service.insertWkplChkList(temp);
			}
		}
		
		if (updateList != null) {
			// 업데이트
			for (int i = 0; i < updateList.size(); i++) {
				Map<String, String> temp = updateList.get(i);
				temp.put("username", userInfo.getUsrId());
				m = service.insertWkplChkList(temp);
			}
		}
		
		if (deleteList != null) {
			// 삭제
			for(int i = 0; i < deleteList.size(); i++) {
				Map<String, String> temp = deleteList.get(i);
				j = service.deleteWkplChkList(temp);
			}
		}
		
		Map<String, String> resultMap = new HashMap<>();
		
		String result;
		result = (n > -1? (m > -1? (j > -1? Constant.SUCCESS : Constant.FAIL) : Constant.FAIL) : Constant.FAIL);
		resultMap.put("result", result);
		
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
}
