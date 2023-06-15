package com.kist.portal.sh.sftchk.rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.kist.portal.common.aprv.service.AprvService;
import com.kist.portal.common.comm.service.FileService;
import com.kist.portal.common.email.rest.SendEmailUtil;
import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.common.utils.MultiLanguageUtil;
import com.kist.portal.sh.sftchk.service.SftChkService;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/she/sftchk")
public class SftChkController {

Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private SftChkService service;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private SendEmailUtil util;
	
	@Autowired
	private MultiLanguageUtil mUtil;
	
	@Value("${dgrm.front-url}")
	private String frontUrl;
	
	@Value("${dgrm.server-url}")
	private String serverUrl;
	
	@Autowired
	private AprvService aprvService;
	
	@GetMapping("/oprnbgnbfchkgtmgntlist")
	public ResponseEntity<?> selectOprbChkTgtList(@RequestParam Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		List<Map<String, ?>> list = service.selectOprbTgtChkList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/oprnbgnbfchkmgntlist")
	public ResponseEntity<?> selectOprbChkList(@RequestParam Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		List<Map<String, ?>> list = service.selectOprbChkList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/oprnbgnbfchkgtmgnt")
	public ResponseEntity<?> selectOprbChkTgtMgnt(@RequestParam Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		Map<String, List<Map<String, ?>>> result = new HashMap<>();

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		List<Map<String, ?>> oprbChkResult = service.selectOprbChkTgtMgnt(param);
		List<Map<String, ?>> oprbChkEqpResult = service.selectOprbChkTgtEqp(param);
		
		result.put("oprbChkResult", oprbChkResult);
		result.put("oprbChkEqpResult", oprbChkEqpResult);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/oprnbgnbfchkmgnt")
	public ResponseEntity<?> selectOprbChkMgnt(@RequestParam Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		Map<String, List<Map<String, ?>>> result = new HashMap<>();

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		// 기본 정보 가져오기
		List<Map<String, ?>> oprbChkResult = service.selectOprbChkMgnt(param);
		// 그리드 리스트
		param.put("oprb_chk_tgt_id", oprbChkResult.get(0).get("oprb_chk_tgt_id"));
		List<Map<String, ?>> oprbChkEqpResult = service.selectOprbChkEqp(param);
		
		result.put("oprbChkResult", oprbChkResult);
		result.put("oprbChkEqpResult", oprbChkEqpResult);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@Transactional
	@PostMapping("/oprnbgnbfchkgtmgnt")
	public ResponseEntity<?> insertOprbChkMgnt(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		param.put("SESS_ID", userInfo.getUsrId());
		
		//insert, update, delete 데이터 초기화
		List<Map<String, Object>> insertList = (List<Map<String, Object>>)param.get("insertRow");
		List<Map<String, Object>> updateList = (List<Map<String, Object>>)param.get("updateRow");
		List<Map<String, Object>> deleteList = (List<Map<String, Object>>)param.get("deleteRow");

		String result = "";
		// 데이터 신규 여부 체크
		if(param.get("oprb_chk_tgt_id") != null && !(String.valueOf(param.get("oprb_chk_tgt_id")).equalsIgnoreCase(""))) {
			result = String.valueOf(param.get("oprb_chk_tgt_id"));
			// 기존 데이터 업데이트
			service.updateOprbChkTgt(param);
			if (insertList != null) {
				// 인서트
				for(int i = 0; i < insertList.size(); i++) {
					Map<String, Object> temp = insertList.get(i);
					temp.put("oprb_chk_tgt_id", String.valueOf(param.get("oprb_chk_tgt_id")));
					temp.put("wkpl_id", String.valueOf(param.get("wkpl_id")));
					temp.put("SESS_ID", userInfo.getUsrId());
					
					service.insertOprbChkTgtEqp(temp);
				}
			}
			
			if (updateList != null) {
				// 업데이트
				for(int i = 0; i < updateList.size(); i++) {
					Map<String, Object> temp = updateList.get(i);
					temp.put("oprb_chk_tgt_id", String.valueOf(param.get("oprb_chk_tgt_id")));
					temp.put("wkpl_id", String.valueOf(param.get("wkpl_id")));
					temp.put("SESS_ID", userInfo.getUsrId());
					
					service.updateOprbChkTgtEqp(temp);
				}
			}
			
			if (deleteList != null) {
				// 딜리트
				for(int i = 0; i < deleteList.size(); i++) {
					Map<String, Object> temp = deleteList.get(i);
					temp.put("oprb_chk_tgt_id", String.valueOf(param.get("oprb_chk_tgt_id")));
					temp.put("wkpl_id", String.valueOf(param.get("wkpl_id")));
					temp.put("SESS_ID", userInfo.getUsrId());
					
					service.deleteOprbChkTgtEqp(temp);
				}
			}
		}else {
			// 신규 번호 채번
			String key = service.selectOprbChkKey(param);
			param.put("oprb_chk_tgt_id", key);
			result = key;
			
			service.insertOprbChkTgt(param);
			
			if (insertList != null) {
				// 인서트
				for(int i = 0; i < insertList.size(); i++) {
					Map<String, Object> temp = insertList.get(i);
					temp.put("oprb_chk_tgt_id", key);
					temp.put("wkpl_id", String.valueOf(param.get("wkpl_id")));
					temp.put("SESS_ID", userInfo.getUsrId());
					
					service.insertOprbChkTgtEqp(temp);
				}
			}
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/auth-check")
	public ResponseEntity<?> selectOprbChkAuth(@RequestParam Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_ID", userInfo.getUsrId());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		
		String result = "";
		
		List<Map<String, ?>> list = null;

		if(String.valueOf(param.get("gubun")).equalsIgnoreCase("OPTGT")) {
			list = service.selectOprbChkTgtAuth(param);
		} else if(String.valueOf(param.get("gubun")).equalsIgnoreCase("OP")) {
			list = service.selectOprbChkAuth(param);
		} else if(String.valueOf(param.get("gubun")).equalsIgnoreCase("WKTGT")) {
			list = service.selectWkChkTgtAuth(param);
		} else if(String.valueOf(param.get("gubun")).equalsIgnoreCase("WCHK")) {
			list = service.selectWkChkAuth(param);
		} else if(String.valueOf(param.get("gubun")).equalsIgnoreCase("JNTGT")) {
			list = service.selectJnChkAuth(param);
		} else if(String.valueOf(param.get("gubun")).equalsIgnoreCase("JCHK")) {
			list = service.selectCoprChkAuth(param);
		} else if(String.valueOf(param.get("gubun")).equalsIgnoreCase("CRIN")) {
			list = service.selectCrinChkAuth(param);
		}
		result = list.size() > 0 ? Constant.SUCCESS : Constant.FAIL;
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/oprnbgnbfchkmgntchkrslt")
	public ResponseEntity<?> selectOprbChkRslt(@RequestParam Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		Map<String, List<Map<String, ?>>> result = new HashMap<>();

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		// 기본 정보
		List<Map<String, ?>> oprbChkResult = service.selectOprbChkBasicRglt(param);
		// 시정지시 정보
		String chkId = String.valueOf(oprbChkResult.get(0).get("oprb_chk_id"));
		param.put("oprb_chk_id", chkId);
		List<Map<String, ?>> oprbCrinResult = service.selectOprbChkCrinRslt(param);
		// 점검 결과
		List<Map<String, ?>> oprbChkList = service.selectOprbChkRsltList(param);
		
		result.put("oprbChkResult", oprbChkResult);
		result.put("oprbCrinResult", oprbCrinResult);
		result.put("oprbChkList", oprbChkList);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PutMapping("/oprnbgnbfchkmgntchkrslt")
	@Transactional
	public ResponseEntity<?> updateOprbChkRslt(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		Map<String, List<Map<String, ?>>> result = new HashMap<>();

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		param.put("SESS_ID", userInfo.getUsrId());
		
		// 구분값 제거 처리
		String oprbChkDt = String.valueOf(param.get("oprb_chk_dt")).replace("-", "");
		param.put("oprb_chk_dt", oprbChkDt);
		String actnHopeDt = String.valueOf(param.get("actn_hope_dt")).replace("-", "");
		param.put("actn_hope_dt", actnHopeDt);
		String oprbChkHm = String.valueOf(param.get("oprb_chk_hm")).replace(":", "");
		param.put("oprb_chk_hm", oprbChkHm);

		// 기본 정보
		service.updateOprbChkBasicRglt(param);
		
		List<Map<String, ?>> oprbChkResult = service.selectOprbChkBasicRglt(param);
		param.put("wkpl_id", oprbChkResult.get(0).get("wkpl_id"));
		param.put("crin_chk_wkpl_loc_id", oprbChkResult.get(0).get("oprb_chk_wkpl_loc_id"));
		param.put("crin_chk_dtl_loc_nm", oprbChkResult.get(0).get("oprb_chk_dtl_loc_nm"));
		param.put("crin_chk_note_txt", oprbChkResult.get(0).get("oprb_chk_note_txt"));
		param.put("crin_chk_supe_usr_id", oprbChkResult.get(0).get("oprb_chk_rspn_usr_id"));
		param.put("crin_chk_dt", oprbChkResult.get(0).get("oprb_chk_dt"));
		param.put("crin_chk_hm", oprbChkResult.get(0).get("oprb_chk_hm"));
		param.put("crin_chk_tgt_nm", oprbChkResult.get(0).get("oprb_chk_tgt_nm"));
		
		// 시정지시 정보(입력된 시정지시서 정보가 없다면 처리하지 않는다)
		if(String.valueOf(param.get("crinYn")).equalsIgnoreCase("false")) {
			String crinId = String.valueOf(oprbChkResult.get(0).get("crin_id"));
			if(oprbChkResult.get(0).get("crin_id") != null && !crinId.equalsIgnoreCase("")) {
				param.put("crin_id", crinId);
				// 시정 지시서가 기존에도 존재할 경우 시정지시서 삭제처리
				service.deleteOprbChkCrinRslt(param);
			}
			
			param.put("crin_id", "");
		} else {
			String crinId = String.valueOf(oprbChkResult.get(0).get("crin_id"));
			if(oprbChkResult.get(0).get("crin_id") == null || crinId.equalsIgnoreCase("")) {
				// 시정 지시서가 존재하지 않을 경우 insert
				String crinKey = service.selectOprbCrinKey(param);
				param.put("crin_id", crinKey);
				int n = service.insertOprbChkCrinRslt(param);
				// 시정지시 메일 전송
				if(n > 0) {
					sendCrinNotiMail(crinKey);
				}
				
			} else {
				// 시정 지시서가 기존에도 존재할 경우 update
				param.put("crin_id", crinId);
				service.updateOprbChkCrinRslt(param);
			}
		}
		
		List<Map<String, Object>> updateRow = (List<Map<String, Object>>)param.get("updateRow");
		// 점검 결과
		if(updateRow != null) {
			for(int i = 0; i < updateRow.size(); i++) {
				param.put("oprn_bf_chec_cl_cd", updateRow.get(i).get("oprn_bf_chec_cl_cd"));
				param.put("oprn_bf_chec_dtl_cl_cd", updateRow.get(i).get("oprn_bf_chec_dtl_cl_cd"));
				param.put("oprn_bf_chec_sn", updateRow.get(i).get("oprn_bf_chec_sn"));
				param.put("chec_lang_cd", updateRow.get(i).get("chec_lang_cd"));
				param.put("chec_chk_rslt_vl", updateRow.get(i).get("chec_chk_rslt_vl"));
				service.updateOprbChkRsltList(param);
			}
		}
		
		return new ResponseEntity<>(Constant.SUCCESS, HttpStatus.OK);
	}
	
	@PutMapping("/oprnbgnbfchkmgntchkrsltmb")
	@Transactional
	public ResponseEntity<?> updateOprbChkRsltMb(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		Map<String, List<Map<String, ?>>> result = new HashMap<>();

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		param.put("SESS_ID", userInfo.getUsrId());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = Calendar.getInstance();
        String strToday = sdf.format(c1.getTime());
        SimpleDateFormat sdf2 = new SimpleDateFormat("HHmm");
        String strTime = sdf2.format(c1.getTime());
		// 구분값 제거 처리
		param.put("oprb_chk_dt", strToday);
		param.put("oprb_chk_hm", strTime);
		param.put("oprb_chk_rspn_usr_id", userInfo.getUsrId());
		
		// 기본 정보
		service.updateOprbChkBasicRglt(param);
		
		List<Map<String, Object>> updateRow = (List<Map<String, Object>>)param.get("saveDatas");
		// 점검 결과
		if(updateRow != null) {
			for(int i = 0; i < updateRow.size(); i++) {
				param.put("oprn_bf_chec_cl_cd", updateRow.get(i).get("oprn_bf_chec_cl_cd"));
				param.put("oprn_bf_chec_dtl_cl_cd", updateRow.get(i).get("oprn_bf_chec_dtl_cl_cd"));
				param.put("oprn_bf_chec_sn", updateRow.get(i).get("oprn_bf_chec_sn"));
				param.put("chec_lang_cd", updateRow.get(i).get("chec_lang_cd"));
				param.put("chec_chk_rslt_vl", updateRow.get(i).get("chec_chk_rslt_vl"));
				service.updateOprbChkRsltList(param);
			}
		}
		
		return new ResponseEntity<>(Constant.SUCCESS, HttpStatus.OK);
	}
	
	@GetMapping("/wknstourchktgtmgntlist")
	public ResponseEntity<?> selectCircTgtList(@RequestParam Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		List<Map<String, ?>> list = service.selectCircTgtChkList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/wknstourchktgtmgnt")
	public ResponseEntity<?> selectWknsChkTgtMgnt(@RequestParam Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		Map<String, List<Map<String, ?>>> result = new HashMap<>();

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		// 기본 정보 가져오기
		List<Map<String, ?>> wknsChkResult = service.selectWknsChkTgtMgnt(param);
		// 그리드 리스트
		param.put("oprb_chk_tgt_id", wknsChkResult.get(0).get("circ_chk_tgt_id"));
		List<Map<String, ?>> wknsAtndResult = service.selectWknsChkTgtAtndList(param);
		
		result.put("wknsChkResult", wknsChkResult);
		result.put("wknsAtndResult", wknsAtndResult);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@Transactional
	@PostMapping("/wknstourchktgtmgnt")
	public ResponseEntity<?> insertWknsChkTgtMgnt(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		param.put("SESS_ID", userInfo.getUsrId());
		
		//insert, update, delete 데이터 초기화
		List<Map<String, Object>> insertList = (List<Map<String, Object>>)param.get("insertRow");
		List<Map<String, Object>> updateList = (List<Map<String, Object>>)param.get("updateRow");
		List<Map<String, Object>> deleteList = (List<Map<String, Object>>)param.get("deleteRow");

		String result = "";
		// 데이터 신규 여부 체크
		if(param.get("circ_chk_tgt_id") != null && !(String.valueOf(param.get("circ_chk_tgt_id")).equalsIgnoreCase(""))) {
			result = String.valueOf(param.get("circ_chk_tgt_id"));
			// 기존 데이터 업데이트
			service.updateWknsChkTgt(param);
			if (insertList != null) {
				// 인서트
				for(int i = 0; i < insertList.size(); i++) {
					Map<String, Object> temp = insertList.get(i);
					temp.put("circ_chk_tgt_id", String.valueOf(param.get("circ_chk_tgt_id")));
					temp.put("wkpl_id", String.valueOf(param.get("wkpl_id")));
					temp.put("SESS_ID", userInfo.getUsrId());
					
					service.insertWknsTgtAtndList(temp);
				}
			}
			
			if (updateList != null) {
				// 업데이트
				for(int i = 0; i < updateList.size(); i++) {
					Map<String, Object> temp = updateList.get(i);
					temp.put("circ_chk_tgt_id", String.valueOf(param.get("circ_chk_tgt_id")));
					temp.put("wkpl_id", String.valueOf(param.get("wkpl_id")));
					temp.put("SESS_ID", userInfo.getUsrId());
					
					service.updateWknsTgtAtndList(temp);
				}
			}
			
			if (deleteList != null) {
				// 딜리트
				for(int i = 0; i < deleteList.size(); i++) {
					Map<String, Object> temp = deleteList.get(i);
					temp.put("circ_chk_tgt_id", String.valueOf(param.get("circ_chk_tgt_id")));
					temp.put("wkpl_id", String.valueOf(param.get("wkpl_id")));
					temp.put("SESS_ID", userInfo.getUsrId());
					
					service.deleteWknsTgtAtndList(temp);
				}
			}
		}else {
			// 신규 번호 채번
			String key = service.selectWknsChkTgtKey(param);
			param.put("circ_chk_tgt_id", key);
			result = key;
			
			service.insertWknsChkTgt(param);
			
			if (insertList != null) {
				// 인서트
				for(int i = 0; i < insertList.size(); i++) {
					Map<String, Object> temp = insertList.get(i);
					temp.put("circ_chk_tgt_id", key);
					temp.put("wkpl_id", String.valueOf(param.get("wkpl_id")));
					temp.put("SESS_ID", userInfo.getUsrId());
					
					service.insertWknsTgtAtndList(temp);
				}
			}
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/wknstourchkmgntlist")
	public ResponseEntity<?> selectWknsChkList(@RequestParam Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		List<Map<String, ?>> list = service.selectWknsChkList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/wknstourchkmgntchkrslt")
	public ResponseEntity<?> selectWknsChkMgnt(@RequestParam Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		Map<String, List<Map<String, ?>>> result = new HashMap<>();

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		// 기본 정보 가져오기
		List<Map<String, ?>> wknsChkResult = service.selectWknsChkMgnt(param);
		// 참석자 리스트
		List<Map<String, ?>> wknsChkAtndList = service.selectWknsChkAtndList(param);
		// 체크리스트 
		List<Map<String, ?>> wknsChkList = service.selectWknsChkResult(param);
		// 시정 지시서 정보
		String chkId = String.valueOf(wknsChkResult.get(0).get("circ_chk_id"));
		param.put("circ_chk_id", chkId);
		List<Map<String, ?>> wknsCrinResult = service.selectWknsChkCrinRslt(param);
		
		result.put("wknsChkResult", wknsChkResult);
		result.put("wknsChkAtndList", wknsChkAtndList);
		result.put("wknsChkList", wknsChkList);
		result.put("wknsCrinResult", wknsCrinResult);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PutMapping("/wknstourchkmgntchkrslt")
	@Transactional
	public ResponseEntity<?> updateWknsChkRslt(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		Map<String, List<Map<String, ?>>> result = new HashMap<>();

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		param.put("SESS_ID", userInfo.getUsrId());
		
		// 구분값 제거 처리
		String circChkDt = String.valueOf(param.get("circ_chk_dt")).replace("-", "");
		param.put("circ_chk_dt", circChkDt);
		String actnHopeDt = String.valueOf(param.get("actn_hope_dt")).replace("-", "");
		param.put("actn_hope_dt", actnHopeDt);
		String circChkHm = String.valueOf(param.get("circ_chk_hm")).replace(":", "");
		param.put("circ_chk_hm", circChkHm);

		// 기본 정보
		service.updateWknsChkRglt(param);
		
		List<Map<String, ?>> wknsChkResult = service.selectWknsChkMgnt(param);
		param.put("wkpl_id", wknsChkResult.get(0).get("wkpl_id"));
		param.put("crin_chk_wkpl_loc_id", wknsChkResult.get(0).get("circ_chk_wkpl_loc_id"));
		param.put("crin_chk_dtl_loc_nm", wknsChkResult.get(0).get("circ_chk_dtl_loc_nm"));
		param.put("crin_chk_note_txt", wknsChkResult.get(0).get("circ_chk_note_txt"));
		param.put("crin_chk_supe_usr_id", wknsChkResult.get(0).get("circ_chk_supe_usr_id"));
		param.put("crin_chk_dt", wknsChkResult.get(0).get("circ_chk_dt"));
		param.put("crin_chk_hm", wknsChkResult.get(0).get("circ_chk_hm"));
		param.put("crin_chk_tgt_nm", wknsChkResult.get(0).get("circ_chk_tgt_nm"));
		
		// 시정지시 정보(입력된 시정지시서 정보가 없다면 처리하지 않는다)
		if(String.valueOf(param.get("crinYn")).equalsIgnoreCase("false")) {
			String crinId = String.valueOf(wknsChkResult.get(0).get("crin_id"));
			if(wknsChkResult.get(0).get("crin_id") != null && !crinId.equalsIgnoreCase("")) {
				param.put("crin_id", crinId);
				// 시정 지시서가 기존에도 존재할 경우 시정지시서 삭제처리
				service.deleteOprbChkCrinRslt(param); // 기존 코드와 동일 쿼리 사용(작업 전 점검)
			}
			
			param.put("crin_id", "");
		} else {
			String crinId = String.valueOf(wknsChkResult.get(0).get("crin_id"));
			if(wknsChkResult.get(0).get("crin_id") == null || crinId.equalsIgnoreCase("")) {
				// 시정 지시서가 존재하지 않을 경우 insert
				String crinKey = service.selectOprbCrinKey(param);
				param.put("crin_id", crinKey);
				if(String.valueOf(param.get("sch_gb")).equalsIgnoreCase("tour")) {
					param.put("crin_cl_cd", "CRN02");
				} else {
					param.put("crin_cl_cd", "CRN05");
				}
				int n = service.insertWknsChkCrinRslt(param);
				if(n > 0) {
					sendCrinNotiMail(crinKey);
				}
			} else {
				// 시정 지시서가 기존에도 존재할 경우 update
				param.put("crin_id", crinId);
				service.updateOprbChkCrinRslt(param); // 기존 코드와 동일 쿼리 사용(작업 전 점검)
			}
		}
		
		//insert, update, delete 데이터 초기화
		List<Map<String, Object>> insertList = (List<Map<String, Object>>)param.get("atndInsert");
		List<Map<String, Object>> updateList = (List<Map<String, Object>>)param.get("atndUpdate");
		List<Map<String, Object>> deleteList = (List<Map<String, Object>>)param.get("atndDelete");
		
		if (insertList != null) {
			// 인서트
			for(int i = 0; i < insertList.size(); i++) {
				Map<String, Object> temp = insertList.get(i);
				temp.put("circ_chk_id", String.valueOf(param.get("circ_chk_id")));
				temp.put("circ_chk_tgt_id", wknsChkResult.get(0).get("circ_chk_tgt_id"));
				temp.put("wkpl_id", String.valueOf(param.get("wkpl_id")));
				temp.put("SESS_ID", userInfo.getUsrId());
				
				service.insertWknsAtndList(temp);
			}
		}
		
		if (updateList != null) {
			// 업데이트
			for(int i = 0; i < updateList.size(); i++) {
				Map<String, Object> temp = updateList.get(i);
				temp.put("circ_chk_id", String.valueOf(param.get("circ_chk_id")));
				temp.put("circ_chk_tgt_id", wknsChkResult.get(0).get("circ_chk_tgt_id"));
				temp.put("wkpl_id", String.valueOf(param.get("wkpl_id")));
				temp.put("SESS_ID", userInfo.getUsrId());
				
				service.updateWknsAtndList(temp);
			}
		}
		
		if (deleteList != null) {
			// 딜리트
			for(int i = 0; i < deleteList.size(); i++) {
				Map<String, Object> temp = deleteList.get(i);
				temp.put("circ_chk_id", String.valueOf(param.get("circ_chk_id")));
				temp.put("circ_chk_tgt_id", wknsChkResult.get(0).get("circ_chk_tgt_id"));
				temp.put("wkpl_id", String.valueOf(param.get("wkpl_id")));
				temp.put("SESS_ID", userInfo.getUsrId());
				
				service.deleteWknsAtndList(temp);
			}
		}
		
		List<Map<String, Object>> updateRow = (List<Map<String, Object>>)param.get("resultRow");
		// 점검 결과
		if(updateRow != null) {
			for(int i = 0; i < updateRow.size(); i++) {
				param.put("chec_chk_cl_cd", updateRow.get(i).get("chec_chk_cl_cd"));
				param.put("circ_chk_chec_sn", updateRow.get(i).get("circ_chk_chec_sn"));
				param.put("chec_lang_cd", updateRow.get(i).get("chec_lang_cd"));
				param.put("chec_chk_rslt_vl", updateRow.get(i).get("chec_chk_rslt_vl"));
				service.updateWknsChkRsltList(param);
			}
		}
		
		return new ResponseEntity<>(Constant.SUCCESS, HttpStatus.OK);
	}
	
	@PutMapping("/wknstourchkmgntchkrsltmb")
	@Transactional
	public ResponseEntity<?> updateWknsChkRsltMb(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		Map<String, List<Map<String, ?>>> result = new HashMap<>();

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		param.put("SESS_ID", userInfo.getUsrId());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = Calendar.getInstance();
        String strToday = sdf.format(c1.getTime());
        SimpleDateFormat sdf2 = new SimpleDateFormat("HHmm");
        String strTime = sdf2.format(c1.getTime());
		
		// 구분값 제거 처리
		param.put("circ_chk_dt", strToday);
		param.put("circ_chk_hm", strTime);
		param.put("circ_chk_supe_usr_id", userInfo.getUsrId());

		// 기본 정보
		service.updateWknsChkRglt(param);
		
		List<Map<String, Object>> updateRow = (List<Map<String, Object>>)param.get("saveDatas");
		// 점검 결과
		if(updateRow != null) {
			for(int i = 0; i < updateRow.size(); i++) {
				param.put("chec_chk_cl_cd", updateRow.get(i).get("chec_chk_cl_cd"));
				param.put("circ_chk_chec_sn", updateRow.get(i).get("circ_chk_chec_sn"));
				param.put("chec_lang_cd", updateRow.get(i).get("chec_lang_cd"));
				param.put("chec_chk_rslt_vl", updateRow.get(i).get("chec_chk_rslt_vl"));
				service.updateWknsChkRsltList(param);
			}
		}
		
		return new ResponseEntity<>(Constant.SUCCESS, HttpStatus.OK);
	}
	
	@GetMapping("/wknscoprchktgtmgntlist")
	public ResponseEntity<?> selectJntTgtList(@RequestParam Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		List<Map<String, ?>> list = service.selectJntTgtChkList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/wknscoprchktgtmgnt")
	public ResponseEntity<?> selectJntChkTgtMgnt(@RequestParam Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		Map<String, List<Map<String, ?>>> result = new HashMap<>();

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		// 기본 정보 가져오기
		List<Map<String, ?>> jntChkResult = service.selectJntChkTgtMgnt(param);
		// 참석자 리스트
		param.put("jnt_chk_tgt_id", jntChkResult.get(0).get("jnt_chk_tgt_id"));
		List<Map<String, ?>> jntAtndResult = service.selectJntChkTgtAtndList(param);
		// 점검 예정일 리스트
		List<Map<String, ?>> jntDtResult = service.selectJntTgtDtList(param);
		
		result.put("jntChkResult", jntChkResult);
		result.put("jntAtndResult", jntAtndResult);
		result.put("jntDtResult", jntDtResult);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@Transactional
	@PostMapping("/wknscoprchktgtmgnt")
	public ResponseEntity<?> insertJntChkTgtMgnt(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		param.put("SESS_ID", userInfo.getUsrId());
		
		//insert, update, delete 데이터 초기화
		List<Map<String, Object>> atnInsertList = (List<Map<String, Object>>)param.get("atninsert");
		List<Map<String, Object>> atnUpdateList = (List<Map<String, Object>>)param.get("atnupdate");
		List<Map<String, Object>> atnDeleteList = (List<Map<String, Object>>)param.get("atndelete");
		
		//insert, update, delete 데이터 초기화
		List<Map<String, Object>> dtInsertList = (List<Map<String, Object>>)param.get("dtinsert");
		List<Map<String, Object>> dtUpdateList = (List<Map<String, Object>>)param.get("dtupdate");
		List<Map<String, Object>> dtDeleteList = (List<Map<String, Object>>)param.get("dtdelete");

		String result = "";
		// 데이터 신규 여부 체크
		if(param.get("jnt_chk_tgt_id") != null && !(String.valueOf(param.get("jnt_chk_tgt_id")).equalsIgnoreCase(""))) {
			result = String.valueOf(param.get("jnt_chk_tgt_id"));
			// 기존 데이터 업데이트
			service.updateJntChkTgt(param);
			if (atnInsertList != null) {
				// 인서트
				for(int i = 0; i < atnInsertList.size(); i++) {
					Map<String, Object> temp = atnInsertList.get(i);
					temp.put("jnt_chk_tgt_id", String.valueOf(param.get("jnt_chk_tgt_id")));
					temp.put("wkpl_id", String.valueOf(param.get("wkpl_id")));
					temp.put("SESS_ID", userInfo.getUsrId());
					
					service.insertJntTgtAtndList(temp);
				}
			}
			
			if (atnUpdateList != null) {
				// 업데이트
				for(int i = 0; i < atnUpdateList.size(); i++) {
					Map<String, Object> temp = atnUpdateList.get(i);
					temp.put("jnt_chk_tgt_id", String.valueOf(param.get("jnt_chk_tgt_id")));
					temp.put("wkpl_id", String.valueOf(param.get("wkpl_id")));
					temp.put("SESS_ID", userInfo.getUsrId());
					
					service.updateJntTgtAtndList(temp);
				}
			}
			
			if (atnDeleteList != null) {
				// 딜리트
				for(int i = 0; i < atnDeleteList.size(); i++) {
					Map<String, Object> temp = atnDeleteList.get(i);
					temp.put("jnt_chk_tgt_id", String.valueOf(param.get("jnt_chk_tgt_id")));
					temp.put("wkpl_id", String.valueOf(param.get("wkpl_id")));
					temp.put("SESS_ID", userInfo.getUsrId());
					
					service.deleteJntTgtAtndList(temp);
				}
			}
			
			if (dtInsertList != null) {
				// 인서트
				for(int i = 0; i < dtInsertList.size(); i++) {
					Map<String, Object> temp = dtInsertList.get(i);
					temp.put("jnt_chk_tgt_id", String.valueOf(param.get("jnt_chk_tgt_id")));
					temp.put("jnt_chk_schd_dttm", String.valueOf(dtInsertList.get(i).get("jnt_chk_schd_dt")) + (String.valueOf(dtInsertList.get(i).get("jnt_chk_schd_tm"))).replaceAll(":", ""));
					temp.put("wkpl_id", String.valueOf(param.get("wkpl_id")));
					temp.put("SESS_ID", userInfo.getUsrId());
					
					service.insertJntTgtDtList(temp);
				}
			}
			
			if (dtUpdateList != null) {
				// 업데이트
				for(int i = 0; i < dtUpdateList.size(); i++) {
					Map<String, Object> temp = dtUpdateList.get(i);
					temp.put("jnt_chk_tgt_id", String.valueOf(param.get("jnt_chk_tgt_id")));
					temp.put("jnt_chk_schd_dttm", String.valueOf(dtUpdateList.get(i).get("jnt_chk_schd_dt")) + (String.valueOf(dtUpdateList.get(i).get("jnt_chk_schd_tm"))).replaceAll(":", ""));
					temp.put("wkpl_id", String.valueOf(param.get("wkpl_id")));
					temp.put("SESS_ID", userInfo.getUsrId());
					
					service.updateJntTgtDtList(temp);
				}
			}
			
			if (dtDeleteList != null) {
				// 딜리트
				for(int i = 0; i < dtDeleteList.size(); i++) {
					Map<String, Object> temp = dtDeleteList.get(i);
					temp.put("jnt_chk_tgt_id", String.valueOf(param.get("jnt_chk_tgt_id")));
					temp.put("jnt_chk_schd_dttm", String.valueOf(dtDeleteList.get(i).get("jnt_chk_schd_dt")) + (String.valueOf(dtDeleteList.get(i).get("jnt_chk_schd_tm"))).replaceAll(":", ""));
					temp.put("wkpl_id", String.valueOf(param.get("wkpl_id")));
					temp.put("SESS_ID", userInfo.getUsrId());
					
					service.deleteJntTgtDtList(temp);
				}
			}
		}else {
			// 신규 번호 채번
			String key = service.selectJntChkTgtKey(param);
			param.put("jnt_chk_tgt_id", key);
			result = key;
			
			service.insertJntChkTgt(param);
			
			if (atnInsertList != null) {
				// 인서트
				for(int i = 0; i < atnInsertList.size(); i++) {
					Map<String, Object> temp = atnInsertList.get(i);
					temp.put("jnt_chk_tgt_id", key);
					temp.put("wkpl_id", String.valueOf(param.get("wkpl_id")));
					temp.put("SESS_ID", userInfo.getUsrId());
					
					service.insertJntTgtAtndList(temp);
				}
			}
			
			if (dtInsertList != null) {
				// 인서트
				for(int i = 0; i < dtInsertList.size(); i++) {
					Map<String, Object> temp = dtInsertList.get(i);
					temp.put("jnt_chk_tgt_id", String.valueOf(param.get("jnt_chk_tgt_id")));
					temp.put("jnt_chk_schd_dttm", String.valueOf(dtInsertList.get(i).get("jnt_chk_schd_dt")) + (String.valueOf(dtInsertList.get(i).get("jnt_chk_schd_tm"))).replaceAll(":", "")); // YYYYMMDDHHMI
					temp.put("wkpl_id", String.valueOf(param.get("wkpl_id")));
					temp.put("SESS_ID", userInfo.getUsrId());
					
					service.insertJntTgtDtList(temp);
				}
			}
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/wknscoprchkmgntlist")
	public ResponseEntity<?> selectCoprChkList(@RequestParam Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		List<Map<String, ?>> list = service.selectCoprChkList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/wknscoprchkmgnt")
	public ResponseEntity<?> selectCoprChkMgnt(@RequestParam Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		Map<String, List<Map<String, ?>>> result = new HashMap<>();
		
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		
		// 기본 정보 가져오기
		List<Map<String, ?>> jntChkResult = service.selectCoprChkMgnt(param);
		// 참석자 리스트
		List<Map<String, ?>> jntAtndResult = service.selectJntChkAtndList(param);
		
		result.put("jntChkResult", jntChkResult);
		result.put("jntAtndResult", jntAtndResult);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@Transactional
	@PostMapping("/wknscoprchkmgnt")
	public ResponseEntity<?> insertJntChkMgnt(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		param.put("SESS_ID", userInfo.getUsrId());
		
		//insert, update, delete 데이터 초기화
		List<Map<String, Object>> atnInsertList = (List<Map<String, Object>>)param.get("atninsert");
		List<Map<String, Object>> atnUpdateList = (List<Map<String, Object>>)param.get("atnupdate");
		List<Map<String, Object>> atnDeleteList = (List<Map<String, Object>>)param.get("atndelete");
		
		// 구분값 제거 처리
		param.put("jnt_chk_dt", String.valueOf(param.get("jnt_chk_dt")).replace("-", ""));
		param.put("jnt_chk_hm", String.valueOf(param.get("jnt_chk_hm_h")).concat(String.valueOf(param.get("jnt_chk_hm_s"))));

		String result = String.valueOf(param.get("jnt_chk_id"));
		// 기존 데이터 업데이트
		service.updateJntChkMgnt(param);
		if (atnInsertList != null) {
			// 인서트
			for(int i = 0; i < atnInsertList.size(); i++) {
				Map<String, Object> temp = atnInsertList.get(i);
				temp.put("jnt_chk_id", String.valueOf(param.get("jnt_chk_id")));
				temp.put("jnt_chk_tgt_id", String.valueOf(param.get("jnt_chk_tgt_id")));
				temp.put("wkpl_id", String.valueOf(param.get("wkpl_id")));
				temp.put("SESS_ID", userInfo.getUsrId());
				
				service.insertJntAtndList(temp);
			}
		}
		
		if (atnUpdateList != null) {
			// 업데이트
			for(int i = 0; i < atnUpdateList.size(); i++) {
				Map<String, Object> temp = atnUpdateList.get(i);
				temp.put("jnt_chk_id", String.valueOf(param.get("jnt_chk_id")));
				temp.put("jnt_chk_tgt_id", String.valueOf(param.get("jnt_chk_tgt_id")));
				temp.put("wkpl_id", String.valueOf(param.get("wkpl_id")));
				temp.put("SESS_ID", userInfo.getUsrId());
				
				service.updateJntAtndList(temp);
			}
		}
		
		if (atnDeleteList != null) {
			// 딜리트
			for(int i = 0; i < atnDeleteList.size(); i++) {
				Map<String, Object> temp = atnDeleteList.get(i);
				temp.put("jnt_chk_id", String.valueOf(param.get("jnt_chk_id")));
				temp.put("jnt_chk_tgt_id", String.valueOf(param.get("jnt_chk_tgt_id")));
				temp.put("wkpl_id", String.valueOf(param.get("wkpl_id")));
				temp.put("SESS_ID", userInfo.getUsrId());
				
				service.deleteJntAtndList(temp);
			}
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@Transactional
	@PostMapping("/wknscoprchkmgntaprv")
	public ResponseEntity<?> insertJntChkMgntAprv(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		param.put("SESS_ID", userInfo.getUsrId());
		
		//insert, update, delete 데이터 초기화
		List<Map<String, Object>> atnInsertList = (List<Map<String, Object>>)param.get("atninsert");
		List<Map<String, Object>> atnUpdateList = (List<Map<String, Object>>)param.get("atnupdate");
		List<Map<String, Object>> atnDeleteList = (List<Map<String, Object>>)param.get("atndelete");
		
		// 구분값 제거 처리
		param.put("jnt_chk_dt", String.valueOf(param.get("jnt_chk_dt")).replace("-", ""));
		param.put("jnt_chk_hm", String.valueOf(param.get("jnt_chk_hm_h")).concat(String.valueOf(param.get("jnt_chk_hm_s"))));

		String result = String.valueOf(param.get("jnt_chk_id"));
		// 기존 데이터 업데이트
		service.updateJntChkMgnt(param);
		if (atnInsertList != null) {
			// 인서트
			for(int i = 0; i < atnInsertList.size(); i++) {
				Map<String, Object> temp = atnInsertList.get(i);
				temp.put("jnt_chk_id", String.valueOf(param.get("jnt_chk_id")));
				temp.put("jnt_chk_tgt_id", String.valueOf(param.get("jnt_chk_tgt_id")));
				temp.put("wkpl_id", String.valueOf(param.get("wkpl_id")));
				temp.put("SESS_ID", userInfo.getUsrId());
				
				service.insertJntAtndList(temp);
			}
		}
		
		if (atnUpdateList != null) {
			// 업데이트
			for(int i = 0; i < atnUpdateList.size(); i++) {
				Map<String, Object> temp = atnUpdateList.get(i);
				temp.put("jnt_chk_id", String.valueOf(param.get("jnt_chk_id")));
				temp.put("jnt_chk_tgt_id", String.valueOf(param.get("jnt_chk_tgt_id")));
				temp.put("wkpl_id", String.valueOf(param.get("wkpl_id")));
				temp.put("SESS_ID", userInfo.getUsrId());
				
				service.updateJntAtndList(temp);
			}
		}
		
		if (atnDeleteList != null) {
			// 딜리트
			for(int i = 0; i < atnDeleteList.size(); i++) {
				Map<String, Object> temp = atnDeleteList.get(i);
				temp.put("jnt_chk_id", String.valueOf(param.get("jnt_chk_id")));
				temp.put("jnt_chk_tgt_id", String.valueOf(param.get("jnt_chk_tgt_id")));
				temp.put("wkpl_id", String.valueOf(param.get("wkpl_id")));
				temp.put("SESS_ID", userInfo.getUsrId());
				
				service.deleteJntAtndList(temp);
			}
		}
		
		// 저장 로직 종료
		// 결제 로직 시작 
		
		int n = 0;
		
		String usrId = userInfo.getUsrId();
		String langCd = userInfo.getLangCd();
		
		// 다국어 처리
		String[] mlangNo = {
				"LB00000449", "LB00000731", "LB00000021"
				, "LB00000679", "LB00000727", "LB00000640"
				, "LB00000680", "LB00000644", "LB00000681"
				, "LB00000994", "LB00000908", "LB00000741"
				, "LB00000701", "LB00000910", "LB00000646"
				, "LB00000901", "LB00000121", "LB00000642"
				, "LB00001013"};
		List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo), langCd);
		// 기본정보
		List<Map<String, ?>> basicInfo = service.selectCoprChkMgnt(param);
		// 대상자정보
		List<Map<String, ?>> userList = service.selectJntChkAtndList(param);
		// 파일 정보
		List<Map<String, ?>> attachFileList = new ArrayList<>();
		
		Map<String, String> fileParam = new HashMap<>();
		fileParam.put("id", String.valueOf(param.get("jnt_chk_atfl_no")));
		// 첨부파일 정보
		try {
			attachFileList = fileService.selectFileList(fileParam);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map<String, ?> tempBasic = basicInfo.get(0);
		
		// 대상자 html 태그
		String subTagUser = "";
		
		// 대상자 html 태그
		for(int i=0; i<userList.size(); i++) {
			subTagUser += "<tr>\r\n" + 
					"            <td style=\"width:211px;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;border-left:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
					"                <p style=\"text-align: center;\">" + (i+1) + "<br></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width:211px;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
					"                <p>" + String.valueOf(userList.get(i).get("jnt_chk_atnd_cl_cd_nm")) + "<br></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width:211px;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
					"                <p>" + String.valueOf(userList.get(i).get("jnt_chk_atnd_nm")) + "<br></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width:211px;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
					"                <p>" + String.valueOf(userList.get(i).get("jnt_chk_atnd_emad")) + "<br></p>\r\n" + 
					"            </td>\r\n" + 
					"        </tr>";
		}
		
		String fileInfo = "";
		
		// 첨부파일 정보 
		for(int j=0; j<attachFileList.size(); j++) {
			fileInfo += "<p><a href=\"" + serverUrl + "/api/v1/public/downloadSeq" + "/" + String.valueOf(attachFileList.get(j).get("key")) + "\" style=\"font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + String.valueOf(attachFileList.get(j).get("name")) + "</a></p>";
		}
		
		// 페이지 구분
		// 1-규제 검토 조회 상세_원료, 2-규제 검토 상세 MRO
		String sub_title = "";
		// 결제 제목 - 화학물질 규제 검토(자재코드 자재명)
		String aprv_ttl = "";
		// 결제 내용
		String aprv_cntn = "";
				
		
		// 결제 제목 - 교육명
		aprv_ttl = String.valueOf(tempBasic.get("jnt_chk_tgt_nm"));
		// 점검 일시
		String chkDt = String.valueOf(tempBasic.get("jnt_chk_dt"));
		chkDt = chkDt.substring(0, 4) + "-" + chkDt.substring(4, 6) + "-" + chkDt.substring(6, 8);
		// 점검 시간
		String chkHm = String.valueOf(tempBasic.get("jnt_chk_hm"));
		chkHm = chkHm.substring(0, 2) + ":" + chkHm.substring(2, 4);
		// 결제 내용
		aprv_cntn = "<div>\r\n" + 
				"<p style=\"font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000731") + "</span></p>\r\n" + 
				"<table class=\"txc-table\" width=\"846\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"border:none;border-collapse:collapse;word-break:break-all;font-family:&quot;Malgun Gothic&quot;, &quot;맑은 고딕&quot;;font-size:13.3333px\">\r\n" + 
				"    <tbody>\r\n" + 
				"        <tr>\r\n" + 
				"            <td style=\"width: 141px; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
				"                <p><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000021") + "</span><br></p>\r\n" + 
				"            </td>\r\n" + 
				"            <td style=\"width:141px;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;border-top:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
				"                <p>" + String.valueOf(tempBasic.get("wkpl_nm")) + "<br></p>\r\n" + 
				"            </td>\r\n" + 
				"            <td style=\"width: 141px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
				"                <p style=\"font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000679") + "</span></p>\r\n" + 
				"            </td>\r\n" + 
				"            <td style=\"width:141px;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;border-top:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
				"                <p>" + String.valueOf(tempBasic.get("jnt_chk_supe_usr_nm")) + "<br></p>\r\n" + 
				"            </td>\r\n" + 
				"            <td style=\"width: 141px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
				"                <p style=\"font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000727") + "</span></p>\r\n" + 
				"            </td>\r\n" + 
				"            <td style=\"width:141px;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;border-top:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
				"                <p>" + String.valueOf(tempBasic.get("lsch_nm")) + "<br></p>\r\n" + 
				"            </td>\r\n" + 
				"        </tr>\r\n" + 
				"        <tr>\r\n" + 
				"            <td style=\"width: 141px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
				"                <p style=\"font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000640") + "</span></p>\r\n" + 
				"            </td>\r\n" + 
				"            <td style=\"width: 423px; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"3\">\r\n" + 
				"                <p>" + String.valueOf(tempBasic.get("jnt_chk_tgt_nm")) + "<br></p>\r\n" + 
				"            </td>\r\n" + 
				"            <td style=\"width: 141px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
				"                <p style=\"font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000680") + "</span></p>\r\n" + 
				"            </td>\r\n" + 
				"            <td style=\"width:141px;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
				"                <p>" + chkDt + " " + chkHm + "<br></p>\r\n" + 
				"            </td>\r\n" + 
				"        </tr>\r\n" + 
				"        <tr>\r\n" + 
				"            <td style=\"width: 141px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
				"                <p style=\"font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00001013") + "</span></p>\r\n" + 
				"            </td>\r\n" + 
				"            <td style=\"width: 705px; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"5\">\r\n" + 
				"                <p>" + String.valueOf(tempBasic.get("jnt_chk_dtl_loc_nm")) + "<br></p>\r\n" + 
				"            </td>\r\n" + 
				"        </tr>\r\n" + 
				"        <tr>\r\n" + 
				"            <td style=\"width: 141px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
				"                <p style=\"font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000994") + "</span></p>\r\n" + 
				"            </td>\r\n" + 
				"            <td style=\"width: 705px; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"5\">\r\n" + 
				"                <p>" + String.valueOf(tempBasic.get("jnt_chk_note_txt")) + "<br></p>\r\n" + 
				"            </td>\r\n" + 
				"        </tr>\r\n" + 
				"    </tbody>\r\n" + 
				"</table>\r\n" + 
				"<p><br></p>\r\n" + 
				"<p style=\"font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000908") + "</span></p>\r\n" + 
				"<table class=\"txc-table\" width=\"846\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"border:none;border-collapse:collapse;word-break:break-all;font-family:&quot;Malgun Gothic&quot;, &quot;맑은 고딕&quot;;font-size:13.3333px\">\r\n" + 
				"    <tbody>\r\n" + 
				"        <tr>\r\n" + 
				"            <td style=\"width: 211px; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
				"                <p style=\"text-align: center;\"><span style=\"font-weight: bold;\">No.</span><br></p>\r\n" + 
				"            </td>\r\n" + 
				"            <td style=\"width: 211px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
				"                <p style=\"font-weight: bold; text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000741") + "</span></p>\r\n" + 
				"            </td>\r\n" + 
				"            <td style=\"width: 211px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
				"                <p style=\"font-weight: bold; text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000701") + "</span></p>\r\n" + 
				"            </td>\r\n" + 
				"            <td style=\"width: 211px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
				"                <p style=\"font-weight: bold; text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000910") + "</span></p>\r\n" + 
				"            </td>\r\n" + 
				"        </tr>\r\n" + 
				subTagUser +
				"    </tbody>\r\n" + 
				"</table>\r\n" + 
				"<p style=\"font-weight: bold;\"><span style=\"font-weight: bold;\"><br></span></p>\r\n" + 
				"<p style=\"font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000646") + "</span></p>\r\n" + 
				"<table class=\"txc-table\" width=\"846\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"border:none;border-collapse:collapse;word-break:break-all;font-family:&quot;Malgun Gothic&quot;, &quot;맑은 고딕&quot;;font-size:13.3333px\">\r\n" + 
				"    <tbody>\r\n" + 
				"        <tr>\r\n" + 
				"            <td style=\"width: 208px; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
				"                <p>﻿" + mUtil.getMLang(mlangCntn, "LB00000642") + "<br></p>\r\n" + 
				"            </td>\r\n" + 
				"            <td style=\"width: 637px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                <p>" + String.valueOf(tempBasic.get("jnt_chk_rslt_txt")) + "<br></p>\r\n" + 
				"            </td>\r\n" + 
				"        </tr>\r\n" + 
				"    </tbody>\r\n" + 
				"         <tr>\r\n" + 
				"            <td style=\"width: 208px; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
				"                <p>" + mUtil.getMLang(mlangCntn, "LB00000121") + "<br></p>\r\n" + 
				"            </td>\r\n" + 
				"            <td style=\"width: 637px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				fileInfo +
				"            </td>\r\n" + 
				"        </tr>" +
				"</table>\r\n" + 
				"<p>\r\n" + 
				"    <spam style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000449") + " :&nbsp;</spam><a href=\"" + frontUrl + "/sftchk/wknscoprchkmgntdtllup" + "/" + tempBasic.get("jnt_chk_id") + "\" style=\"font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"target=\"_blank\">" + frontUrl + "/sftchk/wknscoprchkmgntdtllup" + "/" + tempBasic.get("jnt_chk_id") + "</a>\r\n" + 
				"</p>\r\n" +
				"</div>";
		
		Map<String, Object> aprvParam = new HashMap<>();
		//				결재 키 
		String aprv_no = UUID.randomUUID().toString().replace("-", "");
		aprvParam.put("aprv_no", aprv_no);
		//				결재 코드 - 안전점검(60)
		aprvParam.put("arpv_cd", "60");		
		//		 		업무번호
		aprvParam.put("biz_no", String.valueOf(tempBasic.get("jnt_chk_id")));
		//				상신자
		aprvParam.put("aprv_usr_id", usrId);
		//				결재 상태 코드 - 결재상신
		aprvParam.put("aprv_stt_cd", "1");		
		//				후행메서드	
		aprvParam.put("aprv_mthd_nm", "aprvCoprApprAf");
		//				제목
		aprvParam.put("aprv_ttl", aprv_ttl);
		//				내용
		aprvParam.put("aprv_cntn", aprv_cntn);		
		//				결재 비고	
		aprvParam.put("aprv_note", "");
		//				사원 번호	
		aprvParam.put("SESS_USR_ID", usrId);
		//		 		결제 정보 table insert 
		n = aprvService.insertAprv(aprvParam);		
		
		Map<String, Object> data = new HashMap<>();
		if(n > -1) {	
			param.put("jnt_aprv_no", aprv_no);
			n = service.updateCoprAprvInfo(param); // 해당 합동점검에 결제 번호 update
			
			data.put("Success", "Success");
			data.put("aprvNo", aprv_no);
			data.put("usrId", usrId);
		}
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@Transactional
	@PostMapping("/wknstourchkmgntaprv")
	public ResponseEntity<?> insertTourChkMgntAprv(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		param.put("SESS_ID", userInfo.getUsrId());
		
		// 결제 데이터 초기화
		List<Map<String, Object>> aprvRowInfo = (List<Map<String, Object>>)param.get("aprvData");

		// 결제 로직 시작 
		int n = 0;
		
		String usrId = userInfo.getUsrId();
		String langCd = userInfo.getLangCd();
		
		// 다국어 처리
		String[] mlangNo = {
				  "LB00000021", "LB00000640", "LB00000739"
				  ,"LB00000681", "LB00000680", "LB00000679"
				  
				};
		List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo), langCd);
		
		// 페이지 구분
		// 결제 제목 - 화학물질 규제 검토(자재코드 자재명)
		String aprv_ttl = "";
		// 결제 내용
		String aprv_cntn = "";
		// 반복문
		String subTag = "";
		for(int i = 0; i < aprvRowInfo.size(); i++) {
			if(String.valueOf(param.get("sch_gb")).equalsIgnoreCase("tourch")){
				subTag += "<tr>\r\n" + 
						"            <td style=\"width: 140px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                <p>" + String.valueOf(aprvRowInfo.get(i).get("wkpl_nm")) + "</p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:141px;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p style=\"text-decoration: underline; font-weight: bold; color: rgb(0, 85, 255);\"><span style=\"text-decoration: underline; font-weight: bold; color: rgb(0, 85, 255);\"><a href=\"" + frontUrl + "/sftchk/wknstourchkmgntchdtllup" + "/" + String.valueOf(aprvRowInfo.get(i).get("circ_chk_id")) + "\" style=\"font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + String.valueOf(aprvRowInfo.get(i).get("circ_chk_tgt_nm")) + "</a></span></p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:141px;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p>" + String.valueOf(aprvRowInfo.get(i).get("circ_chk_wkpl_loc_nm")) + "</p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:141px;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p>" + String.valueOf(aprvRowInfo.get(i).get("circ_chk_dtl_loc_nm")) + "</p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:141px;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p>" + String.valueOf(aprvRowInfo.get(i).get("circ_chk_dttm")) + "</p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:141px;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p>" + String.valueOf(aprvRowInfo.get(i).get("circ_chk_supe_usr_nm")) + "</p>\r\n" + 
						"            </td>\r\n" + 
						"        </tr>";
			} else {
				subTag += "<tr>\r\n" + 
						"            <td style=\"width: 140px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						"                <p>" + String.valueOf(aprvRowInfo.get(i).get("wkpl_nm")) + "</p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:141px;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p style=\"text-decoration: underline; font-weight: bold; color: rgb(0, 85, 255);\"><span style=\"text-decoration: underline; font-weight: bold; color: rgb(0, 85, 255);\"><a href=\"" + frontUrl + "/sftchk/wknstourchkmgntdtllup" + "/" + String.valueOf(aprvRowInfo.get(i).get("circ_chk_id")) + "\" style=\"font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + String.valueOf(aprvRowInfo.get(i).get("circ_chk_tgt_nm")) + "</a></span></p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:141px;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p>" + String.valueOf(aprvRowInfo.get(i).get("circ_chk_wkpl_loc_nm")) + "</p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:141px;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p>" + String.valueOf(aprvRowInfo.get(i).get("circ_chk_dtl_loc_nm")) + "</p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:141px;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p>" + String.valueOf(aprvRowInfo.get(i).get("circ_chk_dttm")) + "</p>\r\n" + 
						"            </td>\r\n" + 
						"            <td style=\"width:141px;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
						"                <p>" + String.valueOf(aprvRowInfo.get(i).get("circ_chk_supe_usr_nm")) + "</p>\r\n" + 
						"            </td>\r\n" + 
						"        </tr>";
			}
		}
		
		// 결제 제목 - 교육명
		if(String.valueOf(param.get("sch_gb")).equalsIgnoreCase("tourch")){
			aprv_ttl = String.valueOf("유해물질점검 일괄결제");
		} else {
			aprv_ttl = String.valueOf("순회점검 일괄결제");
		}
		
		
		// 결제 내용
		aprv_cntn = "<div>\r\n" + 
				"<table class=\"txc-table\" width=\"846\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"border:none;border-collapse:collapse;word-break:break-all;font-family:&quot;Malgun Gothic&quot;, &quot;맑은 고딕&quot;;font-size:13.3333px\">\r\n" + 
				"    <tbody>\r\n" + 
				"        <tr>\r\n" + 
				"            <td style=\"width: 140px; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
				"                <p style=\"text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000021") + "</span><br></p>\r\n" + 
				"            </td>\r\n" + 
				"            <td style=\"width: 141px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
				"                <p style=\"font-weight: bold; text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000640") + "</span></p>\r\n" + 
				"            </td>\r\n" + 
				"            <td style=\"width: 141px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
				"                <p style=\"font-weight: bold; text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000739") + "</span></p>\r\n" + 
				"            </td>\r\n" + 
				"            <td style=\"width: 141px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
				"                <p style=\"font-weight: bold; text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000681") + "</span></p>\r\n" + 
				"            </td>\r\n" + 
				"            <td style=\"width: 141px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
				"                <p style=\"font-weight: bold; text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000680") + "</span></p>\r\n" + 
				"            </td>\r\n" + 
				"            <td style=\"width: 141px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
				"                <p style=\"font-weight: bold; text-align: center;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000679") + "</span></p>\r\n" + 
				"            </td>\r\n" + 
				"        </tr>" +
				subTag +
				"    </tbody>\r\n" + 
				"</table>" + 
				"</div>";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddhhmmss");
		String cd = sdf.format(new Date());
		
		Map<String, Object> aprvParam = new HashMap<>();
		//				결재 키 
		String aprv_no = UUID.randomUUID().toString().replace("-", "");
		aprvParam.put("aprv_no", aprv_no);
		//				결재 코드 - 순회점검(60)
		aprvParam.put("arpv_cd", "70");		
		//		 		업무번호
		aprvParam.put("biz_no", cd);
		//				상신자
		aprvParam.put("aprv_usr_id", usrId);
		//				결재 상태 코드 - 결재상신
		aprvParam.put("aprv_stt_cd", "1");		
		//				후행메서드	
		aprvParam.put("aprv_mthd_nm", "aprvTourApprAf");
		//				제목
		aprvParam.put("aprv_ttl", aprv_ttl);
		//				내용
		aprvParam.put("aprv_cntn", aprv_cntn);		
		//				결재 비고	
		aprvParam.put("aprv_note", "");
		//				사원 번호	
		aprvParam.put("SESS_USR_ID", usrId);
		//		 		결제 정보 table insert 
		n = aprvService.insertAprv(aprvParam);		
		
		Map<String, Object> data = new HashMap<>();
		if(n > -1) {	
			// 결제 row 갯수 만큼 해당 순회 점검 테이블에 결제 번호 update
			for(int j = 0; j < aprvRowInfo.size(); j++) {
				Map<String, Object> tempAprvInfo = aprvRowInfo.get(j);
				tempAprvInfo.put("circ_aprv_no", aprv_no);
				tempAprvInfo.put("SESS_ID", userInfo.getUsrId());
				n = service.updateTourAprvInfo(tempAprvInfo); // 해당 합동점검에 결제 번호 update
			}
			data.put("Success", "Success");
			data.put("aprvNo", aprv_no);
			data.put("usrId", usrId);
		}
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	// 시정지시서 목록			
	@GetMapping("/crin-list")		
	public ResponseEntity<?> selectCrinList(@RequestParam Map<String, String> param, HttpSession session) {		
		UserInfo userInfo = sessionManager.get(session.getId());	
		param.put("SESS_LANG", userInfo.getLangCd());	
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());	
		String chkGb = param.get("crin_chk_gb");
		
		if(chkGb != null) { // web 구분 처리용 logic
			if(chkGb.equalsIgnoreCase("actn")) {
				param.put("crin_actn_schd_usr_id", param.get("crin_chk_usr_id"));
			} else if(chkGb.equalsIgnoreCase("supe")){
				param.put("crin_chk_supe_usr_id", param.get("crin_chk_usr_id"));
			}
		}
		List<Map<String, ?>> list = service.selectCrinList(param);	
			
		return new ResponseEntity<>(list, HttpStatus.OK);	
	}		
			
