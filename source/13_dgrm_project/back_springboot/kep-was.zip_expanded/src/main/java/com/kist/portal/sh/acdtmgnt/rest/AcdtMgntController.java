package com.kist.portal.sh.acdtmgnt.rest;

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
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.kist.portal.sh.acdtmgnt.service.AcdtMgntService;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/she/sfty")
public class AcdtMgntController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SessionManager sessionManager;
	
	@Value("${dgrm.front-url}")
	private String frontUrl;
	
	@Value("${dgrm.server-url}")
	private String serverUrl;
	
	@Autowired
	private MultiLanguageUtil mUtil;
	
	@Autowired
	private AprvService aprvService;
	
	@Autowired
	private AcdtMgntService service;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private SendEmailUtil sendEmailUtil;
	
	// 사고보고 목록 조회
	@GetMapping("/acdtmgnt")
	public ResponseEntity<?> selectAcdtMgntList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_DEPT", userInfo.getDeptCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		List<Map<String, ?>> list = service.selectAcdtMgntList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	// 사고보고 상세조회
	@GetMapping("/acdtmgnt/{id}")
	public ResponseEntity<?> selectAcdtMgnt(@PathVariable String id, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		Map<String, Object> param = new HashMap<>();
		param.put("acdt_id", id);
		param.put("SESS_LANG", userInfo.getLangCd());

		Map<String, ?> map = service.selectAcdtMgnt(param);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@PostMapping("/acdtmgnt")
	@Transactional
	public ResponseEntity<?> insertAcdtMgnt(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_USR_ID", userInfo.getUsrId());
		
		int n = -1;
		if("".equals(param.get("acdt_id"))) {
			//최초등록
			n = service.insertAcdtMgnt(param);
		} else {
			// 수정 or 결제
			if("appr".equals(param.get("type"))) {
				return this.updateAppr(param, session);
			} else {
				n = service.updateAcdtMgnt(param);
			}
		}
		
		String result = n > -1? (String) param.get("acdt_id") : Constant.FAIL;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}	
	
	@DeleteMapping("/acdtmgnt/{id}")
	public ResponseEntity<?> deleteAcdtMgnt(@PathVariable String id , HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		Map<String, Object> param = new HashMap<>();
		param.put("acdt_id", id);
		param.put("SESS_USR_ID", userInfo.getUsrId());
		
		int n = service.deleteAcdtMgnt(param);
		
		String result = (n > -1?  Constant.SUCCESS : Constant.FAIL);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PutMapping("/acdtmgnt/{id}")
	public ResponseEntity<?> delAcdt(@PathVariable String id , HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		Map<String, String> param = new HashMap<>();
		param.put("acdt_id", id);
		param.put("username", userInfo.getUsrId());
		
		int n = 0;
		n = service.delAcdt(param);
		
		String result;
		result = (n > -1? Constant.SUCCESS : Constant.FAIL);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// 사고속보 메일 발송
	@PostMapping("/acdtmgnt/report")
	@Transactional
	public ResponseEntity<?> sendAcdtReportMail(@RequestBody Map<String, Object> param, HttpSession session) {
				
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_USR_ID", userInfo.getUsrId());
		
		// 메일 수신처 목록 만들기
		List<String> mailToList = new ArrayList<>();	//  수신처 목록
		List<String> mailCcList = new ArrayList<>();	//  참조처 목록
		List<Map<String, ?>> mailRecvList = service.selectAcdtRecvList(param);	// 사고속보 메일 수신처 전체 목록 조회
		
		// 수신자 사이즈가 0일 경우 메일 발송 X
		if(mailRecvList.size() == 0) {
			return new ResponseEntity<>("MAILRECVNOTDATA", HttpStatus.OK);
		}
		
		for(int i = 0; i < mailRecvList.size(); i++) {
			Map<String, ?> mailRecvInfo = mailRecvList.get(i);

			if("D".equals(mailRecvInfo.get("eml_dstn_cl_cd"))) {
				// 수신처 구분이 부서일때 해당 부서의 부서원 조회
				Map<String, String> deptParam = new HashMap<>();
				deptParam.put("cmpy_cd", (String) param.get("cmpy_cd"));	// 회사코드
				deptParam.put("dept_cd", (String) mailRecvInfo.get("eml_dstn_id"));	// 수신처(부서코드)
				List<Map<String, ?>> deptUserList = service.selectDeptMental(deptParam);	// 해당 부서코드의 직원 조회
				for(int j = 0; j < deptUserList.size(); j++) {
					Map<String, ?> deptUserInfo = deptUserList.get(j);

					if ("TO".equals(mailRecvInfo.get("eml_rcv_tp_cd").toString())) {
						// 구분이 수신일때 수신처 목록에 넣음
						mailToList.add(deptUserInfo.get("usr_id").toString());
					} else {
						// 구분이 참조일때 참조처 목록에 넣음
						mailCcList.add(deptUserInfo.get("usr_id").toString());
					}
				}
			} else {
				// 수신처 구분이 인원일때
				if ("TO".equals(mailRecvInfo.get("eml_rcv_tp_cd").toString())) {					
					// 구분이 수신일때 수신처 목록에 넣음
					mailToList.add(mailRecvInfo.get("eml_dstn_id").toString());
				} else {
					// 구분이 참조일때 참조처 목록에 넣음
					mailCcList.add(mailRecvInfo.get("eml_dstn_id").toString());
				}
			}
		}
		
		// 메일 업무 코드
		String bizCd = "41";
		// 메일 제목
		String title = "[SHE Portal] 사고속보 알림";
		// 메일 내용
		String content = this.getAcdtReportHtml("mail", param);
		// 수신자
		String[] to = new String[mailToList.size()];	
		for(int i = 0; i < mailToList.size(); i++) {
			to[i] = mailToList.get(i);
		}
		
		// 참조자
		String[] cc = new String[mailCcList.size()];
		for(int i = 0; i < mailCcList.size(); i++) {			
			cc[i] = mailCcList.get(i);			
		}
		
		// 메일 송신 
		Boolean mailResult = false;
				
		if(mailCcList.size() == 0) {
			// 참조자가 없는경우
			mailResult = sendEmailUtil.sendMail(bizCd, userInfo.getUsrId(), title, content, to);
		} else {
			// 참조자가 있는경우
			mailResult = sendEmailUtil.sendMail(bizCd, userInfo.getUsrId(), title, content, to, cc);
		}

		String result = (mailResult ? Constant.SUCCESS : Constant.FAIL);
		
		// 메일전송 성공시
		if(result.equals(Constant.SUCCESS)) {
			// 사고속보 발송 'Y'
			service.updateAcdtMailSendingStatus(param);
		}
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// 사고속보 본문 내용 가져오기	
	@GetMapping("/acdtmgnt/report/{id}")
	public ResponseEntity<?> selectAcdtReportHtml(@PathVariable String id, @RequestParam Map<String, Object> params, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		Map<String, Object> param = new HashMap<>();
		
		if(!"".equals(params) || params != null) {			
			param.put("dfdt_sn", params.get("dfdt_sn"));
		}
		param.put("acdt_id", id);
		param.put("SESS_LANG", userInfo.getLangCd());		
		
		// 메일 내용
		String content = this.getAcdtReportHtml("popup", param);		
		return new ResponseEntity<>(content, HttpStatus.OK);
	}
		
	// 사고속보 본문 내용 만들기
	private String getAcdtReportHtml(String callType, Map<String, Object> param) {		
		List<Map<String, ?>> acdtReportContent = service.selectAcdtReport(param);	//사고속보리포트 본문내용	
		List<Map<String, ?>> acdtRpUnBhcContent = service.selectAcdtRpUnBhc(param);	//불안전한행동내용4개
		List<Map<String, ?>> acdtRpUnStCnContent = service.selectAcdtRpUnStCn(param);	//사고불안전상태내용4개
		
		Map<String, ?> acdtContent = service.selectAcdtMgnt(param);	// 사고보고조사 상세 조회
		List<Map<String, ?>> occur_atfl_list = (List<Map<String, ?>>) acdtContent.get("occur_atfl_list");	//사고발생현장 사진 조회
		List<Map<String, ?>> reenact_atfl_list = (List<Map<String, ?>>) acdtContent.get("reenact_atfl_list");	//사고재현 사진 조회
				
		Map<String, ?> tempOccurReq = new HashMap<>();
		Map<String, ?> tempReenactReq = new HashMap<>();
		
		String tempOccurReqText = "";
		String tempReenactReqText = "";
		// 사고발생현장 사진
		if(occur_atfl_list.size() > 0) {
			tempOccurReq = occur_atfl_list.get(0);
			if(!"".equals(tempOccurReq.get("acdt_atfl_no")) && null != tempOccurReq.get("acdt_atfl_no")) {
				tempOccurReqText = "<img src=\"" + serverUrl + "/api/v1/public/download/" + tempOccurReq.get("acdt_atfl_no") + "\" style=\"width:100%\">\r\n";
			}
		}
		
		// 재현 사진
		if(reenact_atfl_list.size() > 0) {
			tempReenactReq = reenact_atfl_list.get(0);

			if(!"".equals(tempReenactReq.get("acdt_atfl_no")) && null != tempReenactReq.get("acdt_atfl_no")) {
				tempReenactReqText = "<img src=\"" + serverUrl + "/api/v1/public/download/" + tempReenactReq.get("acdt_atfl_no") + "\" style=\"width:100%\">\r\n";
			}
		}
		
		Map<String, Object> acdtReportInfo = new HashMap<String, Object>();
		if(acdtReportContent.size() > 0) {
			acdtReportInfo = (Map<String, Object>)acdtReportContent.get(0);
		}
		
		// 사고 속보 내용 html 태그
		String content = "";
		// false(팝업창에서는 주소, 안전보건포털 바로가기x), true(메일발송시 url 주소, 안전보건포털 바로가기o)  
		if ("popup".equals(callType)) {
			// 팝업창에서 호출할때			  
			content += 
					"<div style=\" border-top: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 0.5px solid rgb(204, 204, 204);\">\r\n" +
					"<p style=\"font-weight: bold; margin-bottom: 0px; background-color: #062c5e; height: 50px; padding: 10px 0px 0px 10px;\"><span style=\"font-weight: bold; font-size: 20px; color: white; \">사고 속보</span></p>" +
					"<table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
					"	<colgroup> \n" +
					"   	<col style=\"width: 11%;\"/> \n" +
					"       <col style=\"width: 20%;\"/> \n" +
					"       <col style=\"width: 11%;\"/> \n" +
					"       <col style=\"width: 20%;\"/> \n" +
					"       <col style=\"width: 11%;\"/> \n" +
					"       <col style=\"width: 8%;\"/> \n" +
					"       <col style=\"width: 11%;\"/> \n" +
					"       <col style=\"width: 8%;\"/> \n" +
					"   </colgroup> \n" +
					"	<tbody>\r\n" +
					"     	<tr style=\"height: 40px;\">\r\n" +
					"       	<td colspan=\"1\" style=\"border-width: 0.5px 0.5px 0.5px 0px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); \">\r\n" + 
					"           	<p style=\"font-weight: bold; margin-bottom: 0px; text-align: center;\">" + "사고구분" + "<br></p>\r\n" + 
					"           </td>\r\n" + 	
					"           <td colspan=\"1\" style=\"border-bottom: 0.5px solid rgb(204, 204, 204); border-right: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px; \">\r\n" + 
					"           	<p style=\"margin-bottom: 0px; text-align: center;\">" + acdtReportInfo.get("acdt_cl_dtl_nm") + "<br></p>\r\n" + 
					"           </td>\r\n" + 																				
					"           <td colspan=\"1\" style=\"border-bottom: 0.5px solid rgb(204, 204, 204); border-right: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); \">\r\n" + 
					"           	<p style=\"font-weight: bold; margin-bottom: 0px; text-align: center;\">" + "사고유형" + "<br></p>\r\n" + 
					"           </td>\r\n" + 
					"           <td colspan=\"1\" style=\"border-bottom: 0.5px solid rgb(204, 204, 204); border-right: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px; \">\r\n" + 
					"           	<p style=\"margin-bottom: 0px; text-align: center;\">" + acdtReportInfo.get("acdt_tp_nm") + "<br></p>\r\n" + 
					"           </td>\r\n" + 	
					"           <td colspan=\"1\" style=\"border-bottom: 0.5px solid rgb(204, 204, 204); border-right: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); \">\r\n" + 
					"           	<p style=\"font-weight: bold; margin-bottom: 0px; text-align: center;\">" + "사고위치" + "<br></p>\r\n" + 
					"           </td>\r\n" + 
					"           <td colspan=\"4\" style=\"border-bottom: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px; \">\r\n" + 
					"           	<p style=\"margin-bottom: 0px; text-align: center;\">" + acdtReportInfo.get("acdt_wkpl_loc_all") + "<br></p>\r\n" + 
					"           </td>\r\n" +
					"       </tr>\r\n" +
					"     	<tr style=\"height: 40px;\">\r\n" +						
					"       	<td colspan=\"1\" style=\"border-width: 0.5px 0.5px 0.5px 0px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); \">\r\n" + 
					"       		<p style=\"font-weight: bold; margin-bottom: 0px; text-align: center;\">" + "사고일시" + "<br></p>\r\n" + 
					"       	</td>\r\n" +	
					"           <td colspan=\"1\" style=\"border-bottom: 0.5px solid rgb(204, 204, 204); border-right: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px; \">\r\n" + 
					"           	<p style=\"margin-bottom: 0px; text-align: center;\">" + acdtReportInfo.get("acdt_occr_all") + "<br></p>\r\n" + 
					"           </td>\r\n" +				
					"           <td colspan=\"1\" style=\"border-bottom: 0.5px solid rgb(204, 204, 204); border-right: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); \">\r\n" + 
					"           	<p style=\"font-weight: bold; margin-bottom: 0px; text-align: center;\">" + "사고발생부서" + "<br></p>\r\n" + 
					"          	</td>\r\n" +
					"           <td colspan=\"1\" style=\"border-bottom: 0.5px solid rgb(204, 204, 204); border-right: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px; \">\r\n" + 
					"           	<p style=\"margin-bottom: 0px; text-align: center;\">" + acdtReportInfo.get("kor_acdt_occr_org_nm")  + "<br></p>\r\n" + 
					"           </td>\r\n" +					
					"           <td colspan=\"1\" style=\"border-bottom: 0.5px solid rgb(204, 204, 204); border-right: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); \">\r\n" + 
					"           	<p style=\"font-weight: bold; margin-bottom: 0px; text-align: center;\">" + "업무상 사고" + "<br></p>\r\n" + 
					"          	</td>\r\n" +								
					"           <td colspan=\"1\" style=\"border-bottom: 0.5px solid rgb(204, 204, 204); border-right: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px; \">\r\n" + 
					"           	<p style=\"margin-bottom: 0px; text-align: center;\">" + acdtReportInfo.get("bsns_rel_acdt_yn") + "<br></p>\r\n" + 
					"           </td>\r\n" +				
					"           <td colspan=\"1\" style=\"border-bottom: 0.5px solid rgb(204, 204, 204); border-right: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); \">\r\n" + 
					"           	<p style=\"font-weight: bold; margin-bottom: 0px; text-align: center;\">" + "산업재해" + "<br></p>\r\n" + 
					"           </td>\r\n" +
					"           <td colspan=\"1\" style=\"border-bottom: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px; \">\r\n" + 
					"           	<p style=\"margin-bottom: 0px; text-align: center;\">" + acdtReportInfo.get("inac_ivst_sbms_tgt_yn") + "<br></p>\r\n" + 
					"           </td>\r\n" +
					"		</tr>\r\n" +	
					"     	<tr style=\"height: 40px;\">\r\n" +	
					"       	<td colspan=\"1\" style=\"border-width: 0.5px 0.5px 0.5px 0px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); \">\r\n" + 
					"       		<p style=\"margin-bottom: 0px; text-align: center;\">" + "사고자 정보" + "<br></p>\r\n" + 
					"       	</td>\r\n" +
					"           <td colspan=\"7\" style=\"border-bottom: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px; \">\r\n" + 
					"           	<p style=\"margin-bottom: 0px;\">" + acdtReportInfo.get("acdpr_infrm") + "<br></p>\r\n" + 
					"           </td>\r\n" +	
					"      	</tr>\r\n" +				
					"    </tbody>\r\n" + 
					"</table>\r\n" + 					
					"<p style=\"margin-bottom: 0px; font-size: 13.3333px;\">본 사고 속보는 사고 발생시 초기 전파 및 보고를 위한 임시 보고서 입니다. 정식 사고보고서는 사고 조사 이후 SHE Portal 시스템에 등록 됩니다.</p>\r\n" +					
					"<br/>\r\n" +
					"<table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" +
					"	<tbody>\r\n" +
					"       <tr style=\"display: table; height: 100%; width: 100%;\">\r\n" +
					"       	<td style=\"width: 50%; padding: 0px 10px 0px 0px; display: table-cell; vertical-align: top;\">\r\n" + 
//					"           	<p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" +
					"				<p style=\"font-weight: bold; font-size: medium; margin-bottom: 0px;\">1. 사고발생 현황</p>\r\n" +
					"				<table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px; height: calc(100% - 25.99px);\">\r\n" +
					"					<colgroup> \n" +
					"   					<col style=\"width: 22%;\"/> \n" +
					"   			    	<col style=\"width: auto;\"/> \n" +
					"   				</colgroup> \n" +
					"   				<tbody>\r\n" +
					"       				<tr>\r\n" +
					"							<td style=\"height: 70px; border-width: 0.5px 0.5px 0.5px 0px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" +					
					"       						<p style=\"font-weight: bold; margin-bottom: 0px;\">" + "[사고발생]" + "<br>" + acdtReportInfo.get("occr_hour_min") + "</p>\r\n" +			
					"           				</td>\r\n" +
					"							<td style=\"border-bottom: 0.5px solid rgb(204, 204, 204); border-right: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" +
					"           					<p style=\"margin-bottom: 0px;white-space:pre-wrap; \">" + acdtReportInfo.get("acdt_occr_txt") + "<br></p>\r\n" +
					"       					</td>\r\n" +
					"       				</tr>\r\n" +
					"       				<tr>\r\n" +
					"							<td style=\"height: 70px; border-width: 0.5px 0.5px 0.5px 0px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" +					
					"       						<p style=\"font-weight: bold; margin-bottom: 0px;\">" + "[사고접수]" + "<br>" + acdtReportInfo.get("accp_hour_min") +"</p>\r\n" + 			
					"           				</td>\r\n" +
					"							<td style=\"border-bottom: 0.5px solid rgb(204, 204, 204); border-right: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" +
					"           					<p style=\"margin-bottom: 0px;white-space:pre-wrap; \">" + acdtReportInfo.get("acdt_accp_txt") + "<br></p>\r\n" + 
					"       					</td>\r\n" +
					"       				</tr>\r\n" +
					"       				<tr>\r\n" +
					"							<td style=\"height: 70px; border-width: 0.5px 0.5px 0.5px 0px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" +					
					"       						<p style=\"font-weight: bold; margin-bottom: 0px;\">" + "[초기대응]" + "<br>" + acdtReportInfo.get("bgng_rpos_hour_min") + "</p>\r\n" + 			
					"           				</td>\r\n" +
					"							<td style=\"border-bottom: 0.5px solid rgb(204, 204, 204); border-right: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" +
					"           					<p style=\"margin-bottom: 0px;white-space:pre-wrap; \">" + acdtReportInfo.get("acdt_bgng_rpos_txt") + "<br></p>\r\n" +
					"       					</td>\r\n" +
					"       				</tr>\r\n" +
					"       				<tr>\r\n" +
					"							<td style=\"height: 70px; border-width: 0.5px 0.5px 0.5px 0px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" +					
					"       						<p style=\"font-weight: bold; margin-bottom: 0px;\">" + "[사후조치]" + "<br>" + acdtReportInfo.get("af_actn_hour_min") + "</p>\r\n" + 
					"           				</td>\r\n" +
					"							<td style=\"border-bottom: 0.5px solid rgb(204, 204, 204); border-right: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" +
					"           					<p style=\"margin-bottom: 0px; white-space:pre-wrap; \">" + acdtReportInfo.get("acdt_af_actn_txt") + "<br></p>\r\n" + 
					"       					</td>\r\n" +
					"       				</tr>\r\n" +
					"       			</tbody>\r\n" +
					"   			</table>\r\n" +
					"       	</td>\r\n" +
					"			<td style=\"width: 50%; padding: 0px 0px 0px 10px; display: table-cell; vertical-align: top;\">\r\n" +
//					"           	<p style=\"font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" +
					"				<p style=\"font-weight: bold; font-size: medium; margin-bottom: 0px;\">3. 사고발생 원인</p>\r\n" +
					"				<table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px; height: calc(100% - 25.99px);\">\r\n" +
					"					<colgroup> \n" +
					"   					<col style=\"width: 22%;\"/> \n" +
					"   			    	<col style=\"width: auto;\"/> \n" +
					"   				</colgroup> \n" +
					"   				<tbody>\r\n" +
					"       				<tr>\r\n" +
					"							<td style=\"height: 93px; border-width: 0.5px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" +
					"								<p style=\"font-weight: bold; margin-bottom: 0px;\">불안전한 행동<br></p>\r\n" +
					"       					</td>\r\n" +
					" 							<td style=\"border-width: 0.5px 0px 0.5px 0.5px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; \">\r\n";
												for(int i=0; i < acdtRpUnBhcContent.size(); i++) {
													content +=				
					"								<p style=\"margin-bottom: 0px; white-space:pre-wrap; \">" + (i + 1) + "." + acdtRpUnBhcContent.get(i).get("cd_nm") + "</p>\r\n" ; 
												}				
												content +=
					"       					</td>\r\n" +
					"       				</tr>\r\n" +
					"       				<tr>\r\n" +
					"							<td style=\"height: 93px; border-width: 0.5px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center; \">\r\n" +
					"								<p style=\"font-weight: bold; margin-bottom: 0px;\">불안전한상태<br></p>\r\n" +
					"       					</td>\r\n" +
					" 							<td style=\"border-width: 0.5px 0px 0.5px 0.5px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px;\">\r\n";
												for(int i=0; i < acdtRpUnStCnContent.size(); i++) {
													content +=				
					"          						<p style=\"margin-bottom: 0px; white-space:pre-wrap; \">" + (i + 1) + "." + acdtRpUnStCnContent.get(i).get("cd_nm") + "</p>\r\n"; 
												}													
												content +=
					"       					</td>\r\n" +
					"       				</tr>\r\n" +
					"       				<tr>\r\n" +		
					"							<td style=\"height: 93px; border-width: 0.5px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" +
					"								<p style=\"font-weight: bold; margin-bottom: 0px;\">구체 원인<br></p>\r\n" +						
					"       					</td>\r\n" +
					" 							<td style=\"border-width: 0.5px 0px 0.5px 0.5px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px;\">\r\n" +
					"          						<p style=\"margin-bottom: 0px; white-space:pre-wrap; \">" + acdtReportInfo.get("acdt_occr_caus_txt")  + "</p>\r\n" + 
					"       					</td>\r\n" +
					"       				</tr>\r\n" +
					"       			</tbody>\r\n" +
					"   			</table>\r\n" +
					"			</td>\r\n" +
					"       </tr>\r\n" +
					"	</tbody>\r\n" +
					"</table>\r\n" +
					"<table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" +
					"	<tbody>\r\n" +
					"   	<tr style=\"display:table; width:100%;\">\r\n" +
					"			<td style=\"display:table-cell; width: 50%; height: 24px; padding: 0px 10px 0px 0px;\">\r\n" +
//					"				<p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" +
					"				<p style=\"font-weight: bold; font-size: medium; margin-bottom: 0px;\">2. 사고발생 사진</p>\r\n" +
					"				<table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"height: calc(100% - 25.99px); border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" +
					"   				<tbody>\r\n" +
					"       				<tr>\r\n" +	
					"							<td style=\"width: 50%; height: 50px; border-width: 0.5px 0.5px 0.5px 0px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" +
					"								<p style=\"font-weight: bold; margin-bottom: 0px;\">사고 발생/현장 사진</p>\r\n" +
					"							</td>\r\n" +
					"							<td style=\"width: 50%; height: 50px; border-width: 0.5px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" +
					"								<p style=\"font-weight: bold; margin-bottom: 0px;\">사고 재현 사진</p>\r\n" +
					"							</td>\r\n" +
					"       				</tr>\r\n" +
					"       				<tr>\r\n" +	
					"							<td style=\"height: 230px; border-width: 0.5px 0.5px 0.5px 0px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px;\">\r\n" +
					"								<p style=\"margin-bottom: 0px;\">" + tempOccurReqText + "</p>\r\n" +
					"							</td>\r\n" +
					"							<td style=\"height: 230px; border-bottom: 0.5px solid rgb(204, 204, 204); border-right: 0.5px solid rgb(204, 204, 204); border-left: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" +
					"								<p style=\"margin-bottom: 0px;\">" + tempReenactReqText + "</p>\r\n" +
					"							</td>\r\n" +
					"   					</tr>\r\n" +
					"       			</tbody>\r\n" +
					"   			</table>\r\n" +							
					"			</td>\r\n" +
					"			<br/>\r\n" +	
					"			<td style=\"display:table-cell; width: 50%; height: 24px; padding: 0px 0px 0px 10px;\">\r\n" +	
//					"				<p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" +
					"				<p style=\"font-weight: bold; font-size: medium; margin-bottom: 0px;\">4. 재발방지 대책</p>\r\n" +
					"				<table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"height: calc(100% - 25.99px); border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" +
					"   				<tbody>\r\n" +
					"       				<tr>\r\n" +
					"							<td style=\"vertical-align : top; height: 140px; border-width: 0.5px 0px 0.5px 0.5px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px;\">\r\n" +
					"								<p></p>\r\n" +
					"								<span>· 사고발생부서</span>\r\n" +
					"								<p style=\"margin-bottom: 0px; white-space:pre-wrap; \">" + acdtReportInfo.get("acdt_dept_prvt_meas_txt") + "</p>\r\n" +
					"							</td>\r\n" +
					"   					</tr>\r\n" +
					"       				<tr>\r\n" +
					"							<td style=\"vertical-align : top; height: 140px; border-width: 0.5px 0px 0.5px 0.5px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px;\">\r\n" +
					"								<p></p>\r\n" +
					"								<span>· 안전관리부서</span>\r\n" +					
					"								<p style=\"margin-bottom: 0px; white-space:pre-wrap; \">" + acdtReportInfo.get("sft_dept_prvt_meas_txt") + "</p>\r\n" +
					"							</td>\r\n" +
					"   					</tr>\r\n" +
					"       			</tbody>\r\n" +
					"   			</table>\r\n" +
					"			</td>\r\n" +
					"   	</tr>\r\n" +
					"	</tbody>\r\n" +
					"</table>\r\n" +
					"</div>\r\n";
		} else {
			// 내부적으로 호출할때
			content +=
					sendEmailUtil.getmailCss() +
					"<div id=\"acdtMgnt\" style=\"border-right: 1px solid rgb(204, 204, 204); border-left: 0.5px solid rgb(204, 204, 204);\">\r\n" +
					"	<p style=\"margin-bottom: 0px; background-color: #062c5e; height: 60px; padding-left:15px; position:relative; \">\r\n" +
					" 		<img src=\"http://localhost:8080/_nuxt/img/logo.d0672ff.png" + "\" style=\"display:block; float:left; height: 20px; margin-top: 20px;\">\r\n" +
					"		<span style=\"font-size: 20px; color: white; display:block; float:left; margin-top:15px; margin-left:15px;\">D-EHS PORTAL 사고 속보</span>\r\n" +
					"		<span style=\"float: right; color: white; padding: 0px 10px 0px 0px; position:absolute; right:0; top:50%; transform:translateY(-50%);\">" + acdtReportInfo.get("author_team_name") + " </span>\r\n" +
					"	</p>" +
					"	<table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
					"		<colgroup> \r\n" +
					"   		<col style=\"width: 11%;\"/> \r\n" +
					"       	<col style=\"width: 20%;\"/> \r\n" +
					"       	<col style=\"width: 11%;\"/> \r\n" +
					"       	<col style=\"width: 20%;\"/> \r\n" +
					"       	<col style=\"width: 11%;\"/> \r\n" +
					"       	<col style=\"width: 8%; \"/> \r\n" +
					"       	<col style=\"width: 11%;\"/> \r\n" +
					"       	<col style=\"width: 8%; \"/> \r\n" +
					"		</colgroup> \r\n" +
					"    	<tbody>\r\n" +
					"     		<tr style=\"height: 40px;\">\r\n" +
					"       		<td class=\"table_subject\" colspan=\"1\" style=\"border-width: 0.5px 0.5px 0.5px 0px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); \">\r\n" + 
					"           		<p style=\"margin-bottom: 0px; text-align: center;\">" + "사고구분" + "<br></p>\r\n" + 
					"           	</td>\r\n" + 	
					"           	<td colspan=\"1\" style=\"border-bottom: 0.5px solid rgb(204, 204, 204); border-right: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px; \">\r\n" + 
					"           		<p style=\"margin-bottom: 0px; text-align: center;\">" + acdtReportInfo.get("acdt_cl_dtl_nm") + "<br></p>\r\n" + 
					"           	</td>\r\n" + 																				
					"           	<td class=\"table_subject\" colspan=\"1\" style=\"border-bottom: 0.5px solid rgb(204, 204, 204); border-right: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); \">\r\n" + 
					"           		<p style=\"margin-bottom: 0px; text-align: center;\">" + "사고유형" + "<br></p>\r\n" + 
					"           	</td>\r\n" + 
					"           	<td colspan=\"1\" style=\"border-bottom: 0.5px solid rgb(204, 204, 204); border-right: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px; \">\r\n" + 
					"           		<p style=\"margin-bottom: 0px; text-align: center;\">" + acdtReportInfo.get("acdt_tp_nm") + "<br></p>\r\n" + 
					"           	</td>\r\n" + 	
					"           	<td class=\"table_subject\" colspan=\"1\" style=\"border-bottom: 0.5px solid rgb(204, 204, 204); border-right: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); \">\r\n" + 
					"           		<p style=\"margin-bottom: 0px; text-align: center;\">" + "사고위치" + "<br></p>\r\n" + 
					"           	</td>\r\n" + 
					"           	<td colspan=\"4\" style=\"border-bottom: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px; \">\r\n" + 
					"           		<p style=\"margin-bottom: 0px; text-align: center;\">" + acdtReportInfo.get("acdt_wkpl_loc_all") + "<br></p>\r\n" + 
					"           	</td>\r\n" +
					"       	</tr>\r\n" +
					"     		<tr style=\"height: 40px;\">\r\n" +						
					"       		<td class=\"table_subject\" colspan=\"1\" style=\"border-width: 0.5px 0.5px 0.5px 0px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); \">\r\n" + 
					"       			<p style=\"margin-bottom: 0px; text-align: center;\">" + "사고일시" + "<br></p>\r\n" + 
					"       		</td>\r\n" +	
					"           	<td colspan=\"1\" style=\"border-bottom: 0.5px solid rgb(204, 204, 204); border-right: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px; \">\r\n" + 
					"           		<p style=\"margin-bottom: 0px; text-align: center;\">" + acdtReportInfo.get("acdt_occr_all") + "<br></p>\r\n" + 
					"           	</td>\r\n" +				
					"           	<td class=\"table_subject\" colspan=\"1\" style=\"border-bottom: 0.5px solid rgb(204, 204, 204); border-right: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); \">\r\n" + 
					"           		<p style=\"margin-bottom: 0px; text-align: center;\">" + "사고발생부서" + "<br></p>\r\n" + 
					"          		</td>\r\n" +
					"           	<td colspan=\"1\" style=\"border-bottom: 0.5px solid rgb(204, 204, 204); border-right: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px; \">\r\n" + 
					"           		<p style=\"margin-bottom: 0px; text-align: center;\">" + acdtReportInfo.get("kor_acdt_occr_org_nm")  + "<br></p>\r\n" + 
					"           	</td>\r\n" +					
					"           	<td class=\"table_subject\" colspan=\"1\" style=\"border-bottom: 0.5px solid rgb(204, 204, 204); border-right: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); \">\r\n" + 
					"           		<p style=\"margin-bottom: 0px; text-align: center;\">" + "업무상 사고" + "<br></p>\r\n" + 
					"          		</td>\r\n" +								
					"           	<td colspan=\"1\" style=\"border-bottom: 0.5px solid rgb(204, 204, 204); border-right: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px; \">\r\n" + 
					"           		<p style=\"margin-bottom: 0px; text-align: center;\">" + acdtReportInfo.get("bsns_rel_acdt_yn") + "<br></p>\r\n" + 
					"           	</td>\r\n" +				
					"           	<td class=\"table_subject\" colspan=\"1\" style=\"border-bottom: 0.5px solid rgb(204, 204, 204); border-right: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); \">\r\n" + 
					"           		<p style=\"margin-bottom: 0px; text-align: center;\">" + "산업재해" + "<br></p>\r\n" + 
					"           	</td>\r\n" +
					"           	<td colspan=\"1\" style=\"border-bottom: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px; \">\r\n" + 
					"           		<p style=\"margin-bottom: 0px; text-align: center;\">" + acdtReportInfo.get("inac_ivst_sbms_tgt_yn") + "<br></p>\r\n" + 
					"           	</td>\r\n" +
					"			</tr>\r\n" +	
					"     		<tr style=\"height: 40px;\">\r\n" +	
					"       		<td class=\"table_subject\" colspan=\"1\" style=\"border-width: 0.5px 0.5px 0.5px 0px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); \">\r\n" + 
					"       			<p style=\"margin-bottom: 0px; text-align: center;\">" + "사고자 정보" + "<br></p>\r\n" + 
					"       		</td>\r\n" +
					"           	<td colspan=\"5\" style=\"border-bottom: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px; \">\r\n" + 
					"           		<p style=\"margin-left:5px; margin-bottom: 0px;\">" + acdtReportInfo.get("acdpr_infrm") + "<br></p>\r\n" + 
					"           	</td>\r\n" +				
					"           	<td class=\"table_subject\" colspan=\"2\" style=\"width: 18%; height: 40px; border-left: 0.5px solid rgb(204, 204, 204); border-bottom: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px; \">\r\n" + 
					"					<p style=\"margin-bottom: 0px; text-align:center;\">\r\n" +					
					"						<a target=\"_blank\" href=\"" + frontUrl + "/acdtmgnt/acdtmgntdtllup" + "/" + (String) param.get("acdt_id") + "\" style=\"color: rgb(0,0,255); text-decoration: underline;\" \">" + "안전보건포털 바로가기" + "</a>\r\n" +
					"					</p>\r\n" +
					"           	</td>\r\n" +
					"      		</tr>\r\n" +				
					"    	</tbody>\r\n" + 
					"	</table>\r\n" + 					
					"	<p style=\"margin: 0px; font-size: 13.3333px;\">본 사고 속보는 사고 발생시 초기 전파 및 보고를 위한 임시 보고서 입니다. 정식 사고보고서는 사고 조사 이후 SHE Portal 시스템에 등록 됩니다.</p>\r\n" +					
					"	<br/>\r\n" +
					"	<table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" +
					"		<tbody>\r\n" +
					"       	<tr style=\"display: table; height: 100%; width: 100%;\">\r\n" +
					"       		<td style=\"width: 50%; padding: 0px 10px 0px 0px; display: table-cell; vertical-align: top; border:0; \">\r\n" + 
					"					<p class=\"table_tr_title\" style=\"font-weight: 600; font-size: medium;\">1. 사고발생 현황</p>\r\n" +
					"					<table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px; height: calc(100% - 25.99px);\">\r\n" +
					"						<colgroup> \n" +
					"   						<col style=\"width: 22%;\"/> \n" +
					"   			    		<col style=\"width: auto;\"/> \n" +
					"   					</colgroup> \n" +
					"   					<tbody>\r\n" +
					"       					<tr>\r\n" +
					"								<td class=\"table_subject\" style=\"height: 70px; border-width: 0.5px 0.5px 0.5px 0px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" +					
					"       							<p style=\"line-height:1.3; margin-bottom: 0px;\">" + "[사고발생]" + "<br>" + acdtReportInfo.get("occr_hour_min") + "</p>\r\n" +			
					"           					</td>\r\n" +
					"								<td style=\"border-bottom: 0.5px solid rgb(204, 204, 204); border-right: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" +
					"           						<p style=\"margin-bottom: 0px; white-space:pre-wrap; \">" + acdtReportInfo.get("acdt_occr_txt") + "</p>\r\n" +
					"       						</td>\r\n" +
					"       					</tr>\r\n" +
					"       					<tr>\r\n" +
					"								<td class=\"table_subject\" style=\"height: 70px; border-width: 0.5px 0.5px 0.5px 0px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" +					
					"       							<p style=\"line-height:1.3; margin-bottom: 0px;\">" + "[사고접수]" + "<br>" + acdtReportInfo.get("accp_hour_min") + "</p>\r\n" + 			
					"           					</td>\r\n" +
					"								<td style=\"border-bottom: 0.5px solid rgb(204, 204, 204); border-right: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" +
					"           						<p style=\"margin-bottom: 0px; white-space:pre-wrap; \">" + acdtReportInfo.get("acdt_accp_txt") + "</p>\r\n" + 
					"       						</td>\r\n" +
					"       					</tr>\r\n" +
					"       					<tr>\r\n" +
					"								<td class=\"table_subject\" style=\"height: 70px; border-width: 0.5px 0.5px 0.5px 0px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" +					
					"       							<p style=\"line-height:1.3; margin-bottom: 0px;\">" + "[초기대응]" + "<br>" + acdtReportInfo.get("bgng_rpos_hour_min")  + "</p>\r\n" + 			
					"           					</td>\r\n" +
					"								<td style=\"border-bottom: 0.5px solid rgb(204, 204, 204); border-right: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" +
					"           						<p style=\"margin-bottom: 0px; white-space:pre-wrap; \">" + acdtReportInfo.get("acdt_bgng_rpos_txt") + "</p>\r\n" +
					"       						</td>\r\n" +
					"       					</tr>\r\n" +
					"       					<tr>\r\n" +
					"								<td class=\"table_subject\" style=\"height: 70px; border-width: 0.5px 0.5px 0.5px 0px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" +					
					"       							<p style=\"line-height:1.3; margin-bottom: 0px;\">" + "[사후조치]" + "<br>" + acdtReportInfo.get("af_actn_hour_min") + "</p>\r\n" + 
					"           					</td>\r\n" +
					"								<td style=\"border-bottom: 0.5px solid rgb(204, 204, 204); border-right: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" +
					"           						<p style=\"margin-bottom: 0px; white-space:pre-wrap; \">" + acdtReportInfo.get("acdt_af_actn_txt") + "<br></p>\r\n" + 
					"       						</td>\r\n" +
					"       				</tr>\r\n" +
					"       			</tbody>\r\n" +
					"   			</table>\r\n" +
					"       	</td>\r\n" +
					"			<td style=\"width: 50%; padding: 0px 0px 0px 10px; display: table-cell; vertical-align: top; border:0; \">\r\n" +
//							"           	<p style=\"font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" +
					"				<p class=\"table_tr_title\" style=\"font-weight: 600; font-size: medium;\">3. 사고발생 원인</p>\r\n" +
					"				<table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px; height: calc(100% - 25.99px);\">\r\n" +
					"					<colgroup> \n" +
					"   					<col style=\"width: 22%;\"/> \n" +
					"   			    	<col style=\"width: auto;\"/> \n" +
					"   				</colgroup> \n" +
					"   				<tbody>\r\n" +
					"       				<tr>\r\n" +
					"							<td class=\"table_subject\" style=\"height: 93px; border-width: 0.5px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" +
					"								<p style=\"margin-bottom: 0px;\">불안전한 행동<br></p>\r\n" +
					"       					</td>\r\n" +
					" 							<td style=\"border-width: 0.5px 0px 0.5px 0.5px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; \">\r\n";
												for(int i=0; i < acdtRpUnBhcContent.size(); i++) {
													content +=				
					"								<p style=\"margin-bottom: 0px; white-space:pre-wrap; \">" + (i + 1) + "." + acdtRpUnBhcContent.get(i).get("cd_nm") + "</p>\r\n" ; 
												}				
												content +=
					"       					</td>\r\n" +
					"       				</tr>\r\n" +
					"       				<tr>\r\n" +
					"							<td class=\"table_subject\" style=\"height: 93px; border-width: 0.5px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center; \">\r\n" +
					"								<p style=\"margin-bottom: 0px;\">불안전한상태<br></p>\r\n" +
					"       					</td>\r\n" +
					" 							<td style=\"border-width: 0.5px 0px 0.5px 0.5px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px;\">\r\n";
												for(int i=0; i < acdtRpUnStCnContent.size(); i++) {
													content +=				
					"          						<p style=\"margin-bottom: 0px; white-space:pre-wrap; \">" + (i + 1) + "." + acdtRpUnStCnContent.get(i).get("cd_nm") + "</p>\r\n"; 
												}													
												content +=
					"       					</td>\r\n" +
					"       				</tr>\r\n" +
					"       				<tr>\r\n" +		
					"							<td class=\"table_subject\" style=\"height: 93px; border-width: 0.5px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" +
					"								<p style=\"margin-bottom: 0px;\">구체 원인<br></p>\r\n" +						
					"       					</td>\r\n" +
					" 							<td style=\"border-width: 0.5px 0px 0.5px 0.5px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px;\">\r\n" +
					"          						<p style=\"margin-bottom: 0px; white-space:pre-wrap; \">" + acdtReportInfo.get("acdt_occr_caus_txt")  + "</p>\r\n" + 
					"       					</td>\r\n" +
					"       				</tr>\r\n" +
					"       			</tbody>\r\n" +
					"   			</table>\r\n" +
					"			</td>\r\n" +
					"       </tr>\r\n" +
					"	</tbody>\r\n" +
					"</table>\r\n" +
					"<table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" +
					"	<tbody>\r\n" +
					"   	<tr style=\"display:table; width:100%;\">\r\n" +
					"			<td style=\"display:table-cell;  width: 50%; height: 24px; padding: 0px 10px 0px 0px; border:0;\">\r\n" +
//							"				<p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" +
					"				<p class=\"table_tr_title\" style=\"font-weight: 600; font-size: medium;\">2. 사고발생 사진</p>\r\n" +
					"				<table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"height: calc(100% - 25.99px); border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" +
					"   				<tbody>\r\n" +
					"       				<tr>\r\n" +	
					"							<td class=\"table_subject\" style=\"width: 50%; height: 50px; border-width: 0.5px 0.5px 0.5px 0px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" +
					"								<p style=\"margin-bottom: 0px;\">사고 발생/현장 사진</p>\r\n" +
					"							</td>\r\n" +
					"							<td class=\"table_subject\" style=\"width: 50%; height: 50px; border-width: 0.5px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" +
					"								<p style=\"margin-bottom: 0px;\">사고 재현 사진</p>\r\n" +
					"							</td>\r\n" +
					"       				</tr>\r\n" +
					"       				<tr>\r\n" +	
					"							<td style=\"height: 230px; border-width: 0.5px 0.5px 0.5px 0px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px;\">\r\n" +
					"								<p style=\"margin-bottom: 0px;\">" + tempOccurReqText + "</p>\r\n" +
					"							</td>\r\n" +
					"							<td style=\"height: 230px; border-bottom: 0.5px solid rgb(204, 204, 204); border-right: 0.5px solid rgb(204, 204, 204); border-left: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" +
					"								<p style=\"margin-bottom: 0px;\">" + tempReenactReqText + "</p>\r\n" +
					"							</td>\r\n" +
					"   					</tr>\r\n" +
					"       			</tbody>\r\n" +
					"   			</table>\r\n" +							
					"			</td>\r\n" +
					"			<br/>\r\n" +	
					"			<td style=\"display:table-cell;width: 50%; height: 24px; padding: 0px 0px 0px 10px; border:0;\">\r\n" +	
//							"				<p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" +
					"				<p class=\"table_tr_title\" style=\"font-weight: 600; font-size: medium;\">4. 재발방지 대책</p>\r\n" +
					"				<table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"height: calc(100% - 25.99px); border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" +
					"   				<tbody>\r\n" +
					"       				<tr>\r\n" +
					"							<td style=\"vertical-align : top; height: 140px; border-width: 0.5px 0px 0.5px 0.5px; border-style: solid; border-color: rgb(204, 204, 204); padding: 10px 3px 0;\">\r\n" +
					"								<span>· 사고발생부서</span>\r\n" +
					"								<p style=\"margin-bottom: 0px; font-family: arita-dotum !important; white-space:pre-wrap; \">" + acdtReportInfo.get("acdt_dept_prvt_meas_txt") + "</p>\r\n" +
					"							</td>\r\n" +
					"   					</tr>\r\n" +
					"       				<tr>\r\n" +
					"							<td style=\"vertical-align : top; height: 140px; border-width: 0.5px 0px 0.5px 0.5px; border-style: solid; border-color: rgb(204, 204, 204); padding: 10px 3px 0;\">\r\n" +
					"								<span>· 안전관리부서</span>\r\n" +					
					"								<p style=\"margin-bottom: 0px; font-family: arita-dotum !important; white-space:pre-wrap; \">" + acdtReportInfo.get("sft_dept_prvt_meas_txt") + "</p>\r\n" +
					"							</td>\r\n" +
					"   					</tr>\r\n" +
					"       			</tbody>\r\n" +
					"   			</table>\r\n" +
					"			</td>\r\n" +
					"   	</tr>\r\n" +
					"	</tbody>\r\n" +
					"</table>\r\n" +
					"</div>\r\n";						
		}	
		
		return content;
	}
	
	@SuppressWarnings("unchecked")
	private ResponseEntity<?> updateAppr(Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
				
		String bizNo = param.get("acdt_id").toString();
		String usrId = userInfo.getUsrId();
		param.put("SESS_USR_ID", usrId);
		param.put("SESS_LANG", userInfo.getLangCd());
		
		// 다국어 처리
		String[] mlangNo = {
				"LB00000731","LB00000021","LB00000806","LB00000809","LB00000810"
				,"LB00000831","LB00000832","LB00000833","LB00000807","LB00000834"
				,"LB00000835","LB00000836","LB00000837","LB00000838","LB00000839"
				,"LB00000840","LB00000841","LB00000842","LB00000843","LB00000844"
				,"LB00000845","LB00000846","LB00000847","LB00000106","LB00000838"
				,"LB00000741","LB00000073","LB00000701","LB00000071","LB00000849"
				,"LB00000850","LB00000851","LB00000852","LB00000853","LB00000854"
				,"LB00000855","LB00000856","LB00000123","LB00000857","LB00000858"
				,"LB00000796","LB00000859","LB00000860","LB00000121","LB00000433"
				,"LB00000684","LB00000797"};
		List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo), userInfo.getLangCd());
		
		Map<String, ?> content = service.selectAcdtMgnt(param);
		List<Map<String, ?>> oprnList = (List<Map<String, ?>>) content.get("oprn_list");	// SHAMSD_사고작업내용	
		List<Map<String, ?>> usfttxtList = (List<Map<String, ?>>) content.get("usfttxt_list");	// SHAMSD_사고불안전내용
		List<Map<String, ?>> usftstList = (List<Map<String, ?>>) content.get("usftst_list");	// SHAMSD_사고불안전상태
		List<Map<String, ?>> dfdtList = (List<Map<String, ?>>) content.get("dfdt_list");	// SHAMSM_사고자
		List<Map<String, ?>> ivstList = (List<Map<String, ?>>) content.get("ivst_list");	// SHAMSD_사고조사결과
		List<Map<String, ?>> actnList = (List<Map<String, ?>>) content.get("actn_list");	// SHAMSD_사고조치계획
		List<Map<String, ?>> atfl_list1 = (List<Map<String, ?>>) content.get("atfl_list1");	// 첨부파일정보
		List<Map<String, ?>> atfl_list2 = (List<Map<String, ?>>) content.get("atfl_list2");	// 개선결과 첨부파일정보
		List<Map<String, ?>> occur_atfl_list = (List<Map<String, ?>>) content.get("occur_atfl_list");	//사고발생현장 사진 조회
		List<Map<String, ?>> reenact_atfl_list = (List<Map<String, ?>>) content.get("reenact_atfl_list");	//사고재현 사진 조회
		
		Map<String, ?> tempBasic = atfl_list1.get(0);
		Map<String, ?> tempReq = atfl_list2.get(0);
		Map<String, ?> tempOccurReq = occur_atfl_list.get(0);
		Map<String, ?> tempReenactReq = reenact_atfl_list.get(0);
		
		List<Map<String, ?>> fileList = new ArrayList();
		List<Map<String, ?>> fileReqList = new ArrayList();
		List<Map<String, ?>> fileReqOccurList = new ArrayList();	//사고발생현장 사진
		List<Map<String, ?>> fileReqReenactList = new ArrayList();	//사고재현 사진
		
		if (tempBasic.get("acdt_atfl_no") != null && !"".equals(tempBasic.get("acdt_atfl_no"))) {
			Map<String, String> fileParam = new HashMap<>();
			fileParam.put("id", tempBasic.get("acdt_atfl_no").toString());
			try {
				fileList = fileService.selectFileList(fileParam);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if (tempReq.get("acdt_atfl_no") != null && !"".equals(tempReq.get("acdt_atfl_no"))) {
			Map<String, String> fileParam = new HashMap<>();
			fileParam.put("id", tempReq.get("acdt_atfl_no").toString());
			try {
				fileReqList = fileService.selectFileList(fileParam);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//사고발생현장 사진 조회
		if (tempOccurReq.get("acdt_atfl_no") != null && !"".equals(tempOccurReq.get("acdt_atfl_no"))) {
			Map<String, String> fileParam = new HashMap<>();
			fileParam.put("id", tempOccurReq.get("acdt_atfl_no").toString());
			try {
				fileReqOccurList = fileService.selectFileList(fileParam);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		//사고재현 사진 조회
		if (tempReenactReq.get("acdt_atfl_no") != null && !"".equals(tempReenactReq.get("acdt_atfl_no"))) {
			Map<String, String> fileParam = new HashMap<>();
			fileParam.put("id", tempReenactReq.get("acdt_atfl_no").toString());
			try {
				fileReqReenactList = fileService.selectFileList(fileParam);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// 작업내용 및 과정 html 태그
		String subTag1 = "";
		for(int i=0; i < oprnList.size(); i++) {
			subTag1 += "            <tr>\r\n" + 
					   "                <td style=\"height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + (i+1) + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + oprnList.get(i).get("chk") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					   "                    <p>" + oprnList.get(i).get("cd_nm") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "            </tr>\r\n";
		}
		subTag1 += "            <tr>\r\n" + 
				"                <td style=\"height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
				"                    <p><br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
				"                    <p><br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p><br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n";

		// 불안전한 행동(시스템적 원인) html 태그
		String subTag2 = "";
		for(int i=0; i < usfttxtList.size(); i++) {
			subTag2 += "            <tr>\r\n" + 
					   "                <td style=\"height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + (i+1) + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + usfttxtList.get(i).get("chk") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					   "                    <p>" + usfttxtList.get(i).get("cd_nm") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "            </tr>\r\n";
		}

		// 불안전한 상태(설비적 원인) html 태그
		String subTag3 = "";
		for(int i=0; i < usftstList.size(); i++) {
			subTag3 += "            <tr>\r\n" + 
					   "                <td style=\"height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + (i+1) + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + usftstList.get(i).get("chk") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					   "                    <p>" + usftstList.get(i).get("cd_nm") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "            </tr>\r\n";
		}
		subTag3 += "            <tr>\r\n" + 
				"                <td style=\"height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
				"                    <p><br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
				"                    <p><br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p><br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td style=\"height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
				"                    <p><br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
				"                    <p><br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p><br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td style=\"height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
				"                    <p><br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
				"                    <p><br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p><br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n";
		
		// 인적사항 html 태그
		String subTag4 = "";
		for(int i=0; i < dfdtList.size(); i++) {
			subTag4 += "            <tr>\r\n" + 
					   "                <td style=\"width: 2%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + (i+1) + "<br></p>\r\n" + 
					   "				<td style=\"width: 8%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + dfdtList.get(i).get("dfdt_cl_nm") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"width: 8%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + dfdtList.get(i).get("dfdt_pric_id") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"width: 5%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + dfdtList.get(i).get("dfdt_nm") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + dfdtList.get(i).get("dfdt_dept_nm") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"width: 2%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + dfdtList.get(i).get("dfdt_sex_nm") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"width: 3%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + dfdtList.get(i).get("dfdt_age") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"width: 7%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + dfdtList.get(i).get("jncm_dt") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + dfdtList.get(i).get("disa_cl_nm") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + dfdtList.get(i).get("dfdt_disa_prts_nm") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"width: 5%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + dfdtList.get(i).get("dfdt_hspz_yn") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + dfdtList.get(i).get("dfdt_care_days") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p style=\"text-align: left;\">" + dfdtList.get(i).get("dfdt_dgns_rslt_txt") + "<br></p>\r\n" + 
					   "                </td>\r\n" +
					   "            </tr>\r\n";
		}
		
		// 사고조사 원인분석 html 태그
		String subTag5 = "";
		for(int i=0; i < ivstList.size(); i++) {
			subTag5 += "            <tr>\r\n" + 
					   "                <td style=\"width: 2%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + (i+1) + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + ivstList.get(i).get("acdt_ivst_cl_nm") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + ivstList.get(i).get("acdt_ivst_cat_nm") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"width: 50%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + ivstList.get(i).get("acdt_caus_anl_txt_nm") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "            </tr>\r\n";
		}
		
		// 사고조사 시정조치 html 태그
		String subTag6 = "";
		for(int i=0; i < actnList.size(); i++) {
			subTag6 += "            <tr>\r\n" + 
					   "                <td style=\"width: 2%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + (i+1) + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"width: 23%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + actnList.get(i).get("acdt_actn_cl_nm") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"width: 50%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					   "                    <p>" + actnList.get(i).get("acdt_actn_plan_txt") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + actnList.get(i).get("acdt_actn_usr_nm") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + actnList.get(i).get("acdt_actn_tmlm_dt") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "            </tr>\r\n";
		}
		
		// 첨부파일 정보 html 태그
		String subFile = "";
		if (fileList.size() > 0) {
				for(int i=0; i<atfl_list1.size(); i++) {
					subFile += "            <tr>\r\n" + 
							  "                <td style=\"width: 5%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
							  "                    <p>" + (i+1) + "<br></p>\r\n" + 
							  "                </td>\r\n" + 
							  "                <td style=\"width: 23%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
						      "                    <p>" + atfl_list1.get(i).get("cd_nm") + "<br></p>\r\n" + 
						      "                </td>\r\n" +
							  "                <td style=\"width: 95%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
							  "                    <p>" + "<a href=\"" + serverUrl + "/api/v1/public/download/" + atfl_list1.get(i).get("acdt_atfl_no") + "\">" + atfl_list1.get(i).get("acdt_atfl_nm") + "</a>" + "<br></p>\r\n" + 
							  "                </td>\r\n" + 
							  "            </tr>\r\n";
				}
		}
		
		// 재발방지대책 이행결과 html 태그
		String subTag7 = "";
		for(int i=0; i < actnList.size(); i++) {
			subTag7 += "            <tr>\r\n" + 
					   "                <td style=\"width: 2%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + (i+1) + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					   "                    <p>" + actnList.get(i).get("acdt_actn_cl_nm") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"width: 30%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					   "                    <p>" + actnList.get(i).get("acdt_actn_plan_txt") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + actnList.get(i).get("acdt_actn_usr_nm") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + actnList.get(i).get("acdt_actn_tmlm_dt") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + actnList.get(i).get("acdt_acco_dt") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: left;\">\r\n" + 
					   "                    <p>" + actnList.get(i).get("acdt_actn_txt") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "            </tr>\r\n";
		}
		
		// 개선결과 첨부파일 정보 html 태그
		String subReqFile = "";
		if (fileReqList.size() > 0) {
				for(int i=0; i<atfl_list2.size(); i++) {
					subReqFile += "            <tr>\r\n" + 
							  "                <td style=\"width: 5%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
							  "                    <p>" + (i+1) + "<br></p>\r\n" + 
							  "                </td>\r\n" + 
							  "                <td style=\"width: 23%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
						      "                    <p>" + atfl_list2.get(i).get("cd_nm") + "<br></p>\r\n" + 
						      "                </td>\r\n" +
							  "                <td style=\"width: 95%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
							  "                    <p>" + "<a href=\"" + serverUrl + "/api/v1/public/download/" + atfl_list2.get(i).get("acdt_atfl_no") + "\">" + atfl_list2.get(i).get("acdt_atfl_nm") + "</a>" + "<br></p>\r\n" + 
							  "                </td>\r\n" + 
							  "            </tr>\r\n";
				}
		}
		
		// 사고발생현장 사진 첨부파일 정보 html 태그
		String subReqOccurFile = "";
		if (fileReqOccurList.size() > 0) {
				for(int i=0; i<occur_atfl_list.size(); i++) {
		   subReqOccurFile += "            <tr>\r\n" + 
							  "                <td style=\"width: 5%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
							  "                    <p>" + (i+1) + "<br></p>\r\n" + 
							  "                </td>\r\n" + 
							  "                <td style=\"width: 23%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
						      "                    <p>" + occur_atfl_list.get(i).get("cd_nm") + "<br></p>\r\n" + 
						      "                </td>\r\n" +
							  "                <td style=\"width: 95%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
							  "                    <p>" + "<a href=\"" + serverUrl + "/api/v1/public/download/" + occur_atfl_list.get(i).get("acdt_atfl_no") + "\">" + occur_atfl_list.get(i).get("acdt_atfl_nm") + "</a>" + "<br></p>\r\n" + 
							  "                </td>\r\n" + 
							  "            </tr>\r\n";
				}
		}
		
		//사고재현 사진 첨부파일 정보 html 태그
		String subReqReenactFile = "";
		if (fileReqReenactList.size() > 0) {
				for(int i=0; i<reenact_atfl_list.size(); i++) {
		 subReqReenactFile += "            <tr>\r\n" + 
							  "                <td style=\"width: 5%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
							  "                    <p>" + (i+1) + "<br></p>\r\n" + 
							  "                </td>\r\n" + 
							  "                <td style=\"width: 23%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
						      "                    <p>" + reenact_atfl_list.get(i).get("cd_nm") + "<br></p>\r\n" + 
						      "                </td>\r\n" +
							  "                <td style=\"width: 95%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
							  "                    <p>" + "<a href=\"" + serverUrl + "/api/v1/public/download/" + reenact_atfl_list.get(i).get("acdt_atfl_no") + "\">" + reenact_atfl_list.get(i).get("acdt_atfl_nm") + "</a>" + "<br></p>\r\n" + 
							  "                </td>\r\n" + 
							  "            </tr>\r\n";
				}
		}
		
		// 결재 제목
		String aprv_ttl = "사고발생 정보";
		// 결재 내용
		String aprv_cntn = "<div>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "기본정보" + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "사업장" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + content.get("wkpl_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 	
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "사고구분" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + content.get("acdt_cl_nm") + " / "  + content.get("acdt_dtl_cl_nm") +"<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "사고 유형" + "<br></p>\r\n" + 
				"                </td>\r\n" +
				"                <td colspan=\"3\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" +
				"                    <p>" + content.get("acdt_tp_nm") + "<br></p>\r\n" +
				"                </td>\r\n" +
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 				
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "사고기인물" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 18%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + content.get("acdt_caus_tgt_cd_nm") + " / " + content.get("acdt_caus_tgt_txt") + "<br></p>\r\n" + 
				"                </td>\r\n" +
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "사고위치" + "<br></p>\r\n" + 
				"                </td>\r\n" + 				
				"                <td colspan=\"1\" style=\"width: 18%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + content.get("acdt_wkpl_loc_nm") + " / " + content.get("acdt_dtl_loc_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" +
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "사고발생 부서" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + content.get("kor_acdt_occr_org_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "사고일시" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 9%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + content.get("acdt_occr_dt") + " " + content.get("acdt_occr_hour") + " " + " : " + content.get("acdt_occr_minute") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "업무상 사고여부" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + content.get("bsns_rel_acdt_yn") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "산업재해조사표제출대상" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"3\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + content.get("inac_ivst_sbms_tgt_yn") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "산재여부" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"4\" style=\"width: 9%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + content.get("inac_yn_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 	
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "[사고발생]<br>" + content.get("occrhour") + " " + " : " + content.get("occrminute") +  "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"8\" style=\"width: 88%; height: 72px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + content.get("acdt_occr_txt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "[사고접수]<br>" + content.get("accphour") + " " + " : " + content.get("accpminute") +  "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"8\" style=\"width: 88%; height: 72px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + content.get("acdt_accp_txt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "[초기대응]<br>" + content.get("bgng_rposhour") + " " + " : " + content.get("bgng_rposminute") +  "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"8\" style=\"width: 88%; height: 72px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + content.get("acdt_bgng_rpos_txt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "[사후조치]<br>" + content.get("af_actnhour") + " " + " : " + content.get("af_actnminute") +  "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"8\" style=\"width: 88%; height: 72px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + content.get("acdt_af_actn_txt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 				
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "사고발생원인" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"8\" style=\"width: 88%; height: 72px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + content.get("acdt_occr_caus_txt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 								
				"            <tr>\r\n" + 
				"                <td rowspan=\"2\" colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "재발방지대책" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"8\" style=\"width: 88%; height: 72px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <span>" + "· 사고발생부서" + "</span>\r\n" + 
				"                    <p>" + content.get("acdt_dept_prvt_meas_txt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 		
				"                <td colspan=\"8\" style=\"width: 88%; height: 72px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <span>" + "· 안전관리부서" + "</span>\r\n" + 
				"                    <p>" + content.get("sft_dept_prvt_meas_txt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 
			    "    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" +
			    "        <tbody>\r\n" +
			    "            <tr>\r\n" +
			    "                <td style=\"width: 33%; height: 24px; border-width: 1px 0px 1px 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px;\">\r\n" +
			    "                    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" +
			    "                    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">작업내용 및 과정</p>\r\n" +
			    "                    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" +
			    "                        <tbody>\r\n" +
			    "	                        <tr>\r\n" +
			    "	                            <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" +
			    "	                                <p style=\"font-weight: bold;\">No.<br></p>\r\n" +
			    "	                            </td>\r\n" +
			    "	                            <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" +
			    "	                                <p style=\"font-weight: bold;\">선택<br></p>\r\n" +
			    "	                            </td>\r\n" +
			    "	                            <td style=\"width: 76%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" +
			    "	                                <p style=\"font-weight: bold;\">작업내용 및 과정<br></p>\r\n" +
			    "	                            </td>\r\n" +
			    "	                        </tr>\r\n" +
			    subTag1 +
			    "                        </tbody>\r\n" +
			    "                    </table>\r\n" +
			    "                </td>\r\n" +
			    "                <td style=\"width: 33%; height: 24px; border-width: 1px 0px 1px 0px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px;\">\r\n" +
			    "                    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" +
			    "                    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">불안전한 행동(시스템적 원인)</p>\r\n" +
			    "                    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" +
			    "                        <tbody>\r\n" +
			    "	                        <tr>\r\n" +
			    "	                            <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" +
			    "	                                <p style=\"font-weight: bold;\">No.<br></p>\r\n" +
			    "	                            </td>\r\n" +
			    "	                            <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" +
			    "	                                <p style=\"font-weight: bold;\">선택<br></p>\r\n" +
			    "	                            </td>\r\n" +
			    "	                            <td style=\"width: 76%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" +
			    "	                                <p style=\"font-weight: bold;\">작업내용 및 과정<br></p>\r\n" +
			    "	                            </td>\r\n" +
			    "	                        </tr>\r\n" +
			    subTag2 +
			    "                        </tbody>\r\n" +
			    "                    </table>\r\n" +
			    "                </td>\r\n" +
			    "                <td style=\"width: 33%; height: 24px; border-width: 1px 1px 1px 0px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px;\">\r\n" +
			    "                    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" +
			    "                    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">불안전한 상태(설비적 원인)</p>\r\n" +
			    "                    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" +
			    "                        <tbody>\r\n" +
			    "	                        <tr>\r\n" +
			    "	                            <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" +
			    "	                                <p style=\"font-weight: bold;\">No.<br></p>\r\n" +
			    "	                            </td>\r\n" +
			    "	                            <td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" +
			    "	                                <p style=\"font-weight: bold;\">선택<br></p>\r\n" +
			    "	                            </td>\r\n" +
			    "	                            <td style=\"width: 76%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" +
			    "	                                <p style=\"font-weight: bold;\">작업내용 및 과정<br></p>\r\n" +
			    "	                            </td>\r\n" +
			    "	                        </tr>\r\n" +
			    subTag3 +
			    "                        </tbody>\r\n" +
			    "                    </table>\r\n" +
			    "                </td>\r\n" +
			    "            </tr>\r\n" +
			    "        </tbody>\r\n" +
			    "    </table>\r\n" +
				
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "사고자 인적사항" + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px; table-layout: fixed;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 4%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 8%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "구분" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 8%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "사번" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 5%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "성명" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "부서" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 3%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "성별" + "<br></p>\r\n" + 
				"                </td>\r\n" +
				"                <td style=\"width: 3%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "연령" + "<br></p>\r\n" + 
				"                </td>\r\n" +
				"                <td style=\"width: 7%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "입사일" + "<br></p>\r\n" + 
				"                </td>\r\n" +
				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "재해구분" + "<br></p>\r\n" + 
				"                </td>\r\n" +
				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "재해부위" + "<br></p>\r\n" + 
				"                </td>\r\n" +
				"                <td style=\"width: 5%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "입원유무" + "<br></p>\r\n" + 
				"                </td>\r\n" +
				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "치료기간(일)" + "<br></p>\r\n" + 
				"                </td>\r\n" +
				"                <td style=\"width: 18%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "진단결과" + "<br></p>\r\n" + 
				"                </td>\r\n" +
				"            </tr>\r\n" +
				subTag4 + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" +
				
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "사고조사 결과 : 원인 분석" + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px; table-layout: fixed;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 4%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 20%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "구분" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 28%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "분류" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 50%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "원인 분석 내용" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				subTag5 + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" +
				
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "사고조사 결과 : 시정 조치" + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px; table-layout: fixed;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 4%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 23%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "구분" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 50%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "시정 조치 내용" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "조치자" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "조치기한" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				subTag6 + 
				"        </tbody>\r\n" + 
				"    </table>\r\n";

		aprv_cntn = aprv_cntn +
		"<table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" +
		"	<tbody>\r\n" +
		"   	<tr>\r\n" +
		"			<td>\r\n" +
		"				<table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" +
		"   				<tbody>\r\n" +
		"       				<tr>\r\n" +	
		"							<td style=\"width: 50%; height: 24px; border-width: 0.5px 0.5px 0.5px 0.5px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" +
		"								<p style=\"font-weight: bold; margin-bottom: 0px;\">사고 발생/현장 사진</p>\r\n" +
		"							</td>\r\n" +
		"							<td style=\"width: 50%; height: 24px; border-width: 0.5px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" +
		"								<p style=\"font-weight: bold; margin-bottom: 0px;\">사고 재현 사진</p>\r\n" +
		"							</td>\r\n" +
		"       				</tr>\r\n" +
		"       				<tr>\r\n" +	
		"							<td style=\"height: 230px; border-width: 0.5px 0.5px 0.5px 0.5px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px;\">\r\n" +
		" 								<p style=\"margin-bottom: 0px;\"><a href=\"" + serverUrl + "/api/v1/public/download/" + tempOccurReq.get("acdt_atfl_no") + "\"><img src=\"" + serverUrl + "/api/v1/public/download/" + tempOccurReq.get("acdt_atfl_no") + "\" style=\"width:100%;\"></a></p>\r\n" +
		"							</td>\r\n" +
		"							<td style=\"height: 230px; border-bottom: 0.5px solid rgb(204, 204, 204); border-right: 0.5px solid rgb(204, 204, 204); border-left: 0.5px solid rgb(204, 204, 204); border-top: 0.5px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" +
		" 								<p style=\"margin-bottom: 0px;\"><a href=\"" + serverUrl + "/api/v1/public/download/" + tempReenactReq.get("acdt_atfl_no") + "\"><img src=\"" + serverUrl + "/api/v1/public/download/" + tempReenactReq.get("acdt_atfl_no") + "\" style=\"width:100%;\"></a></p>\r\n" +
		"							</td>\r\n" +
		"   					</tr>\r\n" +
		"       			</tbody>\r\n" +
		"   			</table>\r\n" +							
		"			</td>\r\n" +
		"			<br/>\r\n";
		
		if (fileList.size() > 0) {
			aprv_cntn = aprv_cntn +						
					"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "첨부파일 정보" + "</p>\r\n" + 
					"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px; table-layout: fixed;\">\r\n" + 
					"        <tbody>\r\n" + 
					"            <tr>\r\n" + 
					"                <td style=\"width: 5%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
					"                    <p style=\"font-weight: bold;\">" + "No." + "</p>\r\n" + 
					"                </td>\r\n" +
					"				<td style=\"width: 35%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
					"                    <p style=\"font-weight: bold;\">" + "첨부파일 구분" + "</p>\r\n" + 
					"                </td>\r\n" + 
					"                <td style=\"width: 60%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
					"                    <p style=\"font-weight: bold;\">" + "파일명" + "</p>\r\n" + 
					"                </td>\r\n" + 
					"            </tr>\r\n" +
					subFile + 
					"        </tbody>\r\n" + 
					"    </table>\r\n";
		}
		aprv_cntn = aprv_cntn +
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "재발방지대책 이행 결과" + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px; table-layout: fixed;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 4%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 18%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "구분" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 30%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "시정 조치 내용" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "조치자" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "조치기한" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "조치완료일" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 20%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "조치내용" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				subTag7 + 
				"        </tbody>\r\n" + 
				"    </table>\r\n";
		
				if (fileReqList.size() > 0) {
					aprv_cntn = aprv_cntn +
							"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
							"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "개선결과 첨부파일 정보" + "</p>\r\n" + 
							"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px; table-layout: fixed;\">\r\n" + 
							"        <tbody>\r\n" + 
							"            <tr>\r\n" + 
							"                <td style=\"width: 5%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
							"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
							"                </td>\r\n" + 
							"				<td style=\"width: 30%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
							"                    <p style=\"font-weight: bold;\">" + "첨부파일 구분" + "<br></p>\r\n" + 
							"                </td>\r\n" + 
							"                <td style=\"width: 95%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
							"                    <p style=\"font-weight: bold;\">" + "파일명" + "<br></p>\r\n" + 
							"                </td>\r\n" + 
							"            </tr>\r\n" +
							subReqFile + 
							"        </tbody>\r\n" + 
							"    </table>\r\n";
				}
				aprv_cntn = aprv_cntn +
						"    <p>\r\n" + 
						"        <spam style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "바로가기" + " :&nbsp;</spam><a href=\"" + frontUrl + "/acdtmgnt/acdtmgntdtllup" + "/" + content.get("acdt_id") + "\" style=\"font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"target=\"_blank\">" + frontUrl + "/acdtmgnt/acdtmgntdtllup" + "/" + content.get("acdt_id") + "</a>\r\n" + 
						"    </p>\r\n" +
				"</div>";
		Map<String, Object> aprvParam = new HashMap<>();
//				결재 키 
		String aprv_no = UUID.randomUUID().toString().replace("-", "");
		aprvParam.put("aprv_no", aprv_no);
//				결재 코드
		aprvParam.put("arpv_cd", "40");		
//		 		업무번호
		aprvParam.put("biz_no", bizNo);
//				상신자
		aprvParam.put("aprv_usr_id", usrId);
//				결재 상태 코드 - 결재상신
		aprvParam.put("aprv_stt_cd", "1");		
//				후행메서드	
		aprvParam.put("aprv_mthd_nm", "aprvAcdtMgntApprAf");
//				제목
		aprvParam.put("aprv_ttl", aprv_ttl);
//				내용
		aprvParam.put("aprv_cntn", aprv_cntn);		
//				결재 비고	
		aprvParam.put("aprv_note", "");
//				사원 번호	
		aprvParam.put("SESS_USR_ID", usrId);
//		 		결재 정보 table insert 
		int n = aprvService.insertAprv(aprvParam);		
		
		Map<String, Object> data = new HashMap<>();
		if(n > -1) {	
			param.put("acdt_brie_aprv_no", aprv_no);
			service.updateAppr(param);
			
			data.put("Success", "Success");
			data.put("aprvNo", aprv_no);
			data.put("usrId", usrId);
		}
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
}
