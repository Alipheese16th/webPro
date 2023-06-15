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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kist.portal.common.chems.service.ApprService;
import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/common/chems")
public class ApprController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ApprService service;
	
	@Autowired
	private SessionManager sessionManager;

	// 결재 목록 조회
	@GetMapping("/appr-list")
	public ResponseEntity<?> selectApprList(@RequestParam Map<String, String> param, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_LANG", userInfo.getLangCd());
		List<Map<String, ?>> list = service.selectApprList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	// 결재 상세조회
	@GetMapping("/appr-dtl/{id}")
	public ResponseEntity<?> selectApprDtl(@PathVariable String id, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());

		Map<String, String> param = new HashMap<>();
		param.put("aprv_no", id);
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("sess_usr_id", userInfo.getUsrId());

		// 상세정보
		List<Map<String, ?>> apprInfo = service.selectApprDtl(param);
		// 결재 수신 정보
		List<Map<String, ?>> apprRecvInfo = service.selectApprDtlRecv(param);

		Map<String, List<Map<String, ?>>> data = new HashMap<>();
		data.put("apprInfo", apprInfo);
		data.put("apprRecvInfo", apprRecvInfo);
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	// 결재 수신 저장
	@PostMapping("/appr-dtl")
	@Transactional
	public ResponseEntity<?> insertOtsrConBsen(@RequestBody Map<String, String> param, HttpSession session) {
		int n = 0;
		
		if ("apprsave".equals(param.get("save_type"))) {
			// 결재이력 저장
			n = service.insertApprDtlRecv(param);
		} else {
			// 결재정보 저장
			n = service.updateApprDtl(param);
			if ("T".equals(param.get("usg_yn"))) {
				// 결재 사용여부가 T일 경우 결재 수신 이력 상태 초기화
				n = service.updateApprRecvDtl(param);
			}
		}

		String result;
		result = (n > -1? Constant.SUCCESS : Constant.FAIL);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