	// 시정지시서 상세 조회		
	@GetMapping("/crin-dtl")		
	public ResponseEntity<?> selectCrinBasicDtl(@RequestParam Map<String, String> param, HttpSession session) {		
		UserInfo userInfo = sessionManager.get(session.getId());	
		param.put("SESS_LANG", userInfo.getLangCd());	
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());	
			
		// 기본정보	
		List<Map<String, ?>> basicInfo = service.selectCrinBasicDtl(param);	
		// 조치정보	
		List<Map<String, ?>> actnInfo = service.selectCrinActnDtl(param);	
			
		Map<String, List<Map<String, ?>>> data = new HashMap<>();	
		data.put("basic", basicInfo);	
		data.put("actn", actnInfo);	
			
		return new ResponseEntity<>(data, HttpStatus.OK);	
	}		
			
	// 시정지시서 상세 저장		
	@PutMapping("/crin-dtl")		
	@Transactional		
	public ResponseEntity<?> insertCrinBasicDtl(@RequestBody Map<String, Object> param, HttpSession session) {		
		UserInfo userInfo = sessionManager.get(session.getId());	
			
		Map<String, String> temp = new HashMap<>();	
			
		int n = 0;	
		int m = 0;	
		int j = 0;	
		boolean crt = false;
			
		// 기본정보	
		List<Map<String, String>> infoRow = (List<Map<String, String>>)param.get("infoRow");	
			
		// 조치정보	
		List<Map<String, String>> actnRow = (List<Map<String, String>>)param.get("actnRow");	
			
		Map<String, String> tempInfo = infoRow.get(0);	
		tempInfo.put("SESS_ID", userInfo.getUsrId());
			
		// 키 채번	
		String key = "";	
		if (!"".equals(tempInfo.get("crin_id")) && null != tempInfo.get("crin_id")) {	
			key = tempInfo.get("crin_id");
		} else {	
			key = service.selectCrinkey(temp);
			crt = true;
		}	
			
		// 기본정보 저장	
		tempInfo.put("crin_id", key);	
		tempInfo.put("username", userInfo.getUsrId());	
		n = service.insertCrinBasicDtl(tempInfo);	
			
		// 요청정보 저장	
		if (actnRow != null) {	
			Map<String, String> tempActnInfo = actnRow.get(0);
			tempActnInfo.put("crin_id", key);
			tempActnInfo.put("username", userInfo.getUsrId());
			m = service.insertCrinActnDtl(tempActnInfo);
			
			// 진행 상태가 조치 완료일 경우
			/*
			if(tempActnInfo.get("crin_prst_cd").equalsIgnoreCase("30")) {
				if(tempInfo.get("lnk_chk_id") != null && !tempInfo.get("lnk_chk_id").equalsIgnoreCase("")) {
					tempInfo.put("SESS_ID", userInfo.getUsrId());
					// lnk 정보가 존재할때
					if(tempInfo.get("lnk_chk_id").substring(0, 4).equalsIgnoreCase("SOC")) {
						// 시작 전 점검 시정지시서일 경우 (상태 점검완료)
						tempInfo.put("oprb_chk_id", tempInfo.get("lnk_chk_id"));
						tempInfo.put("oprb_chk_prst_cd", "40");
						service.updateOprbChkStatus(tempInfo);
					} else if(tempInfo.get("lnk_chk_id").substring(0, 4).equalsIgnoreCase("WCHK")) {
						// 순회점검 시정지시서일 경우 (상태 점검완료)
						tempInfo.put("circ_chk_id", tempInfo.get("lnk_chk_id"));
						tempInfo.put("circ_chk_prst_cd", "40");
						service.updateWknsChkStatus(tempInfo);
					}
				}
			}
			*/
		}	
			
		Map<String, String> resultMap = new HashMap<>();	
		
		if(tempInfo.get("send_yn").equalsIgnoreCase("Y")) {
			// 발송 버튼을 눌렀을때
			sendCrinNotiMail(key);
		}
			
		String result;	
		result = (n > -1? (m > -1? (j > -1? Constant.SUCCESS : Constant.FAIL) : Constant.FAIL) : Constant.FAIL);	
		resultMap.put("result", result);	
		resultMap.put("key1", key);	
			
		return new ResponseEntity<>(resultMap, HttpStatus.OK);	
	}		
	
	// 소방/안점 점검 카운트		
	@GetMapping("/sftchklistcnt")		
	public ResponseEntity<?> selectSftChkListCnt(@RequestParam Map<String, Object> param, HttpSession session) {		
		UserInfo userInfo = sessionManager.get(session.getId());	
		param.put("SESS_LANG", userInfo.getLangCd());	
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());	
		param.put("SESS_ID", userInfo.getUsrId());
		List<Map<String, ?>> list = service.selectSftChkListCnt(param);	
			
		return new ResponseEntity<>(list, HttpStatus.OK);	
	}	
	
	@GetMapping("/oprnbgnbfchkmgntchkrsltcrinmb")
	public ResponseEntity<?> selectOprbChkRsltMb(@RequestParam Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		Map<String, List<Map<String, ?>>> result = new HashMap<>();

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		// 시정지시 정보
		String chkId = String.valueOf(param.get("lnk_chk_id"));
		param.put("oprb_chk_id", chkId);
		List<Map<String, ?>> oprbCrinResult = service.selectOprbChkCrinRslt(param);
		
		result.put("oprbCrinResult", oprbCrinResult);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PutMapping("/oprnbgnbfchkmgntchkrsltcrinmb")
	@Transactional
	public ResponseEntity<?> updateOprbChkRsltCrinMb(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		Map<String, List<Map<String, ?>>> result = new HashMap<>();

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		param.put("SESS_ID", userInfo.getUsrId());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = Calendar.getInstance();
        String strToday = sdf.format(c1.getTime());
        SimpleDateFormat sdf2 = new SimpleDateFormat("HHmm");
        String strTime = sdf2.format(c1.getTime());
		
		// 구분값 제거 처리
		String actnHopeDt = String.valueOf(param.get("actn_hope_dt")).replace("-", "");
		param.put("actn_hope_dt", actnHopeDt);
		param.put("oprb_chk_id", param.get("lnk_chk_id"));
		
		List<Map<String, ?>> oprbChkResult = service.selectOprbChkBasicRglt(param);
		param.put("wkpl_id", oprbChkResult.get(0).get("wkpl_id"));
		param.put("crin_chk_wkpl_loc_id", oprbChkResult.get(0).get("oprb_chk_wkpl_loc_id"));
		param.put("crin_chk_dtl_loc_nm", oprbChkResult.get(0).get("oprb_chk_dtl_loc_nm"));
		param.put("crin_chk_note_txt", oprbChkResult.get(0).get("oprb_chk_note_txt"));
		param.put("crin_chk_supe_usr_id", oprbChkResult.get(0).get("oprb_chk_rspn_usr_id"));
		
		param.put("crin_chk_dt", strToday);
		param.put("crin_chk_hm", strTime);
		
		param.put("crin_chk_tgt_nm", oprbChkResult.get(0).get("oprb_chk_tgt_nm"));
		
		/* 시정 지시서 여러개로 등록하도록 바뀌고 수정 불가능하게 처리 되어 무조건 키를 가져와 insert 처리한다. 해당 메서드 실행 횟수만큼 시정 지시서가 증가한다.
		
		// 시정지시 정보(입력된 시정지시서 정보가 없다면 처리하지 않는다)
		String crinId = String.valueOf(oprbChkResult.get(0).get("crin_id"));
		if(oprbChkResult.get(0).get("crin_id") == null || crinId.equalsIgnoreCase("")) {
			// 시정 지시서가 존재하지 않을 경우 insert
			String crinKey = service.selectOprbCrinKey(param);
			param.put("crin_id", crinKey);
			int n = service.insertOprbChkCrinRslt(param);
			if(n > 0) {
				sendCrinNotiMail(crinKey);
			}
		} else {
			// 시정 지시서가 기존에도 존재할 경우 update
			param.put("crin_id", crinId);
			service.updateOprbChkCrinRslt(param);
		}
		
		*/
		// 시정 지시서 여러개로 등록하도록 바뀌고 수정 불가능하게 처리 되어 무조건 키를 가져와 insert 처리한다. 해당 메서드 실행 횟수만큼 시정 지시서가 증가한다.
		String crinKey = service.selectOprbCrinKey(param);
		param.put("crin_id", crinKey);
		int n = service.insertOprbChkCrinRslt(param);
		if(n > 0) {
			sendCrinNotiMail(crinKey);
		}
		
		return new ResponseEntity<>(Constant.SUCCESS, HttpStatus.OK);
	}

	@GetMapping("/wknstourchkmgntchkrsltcrinmb")
	public ResponseEntity<?> selectWknsChkMgntRslt(@RequestParam Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		Map<String, List<Map<String, ?>>> result = new HashMap<>();

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		// 시정 지시서 정보
		String chkId = String.valueOf(param.get("lnk_chk_id"));
		param.put("circ_chk_id", chkId);
		List<Map<String, ?>> wknsCrinResult = service.selectWknsChkCrinRslt(param);
		
		result.put("wknsCrinResult", wknsCrinResult);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PutMapping("/wknstourchkmgntchkrsltcrinmb")
	@Transactional
	public ResponseEntity<?> updateWknsChkRsltCrinMb(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		Map<String, List<Map<String, ?>>> result = new HashMap<>();

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		param.put("SESS_ID", userInfo.getUsrId());
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Calendar c1 = Calendar.getInstance();
        String strToday = sdf.format(c1.getTime());
        SimpleDateFormat sdf2 = new SimpleDateFormat("HHmm");
        String strTime = sdf2.format(c1.getTime());
		
		// 구분값 제거 처리
		String actnHopeDt = String.valueOf(param.get("actn_hope_dt")).replace("-", "");
		param.put("actn_hope_dt", actnHopeDt);
		param.put("circ_chk_id", param.get("lnk_chk_id"));
		
		List<Map<String, ?>> wknsChkResult = service.selectWknsChkMgnt(param);
		param.put("wkpl_id", wknsChkResult.get(0).get("wkpl_id"));
		param.put("crin_chk_wkpl_loc_id", wknsChkResult.get(0).get("circ_chk_wkpl_loc_id"));
		param.put("crin_chk_dtl_loc_nm", wknsChkResult.get(0).get("circ_chk_dtl_loc_nm"));
		param.put("crin_chk_note_txt", wknsChkResult.get(0).get("circ_chk_note_txt"));
		param.put("crin_chk_supe_usr_id", wknsChkResult.get(0).get("circ_chk_supe_usr_id"));
		
		param.put("crin_chk_dt", strToday);
		param.put("crin_chk_hm", strTime);
		
		param.put("crin_chk_tgt_nm", wknsChkResult.get(0).get("circ_chk_tgt_nm"));
		
		/*
		
		// 시정지시 정보(입력된 시정지시서 정보가 없다면 처리하지 않는다)
		String crinId = String.valueOf(wknsChkResult.get(0).get("crin_id"));
		if(wknsChkResult.get(0).get("crin_id") == null || crinId.equalsIgnoreCase("")) {
			// 시정 지시서가 존재하지 않을 경우 insert
			String crinKey = service.selectOprbCrinKey(param);
			param.put("crin_id", crinKey);
			service.insertWknsChkCrinRslt(param);
			if(n > 0) {
				sendCrinNotiMail(crinKey);
			}
		} else {
			// 시정 지시서가 기존에도 존재할 경우 update
			param.put("crin_id", crinId);
			service.updateOprbChkCrinRslt(param);
		}
		
		*/
		// 시정 지시서 여러개로 등록하도록 바뀌고 수정 불가능하게 처리 되어 무조건 키를 가져와 insert 처리한다. 해당 메서드 실행 횟수만큼 시정 지시서가 증가한다.
		String crinKey = service.selectOprbCrinKey(param);
		param.put("crin_id", crinKey);
		if(String.valueOf(param.get("sch_gb")).equalsIgnoreCase("tourch")){
			param.put("crin_cl_cd", "CRN05");
		} else {
			param.put("crin_cl_cd", "CRN02");
		}
		int n = service.insertWknsChkCrinRslt(param);
		if(n > 0) {
			sendCrinNotiMail(crinKey);
		}
		
		return new ResponseEntity<>(Constant.SUCCESS, HttpStatus.OK);
	}
	
	@PutMapping("/crrtdrtmgntdtl")		
	@Transactional		
	public ResponseEntity<?> insertCrinBasicMbDtl(@RequestBody Map<String, Object> param, HttpSession session) {		
		UserInfo userInfo = sessionManager.get(session.getId());	
			
		Map<String, String> temp = new HashMap<>();	
		param.put("SESS_ID", userInfo.getUsrId());
			
		int m = 0;	
			
		// 조치정보
		Map<String, String> infoData = (Map<String, String>)param.get("frmData");
		Map<String, String> actnData = (Map<String, String>)param.get("actnData");
		
		Map<String, String> tempInfo = infoData;
			
		// 요청정보 저장	
		if (actnData != null) {	
			Map<String, String> tempActnInfo = actnData;
			tempActnInfo.put("acco_dt", tempActnInfo.get("acco_dt").replace("-", ""));
			tempActnInfo.put("acco_hm", tempActnInfo.get("acco_hm").replace(":", ""));
			tempActnInfo.put("username", userInfo.getUsrId());
			m = service.insertCrinActnDtl(tempActnInfo);
			
			if(tempActnInfo.get("crin_prst_cd").equalsIgnoreCase("30")) {
				// 진행상태가 조치 완료일 경우
				/*
				if(tempInfo.get("lnk_chk_id") != null && !tempInfo.get("lnk_chk_id").equalsIgnoreCase("")) {
					tempInfo.put("SESS_ID", userInfo.getUsrId());
					// lnk 정보가 존재할때
					if(tempInfo.get("lnk_chk_id").substring(0, 4).equalsIgnoreCase("SOC")) {
						// 시작 전 점검 시정지시서일 경우 (상태 점검완료)
						tempInfo.put("oprb_chk_id", tempInfo.get("lnk_chk_id"));
						tempInfo.put("oprb_chk_prst_cd", "40");
						service.updateOprbChkStatus(tempInfo);
					} else if(tempInfo.get("lnk_chk_id").substring(0, 4).equalsIgnoreCase("WCHK")) {
						// 순회점검 시정지시서일 경우 (상태 점검완료)
						tempInfo.put("circ_chk_id", tempInfo.get("lnk_chk_id"));
						tempInfo.put("circ_chk_prst_cd", "40");
						service.updateWknsChkStatus(tempInfo);
					} else if(tempInfo.get("lnk_chk_id").substring(0, 4).equalsIgnoreCase("CORP")) {
						// 순회점검 시정지시서일 경우 
						// service.updateJnkChkStatus(tempInfo);
					}
				}
				*/
			}
		}	
			
		String result;	
		result = m > -1 ? Constant.SUCCESS : Constant.FAIL;	
			
		return new ResponseEntity<>(result, HttpStatus.OK);	
	}		
	
	@PutMapping("/crrdrtmgntedtmb")		
	@Transactional		
	public ResponseEntity<?> insertCrrDrtMbDtl(@RequestBody Map<String, Object> param, HttpSession session) {		
		UserInfo userInfo = sessionManager.get(session.getId());
		
		Map<String, List<Map<String, ?>>> result = new HashMap<>();

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		param.put("SESS_ID", userInfo.getUsrId());
		
		// 구분값 제거 처리
		String actnHopeDt = String.valueOf(param.get("actn_hope_dt")).replace("-", "");
		String crinChkDt = String.valueOf(param.get("crin_chk_dt")).replace("-", "");
		String crinChkHm = String.valueOf(param.get("crin_chk_hm")).replace(":", "");
		param.put("actn_hope_dt", actnHopeDt);
		param.put("crin_chk_dt", crinChkDt);
		param.put("crin_chk_hm", crinChkHm);
		
		// 시정지시서 키 추출
		String crinKey = service.selectOprbCrinKey(param);
		param.put("crin_id", crinKey);
		int n = service.insertCrrDtCrinMb(param);
		if(n > 0) {
			sendCrinNotiMail(crinKey);
		}
		
		return new ResponseEntity<>(Constant.SUCCESS, HttpStatus.OK);
	}
	
	@Transactional
	public boolean sendCrinNotiMail(String crinId) {		

		Map<String, String> param = new HashMap<>();
		param.put("crin_id", crinId);
		// 전달된 id로 시정지시서 상세 조회
		List<Map<String, ?>> crinData = service.selectCrinBasicDtl(param);
		param.put("SESS_LANG", String.valueOf(crinData.get(0).get("lang_cd")));
		
		if(param.get("SESS_LANG") == null) {
			param.put("SESS_LANG", "KO");
		}
		
		crinData = service.selectCrinBasicDtl(param);
		Map<String, Object> crinInfo = (Map<String, Object>)crinData.get(0);
		
		// 첨부 이미지 처리
		String img = "";
		if(crinInfo.get("crin_img_atfl_no") != null && !String.valueOf(crinInfo.get("crin_img_atfl_no")).equalsIgnoreCase("null")) {
			// 첨부 이미지가 존재할 경우 해당 키 값을 가져온다.
			List<Map<String, ?>> fileList;
			try {
				param.put("id", String.valueOf(crinInfo.get("crin_img_atfl_no")));
				fileList = fileService.selectFileList(param);
				for(int f = 0; f < fileList.size(); f++) {
					img += "<img width=\"300px\" height=\"300px\" src=\"" + serverUrl + "/api/v1/public/img/" + fileList.get(f).get("key") + "\" />";
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				img = "";
			}
		}
		
		// 수신자 학인
		String[] to = new String[2];
		
		Boolean mailResult = true;
		// 수신자 저장(점검 관리자, 조치자)
		to[0] = String.valueOf(crinInfo.get("crin_actn_schd_usr_id"));
		to[1] = String.valueOf(crinInfo.get("crin_chk_supe_usr_id"));
		// 수신자 저장(임시)
		// to[0] = "AC926550";
		// 다국어 처리
		String[] mlangNo = {"LB00000508", "LB00000509", "LB00000510"
							, "LB00000511", "LB00000021", "LB00000024"
							, "LB00000025", "LB00000512", "LB00000143"
							, "LB00000449", "LB00000275", "LB00000731"
							, "LB00000669", "LB00000679", "LB00000648"
							, "LB00000644", "LB00000681", "LB00000640"
							, "LB00000095", "LB00000995", "LB00000671"
							, "LB00000678", "LB00000672", "LB00000799"
							, "LB00000800"};
		List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo), String.valueOf(crinInfo.get("lang_cd")));
		// 메일 업무 코드(정의 필요)
		String bizCd = "33";
		// 메일 제목(정의 필요)
		String title =  "시정지시서 등록 알림(" + crinInfo.get("crin_chk_tgt_nm") + ")";
		// 메일 내용(정의 필요)
		String content = 
					"<p style=\"font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000731") + "</span></p>" + 
					"<table class=\"txc-table\" width=\"846\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"border:none;border-collapse:collapse;word-break:break-all;font-family:&quot;Malgun Gothic&quot;, &quot;맑은 고딕&quot;;font-size:13.3333px\">\r\n" + 
					"    <tbody>\r\n" + 
					"        <tr>\r\n" + 
					"            <td style=\"width: 141px; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
					"                <p><span style=\"font-weight: bold;\">﻿" + mUtil.getMLang(mlangCntn, "LB00000021") + "</span><br></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width:141px;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;border-top:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
					"                <p>" + String.valueOf(crinInfo.get("wkpl_nm")) + "</p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 141px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
					"                <p style=\"font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000669") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width:141px;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;border-top:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
					"                <p>" + String.valueOf(crinInfo.get("crin_cl_nm")) + "</p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 141px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
					"                <p style=\"font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000679") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width:141px;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;border-top:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
					"                <p>" + String.valueOf(crinInfo.get("crin_chk_supe_usr_nm")) + "</p>\r\n" + 
					"            </td>\r\n" + 
					"        </tr>\r\n" + 
					"        <tr>\r\n" + 
					"            <td style=\"width: 141px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
					"                <p style=\"font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000648") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width:141px;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
					"                <p>" + String.valueOf(crinInfo.get("crin_chk_dt")) + " " + String.valueOf(crinInfo.get("crin_chk_hm")) + "</p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 141px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
					"                <p><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000644") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width:141px;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
					"                <p>" + String.valueOf(crinInfo.get("crin_chk_wkpl_loc_nm")) + "</p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 141px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
					"                <p><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000681") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width:141px;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
					"                <p>" + String.valueOf(crinInfo.get("crin_chk_dtl_loc_nm")) + "</p>\r\n" + 
					"            </td>\r\n" + 
					"        </tr>\r\n" + 
					"        <tr>\r\n" + 
					"            <td style=\"width: 141px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
					"                <p style=\"font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000640") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 705px; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"5\">\r\n" + 
					"                <p>" + String.valueOf(crinInfo.get("crin_chk_tgt_nm")) + "</p>\r\n" + 
					"            </td>\r\n" + 
					"        </tr>\r\n" + 
					"        <tr>\r\n" + 
					"            <td style=\"width: 141px; height: 54px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
					"                <p><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000095") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 705px; height: 54px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"5\">\r\n" + 
					"                <p>" + String.valueOf(crinInfo.get("crin_chk_note_txt")) + "</p>\r\n" + 
					"            </td>\r\n" + 
					"        </tr>\r\n" + 
					"    </tbody>\r\n" + 
					"</table>\r\n" + 
					"<p><br></p>\r\n" + 
					"<p style=\"font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000995") + "</span></p>\r\n" + 
					"<table class=\"txc-table\" width=\"846\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"border:none;border-collapse:collapse;word-break:break-all;font-family:&quot;Malgun Gothic&quot;, &quot;맑은 고딕&quot;;font-size:13.3333px\">\r\n" + 
					"    <tbody>\r\n" + 
					"<tr>\r\n" + 
					"            <td style=\"width: 211px; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
					"                <p><span style=\"font-weight: bold;\">﻿" + mUtil.getMLang(mlangCntn, "LB00000671") + "</span><br></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width:211px;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;border-top:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
					"                <p>" + String.valueOf(crinInfo.get("actn_hope_dt")) + "</p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 211px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
					"                <p style=\"font-weight: bold;\"><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000678") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width:211px;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;border-top:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
					"                <p>" + String.valueOf(crinInfo.get("crin_actn_schd_usr_nm")) + "</p>\r\n" + 
					"            </td>\r\n" + 
					"        </tr>\r\n" + 
					"        <tr>\r\n" + 
					"            <td style=\"width: 211px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
					"                <p><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000672") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width:211px;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
					"                <p>" + String.valueOf(crinInfo.get("crin_risk_tp_cd_nm")) + "</p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 211px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
					"                <p><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000672") + "2</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width:211px;height:24px;border-bottom:1px solid #ccc;border-right:1px solid #ccc;padding:0px 3px 0px 3px;;\">\r\n" + 
					"                <p>" + String.valueOf(crinInfo.get("crin_risk_tp_cd_2_nm")) + "</p>\r\n" + 
					"            </td>\r\n" + 
					"        </tr>" + 
					"        <tr>\r\n" + 
					"            <td style=\"width: 141px; height: 62px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
					"                <p><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000799") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 705px; height: 62px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"5\">\r\n" + 
					"                <p>" + String.valueOf(crinInfo.get("crin_txt")) + "</p>\r\n" + 
					"            </td>\r\n" + 
					"        </tr>\r\n" + 
					"        <tr>\r\n" + 
					"            <td style=\"width: 141px; height: 62px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(234, 234, 234);\">\r\n" + 
					"                <p><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000800") + "</span></p>\r\n" + 
					"            </td>\r\n" + 
					"            <td style=\"width: 705px; height: 62px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"5\">\r\n" + 
					
									img + 
					
					"            </td>\r\n" + 
					"        </tr>\r\n" + 
					"    </tbody>\r\n" + 
					"</table>\r\n" +
					"<p>\r\n" + 
					"    <spam style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000449") + " :&nbsp;</spam><a href=\"" + frontUrl + "/sftchk/crrtdrtmgntdtledt" + "/" + crinInfo.get("crin_id") + "\" style=\"font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"target=\"_blank\">" + frontUrl + "/sftchk/crrtdrtmgntdtledt" + "/" + crinInfo.get("crin_id") + "</a>\r\n" + 
					"</p>\r\n";
		// 메일 송신
		mailResult = util.sendMail(bizCd, "AC928960", title, content, to);
		
		return mailResult; 
	}
	
	// 연구실 정기안전점검 조회
	@GetMapping("/labssaftychkmgntlist")
	public ResponseEntity<?> selectLabssaftyChkMgntList(@RequestParam Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		List<Map<String, ?>> list = service.selectLabssaftyChkMgntList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	// 연구실 정기안전점검 상세 조회
	@GetMapping("/labs-safty-chk-dtl/{labsSftChkId}")
	public ResponseEntity<?> selectLabsSaftyChkDtl(@PathVariable String labsSftChkId, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		Map<String, String> param = new HashMap<>();
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		param.put("labs_sft_chk_id", labsSftChkId);
		
		// 기본정보
		List<Map<String, ?>> basicInfo = service.selectLabsSaftyChkDtl(param);
		
		Map<String, List<Map<String, ?>>> data = new HashMap<>();
		data.put("basic", basicInfo);
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	// 연구실 정기안전점검 등록 저장
	@PutMapping("/labs-safty-chk-dtl")
	@Transactional
	public ResponseEntity<?> insertLabsSaftyChkDtl(@RequestBody Map<String, Object> param, HttpSession session) {
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
		if (!"".equals(tempInfo.get("labs_sft_chk_id")) && null != tempInfo.get("labs_sft_chk_id")) {
			key = tempInfo.get("labs_sft_chk_id");
		} else {
			key = service.selectLabsSaftyChkDtlkey(temp);
		}
		
		// 기본정보 저장
		tempInfo.put("labs_sft_chk_id", key);
		tempInfo.put("username", userInfo.getUsrId());
		tempInfo.put("crt_dt", dtT);
		tempInfo.put("upt_dt", dtT);
		n = service.insertLabsSaftyChkDtl(tempInfo);
		
		
		Map<String, String> resultMap = new HashMap<>();
		
		String result;
		result = (n > -1?  Constant.SUCCESS : Constant.FAIL);
		resultMap.put("result", result);
		resultMap.put("key1", tempInfo.get("wkpl_id"));
		resultMap.put("key2", key);
		
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
	
	// 연구실 일상점검 등록, 저장 & 안전점검대상 저장, 업데이트, 삭제
		@Transactional
		@PostMapping("/labssftchkmgnt")
		public ResponseEntity<?> insertLabssftChkMgnt(@RequestBody Map<String, Object> param, HttpSession session) {
			UserInfo userInfo = sessionManager.get(session.getId());
			
			param.put("SESS_LANG", userInfo.getLangCd());
			param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
			param.put("SESS_ID", userInfo.getUsrId());
			
			//insert, update, delete 데이터 초기화
			List<Map<String, Object>> insertList = (List<Map<String, Object>>)param.get("insertRow");
			List<Map<String, Object>> updateList = (List<Map<String, Object>>)param.get("updateRow");
			List<Map<String, Object>> deleteList = (List<Map<String, Object>>)param.get("deleteRow");

			String result = "";
			// 데이터 신규 여부 체크
			if(param.get("labs_sft_chk_id") != null && !(String.valueOf(param.get("labs_sft_chk_id")).equalsIgnoreCase(""))) {
				result = String.valueOf(param.get("labs_sft_chk_id"));
				// 기존 데이터 업데이트
				service.updateLabsChkTgt(param);
				if (insertList != null) {
					// 인서트
					for(int i = 0; i < insertList.size(); i++) {
						Map<String, Object> temp = insertList.get(i);
						temp.put("labs_sft_chk_id", String.valueOf(param.get("labs_sft_chk_id")));
						temp.put("wkpl_id", String.valueOf(param.get("wkpl_id")));
						temp.put("SESS_ID", userInfo.getUsrId());
						
						service.insertLabsTgtList(temp);
					}
				}
				
				if (updateList != null) {
					// 업데이트
					for(int i = 0; i < updateList.size(); i++) {
						Map<String, Object> temp = updateList.get(i);
						temp.put("labs_sft_chk_id", String.valueOf(param.get("labs_sft_chk_id")));
						temp.put("wkpl_id", String.valueOf(param.get("wkpl_id")));
						temp.put("SESS_ID", userInfo.getUsrId());
						
						service.updateLabsTgtList(temp);
					}
				}
				
				if (deleteList != null) {
					// 딜리트
					for(int i = 0; i < deleteList.size(); i++) {
						Map<String, Object> temp = deleteList.get(i);
						temp.put("labs_sft_chk_id", String.valueOf(param.get("labs_sft_chk_id")));
						temp.put("wkpl_id", String.valueOf(param.get("wkpl_id")));
						temp.put("SESS_ID", userInfo.getUsrId());
						
						service.deleteLabsTgtList(temp);
					}
				}
			}else {
				// 신규 번호 채번
				String key = service.selectLabssftChkKey(param);
				param.put("labs_sft_chk_id", key);
				result = key;
				
				service.insertLabssftChk(param);	// 기본정보 저장
				
				if (insertList != null) {
					// 인서트
					for(int i = 0; i < insertList.size(); i++) {
						Map<String, Object> temp = insertList.get(i);
						temp.put("labs_sft_chk_id", key);
						temp.put("wkpl_id", String.valueOf(param.get("wkpl_id")));
						temp.put("SESS_ID", userInfo.getUsrId());
						
						service.insertLabsTgtList(temp);
					}
				}
			}
			
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		// 연구실 일상점검 기본 정보, 안전점검대상 조회
		@GetMapping("/labssftchkmgnt")
		public ResponseEntity<?> selectLabssftChkMgnt(@RequestParam Map<String, Object> param, HttpSession session) {
			UserInfo userInfo = sessionManager.get(session.getId());
			
			Map<String, List<Map<String, ?>>> result = new HashMap<>();

			param.put("SESS_LANG", userInfo.getLangCd());
			param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

			// 기본 정보 가져오기
			List<Map<String, ?>> labsChkResult = service.selectlabsChkMgnt(param);
			// 그리드 리스트
			List<Map<String, ?>> labsChkTgtResult = service.selectlabsChkTgt(param);
			
			result.put("labsChkResult", labsChkResult);
			result.put("labsChkTgtResult", labsChkTgtResult);
			
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
		
		// 연구실 일상점검 목록 조회
		@GetMapping("/labssftchkmgntlist")
		public ResponseEntity<?> selectLabsChkMgntList(@RequestParam Map<String, Object> param, HttpSession session) {
			UserInfo userInfo = sessionManager.get(session.getId());

			param.put("SESS_LANG", userInfo.getLangCd());
			param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

			List<Map<String, ?>> list = service.selectLabsChkMgntList(param);
			
			return new ResponseEntity<>(list, HttpStatus.OK);
		}
		
		// QR 코드 키값 조회
		@GetMapping("/key-chk")
		public ResponseEntity<?> selectSftChkKeyVal(@RequestParam Map<String, Object> param, HttpSession session) {
			UserInfo userInfo = sessionManager.get(session.getId());
			param.put("SESS_ID", userInfo.getUsrId());
			
			String keyVal = "";
			String gb = "";
			if(String.valueOf(param.get("gubun")).equalsIgnoreCase("OPRN")) {
				keyVal = service.selectSftOprnChkKeyVal(param);
			} else if(String.valueOf(param.get("gubun")).equalsIgnoreCase("WKNS")) {
				keyVal = service.selectSftWknsChkKeyVal(param);
				gb = service.selectMbKeyChk(param);
			}
			
			Map<String, String> data = new HashMap<>();
			data.put("keyVal", keyVal);
			data.put("gb", gb);
			
			return new ResponseEntity<>(data, HttpStatus.OK);
		}
		
		@Transactional
		@PutMapping("/oprnbgnbfchkexcept")
		public ResponseEntity<?> updateOprnExcept(@RequestBody Map<String, Object> param, HttpSession session) {
			UserInfo userInfo = sessionManager.get(session.getId());
			
			List<Map<String, Object>> tempList = (List<Map<String, Object>>) param.get("excptList");
			
			String result = "";
			for(int i=0; i<tempList.size(); i++) {
				int n = service.updateOprnExcept(tempList.get(i));
				result = n > 0 ? Constant.SUCCESS : Constant.FAIL;
			}

			return new ResponseEntity<>(Constant.SUCCESS, HttpStatus.OK);
		}
	
}
