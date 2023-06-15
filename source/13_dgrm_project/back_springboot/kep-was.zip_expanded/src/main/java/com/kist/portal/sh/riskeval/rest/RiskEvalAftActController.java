package com.kist.portal.sh.riskeval.rest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kist.portal.common.aprv.service.AprvService;
import com.kist.portal.common.comm.service.CommService;
import com.kist.portal.common.email.rest.SendEmailUtil;
import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.sh.riskeval.service.RiskEvalAftActService;
import com.kist.portal.sh.riskeval.service.RiskEvalService;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/she/riskeval")
public class RiskEvalAftActController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SessionManager sessionManager;
	
	@Value("${spring.profiles}")
	private String profiles;
	
	@Value("${dgrm.front-url}")
	private String frontUrl;
	
	@Value("${dgrm.server-url}")
	private String serverUrl;
	
	@Autowired
	private AprvService aprvService;
	
	@Autowired
	private SendEmailUtil sendEmailUtil;
	
	@Autowired
	private CommService commService;
	
	@Autowired
	private RiskEvalService basicService;
		
	@Autowired
	private RiskEvalAftActService service;
	
	@GetMapping("/aftact")
	public ResponseEntity<?> selectRiskEvalAftActList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_DEPT", userInfo.getDeptCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		param.put("profiles", profiles);
		param.put("serverUrl", serverUrl);

		List<Map<String, ?>> list = service.selectRiskEvalAftActList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PutMapping("/aftact")
	@SuppressWarnings("unchecked")
	@Transactional
	public ResponseEntity<?> updateRiskEvalAftAct(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		List<Map<String, String>> updateList = (List<Map<String, String>>)param.get("updateList");
			
		int n = 0;
				
		for(Map<String, String> map : updateList) {
			map.put("SESS_USR_ID", userInfo.getUsrId());
			n *= service.updateRiskEvalAftAct(map);
		}
			
		String result = n > -1? (String) param.get("dgsn_apal_id") : Constant.FAIL;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("/aftact")
	@Transactional
	public ResponseEntity<?> checkRiskEvalAftActAppr(@RequestBody Map<String, Object> param, HttpSession session) {
		String kind = (String) param.get("kind");
		if("Mail".equals(kind)) {
			return sendMail(param, session);
		} else {
			return updateRiskEvalAftActMultiAppr(param, session);
		}
	}

	@SuppressWarnings("unchecked")
	private ResponseEntity<?> sendMail(Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		Boolean mailResult = true;
		long days = 0;
		
		Map<String, ?> mailMap = (Map<String, ?>) param.get("mailMap");
		for( String key : mailMap.keySet() ){
			Map<String, ?> mail = (Map<String, ?>) mailMap.get(key);
			List<Map<String, ?>> dtlList = (List<Map<String, ?>>) mail.get("dtlList");
			String dtlTag = "";
			for(int i=0; i < dtlList.size(); i++) {
				String dt = dtlList.get(i).get("dgsn_btt_cmpy_schd_dt").toString();
				String code = dtlList.get(i).get("apal_id").toString().substring(0, 19);
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				String tr_class_name = "";
				String style = "";
				String txt = "";
				try {
					String today = sdf.format(Calendar.getInstance().getTime());
					Date d1 = sdf.parse(dt);
					Date d2 =sdf.parse(today);
					
					long sec = (d2.getTime() - d1.getTime()) / 1000;
					days = sec / (24 * 60 * 60);
					if(days > 0) {
						tr_class_name = " class=\"red_color\"";
						style = " style=\"color: red;\"";
						txt = days +"일 초과";
					} else if(days == 0){
						style = " style=\"color: blue;\"";
						txt = "조치 당일";
					}else {
						style = " style=\"color: blue;\"";
						txt = "조치 " + (days * -1) +"일전";
					}
				} catch (ParseException e) {
					logger.error(e.getMessage());
				}
				
				dtlTag +=
						"<tbody style=\"height:40px;\">\r\n" + 
						"	<tr " + tr_class_name + ">\r\n" + 
						"       <td style=\"color:#000;\">\r\n" +  
					    "       	<p>" + (i+1) + "</p>\r\n" + 
					    "       </td>\r\n" + 
						"       <td style=\"color:blue;\">\r\n" +  
					    "           <a href=\"" + frontUrl + "/riskeval/riskevalaftactlist/" + code + "\" target=\"_blank\">" + dtlList.get(i).get("apal_id") + "</a>\r\n" + 
					    "       </td>\r\n" + 
						"       <td>\r\n" +  
					    "           <p>" + dtlList.get(i).get("smcl_dngp_nm") + "</p>\r\n" + 
					    "       </td>\r\n" + 
						"       <td>\r\n" +  
					    "           <p>" + dtlList.get(i).get("dgsn_btt_cmpy_schd_dt") + "</p>\r\n" + 
					    "       </td>\r\n" + 
						"       <td>\r\n" +  
					    "           <p" + style + ">" + txt + "</p>\r\n" + 
					    "       </td>\r\n" + 
					    "	</tr>\r\n" +
			    		"</tbody>\r\n";
			}
		
			// 본문 내용
			String mail_cntn = 
					sendEmailUtil.getmailCss() +
					"<div id=\"mail_div\" class=\"riskeval_div\" style=\"width: 750px;\">\r\n" + 
					"	<div id=\"header_div\" \">\r\n" +
					"		<img class\"logo\" src=\"" + frontUrl + "/_nuxt/img/logo.d0672ff.png\">\r\n" +
					"		<span class=\"title\" \">SHE PORTAL 위험성평가 위험성감소대책 조치 요청 메일</span>\r\n" +
					"	</div>\r\n" + 
					"	<div id=\"container_div\">\r\n" +
					"		<div id=\"content_div\">\r\n" +
					"   		<p>SHE Portal 에서 위험성평가 위험성감소대책 조치 요청 드립니다.<br/>조치 결과를 SHE Portal 등록 부탁 드립니다.</p>\r\n" +
					"   		<p style=\"margin-bottom:15px;\">*등록방법: SHE Portal → 안전/보건 → 6.위험성평가 → 개선조치결과 등록/조회 → 검색/조회 → 조치결과/사진 등록</p>\r\n" + 
					"   		<table class=\"txc-table\" style=\"margin-bottom:15px;\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"1\" \">\r\n" + 
					"				<colgroup> \r\n" +
					"   				<col style=\"width: 40px;\"/> \r\n" +
					"       			<col style=\"width: 200px;\"/> \r\n" +
					"       			<col style=\"width: auto;\"/> \r\n" +
					"       			<col style=\"width: 100px;\"/> \r\n" +
					"       			<col style=\"width: 100px;\"/> \r\n" +
					"				</colgroup> \r\n" +
					"				<thead>\r\n" + 
					"   				<tr style=\"height: 40px;\">\r\n" + 
				    "       				<td>\r\n" + 
				    "           				<p>No.</p>\r\n" + 
				    "           			</td>\r\n" + 
				    "           			<td>\r\n" + 
				    "               			<p>위험성평가 조치번호</p>\r\n" + 
				    "           			</td>\r\n" + 
				    "           			<td>\r\n" + 
				    "               			<p>세부공정명</p>\r\n" + 
				    "           			</td>\r\n" + 
				    "           			<td>\r\n" + 
				    "               			<p>조치일</p>\r\n" + 
				    "           			</td>\r\n" + 
				    "           			<td>\r\n" + 
				    "               			<p>조치기한</p>\r\n" + 
				    "           			</td>\r\n" + 
				    "       			</tr>\r\n" + 
					"				</thead>\r\n" + 
					dtlTag + 
					"			</table>\r\n" +
					"   		<a class=\"link_text\" style=\"display:inline-block; margin-bottom:15px;\" href=\"" + frontUrl + "\" target=\"_blank\">SHE Portal Link</a><br/>\r\n" +
					"   		<p style=\"color:rgb(6,44,94);\">\r\n" +
					"     			*산업안전보건법 제35조(위험성평가의 실시) 제3항 의거, 평가의 결과와 조치사항을 기록 보존하여야 합니다.<br/>\r\n" +
					"     			산업안전보건법 준수를 위하여 계획 수립된 일정 내 조치 완료 부탁 드리겠습니다.<br/>\r\n" +
					"   		</p>\r\n" +
					"		</div>\r\n" +
					"		<div id=\"inquiry_div\">\r\n" +
					"			<div>\r\n" +
					"   			<p>시스템 문의</p>\r\n" +
					"   			<p>안전보건시스템 dehs (dehs@donggramisoft.co.kr)</p>\r\n" +
					"			</div>\r\n" +
					"		</div>\r\n" +
					"	</div>\r\n" +
					"	<div id=\"footer_div\">\r\n" +
					"		<img class=\"background_img\" src=\"" + frontUrl + "/images/ap_background.png\" alt=\"배경이미지\" />\r\n" +
					"	</div>\r\n" +
					"</div>\r\n";
			
			// 메일 업무 코드
			String bizCd = "50";
			// 메일 제목
			String title = "[SHE Portal] 위험성평가 위험성감소대책 조치 요청";
			// 메일 내용
			String content = mail_cntn;
			// 수신자
			String[] to = {mail.get("to") + ""};	
			// 참조자
			String[] cc = {mail.get("cc") + ""};
			
			// 메일 송신 
			mailResult &= sendEmailUtil.sendMail(bizCd, userInfo.getUsrId(), title, content, to, cc);
		}
		String result = (mailResult ? Constant.SUCCESS : Constant.FAIL);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	private ResponseEntity<?> updateRiskEvalAftActMultiAppr(Map<String, Object> multiParam, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
				
		Map<String, String> param = new HashMap<>();
		String bizNo = "riskevalaftactmulti";
		String usrId = userInfo.getUsrId();
		param.put("SESS_USR_ID", usrId);
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("cd_grp_no", "SH00000084");
		
		List<Map<String, ?>> codeList = commService.selectCdList(param);
								
		String dtlTag = "";
		String cdNm1 = "";
		String cdNm2 = "";
		String bg1 = "";
		String bg2 = "";
		String ddmt = "";
		int scr1 = 0;
		int scr2 = 0;
		Map<String, String> chkMap = null;
		int cnt = 1;
		
		List<String> idList = (List<String>) multiParam.get("idList");
		for(String id : idList) {
			param.put("dgsn_apal_dtl_id", id);
			Map<String, ?> content = basicService.selectRiskEvalBasic(param);
			List<Map<String, ?>> dtlList = basicService.selectRiskEvalDtlList(param);
			
			for(int i=0; i < dtlList.size(); i++) {
				if(dtlList.get(i).get("dgsn_dec_meas_txt") != null) {
					ddmt = dtlList.get(i).get("dgsn_dec_meas_txt").toString().replaceAll("\n", "<br/>");
				}
				scr1 = Integer.parseInt(dtlList.get(i).get("dgsn_apal_scr").toString());
				chkMap = chkLevel(scr1, codeList);
				cdNm1 = chkMap.get("cdNm");
				bg1 = chkMap.get("bg");
				scr2 = Integer.parseInt(dtlList.get(i).get("btt_af_dgsn_apal_scr").toString());
				chkMap = chkLevel(scr2, codeList);
				cdNm2 = chkMap.get("cdNm");
				bg2 = chkMap.get("bg");
				dtlTag += "            <tr>\r\n" + 
					    "                <td style=\"padding: 0px 3px;\">\r\n" + 
					    "                    <p>" + (cnt++) + "<br></p>\r\n" + 
					    "                </td>\r\n" + 
					    "                <td style=\"padding: 0px 3px;\">\r\n" + 
					    "                    <p>" + content.get("dgsn_apal_id")  + "-" + dtlList.get(i).get("sort_sn") + "<br></p>\r\n" + 
					    "                </td>\r\n" + 
					    "                <td style=\"padding: 0px 3px;\">\r\n" + 
					    "                    <p>" + content.get("dgsn_apal_type_nm") + "<br></p>\r\n" + 
					    "                </td>\r\n" + 
					    "                <td style=\"padding: 0px 3px;\">\r\n" + 
					    "                    <p>" + content.get("dgsn_apal_bgn_dt") + "<br></p>\r\n" + 
					    "                </td>\r\n" + 
					    "                <td style=\"padding: 0px 3px;\">\r\n" + 
					    "                    <p>" + content.get("cmpy_nm") + "<br></p>\r\n" + 
					    "                </td>\r\n" + 
					    "                <td style=\"padding: 0px 3px;\">\r\n" + 
					    "                    <p>" + content.get("wkpl_nm") + "<br></p>\r\n" + 
					    "                </td>\r\n" + 
					    "                <td style=\"padding: 0px 3px;\">\r\n" + 
					    "                    <p>" + content.get("dgsn_apal_supe_usr_nm") + "<br></p>\r\n" + 
					    "                </td>\r\n" + 
					    "                <td style=\"padding: 0px 3px;\">\r\n" + 
					    "                    <p>" + content.get("lrcl_dngp_nm") + "<br></p>\r\n" + 
					    "                </td>\r\n" + 
					    "                <td style=\"padding: 0px 3px;\">\r\n" + 
					    "                    <p>" + content.get("mdcl_dngp_nm") + "<br></p>\r\n" + 
					    "                </td>\r\n" + 
					    "                <td style=\"padding: 0px 3px;\">\r\n" + 
					    "                    <p>" + dtlList.get(i).get("smcl_dngp_nm") + "<br></p>\r\n" + 
					    "                </td>\r\n" + 
					    "                <td style=\"text-align:left; padding: 0px 3px;\">\r\n" + 
					    "                    <p>" + dtlList.get(i).get("dgsn_apal_dtl_oprn_txt") + "<br></p>\r\n" + 
					    "                </td>\r\n" + 
					    "                <td style=\"text-align:left; padding: 0px 3px;\">\r\n" + 
					    "                    <p>" + dtlList.get(i).get("dgsn_apal_tgt_eqp_nm") + "<br></p>\r\n" + 
					    "                </td>\r\n" + 
					    "                <td style=\"text-align:left; padding: 0px 3px;\">\r\n" + 
					    "                    <p>" + dtlList.get(i).get("dgsn_apal_tgt_mtra_nm") + "<br></p>\r\n" + 
					    "                </td>\r\n" + 
					    "                <td style=\"padding: 0px 3px;\">\r\n" + 
					    "                    <p>" + dtlList.get(i).get("risk_cat_nm") + "<br></p>\r\n" + 
					    "                </td>\r\n" + 
					    "                <td style=\"text-align:left; padding: 0px 3px;\">\r\n" + 
					    "                    <p>" + dtlList.get(i).get("hmfl_risk_fact_nm") + "<br></p>\r\n" + 
					    "                </td>\r\n" + 
					    "                <td style=\"text-align:left; padding: 0px 3px;\">\r\n" + 
					    "                    <p>" + dtlList.get(i).get("risk_occr_rslt_txt").toString().replaceAll("\n", "<br/>") + "<br></p>\r\n" + 
					    "                </td>\r\n" + 
					    "                <td style=\"text-align:left; padding: 0px 3px;\">\r\n" + 
					    "                    <p>" + dtlList.get(i).get("cur_sfhe_actn_txt").toString().replaceAll("\n", "<br/>") + "<br></p>\r\n" + 
					    "                </td>\r\n" + 
					    "                <td style=\"padding: 0px 3px; color:rgb(255, 255, 255); background:" + bg1 + "\">\r\n" + 
					    "                    <p>" + cdNm1 + "<br></p>\r\n" + 
					    "                </td>\r\n" + 
					    "                <td style=\"text-align:left; padding: 0px 3px;\">\r\n" + 
					    "                    <p>" + dtlList.get(i).get("dgsn_dec_meas_txt").toString().replaceAll("\n", "<br/>") + "<br></p>\r\n" + 
					    "                </td>\r\n" + 
					    "                <td style=\"padding: 0px 3px; color:rgb(255, 255, 255); background:" + bg2 + "\">\r\n" + 
					    "                    <p>" + cdNm2 + "<br></p>\r\n" + 
					    "                </td>\r\n" + 
					    "                <td style=\"padding: 0px 3px;\">\r\n" + 
					    "                    <p>" + dtlList.get(i).get("dgsn_btt_rspn_usr_nm") + "<br></p>\r\n" + 
					    "                </td>\r\n" + 
					    "                <td style=\"padding: 0px 3px;\">\r\n" + 
					    "		   		     <p>" + dtlList.get(i).get("dgsn_btt_cmpy_schd_dt") + "<br></p>\r\n" + 
					    "                </td>\r\n" + 
					    "                <td style=\"text-align:left; padding: 0px 3px;\">\r\n" + 
					    "                    <p>" + dtlList.get(i).get("dgsn_btt_txt").toString().replaceAll("\n", "<br/>") + "<br></p>\r\n" + 
					    "                </td>\r\n" + 
					    "                <td style=\"padding: 0px 3px;\">\r\n" + 
					    " 					 <p style=\"margin-bottom: 0px;\"><a href=\"" + serverUrl + "/api/v1/public/download/" + dtlList.get(i).get("dgsn_btt_bf_img_atfl_no") + "\"><img src=\"" + serverUrl + "/api/v1/public/download/" + dtlList.get(i).get("dgsn_btt_bf_img_atfl_no") + "\" style=\"width:97%; height:97%;\"></a></p>\r\n" + 
					    "                </td>\r\n" +
					    "                <td style=\"padding: 0px 3px;\">\r\n" + 
					    " 					 <p style=\"margin-bottom: 0px;\"><a href=\"" + serverUrl + "/api/v1/public/download/" + dtlList.get(i).get("dgsn_btt_af_img_atfl_no") + "\"><img src=\"" + serverUrl + "/api/v1/public/download/" + dtlList.get(i).get("dgsn_btt_af_img_atfl_no") + "\" style=\"width:97%; height:97%;\"></a></p>\r\n" + 
					    "                </td>\r\n" +
					    "            </tr>\r\n";
			}
		}
		
		// 결재 제목
		String aprv_ttl = "위험성평가 조치내용 검토(멀티)";
		// 결재 내용
		String aprv_cntn = "<div>\r\n" + 
				"    <p style=\"color: rgb(0, 0, 0); font-family: Malgun Gothic; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: Malgun Gothic; font-size: medium;\">" + "최종 위험성평가표" + "</p>\r\n" + 
				"    <table class=\"txc-table\" cellspacing=\"0\" cellpadding=\"0\" border=\"1\" style=\"width: 100%; table-layout: fixed; text-align:center; border-color: rgb(204, 204, 204); border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"		<colgroup> \r\n" +
				"   		<col style=\"width: 40px;\"/> \r\n" +
				"   		<col style=\"width: 200px;\"/> \r\n" +
				"   		<col style=\"width: 150px;\"/> \r\n" +
				"   		<col style=\"width: 100px;\"/> \r\n" +
				"   		<col style=\"width: 150px;\"/> \r\n" +
				"   		<col style=\"width: 150px;\"/> \r\n" +
				"   		<col style=\"width: 140px;\"/> \r\n" +
				"   		<col style=\"width: 140px;\"/> \r\n" +
				"   		<col style=\"width: 140px;\"/> \r\n" +
				"   		<col style=\"width: 150px;\"/> \r\n" +
				"   		<col style=\"width: 150px;\"/> \r\n" +
				"   		<col style=\"width: 150px;\"/> \r\n" +
				"   		<col style=\"width: 200px;\"/> \r\n" +
				"   		<col style=\"width: 150px;\"/> \r\n" +
				"   		<col style=\"width: 350px;\"/> \r\n" +
				"   		<col style=\"width: 180px;\"/> \r\n" +
				"   		<col style=\"width: 180px;\"/> \r\n" +
				"   		<col style=\"width: 80px;\"/> \r\n" +
				"   		<col style=\"width: 180px;\"/> \r\n" +
				"   		<col style=\"width: 130px;\"/> \r\n" +
				"   		<col style=\"width: 200px;\"/> \r\n" +
				"   		<col style=\"width: 150px;\"/> \r\n" +
				"   		<col style=\"width: 200px;\"/> \r\n" +
				"   		<col style=\"width: 200px;\"/> \r\n" +
				"   		<col style=\"width: 200px;\"/> \r\n" +
				"		</colgroup> \r\n" +
				"        <thead style=\"text-align:center; height:24px; background: rgb(246, 246, 246); \">\r\n" + 
				"            <tr>\r\n" + 
			    "                <td style=\"padding: 0px 3px;\">\r\n" + 
			    "                    <p style=\"font-weight: bold;\">No.<br></p>\r\n" + 
			    "                </td>\r\n" + 
			    "                <td style=\"padding: 0px 3px;\">\r\n" + 
			    "                    <p style=\"font-weight: bold;\">위험성평가 조치번호<br></p>\r\n" + 
			    "                </td>\r\n" + 
			    "                <td style=\"padding: 0px 3px;\">\r\n" + 
			    "                    <p style=\"font-weight: bold;\">평가종류<br></p>\r\n" + 
			    "                </td>\r\n" + 
			    "                <td style=\"padding: 0px 3px;\">\r\n" + 
			    "                    <p style=\"font-weight: bold;\">실시일자</p>\r\n" + 
			    "                </td>\r\n" + 
			    "                <td style=\"padding: 0px 3px;\">\r\n" + 
			    "                    <p style=\"font-weight: bold;\">회사</p>\r\n" + 
			    "                </td>\r\n" + 
			    "                <td style=\"padding: 0px 3px;\">\r\n" + 
			    "                    <p style=\"font-weight: bold;\">사업장</p>\r\n" + 
			    "                </td>\r\n" + 
			    "                <td style=\"padding: 0px 3px;\">\r\n" + 
			    "                    <p style=\"font-weight: bold;\">관리감독자</p>\r\n" + 
			    "                </td>\r\n" + 
			    "                <td style=\"padding: 0px 3px;\">\r\n" + 
			    "                    <p style=\"font-weight: bold;\">대분류</p>\r\n" + 
			    "                </td>\r\n" + 
			    "                <td style=\"padding: 0px 3px;\">\r\n" + 
			    "                    <p style=\"font-weight: bold;\">공정명</p>\r\n" + 
			    "                </td>\r\n" + 
			    "                <td style=\"padding: 0px 3px;\">\r\n" + 
			    "                    <p style=\"font-weight: bold;\">세부공정</p>\r\n" + 
			    "                </td>\r\n" + 
			    "                <td style=\"padding: 0px 3px;\">\r\n" + 
			    "                    <p style=\"font-weight: bold;\">세부작업설명</p>\r\n" + 
			    "                </td>\r\n" + 
			    "                <td style=\"padding: 0px 3px;\">\r\n" + 
			    "                    <p style=\"font-weight: bold;\">도구/설비</p>\r\n" + 
			    "                </td>\r\n" + 
			    "                <td style=\"padding: 0px 3px;\">\r\n" + 
			    "                    <p style=\"font-weight: bold;\">물질</p>\r\n" + 
			    "                </td>\r\n" + 
			    "                <td style=\"padding: 0px 3px;\">\r\n" + 
			    "                    <p style=\"font-weight: bold;\">위험분류</p>\r\n" + 
			    "                </td>\r\n" + 
			    "                <td style=\"padding: 0px 3px;\">\r\n" + 
			    "                    <p style=\"font-weight: bold;\">유해위험요인</p>\r\n" + 
			    "                </td>\r\n" + 
			    "                <td style=\"padding: 0px 3px;\">\r\n" + 
			    "                    <p style=\"font-weight: bold;\">위험발생 상황 및 결과</p>\r\n" + 
			    "                </td>\r\n" + 
			    "                <td style=\"padding: 0px 3px;\">\r\n" + 
			    "                    <p style=\"font-weight: bold;\">현재의 안전보건 조치</p>\r\n" + 
			    "                </td>\r\n" + 
			    "                <td style=\"padding: 0px 3px;\">\r\n" + 
			    "                    <p style=\"font-weight: bold;\">위험성</p>\r\n" + 
			    "                </td>\r\n" + 
			    "                <td style=\"padding: 0px 3px;\">\r\n" + 
			    "                    <p style=\"font-weight: bold;\">위험성 감소대책</p>\r\n" + 
			    "                </td>\r\n" + 
			    "                <td style=\"padding: 0px 3px;\">\r\n" + 
			    "                    <p style=\"font-weight: bold;\">개선후 위험성</p>\r\n" + 
			    "                </td>\r\n" + 
			    "                <td style=\"padding: 0px 3px;\">\r\n" + 
			    "                    <p style=\"font-weight: bold;\">조치담당자</p>\r\n" + 
			    "                </td>\r\n" + 
			    "                <td style=\"padding: 0px 3px;\">\r\n" + 
			    "                    <p style=\"font-weight: bold;\">조치완료예정일</p>\r\n" + 
			    "                </td>\r\n" + 
			    "                <td style=\"padding: 0px 3px;\">\r\n" + 
			    "                    <p style=\"font-weight: bold;\">조치내용</p>\r\n" + 
			    "                </td>\r\n" + 
			    "                <td style=\"padding: 0px 3px;\">\r\n" + 
			    "                    <p style=\"font-weight: bold;\">조치사진(전)</p>\r\n" + 
			    "                </td>\r\n" + 
			    "                <td style=\"padding: 0px 3px;\">\r\n" + 
			    "                    <p style=\"font-weight: bold;\">조치사진(후)</p>\r\n" + 
			    "                </td>\r\n" + 
			    "            </tr>\r\n" + 
				"        </thead>\r\n" + 
				"        </tbody>\r\n" + 
				dtlTag + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 
				"</div>\r\n";
		
		Map<String, Object> aprvParam = new HashMap<>();
//				결재 키 
		String aprv_no = UUID.randomUUID().toString().replace("-", "");
		aprvParam.put("aprv_no", aprv_no);
//				결재 코드
		aprvParam.put("arpv_cd", "80");		
//		 		업무번호
		aprvParam.put("biz_no", bizNo);
//				상신자
		aprvParam.put("aprv_usr_id", usrId);
//				결재 상태 코드 - 결재상신
		aprvParam.put("aprv_stt_cd", "1");		
//				후행메서드	
		aprvParam.put("aprv_mthd_nm", "aprvRiskEvalAftActApprAf");
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
			for(String id : idList) {
				param.clear();
				param.put("SESS_USR_ID", usrId);
				param.put("dgsn_apal_dtl_id", id);
				param.put("dgsn_btt_aprv_no", aprv_no);
				service.updateRiskEvalAftActAppr(param);
			}
			
			data.put("Success", "Success");
			data.put("aprvNo", aprv_no);
			data.put("usrId", usrId);
		}
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	private Map<String, String> chkLevel(int scr, List<Map<String, ?>> codeList) {
		Map<String, String> result = new HashMap<String, String>();
		String cd = "";
		for(Map<String, ?> map : codeList) {
			if(Integer.parseInt(map.get("buf_1st_cntn").toString()) <= scr && Integer.parseInt(map.get("buf_2nd_cntn").toString()) >= scr) {
	            cd = map.get("cd").toString();
				result.put("cd", cd);
	            result.put("cdNm", map.get("cd_nm").toString());
	            if("VL".equals(cd)) {
	            	result.put("bg", "rgb(0, 128, 0);");
	            } else if("L".equals(cd)) {
	            	result.put("bg", "rgb(76, 175, 80);");
	            } else if("H".equals(cd)) {
	            	result.put("bg", "rgb(248, 128, 48);");
	            } else if("VH".equals(cd)) {
	            	result.put("bg", "rgb(255, 0, 0);");
	            }
	            break;
	        }
		}
		
		return result;
	}

}
