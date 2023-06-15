package com.kist.portal.common.comm.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kist.portal.common.comm.service.CommService;
import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/comm")
public class CommController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private CommService service;
	
	@GetMapping("/comm-cd")
	public ResponseEntity<?> selectCdList(HttpServletRequest request, @RequestParam Map<String, String> param, HttpSession session) {
        logger.info("===================== " + request.getRequestURI() + " START! =======================");
		UserInfo userInfo = sessionManager.get(session.getId());

		Map<String, List<Map<String, ?>>> returnParam = new HashMap<>();
		for(int i = 0 ; i< param.size();i++) 
		{
			String cdGrpNo = param.get(i+"");
			Map<String, String> searchParam = new HashMap<>();
			searchParam.put("SESS_LANG", userInfo.getLangCd());
			searchParam.put("cd_grp_no", cdGrpNo);
			List<Map<String, ?>> searchList = service.selectCdList(searchParam);
			returnParam.put(cdGrpNo, searchList);
		}

        logger.info("===================== " + request.getRequestURI() + " END! =======================");
		return new ResponseEntity<>(returnParam, HttpStatus.OK);
	}
	
	@GetMapping("/comm-cd/all")
	public ResponseEntity<?> selectCdAllList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		Map<String, List<Map<String, ?>>> returnParam = new HashMap<>();
		param.put("SESS_LANG", userInfo.getLangCd());

		List<Map<String, ?>> GrpAllList = service.selectCdGrpAllList(param);
		returnParam.put("GrpAllList", GrpAllList);
		
		List<Map<String, ?>> CdAllList = service.selectCdAllList(param);
		returnParam.put("CdAllList", CdAllList);
		
		return new ResponseEntity<>(returnParam, HttpStatus.OK);
	}
	
	@PutMapping("/config")
	public ResponseEntity<?> updateConfig(@RequestBody Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("usr_id", userInfo.getUsrId());
		// 타임존 코드 존재 여부 확인
		String result = "";
		List<Map<String, ?>> t = service.selectTzCode(param);
		if(t.size() > 0) {
			int n = service.updateConfig(param);
			result = n > -1 ? Constant.SUCCESS : Constant.FAIL;
		} else {
			result = "TIMEFAIL";
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/mlang")
	public ResponseEntity<?> selectMultiLanguageList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_LANG", userInfo.getLangCd());

		List<Map<String, ?>> list = service.selectMultiLanguageList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("/mlang/i18n")
	public ResponseEntity<?> selectMultiLanguageI18n(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		Map<String, String> multiLang = service.selectMultiLanguageI18n(param);
		return new ResponseEntity<>(multiLang, HttpStatus.OK);
	}
	
	@GetMapping("/wkpl")
	public ResponseEntity<?> selectWkplCode(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_LANG", userInfo.getLangCd());

		List<Map<String, ?>> list = service.selectWkplCode(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

}
