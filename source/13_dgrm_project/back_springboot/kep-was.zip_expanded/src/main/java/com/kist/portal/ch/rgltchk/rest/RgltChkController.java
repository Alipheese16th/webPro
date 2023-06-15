package com.kist.portal.ch.rgltchk.rest;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kist.portal.ch.rgltchk.service.RgltChkService;
import com.kist.portal.common.aprv.service.AprvService;
import com.kist.portal.common.email.rest.SendEmailUtil;
import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.common.utils.MultiLanguageUtil;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/ch/rgltchk")
public class RgltChkController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SessionManager sessionManager;

	@Value("${dgrm.front-url}")
	private String frontUrl;
	
	@Value("${dgrm.server-url}")
	private String serverUrl;
	
	@Autowired
	private RgltChkService service;
	
	@Autowired
	private AprvService aprvService;
	
	@Autowired
	private SendEmailUtil util;
	
	@Autowired
	private MultiLanguageUtil mUtil;
	
	// 규제검토요청 목록 조회
	@GetMapping("/rglt-chk-req-list")
	public ResponseEntity<?> selectRgltChkReqList(@RequestParam Map<String, String> param) {
		// 언어 셋 지정
		param.put("SESS_LANG", "KO");
		List<Map<String, ?>> list = service.selectRgltChkReqList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	// 규제검토요청 상세 조회
	@GetMapping("/rglt-chk-req-dtl/{rgltChkNo}")
	public ResponseEntity<?> selectRgltChkReqDtl(@PathVariable String rgltChkNo, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		Map<String, String> param = new HashMap<>();
		param.put("rglt_chk_no", rgltChkNo);
		param.put("chk_req_clsf_cd", "1");
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		
		// 요청 정보
		List<Map<String, ?>> basicList = service.selectRgltChkReqMstDtl(param);
		// 성분 정보 리스트
		List<Map<String, ?>> cnstList = service.selectRgltChkReqCnstList(param);
		// 구성물질 리스트
		List<Map<String, ?>> sbstList = service.selectRgltChkReqSbstList(param);
		// 예상 규제 정보
		List<Map<String, ?>> rgltList = service.selectRgltChkReqRgltList(param);
		// 경고 표지 정보
		List<Map<String, ?>> alertList = service.selectRgltChkReqGwsgnList(param);
		// 그림문자 정보
		List<Map<String, ?>> picList = service.selectRgltChkReqGwsgnPicList(param);
		
		Map<String, List<Map<String, ?>>> data = new HashMap<>();
		data.put("basic", basicList);
		data.put("cnst", cnstList);
		data.put("sbst", sbstList);
		data.put("rglt", rgltList);
		data.put("alert", alertList);
		data.put("pic", picList);
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	// 규제검토요청 상세 조회 (수정화면)
	@GetMapping("/rglt-chk-req-dtl-edt/{rgltChkNo}")
	public ResponseEntity<?> selectRgltChkReqDtlEdt(@PathVariable String rgltChkNo, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		Map<String, String> param = new HashMap<>();
		param.put("rglt_chk_no", rgltChkNo);
		param.put("chk_req_clsf_cd", "1");
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		
		param.put("sess_usr_id", userInfo.getUsrId());
		
		String result = "";
		
		// 권한
		List<Map<String, ?>> authData = service.selectRgltChkReqAuthChk(param);
		
		// 요청 정보
		List<Map<String, ?>> basicList = new ArrayList();
		// 성분 정보 리스트
		List<Map<String, ?>> cnstList = new ArrayList();
		// 구성물질 리스트
		List<Map<String, ?>> sbstList = new ArrayList();
		// 예상 규제 정보
		List<Map<String, ?>> rgltList = new ArrayList();
		// 경고 표지 정보
		List<Map<String, ?>> alertList = new ArrayList();
		// 그림문자 정보
		List<Map<String, ?>> picList = new ArrayList();
		
		// 권한
		List<Map<String, ?>> authInfo = new ArrayList();
		Map<String, String> authM = new HashMap<>();
		
		if ("Y".equals(authData.get(0).get("auth_chk"))) {
			basicList = service.selectRgltChkReqMstDtl(param);
			cnstList = service.selectRgltChkReqCnstList(param);
			sbstList = service.selectRgltChkReqSbstList(param);
			rgltList = service.selectRgltChkReqRgltList(param);
			alertList = service.selectRgltChkReqGwsgnList(param);
			picList = service.selectRgltChkReqGwsgnPicList(param);
			
			result = "Y";
		} else {
			result = "N";
		}
		authM.put("auth", result);
		authInfo.add(0, authM);
		
		Map<String, List<Map<String, ?>>> data = new HashMap<>();
		data.put("basic", basicList);
		data.put("cnst", cnstList);
		data.put("sbst", sbstList);
		data.put("rglt", rgltList);
		data.put("alert", alertList);
		data.put("pic", picList);
		data.put("auth", authInfo);
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	// 규제검토요청 구성물질 추가 조회
	@GetMapping("/rglt-chk-req-sbst-add-list")
	public ResponseEntity<?> selectRgltChkReqRgltAddList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		
		List<Map<String, ?>> list = service.selectRgltChkReqRgltAddList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	// 규제검토요청 저장
	@PutMapping("/rglt-chk-req-dtl-mtrl")
	@Transactional
	public ResponseEntity<?> updateRgltChkReqDtlMtrl(@RequestBody Map<String, Object> param) {
	
		// 구성물질
		//insert, update, delete 데이터 초기화
		List<Map<String, String>> insertList = (List<Map<String, String>>)param.get("insertRow");
		List<Map<String, String>> updateList = (List<Map<String, String>>)param.get("updateRow");
		List<Map<String, String>> deleteList = (List<Map<String, String>>)param.get("deleteRow");
		
		// 예상규제
		List<Map<String, String>> rgltRow = (List<Map<String, String>>)param.get("rgltRow");
		
		// 기본정보
		List<Map<String, String>> infoRow = (List<Map<String, String>>)param.get("infoRow");
		
		int n = 0;
		int m = 0;
		int j = 0;
		int k = 0;
		int l = 0;
		
		// 인서트
		if (insertList != null) {
			for(int i = 0; i < insertList.size(); i++) {
				Map<String, String> temp = insertList.get(i);
				n = service.insertRgltChkReqSbst(temp);
				
//				Map<String, String> chkParam = new HashMap<String, String>();
//				chkParam.put("rglt_chk_no", temp.get("rglt_chk_no"));
//				chkParam.put("chk_req_clsf_cd", temp.get("chk_req_clsf_cd"));
//				chkParam.put("mtrl_no", temp.get("mtrl_no"));
//				chkParam.put("sbst_no", temp.get("sbst_no"));
//				chkParam.put("sbst_seq", temp.get("sbst_seq"));
//				
//				// 기존 데이터 존재 여부 체크
//				Map<String, ?> map = service.selectRgltChkReqSbstChk(chkParam);
//				if ("Y".equals(map.get("up_yn"))) {
//					n = service.updateRgltChkReqSbstAdd(temp);
//				} else {
//					n = service.insertRgltChkReqSbst(temp);
//				}
			}
		}
		
		// 업데이트
		if (updateList != null) {
			for (int i = 0; i < updateList.size(); i++) {
				Map<String, String> temp = updateList.get(i);
				n = service.updateRgltChkReqSbst(temp);
			}
		}
		
		// 삭제
		if (deleteList != null) {
			for(int i = 0; i < deleteList.size(); i++) {
				Map<String, String> temp = deleteList.get(i);
				n = service.deleteRgltChkReqSbst(temp);
			}
		}
		
		Map<String, String> tempInfo = infoRow.get(0);
		// 자재정보 수정
		k = service.updateRgltChkReqMtrl(tempInfo);
		
		if (rgltRow != null) {
			// 예상규제 삭제
			if (rgltRow.size() > 0) {
				service.deleteRgltChkReqRglt(tempInfo);
			}
			
			// 예상규제 저장
			m = service.insertRgltChkReqRglt(tempInfo);
//			for (int i = 0; i < rgltRow.size(); i++) {
//				Map<String, String> temp = rgltRow.get(i);
//				m = service.insertRgltChkReqRglt(temp);
//			}
		}
		
		// 규제물질 삭제
		service.deleteRgltChkReqRgltSbst(tempInfo);
		// 규제물질 저장
		j = service.insertRgltChkReqRgltSbst(tempInfo);
		
		// 검토요청 반려상태가 아닌경우 진행상태 변경
		// 검토요청시 반려상태인 경우에만 규제검토 테이블을 삭제하여 저장시 기존 진행상태 유지 
		if(!"40".equals(tempInfo.get("rglt_chk_req_prog_stt_cd"))) {
			// 검토요청 저장
			k = service.updateRgltChkReqDtl(tempInfo);
		}
		
		String result;
		result = (n > -1? (m > -1? (j > -1? (k > -1? (l > -1? Constant.SUCCESS : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL);
		
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// 규제검토요청 저장
	@PutMapping("/rglt-chk-req-dtl-imk")
	@Transactional
	public ResponseEntity<?> updateRgltChkReqDtlImk(@RequestBody Map<String, Object> param) {
	
		// 구성물질
		//insert, update, delete 데이터 초기화
		List<Map<String, String>> insertList = (List<Map<String, String>>)param.get("insertRow");
		List<Map<String, String>> updateList = (List<Map<String, String>>)param.get("updateRow");
		List<Map<String, String>> deleteList = (List<Map<String, String>>)param.get("deleteRow");
		
		// 예상규제
		List<Map<String, String>> rgltRow = (List<Map<String, String>>)param.get("rgltRow");
		
		// 기본정보
		List<Map<String, String>> infoRow = (List<Map<String, String>>)param.get("infoRow");
		
		// 경고표지
		List<Map<String, String>> alertRow = (List<Map<String, String>>)param.get("alertRow");
		
		// 그림문자
		List<Map<String, String>> pic = (List<Map<String, String>>)param.get("pic");
		
		int n = 0;
		int m = 0;
		int j = 0;
		int k = 0;
		int l = 0;
		int o = 0;
		int p = 0;
		int s = 0;
		int r = 0;
		
		if (insertList != null) {
			// 인서트
			for(int i = 0; i < insertList.size(); i++) {
				Map<String, String> temp = insertList.get(i);
				//n = service.insertRgltChkReqSbst(temp);
				
				Map<String, String> chkParam = new HashMap<String, String>();
				chkParam.put("rglt_chk_no", temp.get("rglt_chk_no"));
				chkParam.put("chk_req_clsf_cd", temp.get("chk_req_clsf_cd"));
				chkParam.put("mtrl_no", temp.get("mtrl_no"));
				chkParam.put("sbst_no", temp.get("sbst_no"));
				chkParam.put("sbst_seq", temp.get("sbst_seq"));
				
				// 기존 데이터 존재 여부 체크
				Map<String, ?> map = service.selectRgltChkReqSbstChk(chkParam);
				if ("Y".equals(map.get("up_yn"))) {
					n = service.updateRgltChkReqSbstAdd(temp);
				} else {
					n = service.insertRgltChkReqSbst(temp);
				}
			}
		}
		
		if (updateList != null) {
			// 업데이트
			for (int i = 0; i < updateList.size(); i++) {
				Map<String, String> temp = updateList.get(i);
				n = service.updateRgltChkReqSbst(temp);
			}
		}
		
		if (deleteList != null) {
			// 삭제
			for(int i = 0; i < deleteList.size(); i++) {
				Map<String, String> temp = deleteList.get(i);
				n = service.deleteRgltChkReqSbst(temp);
			}
		}
		
		Map<String, String> tempInfo = infoRow.get(0);
		// msds 정보 저장
		r = service.insertRgltChkMsdsInfo(tempInfo);
		// 자재정보 수정 : 2021-12-22 함종호님 요청으로 자재명 수정 추가
		p = service.updateRgltChkReqMtrl(tempInfo);
		
		if (rgltRow != null) {
			// 예상규제 삭제
			if (rgltRow.size() > 0) {
				service.deleteRgltChkReqRglt(tempInfo);
			}
			
			// 예상규제 저장
			m = service.insertRgltChkReqRglt(tempInfo);
//			for (int i = 0; i < rgltRow.size(); i++) {
//				Map<String, String> temp = rgltRow.get(i);
//				m = service.insertRgltChkReqRglt(temp);
//			}
		}
		
		// 규제물질 삭제
		service.deleteRgltChkReqRgltSbst(tempInfo);
		// 규제물질 저장
		j = service.insertRgltChkReqRgltSbst(tempInfo);
		
		// 경고 표지
		Map<String, String> alertInfo = alertRow.get(0);
		if(alertInfo.get("vndr_kyin_yn") != null && "Y".equalsIgnoreCase(String.valueOf(alertInfo.get("vndr_kyin_yn")))) {
			if(alertInfo.get("vndr_type_cd") == null || "".equalsIgnoreCase(String.valueOf(alertInfo.get("vndr_type_cd")))) {
				alertInfo.put("vndr_type_cd", "2");
				logger.info("vndr_type_cd insert1");
			}
		}
		k = service.insertRgltChkReqGwsgn(alertInfo);
		
		// 그림 문자 초기화
		service.deleteRgltChkReqPic(tempInfo);
		// 그림문자
		for (int i = 0; i < pic.size(); i++) {
			Map<String, String> temp = pic.get(i);
			temp.put("rglt_chk_no", tempInfo.get("rglt_chk_no"));
			temp.put("chk_req_clsf_cd", tempInfo.get("chk_req_clsf_cd"));
			temp.put("mtrl_no", tempInfo.get("mtrl_no"));
			temp.put("username", tempInfo.get("username"));
			l = service.insertRgltChkReqPic(temp);
		}
		
		// 검토요청 반려상태가 아닌경우 진행상태 변경
		// 검토요청시 반려상태인 경우에만 규제검토 테이블을 삭제하여 저장시 기존 진행상태 유지 
		if(!"40".equals(tempInfo.get("rglt_chk_req_prog_stt_cd"))) {
			// 검토요청 저장
			o = service.updateRgltChkReqDtl(tempInfo);
		}
		
		String result;
		result = (n > -1? (m > -1? (j > -1? (k > -1? (l > -1? (o > -1? (p > -1? (s > -1? (r > -1? Constant.SUCCESS : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL);
		
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// 규제검토요청 요청
	@PutMapping("/rglt-chk-req-dtl-apr")
	@Transactional
	public ResponseEntity<?> updateRgltChkReqDtlApr(@RequestBody Map<String, Object> param, HttpSession session) {
		// 구성물질
		//insert, update, delete 데이터 초기화
		List<Map<String, String>> insertList = (List<Map<String, String>>)param.get("insertRow");
		List<Map<String, String>> updateList = (List<Map<String, String>>)param.get("updateRow");
		List<Map<String, String>> deleteList = (List<Map<String, String>>)param.get("deleteRow");
		
		// 예상규제
		List<Map<String, String>> rgltRow = (List<Map<String, String>>)param.get("rgltRow");
		
		// 기본정보
		List<Map<String, String>> infoRow = (List<Map<String, String>>)param.get("infoRow");
		
		// 경고표지
		List<Map<String, String>> alertRow = (List<Map<String, String>>)param.get("alertRow");
		
		// 그림문자
		List<Map<String, String>> pic = (List<Map<String, String>>)param.get("pic");
		
		int n = 0;
		int m = 0;
		int j = 0;
		int k = 0;
		int l = 0;
		int p = 0;
		int q = 0;
		int r = 0;
		int s = 0;
		int t = 0;
		int u = 0;
		int z = 0;
		int aa = 0;
		int ab = 0;
		int ac = 0;
		int ad = 0;
		int ae = 0;
		int af = 0;
		int ag = 0;
		
		// 기본정보
		Map<String, String> tempInfo = infoRow.get(0);
		
		// 검토반려 인 경우 기존 검토 데이터를 삭제
		if ("40".equals(tempInfo.get("rglt_chk_req_prog_stt_cd"))) {
			tempInfo.put("chk_req_clsf_cd_temp", "2");
			
			aa = service.deleteRgltChkCnstAll(tempInfo);		// 검토 성분정보 삭제
			ab = service.deleteRgltChkSbstAll(tempInfo);		// 검토 구성성분 삭제
			ac = service.deleteRgltChkRgltAll(tempInfo);		// 검토 규제정보 삭제
			ad = service.deleteRgltChkRgltSbstAll(tempInfo);	// 검토 규제물질 삭제
			ae = service.deleteRgltChkGwsgnAll(tempInfo);		// 검토 경고표지 삭제
			af = service.deleteRgltChkPicgAll(tempInfo);		// 검토 그림문자 삭제
			af = service.deleteRgltChkMsdsAll(tempInfo);		// 검토 MSDS 삭제
		}
		
		if (insertList != null) {
			// 인서트
			for(int i = 0; i < insertList.size(); i++) {
				Map<String, String> temp = insertList.get(i);
				//n = service.insertRgltChkReqSbst(temp);
				
				Map<String, String> chkParam = new HashMap<String, String>();
				chkParam.put("rglt_chk_no", temp.get("rglt_chk_no"));
				chkParam.put("chk_req_clsf_cd", temp.get("chk_req_clsf_cd"));
				chkParam.put("mtrl_no", temp.get("mtrl_no"));
				chkParam.put("sbst_no", temp.get("sbst_no"));
				chkParam.put("sbst_seq", temp.get("sbst_seq"));
				
				// 기존 데이터 존재 여부 체크
				Map<String, ?> map = service.selectRgltChkReqSbstChk(chkParam);
				if ("Y".equals(map.get("up_yn"))) {
					n = service.updateRgltChkReqSbstAdd(temp);
				} else {
					n = service.insertRgltChkReqSbst(temp);
				}
			}
		}
		
		if (updateList != null) {
			// 업데이트
			for (int i = 0; i < updateList.size(); i++) {
				Map<String, String> temp = updateList.get(i);
				n = service.updateRgltChkReqSbst(temp);
			}
		}
		
		if (deleteList != null) {
			// 삭제
			for(int i = 0; i < deleteList.size(); i++) {
				Map<String, String> temp = deleteList.get(i);
				n = service.deleteRgltChkReqSbst(temp);
			}
		}
		// msds 정보 저장
		r = service.insertRgltChkMsdsInfo(tempInfo);
		// msds 정보 복사
		r = service.insertRgltChkMsdsF(tempInfo);
		// 자재정보 수정 : 2021-12-22 함종호님 요청으로 자재명 수정 추가
		p = service.updateRgltChkReqMtrl(tempInfo);
		
		if (rgltRow != null) {
			// 예상규제 삭제
			if (rgltRow.size() > 0) {
				service.deleteRgltChkReqRglt(tempInfo);
			}
			
			// 예상규제 저장
			m = service.insertRgltChkReqRglt(tempInfo);
//			for (int i = 0; i < rgltRow.size(); i++) {
//				Map<String, String> temp = rgltRow.get(i);
//				m = service.insertRgltChkReqRglt(temp);
//			}
		}
		
		// 규제물질 삭제
		service.deleteRgltChkReqRgltSbst(tempInfo);
		// 규제물질 저장
		j = service.insertRgltChkReqRgltSbst(tempInfo);
		// 규제검토용 규제물질 저장
		t = service.insertRgltChkRgltSbstF(tempInfo);
		
		if (alertRow != null) {
			// 경고 표지
			Map<String, String> alertInfo = alertRow.get(0);
			if(alertInfo.get("vndr_kyin_yn") != null && "Y".equalsIgnoreCase(String.valueOf(alertInfo.get("vndr_kyin_yn")))) {
				if(alertInfo.get("vndr_type_cd") == null || "".equalsIgnoreCase(String.valueOf(alertInfo.get("vndr_type_cd")))) {
					alertInfo.put("vndr_type_cd", "2");
					logger.info("vndr_type_cd insert2");
				}
			}
			k = service.insertRgltChkReqGwsgn(alertInfo);
			k = service.insertRgltChkGwsgnF(alertInfo);	// 경고표지 복사
		}
		
		if (pic != null) {
			// 그림 문자 초기화
			service.deleteRgltChkReqPic(tempInfo);
			// 그림문자
			for (int i = 0; i < pic.size(); i++) {
				Map<String, String> temp = pic.get(i);
				temp.put("rglt_chk_no", tempInfo.get("rglt_chk_no"));
				temp.put("chk_req_clsf_cd", tempInfo.get("chk_req_clsf_cd"));
				temp.put("mtrl_no", tempInfo.get("mtrl_no"));
				temp.put("username", tempInfo.get("username"));
				l = service.insertRgltChkReqPic(temp);
				l = service.insertRgltChkPicF(temp);	// 그림문자 복사
			}
		}
		
		// 검토요청 저장
		Map<String, String> info = infoRow.get(0);
		q = service.updateRgltChkReqDtlApr(info);
		
		// 검토반려 인 경우 기존 검토 데이터의 상태를 검토요청으로 변경
		if ("40".equals(tempInfo.get("rglt_chk_req_prog_stt_cd"))) {
			tempInfo.put("rglt_chk_prog_stt_cd", "30");
			tempInfo.put("chk_req_clsf_cd", "2");
			r = service.updateRgltChkStatChg(tempInfo);			
			p = service.updateRgltChkReqMtrl(tempInfo);
		} else {
			r = service.insertRgltChkInfo(info);
			u = service.insertRgltChkMtrlF(info);	// 자재 복사
		}
		info.put("chk_req_clsf_cd", "1");
		s = service.insertRgltChkRgltList(info);
		ag  = service.updateReqRgltChkRgltSave(info);
		n = service.insertRgltChkCnstF(info);	// 성분 복사
		z = service.insertRgltChkSbstF(info);	// 구성성분 복사
		
		// 검토요청 메일
		Boolean mailResult = sendRgltChkReqMail(param, session);
		if(!mailResult) {
			return new ResponseEntity<>(Constant.FAIL, HttpStatus.OK);
		}
		
		String result;
		result = (n > -1? (m > -1? (j > -1? (k > -1? (l > -1? (p > -1? (q > -1? (r > -1? (s > -1? (t > -1? (u > -1? (z > -1? (aa > -1? (ab > -1? (ac > -1? (ad > -1? (ae > -1? (af > -1? (ag > -1? 
				Constant.SUCCESS : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// 규제검토 목록 조회
	@GetMapping("/rglt-chk-list")
	public ResponseEntity<?> selectRgltChkList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		
		List<Map<String, ?>> list = service.selectRgltChkList(param);
		List<Map<String, ?>> biz = service.selectRgltChkBizUser(param);
		
		Map<String, List<Map<String, ?>>> data = new HashMap<>();
		data.put("list", list);
		data.put("biz", biz);
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	// 규제검토 상세 조회
	@GetMapping("/rglt-chk-dtl/{rgltChkNo}")
	public ResponseEntity<?> selectRgltChkDtl(@PathVariable String rgltChkNo, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		Map<String, String> param = new HashMap<>();
		param.put("rglt_chk_no", rgltChkNo);
		param.put("chk_req_clsf_cd", "2");
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		param.put("sess_usr_id", userInfo.getUsrId());
		
		String result = "";

		// 권한
		List<Map<String, ?>> authInfo = new ArrayList();
		Map<String, String> authM = new HashMap<>();
		List<Map<String, ?>> authData = service.selectRgltChkAuthChk(param);

		if ("Y".equals(authData.get(0).get("auth_chk"))) {
			result = "Y";
		} else {
			result = "N";
		}
		authM.put("auth", result);
		authInfo.add(0, authM);

		// 요청 정보
		List<Map<String, ?>> basicList = service.selectRgltChkMstDtl(param);
		// 성분 정보 리스트
		List<Map<String, ?>> cnstList = service.selectRgltChkReqCnstList(param);
		// 구성물질 리스트
		List<Map<String, ?>> sbstList = service.selectRgltChkReqSbstList(param);
		// 예상 규제 정보
		List<Map<String, ?>> rgltList = service.selectRgltChkReqRgltList(param);
		// 경고 표지 정보
		List<Map<String, ?>> alertList = service.selectRgltChkReqGwsgnList(param);
		// 그림문자 정보
		List<Map<String, ?>> picList = service.selectRgltChkReqGwsgnPicList(param);
		// 검토결과 정보
		List<Map<String, ?>> rsltList = service.selectRgltChkRsltInfo(param);
		
		Map<String, List<Map<String, ?>>> data = new HashMap<>();
		data.put("basic", basicList);
		data.put("cnst", cnstList);
		data.put("sbst", sbstList);
		data.put("rglt", rgltList);
		data.put("alert", alertList);
		data.put("pic", picList);
		data.put("rslt", rsltList);
		data.put("auth", authInfo);
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	// 규제검토 상세 조회(수정화면)
	@GetMapping("/rglt-chk-dtl-edt/{rgltChkNo}")
	public ResponseEntity<?> selectRgltChkDtlEdt(@PathVariable String rgltChkNo, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		Map<String, String> param = new HashMap<>();
		param.put("rglt_chk_no", rgltChkNo);
		param.put("chk_req_clsf_cd", "2");
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		
		param.put("sess_usr_id", userInfo.getUsrId());
		
		String result = "";
		
		// 권한
		List<Map<String, ?>> authData = service.selectRgltChkAuthChk(param);
		
		// 요청 정보
		List<Map<String, ?>> basicList = new ArrayList();
		// 성분 정보 리스트
		List<Map<String, ?>> cnstList = new ArrayList();
		// 구성물질 리스트
		List<Map<String, ?>> sbstList = new ArrayList();
		// 예상 규제 정보
		List<Map<String, ?>> rgltList = new ArrayList();
		// 경고 표지 정보
		List<Map<String, ?>> alertList = new ArrayList();
		// 그림문자 정보
		List<Map<String, ?>> picList = new ArrayList();
		// 검토결과 정보
		List<Map<String, ?>> rsltList = new ArrayList();
		// 권한
		List<Map<String, ?>> authInfo = new ArrayList();
		Map<String, String> authM = new HashMap<>();
		
		if ("Y".equals(authData.get(0).get("auth_chk"))) {
			basicList = service.selectRgltChkMstDtl(param);
			cnstList = service.selectRgltChkReqCnstList(param);
			sbstList = service.selectRgltChkReqSbstList(param);
			rgltList = service.selectRgltChkReqRgltList(param);
			alertList = service.selectRgltChkReqGwsgnList(param);
			picList = service.selectRgltChkReqGwsgnPicList(param);
			rsltList = service.selectRgltChkRsltInfo(param);
			
			result = "Y";
		} else {
			result = "N";
		}
		authM.put("auth", result);
		authInfo.add(0, authM);
		
		Map<String, List<Map<String, ?>>> data = new HashMap<>();
		data.put("basic", basicList);
		data.put("cnst", cnstList);
		data.put("sbst", sbstList);
		data.put("rglt", rgltList);
		data.put("alert", alertList);
		data.put("pic", picList);
		data.put("rslt", rsltList);
		data.put("auth", authInfo);
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	// 규제검토 규제DB 초기화
	@PutMapping("/rglt-chk-rglt-reset")
	@Transactional
	public ResponseEntity<?> updateRgltChkRgltDbReset(@RequestBody Map<String, Object> param) {
		
		//insert, update, delete 데이터 초기화
		List<Map<String, String>> insertList = (List<Map<String, String>>)param.get("insertRow");
		List<Map<String, String>> updateList = (List<Map<String, String>>)param.get("updateRow");
		List<Map<String, String>> deleteList = (List<Map<String, String>>)param.get("deleteRow");
		
		int n = 0;
		int m = 0;
		int o = 0;
		
		if (insertList != null) {
			// 인서트
			for(int i = 0; i < insertList.size(); i++) {
				Map<String, String> temp = insertList.get(i);
				//n = service.insertRgltChkReqSbst(temp);
				
				Map<String, String> chkParam = new HashMap<String, String>();
				chkParam.put("rglt_chk_no", temp.get("rglt_chk_no"));
				chkParam.put("chk_req_clsf_cd", temp.get("chk_req_clsf_cd"));
				chkParam.put("mtrl_no", temp.get("mtrl_no"));
				chkParam.put("sbst_no", temp.get("sbst_no"));
				chkParam.put("sbst_seq", temp.get("sbst_seq"));
				
				// 기존 데이터 존재 여부 체크
				Map<String, ?> map = service.selectRgltChkReqSbstChk(chkParam);
				if ("Y".equals(map.get("up_yn"))) {
					n = service.updateRgltChkReqSbstAdd(temp);
				} else {
					n = service.insertRgltChkReqSbst(temp);
				}
			}
		}
		
		if (updateList != null) {
			// 업데이트
			for (int i = 0; i < updateList.size(); i++) {
				Map<String, String> temp = updateList.get(i);
				n = service.updateRgltChkReqSbst(temp);
			}
		}
		
		if (deleteList != null) {
			// 삭제
			for(int i = 0; i < deleteList.size(); i++) {
				Map<String, String> temp = deleteList.get(i);
				n = service.deleteRgltChkReqSbst(temp);
			}
		}
	
		// 기본정보
		List<Map<String, String>> infoRow = (List<Map<String, String>>)param.get("infoRow");
		
		Map<String, String> tempInfo = infoRow.get(0);
		
		service.deleteRgltChkReqRglt(tempInfo); // 규제리스트 삭제
		n = service.insertRgltChkRgltList(tempInfo); // 규제리스트 생성
		// 규제물질 삭제
		service.deleteRgltChkReqRgltSbst(tempInfo);
		// 규제물질 저장
		m = service.insertRgltChkReqRgltSbst(tempInfo);
		// 규제검토 목록 생성 후 규제대상 여부 업데이트
		o = service.updateReqRgltChkRgltSave(tempInfo);
		
		String result;
		result = (n > -1? (m > -1? (o > -1? Constant.SUCCESS : Constant.FAIL) : Constant.FAIL) : Constant.FAIL);
		
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
		
	// 규제검토 저장
	@PutMapping("/rglt-chk-dtl")
	@Transactional
	public ResponseEntity<?> updateRgltChkDtl(@RequestBody Map<String, Object> param) {
		// 구성물질
		//insert, update, delete 데이터 초기화
		List<Map<String, String>> insertList = (List<Map<String, String>>)param.get("insertRow");
		List<Map<String, String>> updateList = (List<Map<String, String>>)param.get("updateRow");
		List<Map<String, String>> deleteList = (List<Map<String, String>>)param.get("deleteRow");
		
		// 예상규제
		List<Map<String, String>> rgltRow = (List<Map<String, String>>)param.get("rgltRow");
		
		// 기본정보
		List<Map<String, String>> infoRow = (List<Map<String, String>>)param.get("infoRow");
		
		// 경고표지
		List<Map<String, String>> alertRow = (List<Map<String, String>>)param.get("alertRow");
		
		// 그림문자
		List<Map<String, String>> pic = (List<Map<String, String>>)param.get("pic");
		
		// 검토정보
		List<Map<String, String>> rsltRow = (List<Map<String, String>>)param.get("rsltRow");
		
		int n = 0;
		int m = 0;
		int j = 0;
		int k = 0;
		int l = 0;
		int o = 0;
		int p = 0;
		int q = 0;
		int r = 0;
		int s = 0;
		
		if (insertList != null) {
			// 인서트
			for(int i = 0; i < insertList.size(); i++) {
				Map<String, String> temp = insertList.get(i);
				//n = service.insertRgltChkReqSbst(temp);
				
				Map<String, String> chkParam = new HashMap<String, String>();
				chkParam.put("rglt_chk_no", temp.get("rglt_chk_no"));
				chkParam.put("chk_req_clsf_cd", temp.get("chk_req_clsf_cd"));
				chkParam.put("mtrl_no", temp.get("mtrl_no"));
				chkParam.put("sbst_no", temp.get("sbst_no"));
				chkParam.put("sbst_seq", temp.get("sbst_seq"));
				
				// 기존 데이터 존재 여부 체크
				Map<String, ?> map = service.selectRgltChkReqSbstChk(chkParam);
				if ("Y".equals(map.get("up_yn"))) {
					n = service.updateRgltChkReqSbstAdd(temp);
				} else {
					n = service.insertRgltChkReqSbst(temp);
				}
			}
		}
		
		if (updateList != null) {
			// 업데이트
			for (int i = 0; i < updateList.size(); i++) {
				Map<String, String> temp = updateList.get(i);
				n = service.updateRgltChkReqSbst(temp);
			}
		}
		
		if (deleteList != null) {
			// 삭제
			for(int i = 0; i < deleteList.size(); i++) {
				Map<String, String> temp = deleteList.get(i);
				n = service.deleteRgltChkReqSbst(temp);
			}
		}
		
		Map<String, String> tempInfo = infoRow.get(0);
		
		// msds 정보 저장
		r = service.insertRgltChkMsdsInfo(tempInfo);
		
		// 규제검토의 대상여부, 대상물질이 수정된경우
		if (rgltRow != null) {
			// 예상규제 rglt_chk_rglt 삭제
			if (rgltRow.size() > 0) {
				service.deleteRgltChkReqRglt(tempInfo);
			}
			m = service.insertRgltChkRgltList(tempInfo);
			// 예상규제 저장
			for (int i = 0; i < rgltRow.size(); i++) {
				Map<String, String> temp = rgltRow.get(i);
				if ("N".equals(temp.get("tgt_yn")) && "Y".equals(temp.get("org_tgt_yn"))) {
					// 규제삭제 - 규제물질정보 삭제
					service.deleteRgltChkRgltSbst(temp);
				} else if("Y".equals(temp.get("tgt_yn")) && "N".equals(temp.get("org_tgt_yn")) && temp.get("sbst_no") != null && !"".equals(temp.get("sbst_no"))) {
					String[] sbstNoArr = temp.get("sbst_no").split(",");
					for (int a = 0; a < sbstNoArr.length; a++) {
						temp.put("sbst_no", sbstNoArr[a]);
							// 규제 리스트 대상여부 신규 추가
							m = service.insertRgltChkRgltSbstSave(temp);
					}
				} else if("Y".equals(temp.get("tgt_yn")) && "Y".equals(temp.get("org_tgt_yn")) && temp.get("sbst_no") != null && !"".equals(temp.get("sbst_no"))) {
					// 기존 대상물질에서 물질정보를 추가 또는 삭제한 경우 기존 자재대상 삭제 후 신규 추가
					service.deleteRgltChkRgltSbst(temp);
					
					String[] sbstNoArr = temp.get("sbst_no").split(",");
					for (int a = 0; a < sbstNoArr.length; a++) {
						temp.put("sbst_no", sbstNoArr[a]);
						// 규제 리스트 대상여부 신규 추가
						m = service.insertRgltChkRgltSbstSave(temp);
					}
				}
				s = service.updateRgltChkRgltSave(temp);
			}
		}
		/* 최초 규제검토 저장시 규제검토 정보를 생성하였으나 로직 변경 후 불필해 삭제.
		 * 규제검토 요청시 예상규제 생성(조회시 모든 규제목록 조회), MRO 후처리시 모든 규제정보 생성
		else {
			// 규제물질 삭제
			service.deleteRgltChkReqRgltSbst(tempInfo);
			// 규제물질 저장
			j = service.insertRgltChkReqRgltSbst(tempInfo);
		}
		*/
		
		// 자재정보 수정
		p = service.updateRgltChkMtrl(tempInfo);
		
		if (alertRow != null) {
			// 경고 표지
			Map<String, String> alertInfo = alertRow.get(0);
			if(alertInfo.get("vndr_kyin_yn") != null && "Y".equalsIgnoreCase(String.valueOf(alertInfo.get("vndr_kyin_yn")))) {
				if(alertInfo.get("vndr_type_cd") == null || "".equalsIgnoreCase(String.valueOf(alertInfo.get("vndr_type_cd")))) {
					alertInfo.put("vndr_type_cd", "2");
					logger.info("vndr_type_cd insert3");
				}
			}
			k = service.insertRgltChkReqGwsgn(alertInfo);
		}
		
		if (pic != null) {
			// 그림 문자 초기화
			service.deleteRgltChkReqPic(tempInfo);
			// 그림문자
			for (int i = 0; i < pic.size(); i++) {
				Map<String, String> temp = pic.get(i);
				temp.put("rglt_chk_no", tempInfo.get("rglt_chk_no"));
				temp.put("chk_req_clsf_cd", tempInfo.get("chk_req_clsf_cd"));
				temp.put("mtrl_no", tempInfo.get("mtrl_no"));
				temp.put("username", tempInfo.get("username"));
				l = service.insertRgltChkReqPic(temp);
			}
		}
		
		// 검토 저장
		o = service.updateRgltChkReqDtlApr(tempInfo);
		tempInfo.put("rglt_chk_prog_stt_cd", "50");
		p = service.updateRgltChkStatChg(tempInfo);
		
		// 분야별 담당자 저장
		Map<String, String> rsltMap = rsltRow.get(0);
		rsltMap.put("rglt_chk_doma_cd", "Z");
		q = service.insertRgltChkDomaRslt(rsltMap);
		
		String result;
		result = (n > -1? (m > -1? (j > -1? (k > -1? (l > -1? (p > -1? (q > -1? (o > -1? (r > -1? (s > -1? Constant.SUCCESS : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// 규제검토 검토결과
	@PutMapping("/rglt-chk-rglt-accept")
	@Transactional
	public ResponseEntity<?> updateRgltChkRgltCnfm(@RequestBody Map<String, Object> param, HttpSession session) {
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
		// LB00000417	규제 검토 조회 상세_원료
		// LB00000420	규제 검토 상세		
		// 기본정보
		List<Map<String, String>> infoRow = (List<Map<String, String>>)param.get("infoRow");
		Map<String, String> info = infoRow.get(0);
		
		int n = 0;
	
		info.put("SESS_LANG", langCd);
		info.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		
		// 규제 검토 조회 상세(원료) + 자재 정보 + MSDS 정보
		List<Map<String, ?>> basicList = service.selectRgltChkMstDtl(info);		
		// 성분 정보
		List<Map<String, ?>> cnstList = service.selectRgltChkReqCnstList(info);
		// 구성물질 정보
		List<Map<String, ?>> sbstList = service.selectRgltChkReqSbstList(info);
		// 규제 검토
		List<Map<String, ?>> rgltList = service.selectRgltChkReqRgltList(info);
		// 규제 검토-검토결과
		List<Map<String, ?>> rsltList = service.selectRgltChkRsltInfo(info);
		// 경고 표지
		List<Map<String, ?>> alertList = service.selectRgltChkReqGwsgnList(info);
		// 그림문자 정보
		List<Map<String, ?>> picList = service.selectRgltChkReqGwsgnPicList(info);
		
		// 페이지 구분
		// 1-규제 검토 조회 상세_원료, 2-규제 검토 상세 MRO
		String sub_title = "";
		// 결제 제목 - 화학물질 규제 검토(자재코드 자재명)
		String aprv_ttl = "";
		// 결제 내용
		String aprv_cntn = "";
		// 성분정보 html 태그
		String subTagCnst = "";
		// 구성물질 html 태그
		String subTagSbst = "";
		// 자재구분 IF html 태그
		String subTagMtrlKind = "";
		// 규제검토 정보 html 태그
		String subTagRglt = "";
		// 경고표지 html 태그
		String subTagAlert = "";
		// 그림문자 html 태그
		String subTag = "";
		
		// 그림문자 html 태그
		for(int i=0; i<picList.size(); i++) {
			if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("1")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P01.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("2")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P02.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("3")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P03.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("4")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P04.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("5")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P05.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("6")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P06.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("7")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P07.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("8")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P08.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("9")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P09.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			}
		}
		
		// 성분정보 html 태그
		for(int i=0; i<cnstList.size(); i++) {
			subTagCnst += "            <tr>\r\n" + 
					      "                <td style=\"width: 3%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + (i+1) + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + cnstList.get(i).get("comp_cnst_no") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + cnstList.get(i).get("cas_no") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 57%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					      "                    <p>" + cnstList.get(i).get("comp_cnst_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + cnstList.get(i).get("comp_cnst_ratio") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "            </tr>\r\n";
		}
		
		// 구성물질 html 태그
		for(int i=0; i<sbstList.size(); i++) {
			subTagSbst += "            <tr>\r\n" + 
					      "                <td style=\"width: 3%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + (i+1) + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + sbstList.get(i).get("sbst_type_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + sbstList.get(i).get("cas_no") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 42%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					      "                    <p>" + sbstList.get(i).get("sbst_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + sbstList.get(i).get("sbst_rati") + "<br></p>\r\n" + 
					      "                </td>\r\n" +
					      "            </tr>\r\n";
		}
		
		// 규제검토 정보 html 태그
		for(int i=0; i<rgltList.size(); i++) {
			subTagRglt += "            <tr>\r\n" + 
					      "                <td style=\"width: 3%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + (i+1) + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + rgltList.get(i).get("law_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					      "                    <p>" + rgltList.get(i).get("rglt_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + rgltList.get(i).get("org_tgt_yn") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					      "                    <p>" + rgltList.get(i).get("cas_no") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 21%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					      "                    <p>" + rgltList.get(i).get("rglt_note") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "            </tr>\r\n";
		}
		
		// 경고표지 html 태그
		for(int i=0; i<alertList.size(); i++) {
			subTagAlert += "    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
					       "    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000038") + "</p>\r\n" + 
					       "    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
					       "        <tbody>\r\n" + 
					       "            <tr>\r\n" + 
					       "                <td colspan=\"2\" style=\"width: 20%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					       "                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000025") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td colspan=\"3\" style=\"width: 80%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
					       "                    <p>" + basicList.get(0).get("mtrl_nm") + "<br></p>\r\n" + 
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
					       "                    <p>" + alertList.get(0).get("hars_cntn").toString().replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" + 
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
					       "                    <p>" + alertList.get(0).get("prv_cntn").toString().replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "            </tr>\r\n" + 
					       "            <tr>\r\n" + 
					       "                <td style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					       "                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000190") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td colspan=\"3\" style=\"margin: 3px 3px 3px 3px; width: 80%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
					       "                    <p>" + alertList.get(0).get("act_cntn").toString().replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "            </tr>\r\n" + 
					       "            <tr>\r\n" + 
					       "                <td style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					       "                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000045") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td colspan=\"3\" style=\"margin: 3px 3px 3px 3px; width: 80%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
					       "					 <p>" + alertList.get(0).get("str_cntn").toString().replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" +
					       "                </td>\r\n" + 
					       "            </tr>\r\n" + 
					       "            <tr>\r\n" + 
					       "                <td style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					       "                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000189") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td colspan=\"3\" style=\"margin: 3px 3px 3px 3px; width: 80%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
					       "					 <p>" + alertList.get(0).get("scr_cntn").toString().replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" +
					       "                </td>\r\n" + 
					       "            </tr>\r\n" + 
					       "            <tr>\r\n" + 
					       "                <td colspan=\"2\" style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					       "                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000130") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
					       "                    <p>" + alertList.get(0).get("vndr_nm") + "<br></p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					       "                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000186") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td style=\"width: 30%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
					       "                    <p>" + alertList.get(0).get("vndr_addr") + "<br></p>\r\n" + 
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
					       "                    <p>" + alertList.get(0).get("vndr_phon_no") + "<br></p>\r\n" + 
					       "                </td>\r\n" + 
					       "            </tr>\r\n" + 
					       "            <tr>\r\n" + 
					       "                <td colspan=\"2\" style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					       "                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000183") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td style=\"width: 302px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					       "                    <p>" + alertList.get(0).get("vndr_rprs_nm") + "<br></p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td style=\"width: 211px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					       "                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000182") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td style=\"width: 212px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					       "                    <p>" + alertList.get(0).get("vndr_coreg_no") + "<br></p>\r\n" + 
					       "                </td>\r\n" + 
					       "            </tr>\r\n" + 
					       "        </tbody>\r\n" + 
					       "    </table>\r\n";
		}
		
		// 자재구분 원료인 경우(원료, 향료)
		if ("1".equals(basicList.get(0).get("mtrl_clsf_cd"))) {
			sub_title = mUtil.getMLang(mlangCntn, "LB00000417") ;
			// 성분정보
			subTagMtrlKind += 
							"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
							"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000401") + "</p>\r\n" + 
							"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
							"        <tbody>\r\n" + 
							"            <tr>\r\n" + 
							"                <td style=\"width: 3%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
							"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
							"                </td>\r\n" + 
							"                <td style=\"width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
							"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000407") + "<br></p>\r\n" + 
							"                </td>\r\n" + 
							"                <td style=\"width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
							"                    <p style=\"font-weight: bold;\">" + "CAS No." + "<br></p>\r\n" + 
							"                </td>\r\n" + 
							"                <td style=\"width: 57%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
							"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000196") + "<br></p>\r\n" + 
							"                </td>\r\n" + 
							"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
							"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000195") + "<br></p>\r\n" + 
							"                </td>\r\n" + 
							"            </tr>\r\n" + 
							subTagCnst + 
							"        </tbody>\r\n" + 
							"    </table>\r\n  		";
		} else {
			sub_title = mUtil.getMLang(mlangCntn, "LB00000420") ;
		}
		// 구성물질 정보
		subTagMtrlKind +=	
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000400") + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 3%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000270") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "CAS No." + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 42%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000196") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000195") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				subTagSbst + 
				"        </tbody>\r\n" + 
				"    </table>\r\n";
		 
		
		// 결제 제목 - 화학물질 규제 검토(자재코드 자재명)
		aprv_ttl = mUtil.getMLang(mlangCntn, "LB00000514") + "(" + info.get("mtrl_no") + " " + basicList.get(0).get("mtrl_nm") + ")";
		// 결제 내용
		aprv_cntn = "<div>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + sub_title + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000397") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("rglt_chk_no") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000283") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("rglt_chk_usr_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000284") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("rglt_chk_dt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 				
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000416") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("wkpl_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000150") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("rglt_chk_req_usr_nm") + " / " + basicList.get(0).get("rglt_chk_req_dept_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000149") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("rglt_chk_req_dt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 

				
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000405") + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000024") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_no") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000025") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000022") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_clsf_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000023") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("macl_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 		
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000404") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_reg_dt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000403") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_reg_usr_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000402") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_site") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000355") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("phase_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000354") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_puse_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000353") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_puse_cntn") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n";
		// 자재구분 MRO인경우 단위중량 조회
		if ("2".equals(basicList.get(0).get("mtrl_clsf_cd"))) {
			aprv_cntn += 
		   		"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000528") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"5\" style=\"width: 88%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("pchs_unit_weght") + "&nbsp;" + basicList.get(0).get("pchs_unit_nm") + "<br></p>\r\n" +  
				"                </td>\r\n" +  
				"            </tr>\r\n";
		}
			aprv_cntn += 
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000506") + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000202") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("enact_da") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000203") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("rev_da") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000266") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("msds_ver") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000177") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + "<a href=\"" + serverUrl + "/api/v1/public/download/" + basicList.get(0).get("ko_atfl_no") + "\">" + basicList.get(0).get("ko_atfl_nm") + "</a>" + "<br></p>\r\n" +
				"                </td>\r\n" + 
				"            </tr>\r\n" + 	
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000176") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n"; 

				if(!"".equals(basicList.get(0).get("en_atfl_no")) && null != (basicList.get(0).get("en_atfl_no"))) {
					aprv_cntn += " <p>" + "<a href=\"" + serverUrl + "/api/v1/public/download/" + basicList.get(0).get("en_atfl_no") + "\">" + basicList.get(0).get("en_atfl_nm") + "</a>" + "<br></p>\r\n";
				}
				
				aprv_cntn +=
				"                </td>\r\n" + 
				"            </tr>\r\n" + 				
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 

				
				subTagMtrlKind +
				
				
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000268") + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 3%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000322") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 25%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000028") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000212") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 25%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000408") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 21%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000095") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				subTagRglt + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" +
				
								
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000268") + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000395") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + rsltList.get(0).get("rglt_chk_usr_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000415") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + rsltList.get(0).get("rglt_chk_dt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000538") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("dngr_kind_nm") + " / " + basicList.get(0).get("dngr_dtl_kind_nm") +"</p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td rowspan=\"2\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000282") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + rsltList.get(0).get("rglt_chk_mtrl_use_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + rsltList.get(0).get("rglt_chk_rslt_cntn") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 	
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000419") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + rsltList.get(0).get("rglt_chk_rejc_cntn") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 			
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 
				
				
				subTagAlert +
				"</div>";
		
//		결재 키 
		String aprv_no = UUID.randomUUID().toString().replace("-", "");
		param.put("aprv_no", aprv_no);
//		결재 코드 - 규제 검토
		param.put("arpv_cd", "10");		
// 		업무번호
		param.put("biz_no", basicList.get(0).get("rglt_chk_no"));
//		상신자
		param.put("aprv_usr_id", usrId);
//		결재 상태 코드 - 결재상신
		param.put("aprv_stt_cd", "1");		
//		후행메서드	
		param.put("aprv_mthd_nm", "updateRgltChkAprv");
//		제목
		param.put("aprv_ttl", aprv_ttl);
//		내용
		param.put("aprv_cntn", aprv_cntn);		
//		결재 비고	
		param.put("aprv_note", "");
//		사원 번호	
		param.put("SESS_USR_ID", usrId);
// 		결제 정보 table insert 
		n = aprvService.insertAprv(param);		
		
		Map<String, Object> data = new HashMap<>();
		if(n > -1) {	
			// 규제검토 상태변경 결제 키 등록, 규제_검토_진행_상태_코드 = 결재 상신 '60'
		   	info.put("aprv_no", aprv_no);
			n = service.updateRgltChkAprvChg(info);
			
			data.put("Success", "Success");
			data.put("aprvNo", param.get("aprv_no"));
			data.put("usrId", param.get("SESS_USR_ID"));
		}
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

	// 규제검토 검토반려
	@PutMapping("/rglt-chk-rglt-reject")
	@Transactional
	public ResponseEntity<?> updateRgltChkRgltCnfmCancle(@RequestBody Map<String, Object> param, HttpSession session) {
	
		// 기본정보
		List<Map<String, String>> infoRow = (List<Map<String, String>>)param.get("infoRow");
		
		int n = 0;
		int m = 0;
		int o = 0;
		
		Map<String, String> tempInfo = infoRow.get(0);
		
		// 담당자 반려
		o = service.updateRgltChkDomaRsltReject(tempInfo);
		
		tempInfo.put("rglt_chk_prog_stt_cd", "40");
		
		// 검토반려
		n = service.updateRgltChkReject(tempInfo);
		
		tempInfo.put("rglt_chk_req_prog_stt_cd", "40");
		tempInfo.put("chk_req_clsf_cd", "1");
		
		// 요청반려
		m = service.updateRgltChkReqStatChg(tempInfo);
		
		
		
		// 검토반려 메일 발송
		Boolean mailResult = sendRgltChkRejectMail(param, session);
		
		if(!mailResult) {
			return new ResponseEntity<>(Constant.FAIL, HttpStatus.OK);
		}
		
		String result;
		result = (n > -1? (m > -1? Constant.SUCCESS : Constant.FAIL) : Constant.FAIL);
		
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	// 규제검토 검토반려 취소
	@PutMapping("/rglt-chk-rglt-reject-cancel")
	@Transactional
	public ResponseEntity<?> updateRgltChkRgltRejectCancel(@RequestBody Map<String, Object> param, HttpSession session) {
	
		// 기본정보
		List<Map<String, String>> infoRow = (List<Map<String, String>>)param.get("infoRow");
		
		int n = 0;
		int m = 0;
		int o = 0;
		
		Map<String, String> tempInfo = infoRow.get(0);
		
		// 담당자 반려 취소
		o = service.updateRgltChkDomaRsltRejectCancel(tempInfo);
		
		// 검토반려 -> 검토요청
		tempInfo.put("rglt_chk_prog_stt_cd", "30");
		n = service.updateRgltChkRejectCancel(tempInfo);
		
		// 요청반려 -> 검토요청
		tempInfo.put("rglt_chk_req_prog_stt_cd", "30");
		tempInfo.put("chk_req_clsf_cd", "1");
		m = service.updateRgltChkReqStatChgRejectCancel(tempInfo);
		
		
		// 검토반려 취소 메일 발송
		Boolean mailResult = sendRgltChkRejectCancelMail(param, session);
		
		if(!mailResult) {
			return new ResponseEntity<>(Constant.FAIL, HttpStatus.OK);
		}
		
		String result;
		result = (n > -1? (m > -1? Constant.SUCCESS : Constant.FAIL) : Constant.FAIL);
		
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// 규제검토 적용제외 요청 저장
	@PutMapping("/rglt-chk-req-exception")
	@Transactional
	public ResponseEntity<?> updateRgltChkExc(@RequestBody Map<String, Object> param) {
		
		// 구성물질
		//insert, update, delete 데이터 초기화
		List<Map<String, String>> insertList = (List<Map<String, String>>)param.get("insertRow");
		List<Map<String, String>> updateList = (List<Map<String, String>>)param.get("updateRow");
		List<Map<String, String>> deleteList = (List<Map<String, String>>)param.get("deleteRow");
		
		// 예상규제
		List<Map<String, String>> rgltRow = (List<Map<String, String>>)param.get("rgltRow");
		
		// 기본정보
		List<Map<String, String>> infoRow = (List<Map<String, String>>)param.get("infoRow");
		
		// 경고표지
		List<Map<String, String>> alertRow = (List<Map<String, String>>)param.get("alertRow");
		
		// 그림문자
		List<Map<String, String>> pic = (List<Map<String, String>>)param.get("pic");
		
		int n = 0;
		int m = 0;
		int j = 0;
		int k = 0;
		int l = 0;
		int p = 0;
		int q = 0;
		int r = 0;
		int s = 0;
		int t = 0;
		int u = 0;
		int z = 0;
		int aa = 0;
		int ab = 0;
		int ac = 0;
		int ad = 0;
		int ae = 0;
		int af = 0;
		int ag = 0;
		
		// 기본정보
		Map<String, String> tempInfo = infoRow.get(0);
		
		// 검토반려 인 경우 기존 검토 데이터를 삭제
		if ("40".equals(tempInfo.get("rglt_chk_req_prog_stt_cd"))) {
			tempInfo.put("chk_req_clsf_cd_temp", "2");
			
			aa = service.deleteRgltChkCnstAll(tempInfo);		// 검토 성분정보 삭제
			ab = service.deleteRgltChkSbstAll(tempInfo);		// 검토 구성성분 삭제
			ac = service.deleteRgltChkRgltAll(tempInfo);		// 검토 규제정보 삭제
			ad = service.deleteRgltChkRgltSbstAll(tempInfo);	// 검토 규제물질 삭제
			ae = service.deleteRgltChkGwsgnAll(tempInfo);		// 검토 경고표지 삭제
			af = service.deleteRgltChkPicgAll(tempInfo);		// 검토 그림문자 삭제
			af = service.deleteRgltChkMsdsAll(tempInfo);		// 검토 MSDS 삭제
		}
		
		if (insertList != null) {
			// 인서트
			for(int i = 0; i < insertList.size(); i++) {
				Map<String, String> temp = insertList.get(i);
				//n = service.insertRgltChkReqSbst(temp);
				
				Map<String, String> chkParam = new HashMap<String, String>();
				chkParam.put("rglt_chk_no", temp.get("rglt_chk_no"));
				chkParam.put("chk_req_clsf_cd", temp.get("chk_req_clsf_cd"));
				chkParam.put("mtrl_no", temp.get("mtrl_no"));
				chkParam.put("sbst_no", temp.get("sbst_no"));
				chkParam.put("sbst_seq", temp.get("sbst_seq"));
				
				// 기존 데이터 존재 여부 체크
				Map<String, ?> map = service.selectRgltChkReqSbstChk(chkParam);
				if ("Y".equals(map.get("up_yn"))) {
					n = service.updateRgltChkReqSbstAdd(temp);
				} else {
					n = service.insertRgltChkReqSbst(temp); // 구성 물질 추가 
				}
			}
		}
		
		if (updateList != null) {
			// 업데이트
			for (int i = 0; i < updateList.size(); i++) {
				Map<String, String> temp = updateList.get(i);
				n = service.updateRgltChkReqSbst(temp);
			}
		}
		
		if (deleteList != null) {
			// 삭제
			for(int i = 0; i < deleteList.size(); i++) {
				Map<String, String> temp = deleteList.get(i);
				n = service.deleteRgltChkReqSbst(temp);
			}
		}
		// msds 정보 저장
		r = service.insertRgltChkMsdsInfo(tempInfo);
		// msds 정보 복사
		r = service.insertRgltChkMsdsF(tempInfo);
		// 자재정보 수정 : 2021-12-22 함종호님 요청으로 자재명 수정 추가
		p = service.updateRgltChkReqMtrl(tempInfo);
		
		if (rgltRow != null) {
			// 예상규제 삭제
			if (rgltRow.size() > 0) {
				service.deleteRgltChkReqRglt(tempInfo);
			}
			
			// 예상규제 저장
			m = service.insertRgltChkReqRglt(tempInfo);
//					for (int i = 0; i < rgltRow.size(); i++) {
//						Map<String, String> temp = rgltRow.get(i);
//						m = service.insertRgltChkReqRglt(temp);
//					}
		}
		
		// 규제물질 삭제
		service.deleteRgltChkReqRgltSbst(tempInfo);
		// 규제물질 저장
		j = service.insertRgltChkReqRgltSbst(tempInfo);
		// 규제검토용 규제물질 저장
		t = service.insertRgltChkRgltSbstF(tempInfo);
		
		if (alertRow != null) {
			// 경고 표지
			Map<String, String> alertInfo = alertRow.get(0);
			if(alertInfo.get("vndr_kyin_yn") != null && "Y".equalsIgnoreCase(String.valueOf(alertInfo.get("vndr_kyin_yn")))) {
				if(alertInfo.get("vndr_type_cd") == null || "".equalsIgnoreCase(String.valueOf(alertInfo.get("vndr_type_cd")))) {
					alertInfo.put("vndr_type_cd", "2");
					logger.info("vndr_type_cd insert2");
				}
			}
			k = service.insertRgltChkReqGwsgn(alertInfo);
			k = service.insertRgltChkGwsgnF(alertInfo);	// 경고표지 복사
		}
		
		if (pic != null) {
			// 그림 문자 초기화
			service.deleteRgltChkReqPic(tempInfo);
			// 그림문자
			for (int i = 0; i < pic.size(); i++) {
				Map<String, String> temp = pic.get(i);
				temp.put("rglt_chk_no", tempInfo.get("rglt_chk_no"));
				temp.put("chk_req_clsf_cd", tempInfo.get("chk_req_clsf_cd"));
				temp.put("mtrl_no", tempInfo.get("mtrl_no"));
				temp.put("username", tempInfo.get("username"));
				l = service.insertRgltChkReqPic(temp);
				l = service.insertRgltChkPicF(temp);	// 그림문자 복사
			}
		}
		
		// 검토요청 저장
		Map<String, String> info = infoRow.get(0);
		 // q = service.updateRgltChkReqDtlApr(info);
		
		// 검토반려 인 경우 기존 검토 데이터의 상태를 검토요청으로 변경
		if ("40".equals(tempInfo.get("rglt_chk_req_prog_stt_cd"))) {
			tempInfo.put("rglt_chk_prog_stt_cd", "30");
			tempInfo.put("chk_req_clsf_cd", "2");
			r = service.updateRgltChkStatChg(tempInfo);			
			p = service.updateRgltChkReqMtrl(tempInfo);
		} else {
			r = service.insertRgltChkInfo(info);	// 규제 검토 추가
			u = service.insertRgltChkMtrlF(info);	// 자재 복사
		}
		info.put("chk_req_clsf_cd", "1");	// 검토 요청 구분 코드
		s = service.insertRgltChkRgltList(info);
		ag  = service.updateReqRgltChkRgltSave(info);
		n = service.insertRgltChkCnstF(info);	// 성분 복사
		z = service.insertRgltChkSbstF(info);	// 구성성분 복사
				
		
		// 제외사유 정보
		List<Map<String, String>> popinfoRow = (List<Map<String, String>>)param.get("popinfoRow");
		
		int o = 0;
		
		Map<String, String> poptempInfo = popinfoRow.get(0);
		
		// 규제검토 요청 진행상태 - 제외요청
		poptempInfo.put("rglt_chk_req_prog_stt_cd", "90"); 
		
		o = service.updatetRgltChkReqException(poptempInfo);
		
		String result;
		result = (n > -1? (m > -1? (j > -1? (k > -1? (l > -1? (p > -1? (q > -1? (o > -1? (r > -1? (s > -1? Constant.SUCCESS : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// 규제검토 적용제외 요청 - 승인
	@PutMapping("/rglt-chk-exception")
	@Transactional
	public ResponseEntity<?> updateRgltChkException(@RequestBody Map<String, Object> param, HttpSession session) {
	
		// 기본정보
		List<Map<String, String>> infoRow = (List<Map<String, String>>)param.get("infoRow");
		
		int n = 0;
		int m = 0;
		int o = 0;
		
		Map<String, String> tempInfo = infoRow.get(0);
		
		// 규제검토 대상 제외 자재 추가 - 코드 / 다국어
		n = service.insertRgltChkException(tempInfo);
		
		// 규제검토 진행상태 - 적용제외 완료 
		tempInfo.put("rglt_chk_prog_stt_cd", "100");
		
		m = service.updateRgltChkExcReject(tempInfo);

		// 규제 테이블 제외여부 - Y
		o = service.updateRgltExcMtrl(tempInfo);
		
		String result;
		result = (n > -1? (m >  -1? (o > -1? Constant.SUCCESS : Constant.FAIL) : Constant.FAIL) : Constant.FAIL);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// 규제검토 적용제외 요청 - 반려 
	@PutMapping("/rglt-chk-exc-reject")
	@Transactional
	public ResponseEntity<?> updateRgltChkExcRgltCnfmCancle(@RequestBody Map<String, Object> param, HttpSession session) {
	
		// 기본정보
		List<Map<String, String>> infoRow = (List<Map<String, String>>)param.get("infoRow");
		
		int n = 0;
		int m = 0;
		int o = 0;
		
		Map<String, String> tempInfo = infoRow.get(0);
		
		// 담당자 반려
		o = service.updateRgltChkDomaRsltReject(tempInfo);
		
		// 규제검토 진행상태 - 검토반려
		tempInfo.put("rglt_chk_prog_stt_cd", "40");
		
		n = service.updateRgltChkReject(tempInfo);
		
		// 규제검토 요청 진행상태 - 검토반려
		// 검토요청 구분 코드 - 요청
		tempInfo.put("rglt_chk_req_prog_stt_cd", "40");
		tempInfo.put("chk_req_clsf_cd", "1");

		m = service.updateRgltChkReqStatChg(tempInfo);
		
		
		// 규제검토 적용제외 요청 - 반려 메일 발송
		Boolean mailResult = sendRgltChkExcRejectMail(param, session);
		
		if(!mailResult) {
			return new ResponseEntity<>(Constant.FAIL, HttpStatus.OK);
		}
		
		String result;
		result = (n > -1? (m > -1? Constant.SUCCESS : Constant.FAIL) : Constant.FAIL);
		
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// 규제검토 적용제외 요청 - 반려 메일
	@Transactional
	@SuppressWarnings("unchecked")
	public boolean sendRgltChkExcRejectMail(Map<String, Object> param, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		String langCd = userInfo.getLangCd();
				
		// 데이터 초기화
		List<Map<String, String>> infoRow = (List<Map<String, String>>)param.get("infoRow");
		Map<String, String> info = infoRow.get(0);
		
		info.put("SESS_LANG", langCd);
		info.put("SESS_TIMEZONE", userInfo.getTimeZone());
		info.put("chk_req_clsf_cd", "2");
		
		// 요청 정보
		List<Map<String, ?>> basicList = service.selectRgltChkMstDtl(info);
		// 성분 정보 리스트
		List<Map<String, ?>> cnstList = service.selectRgltChkReqCnstList(info);
		// 구성물질 리스트
		List<Map<String, ?>> sbstList = service.selectRgltChkReqSbstList(info);
		// 예상 규제 정보
		List<Map<String, ?>> rgltList = service.selectRgltChkReqRgltList(info);
		// 경고 표지 정보
		List<Map<String, ?>> alertList = service.selectRgltChkReqGwsgnList(info);
		// 그림문자 정보
		List<Map<String, ?>> picList = service.selectRgltChkReqGwsgnPicList(info);
		// 검토결과 정보
		List<Map<String, ?>> rsltList = service.selectRgltChkRsltInfo(info);
		
		param.put("SESS_LANG", langCd);
		
		// 페이지 구분
		String pageUrl = "";
		if ("1".equals(basicList.get(0).get("mtrl_clsf_cd"))) {
			pageUrl = "rgltchkmtrledt";
		} else {
			pageUrl = "rgltchkimkedt";
		}
		
		// 다국어 처리
		String[] mlangNo = {"LB00001287", "LB00000419", "LB00000198", "LB00000420",
				"LB00000397", "LB00000283", "LB00000284", "LB00000416",
				"LB00000150", "LB00000149", "LB00000405", "LB00000024",
				"LB00000025", "LB00000177", "LB00000022", "LB00000023",
				"LB00000176", "LB00000404", "LB00000403", "LB00000402",
				"LB00000355", "LB00000354", "LB00000353", "LB00000401",
				"LB00000407", "LB00000196", "LB00000195", "LB00000400",
				"LB00000270", "LB00000394", "LB00000196", "LB00000195",
				"LB00000268", "LB00000272", "LB00000028", "LB00000212",
				"LB00000408", "LB00000095", "LB00000038", "LB00000025",
				"LB00000173", "LB00000172", "LB00000193", "LB00000192",
				"LB00000191", "LB00000190", "LB00000045", "LB00000189",
				"LB00000130", "LB00000186", "LB00000185", "LB00000184",
				"LB00000183", "LB00000182", "LB00000282", "LB00000322",
				"LB00000395", "LB00000415", "LB00000528"};
		List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo), langCd);
		
		// 그림문자 html 태그
		String subTag = "";
		for(int i=0; i<picList.size(); i++) {
			if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("1")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P01.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("2")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P02.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("3")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P03.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("4")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P04.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("5")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P05.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("6")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P06.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("7")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P07.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("8")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P08.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("9")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P09.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			}
		}
		
		// 성분정보 html 태그
		String subTagCnst = "";
		for(int i=0; i<cnstList.size(); i++) {
			subTagCnst += "            <tr>\r\n" + 
					      "                <td style=\"width: 3%; height: 24px; border-left: 1px solid rgb(204, 204, 204); border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + (i+1) + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + cnstList.get(i).get("comp_cnst_no") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + cnstList.get(i).get("cas_no") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 57%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					      "                    <p>" + cnstList.get(i).get("comp_cnst_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + cnstList.get(i).get("comp_cnst_ratio") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "            </tr>\r\n";
		}
		
		// 구성물질 html 태그
		String subTagSbst = "";
		for(int i=0; i<sbstList.size(); i++) {
			subTagSbst += "            <tr>\r\n" + 
					      "                <td style=\"width: 3%; height: 24px; border-left: 1px solid rgb(204, 204, 204); border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + (i+1) + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + sbstList.get(i).get("sbst_type_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + sbstList.get(i).get("cas_no") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 42%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					      "                    <p>" + sbstList.get(i).get("sbst_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + sbstList.get(i).get("sbst_rati") + "<br></p>\r\n" + 
					      "                </td>\r\n" +
					      "            </tr>\r\n";
		}
		
		// 규제정보 html 태그
		String subTagRglt = "";
		for(int i=0; i<rgltList.size(); i++) {
			subTagRglt += "            <tr>\r\n" + 
					      "                <td style=\"width: 3%; height: 24px; border-left: 1px solid rgb(204, 204, 204); border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + (i+1) + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + rgltList.get(i).get("law_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					      "                    <p>" + rgltList.get(i).get("rglt_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + rgltList.get(i).get("org_tgt_yn") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					      "                    <p>" + rgltList.get(i).get("cas_no") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 21%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + rgltList.get(i).get("rglt_note") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "            </tr>\r\n";
		}
		
		// 메일 업무 코드
		String bizCd = "10";
		// 메일 제목
		String subject = mUtil.getMLang(mlangCntn, "LB00001287") + "(" + basicList.get(0).get("mtrl_nm") + ")";
		// 메일 내용
		String content = "<div>\r\n" +
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000420") + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000397") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("rglt_chk_no") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000283") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("rglt_chk_usr_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000284") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("rglt_chk_dt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000416") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("wkpl_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000150") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("rglt_chk_req_usr_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000149") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("rglt_chk_req_dt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000405") + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000024") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_no") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000025") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000177") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + "<a href=\"" + serverUrl + "/api/v1/public/download/" + basicList.get(0).get("ko_atfl_no") + "\">" + basicList.get(0).get("ko_atfl_nm") + "</a>" + "<br></p>\r\n" +
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000022") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_clsf_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000023") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("macl_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000176") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n";

				if(!"".equals(basicList.get(0).get("en_atfl_no")) && null != (basicList.get(0).get("en_atfl_no"))) {
					content += "                    <p>" + "<a href=\"" + serverUrl + "/api/v1/public/download/" + basicList.get(0).get("en_atfl_no") + "\">" + basicList.get(0).get("en_atfl_nm") + "</a>" + "<br></p>\r\n";
				}

				content +=	 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000404") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_reg_dt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000403") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_reg_usr_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000402") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_site") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000355") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("phase_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000354") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_puse_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000353") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_puse_cntn") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n";
		// 자재구분 MRO인경우 단위중량 조회
		if ("2".equals(basicList.get(0).get("mtrl_clsf_cd"))) {
			content += 
		   		"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000528") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"5\" style=\"width: 88%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("pchs_unit_weght") + "&nbsp;" + basicList.get(0).get("pchs_unit_nm") + "<br></p>\r\n" +  
				"                </td>\r\n" +  
				"            </tr>\r\n";
		}				
			content +=	
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000401") + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 3%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000407") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "CAS No." + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 57%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000196") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000195") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				subTagCnst + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" +
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000400") + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 3%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000270") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "CAS No." + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 42%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000196") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000195") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				subTagSbst + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" +
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000268") + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 3%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000322") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 25%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000028") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000212") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 25%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000408") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 21%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000095") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				subTagRglt + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" +
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000282") + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000395") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 38%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + rsltList.get(0).get("rglt_chk_usr_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000415") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 38%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" +
				"                    <p>" + rsltList.get(0).get("rglt_chk_dt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000282") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 12%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" +
				"                    <p>" + rsltList.get(0).get("rglt_chk_mtrl_use_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 76%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + rsltList.get(0).get("rglt_chk_rslt_cntn").toString().replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000419") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"7\" style=\"width: 88%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + rsltList.get(0).get("rglt_chk_rejc_cntn").toString().replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"        </tbody>\r\n" + 
				"    </table>\r\n" +
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000038") + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 20%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000025") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 80%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_nm") + "<br></p>\r\n" + 
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
				"                    <p>" + alertList.get(0).get("hars_cntn").toString().replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" + 
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
				"                    <p>" + alertList.get(0).get("prv_cntn").toString().replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000190") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"margin: 3px 3px 3px 3px; width: 80%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + alertList.get(0).get("act_cntn").toString().replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000045") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"margin: 3px 3px 3px 3px; width: 80%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"					 <p>" + alertList.get(0).get("str_cntn").toString().replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" +
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000189") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"margin: 3px 3px 3px 3px; width: 80%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"					 <p>" + alertList.get(0).get("scr_cntn").toString().replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" +
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000130") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + alertList.get(0).get("vndr_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000186") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 30%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + alertList.get(0).get("vndr_addr") + "<br></p>\r\n" + 
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
				"                    <p>" + alertList.get(0).get("vndr_phon_no") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000183") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 302px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + alertList.get(0).get("vndr_rprs_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 211px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000182") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 212px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + alertList.get(0).get("vndr_coreg_no") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 
				"</div>";
		// 메일 수신자
		String[] to = new String[1];
		to[0] = info.get("rglt_chk_req_usr_id").toString();
		
		logger.debug("sendRgltChkExcRejectMail content : " + content);
		// 메일 송신
		Boolean mailResult = util.sendMail(bizCd, userInfo.getUsrId(), subject, content, to);
		
		return mailResult; 
	}

	// 규제검토 권한 체크
	@GetMapping("/rglt-chk-dtl-auth/{rgltChkNo}")
	public ResponseEntity<?> selectRgltChkAuthChk(@PathVariable String rgltChkNo, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		Map<String, String> param = new HashMap<>();
		param.put("rglt_chk_no", rgltChkNo);
		param.put("sess_usr_id", userInfo.getUsrId());
		
		String result = "";
		
		// 권한
		List<Map<String, ?>> authData = service.selectRgltChkAuthChk(param);
		
		if ("Y".equals(authData.get(0).get("auth_chk"))) {
			result = "Y";
		} else {
			result = "N";
		}
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// 검토요청 메일
	@Transactional
	@SuppressWarnings("unchecked")
	public boolean sendRgltChkReqMail(Map<String, Object> param, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		String langCd = userInfo.getLangCd();
				
		// 데이터 초기화
		List<Map<String, String>> infoRow = (List<Map<String, String>>)param.get("infoRow");
		Map<String, String> info = infoRow.get(0);
		
		info.put("SESS_LANG", langCd);
		info.put("SESS_TIMEZONE", userInfo.getTimeZone());
		info.put("chk_req_clsf_cd", "1");
		
		
		// 요청정보
		List<Map<String, ?>> basicList = service.selectRgltChkReqMstDtl(info);
		// 성분 정보 리스트
		List<Map<String, ?>> cnstList = service.selectRgltChkReqCnstList(info);
		// 구성물질 리스트
		List<Map<String, ?>> sbstList = service.selectRgltChkReqSbstList(info);
		// 예상 규제 정보
		List<Map<String, ?>> rgltList = service.selectRgltChkReqRgltList(info);
		// 경고 표지 정보
		List<Map<String, ?>> alertList = service.selectRgltChkReqGwsgnList(info);
		// 그림문자 정보
		List<Map<String, ?>> picList = service.selectRgltChkReqGwsgnPicList(info);
		
		param.put("SESS_LANG", langCd);
		
		// 페이지 구분
		String pageUrl = "";
		if ("1".equals(basicList.get(0).get("mtrl_clsf_cd"))) {
			pageUrl = "rgltchkmtrledt";
		} else {
			pageUrl = "rgltchkimkedt";
		}
		
		// 다국어 처리
		String[] mlangNo = {"LB00000337", "LB00000198", "LB00000420",
				"LB00000151", "LB00000283", "LB00000284", "LB00000416",
				"LB00000150", "LB00000149", "LB00000405", "LB00000024",
				"LB00000025", "LB00000177", "LB00000022", "LB00000023",
				"LB00000176", "LB00000404", "LB00000403", "LB00000402",
				"LB00000355", "LB00000354", "LB00000353", "LB00000401",
				"LB00000407", "LB00000196", "LB00000195", "LB00000400",
				"LB00000270", "LB00000394", "LB00000196", "LB00000195",
				"LB00000268", "LB00000322", "LB00000028", "LB00000212",
				"LB00000408", "LB00000095", "LB00000038", "LB00000025",
				"LB00000173", "LB00000172", "LB00000193", "LB00000192",
				"LB00000191", "LB00000190", "LB00000045", "LB00000189",
				"LB00000130", "LB00000186", "LB00000185", "LB00000184",
				"LB00000183", "LB00000182", "LB00000528"};
		List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo), langCd);
		
		// 그림문자 html 태그
		String subTag = "";
		for(int i=0; i<picList.size(); i++) {
			if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("1")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P01.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("2")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P02.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("3")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P03.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("4")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P04.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("5")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P05.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("6")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P06.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("7")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P07.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("8")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P08.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("9")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P09.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			}
		}
		
		// 성분정보 html 태그
		String subTagCnst = "";
		if(cnstList.size() > 0) {
			subTagCnst += "    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
					"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000401") + "</p>\r\n" + 
					"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
					"        <tbody>\r\n" + 
					"            <tr>\r\n" + 
					"                <td style=\"width: 3%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
					"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"                <td style=\"width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
					"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000407") + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"                <td style=\"width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
					"                    <p style=\"font-weight: bold;\">" + "CAS No." + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"                <td style=\"width: 57%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
					"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000196") + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
					"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000195") + "<br></p>\r\n" + 
					"                </td>\r\n" + 
					"            </tr>\r\n";
			for(int i=0; i<cnstList.size(); i++) {
				subTagCnst += "            <tr>\r\n" + 
						      "                <td style=\"width: 3%; height: 24px; border-left: 1px solid rgb(204, 204, 204); border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
						      "                    <p>" + (i+1) + "<br></p>\r\n" + 
						      "                </td>\r\n" + 
						      "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
						      "                    <p>" + cnstList.get(i).get("comp_cnst_no") + "<br></p>\r\n" + 
						      "                </td>\r\n" + 
						      "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
						      "                    <p>" + cnstList.get(i).get("cas_no") + "<br></p>\r\n" + 
						      "                </td>\r\n" + 
						      "                <td style=\"width: 57%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
						      "                    <p>" + cnstList.get(i).get("comp_cnst_nm") + "<br></p>\r\n" + 
						      "                </td>\r\n" + 
						      "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
						      "                    <p>" + cnstList.get(i).get("comp_cnst_ratio") + "<br></p>\r\n" + 
						      "                </td>\r\n" + 
						      "            </tr>\r\n";
			}
			subTagCnst += "        </tbody>\r\n" + 
					"    </table>\r\n";
		}
		
		// 구성물질 html 태그
		String subTagSbst = "";
		for(int i=0; i<sbstList.size(); i++) {
			subTagSbst += "            <tr>\r\n" + 
					      "                <td style=\"width: 3%; height: 24px; border-left: 1px solid rgb(204, 204, 204); border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + (i+1) + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + sbstList.get(i).get("sbst_type_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + sbstList.get(i).get("cas_no") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 42%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					      "                    <p>" + sbstList.get(i).get("sbst_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + sbstList.get(i).get("sbst_rati") + "<br></p>\r\n" + 
					      "                </td>\r\n" +
					      "            </tr>\r\n";
		}
		
		// 규제정보 html 태그
		String subTagRglt = "";
		for(int i=0; i<rgltList.size(); i++) {
			subTagRglt += "            <tr>\r\n" + 
					      "                <td style=\"width: 3%; height: 24px; border-left: 1px solid rgb(204, 204, 204); border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + (i+1) + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + rgltList.get(i).get("law_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					      "                    <p>" + rgltList.get(i).get("rglt_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + rgltList.get(i).get("org_tgt_yn") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					      "                    <p>" + rgltList.get(i).get("cas_no") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 21%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + rgltList.get(i).get("rglt_note") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "            </tr>\r\n";
		}
		
		// 경고표지 html 태그
		String subTagAlert = "";
		for(int i=0; i<alertList.size(); i++) {
			subTagAlert += "    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
					       "    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000038") + "</p>\r\n" + 
					       "    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
					       "        <tbody>\r\n" + 
					       "            <tr>\r\n" + 
					       "                <td colspan=\"2\" style=\"width: 20%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					       "                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000025") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td colspan=\"3\" style=\"width: 80%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
					       "                    <p>" + basicList.get(0).get("mtrl_nm") + "<br></p>\r\n" + 
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
					       "                    <p>" + alertList.get(0).get("hars_cntn").toString().replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" + 
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
					       "                    <p>" + alertList.get(0).get("prv_cntn").toString().replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "            </tr>\r\n" + 
					       "            <tr>\r\n" + 
					       "                <td style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					       "                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000190") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td colspan=\"3\" style=\"margin: 3px 3px 3px 3px; width: 80%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
					       "                    <p>" + alertList.get(0).get("act_cntn").toString().replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "            </tr>\r\n" + 
					       "            <tr>\r\n" + 
					       "                <td style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					       "                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000045") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td colspan=\"3\" style=\"margin: 3px 3px 3px 3px; width: 80%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
					       "					 <p>" + alertList.get(0).get("str_cntn").toString().replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" +
					       "                </td>\r\n" + 
					       "            </tr>\r\n" + 
					       "            <tr>\r\n" + 
					       "                <td style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					       "                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000189") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td colspan=\"3\" style=\"margin: 3px 3px 3px 3px; width: 80%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
					       "					 <p>" + alertList.get(0).get("scr_cntn").toString().replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" +
					       "                </td>\r\n" + 
					       "            </tr>\r\n" + 
					       "            <tr>\r\n" + 
					       "                <td colspan=\"2\" style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					       "                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000130") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
					       "                    <p>" + alertList.get(0).get("vndr_nm") + "<br></p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					       "                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000186") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td style=\"width: 30%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
					       "                    <p>" + alertList.get(0).get("vndr_addr") + "<br></p>\r\n" + 
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
					       "                    <p>" + alertList.get(0).get("vndr_phon_no") + "<br></p>\r\n" + 
					       "                </td>\r\n" + 
					       "            </tr>\r\n" + 
					       "            <tr>\r\n" + 
					       "                <td colspan=\"2\" style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					       "                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000183") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td style=\"width: 302px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					       "                    <p>" + alertList.get(0).get("vndr_rprs_nm") + "<br></p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td style=\"width: 211px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
					       "                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000182") + "</p>\r\n" + 
					       "                </td>\r\n" + 
					       "                <td style=\"width: 212px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					       "                    <p>" + alertList.get(0).get("vndr_coreg_no") + "<br></p>\r\n" + 
					       "                </td>\r\n" + 
					       "            </tr>\r\n" + 
					       "        </tbody>\r\n" + 
					       "    </table>\r\n";
		}
		
		// 메일 업무 코드
		String bizCd = "10";
		// 메일 제목
		String subject = mUtil.getMLang(mlangCntn, "LB00000337") + "(" + basicList.get(0).get("mtrl_nm") + ")";
		// 메일 내용
		String content = "<div>\r\n" + 
				"    <p>\r\n" + 
				"        <spam style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang("LB00000198", langCd) + " :&nbsp;</spam><a href=\"" + frontUrl + "/rgltchk/" + pageUrl + "/" + info.get("rglt_chk_no") + "\" style=\"font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + frontUrl + "/rgltchk/" + pageUrl + "/" + info.get("rglt_chk_no") + "</a>\r\n" + 
				"    </p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang("LB00000420", langCd) + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000151") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("rglt_chk_no") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000150") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("rglt_chk_req_usr_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000149") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("rglt_chk_req_dt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000405") + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000024") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_no") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000025") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000177") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + "<a href=\"" + serverUrl + "/api/v1/public/download/" + basicList.get(0).get("ko_atfl_no") + "\">" + basicList.get(0).get("ko_atfl_nm") + "</a>" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000022") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_clsf_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000023") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("macl_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000176") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n";

				if(!"".equals(basicList.get(0).get("en_atfl_no")) && null != (basicList.get(0).get("en_atfl_no"))) {
					content += "                    <p>" + "<a href=\"" + serverUrl + "/api/v1/public/download/" + basicList.get(0).get("en_atfl_no") + "\">" + basicList.get(0).get("en_atfl_nm") + "</a>" + "<br></p>\r\n";
				}
		
				content +=		
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000404") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_reg_dt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000403") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_reg_usr_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000402") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_site") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000355") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("phase_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000354") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_puse_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000353") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_puse_cntn") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n";
		// 자재구분 MRO인경우 단위중량 조회
		if ("2".equals(basicList.get(0).get("mtrl_clsf_cd"))) {
			content += 
		   		"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000528") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"5\" style=\"width: 88%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("pchs_unit_weght") + "&nbsp;" + basicList.get(0).get("pchs_unit_nm") + "<br></p>\r\n" +  
				"                </td>\r\n" +  
				"            </tr>\r\n";
		}				
			content +=		
				"        </tbody>\r\n" + 
				"    </table>\r\n" +  
				subTagCnst + 
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000400") + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px; text-align: center;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 3%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000270") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "CAS No." + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 42%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000196") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000195") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				subTagSbst + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" +
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000268") + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 3%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000322") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 25%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000028") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000212") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 25%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000408") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 21%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000095") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				subTagRglt + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" +
				subTagAlert +
				"</div>";
		
		Map<String, String> map = new HashMap();
		map.put("wkpl_id", info.get("wkpl_id"));
		map.put("SESS_LANG", langCd);
		// 메일 수신자
		List<Map<String, ?>> mailUser = service.selectRgltChkReqMailReceiver(map);
		String[] to = new String[mailUser.size()];
		for(int i=0; i<mailUser.size(); i++) {
			to[i] = mailUser.get(i).get("usr_id").toString();
		}
		// 메일 송신
		Boolean mailResult = util.sendMail(bizCd, userInfo.getUsrId(), subject, content, to);
		
		return mailResult; 
	}
	
	// 검토반려 메일
	@Transactional
	@SuppressWarnings("unchecked")
	public boolean sendRgltChkRejectMail(Map<String, Object> param, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		String langCd = userInfo.getLangCd();
				
		// 데이터 초기화
		List<Map<String, String>> infoRow = (List<Map<String, String>>)param.get("infoRow");
		Map<String, String> info = infoRow.get(0);
		
		info.put("SESS_LANG", langCd);
		info.put("SESS_TIMEZONE", userInfo.getTimeZone());
		info.put("chk_req_clsf_cd", "2");
		
		// 요청 정보
		List<Map<String, ?>> basicList = service.selectRgltChkMstDtl(info);
		// 성분 정보 리스트
		List<Map<String, ?>> cnstList = service.selectRgltChkReqCnstList(info);
		// 구성물질 리스트
		List<Map<String, ?>> sbstList = service.selectRgltChkReqSbstList(info);
		// 예상 규제 정보
		List<Map<String, ?>> rgltList = service.selectRgltChkReqRgltList(info);
		// 경고 표지 정보
		List<Map<String, ?>> alertList = service.selectRgltChkReqGwsgnList(info);
		// 그림문자 정보
		List<Map<String, ?>> picList = service.selectRgltChkReqGwsgnPicList(info);
		// 검토결과 정보
		List<Map<String, ?>> rsltList = service.selectRgltChkRsltInfo(info);
		
		param.put("SESS_LANG", langCd);
		
		// 페이지 구분
		String pageUrl = "";
		if ("1".equals(basicList.get(0).get("mtrl_clsf_cd"))) {
			pageUrl = "rgltchkmtrledt";
		} else {
			pageUrl = "rgltchkimkedt";
		}
		
		// 다국어 처리
		String[] mlangNo = {"LB00000419", "LB00000198", "LB00000420",
				"LB00000397", "LB00000283", "LB00000284", "LB00000416",
				"LB00000150", "LB00000149", "LB00000405", "LB00000024",
				"LB00000025", "LB00000177", "LB00000022", "LB00000023",
				"LB00000176", "LB00000404", "LB00000403", "LB00000402",
				"LB00000355", "LB00000354", "LB00000353", "LB00000401",
				"LB00000407", "LB00000196", "LB00000195", "LB00000400",
				"LB00000270", "LB00000394", "LB00000196", "LB00000195",
				"LB00000268", "LB00000272", "LB00000028", "LB00000212",
				"LB00000408", "LB00000095", "LB00000038", "LB00000025",
				"LB00000173", "LB00000172", "LB00000193", "LB00000192",
				"LB00000191", "LB00000190", "LB00000045", "LB00000189",
				"LB00000130", "LB00000186", "LB00000185", "LB00000184",
				"LB00000183", "LB00000182", "LB00000282", "LB00000322",
				"LB00000395", "LB00000415", "LB00000419", "LB00000528"};
		List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo), langCd);
		
		// 그림문자 html 태그
		String subTag = "";
		for(int i=0; i<picList.size(); i++) {
			if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("1")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P01.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("2")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P02.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("3")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P03.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("4")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P04.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("5")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P05.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("6")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P06.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("7")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P07.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("8")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P08.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("9")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P09.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			}
		}
		
		// 성분정보 html 태그
		String subTagCnst = "";
		for(int i=0; i<cnstList.size(); i++) {
			subTagCnst += "            <tr>\r\n" + 
					      "                <td style=\"width: 3%; height: 24px; border-left: 1px solid rgb(204, 204, 204); border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + (i+1) + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + cnstList.get(i).get("comp_cnst_no") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + cnstList.get(i).get("cas_no") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 57%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					      "                    <p>" + cnstList.get(i).get("comp_cnst_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + cnstList.get(i).get("comp_cnst_ratio") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "            </tr>\r\n";
		}
		
		// 구성물질 html 태그
		String subTagSbst = "";
		for(int i=0; i<sbstList.size(); i++) {
			subTagSbst += "            <tr>\r\n" + 
					      "                <td style=\"width: 3%; height: 24px; border-left: 1px solid rgb(204, 204, 204); border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + (i+1) + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + sbstList.get(i).get("sbst_type_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + sbstList.get(i).get("cas_no") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 42%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					      "                    <p>" + sbstList.get(i).get("sbst_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + sbstList.get(i).get("sbst_rati") + "<br></p>\r\n" + 
					      "                </td>\r\n" +
					      "            </tr>\r\n";
		}
		
		// 규제정보 html 태그
		String subTagRglt = "";
		for(int i=0; i<rgltList.size(); i++) {
			subTagRglt += "            <tr>\r\n" + 
					      "                <td style=\"width: 3%; height: 24px; border-left: 1px solid rgb(204, 204, 204); border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + (i+1) + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + rgltList.get(i).get("law_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					      "                    <p>" + rgltList.get(i).get("rglt_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + rgltList.get(i).get("org_tgt_yn") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					      "                    <p>" + rgltList.get(i).get("cas_no") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 21%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + rgltList.get(i).get("rglt_note") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "            </tr>\r\n";
		}
		
		// 메일 업무 코드
		String bizCd = "10";
		// 메일 제목
		String subject = mUtil.getMLang(mlangCntn, "LB00000419") + "(" + basicList.get(0).get("mtrl_nm") + ")";
		// 메일 내용
		String content = "<div>\r\n" + 
				"    <p>\r\n" + 
				"        <spam style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang("LB00000198", langCd) + " :&nbsp;</spam><a href=\"" + frontUrl + "/rgltchk/" + pageUrl + "/" + info.get("rglt_chk_no") + "\" style=\"font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + frontUrl + "/rgltchk/" + pageUrl + "/" + info.get("rglt_chk_no") + "</a>\r\n" + 
				"    </p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000420") + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000397") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("rglt_chk_no") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000283") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("rglt_chk_usr_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000284") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("rglt_chk_dt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000416") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("wkpl_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000150") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("rglt_chk_req_usr_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000149") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("rglt_chk_req_dt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000405") + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000024") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_no") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000025") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000177") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + "<a href=\"" + serverUrl + "/api/v1/public/download/" + basicList.get(0).get("ko_atfl_no") + "\">" + basicList.get(0).get("ko_atfl_nm") + "</a>" + "<br></p>\r\n" +
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000022") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_clsf_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000023") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("macl_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000176") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n";

				if(!"".equals(basicList.get(0).get("en_atfl_no")) && null != (basicList.get(0).get("en_atfl_no"))) {
					content += "                    <p>" + "<a href=\"" + serverUrl + "/api/v1/public/download/" + basicList.get(0).get("en_atfl_no") + "\">" + basicList.get(0).get("en_atfl_nm") + "</a>" + "<br></p>\r\n";
				}

				content +=	 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000404") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_reg_dt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000403") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_reg_usr_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000402") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_site") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000355") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("phase_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000354") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_puse_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000353") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_puse_cntn") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n";
		// 자재구분 MRO인경우 단위중량 조회
		if ("2".equals(basicList.get(0).get("mtrl_clsf_cd"))) {
			content += 
		   		"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000528") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"5\" style=\"width: 88%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("pchs_unit_weght") + "&nbsp;" + basicList.get(0).get("pchs_unit_nm") + "<br></p>\r\n" +  
				"                </td>\r\n" +  
				"            </tr>\r\n";
		}				
			content +=	
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000401") + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 3%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000407") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "CAS No." + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 57%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000196") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000195") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				subTagCnst + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" +
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000400") + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 3%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000270") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "CAS No." + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 42%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000196") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000195") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				subTagSbst + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" +
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000268") + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 3%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000322") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 25%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000028") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000212") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 25%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000408") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 21%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000095") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				subTagRglt + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" +
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000282") + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000395") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 38%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + rsltList.get(0).get("rglt_chk_usr_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000415") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 38%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" +
				"                    <p>" + rsltList.get(0).get("rglt_chk_dt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000282") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 12%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" +
				"                    <p>" + rsltList.get(0).get("rglt_chk_mtrl_use_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 76%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + rsltList.get(0).get("rglt_chk_rslt_cntn").toString().replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000419") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"7\" style=\"width: 88%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + rsltList.get(0).get("rglt_chk_rejc_cntn").toString().replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"        </tbody>\r\n" + 
				"    </table>\r\n" +
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000038") + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 20%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000025") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 80%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_nm") + "<br></p>\r\n" + 
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
				"                    <p>" + alertList.get(0).get("hars_cntn").toString().replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" + 
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
				"                    <p>" + alertList.get(0).get("prv_cntn").toString().replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000190") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"margin: 3px 3px 3px 3px; width: 80%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + alertList.get(0).get("act_cntn").toString().replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000045") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"margin: 3px 3px 3px 3px; width: 80%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"					 <p>" + alertList.get(0).get("str_cntn").toString().replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" +
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000189") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"margin: 3px 3px 3px 3px; width: 80%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"					 <p>" + alertList.get(0).get("scr_cntn").toString().replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" +
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000130") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + alertList.get(0).get("vndr_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000186") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 30%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + alertList.get(0).get("vndr_addr") + "<br></p>\r\n" + 
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
				"                    <p>" + alertList.get(0).get("vndr_phon_no") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000183") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 302px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + alertList.get(0).get("vndr_rprs_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 211px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000182") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 212px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + alertList.get(0).get("vndr_coreg_no") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 
				"</div>";
		// 메일 수신자
		String[] to = new String[1];
		to[0] = info.get("rglt_chk_req_usr_id").toString();
		
		logger.debug("sendRgltChkRejectMail content : " + content);
		// 메일 송신
		Boolean mailResult = util.sendMail(bizCd, userInfo.getUsrId(), subject, content, to);
		
		return mailResult; 
	}

	// 검토반려 취소 메일
	@Transactional
	@SuppressWarnings("unchecked")
	public boolean sendRgltChkRejectCancelMail(Map<String, Object> param, HttpSession session) {
		// 언어 셋 지정
		UserInfo userInfo = sessionManager.get(session.getId());
		String langCd = userInfo.getLangCd();
				
		// 데이터 초기화
		List<Map<String, String>> infoRow = (List<Map<String, String>>)param.get("infoRow");
		Map<String, String> info = infoRow.get(0);
		
		info.put("SESS_LANG", langCd);
		info.put("SESS_TIMEZONE", userInfo.getTimeZone());
		info.put("chk_req_clsf_cd", "2");
		
		// 요청 정보
		List<Map<String, ?>> basicList = service.selectRgltChkMstDtl(info);
		// 성분 정보 리스트
		List<Map<String, ?>> cnstList = service.selectRgltChkReqCnstList(info);
		// 구성물질 리스트
		List<Map<String, ?>> sbstList = service.selectRgltChkReqSbstList(info);
		// 예상 규제 정보
		List<Map<String, ?>> rgltList = service.selectRgltChkReqRgltList(info);
		// 경고 표지 정보
		List<Map<String, ?>> alertList = service.selectRgltChkReqGwsgnList(info);
		// 그림문자 정보
		List<Map<String, ?>> picList = service.selectRgltChkReqGwsgnPicList(info);
		// 검토결과 정보
		List<Map<String, ?>> rsltList = service.selectRgltChkRsltInfo(info);
		
		param.put("SESS_LANG", langCd);
		
		// 페이지 구분
		String pageUrl = "";
		if ("1".equals(basicList.get(0).get("mtrl_clsf_cd"))) {
			pageUrl = "rgltchkmtrledt";
		} else {
			pageUrl = "rgltchkimkedt";
		}
		
		// 다국어 처리
		String[] mlangNo = {"LB00001244", "LB00000198", "LB00000420",
				"LB00000397", "LB00000283", "LB00000284", "LB00000416",
				"LB00000150", "LB00000149", "LB00000405", "LB00000024",
				"LB00000025", "LB00000177", "LB00000022", "LB00000023",
				"LB00000176", "LB00000404", "LB00000403", "LB00000402",
				"LB00000355", "LB00000354", "LB00000353", "LB00000401",
				"LB00000407", "LB00000196", "LB00000195", "LB00000400",
				"LB00000270", "LB00000394", "LB00000196", "LB00000195",
				"LB00000268", "LB00000272", "LB00000028", "LB00000212",
				"LB00000408", "LB00000095", "LB00000038", "LB00000025",
				"LB00000173", "LB00000172", "LB00000193", "LB00000192",
				"LB00000191", "LB00000190", "LB00000045", "LB00000189",
				"LB00000130", "LB00000186", "LB00000185", "LB00000184",
				"LB00000183", "LB00000182", "LB00000282", "LB00000322",
				"LB00000395", "LB00000415", "LB00000419", "LB00000528"};
		List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo), langCd);
		
		// 그림문자 html 태그
		String subTag = "";
		for(int i=0; i<picList.size(); i++) {
			if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("1")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P01.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("2")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P02.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("3")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P03.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("4")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P04.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("5")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P05.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("6")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P06.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("7")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P07.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("8")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P08.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			} else if(picList.get(i).get("picg_cd").toString().equalsIgnoreCase("9")) {
				subTag += "<p><img src=\"" + frontUrl + "/images/P09.png\" alt=\"\" class=\"\" style=\"background-repeat: no-repeat; box-sizing: inherit; padding: 0px; margin: 0px; float: left; border-style: none; width: 120px; color: rgb(68, 68, 68); font-family: arita-dotum; font-size: 13px; text-align: center; transition: none 0s ease 0s !important;\"></p>";
			}
		}
		
		// 성분정보 html 태그
		String subTagCnst = "";
		for(int i=0; i<cnstList.size(); i++) {
			subTagCnst += "            <tr>\r\n" + 
					      "                <td style=\"width: 3%; height: 24px; border-left: 1px solid rgb(204, 204, 204); border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + (i+1) + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + cnstList.get(i).get("comp_cnst_no") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + cnstList.get(i).get("cas_no") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 57%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					      "                    <p>" + cnstList.get(i).get("comp_cnst_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + cnstList.get(i).get("comp_cnst_ratio") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "            </tr>\r\n";
		}
		
		// 구성물질 html 태그
		String subTagSbst = "";
		for(int i=0; i<sbstList.size(); i++) {
			subTagSbst += "            <tr>\r\n" + 
					      "                <td style=\"width: 3%; height: 24px; border-left: 1px solid rgb(204, 204, 204); border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + (i+1) + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + sbstList.get(i).get("sbst_type_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + sbstList.get(i).get("cas_no") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 42%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					      "                    <p>" + sbstList.get(i).get("sbst_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + sbstList.get(i).get("sbst_rati") + "<br></p>\r\n" + 
					      "                </td>\r\n" +
					      "            </tr>\r\n";
		}
		
		// 규제정보 html 태그
		String subTagRglt = "";
		for(int i=0; i<rgltList.size(); i++) {
			subTagRglt += "            <tr>\r\n" + 
					      "                <td style=\"width: 3%; height: 24px; border-left: 1px solid rgb(204, 204, 204); border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + (i+1) + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + rgltList.get(i).get("law_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					      "                    <p>" + rgltList.get(i).get("rglt_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + rgltList.get(i).get("org_tgt_yn") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					      "                    <p>" + rgltList.get(i).get("cas_no") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 21%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + rgltList.get(i).get("rglt_note") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "            </tr>\r\n";
		}
		
		// 메일 업무 코드
		String bizCd = "10";
		// 메일 제목
		String subject = mUtil.getMLang(mlangCntn, "LB00001244") + "(" + basicList.get(0).get("mtrl_nm") + ")";
		// 메일 내용
		String content = "<div>\r\n" + 
				"    <p>\r\n" + 
				"        <spam style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang("LB00000198", langCd) + " :&nbsp;</spam><a href=\"" + frontUrl + "/rgltchk/" + pageUrl + "/" + info.get("rglt_chk_no") + "\" style=\"font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + frontUrl + "/rgltchk/" + pageUrl + "/" + info.get("rglt_chk_no") + "</a>\r\n" + 
				"    </p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000420") + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000397") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("rglt_chk_no") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000283") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("rglt_chk_usr_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000284") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("rglt_chk_dt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000416") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("wkpl_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000150") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("rglt_chk_req_usr_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000149") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("rglt_chk_req_dt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000405") + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000024") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_no") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000025") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000177") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + "<a href=\"" + serverUrl + "/api/v1/public/download/" + basicList.get(0).get("ko_atfl_no") + "\">" + basicList.get(0).get("ko_atfl_nm") + "</a>" + "<br></p>\r\n" +
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000022") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_clsf_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000023") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("macl_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000176") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n";

				if(!"".equals(basicList.get(0).get("en_atfl_no")) && null != (basicList.get(0).get("en_atfl_no"))) {
					content += "                    <p>" + "<a href=\"" + serverUrl + "/api/v1/public/download/" + basicList.get(0).get("en_atfl_no") + "\">" + basicList.get(0).get("en_atfl_nm") + "</a>" + "<br></p>\r\n";
				}

				content +=	 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000404") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_reg_dt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000403") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_reg_usr_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000402") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_site") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000355") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("phase_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000354") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_puse_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000353") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_puse_cntn") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n";
		// 자재구분 MRO인경우 단위중량 조회
		if ("2".equals(basicList.get(0).get("mtrl_clsf_cd"))) {
			content += 
		   		"            <tr>\r\n" + 
				"                <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000528") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"5\" style=\"width: 88%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("pchs_unit_weght") + "&nbsp;" + basicList.get(0).get("pchs_unit_nm") + "<br></p>\r\n" +  
				"                </td>\r\n" +  
				"            </tr>\r\n";
		}				
			content +=	
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000401") + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 3%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000407") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "CAS No." + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 57%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000196") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000195") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				subTagCnst + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" +
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000400") + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 3%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000270") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "CAS No." + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 42%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000196") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000195") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				subTagSbst + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" +
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000268") + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 3%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000322") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 25%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000028") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000212") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 25%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000408") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 21%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000095") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				subTagRglt + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" +
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000282") + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000395") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 38%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + rsltList.get(0).get("rglt_chk_usr_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000415") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 38%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" +
				"                    <p>" + rsltList.get(0).get("rglt_chk_dt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000282") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 12%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" +
				"                    <p>" + rsltList.get(0).get("rglt_chk_mtrl_use_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 76%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + rsltList.get(0).get("rglt_chk_rslt_cntn").toString().replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000419") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"7\" style=\"width: 88%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + rsltList.get(0).get("rglt_chk_rejc_cntn").toString().replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"        </tbody>\r\n" + 
				"    </table>\r\n" +
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000038") + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 20%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000025") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 80%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + basicList.get(0).get("mtrl_nm") + "<br></p>\r\n" + 
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
				"                    <p>" + alertList.get(0).get("hars_cntn").toString().replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" + 
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
				"                    <p>" + alertList.get(0).get("prv_cntn").toString().replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000190") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"margin: 3px 3px 3px 3px; width: 80%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + alertList.get(0).get("act_cntn").toString().replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000045") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"margin: 3px 3px 3px 3px; width: 80%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"					 <p>" + alertList.get(0).get("str_cntn").toString().replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" +
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000189") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"margin: 3px 3px 3px 3px; width: 80%; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"					 <p>" + alertList.get(0).get("scr_cntn").toString().replaceAll("(\r\n|\r|\n|\n\r)", "<br/>") + "</p>\r\n" +
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000130") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 25%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + alertList.get(0).get("vndr_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000186") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 30%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 3px;\">\r\n" + 
				"                    <p>" + alertList.get(0).get("vndr_addr") + "<br></p>\r\n" + 
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
				"                    <p>" + alertList.get(0).get("vndr_phon_no") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 119px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000183") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 302px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + alertList.get(0).get("vndr_rprs_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 211px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000182") + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 212px; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + alertList.get(0).get("vndr_coreg_no") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 
				"</div>";
		// 메일 수신자
		String[] to = new String[1];
		to[0] = info.get("rglt_chk_req_usr_id").toString();

		logger.debug("sendRgltChkRejectMail content : " + content);
		logger.debug("sendRgltChkRejectMail to : " + to[0]);
		
		// 메일 송신
		Boolean mailResult = util.sendMail(bizCd, userInfo.getUsrId(), subject, content, to);
		
		return mailResult; 
	}
	
	// 규제검토 결재 후 처리
	@PutMapping("/rglt-chk-aprv")
	@Transactional
	public ResponseEntity<?> updateRgltChkAprv(@RequestBody Map<String, Object> param) {
	
		int a = 0;
		int b = 0;
		int c = 0;
		int d = 0;
		int e = 0;
		int f = 0;
		int g = 0;
		int h = 0;
		int i = 0;
		int j = 0;
		int k = 0;
		int l = 0;
		int m = 0;
		int n = 0;
		
		List<Map<String, String>> aprvParam = (List<Map<String, String>>)param.get("aprvRow");
		
		Map<String, String> info = aprvParam.get(0);
		
		// 결재정보 조회
		List<Map<String, ?>> aprvInfo = service.selectRgltChkAprvBasicInfo(info);
		
		Map<String, String> tempInfo = (Map<String, String>) aprvInfo.get(0);
		
		// 결재 완료
		if ("Y".equals(info.get("aprv_rslt_yn"))) {
			b = service.deleteRgltChkAprvSbstAll(tempInfo);		// 물질 삭제
			c = service.deleteRgltChkAprvRgltAll(tempInfo);		// 규제정보 삭제
			d = service.deleteRgltChkAprvRgltSbstAll(tempInfo);	// 규제물질 삭제
			e = service.deleteRgltChkAprvGwsgnAll(tempInfo);	// 경고표지 삭제
			f = service.deleteRgltChkAprvPicgAll(tempInfo);		// 그림문자 삭제
			g = service.deleteRgltChkAprvMsdsAll(tempInfo);		// MSDS 삭제
			
			
			a = service.updateRgltChkAprvMtrlInfo(tempInfo);	// 자재정보 수정
			
			tempInfo.put("rglt_chk_prog_stt_cd", "80");
			
			h = service.updateRgltChkAprvChkInfo(tempInfo);		// 규제검토 수정
			
			i = service.insertRgltChkAprvSbst(tempInfo);		// 물질 추가
			j = service.insertRgltChkAprvRgltList(tempInfo);	// 규제정보 추가
			k = service.insertRgltChkAprvRgltSbst(tempInfo);	// 규제물질 추가
			l = service.insertRgltChkAprvMsds(tempInfo);		// 경고표지 추가
			m = service.insertRgltChkAprvGwsgn(tempInfo);		// 그림문자 추가
			n = service.insertRgltChkAprvPicg(tempInfo);		// MSDS 추가
		} else { // 반려
			tempInfo.put("rglt_chk_prog_stt_cd", "70");
			
			h = service.updateRgltChkAprvChkInfo(tempInfo);		// 규제검토 수정
		}
		
		String result;
		result = (a > -1? (b > -1? (c > -1? (d > -1? (e > -1? (f > -1? (g > -1? (h > -1? (i > -1? (j > -1? (k > -1? (l > -1? (m > -1? (n > -1? 
				Constant.SUCCESS : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL);
		
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
}
