package com.kist.portal.sh.trnmgnt.rest;

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
import com.kist.portal.common.comm.service.CommService;
import com.kist.portal.common.comm.service.FileService;
import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.common.utils.MultiLanguageUtil;
import com.kist.portal.sh.trnmgnt.service.TrnMgntService;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/she/trnmgnt")
public class TrnMgntController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SessionManager sessionManager;
	
	@Autowired
	private TrnMgntService service;
	
	@Value("${dgrm.front-url}")
	private String frontUrl;
	
	@Value("${dgrm.server-url}")
	private String serverUrl;
	
	@Autowired
	private MultiLanguageUtil mUtil;
	
	@Autowired
	private AprvService aprvService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private CommService commService;
	
	// 교육 관리 목록
	@GetMapping("/trn-list")
	public ResponseEntity<?> selectTrnList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		List<Map<String, ?>> list = service.selectTrnList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	// 교육 관리 상세 조회
	@GetMapping("/trn-dtl/{trnId}")
	public ResponseEntity<?> selectTrnDtl(@PathVariable String trnId, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		Map<String, String> param = new HashMap<>();
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		param.put("sess_usr_id", userInfo.getUsrId());
		param.put("trn_id", trnId);
		String result = "";
		// 권한
		List<Map<String, ?>> authData = service.selectTrnAuthChk(param);
		
		// 기본정보
		List<Map<String, ?>> basicInfo = new ArrayList();
		// 허가 정보
		List<Map<String, ?>> userList = new ArrayList();
		// 메일 정보
		List<Map<String, ?>> mailInfo = new ArrayList();
		
		// 권한
		List<Map<String, ?>> authInfo = new ArrayList();
		Map<String, String> authM = new HashMap<>();
		
		if (param.get("auth_slct") != null && "Y".equals(param.get("auth_slct"))) {
			if ("Y".equals(authData.get(0).get("auth_chk"))) {
				basicInfo = service.selectTrnDtl(param);
				userList = service.selectTrnUserList(param);
				mailInfo = service.selectTrnMailUserInfo(param);
				
				if(userList.size() > 0) {
					Map<String, String> logInfo = userInfo.getApiInfo();
					logInfo.put("result_cnt", String.valueOf(userList.size()));
					logInfo.put("result_item", String.valueOf(userList));
					
					SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
					Date dt = new Date();
					
					String dtT = formatter.format(dt);
					
					for(int i = 0; i < userList.size(); i++) {
						logInfo.put("exe_dt", dtT);
						logInfo.put("result_cnt", String.valueOf(userList.size()));
						Map<String, String> temp = new HashMap<String, String>();
						// 사번
						temp.put("trn_tgt_usr_id", String.valueOf(userList.get(i).get("trn_tgt_usr_id")));
						// 성명
						temp.put("full_trn_tgt_usr_nm", String.valueOf(userList.get(i).get("full_trn_tgt_usr_nm")));
						// 부서
						temp.put("unit_nm", String.valueOf(userList.get(i).get("unit_nm")));
						temp.put("div_nm", String.valueOf(userList.get(i).get("div_nm")));
						temp.put("team_nm", String.valueOf(userList.get(i).get("team_nm")));
						logInfo.put("result_item", String.valueOf(temp));
						
						commService.insertPrivacyLog(logInfo);
					}
				}
				
				result = "Y";
			} else {
				result = "N";
			}
		} else {
			basicInfo = service.selectTrnDtl(param);
			userList = service.selectTrnUserList(param);
			mailInfo = service.selectTrnMailUserInfo(param);
			
			if(userList.size() > 0) {
				Map<String, String> logInfo = userInfo.getApiInfo();
				logInfo.put("result_cnt", String.valueOf(userList.size()));
				logInfo.put("result_item", String.valueOf(userList));
				
				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
				Date dt = new Date();
				
				String dtT = formatter.format(dt);
				
				for(int i = 0; i < userList.size(); i++) {
					logInfo.put("exe_dt", dtT);
					logInfo.put("result_cnt", String.valueOf(userList.size()));
					Map<String, String> temp = new HashMap<String, String>();
					// 사번
					temp.put("trn_tgt_usr_id", String.valueOf(userList.get(i).get("trn_tgt_usr_id")));
					// 성명
					temp.put("full_trn_tgt_usr_nm", String.valueOf(userList.get(i).get("full_trn_tgt_usr_nm")));
					// 부서
					temp.put("unit_nm", String.valueOf(userList.get(i).get("unit_nm")));
					temp.put("div_nm", String.valueOf(userList.get(i).get("div_nm")));
					temp.put("team_nm", String.valueOf(userList.get(i).get("team_nm")));
					logInfo.put("result_item", String.valueOf(temp));
					
					commService.insertPrivacyLog(logInfo);
				}
			}
			
			result = "Y";
		}
		authM.put("auth", result);
		authInfo.add(0, authM);
		
		Map<String, List<Map<String, ?>>> data = new HashMap<>();
		data.put("basic", basicInfo);
		data.put("user", userList);
		data.put("mail", mailInfo);
		data.put("auth", authInfo);
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	// 교육 관리 상세 미이수자 조회
	@GetMapping("/trn-dtl-excel")
	public ResponseEntity<?> selectTrnDtlUnTgt(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		param.put("trn_fcst_yn_chk", "Y");
		
		// 허가 정보
		List<Map<String, ?>> userList = service.selectTrnUserList(param);
		
		Map<String, List<Map<String, ?>>> data = new HashMap<>();
		data.put("user", userList);
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	// 교육 관리 상세 삭제
	@DeleteMapping("/trn-dtl/{id}")
	@Transactional
	public ResponseEntity<?> updateTrnDtlDel(@PathVariable String id, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		Map<String, String> param = new HashMap<>();
		param.put("trn_id", id);
		param.put("username", userInfo.getUsrId());
		
		int n = 0;
		n = service.updateTrnDtlDel(param);
		
		String result;
		result = (n > -1? Constant.SUCCESS : Constant.FAIL);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// 교육 관리 상세 저장
	@PutMapping("/trn-dtl")
	@Transactional
	public ResponseEntity<?> insertTrnDtl(@RequestBody Map<String, Object> param, HttpSession session) {
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
		
		// 대상자정보
		//insert, update, delete 데이터 초기화
		List<Map<String, String>> insertList = (List<Map<String, String>>)param.get("insertRow");
		List<Map<String, String>> updateList = (List<Map<String, String>>)param.get("updateRow");
		List<Map<String, String>> deleteList = (List<Map<String, String>>)param.get("deleteRow");
		
		Map<String, String> tempInfo = infoRow.get(0);
		// 키 채번
		String key = "";
		if (!"".equals(tempInfo.get("trn_id")) && null != tempInfo.get("trn_id")) {
			key = tempInfo.get("trn_id");
		} else {
			key = service.selectTrnKey(temp);
		}
		
		// 기본정보 저장
		tempInfo.put("trn_id", key);
		tempInfo.put("username", userInfo.getUsrId());
		n = service.insertTrnDtl(tempInfo);
		
		// 대상자 정보
		if (deleteList != null) {
			// 삭제
			for(int i = 0; i < deleteList.size(); i++) {
				Map<String, String> tempList = deleteList.get(i);
				j = service.deleteTrnUserDtl(tempList);
			}
		}
		if (insertList != null) {
			// 인서트
			for(int i = 0; i < insertList.size(); i++) {
				Map<String, String> tempList = insertList.get(i);
				if (tempList.get("trn_tgt_usr_id") == null || "".equals(tempList.get("trn_tgt_usr_id"))) {
					String empId = service.selectTrnUserKey(temp);
					tempList.put("trn_tgt_usr_id", empId);
				}
				tempList.put("trn_id", key);
				tempList.put("wkpl_id", tempInfo.get("wkpl_id"));
				tempList.put("username", userInfo.getUsrId());
				n = service.insertTrnUserDtl(tempList);
			}
		}
		
		if (updateList != null) {
			// 업데이트
			for (int i = 0; i < updateList.size(); i++) {
				Map<String, String> tempList = updateList.get(i);
				tempList.put("trn_id", key);
				tempList.put("wkpl_id", tempInfo.get("wkpl_id"));
				tempList.put("username", userInfo.getUsrId());
				n = service.insertTrnUserDtl(tempList);
			}
		}
		
		n = service.updateTrnDtlCnt(tempInfo);
		
		Map<String, String> resultMap = new HashMap<>();
		
		String result;
		result = (n > -1? (m > -1? (j > -1? (k > -1? (l > -1? (o > -1? Constant.SUCCESS : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL);
		resultMap.put("result", result);
		resultMap.put("key", key);
		
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
	
	// 교육 관리 결재상신
	@PutMapping("/trn-dtl-appr")
	@Transactional
	public ResponseEntity<?> updateTrnAppr(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		Map<String, String> temp = new HashMap<>();
		
		int n = 0;
		
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
		
		List<Map<String, String>> infoRow = (List<Map<String, String>>)param.get("infoRow");
		Map<String, String> schParam = infoRow.get(0);
	
		schParam.put("SESS_LANG", langCd);
		schParam.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		
		// 기본정보
		List<Map<String, ?>> basicInfo = service.selectTrnDtl(schParam);
		// 대상자정보
		List<Map<String, ?>> userList = service.selectTrnUserList(schParam);
		
		Map<String, ?> tempBasic = basicInfo.get(0);
		
		List<Map<String, ?>> fileList = new ArrayList();
		
		if (tempBasic.get("trn_atfl_no") != null && !"".equals(tempBasic.get("trn_atfl_no"))) {
			Map<String, String> fileParam = new HashMap<>();
			fileParam.put("id", tempBasic.get("trn_atfl_no").toString());
			try {
				fileList = fileService.selectFileList(fileParam);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// 대상자 html 태그
		String subTagUser = "";
		
		// 대상자 html 태그
		for(int i=0; i<userList.size(); i++) {
			subTagUser += "            <tr>\r\n" + 
					      "                <td style=\"width: 5%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + (i+1) + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + userList.get(i).get("trn_tgt_usr_id") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + userList.get(i).get("trn_tgt_usr_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					      "                    <p>" + userList.get(i).get("unit_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + userList.get(i).get("div_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + userList.get(i).get("team_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" +
					      "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + userList.get(i).get("trn_fcst_cl_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" +
					      "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + userList.get(i).get("trn_fcst_yn") + "<br></p>\r\n" + 
					      "                </td>\r\n" +
					      "            </tr>\r\n";
		}
		
		// 첨부파일 html 태그
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
		
		// 페이지 구분
		// 1-규제 검토 조회 상세_원료, 2-규제 검토 상세 MRO
		String sub_title = "";
		// 결제 제목 - 화학물질 규제 검토(자재코드 자재명)
		String aprv_ttl = "";
		// 결제 내용
		String aprv_cntn = "";
				
		
		// 결제 제목 - 교육명
		aprv_ttl = tempBasic.get("trn_nm").toString();
		// 결제 내용
		aprv_cntn = "<div>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "기본정보" + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "사업장" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempBasic.get("wkpl_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "구분" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempBasic.get("trn_cl_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "등록자" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempBasic.get("lsch_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 				
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "교육/훈련명" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"5\" style=\"width: 58%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempBasic.get("trn_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "강사명" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempBasic.get("trn_techer_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "일시" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"5\" style=\"width: 58%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempBasic.get("trn_dt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "시간" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempBasic.get("trn_time_txt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "내용" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"8\" style=\"width: 88%; height: 72px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempBasic.get("trn_note_txt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 				
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "이수실적 (이수자/대상자)" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"8\" style=\"width: 88%; height: 72px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempBasic.get("trn_fcst_prsn_cnt") + " / " + tempBasic.get("trn_tgt_prsn_cnt") + "명"  + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 				
				"        </tbody>\r\n" + 
				"    </table>\r\n";

				
//				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
//				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "대상자" + "</p>\r\n" + 
//				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
//				"        <tbody>\r\n" + 
//				"            <tr>\r\n" + 
//				"                <td style=\"width: 3%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
//				"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
//				"                </td>\r\n" + 
//				"                <td style=\"width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
//				"                    <p style=\"font-weight: bold;\">" + "사번" + "<br></p>\r\n" + 
//				"                </td>\r\n" + 
//				"                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
//				"                    <p style=\"font-weight: bold;\">" + "성명" + "</p>\r\n" + 
//				"                </td>\r\n" + 
//				"                <td style=\"width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
//				"                    <p style=\"font-weight: bold;\">" + "Unit" + "<br></p>\r\n" + 
//				"                </td>\r\n" + 
//				"                <td style=\"width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
//				"                    <p style=\"font-weight: bold;\">" + "Div." + "<br></p>\r\n" + 
//				"                </td>\r\n" + 
//				"                <td style=\"width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
//				"                    <p style=\"font-weight: bold;\">" + "팀명" + "<br></p>\r\n" + 
//				"                </td>\r\n" + 
//				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
//				"                    <p style=\"font-weight: bold;\">" + "이수구분" + "<br></p>\r\n" + 
//				"                </td>\r\n" + 
//				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
//				"                    <p style=\"font-weight: bold;\">" + "이수여부" + "<br></p>\r\n" + 
//				"                </td>\r\n" + 
//				"            </tr>\r\n" +
//				subTagUser + 
//				"        </tbody>\r\n" + 
//				"    </table>\r\n" + 

				if (fileList.size() > 0) {
					aprv_cntn = aprv_cntn +
							"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
							"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "첨부파일" + "</p>\r\n" + 
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
				aprv_cntn = aprv_cntn +
						"    <p>\r\n" + 
						"        <spam style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "바로가기" + " :&nbsp;</spam><a href=\"" + frontUrl + "/trnmgnt/trnrsltmgntdtllup" + "/" + tempBasic.get("trn_id") + "\" style=\"font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"target=\"_blank\">" + frontUrl + "/trnmgnt/trnrsltmgntdtllup" + "/" + tempBasic.get("trn_id") + "</a>\r\n" + 
						"    </p>\r\n" +
				"</div>";
		
		Map<String, Object> aprvParam = new HashMap<>();
//				결재 키 
		String aprv_no = UUID.randomUUID().toString().replace("-", "");
		aprvParam.put("aprv_no", aprv_no);
//				결재 코드
		aprvParam.put("arpv_cd", "40");		
//		 		업무번호
		aprvParam.put("biz_no", tempBasic.get("trn_id").toString());
//				상신자
		aprvParam.put("aprv_usr_id", usrId);
//				결재 상태 코드 - 결재상신
		aprvParam.put("aprv_stt_cd", "1");		
//				후행메서드	
		aprvParam.put("aprv_mthd_nm", "aprvTrnApprAf");
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
			schParam.put("trn_aprv_no", aprv_no);
			schParam.put("username", userInfo.getUsrId());
			n = service.updateTrnAppr(schParam);
			
			data.put("Success", "Success");
			data.put("aprvNo", aprv_no);
			data.put("usrId", usrId);
		}
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
}
