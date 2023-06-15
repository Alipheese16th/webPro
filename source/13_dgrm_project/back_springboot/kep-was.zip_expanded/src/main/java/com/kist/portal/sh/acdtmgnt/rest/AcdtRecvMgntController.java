package com.kist.portal.sh.acdtmgnt.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.kist.portal.sh.acdtmgnt.service.AcdtRecvMgntService;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/she/sfty")
public class AcdtRecvMgntController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SessionManager sessionManager;
	
	@Value("${dgrm.front-url}")
	private String frontUrl;
	
	@Value("${dgrm.server-url}")
	private String serverUrl;
	
	@Autowired
	private AcdtRecvMgntService service;
	
	@GetMapping("/acdtrcvrec")
	public ResponseEntity<?> selectAcdtRecvMgntList(@RequestParam Map<String, String> param, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG_CD", userInfo.getLangCd());
		
		// 기본정보 조회
		List<Map<String, Object>> basic = service.selectAcdtRecvMgntBasic(param);
		// 수신처 부서 목록 조회
		List<Map<String, Object>> rcvDeptList = service.selectAcdtRecvMgntRcvDeptList(param);
		// 수신처 인원 목록 조회
		List<Map<String, Object>> rcvEmpList = service.selectAcdtRecvMgntRcvEmpList(param);
		// 참조처 부서 목록 조회
		List<Map<String, Object>> refDeptList = service.selectAcdtRecvMgntRefDeptList(param);
		// 참조처 인원 목록 조회
		List<Map<String, Object>> refEmpList = service.selectAcdtRecvMgntRefEmpList(param);

		Map<String, Object> rst = new HashMap<String, Object>();
		rst.put("basic", basic);
		rst.put("rcvDeptList", rcvDeptList);
		rst.put("rcvEmpList", rcvEmpList);
		rst.put("refDeptList", refDeptList);
		rst.put("refEmpList", refEmpList);
		return new ResponseEntity<>(rst, HttpStatus.OK);
	}
	
	@PostMapping("/acdtrcv")
	@Transactional
	public ResponseEntity<?> insertAcdtrcv(@RequestBody Map<String, Object> param, HttpSession session) {
		System.out.println(param);
		UserInfo userInfo = sessionManager.get(session.getId());

		int n = -1;
		
		// 전부 삭제하고
		n = service.deleteAcdtRcv(param);

		// 수신처 목록 저장
		List<Map<String, Object>> saveRecList = (List<Map<String, Object>>) param.get("saveRecList");
		for (int i = 0; i < saveRecList.size(); i++) {
			Map<String, Object> data = saveRecList.get(i);
			data.put("SESS_USR_ID", userInfo.getUsrId());
			data.put("sn", i + 1);
			n = service.insertAcdtRcv(data);
		}

		// 참조처 목록 저장
		List<Map<String, Object>> saveRefList = (List<Map<String, Object>>) param.get("saveRefList");
		for (int i = 0; i < saveRefList.size(); i++) {
			Map<String, Object> data = saveRefList.get(i);
			data.put("SESS_USR_ID", userInfo.getUsrId());
			data.put("sn", i + 1);
			n = service.insertAcdtRcv(data);
		}
		
		String result = n > -1 ? Constant.SUCCESS : Constant.FAIL;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
