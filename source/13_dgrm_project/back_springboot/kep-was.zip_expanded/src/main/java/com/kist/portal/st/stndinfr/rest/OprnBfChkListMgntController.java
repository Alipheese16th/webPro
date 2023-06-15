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
import com.kist.portal.st.stndinfr.service.OprnBfChkListMgntService;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/st/stndinfr")
public class OprnBfChkListMgntController {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private OprnBfChkListMgntService service;
	
	@GetMapping("/oprnBfChkListMgnt")
	public ResponseEntity<?> selectOprnBfChkList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		List<Map<String, ?>> list = service.selectOprnBfChkList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PostMapping("/oprnBfChkListMgnt")
	@Transactional
	public ResponseEntity<?> insertOprnBfChkList(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		//insert, update, delete 데이터 초기화
		List<Map<String, Object>> insertList = (List<Map<String, Object>>)param.get("insertRow");
		List<Map<String, Object>> updateList = (List<Map<String, Object>>)param.get("updateRow");
		List<Map<String, Object>> deleteList = (List<Map<String, Object>>)param.get("deleteRow");
		
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		
		for(int i = 0; i < insertList.size(); i++) {
			Map<String, Object> temp = insertList.get(i);
			temp.put("crt_usr_id",userInfo.getUsrId());
			temp.put("upt_usr_id",userInfo.getUsrId());
			temp.put("chec_gb", param.get("chec_gb"));
			temp.put("lang_cd", "KO");
			service.insertOprnBfChkList(temp);
			temp.put("lang_cd", "EN");
			service.insertOprnBfChkList(temp);
			temp.put("lang_cd", "ZH");
			service.insertOprnBfChkList(temp);
		}
		for(int i = 0; i < updateList.size(); i++) {
			Map<String, Object> temp = updateList.get(i);
			temp.put("crt_usr_id",userInfo.getUsrId());
			temp.put("upt_usr_id",userInfo.getUsrId());
			temp.put("chec_gb", param.get("chec_gb"));
			temp.put("lang_cd", "KO");
			service.updateOprnBfChkList(temp);
			temp.put("lang_cd", "EN");
			service.updateOprnBfChkList(temp);
			temp.put("lang_cd", "ZH");
			service.updateOprnBfChkList(temp);
		}
		for(int i = 0; i < deleteList.size(); i++) {
			Map<String, Object> temp = deleteList.get(i);
			temp.put("chec_gb", param.get("chec_gb"));
			temp.put("lang_cd", "KO");
			service.deleteOprnBfChkList(temp);
			temp.put("lang_cd", "EN");
			service.deleteOprnBfChkList(temp);
			temp.put("lang_cd", "ZH");
			service.deleteOprnBfChkList(temp);
		}
		
		return new ResponseEntity<>(Constant.SUCCESS, HttpStatus.OK);
	}
}
