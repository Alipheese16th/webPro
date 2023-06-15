package com.kist.portal.st.stndinfr.rest;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kist.portal.common.aprv.service.AprvService;
import com.kist.portal.common.chems.service.CdService;
import com.kist.portal.common.comm.service.FileService;
import com.kist.portal.common.email.rest.SendEmailUtil;
import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.common.utils.MultiLanguageUtil;
import com.kist.portal.st.stndinfr.service.RgnMgntService;
import com.kist.portal.st.stndinfr.service.WkplMgntService;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/st/stndinfr")
public class WkplMgntController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SessionManager sessionManager;

	@Value("${spring.profiles.active}")
	private String activeProfile;
	
	@Autowired
	private CdService cdService;
	
	@Value("${dgrm.front-url}")
	private String frontUrl;
	
	@Value("${dgrm.server-url}")
	private String serverUrl;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private AprvService aprvService;
	
	@Autowired
	private SendEmailUtil util;
	
	@Autowired
	private MultiLanguageUtil mUtil;
	
	@Autowired
	private RgnMgntService rgnService;
	
	@Autowired
	private WkplMgntService service;
	
	// 사업장관리 목록
	@GetMapping("/wkpl-mgnt-list")
	public ResponseEntity<?> selectWkplMgntList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		List<Map<String, ?>> list = service.selectWkplMgntList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	// 사업장 관리 상세 조회
	@GetMapping("/wkpl-mgnt-dtl/{wkplId}")
	public ResponseEntity<?> selectWkplMgntDtl(@PathVariable String wkplId, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		Map<String, String> param = new HashMap<>();
		param.put("wkpl_id", wkplId);
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		
		// 사업장 관리 상세
		List<Map<String, ?>> basicInfo = service.selectWkplMgntDtl(param);
		// 사업장 선임 현황
		List<Map<String, ?>> elcList = service.selectWkplMgntElcList(param);
		// 상주 협력사 현황
		List<Map<String, ?>> sbcnList = service.selectWkplMgntSbcnList(param);
		// 사업장 평가
		List<Map<String, ?>> apalList = service.selectWkplMgntApalList(param);
		// 사업장 위치
		List<Map<String, ?>> rgnList = service.selectWkplMgntRgnList(param);
		
		Map<String, Object> param2 = new HashMap<>();
		param2.put("wkpl_id", wkplId);
		param2.put("SESS_LANG", userInfo.getLangCd());
		param2.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		List<Map<String, ?>> locList = rgnService.selectRgnmgntList(param2);

		List<Map<String, ?>> catList = rgnService.selectCatMapgtList(param2);
		Map<String, List<Map<String, ?>>> data = new HashMap<>();
		data.put("basic", basicInfo);
		data.put("elc", elcList);
		data.put("sbcn", sbcnList);
		data.put("apal", apalList);
		data.put("rgn", rgnList);
		data.put("loc", locList);
		data.put("cat", catList);
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	@GetMapping("/wkpl-img")
	public ResponseEntity<?> selectWkplImg(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		Map<String, String> param2 = new HashMap<>();
		param2.put("wkpl_id", (String) param.get("wkpl_id"));
		param2.put("SESS_LANG", userInfo.getLangCd());
		param2.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		
		List<Map<String, ?>> basicInfo = service.selectWkplImg(param2);

		return new ResponseEntity<>(basicInfo, HttpStatus.OK);
	}

	// 사업장 관리 저장
	@SuppressWarnings("unchecked")
	@PutMapping("/wkpl-mgnt-dtl")
	@Transactional
	public ResponseEntity<?> insertWkplMgntDtl(@RequestBody Map<String, Object> param, HttpSession session) {
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
		
		// 선임정보
		//insert, update, delete 데이터 초기화
		List<Map<String, String>> insertElcList = (List<Map<String, String>>)param.get("insertElcRow");
		List<Map<String, String>> updateElcList = (List<Map<String, String>>)param.get("updateElcRow");
		List<Map<String, String>> deleteElcList = (List<Map<String, String>>)param.get("deleteElcRow");
		
		// 협력사정보
		//insert, update, delete 데이터 초기화
		List<Map<String, String>> insertSbcnList = (List<Map<String, String>>)param.get("insertSbcnRow");
		List<Map<String, String>> updateSbcnList = (List<Map<String, String>>)param.get("updateSbcnRow");
		List<Map<String, String>> deleteSbcnList = (List<Map<String, String>>)param.get("deleteSbcnRow");
		
		// 평가정보
		//insert, update, delete 데이터 초기화
		List<Map<String, String>> insertApalList = (List<Map<String, String>>)param.get("insertApalRow");
		List<Map<String, String>> updateApalList = (List<Map<String, String>>)param.get("updateApalRow");
		List<Map<String, String>> deleteApalList = (List<Map<String, String>>)param.get("deleteApalRow");
		
		// 위치정보
		//insert, update, delete 데이터 초기화
		List<Map<String, String>> insertRgnList = (List<Map<String, String>>)param.get("insertRgnRow");
		List<Map<String, String>> updateRgnList = (List<Map<String, String>>)param.get("updateRgnRow");
		List<Map<String, String>> deleteRgnList = (List<Map<String, String>>)param.get("deleteRgnRow");

		// 그룹정보
		//insert, delete 데이터 초기화
		List<Map<String, String>> insertCatList = (List<Map<String, String>>)param.get("insertCatRow");
		List<Map<String, String>> deleteCatList = (List<Map<String, String>>)param.get("deleteCatRow");
	
		Map<String, String> tempInfo = infoRow.get(0);
		String wkplKey = tempInfo.get("wkpl_id");
		String delYn = tempInfo.get("del_yn");
//		String passYn = "Y";
		// 신규
		if ("Y".equals(tempInfo.get("new_yn"))) {
			List<Map<String, ?>> keyMap = service.selectWkplMgntKey(temp);
			wkplKey = keyMap.get(0).get("key").toString();
			// 다국어 키 조회
			String langKey = cdService.selectMlangCdGrpKey(temp);
			temp.put("username", userInfo.getUsrId());
			temp.put("cd_grp_no", "ST00000002");
			// 다국어 기준이 될 언어 파라미터 설정
			temp.put("lang_cd", userInfo.getLangCd());
			// 채번해온 다국어 키 설정
			temp.put("mlang_key", langKey);
			// 코드 그룹의 업무 코드 설정
			temp.put("biz_cd", "ST");
			temp.put("usg_yn", "Y");
			temp.put("cd", wkplKey);
			temp.put("cd_nm", tempInfo.get("wkpl_nm"));
			temp.put("sort_seq", keyMap.get(0).get("sort_seq").toString());
			System.out.println(temp);
			Map<String, ?> cdData = cdService.selectCdDetail(temp);
			if(cdData == null || cdData.isEmpty()) {
				// 코드 테이블 인서트
				cdService.insertCdList(temp);
				// 다국어 테이블 인서트
				n = cdService.insertCdMlangList(temp);
			} else {
				return new ResponseEntity<>("OverlapFail", HttpStatus.OK);
			}
		} else {
			// 삭제
			if ("Y".equals(delYn)) {
				tempInfo.put("usg_yn", "N");
				tempInfo.put("cd", tempInfo.get("wkpl_id"));
				// 코드 테이블 삭제
				cdService.deleteCd(tempInfo);
				// 다국어 테이블 삭제
				n = cdService.deleteCdMlangList(tempInfo);
			} else {
				temp.put("cd_nm", tempInfo.get("wkpl_nm"));
				temp.put("cd", tempInfo.get("wkpl_id"));
				temp.put("cd_grp_no", "ST00000002");
				temp.put("lang_cd", userInfo.getLangCd());
				temp.put("mlang_type_cd", "CD");
				temp.put("biz_cd", "ST");
				temp.put("username", userInfo.getUsrId());
				Map<String, ?> mlangNoMap = cdService.selectCdMlangNoInfo(temp);
				temp.put("mlang_no", mlangNoMap.get("mlang_no").toString());
				service.updateCdMlangWkpl(temp);
			}
		}
		
//		// 중복된 사업장이 있으면 패스
//		if ("N".equals(passYn)) {
//			return new ResponseEntity<>("OverlapFail", HttpStatus.OK);
//		}
		
		// 사업장 등록
		tempInfo.put("wkpl_id", wkplKey);
		tempInfo.put("username", userInfo.getUsrId());
		m = service.insertWkplMgnt(tempInfo);
		
		// 선임정보
		if (insertElcList != null) {
			// 인서트
			for(int i = 0; i < insertElcList.size(); i++) {
				Map<String, String> tempElc = insertElcList.get(i);
				tempElc.put("wkpl_id", wkplKey);
				if ("".equals(tempElc.get("elc_reg_sn")) || null == tempElc.get("elc_reg_sn")) {
					tempElc.put("elc_reg_sn", "0");
				}
				tempElc.put("username", userInfo.getUsrId());
				
				n = service.insertWkplMgntDtlElcInfo(tempElc);
			}
		}
		
		if (updateElcList != null) {
			// 업데이트
			for (int i = 0; i < updateElcList.size(); i++) {
				Map<String, String> tempElc = updateElcList.get(i);
				tempElc.put("wkpl_id", wkplKey);
				tempElc.put("username", userInfo.getUsrId());
				n = service.insertWkplMgntDtlElcInfo(tempElc);
			}
		}
		
		if (deleteElcList != null) {
			// 삭제
			for(int i = 0; i < deleteElcList.size(); i++) {
				Map<String, String> tempElc = deleteElcList.get(i);
				n = service.deleteWkplMgntDtlElcInfo(tempElc);
			}
		}
		
		// 협력사정보
		if (insertSbcnList != null) {
			// 인서트
			for(int i = 0; i < insertSbcnList.size(); i++) {
				Map<String, String> tempSbcn = insertSbcnList.get(i);
				tempSbcn.put("username", userInfo.getUsrId());
				tempSbcn.put("wkpl_id", wkplKey);
				if ("".equals(tempSbcn.get("sbcn_reg_sn")) || null == tempSbcn.get("sbcn_reg_sn")) {
					tempSbcn.put("sbcn_reg_sn", "0");
				}
				
				n = service.insertWkplMgntDtlSbcnInfo(tempSbcn);
			}
		}
		
		if (updateSbcnList != null) {
			// 업데이트
			for (int i = 0; i < updateSbcnList.size(); i++) {
				Map<String, String> tempSbcn = updateSbcnList.get(i);
				tempSbcn.put("username", userInfo.getUsrId());
				tempSbcn.put("wkpl_id", wkplKey);
				n = service.insertWkplMgntDtlSbcnInfo(tempSbcn);
			}
		}
		
		if (deleteSbcnList != null) {
			// 삭제
			for(int i = 0; i < deleteSbcnList.size(); i++) {
				Map<String, String> tempSbcn = deleteSbcnList.get(i);
				n = service.deleteWkplMgntDtlSbcnInfo(tempSbcn);
			}
		}
		
		// 인증정보
		if (insertApalList != null) {
			// 인서트
			for(int i = 0; i < insertApalList.size(); i++) {
				Map<String, String> tempApal = insertApalList.get(i);
				tempApal.put("wkpl_id", wkplKey);
				if ("".equals(tempApal.get("apal_atht_reg_sn")) || null == tempApal.get("apal_atht_reg_sn")) {
					tempApal.put("apal_atht_reg_sn", "0");
				}
				tempApal.put("username", userInfo.getUsrId());
				
				n = service.insertWkplMgntDtlApalInfo(tempApal);
			}
		}
		
		if (updateApalList != null) {
			// 업데이트
			for (int i = 0; i < updateApalList.size(); i++) {
				Map<String, String> tempApal = updateApalList.get(i);
				tempApal.put("username", userInfo.getUsrId());
				tempApal.put("wkpl_id", wkplKey);
				n = service.insertWkplMgntDtlApalInfo(tempApal);
			}
		}
		
		if (deleteApalList != null) {
			// 삭제
			for(int i = 0; i < deleteApalList.size(); i++) {
				Map<String, String> tempApal = deleteApalList.get(i);
				n = service.deleteWkplMgntDtlApalInfo(tempApal);
			}
		}
		
		// 위치정보
		if (insertRgnList != null) {
			// 인서트
			for(int i = 0; i < insertRgnList.size(); i++) {
				Map<String, String> tempRgn = insertRgnList.get(i);
				tempRgn.put("username", userInfo.getUsrId());
				n = service.insertWkplMgntDtlRgnInfo(tempRgn);
			}
		}
		
		if (updateRgnList != null) {
			// 업데이트
			for (int i = 0; i < updateRgnList.size(); i++) {
				Map<String, String> tempRgn = updateRgnList.get(i);
				tempRgn.put("username", userInfo.getUsrId());
				n = service.insertWkplMgntDtlRgnInfo(tempRgn);
			}
		}
		
		if (deleteRgnList != null) {
			// 삭제
			for(int i = 0; i < deleteRgnList.size(); i++) {
				Map<String, String> tempRgn = deleteRgnList.get(i);
				n = service.deleteWkplMgntDtlRgnInfo(tempRgn);
			}
		}

		// 그룹정보
		if (deleteCatList != null) {
			// 삭제
			for(int i = 0; i < deleteCatList.size(); i++) {
				Map<String, String> tempCat = deleteCatList.get(i);
				tempCat.put("wkpl_id", wkplKey);
				tempCat.put("SESS_USR_ID", userInfo.getUsrId());
				tempCat.put("del_yn", "Y");
				String count = "";
				count = service.selectWkplMgntDtlCatInfo(tempCat);
				if (count != null) {
					n = service.updateWkplMgntDtlCatInfo(tempCat);
				} else {
					n = service.deleteWkplMgntDtlCatInfo(tempCat);
				}
			}
		}

		if (insertCatList != null) {
			// 인서트
			for(int i = 0; i < insertCatList.size(); i++) {
				Map<String, String> tempCat = insertCatList.get(i);
				tempCat.put("wkpl_id", wkplKey);
				tempCat.put("SESS_USR_ID", userInfo.getUsrId());
				tempCat.put("del_yn", "N");

				String count = "";
				count = service.selectWkplMgntDtlCatInfo(tempCat);
				if (count != null) {
					n = service.updateWkplMgntDtlCatInfo(tempCat);
				} else {
					n = service.insertWkplMgntDtlCatInfo(tempCat);
				}
			}
		}
		Map<String, String> resultMap = new HashMap<>();
		
		String result;
		result = (n > -1? (m > -1? (j > -1? (k > -1? (l > -1? (o > -1? Constant.SUCCESS : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL);
		resultMap.put("result", result);
		resultMap.put("key", wkplKey);
		
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
	
	// 법적인원관리 조회
	@GetMapping("/wkpl-elc-mgnt-dtl")
	public ResponseEntity<?> selectWkplElcMgntDtl(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		
		List<Map<String, ?>> elc = service.selectWkplElcMgntDtl(param);
		
		Map<String, List<Map<String, ?>>> data = new HashMap<>();
		data.put("elc", elc);
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	// 교육 이력조회
	@GetMapping("/wkpl-trn-pop")
	public ResponseEntity<?> selectWkplTrnList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_LANG", userInfo.getLangCd());
		List<Map<String, ?>> trnlist = service.selectWkplTrnList(param);
		
		return new ResponseEntity<>(trnlist, HttpStatus.OK);
	}

	// 교육 이력 삭제
	@PutMapping("/wkpl-trn-pop")
	@Transactional
	public ResponseEntity<?> deleteWkplElcTrn(@RequestBody Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_USR_ID", userInfo.getUsrId());
		String result = "";
		Map<String, String> resultMap = new HashMap<>();
		int n = 0;
		int i= 0;
		n = service.deleteWkplElcTrn2(param);
		i = service.updateWkplElcTrn2(param);
		result = (n > -1? (i > -1? Constant.SUCCESS : Constant.FAIL) : Constant.FAIL);
		resultMap.put("result", result);
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}

	// 교육 이력이수증 저장
	@PutMapping("/wkpl-trn-pop-save")
	@Transactional
	public ResponseEntity<?> insertWkplElcTrn(@RequestBody Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		String result = "";
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_USR_ID", userInfo.getUsrId());
		
		Map<String, String> resultMap = new HashMap<>();
		int n = 0;
		int m = 0;
		int l = 0;
		
		n = service.deleteWkplElcTrn(param);
		m = service.insertWkplElcTrn(param);
		
		// 교육이수증 등록여부 업데이트
		l = service.updateWkplElcTrn(param);
		
		result = (n > -1? (m > -1?(l > -1? Constant.SUCCESS : Constant.FAIL) : Constant.FAIL) : Constant.FAIL);
		resultMap.put("result", result);
		
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}	
		
	// 교육이수증 등록 요청 메일 발송
	@PutMapping("/wkpl-trn-mail")
	@Transactional
	public ResponseEntity<?> sendWkplTrnMail (@RequestBody Map<String, String> param, HttpSession session) throws Exception {
		UserInfo userInfo = sessionManager.get(session.getId());
		String[] newStr = param.get("elc_cl_nm").split("[.]");
		String elc_cl_nm = newStr[1].substring(1, newStr[1].length());
		String[] dt = param.get("trn_expr_dt").split("-");

		// 수신처 목록
		String[] to = new String[1];
		// 참조처 목록
		String[] cc = new String[1];

		String shePortalLink = frontUrl + "/stndinfr/wkplelcmgntdtledt/";

		// 수신자
		to[0] = param.get("elc_rspn_usr_id"); //선임당당자사번
		// 참조자
		cc[0] = param.get("dept_head_id"); // 소속부서 팀장
		
		// 메일 업무 코드(정의 필요)
		String bizCd = "60";
		// 메일 제목(정의 필요)
		String title = "[SHE PORTAL] '" + elc_cl_nm + "' 법정 교육 이수 확인 서류 등록 안내";
		// 메일 내용(정의 필요)
		String content = 
			util.getmailCss() +
			"<div id=\"WkplMgnt\" class=\"type1_div\" style=\"width:820px; display:table; position:relative;\">\r\n" +
			"	<table cellspacing=\"0\" cellpadding=\"0\" style=\"width:100%; \"\">\r\n" + 
			"		<thead>\r\n" +
			"			<tr style=\"background-color: #062c5e;\">\r\n" +	
			"				<td style=\"border: 0;\">\r\n" +
			"					<p style=\"height: 60px; padding-left: 15px;\">\r\n" +
			" 						<img src=\"http://localhost:8080/_nuxt/img/logo.d0672ff.png" + "\" style=\"height: 20px; margin-top: 20px;\">&nbsp;&nbsp;\r\n" +
			" 						<span style=\"color: white; font-size: 20px;\">SHE PORTAL '" + elc_cl_nm + "' 법정 교육 이수 확인 서류 등록</span>\r\n" +
			"					</p>\r\n" +
			"				</td>\r\n" +	
			"			</tr>\r\n" +	
			"		</thead>\r\n" +
			"		<tbody style=\"display:table; width:100%; border-left:1px solid #ddd; border-right:1px solid #ddd;\">\r\n" +
			"			<tr>\n" +
			"				<td style=\"font-size: 14px; line-height:1.5; padding:30px 30px 130px 30px;\">\r\n" +
			"					<p style=\"font-size: 14px; color:rgb(0,0,0);\">" +
			"						SHE Portal 에서 '" + elc_cl_nm + "' 법정 교육 이수 안내 확인 서류 등록 안내 드립니다.<br>" +
									String.valueOf(param.get("wkpl_nm")) + ", '" + elc_cl_nm + "' " + String.valueOf(param.get("elc_pric_nm")) + "님 께서는 " +  String.valueOf(dt[1]) + "월 " + String.valueOf(dt[2]) + "일까지 정기 교육을 이수하여야 합니다.<br>" +
			"						교육 이수 후 교육 이수를 증명 할 수 있는 공식 서류/자료 등 시스템 등록 바랍니다.<br><br>" +
			"						ㆍ교육구분: '" + elc_cl_nm + "' 법정 교육<br>" +
			"						ㆍ법정교육 만료일: " + String.valueOf(dt[0]) + "-" + String.valueOf(dt[1]) + "-" + String.valueOf(dt[2]) + "<br>" +
			"						<p style=\"margin-top:10px;\">\r\n" +					
			"							<a target=\"_blank\" href=\"" + shePortalLink + "\"style=\"color: rgb(0,0,255); text-decoration: underline;\" \">" + "SHE Portal Link" + "</a>\r\n" +
			"						</p>\r\n" +	
			"						<p style=\"margin-top:20px; font-size: 14px; color:rgb(6,44,94);\">* 본 메일은 법정 교육 이수 확인 서류 등록시 까지 매주 발송됩니다.</p>" +
			"					</p>\n" +
			"				</td>\n" +
			"			</tr>\n" +
			"			<tr class=\"inquiry_info\" style=\"position:absolute; bottom:90px; display:table; right:10px; z-index:10;\">\r\n" +
			"				<td style=\"border:0; width:100%; display:block; line-height:1.6;\">\r\n" +
			"					<p style=\"text-align:left;\">시스템 문의</p>\r\n" +
			" 					<p style=\"text-align:left;\">안전보건시스템 dehs (dehs@donggramisoft.co.kr)</p>\r\n" +
			"				</td>\r\n" +
			"			</tr>\r\n" +
			"		</tbody>\r\n" + 
			"		<tfoot>\r\n" +
			"			<tr style=\"height: 60px; background-color: #062c5e;\">\r\n" +	
			"				<td>\r\n" +
			"				</td>\r\n" +	
			"			</tr>\r\n" +	
			"		</tfoot>\r\n" +
			"	</table>" +
			"	<img src=\"" + frontUrl + "/images/ap_background.png\" alt=\"\" style=\"width: 250px; position:absolute; bottom:1px; right:1px;\"\">\r\n" +
			"</div>";
		
		// 메일 송신
		boolean mailResult = util.sendMail(bizCd, "AC928960", title, content, to, cc);		

		String result = "Fail";
		if(mailResult) {
			// 전송 성공시
			result = "Success";
		} else {
			logger.error("Send Mail Error");
		}
	
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	//법적인원관리 결재상신
	@Transactional
	@SuppressWarnings("unchecked")
	@PostMapping("/wkpl-trn-aprv")
	public ResponseEntity<?> aprvWkplTrn(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		List<Map<String, ?>> fileList = new ArrayList();
		List<Map<String, String>> selectList = (List<Map<String, String>>)param.get("aprvList");
		param.put("SESS_USR_ID", userInfo.getUsrId());
		param.put("SESS_LANG", userInfo.getLangCd());

		for(int i=0; i<selectList.size(); i++) {
			if (selectList.get(i).get("elc_infr_atfl_no") != null && !"".equals(selectList.get(i).get("elc_infr_atfl_no"))) {
				Map<String, String> fileParam = new HashMap<>();
				fileParam.put("id", selectList.get(i).get("elc_infr_atfl_no").toString());
				try {
					fileList.addAll(fileService.selectFileList(fileParam));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("fileList: " + fileList);
		
		Map<String, Object> aprvData = new HashMap<>();
//			결재 키 
		String aprv_no = UUID.randomUUID().toString().replace("-", "");
		
		aprvData.put("aprv_no",aprv_no);
//			결재 코드
		aprvData.put("arpv_cd","90");
//			업무 번호
		aprvData.put("biz_no",param.get("wkpl_id"));
//			상신자
		aprvData.put("aprv_usr_id",userInfo.getUsrId());
//			결재 상태 코드
		aprvData.put("aprv_stt_cd","1");
//			후행메서드	
		aprvData.put("aprv_mthd_nm","aprvElcTrnApprAf");
//			결재 비고	
		aprvData.put("aprv_note","");
//			등록자
		aprvData.put("SESS_USR_ID", userInfo.getUsrId());
		
		// 다국어 처리
		String[] mlangNo = {"LB00000909", "LB00001217", "LB00001356", "LB00001357", "LB00001358", "LB00001359", "LB00000741",
							"LB00001214", "LB00001360", "LB00001361", "LB00000701", "LB00001362", 
							"LB00001375", "LB00001363", "LB00001213", "LB00001366", "LB00000723",
							"LB00000910", "LB00001369", "LB00001214" ,"LB00001370", "LB00001371", 
							"LB00001372", "LB00001373", "LB00001374", "LB00001364", "LB00001367",
							"LB00001368", "LB00001365", "LB00000121", "LB00001376"};
		
		String langCd = userInfo.getLangCd();
		List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo), langCd);
		
//			제목
		aprvData.put("aprv_ttl",mUtil.getMLang(mlangCntn, "LB00001356"));
//			본문 html 소스
		String aprv_cntn = 
				aprvService.getaprvCSS() +
				"<div class=\"aprv_div\">\r\n" + 
				"	<p class=\"title\">" + mUtil.getMLang(mlangCntn, "LB00001356") + "</p>\r\n" + 
				"	<table class=\"txc-table\" cellspacing=\"0\" cellpadding=\"0\" border=\"1\">\r\n" + 
				"		<colgroup> \r\n" +
				"   		<col style=\"width: 40px;\"/> \r\n" +
				"   		<col style=\"width: 150px;\"/> \r\n" +
				"   		<col style=\"width: 150px;\"/> \r\n" +
				"   		<col style=\"width: 200px;\"/> \r\n" +
				"   		<col style=\"width: 80px;\"/> \r\n" +
				"   		<col style=\"width: 100px;\"/> \r\n" +
				"   		<col style=\"width: 100px;\"/> \r\n" +
				"   		<col style=\"width: 100px;\"/> \r\n" +
				"   		<col style=\"width: 100px;\"/> \r\n" +
				"   		<col style=\"width: 150px;\"/> \r\n" +
				"   		<col style=\"width: 100px;\"/> \r\n" +
				"   		<col style=\"width: 100px;\"/> \r\n" +
				"   		<col style=\"width: 100px;\"/> \r\n" +
				"   		<col style=\"width: 100px;\"/> \r\n" +
				"   		<col style=\"width: 120px;\"/> \r\n" +
				"   		<col style=\"width: 120px;\"/> \r\n" +
				"   		<col style=\"width: 150px;\"/> \r\n" +
				"   		<col style=\"width: 300px;\"/> \r\n" +
				"   		<col style=\"width: 200px;\"/> \r\n" +
				"   		<col style=\"width: 80px;\"/> \r\n" +
				"   		<col style=\"width: 100px;\"/> \r\n" +
				"   		<col style=\"width: 100px;\"/> \r\n" +
				"   		<col style=\"width: 80px;\"/> \r\n" +
				"   		<col style=\"width: 100px;\"/> \r\n" +
				"   		<col style=\"width: 150px;\"/> \r\n" +
				"   		<col style=\"width: 500px;\"/> \r\n" +
				"		</colgroup> \r\n" +
				"		<thead>\r\n" +
				"			<tr style=\"height: 30px;\">\r\n" +
				"				<td rowspan=\"2\">\r\n" +
				"					<p>No.</p>\r\n" +
				"				</td>\r\n" +
				"				<td rowspan=\"2\">\r\n" +
				"					<p>" + mUtil.getMLang(mlangCntn, "LB00000909") + "</p>\r\n" +
				"				</td>\r\n" +
				"				<td rowspan=\"2\">\r\n" +
				"					<p>" + mUtil.getMLang(mlangCntn, "LB00001217") + "</p>\r\n" + 
				"				</td>\r\n" + 
				"				<td colspan=\"2\">\r\n" + 
				"					<p>" + mUtil.getMLang(mlangCntn, "LB00001358") + "</p>\r\n" + 
				"				</td>\r\n" + 
				"				<td rowspan=\"2\">\r\n" + 
				"					<p>" + mUtil.getMLang(mlangCntn, "LB00001214") + "</p>\r\n" + 
				"				</td>\r\n" + 
				"				<td rowspan=\"2\">\r\n" + 
				"					<p>" + mUtil.getMLang(mlangCntn, "LB00001360") + "</p>\r\n" + 
				"				</td>\r\n" + 
				"				<td rowspan=\"2\">\r\n" + 
				"					<p>" + mUtil.getMLang(mlangCntn, "LB00001361") + "</p>\r\n" + 
				"				</td>\r\n" + 
				"				<td rowspan=\"2\">\r\n" + 
				"					<p>" + mUtil.getMLang(mlangCntn, "LB00000701") + "</p>\r\n" + 
				"				</td>\r\n" + 
				"				<td rowspan=\"2\">\r\n" + 
				"					<p>" + mUtil.getMLang(mlangCntn, "LB00001362") + "</p>\r\n" + 
				"				</td>\r\n" + 
				"				<td rowspan=\"2\">\r\n" + 
				"					<p>" + mUtil.getMLang(mlangCntn, "LB00001375") + "</p>\r\n" + 
				"				</td>\r\n" + 
				"				<td colspan=\"2\">\r\n" + 
				"					<p>" + mUtil.getMLang(mlangCntn, "LB00001363") + "</p>\r\n" + 
				"				</td>\r\n" + 
				"				<td rowspan=\"2\">\r\n" + 
				"					<p>" + mUtil.getMLang(mlangCntn, "LB00001213") + "</p>\r\n" + 
				"				</td>\r\n" + 
				"				<td colspan=\"2\">\r\n" + 
				"					<p>" + mUtil.getMLang(mlangCntn, "LB00001366") + "</p>\r\n" + 
				"				</td>\r\n" + 
				"				<td rowspan=\"2\">\r\n" + 
				"					<p>" + mUtil.getMLang(mlangCntn, "LB00000723") + "</p>\r\n" + 
				"				</td>\r\n" + 
				"				<td rowspan=\"2\">\r\n" + 
				"					<p>" + mUtil.getMLang(mlangCntn, "LB00000910") + "</p>\r\n" + 
				"				</td>\r\n" + 
				"				<td rowspan=\"2\">\r\n" + 
				"					<p>" + mUtil.getMLang(mlangCntn, "LB00001369") + "</p>\r\n" + 
				"				</td>\r\n" + 
				"				<td rowspan=\"2\">\r\n" + 
				"					<p>" + mUtil.getMLang(mlangCntn, "LB00001376") + "</p>\r\n" + 
				"				</td>\r\n" + 
				"				<td rowspan=\"2\">\r\n" + 
				"					<p>" + mUtil.getMLang(mlangCntn, "LB00001370") + "</p>\r\n" + 
				"				</td>\r\n" + 
				"				<td rowspan=\"2\">\r\n" + 
				"					<p>" + mUtil.getMLang(mlangCntn, "LB00001371") + "</p>\r\n" + 
				"				</td>\r\n" + 
				"				<td rowspan=\"2\">\r\n" + 
				"					<p>" + mUtil.getMLang(mlangCntn, "LB00001372") + "</p>\r\n" + 
				"				</td>\r\n" + 
				"				<td rowspan=\"2\">\r\n" + 
				"					<p>" + mUtil.getMLang(mlangCntn, "LB00001373") + "</p>\r\n" + 
				"				</td>\r\n" + 
				"				<td rowspan=\"2\">\r\n" + 
				"					<p>" + mUtil.getMLang(mlangCntn, "LB00001374") + "</p>\r\n" + 
				"				</td>\r\n" + 
				"				<td rowspan=\"2\">\r\n" + 
				"					<p>" + mUtil.getMLang(mlangCntn, "LB00000121") + "</p>\r\n" + 
				"				</td>\r\n" + 
				"			</tr>\r\n" +
				"			<tr style=\"height: 30px;\">\r\n" + 
				"				<td>\r\n" + 
				"					<p>"+mUtil.getMLang(mlangCntn, "LB00001357")+"</p>\r\n" + 
				"				</td>\r\n" + 
				"				<td>\r\n" + 
				"					<p>"+mUtil.getMLang(mlangCntn, "LB00000741")+"</p>\r\n" + 
				"				</td>\r\n" + 
				"				<td>\r\n" + 
				"					<p>"+mUtil.getMLang(mlangCntn, "LB00001364")+"</p>\r\n" + 
				"				</td>\r\n" + 
				"				<td>\r\n" + 
				"					<p>"+mUtil.getMLang(mlangCntn, "LB00001365")+"</p>\r\n" + 
				"				</td>\r\n" + 
				"				<td>\r\n" + 
				"					<p>"+mUtil.getMLang(mlangCntn, "LB00001367")+"</p>\r\n" + 
				"				</td>\r\n" + 
				"				<td>\r\n" + 
				"					<p>"+mUtil.getMLang(mlangCntn, "LB00001368")+"</p>\r\n" + 
				"				</td>\r\n" +
				"			</tr>\r\n" +
				"		</thead>\r\n";
		
				for(int i=0 ;i <selectList.size();i++) {
					String ctsv_yrs = "";
					if(!"".equals(selectList.get(i).get("ctsv_yrs")) && null != (selectList.get(i).get("ctsv_yrs"))) {
						ctsv_yrs = selectList.get(i).get("ctsv_yrs") + "년";
					} else {
						ctsv_yrs = selectList.get(i).get("ctsv_yrs");
					}
					String ctsv_mths = "";
					if(!"".equals(selectList.get(i).get("ctsv_mths")) && null != (selectList.get(i).get("ctsv_mths"))) {
						ctsv_mths = selectList.get(i).get("ctsv_mths") + "개월";
					} else {
						ctsv_mths = selectList.get(i).get("ctsv_mths");
					}
					String elc_crr_yrs = "";
					if(!"".equals(selectList.get(i).get("elc_crr_yrs")) && null != (selectList.get(i).get("elc_crr_yrs"))) {
						elc_crr_yrs = selectList.get(i).get("elc_crr_yrs") + "년";
					} else {
						elc_crr_yrs = selectList.get(i).get("elc_crr_yrs");
					}
					String elc_crr_mths = "";
					if(!"".equals(selectList.get(i).get("elc_crr_mths")) && null != (selectList.get(i).get("elc_crr_mths"))) {
						elc_crr_mths = selectList.get(i).get("elc_crr_mths") + "개월";
					} else {
						elc_crr_mths = selectList.get(i).get("elc_crr_mths");
					}
					aprv_cntn +=
						"<tbody>\r\n" + 
						"	<tr>\r\n" + 
						"		<td>\r\n" + 
						"			<p>\r\n" + 
						"				<span>"+(i+1)+"</span><br>\r\n" + 
						"			</p>\r\n" + 
						"		</td>\r\n" + 
						"		<td>\r\n" + 
						"			<p>\r\n" + 
						"				<span>"+selectList.get(i).get("cmpy_nm").toString()+"</span><br>\r\n" + 
						"			</p>\r\n" + 
						"		</td>\r\n" + 
						"		<td>\r\n" + 
						"			<p>\r\n" + 
						"				<span>"+selectList.get(i).get("wkpl_nm").toString()+"</span><br>\r\n" + 
						"			</p>\r\n" + 
						"		</td>\r\n" + 
						"		<td class=\"text_left\">\r\n" + 
						"			<p>\r\n" + 
						"				<span>"+selectList.get(i).get("elc_cl_nm").toString()+"</span><br>\r\n" + 
						"			</p>\r\n" + 
						"		<td>\r\n" + 
						"			<p>\r\n" + 
						"				<span>"+selectList.get(i).get("elc_dtl_cl_nm").toString()+"</span><br>\r\n" + 
						"			</p>\r\n" + 
						"		</td>\r\n" + 
						"		<td>\r\n" + 
						"			<p>\r\n" + 
						"				<span>"+selectList.get(i).get("ag_yn").toString()+"</span><br>\r\n" + 
						"			</p>\r\n" + 
						"		</td>\r\n" + 
						"		<td>\r\n" + 
						"			<p>\r\n" + 
						"				<span>"+selectList.get(i).get("bsns_agen_orga_nm").toString()+"</span><br>\r\n" + 
						"			</p>\r\n" + 
						"		</td>\r\n" + 
						"		<td>\r\n" + 
						"			<p>\r\n";
						if(!"".equals(selectList.get(i).get("bsns_agen_cont_rnew_dt")) && null != (selectList.get(i).get("bsns_agen_cont_rnew_dt"))) {
							aprv_cntn += "<span>"+selectList.get(i).get("bsns_agen_cont_rnew_dt").toString()+"</span><br>\r\n";
						} else {
							aprv_cntn += "<span></span><br>\r\n";
						}
					aprv_cntn += "	</p>\r\n" + 
						"		</td>\r\n" + 
						"		<td>\r\n" + 
						"			<p>\r\n" + 
						"				<span>"+selectList.get(i).get("elc_pric_nm").toString()+"</span><br>\r\n" + 
						"			</p>\r\n" + 
						"		</td>\r\n" +
						"		<td>\r\n" + 
						"			<p>\r\n" + 
						"				<span>"+selectList.get(i).get("elc_pric_dept_nm").toString()+"</span><br>\r\n" + 
						"			</p>\r\n" + 
						"		</td>\r\n"+
						"		<td>\r\n" + 
						"			<p>\r\n" + 
						"				<span>"+selectList.get(i).get("elc_pric_pstn_nm").toString()+"</span><br>\r\n" + 
						"			</p>\r\n" + 
						"		</td>\r\n" +
						"		<td>\r\n" + 
						"			<p>\r\n" + 
						"				<span>"+ctsv_yrs+"</span><br>\r\n" + 
						"			</p>\r\n" + 
						"		</td>\r\n" +
						"		<td>\r\n" + 
						"			<p>\r\n" + 
						"				<span>"+ctsv_mths+"</span><br>\r\n" + 
						"			</p>\r\n" + 
						"		</td>\r\n" +
						"		<td>\r\n" + 
						"			<p>\r\n" + 
						"				<span>"+selectList.get(i).get("wkpl_elc_dt").toString()+"</span><br>\r\n" + 
						"			</p>\r\n" + 
						"		</td>\r\n" +
						"		<td>\r\n" + 
						"			<p>\r\n" + 
						"				<span>"+elc_crr_yrs+"</span><br>\r\n" + 
						"			</p>\r\n" + 
						"		</td>\r\n" +
						"		<td>\r\n" + 
						"			<p>\r\n" + 
						"				<span>"+elc_crr_mths+"</span><br>\r\n" + 
						"			</p>\r\n" + 
						"		</td>\r\n" +
						"		<td>\r\n" + 
						"			<p>\r\n" + 
						"				<span>"+selectList.get(i).get("elc_pric_tel_no").toString()+"</span><br>\r\n" + 
						"			</p>\r\n" + 
						"		</td>\r\n" +
						"		<td>\r\n" + 
						"			<p>\r\n" + 
						"				<span>"+selectList.get(i).get("elc_pric_email").toString()+"</span><br>\r\n" + 
						"			</p>\r\n" + 
						"		</td>\r\n" +
						"		<td>\r\n" + 
						"			<p>\r\n" + 
						"				<span>"+selectList.get(i).get("crtf_nm").toString()+"</span><br>\r\n" + 
						"			</p>\r\n" + 
						"		</td>\r\n" +
						"		<td>\r\n" + 
						"			<p>\r\n" + 
						"				<span>"+selectList.get(i).get("mand_tlgl_trn_yn").toString()+"</span><br>\r\n" + 
						"			</p>\r\n" + 
						"		</td>\r\n" +
						"		<td>\r\n" + 
						"			<p>\r\n";
						if(!"".equals(selectList.get(i).get("fst_trn_fcst_dt")) && null != (selectList.get(i).get("fst_trn_fcst_dt"))) {
							aprv_cntn += "<span>"+selectList.get(i).get("fst_trn_fcst_dt").toString()+"</span><br>\r\n";
						} else {
							aprv_cntn += "<span></span><br>\r\n";
						}
					aprv_cntn +="	</p>\r\n" + 
						"		</td>\r\n" +
						"		<td>\r\n" + 
						"			<p>\r\n";
						if(!"".equals(selectList.get(i).get("rglr_trn_fcst_dt")) && null != (selectList.get(i).get("rglr_trn_fcst_dt"))) {
							aprv_cntn += "<span>"+selectList.get(i).get("rglr_trn_fcst_dt").toString()+"</span><br>\r\n";
						} else {
							aprv_cntn += "<span></span><br>\r\n";
						}
					aprv_cntn +="	</p>\r\n" + 
						"		</td>\r\n" +
						"		<td>\r\n" + 
						"			<p>\r\n" + 
						"				<span>"+selectList.get(i).get("elc_trn_cyc_nm").toString()+"</span><br>\r\n" + 
						"			</p>\r\n" + 
						"		</td>\r\n" +
						"		<td>\r\n" + 
						"			<p>\r\n";
						if(!"".equals(selectList.get(i).get("trn_expr_dt")) && null != (selectList.get(i).get("trn_expr_dt"))) {
							aprv_cntn += "<span>"+selectList.get(i).get("trn_expr_dt").toString()+"</span><br>\r\n";
						} else {
							aprv_cntn += "<span></span><br>\r\n";
						}
					aprv_cntn +="	</p>\r\n" + 
						"		</td>\r\n" +
						"		<td>\r\n" + 
						"			<p>\r\n" + 
						"				<span>"+selectList.get(i).get("trn_fcst_cert_reg_yn_nm").toString()+"</span><br>\r\n" + 
						"			</p>\r\n" + 
						"		</td>\r\n" +
						"		<td>\r\n" + 
						"			<p>\r\n"; 
						if(!"".equals(selectList.get(i).get("elc_infr_atfl_no")) && null != (selectList.get(i).get("elc_infr_atfl_no"))) {
							for( int j = 0; j < fileList.size() ; j++) {
							if(selectList.get(i).get("elc_infr_atfl_no").toString().equals(fileList.get(j).get("atfl_no").toString())) {
								aprv_cntn += "<span>"+ "<a href=\"" + serverUrl + "/api/v1/public/downloadSeq/" + fileList.get(j).get("key") + "\">" + fileList.get(j).get("name") + "</a>"+"</span><br>\r\n";
								} 
							}
						} 
						aprv_cntn +=
						"			</p>\r\n" + 
						"		</td>\r\n";
				}
				aprv_cntn +=		
				"	</tbody>\r\n" + 
				"	</table>\r\n" + 
				"</div>";
				aprvData.put("aprv_cntn",aprv_cntn);
		// 결제 정보 table insert 
		int n = aprvService.insertAprv(aprvData);
		
		Map<String, Object> data = new HashMap<>();
		Map<String, Object> param2 = new HashMap<>();
		if(n > -1) {	
			param2.put("aprv_no", aprv_no);
			param2.put("SESS_USR_ID", userInfo.getUsrId());
			for(int i=0; i<selectList.size(); i++) {
				String wkpl_id = selectList.get(i).get("wkpl_id");
				String elc_reg_sn = selectList.get(i).get("elc_reg_sn");
				param2.put("wkpl_id", wkpl_id);
				param2.put("elc_reg_sn", elc_reg_sn);
				service.updateWkplElcAppr(param2);
			}

			data.put("Success", "Success");
			data.put("aprvNo", aprv_no);
			data.put("usrId", userInfo.getUsrId());
		}
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
}
