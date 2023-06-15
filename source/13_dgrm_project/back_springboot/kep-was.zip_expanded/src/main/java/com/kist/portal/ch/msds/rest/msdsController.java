package com.kist.portal.ch.msds.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kist.portal.ch.msds.service.msdsService;
import com.kist.portal.common.comm.service.FileService;
import com.kist.portal.common.email.rest.SendEmailUtil;
import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.common.utils.MultiLanguageUtil;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/ch/msds")
public class msdsController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private msdsService service;

	@Autowired
	private FileService fileService;
	
	@Value("${dgrm.front-url}")
	private String frontUrl;
	
	@Value("${dgrm.server-url}")
	private String serverUrl;

	@Value("${dgrm.app-temp-dir}")
    private String appTempDir;
	
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private SendEmailUtil util;
	
	@Autowired
	private MultiLanguageUtil mUtil;

	@GetMapping("/msds-list")
	public ResponseEntity<?> selectMsdsList(@RequestParam Map<String, String> param, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_LANG", userInfo.getLangCd());
		
		List<Map<String, ?>> list = service.selectMsdsList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}

	@GetMapping("/msds-submit-list")
	public ResponseEntity<?> selectMsdsSubmitList(@RequestParam Map<String, String> param, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_LANG", userInfo.getLangCd());
		
		List<Map<String, ?>> list = service.selectMsdsSubmitList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/msds-hst")
	public ResponseEntity<?> selectMsdsHstList(@RequestParam Map<String, String> param, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_LANG", userInfo.getLangCd());
		
		List<Map<String, ?>> list = service.selectMsdsHstList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/msds-dtl/{mtrlNo}")
	public ResponseEntity<?> selectMsdsDtl(@PathVariable String mtrlNo, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		Map<String, String> param = new HashMap<>();
		param.put("mtrl_no", mtrlNo);
		param.put("SESS_LANG", userInfo.getLangCd());
		
		// msds 상세 리스트
		List<Map<String, ?>> msdsList = service.selectMsdsDtl(param);
		// 물질 정보 리스트
		List<Map<String, ?>> mtrlList = service.selectMtrlDtl(param);
		// 규제 검토 리스트
		List<Map<String, ?>> rgltList = service.selectRgltDtl(param);
		// 경고 표지 정보
		List<Map<String, ?>> alertList = service.selectAlertDtl(param);
		// 그림문자 정보
		List<Map<String, ?>> picList = service.selectPicDtl(param);
		// 개정 이력 리스트
		List<Map<String, ?>> revList = service.selectRevDtl(param);
		
		Map<String, List<Map<String, ?>>> data = new HashMap<>();
		data.put("msds", msdsList);
		data.put("mtrl", mtrlList);
		data.put("alert", alertList);
		data.put("pic", picList);
		data.put("rglt", rgltList);
		data.put("rev", revList);
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@GetMapping("/msds-rev")
	public ResponseEntity<?> selectMsdsRevReqList(@RequestParam Map<String, String> param, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		
		List<Map<String, ?>> list = service.selectMsdsRevReqList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/msds-pop")
	public ResponseEntity<?> selectMsdsPopList(@RequestParam Map<String, String> param, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		
		List<Map<String, ?>> list = service.selectMsdsPopList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/msds-pop2")
	public ResponseEntity<?> selectMsdsPopScList(@RequestParam Map<String, String> param, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		
		List<Map<String, ?>> list = service.selectMsdsPopScList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/msds-rev-dtl/{mtrlNo}")
	public ResponseEntity<?> selectMsdsRevSchDtl(@PathVariable String mtrlNo, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		Map<String, String> param = new HashMap<>();
		param.put("mtrl_no", mtrlNo);
		param.put("SESS_LANG", userInfo.getLangCd());
		
		String res = "";
		
		// 팝업 조회에 의한 mtrl_no 자재 조회로서 진행 상태 코드가 완료 상태가 아닌 다른 상태를 가진 mtrl_no가 존재할 경우 return false
		List<Map<String, ?>> result = service.sltAlMtrlDt(param);
		// 완료 상태가 아닌 해당 자제 번호의 개정 요청 이력이 존재할 경우
		if(result.size() > 0) {
			res = "FAIL";
			
			return new ResponseEntity<>(res, HttpStatus.OK);
		} else {
			// 개정 데이터가 최신화 되었으므로 이전 버전 데이터를 msds 테이블에서 가져온다.
			List<Map<String, ?>> list = service.selectMsdsRevDtl(param);
			List<Map<String, ?>> alert = service.selectAlertDtl(param);
			List<Map<String, ?>> pic = service.selectPicDtl(param);
			// List<Map<String, ?>> chk = service.selectMsdsChkRslt(param);
			
			Map<String, List<Map<String, ?>>> data = new HashMap<>();
			data.put("msds", list);
			data.put("alert", alert);
			data.put("pic", pic);
			
			return new ResponseEntity<>(data, HttpStatus.OK);
		}
	}
	
	@GetMapping("/msds-rev-upt/{msdsRevNo}")
	public ResponseEntity<?> selectMsdsRevDtl(@PathVariable String msdsRevNo, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		Map<String, String> param = new HashMap<>();
		param.put("msds_rev_no", msdsRevNo);
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIMEZONE", userInfo.getTimeZone());
	
		// msds 개정 테이블에서 정보를 가져온다. (이미 저장된 데이터)
		List<Map<String, ?>> list = service.selectMsdsRevBfDtl(param);
		List<Map<String, ?>> alert = service.selectAlertBfDtl(param);
		List<Map<String, ?>> pic = service.selectPicBfDtl(param);
		
		Map<String, List<Map<String, ?>>> data = new HashMap<>();
		data.put("msds", list);
		data.put("alert", alert);
		data.put("pic", pic);
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@GetMapping("/authchk")
	public ResponseEntity<?> selectMsdsAuthChk(@RequestParam Map<String, String> param, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		
		String result = "";
		
		// 유저 dept_chk 및 진행 상태 chk (동일하면 update, 다를 경우 read)
		if(param.containsKey("dept_cd")) {
			result = service.selectChkDeptYn(param);
		} else {
			result = "READ";
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/auth-revchk")
	public ResponseEntity<?> selectMsdsAuthRevChk(@RequestParam Map<String, String> param, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		
		String result = "";
		
		// msds_rev_no와 usr_id를 통해 해당 사원이 개정 검토에 대한 담당자인지, 해당 사업장인지 확인한다.
		if(param.containsKey("usr_id")) {
			List<Map<String, ?>> data = service.selectRevChkDeptYn(param);
			if(data.size() > 0) {
				result = "UPDATE";
			} else {
				result = "READ";
			}
		} else {
			result = "READ";
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	@PostMapping("/msds-rev")
	public ResponseEntity<?> insertMsdsRev(@RequestBody Map<String, Object> param) {
		
		String chk = "SUCCESS";
		
		//데이터 초기화
		Map<String, String> msds = (Map<String, String>)param.get("msds");
		System.out.println(msds);
		Map<String, String> alert = (Map<String, String>)param.get("alert");
		System.out.println(alert);
		List<Map<String, String>> picList = (List<Map<String, String>>)param.get("pic");
		System.out.println(picList);
		
		//저장 전에 유효성 검사한다.(최초 저장이므로 진행 상태만 체크)
		msds.put("dept_cd", param.get("dept_cd").toString());
		List<Map<String, ?>> chkList = service.sltAlMtrlDt(msds);
		if(chkList.size() > 0) {
			chk = "FAIL";
		}
		else {
			chk = "SUCCESS";
		}
		
		int n = 0;
		int m = 0;
		int j = 0;
		String result = "Fail";
		
		//유효성 검사 통과 시에만 insert 처리
		if(chk != "FAIL") {
			// msds 키 채번
			String key = service.selectMsdsKey(msds);
			msds.put("msds_rev_no", key);
			// 날짜 포맷 수정
			if(msds.get("rev_bf_enact_da") != null) {
				msds.put("rev_bf_enact_da", msds.get("rev_bf_enact_da").toString().replace("-", ""));
			}
			if(msds.get("rev_af_enact_da") != null) {
				msds.put("rev_af_enact_da", msds.get("rev_af_enact_da").toString().replace("-", ""));
			}
			if(msds.get("rev_bf_last_rev_da") != null) {
				msds.put("rev_bf_last_rev_da", msds.get("rev_bf_last_rev_da").toString().replace("-", ""));
			}
			if(msds.get("rev_af_last_rev_da") != null) {
				msds.put("rev_af_last_rev_da", msds.get("rev_af_last_rev_da").toString().replace("-", ""));
			}
			// 기본 정보 insert
			n = service.insertMsdsRev(msds);
			
			// 경고 표지 update
			alert.put("req_usr_id", msds.get("req_usr_id"));
			alert.put("msds_rev_no", key);
			alert.put("mtrl_no", msds.get("mtrl_no"));
			m = service.insertMsdsRevAlert(alert);
			
			// 그림 문자
			for(int i = 0; i < picList.size(); i++) {
				Map<String, String> temp = picList.get(i);
				temp.put("msds_rev_no", key);
				temp.put("mtrl_no", msds.get("mtrl_no"));
				temp.put("req_usr_id", msds.get("req_usr_id"));
				j = service.insertMsdsRevPic(temp);
			}
			
			result = (n > -1? (m > -1? (j > -1? key : Constant.FAIL) : Constant.FAIL) : Constant.FAIL);
		}else {
			result = "READ";
		}
		
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	@PutMapping("/msds-rev")
	public ResponseEntity<?> updateMsdsRev(@RequestBody Map<String, Object> param) {
		
		String chk = "SUCCESS";
		
		//데이터 초기화
		Map<String, String> msds = (Map<String, String>)param.get("msds");
		System.out.println(msds);
		Map<String, String> alert = (Map<String, String>)param.get("alert");
		System.out.println(alert);
		List<Map<String, String>> picList = (List<Map<String, String>>)param.get("pic");
		System.out.println(picList);
		
		//저장 전에 유효성 검사한다.(진행상태가 10, 30이거나 저장 유저의 dept_cd 체크헤준다.)
		if(param.get("dept_cd") == null) {
			chk = "FAIL";
		} else {
			msds.put("dept_cd", param.get("dept_cd").toString());
			chk = service.selectChkDeptYn(msds);
			if(!chk.equalsIgnoreCase("UPDATE")) {
				chk = "FAIL";
			}
			else {
				chk = "SUCCESS";
			}
		}
		
		int n = 0;
		int m = 0;
		int j = 0;
		String result = "Fail";
		
		//유효성 검사 통과 시에만 insert 처리
		if(chk != "FAIL") {
			// 날짜 포맷 수정
			if(msds.get("rev_bf_enact_da") != null) {
				msds.put("rev_bf_enact_da", msds.get("rev_bf_enact_da").toString().replace("-", ""));
			}
			if(msds.get("rev_af_enact_da") != null) {
				msds.put("rev_af_enact_da", msds.get("rev_af_enact_da").toString().replace("-", ""));
			}
			if(msds.get("rev_bf_last_rev_da") != null) {
				msds.put("rev_bf_last_rev_da", msds.get("rev_bf_last_rev_da").toString().replace("-", ""));
			}
			if(msds.get("rev_af_last_rev_da") != null) {
				msds.put("rev_af_last_rev_da", msds.get("rev_af_last_rev_da").toString().replace("-", ""));
			}
			// 기본 정보 insert
			n = service.updateMsdsRev(msds);
			
			// 경고 표지 update
			alert.put("req_usr_id", msds.get("req_usr_id"));
			alert.put("mtrl_no", msds.get("mtrl_no"));
			alert.put("msds_rev_no", msds.get("msds_rev_no"));
			m = service.updateMsdsRevAlert(alert);
			
			// 그림 문자 초기화
			service.deleteMsdsRevPic(msds);
			// 그림 문자
			for(int i = 0; i < picList.size(); i++) {
				Map<String, String> temp = picList.get(i);
				temp.put("msds_rev_no", msds.get("msds_rev_no"));
				temp.put("mtrl_no", msds.get("mtrl_no"));
				temp.put("req_usr_id", msds.get("req_usr_id"));
				j = service.updateMsdsRevPic(temp);
			}
			
			result = (n > -1? (m > -1? (j > -1? msds.get("msds_rev_no") : Constant.FAIL) : Constant.FAIL) : Constant.FAIL);
		}else {
			result = "READ";
		}
		
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@Transactional
	@SuppressWarnings("unchecked")
	@PutMapping("/msds-submit")
	public ResponseEntity<?> updateMsdsSubmit(@RequestBody Map<String, Object> param) {
		
		String chk = "SUCCESS";
		
		//데이터 초기화
		Map<String, String> msds = (Map<String, String>)param.get("msds");

		int n = 0;
		String result = "Fail";
		
		//유효성 검사 통과 시에만 insert 처리
		if(chk != "FAIL") {
			// 기본 정보 insert
			n = service.updateMsdsSubmit(msds);
			
			result = (n > -1? msds.get("mtrl_no") : Constant.FAIL);
		}else {
			result = "READ";
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	@PostMapping("/msds-req")
	public ResponseEntity<?> upsertMsdsRevReq(@RequestBody Map<String, Object> param, HttpSession session) {
		
		Boolean mailResult = sendMsdsReqMail(param, session);
		
		if(!mailResult) {
			return new ResponseEntity<>(Constant.FAIL, HttpStatus.OK);
		}
		
		//데이터 초기화
		Map<String, String> msds = (Map<String, String>)param.get("msds");
		System.out.println(msds);
		Map<String, String> alert = (Map<String, String>)param.get("alert");
		System.out.println(alert);
		List<Map<String, String>> picList = (List<Map<String, String>>)param.get("pic");
		System.out.println(picList);
		
		int n = 0;
		int m = 0;
		int j = 0;
		int k = 0;
		String result = "Fail";
		
		// 날짜 포맷 수정
		if(msds.get("rev_bf_enact_da") != null) {
			msds.put("rev_bf_enact_da", msds.get("rev_bf_enact_da").toString().replace("-", ""));
		}
		if(msds.get("rev_af_enact_da") != null) {
			msds.put("rev_af_enact_da", msds.get("rev_af_enact_da").toString().replace("-", ""));
		}
		if(msds.get("rev_bf_last_rev_da") != null) {
			msds.put("rev_bf_last_rev_da", msds.get("rev_bf_last_rev_da").toString().replace("-", ""));
		}
		if(msds.get("rev_af_last_rev_da") != null) {
			msds.put("rev_af_last_rev_da", msds.get("rev_af_last_rev_da").toString().replace("-", ""));
		}
		// 기본 테이블 상태코드, 요청일 업데이트(->20 개정 요청)
		n = service.updateMsdsRevStt(msds);
		
		// 신규 테이블 기본 테이블 upsert
		k = service.upsertMsdsRevReq(msds);
		
		// 경고 표지 upsert
		alert.put("req_usr_id", msds.get("req_usr_id"));
		alert.put("mtrl_no", msds.get("mtrl_no"));
		alert.put("msds_rev_no", msds.get("msds_rev_no"));
		if(alert.get("vndr_kyin_yn").equalsIgnoreCase("N")) {
			alert.put("vndr_kyin_yn", "N");
		} else {
			alert.put("vndr_kyin_yn", "Y");
		}
		m = service.upsertMsdsRevReqAlert(alert);
		
		// 그림 문자 초기화
		service.deleteMsdsChkRevPic(msds);
		// 그림 upsert
		for(int i = 0; i < picList.size(); i++) {
			Map<String, String> temp = picList.get(i);
			temp.put("msds_rev_no", msds.get("msds_rev_no"));
			temp.put("mtrl_no", msds.get("mtrl_no"));
			temp.put("req_usr_id", msds.get("req_usr_id"));
			j = service.upsertMsdsRevReqPic(temp);
		}
		
		result = (n > -1? (m > -1? (j > -1? (k > -1? Constant.SUCCESS : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@DeleteMapping("/msds-rev/{msdsRevNo}")
	public ResponseEntity<?> deleteMsdsRevReq(@PathVariable String msdsRevNo, HttpSession session) {
		// 언어 셋 지정		
		Map<String, String> param = new HashMap<>();
		param.put("msds_rev_no", msdsRevNo);
		
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("dept_cd", userInfo.getDeptCd());
		
		String chk = "";
		
		//저장 전에 유효성 검사한다.(진행상태가 10, 30이거나 저장 유저의 dept_cd 체크헤준다.)
		if(param.get("dept_cd") == null) {
			chk = "FAIL";
		} else {
			chk = service.selectChkDeptYn(param);
			if(!chk.equalsIgnoreCase("UPDATE")) {
				chk = "FAIL";
			}
			else {
				chk = "SUCCESS";
			}
		}
		
		String result = "";
		
		// 권한이 있을 경우에만 삭제 처리
		if(chk != "FAIL") {
			// 개정 요청 데이터 삭제
			int n = service.deleteMsdsRev(param);
			// 긴급 문구 데이터 삭제
			int m = service.deleteMsdsRevAlert(param);
			// 그림 문자 데이터 삭제
			int k = service.deleteMsdsRevPic(param);
			
			result = (n > -1? (m > -1? (k > -1? Constant.SUCCESS : Constant.FAIL) : Constant.FAIL) : Constant.FAIL); 
		} else {
			result = Constant.FAIL;
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/msds-chk")
	public ResponseEntity<?> selectMsdsRevChkList(@RequestParam Map<String, String> param, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIMEZONE", userInfo.getTimeZone());
		
		List<Map<String, ?>> list = service.selectMsdsRevChkList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/msds-rechk/{msdsRevNo}")
	public ResponseEntity<?> selectMsdsRevChkDtl(@PathVariable String msdsRevNo, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		Map<String, String> param = new HashMap<>();
		param.put("msds_rev_no", msdsRevNo);
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIMEZONE", userInfo.getTimeZone());
	
		// msds 개정 테이블에서 정보를 가져온다. (이미 저장된 데이터)
		List<Map<String, ?>> list = service.selectMsdsRevChkDtl(param);
		List<Map<String, ?>> alert = service.selectAlertChkDtl(param);
		List<Map<String, ?>> pic = service.selectPicChkDtl(param);
		
		Map<String, List<Map<String, ?>>> data = new HashMap<>();
		data.put("msds", list);
		data.put("alert", alert);
		data.put("pic", pic);
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	@PutMapping("/msds-chk")
	public ResponseEntity<?> updateMsdsChkRev(@RequestBody Map<String, Object> param, HttpSession session) {
		
		String chk = "SUCCESS";
		
		//데이터 초기화
		Map<String, String> msds = (Map<String, String>)param.get("msds");
		System.out.println(msds);
		Map<String, String> alert = (Map<String, String>)param.get("alert");
		System.out.println(alert);
		List<Map<String, String>> picList = (List<Map<String, String>>)param.get("pic");
		System.out.println(picList);
		
		// msds_rev_no와 usr_id를 통해 해당 사원이 개정 검토에 대한 담당자인지, 해당 사업장인지 확인한다.
		if(msds.containsKey("usr_id")) {
			msds.put("usr_id", msds.get("usr_id"));
			List<Map<String, ?>> data = service.selectRevChkDeptYn(msds);
			if(data.size() > 0) {
				chk = "SUCCESS";
			} else {
				chk = "FAIL";
			}
		} else {
			chk = "FAIL";
		}
		
		int n = 0;
		int m = 0;
		int j = 0;
		String result = "Fail";
		
		//유효성 검사 통과 시에만 insert 처리
		if(chk != "FAIL") {
			// 날짜 포맷 수정
			if(msds.get("rev_bf_enact_da") != null) {
				msds.put("rev_bf_enact_da", msds.get("rev_bf_enact_da").toString().replace("-", ""));
			}
			if(msds.get("rev_af_enact_da") != null) {
				msds.put("rev_af_enact_da", msds.get("rev_af_enact_da").toString().replace("-", ""));
			}
			if(msds.get("rev_bf_last_rev_da") != null) {
				msds.put("rev_bf_last_rev_da", msds.get("rev_bf_last_rev_da").toString().replace("-", ""));
			}
			if(msds.get("rev_af_last_rev_da") != null) {
				msds.put("rev_af_last_rev_da", msds.get("rev_af_last_rev_da").toString().replace("-", ""));
			}
			// 기본 정보 insert
			n = service.updateMsdsChkRev(msds);
			
			// 경고 표지 update
			alert.put("usr_id", msds.get("usr_id"));
			alert.put("mtrl_no", msds.get("mtrl_no"));
			alert.put("msds_rev_no", msds.get("msds_rev_no"));
			m = service.updateMsdsChkRevAlert(alert);
			
			// 그림 문자 초기화
			service.deleteMsdsChkRevPic(msds);
			// 그림 문자
			for(int i = 0; i < picList.size(); i++) {
				Map<String, String> temp = picList.get(i);
				temp.put("msds_rev_no", msds.get("msds_rev_no"));
				temp.put("mtrl_no", msds.get("mtrl_no"));
				temp.put("usr_id", msds.get("usr_id"));
				j = service.updateMsdsChkRevPic(temp);
			}
			
			service.updateMsdsRevChkStt(msds);
			
			result = (n > -1? (m > -1? (j > -1? msds.get("msds_rev_no") : Constant.FAIL) : Constant.FAIL) : Constant.FAIL);
		}else {
			result = "READ";
		}
		
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	@PutMapping("/msds-rjt")
	public ResponseEntity<?> updateMsdsRjtStt(@RequestBody Map<String, Object> param, HttpSession session) {
		
		Boolean mailResult = sendMsdsRjtMail(param, session);
		
		if(!mailResult) {
			return new ResponseEntity<>(Constant.FAIL, HttpStatus.OK);
		}
		
		//데이터 초기화
		Map<String, String> msds = (Map<String, String>)param.get("msds");
		
		// msds 개정 요청 상태값 변경
		int n = service.updateMsdsRevRjtStt(msds);  
		
		String result = n > -1 ? Constant.SUCCESS : Constant.FAIL;
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	@PutMapping("/chk-end")
	public ResponseEntity<?> updateChkFinishStt(@RequestBody Map<String, Object> param, HttpSession session) {
		
		Boolean mailResult = sendMsdsChkMail(param, session);
		
		if(!mailResult) {
			return new ResponseEntity<>(Constant.FAIL, HttpStatus.OK);
		}
		
		//데이터 초기화
		Map<String, String> msds = (Map<String, String>)param.get("msds");
		Map<String, String> alert = (Map<String, String>)param.get("alert");
		List<Map<String, String>> picList = (List<Map<String, String>>)param.get("pic");
		
		String result = "Fail";

		// 날짜 포맷 수정
		if(msds.get("rev_bf_enact_da") != null) {
			msds.put("rev_bf_enact_da", msds.get("rev_bf_enact_da").toString().replace("-", ""));
		}
		if(msds.get("rev_af_enact_da") != null) {
			msds.put("rev_af_enact_da", msds.get("rev_af_enact_da").toString().replace("-", ""));
		}
		if(msds.get("rev_bf_last_rev_da") != null) {
			msds.put("rev_bf_last_rev_da", msds.get("rev_bf_last_rev_da").toString().replace("-", ""));
		}
		if(msds.get("rev_af_last_rev_da") != null) {
			msds.put("rev_af_last_rev_da", msds.get("rev_af_last_rev_da").toString().replace("-", ""));
		}
		
		// msds alert 테이블에 업데이트
		alert.put("usr_id", msds.get("usr_id"));
		alert.put("mtrl_no", msds.get("mtrl_no"));
		
		String vndrNo = "";
		// 직접 입력의 경우 공급자 마스터 테이블에 입력을 위해 키 조회
		if("Y".equalsIgnoreCase(alert.get("vndr_kyin_yn"))) {
			vndrNo = service.selectVndrKey(msds);
			// 공급자 확인하여 마스터 테이블에 업데이트
			alert.put("vndr_no", vndrNo);
			service.insertVndrMstData(alert);
			// 공급자 자재 테이블에 추가
			service.insertMtrlVndrMstData(alert);
		}else {
			vndrNo = alert.get("vndr_no");
		}
		
		// msds 마스터 테이블에 업데이트 
		int n = service.updateMsdsMstData(msds);
		int m = service.updateMsdsAlertMstData(alert);
		
		// 그림 문자 초기화
		service.deleteMsdsMstPic(msds);
		
		int j = 0;
		
		// msds 그림문자 테이블에 업데이트
		for(int i = 0; i < picList.size(); i++) {
			Map<String, String> temp = picList.get(i);
			temp.put("mtrl_no", msds.get("mtrl_no"));
			temp.put("usr_id", msds.get("usr_id"));
			j = service.updateMsdsPicMstData(temp);
		}
		
		// 진행 상태 코드 -> 검토 완료 업데이트(검토 내용, 일자 등 업데이트)
		int k = service.updateMsdsFinishStt(msds);
		
		result = (n > -1? (m > -1? (j > -1? k > -1 ? Constant.SUCCESS : Constant.FAIL : Constant.FAIL) : Constant.FAIL) : Constant.FAIL);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/law")
	public ResponseEntity<?> selectMsdsLawCd(HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		
		Map<String, String> param = new HashMap<>();
		
		param.put("SESS_LANG", userInfo.getLangCd());
		
		List<Map<String, ?>> list = service.selectMsdsLawCd(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	public boolean sendMsdsReqMail(Map<String, Object> param, HttpSession session) {
		// 데이터 초기화
		Map<String, String> msds = (Map<String, String>)param.get("msds");
		Map<String, String> alert = (Map<String, String>)param.get("alert");
		List<Map<String, String>> picList = (List<Map<String, String>>)param.get("pic");
		
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		String langCd = userInfo.getLangCd();
		param.put("SESS_LANG", langCd);
		
		msds.put("SESS_LANG", langCd);
		msds.put("SESS_TIMEZONE", userInfo.getTimeZone());
		List<Map<String, ?>> msdsList = service.selectMsdsRevBfDtl(msds);
		List<Map<String, ?>> alertList = service.selectAlertBfDtl(msds);
		
		// 다국어 처리
		String[] mlangNo = {"MS00000059", "LB00000151", "LB00000150",
				"LB00000149", "LB00000199", "LB00000025",
				"LB00000200", "LB00000201", "LB00000202",
				"LB00000203", "LB00000204", "LB00000205",
				"LB00000206", "LB00000207", "LB00000208",
				"LB00000209", "LB00000210", "LB00000211",
				"LB00000173", "LB00000172", "LB00000193",
				"LB00000192", "LB00000191", "LB00000190",
				"LB00000045", "LB00000189", "LB00000130",
				"LB00000186", "LB00000185", "LB00000184",
				"LB00000183", "LB00000182"};
		List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo), langCd);
		
		// 그림문자 html 태그
		String subTag = "";
		for(int i=0; i<picList.size(); i++) {
			if(picList.get(i).get("picg_cd").equalsIgnoreCase("1")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P01.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").equalsIgnoreCase("2")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P02.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").equalsIgnoreCase("3")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P03.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").equalsIgnoreCase("4")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P04.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").equalsIgnoreCase("5")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P05.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").equalsIgnoreCase("6")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P06.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").equalsIgnoreCase("7")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P07.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").equalsIgnoreCase("8")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P08.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").equalsIgnoreCase("9")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P09.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			}
		}
		
		// 메일 업무 코드
		String bizCd = "10";
		// 메일 제목
		String subject = mUtil.getMLang(mlangCntn, "MS00000059") + "(" + msds.get("mtrl_no") + " : " + msds.get("mtrl_nm") + ")";
		// 메일 내용
		String content = "<div>\r\n" + 
				"    <p>\r\n" + 
				"        <spam style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang("LB00000198", langCd) + " :&nbsp;</spam><a href=\"" + frontUrl + "/msds/msdsrevchkedt/" + msds.get("msds_rev_no") + "\" style=\"font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + frontUrl + "/msds/msdsrevchkedt/" + msds.get("msds_rev_no") + "</a>\r\n" + 
				"    </p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang("CD00000801", langCd) + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000151") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + msds.get("msds_rev_no") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000150") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + msdsList.get(0).get("req_usr_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000149") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + msds.get("req_da") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000199") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + msds.get("mtrl_no") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000025") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + msds.get("mtrl_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000200") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + msdsList.get(0).get("rev_wkpl_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">MSDS</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 20%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000201") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 30%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + mUtil.getMLang(mlangCntn, "LB00000202") + " : " + msds.get("rev_bf_enact_da") + "</p>\r\n" + 
				"                    <p>" + mUtil.getMLang(mlangCntn, "LB00000203") + " : " + msds.get("rev_bf_last_rev_da") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\"><span style=\"font-size: 13.3333px;\">" + mUtil.getMLang(mlangCntn, "LB00000204") + "</span><br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 30%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + mUtil.getMLang(mlangCntn, "LB00000202") + " : " + msds.get("rev_af_enact_da") + "</p>\r\n" + 
				"                    <p>" + mUtil.getMLang(mlangCntn, "LB00000203") + " : " + msds.get("rev_af_last_rev_da") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 137px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000205") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 284px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + msds.get("rev_bf_ver") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 141px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000206") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 284px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + msds.get("rev_af_ver") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 137px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000207") + "</p>\r\n" + 
				"                </td>\r\n" +
				"                <td style=\"width: 284px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n";
	if((!"".equals(msds.get("rev_bf_ko_atfl_no")) && null != (msds.get("rev_bf_ko_atfl_no"))) || (!"".equals(msds.get("rev_bf_ko_atfl_nm")) && null != (msds.get("rev_bf_ko_atfl_nm")))) {	
	 content += "                    <p>" + "<a href=\"" + serverUrl + "/api/v1/public/download/" + String.valueOf(msds.get("rev_bf_ko_atfl_no")) + "\">" + msds.get("rev_bf_ko_atfl_nm") + "</a>" + "<br></p>\r\n";
	}
	 content += "                </td>\r\n" +
	 			"                <td style=\"width: 141px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000208") + "<br></p>\r\n" + 
				"                </td>\r\n" +
	 			"                <td style=\"width: 284px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n"; 
	if((!"".equals(msds.get("rev_af_ko_atfl_no")) && null != (msds.get("rev_af_ko_atfl_no"))) || (!"".equals(msds.get("rev_af_ko_atfl_nm")) && null != (msds.get("rev_af_ko_atfl_nm")))) {			
	 content += "                    <p>" + "<a href=\"" + serverUrl + "/api/v1/public/download/" + String.valueOf(msds.get("rev_af_ko_atfl_no")) + "\">" + msdsList.get(0).get("rev_af_ko_atfl_nm") + "</a>" + "<br></p>\r\n";
	}	 
	 content += "                </td>\r\n" +
	 			"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 137px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000209") + "</p>\r\n" + 
				"                </td>\r\n" +
	  			"                <td style=\"width: 284px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n";
	if((!"".equals(msds.get("rev_bf_en_atfl_no")) && null != (msds.get("rev_bf_en_atfl_no"))) || (!"".equals(msds.get("rev_bf_en_atfl_nm")) && null != (msds.get("rev_bf_en_atfl_nm")))) {	
	 content += "                    <p>" + "<a href=\"" + serverUrl + "/api/v1/public/download/" + String.valueOf(msds.get("rev_bf_en_atfl_no")) + "\">" + msds.get("rev_bf_en_atfl_nm") + "</a>" + "<br></p>\r\n";
	}
	 content += "                </td>\r\n" +
	 			"                <td style=\"width: 141px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000210") + "<br></p>\r\n" + 
				"                </td>\r\n" +
				"                <td style=\"width: 284px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n";
	if((!"".equals(msds.get("rev_af_en_atfl_no")) && null != (msds.get("rev_af_en_atfl_no"))) || (!"".equals(msds.get("rev_af_en_atfl_nm")) && null != (msds.get("rev_af_en_atfl_nm")))) {
	 content += "                    <p>" + "<a href=\"" + serverUrl + "/api/v1/public/download/" + String.valueOf(msds.get("rev_af_en_atfl_no")) + "\">" + msdsList.get(0).get("rev_af_en_atfl_nm") + "</a>" + "<br></p>\r\n"; 
	}	
	 content += "                </td>\r\n" +
	 			"            </tr>\r\n" + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000211") + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 20%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000025") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 80%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + msds.get("mtrl_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000173") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 80%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + alertList.get(0).get("sgw_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 20%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000172") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 80%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				subTag + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000193") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 80%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + alert.get("hars_cntn").replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td rowspan=\"4\" style=\"width: 13%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000192") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 7%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000191") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"margin: 3px 3px 3px 3px; width: 80%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + alert.get("prv_cntn").replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000190") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"margin: 3px 3px 3px 3px; width: 80%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + alert.get("act_cntn").replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000045") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"margin: 3px 3px 3px 3px; width: 80%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"					 <p>" + alert.get("str_cntn").replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" +
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000189") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"margin: 3px 3px 3px 3px; width: 80%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"					 <p>" + alert.get("scr_cntn").replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" +
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000130") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + alert.get("vndr_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000186") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 30%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + alert.get("vndr_addr") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000185") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 302px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + alertList.get(0).get("nat_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 211px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000184") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 212px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + alert.get("vndr_phon_no") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000183") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 302px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + alert.get("vndr_rprs_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 211px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000182") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 212px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + alert.get("vndr_coreg_no") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 
				"</div>";
		// 메일 수신자
		List<Map<String, String>> msdsChkAuthMem = service.selectMsdsChkUsrList(msds);
		String[] to = new String[msdsChkAuthMem.size()];
		for(int i=0; i<msdsChkAuthMem.size(); i++) {
			to[i] = msdsChkAuthMem.get(i).get("usr_id");
		}
		// 메일 송신
		Boolean mailResult = util.sendMail(bizCd, msds.get("req_usr_id"), subject, content, to);
		
		return mailResult; 
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	public boolean sendMsdsChkMail(Map<String, Object> param, HttpSession session) {
		// 데이터 초기화
		Map<String, String> msds = (Map<String, String>)param.get("msds");
		Map<String, String> alert = (Map<String, String>)param.get("alert");
		List<Map<String, String>> picList = (List<Map<String, String>>)param.get("pic");
		
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		String langCd = userInfo.getLangCd();
		param.put("SESS_LANG", langCd);
		
		// 다국어 처리
		String[] mlangNo = {"MS00000059", "LB00000151", "LB00000150",
				"LB00000149", "LB00000199", "LB00000025",
				"LB00000200", "LB00000201", "LB00000202",
				"LB00000203", "LB00000204", "LB00000205",
				"LB00000206", "LB00000207", "LB00000208",
				"LB00000209", "LB00000210", "LB00000211",
				"LB00000173", "LB00000172", "LB00000193",
				"LB00000192", "LB00000191", "LB00000190",
				"LB00000045", "LB00000189", "LB00000130",
				"LB00000186", "LB00000185", "LB00000184",
				"LB00000183", "LB00000182", "LB00000282",
				"LB00000283", "LB00000284", "MS00000155",
				"LB00000356"};
		List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo), langCd);
		
		// 그림문자 html 태그
		String subTag = "";
		for(int i=0; i<picList.size(); i++) {
			if(picList.get(i).get("picg_cd").equalsIgnoreCase("1")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P01.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").equalsIgnoreCase("2")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P02.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").equalsIgnoreCase("3")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P03.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").equalsIgnoreCase("4")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P04.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").equalsIgnoreCase("5")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P05.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").equalsIgnoreCase("6")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P06.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").equalsIgnoreCase("7")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P07.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").equalsIgnoreCase("8")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P08.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").equalsIgnoreCase("9")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P09.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			}
		}
		Calendar cal = Calendar.getInstance();
		// 메일 업무 코드
		String bizCd = "11";
		// 메일 제목
		String subject = mUtil.getMLang(mlangCntn, "MS00000155") + "(" + msds.get("mtrl_no") + " : " + msds.get("mtrl_nm") + ")";
		// 메일 내용
		String content = "<div>\r\n" + 
				"    <p>\r\n" + 
				"        <spam style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang("LB00000356", langCd) + " :&nbsp;</spam><a href=\"" + frontUrl + "/msds/msdsrevchklup/" + msds.get("msds_rev_no") + "\" style=\"font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + frontUrl + "/msds/msdsrevchklup/" + msds.get("msds_rev_no") + "</a>\r\n" + 
				"    </p>\r\n" + 
				"<p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>" +
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang("CD00000801", langCd) + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000151") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + msds.get("msds_rev_no") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000150") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + msds.get("req_usr_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000149") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + msds.get("req_da") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000199") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + msds.get("mtrl_no") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000025") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + msds.get("mtrl_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000200") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + msds.get("rev_wkpl_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 
				"<p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>" +
				"<p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000282") + "</p>\r\n" + 
				"<div><span style=\"font-size: 12pt;\">\r\n" + 
				"        <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border:none;border-collapse:collapse;word-break:break-all;font-family:&quot;Malgun Gothic&quot;, &quot;맑은 고딕&quot;;font-size:13.3333px\">\r\n" + 
				"            <tbody>\r\n" + 
				"                <tr>\r\n" + 
				"                    <td style=\"width: 20%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                        <p><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000283") + "</span></p>\r\n" + 
				"                    </td>\r\n" + 
				"                    <td style=\"width: 30%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                        <p>" + userInfo.getUsrNm() + "<br></p>\r\n" + 
				"                    </td>\r\n" + 
				"                    <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                        <p><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000284") + "</span></p>\r\n" + 
				"                    </td>\r\n" + 
				"                    <td style=\"width: 30%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                        <p>" + cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH) + "<br></p>\r\n" + 
				"                    </td>\r\n" + 
				"                </tr>\r\n" + 
				"                <tr>\r\n" + 
				"                    <td style=\"width: 20%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                        <p><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000282") + "</span></p>\r\n" + 
				"                    </td>\r\n" + 
				"                    <td style=\"width: 80%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"3\">\r\n" + 
				"                        <p>" + msds.get("rev_chk_cntn") + "<br></p>\r\n" + 
				"                    </td>\r\n" + 
				"                </tr>\r\n" + 
				"            </tbody>\r\n" + 
				"        </table>\r\n" + 
				"    </span>\r\n" + 
				"</div>" +
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">MSDS</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 20%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000201") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 30%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + mUtil.getMLang(mlangCntn, "LB00000202") + " : " + msds.get("rev_bf_enact_da") + "</p>\r\n" + 
				"                    <p>" + mUtil.getMLang(mlangCntn, "LB00000203") + " : " + msds.get("rev_bf_last_rev_da") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\"><span style=\"font-size: 13.3333px;\">" + mUtil.getMLang(mlangCntn, "LB00000204") + "</span><br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 30%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + mUtil.getMLang(mlangCntn, "LB00000202") + " : " + msds.get("rev_af_enact_da") + "</p>\r\n" + 
				"                    <p>" + mUtil.getMLang(mlangCntn, "LB00000203") + " : " + msds.get("rev_af_last_rev_da") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 137px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000205") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 284px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + msds.get("rev_bf_ver") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 141px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000206") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 284px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + msds.get("rev_af_ver") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 137px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000207") + "</p>\r\n" + 
				"                </td>\r\n" +
	 			"                <td style=\"width: 284px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n"; 
	if((!"".equals(msds.get("rev_bf_ko_atfl_no")) && null != (msds.get("rev_bf_ko_atfl_no"))) || (!"".equals(msds.get("rev_bf_ko_atfl_nm")) && null != (msds.get("rev_bf_ko_atfl_nm")))) {
	 content += "                    <p>" + "<a href=\"" + serverUrl + "/api/v1/public/download/" + String.valueOf(msds.get("rev_bf_ko_atfl_no")) + "\">" + msds.get("rev_bf_ko_atfl_nm") + "</a>" + "<br></p>\r\n";
	}
	 content += "                </td>\r\n" +
	 			"                <td style=\"width: 141px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000208") + "<br></p>\r\n" + 
				"                </td>\r\n" +
				"                <td style=\"width: 284px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n";
	if((!"".equals(msds.get("rev_af_ko_atfl_no")) && null != (msds.get("rev_af_ko_atfl_no"))) || (!"".equals(msds.get("rev_af_ko_atfl_nm")) && null != (msds.get("rev_af_ko_atfl_nm")))) {
	 content += "                    <p>" + "<a href=\"" + serverUrl + "/api/v1/public/download/" + String.valueOf(msds.get("rev_af_ko_atfl_no")) + "\">" + msds.get("rev_af_ko_atfl_nm") + "</a>" + "<br></p>\r\n";
	}	
	 content += "                </td>\r\n" +
	 			"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 137px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000209") + "</p>\r\n" + 
				"                </td>\r\n" +
				"                <td style=\"width: 284px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n"; 
	if((!"".equals(msds.get("rev_bf_en_atfl_no")) && null != (msds.get("rev_bf_en_atfl_no"))) || (!"".equals(msds.get("rev_bf_en_atfl_nm")) && null != (msds.get("rev_bf_en_atfl_nm")))) {
	 content += "                    <p>" + "<a href=\"" + serverUrl + "/api/v1/public/download/" + String.valueOf(msds.get("rev_bf_en_atfl_no")) + "\">" + msds.get("rev_bf_en_atfl_nm") + "</a>" + "<br></p>\r\n"; 
	}
	 content += "                </td>\r\n" +			
	 			"                <td style=\"width: 141px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000210") + "<br></p>\r\n" + 
				"                </td>\r\n" +
	 			"                <td style=\"width: 284px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n";
	if((!"".equals(msds.get("rev_af_en_atfl_no")) && null != (msds.get("rev_af_en_atfl_no"))) || (!"".equals(msds.get("rev_af_en_atfl_nm")) && null != (msds.get("rev_af_en_atfl_nm")))) {
	 content += "                    <p>" + "<a href=\"" + serverUrl + "/api/v1/public/download/" + String.valueOf(msds.get("rev_af_en_atfl_no")) + "\">" + msds.get("rev_af_en_atfl_nm") + "</a>" + "<br></p>\r\n"; 
	}
	content +=  "                </td>\r\n" +
				"            </tr>\r\n" + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000211") + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 20%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000025") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 80%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + msds.get("mtrl_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000173") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 80%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + alert.get("sgw_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 20%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000172") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 80%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				subTag + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000193") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 80%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + alert.get("hars_cntn").replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td rowspan=\"4\" style=\"width: 13%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000192") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 7%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000191") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"margin: 3px 3px 3px 3px; width: 80%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + alert.get("prv_cntn").replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000190") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"margin: 3px 3px 3px 3px; width: 80%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + alert.get("act_cntn").replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000045") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"margin: 3px 3px 3px 3px; width: 80%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"					 <p>" + alert.get("str_cntn").replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" +
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000189") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"margin: 3px 3px 3px 3px; width: 80%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"					 <p>" + alert.get("scr_cntn").replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" +
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000130") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + alert.get("vndr_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000186") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 30%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + alert.get("vndr_addr") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000185") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 302px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + alert.get("nat_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 211px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000184") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 212px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + alert.get("vndr_phon_no") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000183") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 302px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + alert.get("vndr_rprs_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 211px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000182") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 212px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + alert.get("vndr_coreg_no") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 
				"</div>";
		// 메일 수신자(검토 요청자)
		String[] to = {msds.get("req_usr_id")};
		// 메일 송신
		Boolean mailResult = util.sendMail(bizCd, msds.get("usr_id"), subject, content, to);
		
		return mailResult; 
	}
	
	@Transactional
	@SuppressWarnings("unchecked")
	public boolean sendMsdsRjtMail(Map<String, Object> param, HttpSession session) {
		// 데이터 초기화
		Map<String, String> msds = (Map<String, String>)param.get("msds");
		Map<String, String> alert = (Map<String, String>)param.get("alert");
		List<Map<String, String>> picList = (List<Map<String, String>>)param.get("pic");
		
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		String langCd = userInfo.getLangCd();
		param.put("SESS_LANG", langCd);
		
		// 다국어 처리
		String[] mlangNo = {"MS00000059", "LB00000151", "LB00000150",
				"LB00000149", "LB00000199", "LB00000025",
				"LB00000200", "LB00000201", "LB00000202",
				"LB00000203", "LB00000204", "LB00000205",
				"LB00000206", "LB00000207", "LB00000208",
				"LB00000209", "LB00000210", "LB00000211",
				"LB00000173", "LB00000172", "LB00000193",
				"LB00000192", "LB00000191", "LB00000190",
				"LB00000045", "LB00000189", "LB00000130",
				"LB00000186", "LB00000185", "LB00000184",
				"LB00000183", "LB00000182", "LB00000281",
				"LB00000279", "LB00000280", "MS00000156",
				"LB00000357"};
		List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo), langCd);
		
		// 그림문자 html 태그
		String subTag = "";
		for(int i=0; i<picList.size(); i++) {
			if(picList.get(i).get("picg_cd").equalsIgnoreCase("1")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P01.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").equalsIgnoreCase("2")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P02.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").equalsIgnoreCase("3")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P03.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").equalsIgnoreCase("4")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P04.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").equalsIgnoreCase("5")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P05.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").equalsIgnoreCase("6")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P06.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").equalsIgnoreCase("7")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P07.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").equalsIgnoreCase("8")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P08.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").equalsIgnoreCase("9")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P09.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			}
		}
		Calendar cal = Calendar.getInstance();
		// 메일 업무 코드
		String bizCd = "12";
		// 메일 제목
		String subject = mUtil.getMLang(mlangCntn, "MS00000156") + "(" + msds.get("mtrl_no") + " : " + msds.get("mtrl_nm") + ")";
		// 메일 내용
		String content = "<div>\r\n" + 
				"    <p>\r\n" + 
				"        <spam style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang("LB00000357", langCd) + " :&nbsp;</spam><a href=\"" + frontUrl + "/msds/msdsrevreqedt/" + msds.get("msds_rev_no") + "\" style=\"font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + frontUrl + "/msds/msdsrevreqedt/" + msds.get("msds_rev_no") + "</a>\r\n" + 
				"    </p>\r\n" + 
				"<p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>" +
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang("CD00000801", langCd) + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000151") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + msds.get("msds_rev_no") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000150") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + msds.get("req_usr_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000149") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + msds.get("req_da") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000199") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + msds.get("mtrl_no") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000025") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + msds.get("mtrl_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000200") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + msds.get("rev_wkpl_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 
				"<p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>" +
				"<p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000281") + "</p>\r\n" + 
				"<div><span style=\"font-size: 12pt;\">\r\n" + 
				"        <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border:none;border-collapse:collapse;word-break:break-all;font-family:&quot;Malgun Gothic&quot;, &quot;맑은 고딕&quot;;font-size:13.3333px\">\r\n" + 
				"            <tbody>\r\n" + 
				"                <tr>\r\n" + 
				"                    <td style=\"width: 20%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                        <p><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000279") + "</span></p>\r\n" + 
				"                    </td>\r\n" + 
				"                    <td style=\"width: 30%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                        <p>" + userInfo.getUsrNm() + "<br></p>\r\n" + 
				"                    </td>\r\n" + 
				"                    <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                        <p><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000280") + "</span></p>\r\n" + 
				"                    </td>\r\n" + 
				"                    <td style=\"width: 30%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                        <p>" + cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DAY_OF_MONTH) + "<br></p>\r\n" + 
				"                    </td>\r\n" + 
				"                </tr>\r\n" + 
				"                <tr>\r\n" + 
				"                    <td style=\"width: 20%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                        <p><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000279") + "</span></p>\r\n" + 
				"                    </td>\r\n" + 
				"                    <td style=\"width: 80%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"3\">\r\n" + 
				"                        <p>" + msds.get("rev_chk_rejc_cntn") + "<br></p>\r\n" + 
				"                    </td>\r\n" + 
				"                </tr>\r\n" + 
				"            </tbody>\r\n" + 
				"        </table>\r\n" + 
				"    </span>\r\n" + 
				"</div>" +
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">MSDS</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 20%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000201") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 30%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + mUtil.getMLang(mlangCntn, "LB00000202") + " : " + msds.get("rev_bf_enact_da") + "</p>\r\n" + 
				"                    <p>" + mUtil.getMLang(mlangCntn, "LB00000203") + " : " + msds.get("rev_bf_last_rev_da") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\"><span style=\"font-size: 13.3333px;\">" + mUtil.getMLang(mlangCntn, "LB00000204") + "</span><br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 30%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + mUtil.getMLang(mlangCntn, "LB00000202") + " : " + msds.get("rev_af_enact_da") + "</p>\r\n" + 
				"                    <p>" + mUtil.getMLang(mlangCntn, "LB00000203") + " : " + msds.get("rev_af_last_rev_da") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 137px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000205") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 284px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + msds.get("rev_bf_ver") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 141px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000206") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 284px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + msds.get("rev_af_ver") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 137px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000207") + "</p>\r\n" + 
				"                </td>\r\n" +
	 			"                <td style=\"width: 284px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n"; 
	if((!"".equals(msds.get("rev_bf_ko_atfl_no")) && null != (msds.get("rev_bf_ko_atfl_no"))) || (!"".equals(msds.get("rev_bf_ko_atfl_nm")) && null != (msds.get("rev_bf_ko_atfl_nm")))) {
	 content += "                    <p>" + "<a href=\"" + serverUrl + "/api/v1/public/download/" + String.valueOf(msds.get("rev_bf_ko_atfl_no")) + "\">" + msds.get("rev_bf_ko_atfl_nm") + "</a>" + "<br></p>\r\n"; 
	}
	 content += "                </td>\r\n" +
			 	"                <td style=\"width: 141px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000208") + "<br></p>\r\n" + 
				"                </td>\r\n" +
				"                <td style=\"width: 284px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n"; 
	if((!"".equals(msds.get("rev_af_ko_atfl_no")) && null != (msds.get("rev_af_ko_atfl_no"))) || (!"".equals(msds.get("rev_af_ko_atfl_nm")) && null != (msds.get("rev_af_ko_atfl_nm")))) {
	 content += "                    <p>" + "<a href=\"" + serverUrl + "/api/v1/public/download/" + String.valueOf(msds.get("rev_af_ko_atfl_no")) + "\">" + msds.get("rev_af_ko_atfl_nm") + "</a>" + "<br></p>\r\n"; 
	}
	 content += "                </td>\r\n" +
	 			"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 137px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000209") + "</p>\r\n" + 
				"                </td>\r\n" +
				"                <td style=\"width: 284px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n"; 
	if((!"".equals(msds.get("rev_bf_en_atfl_no")) && null != (msds.get("rev_bf_en_atfl_no"))) || (!"".equals(msds.get("rev_bf_en_atfl_nm")) && null != (msds.get("rev_bf_en_atfl_nm")))) {	
	 content += "                    <p>" + "<a href=\"" + serverUrl + "/api/v1/public/download/" + String.valueOf(msds.get("rev_bf_en_atfl_no")) + "\">" + msds.get("rev_bf_en_atfl_nm") + "</a>" + "<br></p>\r\n"; 
	}
	 content += "                </td>\r\n" +
			 	"                <td style=\"width: 141px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000210") + "<br></p>\r\n" + 
				"                </td>\r\n" +
				"                <td style=\"width: 284px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n"; 
    if((!"".equals(msds.get("rev_af_en_atfl_no")) && null != (msds.get("rev_af_en_atfl_no"))) || (!"".equals(msds.get("rev_af_en_atfl_nm")) && null != (msds.get("rev_af_en_atfl_nm")))) {
     content += "                    <p>" + "<a href=\"" + serverUrl + "/api/v1/public/download/" + String.valueOf(msds.get("rev_af_en_atfl_no")) + "\">" + msds.get("rev_af_en_atfl_nm") + "</a>" + "<br></p>\r\n"; 
    }
	content += "                </td>\r\n" +
				"            </tr>\r\n" + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000211") + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 20%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000025") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 80%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + msds.get("mtrl_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000173") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 80%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + alert.get("sgw_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 20%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000172") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 80%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				subTag + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000193") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 80%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + alert.get("hars_cntn").replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td rowspan=\"4\" style=\"width: 13%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000192") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 7%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000191") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"margin: 3px 3px 3px 3px; width: 80%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + alert.get("prv_cntn").replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000190") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"margin: 3px 3px 3px 3px; width: 80%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + alert.get("act_cntn").replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000045") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"margin: 3px 3px 3px 3px; width: 80%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"					 <p>" + alert.get("str_cntn").replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" +
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000189") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"margin: 3px 3px 3px 3px; width: 80%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"					 <p>" + alert.get("scr_cntn").replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" +
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000130") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + alert.get("vndr_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000186") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 30%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + alert.get("vndr_addr") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000185") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 302px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + alert.get("nat_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 211px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000184") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 212px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + alert.get("vndr_phon_no") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000183") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 302px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + alert.get("vndr_rprs_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 211px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000182") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 212px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + alert.get("vndr_coreg_no") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 
				"</div>";
		// 메일 수신자(검토 요청자)
		String[] to = {msds.get("req_usr_id")};
		// 메일 송신
		Boolean mailResult = util.sendMail(bizCd, msds.get("usr_id"), subject, content, to);
		
		return mailResult; 
	}

	@PostMapping("/msds-multi-file-down")
	public ResponseEntity<InputStreamResource> msdsMultiFileDownload(HttpServletResponse response, @RequestBody Map<String, Object> param, HttpSession session) throws Exception {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		
		///// ZIP파일 생성 START
		String zipFilePath = appTempDir;		// ZIP파일 임시 저장 경로
		//String zipFileName = UUID.randomUUID().toString().replace("-", "");	// ZIP 압축 파일명
		String zipFileName = "01.zip";	// ZIP 압축 파일명
		String restrictChars = "|\\\\?*<\":>/";		// 파일명에 쓸수 없는 특수문자
	    String regExpr = "[" + restrictChars + "]+";	// 파일명에 쓸수 없는 특수문자 정규식
	    
	    // ZIP파일이 write될 임시 폴더
		File zipFilePathDir = new File(zipFilePath);
		
		logger.debug("==> zipFilePathDir : " + zipFilePathDir.getPath());
		logger.debug("==> zipFilePathDir.exists() : " + zipFilePathDir.exists());
		if (!zipFilePathDir.exists()) {
			boolean suc = zipFilePathDir.mkdir();
			logger.info("==> zipFilePathDir.mkdir() : " + suc);
		}
		
		ZipOutputStream zout = new ZipOutputStream(new FileOutputStream(zipFilePath + zipFileName));

		// 화면에서 넘어온 MSDS목록
		List<Map<String, String>> msdsList = (List<Map<String, String>>)param.get("msdsList");
		
		for (int i = 0; i < msdsList.size(); i++) {
			Map<String, String> msdsInfo = new HashMap<>();
			msdsInfo = msdsList.get(i);
			logger.debug("==> msdsInfo : " + msdsInfo);
			
			// 기본 파일명 : 자재번호 + 자재명
			String msdsBaseFileNm = msdsInfo.get("mtrl_no") + "_" + msdsInfo.get("mtrl_nm");
			// 파일명에 사용할 수 없는 특수문자 제거
			msdsBaseFileNm = msdsBaseFileNm.replaceAll(regExpr, "");
			logger.debug("==> msdsBaseFileNm : " + msdsBaseFileNm);
			
			String koFileKey = msdsInfo.get("ko_atfl_key");
			// KO 파일이 있는지 체크
			if (koFileKey != null && !koFileKey.isEmpty()) {
				// File data 가져오기
				Map<String, Object> koFileInfo = fileService.toByteArrayFromUrlFile(koFileKey);
				
				// 한글 파일명 정의
				String msdsKoFileNm = msdsBaseFileNm + "_KO." + koFileInfo.get("fileExt");
				zout.putNextEntry(new ZipEntry(msdsKoFileNm));	//압축파일에 저장될 파일명
				zout.write((byte[]) koFileInfo.get("fileContent"));	// 파일 write
				
				// Zip 파일 내 파일 close
				zout.closeEntry();
				zout.flush();
			}

			String enFileKey = msdsInfo.get("en_atfl_key");
			// EN 파일이 있는지 체크
			if (enFileKey != null && !enFileKey.isEmpty()) {
				// File data 가져오기
				Map<String, Object> enFileInfo = fileService.toByteArrayFromUrlFile(enFileKey);

				// 영문 파일명 정의
				String msdsEnFileNm = msdsBaseFileNm + "_EN." + enFileInfo.get("fileExt");
				zout.putNextEntry(new ZipEntry(msdsEnFileNm));	//압축파일에 저장될 파일명
				zout.write((byte[]) enFileInfo.get("fileContent"));	// 파일 write
				
				// Zip 파일 내 파일 close
				zout.closeEntry();
				zout.flush();
			}
		}

		// Zip 파일 close
		zout.close();

		if (zout != null) {
			zout = null;
		}
		///// ZIP파일 생성 END
		
		///// ZIP파일 전송 START
		// reponse header set
		HttpHeaders header = new HttpHeaders();
		header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + zipFileName);
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");

		// ZIP파일 객체 전환
		File zipFile = new File(zipFilePath + zipFileName);
		InputStreamResource resource3 = new InputStreamResource(new FileInputStream(zipFile));

		/*
		// 읽고난 후 삭제
		logger.info("zipFilePathDir exists ==> " + zipFilePathDir.exists());
		logger.info("zipFile.getPath() ==> " + zipFile.getPath());
		logger.info("zipFile.delete() ==> " + zipFile.delete());
		logger.info("zipFilePathDir.delete() ==> " + zipFilePathDir.delete());
		zipFile.delete();
		zipFilePathDir.delete();
		*/
		
		return ResponseEntity
				.ok()
				.headers(header)
				.contentLength(zipFile.length())
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(resource3);
		///// ZIP파일 전송 END
	}
}
