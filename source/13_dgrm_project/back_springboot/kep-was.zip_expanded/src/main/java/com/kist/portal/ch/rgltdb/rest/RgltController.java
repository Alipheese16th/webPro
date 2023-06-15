package com.kist.portal.ch.rgltdb.rest;

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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kist.portal.ch.rgltdb.service.RgltService;
import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/ch/rgltdb")
public class RgltController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private RgltService service;
	
	@GetMapping("/rglt-list")
	public ResponseEntity<?> selectRgltList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		List<Map<String, ?>> list = service.selectRgltList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/rglt-dtl/{id}")
	public ResponseEntity<?> selectRgltDtl(@PathVariable String id, HttpSession session) {
		Map<String, ?> map = service.selectRgltDtl(id);
		
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@PostMapping("/rglt-dtl")
	public ResponseEntity<?> insertRgltDtl(@RequestBody Map<String, String> param) {
		param.put("chg_type_cd", "C");
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date dt = new Date();
		
		String dtT = formatter.format(dt);
		
		Map<String, ?> map = service.selectRgltDtlKey();
		
		String rgltKey = map.get("rglt_no").toString();
		
		param.put("rglt_no", rgltKey);
		param.put("del_yn", "N");
		param.put("crt_usr_id", param.get("username"));
		param.put("upt_usr_id", param.get("username"));
		param.put("crt_dt", dtT);
		param.put("upt_dt", dtT);
		
		int n = service.insertRgltDtl(param);
		int nH = service.insertRgltDtlHst(param);
		
		String result = n > -1? Constant.SUCCESS : Constant.FAIL;
		String resultH = nH > -1? Constant.SUCCESS : Constant.FAIL;
		
		if (!result.equals(resultH)) {
			result = Constant.FAIL;
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PutMapping("/rglt-dtl")
	public ResponseEntity<?> updateRgltDtl(@RequestBody Map<String, String> param) {
		param.put("chg_type_cd", "U");
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date dt = new Date();
		
		String dtT = formatter.format(dt);
		
		param.put("upt_usr_id", param.get("username"));
		param.put("upt_dt", dtT);
		
		int n = service.updateRgltDtl(param);
		int nH = service.insertRgltDtlHst(param);
		
		String result = n > -1? Constant.SUCCESS : Constant.FAIL;
		String resultH = nH > -1? Constant.SUCCESS : Constant.FAIL;
		
		if (!result.equals(resultH)) {
			result = Constant.FAIL;
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@DeleteMapping("/rglt-dtl")
	public ResponseEntity<?> deleteRgltDtl(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		//Map<String, String> param = new HashMap<>();
		param.put("chg_type_cd", "D");
		//param.put("rglt_no", id);
		param.put("username", userInfo.getUsrId());
		int n = service.deleteRgltDtl(param);
		int nH = service.insertRgltDtlHst(param);
		
		String result = n > -1? Constant.SUCCESS : Constant.FAIL;
		String resultH = nH > -1? Constant.SUCCESS : Constant.FAIL;
		
		if (!result.equals(resultH)) {
			result = Constant.FAIL;
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/rglt-hst-list")
	public ResponseEntity<?> selectRgltHstList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		List<Map<String, ?>> list = service.selectRgltHstList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
}
