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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kist.portal.ch.rgltdb.service.RgltSbstService;
import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/ch/rgltdb")
public class RgltSbstController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private RgltSbstService service;
	
	@GetMapping("/sbst-list")
	public ResponseEntity<?> selectSbstList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		List<Map<String, ?>> list = service.selectSbstList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/sbst-dtl/{id}")
	public ResponseEntity<?> selectSbstDtl(@PathVariable String id) {
		Map<String, ?> map = service.selectSbstDtl(id);
		
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@GetMapping("/sbst-key-chk/{id}")
	public ResponseEntity<?> selectSbstKeyChk(@PathVariable String id) {
		Map<String, ?> map = service.selectSbstKeyChk(id);
		
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@PostMapping("/sbst-dtl")
	@Transactional
	public ResponseEntity<?> insertRgltDtl(@RequestBody Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date dt = new Date();
		
		String dtT = formatter.format(dt);
		
		Map<String, ?> map = service.selectSbstKeyChk(param.get("sbst_no"));
		
		String keyChk = map.get("dup_yn").toString();
		
		String result = "";
		
		if ("N".equals(keyChk)) {
			param.put("chg_type_cd", "C");
			param.put("crt_usr_id", userInfo.getUsrId());
			param.put("username", userInfo.getUsrId());
			param.put("crt_dt", dtT);
			param.put("upt_dt", dtT);
			
			int n = service.insertSbstDtl(param);
			int nH = service.insertSbstDtlHst(param);
			
			result = n > -1? Constant.SUCCESS : Constant.FAIL;
			String resultH = nH > -1? Constant.SUCCESS : Constant.FAIL;
			
			if (!result.equals(resultH)) {
				result = Constant.FAIL;
			}
		} else {
			result = "OverlapFail";
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PutMapping("/sbst-dtl")
	@Transactional
	public ResponseEntity<?> updateRgltDtl(@RequestBody Map<String, String> param) {
		param.put("chg_type_cd", "U");
		
		int n = service.updateSbstDtl(param);
		int nH = service.insertSbstDtlHst(param);
		
		String result = n > -1? Constant.SUCCESS : Constant.FAIL;
		String resultH = nH > -1? Constant.SUCCESS : Constant.FAIL;
		
		if (!result.equals(resultH)) {
			result = Constant.FAIL;
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/sbst-hst-list")
	public ResponseEntity<?> selectSbstHstList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		List<Map<String, ?>> list = service.selectSbstHstList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/sbst-rglt-dtl/{id}")
	public ResponseEntity<?> selectSbstRgltDtlInfo(@PathVariable String id, HttpSession session) {
		Map<String, String> param = new HashMap<>();
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		param.put("sbst_no", id);
		
		Map<String, ?> map = service.selectSbstRgltDtlInfo(param);
		
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@GetMapping("/sbst-rglt-dtl-list")
	public ResponseEntity<?> selectSbstRgltDtlList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		List<Map<String, ?>> list = service.selectSbstRgltDtlList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PutMapping("/sbst-rglt-dtl")
	@Transactional
	public ResponseEntity<?> updateSbstRgltDtlList(@RequestBody Map<String, Object> param) {
	
		//insert, update, delete 데이터 초기화
		List<Map<String, String>> updateList = (List<Map<String, String>>)param.get("updateRow");
		
		int n = 0;
		int nH = 0;
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date dt = new Date();
		String dtT = formatter.format(dt);
		
		for (int i = 0; i < updateList.size(); i++) {
			Map<String, String> temp = updateList.get(i);
			
			if ("Y".equals(temp.get("tgt_yn"))) {
				
				Map<String, String> chkParam = new HashMap<String, String>();
				chkParam.put("rglt_no", temp.get("rglt_no"));
				chkParam.put("sbst_no", temp.get("sbst_no"));
				
				// 기존 데이터 존재 여부 체크
//				Map<String, ?> map = service.selectSbstRgltDtlChk(chkParam);
//				if ("Y".equals(map.get("up_yn"))) {
//					temp.put("chg_type_cd", "U");
//					temp.put("del_yn", "N");
//				} else {
//					temp.put("chg_type_cd", "C");
//					temp.put("crt_usr_id", temp.get("username"));
//					temp.put("crt_dt", dtT);
//					temp.put("usg_yn", "Y");
//					temp.put("del_yn", "N");
//				}
				
				if ("Y".equals(temp.get("org_tgt_yn"))) {
					temp.put("chg_type_cd", "U");
					temp.put("del_yn", "N");
				} else {
					temp.put("chg_type_cd", "C");
					temp.put("crt_usr_id", temp.get("username"));
					temp.put("crt_dt", dtT);
					temp.put("usg_yn", "Y");
					temp.put("del_yn", "N");
				}
				
				n = service.insertSbstRgltDtlList(temp);
				nH = service.insertSbstRgltDtlHst(temp);
			} else {
				if ("Y".equals(temp.get("org_tgt_yn"))) {
					temp.put("chg_type_cd", "D");
					temp.put("del_yn", "Y");
					n = service.deleteSbstRgltDtlList(temp);
					nH = service.insertSbstRgltDtlHst(temp);
				} else {
					temp.put("chg_type_cd", "C");
					temp.put("del_yn", "N");
					temp.put("crt_usr_id", temp.get("username"));
					temp.put("crt_dt", dtT);
					temp.put("usg_yn", "Y");
					n = service.insertSbstRgltDtlList(temp);
					nH = service.insertSbstRgltDtlHst(temp);
				}
			}
		}
		
		String result = n > -1? Constant.SUCCESS : Constant.FAIL;
		String resultH = nH > -1? Constant.SUCCESS : Constant.FAIL;
		
		if (!result.equals(resultH)) {
			result = Constant.FAIL;
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/rglt-sbst-dtl/{id}")
	public ResponseEntity<?> selectRgltSbstDtlInfo(@PathVariable String id, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		Map<String, String> param = new HashMap<>();
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		param.put("rglt_no", id);
		
		Map<String, ?> map = service.selectRgltSbstDtlInfo(param);
		
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@GetMapping("/rglt-sbst-dtl-list")
	public ResponseEntity<?> selectRgltSbstDtlList(@RequestParam Map<String, String> param) {
		List<Map<String, ?>> list = service.selectRgltSbstDtlList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PutMapping("/rglt-sbst-dtl")
	@Transactional
	public ResponseEntity<?> updateRgltSbstDtlList(@RequestBody Map<String, Object> param) {
	
		//insert, update, delete 데이터 초기화
		List<Map<String, String>> insertList = (List<Map<String, String>>)param.get("insertRow");
		List<Map<String, String>> updateList = (List<Map<String, String>>)param.get("updateRow");
		List<Map<String, String>> deleteList = (List<Map<String, String>>)param.get("deleteRow");
		
		int n = 0;
		int nH = 0;
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date dt = new Date();
		
		String dtT = formatter.format(dt);
		
		// 인서트
		for(int i = 0; i < insertList.size(); i++) {
			Map<String, String> temp = insertList.get(i);
			
			Map<String, String> chkParam = new HashMap<String, String>();
			chkParam.put("rglt_no", temp.get("rglt_no"));
			chkParam.put("sbst_no", temp.get("sbst_no"));
			
			// 기존 데이터 존재 여부 체크
			Map<String, ?> map = service.selectSbstRgltDtlChk(chkParam);
			if ("Y".equals(map.get("up_yn"))) {
				temp.put("chg_type_cd", "C");
				temp.put("crt_usr_id", temp.get("username"));
				temp.put("crt_dt", dtT);
				temp.put("usg_yn", "Y");
				temp.put("del_yn", "N");
			} else {
				temp.put("chg_type_cd", "C");
				temp.put("crt_usr_id", temp.get("username"));
				temp.put("crt_dt", dtT);
				temp.put("usg_yn", "Y");
				temp.put("del_yn", "N");
			}
			n = service.insertSbstRgltDtlList(temp);
			nH = service.insertSbstRgltDtlHst(temp);
		}
		
		// 업데이트
		for (int i = 0; i < updateList.size(); i++) {
			Map<String, String> temp = updateList.get(i);
			
				
			Map<String, String> chkParam = new HashMap<String, String>();
			chkParam.put("rglt_no", temp.get("rglt_no"));
			chkParam.put("sbst_no", temp.get("sbst_no"));
			
			// 기존 데이터 존재 여부 체크
			Map<String, ?> map = service.selectSbstRgltDtlChk(chkParam);
			if ("Y".equals(map.get("up_yn"))) {
				temp.put("chg_type_cd", "U");
				
				n = service.insertSbstRgltDtlList(temp);
				nH = service.insertSbstRgltDtlHst(temp);
			}
		}
		
		// 삭제
		for(int i = 0; i < deleteList.size(); i++) {
			Map<String, String> temp = deleteList.get(i);
			temp.put("chg_type_cd", "D");
			temp.put("del_yn", "Y");
			n = service.deleteSbstRgltDtlList(temp);
			nH = service.insertSbstRgltDtlHst(temp);
		}
		
		String result = n > -1? Constant.SUCCESS : Constant.FAIL;
		String resultH = nH > -1? Constant.SUCCESS : Constant.FAIL;
		
		if (!result.equals(resultH)) {
			result = Constant.FAIL;
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("rglt-sbst-list")
	public ResponseEntity<?> selectRgltSbstList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		List<Map<String, ?>> list = service.selectRgltSbstList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("rglt-sbst-hst-list")
	public ResponseEntity<?> selectRgltSbstChgHstList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		List<Map<String, ?>> list = service.selectRgltSbstChgHstList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
}
