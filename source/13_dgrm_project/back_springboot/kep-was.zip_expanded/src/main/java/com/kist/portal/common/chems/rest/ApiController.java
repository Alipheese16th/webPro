package com.kist.portal.common.chems.rest;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kist.portal.common.chems.service.ApiService;
import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/common/chems")
public class ApiController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ApiService service;
	
	@Autowired
	private SessionManager sessionManager;
	
	@GetMapping("/api")
	public ResponseEntity<?> selectApiList(@RequestParam Map<String, String> param, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_LANG", userInfo.getLangCd());
		List<Map<String, ?>> list = service.selectApiList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/api-role")
	public ResponseEntity<?> selectApiRoleList(@RequestParam Map<String, String> param, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_LANG", userInfo.getLangCd());
		List<Map<String, ?>> list = service.selectApiRoleList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PostMapping("/api")
	@SuppressWarnings("unchecked")
	@Transactional
	public ResponseEntity<?> insertApiList(@RequestBody Map<String, Object> param, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		String lang = userInfo.getLangCd();
		param.put("SESS_LANG", lang);
		
		//insert, update, delete 데이터 초기화
		List<Map<String, String>> insertList = (List<Map<String, String>>)param.get("insertRow");
		List<Map<String, String>> updateList = (List<Map<String, String>>)param.get("updateRow");
		List<Map<String, String>> deleteList = (List<Map<String, String>>)param.get("deleteRow");
		
		// 권한 데이터 초기화
		Map<String, Object> chkData = (HashMap<String, Object>)param.get("chkData");
		List<Map<String, String>> updateValue = (List<Map<String, String>>)chkData.get("updateValue");
		
		String key = "";
		// 인서트 처리
		for(int i=0; i<insertList.size(); i++) {
			Map<String, String> temp = insertList.get(i);
			key = service.selectApiKey(temp);
			temp.put("username", userInfo.getUsrId());
			temp.put("api_no", key);
			System.out.println(temp);
			service.insertApiList(temp);
		}
		// 업데이트 처리
		for(int i=0; i<updateList.size(); i++) {
			Map<String, String> temp = updateList.get(i);
			temp.put("username", userInfo.getUsrId());
			System.out.println(temp);
			service.updateApiList(temp);
		}
		// 삭제와 동시에 권한도 함께 삭제 처리
		for(int i=0; i<deleteList.size(); i++) {
			Map<String, String> temp = deleteList.get(i);
			temp.put("username", userInfo.getUsrId());
			System.out.println(temp);
			// api 테이블에서 삭제
			service.deleteApiList(temp);
			// 권한 테이블에서 삭제
			service.deleteApiRole(temp);
		}
		
		Map<String, String> tempMap;
		
		int n = 0;

		// update 값 인서트 처리
		for(int i = 0; i < updateValue.size(); i++) {
			tempMap = updateValue.get(i);
			if(chkData.get("api_no") == null || "".equalsIgnoreCase(chkData.get("api_no").toString())){
				tempMap.put("api_no", key);
			}else {
				tempMap.put("api_no", chkData.get("api_no").toString());
			}
			tempMap.put("username", userInfo.getUsrId());
			n = service.insertApiRole(tempMap); 
		}
		
		String result = n > -1? Constant.SUCCESS : Constant.FAIL;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
