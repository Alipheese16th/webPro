package com.kist.portal.sh.cstprms.rest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.kist.portal.sh.cstprms.service.CstprmsService;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/she/cstprms")
public class CstprmsController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private CstprmsService service;
	
	@Value("${dgrm.front-url}")
	private String frontUrl;
	
	@Value("${dgrm.server-url}")
	private String serverUrl;
	
	@Autowired
	private SendEmailUtil util;
	
	@Autowired
	private MultiLanguageUtil mUtil;
	
	@Autowired
	private AprvService aprvService;
	
	@Autowired
	private FileService fileService;
	
	// 업체 관리 목록
	@GetMapping("/otsr-con-bsen-list")
	public ResponseEntity<?> selectOtsrConBsenList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		List<Map<String, ?>> list = service.selectOtsrConBsenList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	// 업체 관리 상세 조회
	@GetMapping("/otsr-con-bsen-dtl/{otsrConBsenId}")
	public ResponseEntity<?> selectOtsrConBsenDtl(@PathVariable String otsrConBsenId, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		Map<String, String> param = new HashMap<>();
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		param.put("otsr_con_bsen_id", otsrConBsenId);
		
		// 기본정보
		List<Map<String, ?>> basicInfo = service.selectOtsrConBsenDtl(param);
		// 작업자 정보
		List<Map<String, ?>> oprrList = service.selectOtsrConBsenOprrList(param);
		
		Map<String, List<Map<String, ?>>> data = new HashMap<>();
		data.put("basic", basicInfo);
		data.put("oprr", oprrList);
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	// 업체 관리 사업장 교육 이수 정보 조회
	@GetMapping("/otsr-con-bsen-fcst-dtl")
	public ResponseEntity<?> selectOtsrConBsenWkplFcstList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		
		// 사업장 교육 이수 정보
		List<Map<String, ?>> fcstList = service.selectOtsrConBsenWkplFcstList(param);
		
		Map<String, List<Map<String, ?>>> data = new HashMap<>();
		data.put("fcst", fcstList);
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	// 업체 관리 상세 저장
	@PutMapping("/otsr-con-bsen-dtl")
	@Transactional
	public ResponseEntity<?> insertOtsrConBsen(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date dt = new Date();
		
		String dtT = formatter.format(dt);
		
		Map<String, String> temp = new HashMap<>();
		
		int n = 0;
		int m = 0;
		int j = 0;
		int k = 0;
		int l = 0;
		int o = 0;
		
		// 기본정보
		List<Map<String, String>> infoRow = (List<Map<String, String>>)param.get("infoRow");
		
		// 작업자 정보
		//insert, update, delete 데이터 초기화
		List<Map<String, String>> insertOprrList = (List<Map<String, String>>)param.get("insertOprrRow");
		List<Map<String, String>> updateOprrList = (List<Map<String, String>>)param.get("updateOprrRow");
		List<Map<String, String>> deleteOprrList = (List<Map<String, String>>)param.get("deleteOprrRow");
		
		// 사업장 교육 이수 정보
		//insert, update, delete 데이터 초기화
		List<Map<String, String>> insertFcstList = (List<Map<String, String>>)param.get("insertFcstRow");
		List<Map<String, String>> updateFcstList = (List<Map<String, String>>)param.get("updateFcstRow");
		List<Map<String, String>> deleteFcstList = (List<Map<String, String>>)param.get("deleteFcstRow");
		
		Map<String, String> tempInfo = infoRow.get(0);
		String passYn = "Y";
		if ("Y".equals(tempInfo.get("new_yn"))) {
			passYn = service.selectOtsrConBsenKeyPass(tempInfo);
		}
		
		// 중복된 키가 있으면 패스
		if ("N".equals(passYn)) {
			return new ResponseEntity<>("OverlapFail", HttpStatus.OK);
		}
		
		// 키 채번
		String key = "";
		if (!"".equals(tempInfo.get("otsr_con_bsen_id")) && null != tempInfo.get("otsr_con_bsen_id")) {
			key = tempInfo.get("otsr_con_bsen_id");
		} else {
			key = service.selectOtsrConBsenkey(temp);
		}
		
		// 기본정보 저장
		tempInfo.put("otsr_con_bsen_id", key);
		tempInfo.put("username", userInfo.getUsrId());
		m = service.insertOtsrConBsen(tempInfo);
		
		// 작업자정보
		if (insertOprrList != null) {
			// 인서트
			for(int i = 0; i < insertOprrList.size(); i++) {
				Map<String, String> tempList = insertOprrList.get(i);
				tempList.put("otsr_con_bsen_id", key);
				tempList.put("username", userInfo.getUsrId());
				
				n = service.insertOtsrConBsenOprrInfo(tempList);
			}
		}
		
		if (updateOprrList != null) {
			// 업데이트
			for (int i = 0; i < updateOprrList.size(); i++) {
				Map<String, String> tempList = updateOprrList.get(i);
				tempList.put("username", userInfo.getUsrId());
				n = service.insertOtsrConBsenOprrInfo(tempList);
			}
		}
		
		
		// 사업장 교육 이수 정보
		if (insertFcstList != null) {
			// 인서트
			for(int i = 0; i < insertFcstList.size(); i++) {
				Map<String, String> tempList = insertFcstList.get(i);
				tempList.put("username", userInfo.getUsrId());
				if ("".equals(tempList.get("trn_fcst_sn")) || null == tempList.get("trn_fcst_sn")) {
					tempList.put("trn_fcst_sn", "0");
				}
				n = service.insertOtsrConBsenWkplFcstInfo(tempList);
			}
		}
		
		if (updateFcstList != null) {
			// 업데이트
			for (int i = 0; i < updateFcstList.size(); i++) {
				Map<String, String> tempList = updateFcstList.get(i);
				tempList.put("username", userInfo.getUsrId());
				m = service.insertOtsrConBsenWkplFcstInfo(tempList);
			}
		}
		
		if (deleteFcstList != null) {
			// 삭제
			for(int i = 0; i < deleteFcstList.size(); i++) {
				Map<String, String> tempList = deleteFcstList.get(i);
				j = service.deleteOtsrConBsenWkplFcstInfo(tempList);
			}
		}
		
		Map<String, String> resultMap = new HashMap<>();
		
		String result;
		result = (n > -1? (m > -1? (j > -1? (k > -1? (l > -1? (o > -1? Constant.SUCCESS : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL);
		resultMap.put("result", result);
		resultMap.put("key1", tempInfo.get("otsr_con_bsen_id"));
		
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
	
	// 업체 관리 사업장 교육 이수 정보 저장
	@PutMapping("/otsr-con-bsen-fcst-dtl")
	@Transactional
	public ResponseEntity<?> insertOtsrConBsenWkplFcstInfo(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		Map<String, String> temp = new HashMap<>();
		
		int n = 0;
		int m = 0;
		int j = 0;
		
		// 사업장 교육 이수 정보
		//insert, update, delete 데이터 초기화
		List<Map<String, String>> insertFcstList = (List<Map<String, String>>)param.get("insertFcstRow");
		List<Map<String, String>> updateFcstList = (List<Map<String, String>>)param.get("updateFcstRow");
		List<Map<String, String>> deleteFcstList = (List<Map<String, String>>)param.get("deleteFcstRow");
		
		// 사업장 교육 이수 정보
		if (insertFcstList != null) {
			// 인서트
			for(int i = 0; i < insertFcstList.size(); i++) {
				Map<String, String> tempList = insertFcstList.get(i);
				tempList.put("username", userInfo.getUsrId());
				if ("".equals(tempList.get("trn_fcst_sn")) || null == tempList.get("trn_fcst_sn")) {
					tempList.put("trn_fcst_sn", "0");
				}
				n = service.insertOtsrConBsenWkplFcstInfo(tempList);
			}
		}
		
		if (updateFcstList != null) {
			// 업데이트
			for (int i = 0; i < updateFcstList.size(); i++) {
				Map<String, String> tempList = updateFcstList.get(i);
				tempList.put("username", userInfo.getUsrId());
				m = service.insertOtsrConBsenWkplFcstInfo(tempList);
			}
		}
		
		if (deleteFcstList != null) {
			// 삭제
			for(int i = 0; i < deleteFcstList.size(); i++) {
				Map<String, String> tempList = deleteFcstList.get(i);
				j = service.deleteOtsrConBsenWkplFcstInfo(tempList);
			}
		}
		
		Map<String, String> resultMap = new HashMap<>();
		
		String result;
		result = (n > -1? (m > -1? (j > -1? Constant.SUCCESS : Constant.FAIL) : Constant.FAIL) : Constant.FAIL);
		resultMap.put("result", result);
		
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
	
	// 작업허가 목록
	@GetMapping("/con-oprn-prms-list")
	public ResponseEntity<?> selectConOprnPrmsList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		List<Map<String, ?>> list = service.selectConOprnPrmsList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	// 작업허가 상세 조회
	@GetMapping("/con-oprn-prms-dtl/{id}")
	public ResponseEntity<?> selectConOprnPrmsDtl(@PathVariable String id, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		String[] key = id.split("-");
		Map<String, String> param = new HashMap<>();
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		param.put("sess_usr_id", userInfo.getUsrId());
		param.put("con_id", key[0]);
		param.put("con_prms_id", key[1]);
		String result = "";
		// 권한
		List<Map<String, ?>> authData = service.selectConOprnPrmsAuthChk(param);
		
		// 기본정보
		List<Map<String, ?>> basicInfo = new ArrayList();
		// 허가 정보
		List<Map<String, ?>> reqInfo = new ArrayList();
		// 작업 정보
		List<Map<String, ?>> oprnInfo = new ArrayList();
		// 작업자 정보
		List<Map<String, ?>> oprrInfo = new ArrayList();
		
		// 작업완료 정보
		List<Map<String, ?>> cmplInfo = new ArrayList();
		
		// 작업완료 점검 정보
		List<Map<String, ?>> chkInfo = new ArrayList();
		
		// 권한
		List<Map<String, ?>> authInfo = new ArrayList();
		Map<String, String> authM = new HashMap<>();
		
		if (param.get("auth_slct") != null && "Y".equals(param.get("auth_slct"))) {
			if ("Y".equals(authData.get(0).get("auth_chk"))) {
				basicInfo = service.selectConOprnPrmsDtl(param);
				reqInfo = service.selectConOprnPrmsReqDtl(param);
				oprnInfo = service.selectConOprnPrmsOprn(param);
				oprrInfo = service.selectConOprnPrmsOprrSave(param);
				cmplInfo = service.selectConOprnPrmsCmplData(param);
				chkInfo = service.selectConOprnPrmsCmplChk(param);
				
				result = "Y";
			} else {
				result = "N";
			}
		} else {
			basicInfo = service.selectConOprnPrmsDtl(param);
			reqInfo = service.selectConOprnPrmsReqDtl(param);
			oprnInfo = service.selectConOprnPrmsOprn(param);
			oprrInfo = service.selectConOprnPrmsOprrSave(param);
			cmplInfo = service.selectConOprnPrmsCmplData(param);
			chkInfo = service.selectConOprnPrmsCmplChk(param);
			
			result = "Y";
		}
		authM.put("auth", result);
		authInfo.add(0, authM);
		
		Map<String, List<Map<String, ?>>> data = new HashMap<>();
		data.put("auth", authInfo);
		data.put("basic", basicInfo);
		data.put("req", reqInfo);
		data.put("oprn", oprnInfo);
		data.put("oprr", oprrInfo);
		data.put("cmpl", cmplInfo);
		data.put("chkl", chkInfo);
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	// 작업허가 작업준수사항 조회
	@GetMapping("/con-oprn-prms-dtl-obey")
	public ResponseEntity<?> selectConOprnPrmsObey(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		
		// 기본정보
		List<Map<String, ?>> obey = service.selectConOprnPrmsObey(param);
		
		Map<String, List<Map<String, ?>>> data = new HashMap<>();
		data.put("obey", obey);
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	// 작업허가 작업준수사항 조회(결재 후)
	@GetMapping("/con-oprn-prms-dtl-obey-cmpl")
	public ResponseEntity<?> selectConOprnPrmsObeyCmpl(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		
		// 기본정보
		List<Map<String, ?>> obey = service.selectConOprnPrmsObeyCmpl(param);
		
		Map<String, List<Map<String, ?>>> data = new HashMap<>();
		data.put("obey", obey);
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	// 작업허가 작원인원 조회
	@GetMapping("/con-oprn-prms-dtl-oprr")
	public ResponseEntity<?> selectConOprnPrmsOprr(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		
		// 기본정보
		List<Map<String, ?>> data = service.selectConOprnPrmsOprr(param);
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	// 작업허가정보 작업일시 주말/휴일 체크
	@GetMapping("/chkDt")
	public ResponseEntity<?> selectHolidayChk(@RequestParam Map<String, String> param, HttpSession session) {
		Map<String, String> holchk = service.selectHolidayChk(param);
		return new ResponseEntity<>(holchk, HttpStatus.OK);
	}
	
	// 작업허가 허가서 출력
	@PutMapping("/con-oprn-prms-dtl-print")
	@Transactional
	public ResponseEntity<?> updateConOprnPrmsReqPrint(@RequestBody Map<String, Object> param) {
		// UserInfo userInfo = sessionManager.get(session.getId());
		
		Map<String, String> temp = new HashMap<>();
		
		int n = 0;
		
		// 기본정보 저장
		temp.put("con_prms_id", param.get("con_prms_id").toString());
		temp.put("username", param.get("usr_id").toString());
		n = service.updateConOprnPrmsReqPrint(temp);
		
		Map<String, String> resultMap = new HashMap<>();
		
		String result;
		result = (n > -1? Constant.SUCCESS : Constant.FAIL);
		resultMap.put("result", result);
		
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
	
	// 공사작업허가 삭제
	@DeleteMapping("/con-oprn-prms-dtl/{id}")
	@Transactional
	public ResponseEntity<?> updateConOprnPrmsReqDel(@PathVariable String id, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		Map<String, String> param = new HashMap<>();
		param.put("con_prms_id", id);
		param.put("username", userInfo.getUsrId());
		
		int n = 0;
		n = service.updateConOprnPrmsReqDel(param);
		
		String result;
		result = (n > -1? Constant.SUCCESS : Constant.FAIL);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// 작업허가 상세 저장
	@PutMapping("/con-oprn-prms-dtl")
	@Transactional
	public ResponseEntity<?> insertConOprnPrmsDtl(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		Map<String, String> temp = new HashMap<>();
		
		int n = 0;
		int m = 0;
		int j = 0;
		int k = 0;
		int l = 0;
		int o = 0;
		
		// 기본정보
		List<Map<String, String>> infoRow = (List<Map<String, String>>)param.get("infoRow");
		
		// 허가정보
		List<Map<String, String>> reqRow = (List<Map<String, String>>)param.get("reqRow");
		
		// 작업정보
		List<Map<String, String>> oprnRow = (List<Map<String, String>>)param.get("oprnRow");
		
		// 준수사항 정보
		List<Map<String, String>> obeyList = (List<Map<String, String>>)param.get("obeyList");
		
		// 작업자 정보
		List<Map<String, String>> oprrList = (List<Map<String, String>>)param.get("oprrList");
		
		Map<String, String> tempInfo = infoRow.get(0);
		Map<String, String> tempReqInfo = reqRow.get(0);
		// 키 채번
		String key = "";
		if (!"".equals(tempInfo.get("con_id")) && null != tempInfo.get("con_id")) {
			key = tempInfo.get("con_id");
		} else {
			key = service.selectConOprnPrmsKey(temp);
		}
		String reqKey = "";
		if (!"".equals(tempReqInfo.get("con_prms_id")) && null != tempReqInfo.get("con_prms_id")) {
			reqKey = tempReqInfo.get("con_prms_id");
			service.deleteConOprnPrmsOprn(tempReqInfo);	// 작업정보 삭제
			service.deleteConOprnPrmsObey(tempReqInfo);	// 작업준수사항 삭제
			service.deleteConOprnPrmsOprr(tempReqInfo);	// 작업인원 삭제
		} else {
			reqKey = service.selectConOprnPrmsReqKey(temp);
		}
		
		// 기본정보 저장
		tempInfo.put("con_id", key);
		tempInfo.put("username", userInfo.getUsrId());
		n = service.insertConOprnPrmsDtl(tempInfo);
		
		// 요청정보 저장
		tempReqInfo.put("con_id", key);
		tempReqInfo.put("con_prms_id", reqKey);
		tempReqInfo.put("username", userInfo.getUsrId());
		m = service.insertConOprnPrmsReqDtl(tempReqInfo);
		
		// 작업 정보
		if (oprnRow != null) {
			// 인서트
			for(int i = 0; i < oprnRow.size(); i++) {
				Map<String, String> tempList = oprnRow.get(i);
				tempList.put("con_prms_id", reqKey);
				tempList.put("username", userInfo.getUsrId());
				
				j = service.insertConOprnPrmsOprn(tempList);
			}
		}
		
		// 준수사항 정보
		if (obeyList != null) {
			// 인서트
			for(int i = 0; i < obeyList.size(); i++) {
				Map<String, String> tempList = obeyList.get(i);
				tempList.put("con_prms_id", reqKey);
				tempList.put("username", userInfo.getUsrId());
				
				k = service.insertConOprnPrmsObey(tempList);
			}
		}
		
		// 작업자정보
		if (oprrList != null) {
			// 인서트
			for(int i = 0; i < oprrList.size(); i++) {
				Map<String, String> tempList = oprrList.get(i);
				tempList.put("con_prms_id", reqKey);
				tempList.put("username", userInfo.getUsrId());
				
				l = service.insertConOprnPrmsOprr(tempList);
			}
		}
		
		Map<String, String> resultMap = new HashMap<>();
		
		String result;
		result = (n > -1? (m > -1? (j > -1? (k > -1? (l > -1? (o > -1? Constant.SUCCESS : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL);
		resultMap.put("result", result);
		resultMap.put("key1", key);
		resultMap.put("key2", reqKey);
		
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
	
	// 작업 미실시 사유 처리
	@PutMapping("/con-oprn-undo")
	@Transactional
	public ResponseEntity<?> updateConOprnPrmsUnDo (@RequestBody Map<String, String> param, HttpSession session) throws Exception {
		UserInfo userInfo = sessionManager.get(session.getId());
		String result = "";
		String now = param.get("time_zone");
		int n = service.updateConOprnUndo(param);
		
		Map<String, String> temp = new HashMap<>();		
		Boolean mailResult = true;
			temp.put("hour", now.substring(0,2));
			temp.put("min", now.substring(2,4));
			temp.put("chkList", "Y"); 
			temp.put("lang_cd", userInfo.getLangCd());
			temp.put("dept_nm", param.get("dept_nm"));
			temp.put("usr_nm", param.get("usr_nm"));
			temp.put("con_nm", param.get("con_nm"));
			temp.put("con_prms_id", param.get("con_prms_id"));
			temp.put("ntsg_txt", param.get("ntsg_txt"));
			temp.put("SESS_TIME_ZONE", userInfo.getTimeZone());
			
			// 수신처 목록
			String[] to = new String[1];
			// 참조처 목록
			String[] cc = new String[3];
			
			// 수신자
			to[0] = param.get("con_oprn_supe_usr_id"); //작업담당자
			// 참조자
			cc[0] = param.get("con_supe_usr_id"); //관리감독자
			cc[1] = param.get("con_sft_rspn_usr_id"); //안전관리 담당자
			cc[2] = param.get("con_rq_usr_id"); //작업요청자
			
			// 다국어 처리
			String[] mlangNo = {  "LB00000021", "LB00000863","LB00000636"
								, "LB00000876", "LB00000888", "LB00000681"
								, "LB00000878", "LB00000959", "LB00001348"};
			List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo), String.valueOf(userInfo.getLangCd()));
			// 메일 업무 코드(정의 필요)
			String bizCd = "34";
			// 메일 제목(정의 필요)
			String title = "[SHE Portal] " + mUtil.getMLang(mlangCntn, "LB00000876") + "(" + String.valueOf(temp.get("con_prms_id")) + ")" +"작업이 " + "'" + String.valueOf(temp.get("usr_nm")) +"'" +"님의 요청으로 작업 미실시 처리 되었습니다.";
			// 메일 내용(정의 필요)
			String content = "<table width=\"820\" cellspacing=\"0\" cellpadding=\"0\" style=\"border-width: 1px; border-style: solid; border-color: rgb(6,44,94);\">\r\n" + 
					"    <tbody>\r\n" + 
					"        <tr style=\"height: 60px; background: rgb(6, 44, 94);\">\r\n" +
					"				<td>\n" +
					"					<p style=\"font-weight: bold; color:rgb(255,255,255); padding: 0px 30px;\">" +
					" 						<img src=\"" + frontUrl + "/_nuxt/img/logo.d0672ff.png\">&nbsp;&nbsp;\r\n" +
					" 							<span style=\"color: white; font-size: 18px;\">SHE PORTAL 안전작업허가서 미실시 처리 안내</span>\r\n" + "<br></p>\n" +
					"				</td>\n" +
					"				\n" +
					"            </tr>\n" +
					"			<tr>\n" +
					"				<td>\n" +
					"					<p style=\"font-weight: bold; font-size: 14px; padding: 30px 30px;\">SHE Portal 에서 안내 메일 전달 드립니다. " + String.valueOf(temp.get("hour")) + "시" + String.valueOf(temp.get("min")) + "분" + ", " + String.valueOf(temp.get("dept_nm")) + "부서 " + String.valueOf(temp.get("usr_nm")) + "님의 요청으로 작업 미실시 처리됨을 안내 드립니다.<br><br>"
											+ "ㆍ" +mUtil.getMLang(mlangCntn, "LB00000876") +": " + String.valueOf(temp.get("con_prms_id")) +"<br>" + "ㆍ" +mUtil.getMLang(mlangCntn, "LB00000636") + ": " + String.valueOf(temp.get("con_nm")) + "<br>" + "ㆍ" +mUtil.getMLang(mlangCntn, "LB00001348") + ": " + String.valueOf(temp.get("ntsg_txt"))
												+ "<br><br>" + "*'작업 미실시 처리'는 안전작업허가서 결재가 완료된 작업을 정당한 사유로 실시하지 못하게 된 경우에만 사용이 가능 합니다.\n" +
													 "  그 外 목적으로 사용할 경우 발생되는 책임은 요청자 본인에 있음을 알려 드립니다.\n" + "<br><br><br>"
														+ "[시스템 문의] 안전보건시스템 dehs (dehs@donggramisoft.co.kr)"
										+ "</p>\n" +
					"				</td>\n" +
					"				\n" +
					"            </tr>			\n" +
					"            <tr>\n" +
					"                <td style=\"height: 60px;background: rgb(6, 44, 94); \">\n" +
					"				</td>\n" +
					"            </tr>" +
					"</tbody>\r\n" + 
					"</table>";
			
			
			// 메일 송신
			mailResult = util.sendMail(bizCd, "AC928960", title, content, to , cc);		
			
			if(mailResult) {
				// 전송 성공시
				result = "Success";
			} else {
				throw new Exception("Send Mail Error");
			}
			return new ResponseEntity<>(result, HttpStatus.OK);
			
		}
	
	// 작업허가 당일작업 작업인원 조회
	@GetMapping("/con-oprn-prms-curr-oprr")
	public ResponseEntity<?> selectConOprnPrmsOprrCurrent(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		List<Map<String, ?>> list = service.selectConOprnPrmsOprrCurrent(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	// 작업허가 이전작업 불러오기 조회
	@GetMapping("/con-oprn-prms-be-data")
	public ResponseEntity<?> selectConOprnPrmsBeReqData(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		List<Map<String, ?>> list = service.selectConOprnPrmsBeReqData(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	// 작업완료 목록
	@GetMapping("/con-oprn-prms-cmpl-list")
	public ResponseEntity<?> selectConOprnPrmsCmplList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		List<Map<String, ?>> list = service.selectConOprnPrmsCmplList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	// 작업완료 저장
	@PutMapping("/con-oprn-prms-cmpl-dtl")
	@Transactional
	public ResponseEntity<?> insertConOprnPrmsCmplData(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		int n = 0;
		
		// 기본정보
		List<Map<String, String>> infoRow = (List<Map<String, String>>)param.get("cmplRow");
		Map<String, String> temp = infoRow.get(0);
		
		// 기본정보 저장
		temp.put("username", userInfo.getUsrId());
		n = service.insertConOprnPrmsCmplData(temp);
		
		Map<String, String> resultMap = new HashMap<>();
		
		String result;
		result = (n > -1? Constant.SUCCESS : Constant.FAIL);
		resultMap.put("result", result);
		
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
	
	// 작업완료 결재
	@PutMapping("/con-oprn-prms-cmpl-appr")
	@Transactional
	public ResponseEntity<?> updateConOprnPrmsCmplMainStat(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		int n = 0;
		
		// 기본정보
		List<Map<String, String>> infoRow = (List<Map<String, String>>)param.get("cmplRow");
		Map<String, String> temp = infoRow.get(0);
		
		// 기본정보 저장
		temp.put("username", userInfo.getUsrId());
		n = service.updateConOprnPrmsMainStat(temp);
		
		Map<String, String> resultMap = new HashMap<>();
		
		String result;
		result = (n > -1? Constant.SUCCESS : Constant.FAIL);
		resultMap.put("result", result);
		
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
	
	// 작업완료 점검 조회
	@GetMapping("/con-oprn-prms-cmpl-chk")
	public ResponseEntity<?> selectConOprnPrmsCmplChk(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		List<Map<String, ?>> list = service.selectConOprnPrmsCmplChk(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	// 작업완료 점검 상세 조회
	@GetMapping("/con-oprn-prms-cmpl-chk-dtl")
	public ResponseEntity<?> selectConOprnPrmsCmplChkDtl(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		List<Map<String, ?>> list = service.selectConOprnPrmsCmplChkDtl(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	// 작업 메인 모바일 조회
	@GetMapping("/con-oprn-prms-mobile-main")
	public ResponseEntity<?> selectConOprnPrmsMainMobile(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("sess_user_id", userInfo.getUsrId());
		List<Map<String, ?>> list = service.selectConOprnPrmsMainMobile(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	// 점검 목록 모바일
	@GetMapping("/con-oprn-prms-mobile-list")
	public ResponseEntity<?> selectConOprnPrmsMobileList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		List<Map<String, ?>> list = service.selectConOprnPrmsMobileList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	// 작업허가 상세 조회 모바일
	@GetMapping("/con-oprn-prms-mobile-dtl/{id}")
	public ResponseEntity<?> selectConOprnPrmsDtlMobile(@PathVariable String id, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		Map<String, String> param = new HashMap<>();
		
		String[] key = id.split("-");
		param.put("con_prms_id", key[0]);
		
		if (key.length > 1) {
			param.put("wkpl_chec_cl_cd", key[1]);
		} else {
			param.put("wkpl_chec_cl_cd", "");
		}
		
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		param.put("sess_usr_id", userInfo.getUsrId());
		
		List<Map<String, ?>> authChk = service.selectConOprnPrmsMobileAuthChk(param);
		Map<String, ?> authData = authChk.get(0);
		Map<String, String> authM = new HashMap<>();
		String result = "";
		
		// 권한 체크 결과
		List<Map<String, ?>> resultInfo = new ArrayList();
		// 기본정보
		List<Map<String, ?>> basicInfo = new ArrayList();
		// 점검 정보
		List<Map<String, ?>> chkInfo = new ArrayList();
		if ("Y".equals(authData.get("auth_yn")) && "Y".equals(authData.get("dt_yn"))) {
			basicInfo = service.selectConOprnPrmsDtlMobile(param);
			chkInfo = service.selectConOprnPrmsDtlChkListMobile(param);
			result = "Y";
		} else {
			result = "N";
		}
		
		authM.put("auth", result);
		resultInfo.add(0, authM);
		
		Map<String, List<Map<String, ?>>> data = new HashMap<>();
		data.put("basic", basicInfo);
		data.put("chklist", chkInfo);
		data.put("auth", resultInfo);
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	// 작업 모바일 저장
	@PutMapping("/con-oprn-prms-mobile-dtl")
	@Transactional
	public ResponseEntity<?> insertConOprnPrmsChkDataMobile(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		Map<String, String> temp = new HashMap<>();
		
		int n = 0;
		int m = 0;
		int j = 0;
		int k = 0;
		
		// 기본정보
		List<Map<String, String>> infoRow = (List<Map<String, String>>)param.get("infoRow");
		
		// 점검정보
		List<Map<String, String>> chkRow = (List<Map<String, String>>)param.get("chkRow");
		
		Map<String, String> tempInfo = infoRow.get(0);
		
		// 기본정보 저장
		tempInfo.put("username", userInfo.getUsrId());
		n = service.insertConOprnPrmsChkDataMobile(tempInfo);
		
		// 점검 정보
		if (chkRow != null) {
			for(int i = 0; i < chkRow.size(); i++) {
				Map<String, String> tempList = chkRow.get(i);
				tempList.put("username", userInfo.getUsrId());
				
				m = service.insertConOprnPrmsChkListDataMobile(tempList);
			}
		}
		
		// 조치완료일 경우 다음 단계 데이터 생성
		String statCd = tempInfo.get("con_chk_prst_cd");
		String checClCd = tempInfo.get("wkpl_chec_cl_cd");
		String chgStatCd = "40";
		String chgChecClCd = "";
		String mainStatCd = "S08";
		if ("30".equals(statCd)) {	// 조치완료일 경우
			if ("WC02".equals(checClCd)) {	// 작업 전 일때 작업 중 데이터 생성
				chgChecClCd = "WC03";
				tempInfo.put("wkpl_chec_cl_cd", chgChecClCd);
				tempInfo.put("wkpl_chec_sn", "1");
				tempInfo.put("con_chk_prst_cd", chgStatCd);
				j = service.insertConOprnPrmsChkNextDataMobile(tempInfo);
				k = service.insertConOprnPrmsChkListNextDataMobile(tempInfo);
			} else if ("WC03".equals(checClCd)) {	//  작업 중 일때 작업 후 데이터 생성
				chgChecClCd = "WC04";
				tempInfo.put("wkpl_chec_cl_cd", chgChecClCd);
				tempInfo.put("wkpl_chec_sn", "1");
				tempInfo.put("con_chk_prst_cd", chgStatCd);
				j = service.insertConOprnPrmsChkNextDataMobile(tempInfo);
				k = service.insertConOprnPrmsChkListNextDataMobile(tempInfo);
			} 
//			else if ("WC04".equals(checClCd)) {
//				tempInfo.put("con_req_prst_cd", mainStatCd);
//				j = service.updateConOprnPrmsMainStatMobile(tempInfo);
//			}
		}
		
		Map<String, String> resultMap = new HashMap<>();
		
		String result;
		result = (n > -1? (m > -1? (j > -1? (k > -1? Constant.SUCCESS : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL);
		resultMap.put("result", result);
		
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
	
	// 작업허가 결재
	@PutMapping("/con-oprn-prms-dtl-appr")
	@Transactional
	public ResponseEntity<?> updateConOprnPrmsCnfm(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		String usrId = userInfo.getUsrId();
		String langCd = userInfo.getLangCd();
		
		// 다국어 처리
		String[] mlangNo = {
				"LB00000038","LB00000022","LB00000023","LB00000024","LB00000025"
				,"LB00000028","LB00000045","LB00000095","LB00000130","LB00000149"
				,"LB00000150","LB00000172","LB00000173","LB00000176","LB00000177"
				,"LB00000182","LB00000183","LB00000184","LB00000185","LB00000186"
				,"LB00000189","LB00000190","LB00000191","LB00000192","LB00000193"
				,"LB00000195","LB00000196","LB00000202","LB00000203","LB00000212"
				,"LB00000266","LB00000268","LB00000270","LB00000282","LB00000283"
				,"LB00000284","LB00000322","LB00000353","LB00000354","LB00000355"
				,"LB00000394","LB00000395","LB00000397","LB00000400","LB00000401"
				,"LB00000402","LB00000403","LB00000404","LB00000405","LB00000407"
				,"LB00000408","LB00000415","LB00000416","LB00000417","LB00000419"
				,"LB00000420","LB00000506","LB00000514","LB00000538","LB00000528"};
		List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo), langCd);
		
		int n = 0;
		
		List<Map<String, String>> infoRow = (List<Map<String, String>>)param.get("infoRow");
		Map<String, String> schParam = infoRow.get(0);
	
		schParam.put("SESS_LANG", langCd);
		schParam.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		
		// 기본정보
		List<Map<String, ?>> basicInfo = service.selectConOprnPrmsDtl(schParam);
		// 허가정보
		List<Map<String, ?>> reqInfo = service.selectConOprnPrmsReqDtl(schParam);
		// 작업정보
		List<Map<String, ?>> oprnInfo = service.selectConOprnPrmsOprn(schParam);
		// 작업자 정보
		List<Map<String, ?>> oprrInfo = service.selectConOprnPrmsOprrSave(schParam);
				
		Map<String, ?> tempBasic = basicInfo.get(0);
		Map<String, ?> tempReq = reqInfo.get(0);
		Map<String, ?> tempOprn = oprnInfo.get(0);
		
		List<Map<String, ?>> fileList = new ArrayList();
		List<Map<String, ?>> fileReqList = new ArrayList();
		
		if (tempBasic.get("oprn_plan_atfl_no") != null && !"".equals(tempBasic.get("oprn_plan_atfl_no"))) {
			Map<String, String> fileParam = new HashMap<>();
			fileParam.put("id", tempBasic.get("oprn_plan_atfl_no").toString());
			try {
				fileList = fileService.selectFileList(fileParam);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (tempReq.get("con_req_atfl_no") != null && !"".equals(tempReq.get("con_req_atfl_no"))) {
			Map<String, String> fileParam = new HashMap<>();
			fileParam.put("id", tempReq.get("con_req_atfl_no").toString());
			try {
				fileReqList = fileService.selectFileList(fileParam);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// 페이지 구분
		// 1-규제 검토 조회 상세_원료, 2-규제 검토 상세 MRO
		String sub_title = "";
		// 결제 제목 - 화학물질 규제 검토(자재코드 자재명)
		String aprv_ttl = "";
		// 결제 내용
		String aprv_cntn = "";
		// html 태그
		String subTag = "";
				
		String ugncConYn = tempBasic.get("ugnc_con_yn").toString();
		String titleGb = "[계획품의]";
		if ("Y".equals(ugncConYn)) {
			titleGb = "[긴급][계획품의]";
		}
		String conYnRtn = "";
		if (tempBasic.get("wekd_con_yn") != null) {
			if ("Y".equals(tempBasic.get("wekd_con_yn").toString())) {
				conYnRtn = conYnRtn + "주중,";
			}
		}
		if (tempBasic.get("wknd_con_yn") != null) {
			if ("Y".equals(tempBasic.get("wknd_con_yn").toString())) {
				conYnRtn = conYnRtn + "주말,";
			}
		}
		if (tempBasic.get("nght_con_yn") != null) {
			if ("Y".equals(tempBasic.get("nght_con_yn").toString())) {
				conYnRtn = conYnRtn + "야간,";
			}
		}
		conYnRtn = conYnRtn.substring(0, conYnRtn.length()-1);
		
		String reqConYnRtn = "";
		if (tempReq.get("wekd_con_yn") != null) {
			if ("Y".equals(tempReq.get("wekd_con_yn").toString())) {
				reqConYnRtn = reqConYnRtn + "주중,";
			}
		}
		if (tempReq.get("wknd_con_yn") != null) {
			if ("Y".equals(tempReq.get("wknd_con_yn").toString())) {
				reqConYnRtn = reqConYnRtn + "주말,";
			}
		}
		if (tempReq.get("nght_con_yn") != null) {
			if ("Y".equals(tempReq.get("nght_con_yn").toString())) {
				reqConYnRtn = reqConYnRtn + "야간,";
			}
		}
		reqConYnRtn = reqConYnRtn.substring(0, reqConYnRtn.length()-1);
		
		String oprnRtn = "";
		if ("Y".equals(tempOprn.get("h_yn").toString())) {
			oprnRtn = oprnRtn + "화기,";
		}
		if ("Y".equals(tempOprn.get("g_yn").toString())) {
			oprnRtn = oprnRtn + "고소,";
		}
		if ("Y".equals(tempOprn.get("m_yn").toString())) {
			oprnRtn = oprnRtn + "밀폐,";
		}
		if ("Y".equals(tempOprn.get("j_yn").toString())) {
			oprnRtn = oprnRtn + "전기차단,";
		}
		if ("Y".equals(tempOprn.get("u_yn").toString())) {
			oprnRtn = oprnRtn + "유해/위험물질,";
		}
		if ("Y".equals(tempOprn.get("v_yn").toString())) {
			oprnRtn = oprnRtn + "중장비사용,";
		}
		if ("Y".equals(tempOprn.get("n_yn").toString())) {
			oprnRtn = oprnRtn + "일반,";
		}
		oprnRtn = oprnRtn.substring(0, oprnRtn.length()-1);
		
		// 대상 html 태그
		for(int i=0; i<oprrInfo.size(); i++) {
			subTag += "            <tr>\r\n" + 
					  "                <td style=\"width: 5%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					  "                    <p>" + (i+1) + "<br></p>\r\n" + 
					  "                </td>\r\n" + 
					  "                <td style=\"width: 35%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					  "                    <p>" + oprrInfo.get(i).get("oprr_nm") + "<br></p>\r\n" + 
					  "                </td>\r\n" + 
					  "                <td style=\"width: 40%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					  "                    <p>" + oprrInfo.get(i).get("oprr_id") + "<br></p>\r\n" + 
					  "                </td>\r\n" + 
					  "                <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					  "                    <p>" + oprrInfo.get(i).get("oprn_cl_nm") + "<br></p>\r\n" + 
					  "                </td>\r\n" + 
					  "            </tr>\r\n";
		}
		// 대상 html 태그
		String subFile = "";
		if (fileList.size() > 0) {
			for(int i=0; i<fileList.size(); i++) {
				subFile += "            <tr>\r\n" + 
						  "                <td style=\"width: 5%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
						  "                    <p>" + (i+1) + "<br></p>\r\n" + 
						  "                </td>\r\n" + 
						  "                <td style=\"width: 95%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
						  "                    <p>" + "<a href=\"" + serverUrl + "/api/v1/public/downloadSeq/" + fileList.get(i).get("key") + "\">" + fileList.get(i).get("name") + "</a>" + "<br></p>\r\n" + 
						  "                </td>\r\n" + 
						  "            </tr>\r\n";
			}
		}
		// 대상 html 태그
		String subReqFile = "";
		if (fileReqList.size() > 0) {
			for(int i=0; i<fileReqList.size(); i++) {
				subReqFile += "            <tr>\r\n" + 
						  "                <td style=\"width: 5%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
						  "                    <p>" + (i+1) + "<br></p>\r\n" + 
						  "                </td>\r\n" + 
						  "                <td style=\"width: 95%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
						  "                    <p>" + "<a href=\"" + serverUrl + "/api/v1/public/downloadSeq/" + fileReqList.get(i).get("key") + "\">" + fileReqList.get(i).get("name") + "</a>" + "<br></p>\r\n" + 
						  "                </td>\r\n" + 
						  "            </tr>\r\n";
			}
		}
		
		// 결제 제목 - 작업명
		aprv_ttl = titleGb + tempBasic.get("con_nm").toString();
		// 결제 내용
		aprv_cntn = "<div>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "작업계획 기본정보" + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "작업번호" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempBasic.get("con_id") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "사업장" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempBasic.get("wkpl_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "작업 요청자" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempBasic.get("con_rq_usr_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 				
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "작업기간" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"5\" style=\"width: 58%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempBasic.get("con_bgn_dt") + " ~ " + tempBasic.get("con_end_dt") + " " + conYnRtn + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "작업 주관부서" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempBasic.get("con_rspn_org_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "작업명" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"5\" style=\"width: 58%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempBasic.get("con_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "긴급작업 여부" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempBasic.get("ugnc_con_yn") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "작업 업체명" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempBasic.get("otsr_con_bsen_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "업체 작업책임자" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempBasic.get("otsr_con_bsen_pric_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "업체 담당자 전화번호" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempBasic.get("otsr_con_bsen_pric_tphn") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 				
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "작업내용" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"8\" style=\"width: 88%; height: 72px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempBasic.get("con_txt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 				
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 

				
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "작업허가정보" + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "허가번호" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"5\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempReq.get("con_prms_id") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "작업일시" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempReq.get("con_dt") + " " + tempReq.get("con_bgn_hm") + " - " + tempReq.get("con_end_hm") + "  " + reqConYnRtn + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "작업종류" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"8\" style=\"width: 88%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + oprnRtn + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "작업위치" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempReq.get("con_oprn_wkpl_loc_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "상세위치" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempReq.get("con_oprn_dtl_loc_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "PSM대상" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempReq.get("psm_tgt_yn") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "관리감독자" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempReq.get("con_supe_usr_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "작업 담당자" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempReq.get("con_oprn_supe_usr_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "안전관리(담당)자" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempReq.get("con_sft_rspn_usr_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "작업내용" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"8\" style=\"width: 88%; height: 72px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempReq.get("con_oprn_txt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "기타 주의사항" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"8\" style=\"width: 88%; height: 72px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempReq.get("oth_prct_txt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 
				
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "작업인원" + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 5%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 35%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "성명" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 40%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "작업자 번호" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "자격구분" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				subTag + 
				"        </tbody>\r\n" + 
				"    </table>\r\n";
				

				if (fileList.size() > 0) {
					aprv_cntn = aprv_cntn +
							"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
							"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "작업 계획 첨부파일" + "</p>\r\n" + 
							"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
							"        <tbody>\r\n" + 
							"            <tr>\r\n" + 
							"                <td style=\"width: 5%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
							"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
							"                </td>\r\n" + 
							"                <td style=\"width: 95%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
							"                    <p style=\"font-weight: bold;\">" + "파일명" + "<br></p>\r\n" + 
							"                </td>\r\n" + 
							"            </tr>\r\n" +
							subFile + 
							"        </tbody>\r\n" + 
							"    </table>\r\n";
				}
				
				if (fileReqList.size() > 0) {
					aprv_cntn = aprv_cntn +
							"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
							"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "작업 허가 첨부파일" + "</p>\r\n" + 
							"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
							"        <tbody>\r\n" + 
							"            <tr>\r\n" + 
							"                <td style=\"width: 5%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
							"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
							"                </td>\r\n" + 
							"                <td style=\"width: 95%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
							"                    <p style=\"font-weight: bold;\">" + "파일명" + "<br></p>\r\n" + 
							"                </td>\r\n" + 
							"            </tr>\r\n" +
							subReqFile + 
							"        </tbody>\r\n" + 
							"    </table>\r\n";
				}
				aprv_cntn = aprv_cntn +
						"    <p>\r\n" + 
						"        <spam style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "바로가기" + " :&nbsp;</spam><a href=\"" + frontUrl + "/cstprms/cstprmsmgntdtllup" + "/" + tempBasic.get("con_id") + "-" + tempReq.get("con_prms_id") + "\" style=\"font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"target=\"_blank\">" + frontUrl + "/cstprms/cstprmsmgntdtllup" + "/" + tempBasic.get("con_id") + "-" + tempReq.get("con_prms_id") + "</a>\r\n" + 
						"    </p>\r\n" +
				"</div>";
		
		Map<String, Object> aprvParam = new HashMap<>();
//			결재 키 
		String aprv_no = UUID.randomUUID().toString().replace("-", "");
		aprvParam.put("aprv_no", aprv_no);
//			결재 코드
		aprvParam.put("arpv_cd", "30");		
//	 		업무번호
		aprvParam.put("biz_no", tempReq.get("con_prms_id").toString());
//			상신자
		aprvParam.put("aprv_usr_id", usrId);
//			결재 상태 코드 - 결재상신
		aprvParam.put("aprv_stt_cd", "1");		
//			후행메서드	
		aprvParam.put("aprv_mthd_nm", "aprvConOprnPrmsCnfm");
//			제목
		aprvParam.put("aprv_ttl", aprv_ttl);
//			내용
		aprvParam.put("aprv_cntn", aprv_cntn);		
//			결재 비고	
		aprvParam.put("aprv_note", "");
//			사원 번호	
		aprvParam.put("SESS_USR_ID", usrId);
//	 		결제 정보 table insert 
		n = aprvService.insertAprv(aprvParam);		
		
		Map<String, Object> data = new HashMap<>();
		if(n > -1) {	
			schParam.put("con_req_aprv_no", aprv_no);
			n = service.updateConOprnPrmsAprv(schParam);
			
			data.put("Success", "Success");
			data.put("aprvNo", aprv_no);
			data.put("usrId", usrId);
		}
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	// 작업완료 결재
	@PutMapping("/con-oprn-prms-cmpl-dtl-appr")
	@Transactional
	public ResponseEntity<?> updateConOprnPrmsCmplCnfm(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		String usrId = userInfo.getUsrId();
		String langCd = userInfo.getLangCd();
		
		// 다국어 처리
		String[] mlangNo = {
				"LB00000038","LB00000022","LB00000023","LB00000024","LB00000025"
				,"LB00000028","LB00000045","LB00000095","LB00000130","LB00000149"
				,"LB00000150","LB00000172","LB00000173","LB00000176","LB00000177"
				,"LB00000182","LB00000183","LB00000184","LB00000185","LB00000186"
				,"LB00000189","LB00000190","LB00000191","LB00000192","LB00000193"
				,"LB00000195","LB00000196","LB00000202","LB00000203","LB00000212"
				,"LB00000266","LB00000268","LB00000270","LB00000282","LB00000283"
				,"LB00000284","LB00000322","LB00000353","LB00000354","LB00000355"
				,"LB00000394","LB00000395","LB00000397","LB00000400","LB00000401"
				,"LB00000402","LB00000403","LB00000404","LB00000405","LB00000407"
				,"LB00000408","LB00000415","LB00000416","LB00000417","LB00000419"
				,"LB00000420","LB00000506","LB00000514","LB00000538","LB00000528"};
		List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo), langCd);
		
		int n = 0;
		
		List<Map<String, String>> infoRow = (List<Map<String, String>>)param.get("infoRow");
		Map<String, String> schParam = infoRow.get(0);
	
		schParam.put("SESS_LANG", langCd);
		schParam.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		
		// 기본정보
		List<Map<String, ?>> basicInfo = service.selectConOprnPrmsDtl(schParam);
		// 허가정보
		List<Map<String, ?>> reqInfo = service.selectConOprnPrmsReqDtl(schParam);
		// 작업정보
		List<Map<String, ?>> oprnInfo = service.selectConOprnPrmsOprn(schParam);
		// 작업자 정보
		List<Map<String, ?>> oprrInfo = service.selectConOprnPrmsOprrSave(schParam);
		// 작업완료 점검 정보
		List<Map<String, ?>> chkInfo = service.selectConOprnPrmsCmplChk(schParam);
		
		Map<String, ?> tempBasic = basicInfo.get(0);
		Map<String, ?> tempReq = reqInfo.get(0);
		Map<String, ?> tempOprn = oprnInfo.get(0);
		Map<String, ?> tempChk = chkInfo.get(0);
		
		List<Map<String, ?>> fileList = new ArrayList();
		List<Map<String, ?>> fileReqList = new ArrayList();
		List<Map<String, ?>> fileChkList = new ArrayList();
		
		if (tempBasic.get("oprn_plan_atfl_no") != null && !"".equals(tempBasic.get("oprn_plan_atfl_no"))) {
			Map<String, String> fileParam = new HashMap<>();
			fileParam.put("id", tempBasic.get("oprn_plan_atfl_no").toString());
			try {
				fileList = fileService.selectFileList(fileParam);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (tempReq.get("con_req_atfl_no") != null && !"".equals(tempReq.get("con_req_atfl_no"))) {
			Map<String, String> fileParam = new HashMap<>();
			fileParam.put("id", tempReq.get("con_req_atfl_no").toString());
			try {
				fileReqList = fileService.selectFileList(fileParam);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (tempChk.get("con_chk_atfl_no") != null && !"".equals(tempChk.get("con_chk_atfl_no"))) {
			Map<String, String> fileParam = new HashMap<>();
			fileParam.put("id", tempChk.get("con_chk_atfl_no").toString());
			try {
				fileChkList = fileService.selectFileList(fileParam);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// 페이지 구분
		// 1-규제 검토 조회 상세_원료, 2-규제 검토 상세 MRO
		String sub_title = "";
		// 결제 제목 - 화학물질 규제 검토(자재코드 자재명)
		String aprv_ttl = "";
		// 결제 내용
		String aprv_cntn = "";
		// html 태그
		String subTag = "";
				
		String ugncConYn = tempBasic.get("ugnc_con_yn").toString();
		String titleGb = "[작업종료 품의]";
		if ("Y".equals(ugncConYn)) {
			titleGb = "[긴급][작업종료 품의]";
		}
		String conYnRtn = "";
		if (tempBasic.get("wekd_con_yn") != null) {
			if ("Y".equals(tempBasic.get("wekd_con_yn").toString())) {
				conYnRtn = conYnRtn + "주중,";
			}
		}
		if (tempBasic.get("wknd_con_yn") != null) {
			if ("Y".equals(tempBasic.get("wknd_con_yn").toString())) {
				conYnRtn = conYnRtn + "주말,";
			}
		}
		if (tempBasic.get("nght_con_yn") != null) {
			if ("Y".equals(tempBasic.get("nght_con_yn").toString())) {
				conYnRtn = conYnRtn + "야간,";
			}
		}
		conYnRtn = conYnRtn.substring(0, conYnRtn.length()-1);
		
		String reqConYnRtn = "";
		if (tempReq.get("wekd_con_yn") != null) {
			if ("Y".equals(tempReq.get("wekd_con_yn").toString())) {
				reqConYnRtn = reqConYnRtn + "주중,";
			}
		}
		if (tempReq.get("wknd_con_yn") != null) {
			if ("Y".equals(tempReq.get("wknd_con_yn").toString())) {
				reqConYnRtn = reqConYnRtn + "주말,";
			}
		}
		if (tempReq.get("nght_con_yn") != null) {
			if ("Y".equals(tempReq.get("nght_con_yn").toString())) {
				reqConYnRtn = reqConYnRtn + "야간,";
			}
		}
		reqConYnRtn = reqConYnRtn.substring(0, reqConYnRtn.length()-1);
		
		String oprnRtn = "";
		if ("Y".equals(tempOprn.get("h_yn").toString())) {
			oprnRtn = oprnRtn + "화기,";
		}
		if ("Y".equals(tempOprn.get("g_yn").toString())) {
			oprnRtn = oprnRtn + "고소,";
		}
		if ("Y".equals(tempOprn.get("m_yn").toString())) {
			oprnRtn = oprnRtn + "밀폐,";
		}
		if ("Y".equals(tempOprn.get("j_yn").toString())) {
			oprnRtn = oprnRtn + "전기차단,";
		}
		if ("Y".equals(tempOprn.get("u_yn").toString())) {
			oprnRtn = oprnRtn + "유해/위험물질,";
		}
		if ("Y".equals(tempOprn.get("v_yn").toString())) {
			oprnRtn = oprnRtn + "중장비사용,";
		}
		if ("Y".equals(tempOprn.get("n_yn").toString())) {
			oprnRtn = oprnRtn + "일반,";
		}
		oprnRtn = oprnRtn.substring(0, oprnRtn.length()-1);
		
		// 대상 html 태그
		for(int i=0; i<oprrInfo.size(); i++) {
			subTag += "            <tr>\r\n" + 
					  "                <td style=\"width: 5%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					  "                    <p>" + (i+1) + "<br></p>\r\n" + 
					  "                </td>\r\n" + 
					  "                <td style=\"width: 35%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					  "                    <p>" + oprrInfo.get(i).get("oprr_nm") + "<br></p>\r\n" + 
					  "                </td>\r\n" + 
					  "                <td style=\"width: 40%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					  "                    <p>" + oprrInfo.get(i).get("oprr_id") + "<br></p>\r\n" + 
					  "                </td>\r\n" + 
					  "                <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					  "                    <p>" + oprrInfo.get(i).get("oprn_cl_nm") + "<br></p>\r\n" + 
					  "                </td>\r\n" + 
					  "            </tr>\r\n";
		}
		
		// 작업계획 첨부파일 html 태그
		String subFile = "";
		if (fileList.size() > 0) {
			for(int i=0; i<fileList.size(); i++) {
				subFile += "            <tr>\r\n" + 
						  "                <td style=\"width: 5%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
						  "                    <p>" + (i+1) + "<br></p>\r\n" + 
						  "                </td>\r\n" + 
						  "                <td style=\"width: 95%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
						  "                    <p>" + "<a href=\"" + serverUrl + "/api/v1/public/downloadSeq/" + fileList.get(i).get("key") + "\">" + fileList.get(i).get("name") + "</a>" + "<br></p>\r\n" + 
						  "                </td>\r\n" + 
						  "            </tr>\r\n";
			}
		}
		// 작업허가 첨부파일 html 태그
		String subReqFile = "";
		if (fileReqList.size() > 0) {
			for(int i=0; i<fileReqList.size(); i++) {
				subReqFile += "            <tr>\r\n" + 
						  "                <td style=\"width: 5%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
						  "                    <p>" + (i+1) + "<br></p>\r\n" + 
						  "                </td>\r\n" + 
						  "                <td style=\"width: 95%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
						  "                    <p>" + "<a href=\"" + serverUrl + "/api/v1/public/downloadSeq/" + fileReqList.get(i).get("key") + "\">" + fileReqList.get(i).get("name") + "</a>" + "<br></p>\r\n" + 
						  "                </td>\r\n" + 
						  "            </tr>\r\n";
			}
		}
		// 작업완료 첨부파일 html 태그
		String subChkFile = "";
		if (fileReqList.size() > 0) {
			for(int i=0; i<fileChkList.size(); i++) {
				subChkFile += "            <tr>\r\n" + 
						  "                <td style=\"width: 5%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
						  "                    <p>" + (i+1) + "<br></p>\r\n" + 
						  "                </td>\r\n" + 
						  "                <td style=\"width: 95%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
						  "                    <p>" + "<a href=\"" + serverUrl + "/api/v1/public/downloadSeq/" + fileChkList.get(i).get("key") + "\">" + fileChkList.get(i).get("name") + "</a>" + "<br></p>\r\n" + 
						  "                </td>\r\n" + 
						  "            </tr>\r\n";
			}
		}
		
		// 결제 제목 - 작업명
		aprv_ttl = titleGb + tempBasic.get("con_nm").toString();
		// 결제 내용
		aprv_cntn = "<div>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "작업계획 기본정보" + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "작업번호" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempBasic.get("con_id") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "사업장" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempBasic.get("wkpl_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "작업 요청자" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempBasic.get("con_rq_usr_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 				
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "작업기간" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"5\" style=\"width: 58%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempBasic.get("con_bgn_dt") + " ~ " + tempBasic.get("con_end_dt") + " " + conYnRtn + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "작업 주관부서" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempBasic.get("con_rspn_org_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "작업명" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"5\" style=\"width: 58%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempBasic.get("con_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "긴급작업 여부" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempBasic.get("ugnc_con_yn") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "작업 업체명" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempBasic.get("otsr_con_bsen_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "업체 작업책임자" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempBasic.get("otsr_con_bsen_pric_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "업체 담당자 전화번호" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempBasic.get("otsr_con_bsen_pric_tphn") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 				
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "작업내용" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"8\" style=\"width: 88%; height: 72px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempBasic.get("con_txt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 				
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 

				
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "작업허가정보" + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "허가번호" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"5\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempReq.get("con_prms_id") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "작업일시" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempReq.get("con_dt") + " " + tempReq.get("con_bgn_hm") + " - " + tempReq.get("con_end_hm") + "  " + reqConYnRtn + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "작업종류" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"8\" style=\"width: 88%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + oprnRtn + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "작업위치" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempReq.get("con_oprn_wkpl_loc_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "상세위치" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempReq.get("con_oprn_dtl_loc_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "PSM대상" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempReq.get("psm_tgt_yn") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "관리감독자" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempReq.get("con_supe_usr_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "작업 담당자" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempReq.get("con_oprn_supe_usr_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "안전관리(담당)자" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempReq.get("con_sft_rspn_usr_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "작업내용" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"8\" style=\"width: 88%; height: 72px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempReq.get("con_oprn_txt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "기타 주의사항" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"8\" style=\"width: 88%; height: 72px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempReq.get("oth_prct_txt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 
				
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "작업인원" + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 5%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 35%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "성명" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 40%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "작업자 번호" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "자격구분" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				subTag + 
				"        </tbody>\r\n" + 
				"    </table>\r\n";
				
				if (fileList.size() > 0) {
					aprv_cntn = aprv_cntn +
							"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
							"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "작업 계획 첨부파일" + "</p>\r\n" + 
							"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
							"        <tbody>\r\n" + 
							"            <tr>\r\n" + 
							"                <td style=\"width: 5%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
							"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
							"                </td>\r\n" + 
							"                <td style=\"width: 95%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
							"                    <p style=\"font-weight: bold;\">" + "파일명" + "<br></p>\r\n" + 
							"                </td>\r\n" + 
							"            </tr>\r\n" +
							subFile + 
							"        </tbody>\r\n" + 
							"    </table>\r\n";
				}
				
				if (fileReqList.size() > 0) {
					aprv_cntn = aprv_cntn +
							"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
							"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "작업 허가 첨부파일" + "</p>\r\n" + 
							"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
							"        <tbody>\r\n" + 
							"            <tr>\r\n" + 
							"                <td style=\"width: 5%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
							"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
							"                </td>\r\n" + 
							"                <td style=\"width: 95%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
							"                    <p style=\"font-weight: bold;\">" + "파일명" + "<br></p>\r\n" + 
							"                </td>\r\n" + 
							"            </tr>\r\n" +
							subReqFile + 
							"        </tbody>\r\n" + 
							"    </table>\r\n";
				}
				
				if (fileChkList.size() > 0) {
					aprv_cntn = aprv_cntn +
							"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
							"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "작업 완료 첨부파일" + "</p>\r\n" + 
							"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
							"        <tbody>\r\n" + 
							"            <tr>\r\n" + 
							"                <td style=\"width: 5%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
							"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
							"                </td>\r\n" + 
							"                <td style=\"width: 95%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
							"                    <p style=\"font-weight: bold;\">" + "파일명" + "<br></p>\r\n" + 
							"                </td>\r\n" + 
							"            </tr>\r\n" +
							subChkFile + 
							"        </tbody>\r\n" + 
							"    </table>\r\n";
				}
				aprv_cntn = aprv_cntn +
						"    <p>\r\n" + 
						"        <spam style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "바로가기" + " :&nbsp;</spam><a href=\"" + frontUrl + "/cstprms/cstprmsmgntdtllup" + "/" + tempBasic.get("con_id") + "-" + tempReq.get("con_prms_id") + "\" style=\"font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"target=\"_blank\">" + frontUrl + "/cstprms/cstprmsmgntdtllup" + "/" + tempBasic.get("con_id") + "-" + tempReq.get("con_prms_id") + "</a>\r\n" + 
						"    </p>\r\n" +
				"</div>";
		
		Map<String, Object> aprvParam = new HashMap<>();
//				결재 키 
		String aprv_no = UUID.randomUUID().toString().replace("-", "");
		aprvParam.put("aprv_no", aprv_no);
//				결재 코드
		aprvParam.put("arpv_cd", "30");		
//		 		업무번호
		aprvParam.put("biz_no", tempReq.get("con_prms_id").toString());
//				상신자
		aprvParam.put("aprv_usr_id", usrId);
//				결재 상태 코드 - 결재상신
		aprvParam.put("aprv_stt_cd", "1");		
//				후행메서드	
		aprvParam.put("aprv_mthd_nm", "aprvConOprnPrmsCmplCnfm");
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
			schParam.put("con_oprn_cmpy_aprv_no", aprv_no);
			n = service.updateConOprnPrmsCmplAprv(schParam);
			
			data.put("Success", "Success");
			data.put("aprvNo", aprv_no);
			data.put("usrId", usrId);
		}
		System.out.println("data :" + data);
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	// 작업완료 멀티결재
	@PutMapping("/con-oprn-prms-cmpl-multi-appr")
	@Transactional
	public ResponseEntity<?> updateConOprnPrmsCmplMultiCnfm(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		String usrId = userInfo.getUsrId();
		String langCd = userInfo.getLangCd();
		
		// 다국어 처리
		String[] mlangNo = {
				"LB00000038","LB00000022","LB00000023","LB00000024","LB00000025"
				,"LB00000028","LB00000045","LB00000095","LB00000130","LB00000149"
				,"LB00000150","LB00000172","LB00000173","LB00000176","LB00000177"
				,"LB00000182","LB00000183","LB00000184","LB00000185","LB00000186"
				,"LB00000189","LB00000190","LB00000191","LB00000192","LB00000193"
				,"LB00000195","LB00000196","LB00000202","LB00000203","LB00000212"
				,"LB00000266","LB00000268","LB00000270","LB00000282","LB00000283"
				,"LB00000284","LB00000322","LB00000353","LB00000354","LB00000355"
				,"LB00000394","LB00000395","LB00000397","LB00000400","LB00000401"
				,"LB00000402","LB00000403","LB00000404","LB00000405","LB00000407"
				,"LB00000408","LB00000415","LB00000416","LB00000417","LB00000419"
				,"LB00000420","LB00000506","LB00000514","LB00000538","LB00000528"};
		List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo), langCd);
		
		int n = 0;
		
		List<Map<String, Object>> infoRow = (List<Map<String, Object>>)param.get("infoRow");
		Map<String, Object> schParam = infoRow.get(0);
	
		schParam.put("SESS_LANG", langCd);
		schParam.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		
		// 기본정보
		List<Map<String, ?>> basicInfo = service.selectConOprnPrmsCmplMultiAprvList(schParam);
		
		// 페이지 구분
		// 1-규제 검토 조회 상세_원료, 2-규제 검토 상세 MRO
		String sub_title = "";
		// 결제 제목 - 화학물질 규제 검토(자재코드 자재명)
		String aprv_ttl = "";
		// 결제 내용
		String aprv_cntn = "";
		
		// 대상 html 태그
		String subTag = "";
		
		// 대상 html 태그
		for(int i=0; i<basicInfo.size(); i++) {
			subTag += "            <tr>\r\n" + 
					  "                <td style=\"width: 3%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					  "                    <p>" + (i+1) + "<br></p>\r\n" + 
					  "                </td>\r\n" + 
					  "                <td style=\"width: 5%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					  "                    <p>" + basicInfo.get(i).get("cmpy_nm") + "<br></p>\r\n" + 
					  "                </td>\r\n" + 
					  "                <td style=\"width: 7%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					  "                    <p>" + basicInfo.get(i).get("wkpl_nm") + "<br></p>\r\n" + 
					  "                </td>\r\n" + 
					  "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					  "                    <p>" + basicInfo.get(i).get("con_id") + "<br></p>\r\n" + 
					  "                </td>\r\n" + 
					  "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					  "                    <p>" + "<a href=\"" + frontUrl + "/cstprms/cstcmpymgntdtllup" + "/" + basicInfo.get(i).get("con_id") + "-" + basicInfo.get(i).get("con_prms_id") + "\" style=\"font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + basicInfo.get(i).get("con_nm") + "</a><br></p>\r\n" + 
					  "                </td>\r\n" + 
					  "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					  "                    <p>" + basicInfo.get(i).get("con_prms_id") + "<br></p>\r\n" + 
					  "                </td>\r\n" + 
					  "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					  "                    <p>" + basicInfo.get(i).get("con_oprn_type_nm") + "<br></p>\r\n" + 
					  "                </td>\r\n" +
					  "                <td style=\"width: 7%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					  "                    <p>" + basicInfo.get(i).get("con_oprn_wkpl_loc_nm") + "<br></p>\r\n" + 
					  "                </td>\r\n" +
					  "                <td style=\"width: 8%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					  "                    <p>" + basicInfo.get(i).get("con_oprn_dtl_loc_nm") + "<br></p>\r\n" + 
					  "                </td>\r\n" +
					  "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					  "                    <p>" + basicInfo.get(i).get("con_dt") + "<br></p>\r\n" + 
					  "                </td>\r\n" +
					  "                <td style=\"width: 5%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					  "                    <p>" + basicInfo.get(i).get("con_rq_usr_nm") + "<br></p>\r\n" + 
					  "                </td>\r\n" +
					  "                <td style=\"width: 5%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					  "                    <p>" + basicInfo.get(i).get("otsr_con_bsen_nm") + "<br></p>\r\n" + 
					  "                </td>\r\n" +
					  "                <td style=\"width: 5%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: right;\">\r\n" + 
					  "                    <p>" + basicInfo.get(i).get("oprr_cnt") + "<br></p>\r\n" + 
					  "                </td>\r\n" +
					  "            </tr>\r\n";
		}
				
		
		// 결제 제목 - 작업명
		aprv_ttl = "[작업종료 품의]작업완료 멀티 결재";
		// 결제 내용
		aprv_cntn = "<div>\r\n" + 
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "대상목록" + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 3%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 5%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "회사" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 7%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "사업장" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "작업번호" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "작업명" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "허가번호" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "작업종류" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 7%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "작업위치" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 8%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "상세위치" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "작업일시" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 5%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "작업요청자" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 5%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "작업업체" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 5%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "작업인원" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				subTag + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 
				"</div>";
		
		Map<String, Object> aprvParam = new HashMap<>();
//					결재 키 
		String aprv_no = UUID.randomUUID().toString().replace("-", "");
		aprvParam.put("aprv_no", aprv_no);
//					결재 코드
		aprvParam.put("arpv_cd", "30");		
//			 		업무번호
		aprvParam.put("biz_no", "conoprnprmscmplmulti");
//					상신자
		aprvParam.put("aprv_usr_id", usrId);
//					결재 상태 코드 - 결재상신
		aprvParam.put("aprv_stt_cd", "1");		
//					후행메서드	
		aprvParam.put("aprv_mthd_nm", "aprvConOprnPrmsCmplMultiCnfm");
//					제목
		aprvParam.put("aprv_ttl", aprv_ttl);
//					내용
		aprvParam.put("aprv_cntn", aprv_cntn);		
//					결재 비고	
		aprvParam.put("aprv_note", "");
//					사원 번호	
		aprvParam.put("SESS_USR_ID", usrId);
//			 		결제 정보 table insert 
		n = aprvService.insertAprv(aprvParam);		
		
		Map<String, Object> data = new HashMap<>();
		if(n > -1) {	
			schParam.put("con_oprn_cmpy_aprv_no", aprv_no);

			List<String> updateList = new ArrayList<>();
			updateList = (List<String>) schParam.get("con_prms_ids");
			
			if(updateList.size() > 0) {
				for(int i = 0; i < updateList.size(); i++) {
					schParam.put("con_prms_id", updateList.get(i));
					n = service.updateConOprnPrmsCmplMultiAprv(schParam);
				}
			}
			
			data.put("Success", "Success");
			data.put("aprvNo", aprv_no);
			data.put("usrId", usrId);
		}
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	// 대시보드 테스트 데이터 조회
	@GetMapping("/dash-test")
	public ResponseEntity<?> selectDashTestList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		List<Map<String, ?>> list = service.selectDashTestList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
}
