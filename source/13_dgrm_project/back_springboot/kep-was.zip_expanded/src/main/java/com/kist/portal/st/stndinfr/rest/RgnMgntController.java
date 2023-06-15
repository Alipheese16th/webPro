package com.kist.portal.st.stndinfr.rest;

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

import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.st.stndinfr.service.RgnMgntService;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/st/stndinfr")
public class RgnMgntController {

Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private RgnMgntService service;
	
	@GetMapping("/rgnmgntlist")
	public ResponseEntity<?> selectRgnmgntList(@RequestParam Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		List<Map<String, ?>> list = service.selectRgnmgntList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/rgn-mgnt-cd")
	public ResponseEntity<?> selectRgnmgntCdList(@RequestParam Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		List<Map<String, ?>> list = service.selectRgnmgntCdList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/rgnmgnt-pop-list")
	public ResponseEntity<?> selectRgnmgntPopList(@RequestParam Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		List<Map<String, ?>> list = service.selectRgnmgntPopList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PostMapping("/rgnmgntlist")
	@Transactional
	public ResponseEntity<?> insertRgnmgntList(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		//insert, update, delete 데이터 초기화
		List<Map<String, Object>> insertList = (List<Map<String, Object>>)param.get("insertRow");
		List<Map<String, Object>> updateList = (List<Map<String, Object>>)param.get("updateRow");
		List<Map<String, Object>> deleteList = (List<Map<String, Object>>)param.get("deleteRow");
		
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		
		for(int i = 0; i < insertList.size(); i++) {
			Map<String, Object> temp = insertList.get(i);
			String key = "";
			if(temp.get("level").equals("1")) {
				key = service.selectRgnmgntBKey(temp);
			} else {
				key = service.selectRgnmgntFKey(temp);
			}
			temp.put("wkpl_loc_id", key);
			temp.put("crt_usr_id",userInfo.getUsrId());
			temp.put("upt_usr_id",userInfo.getUsrId());
			service.insertRgnmgntList(temp);
		}
		for(int i = 0; i < updateList.size(); i++) {
			Map<String, Object> temp = updateList.get(i);
			temp.put("crt_usr_id",userInfo.getUsrId());
			temp.put("upt_usr_id",userInfo.getUsrId());
			service.updateRgnmgntList(temp);
		}
		for(int i = 0; i < deleteList.size(); i++) {
			Map<String, Object> temp = deleteList.get(i);
			service.deleteRgnmgntList(temp);
		}
		
		return new ResponseEntity<>(Constant.SUCCESS, HttpStatus.OK);
	}

	@GetMapping("/rgnmgnt/loc")
	@Transactional
	public ResponseEntity<?> selectRgnmgntLoc(@RequestParam Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		List<Map<String, ?>> result = service.selectRgnmgntLoc(param);

		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PostMapping("/rgnmgnt/loc")
	@Transactional
	public ResponseEntity<?> insertRgnmgntLoc(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		System.out.println("LOC 저장 START");
		param.put("crt_usr_id", userInfo.getUsrId());
		param.put("upt_usr_id", userInfo.getUsrId());

		service.updateRgnmgntLoc(param);
		service.saveLocPoint(param);

		System.out.println("LOC 저장 FINISH");

		return new ResponseEntity<>(Constant.SUCCESS, HttpStatus.OK);
	}
}
