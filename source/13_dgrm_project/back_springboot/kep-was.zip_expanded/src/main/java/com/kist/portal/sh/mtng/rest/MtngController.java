package com.kist.portal.sh.mtng.rest;

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
import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.common.utils.MultiLanguageUtil;
import com.kist.portal.sh.mtng.service.MtngService;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/she/sfty")
public class MtngController {
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
	private FileService fileService;
	
	@Autowired
	private MtngService service;
	
	@GetMapping("/mtng")
	public ResponseEntity<?> selectMtngList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_DEPT", userInfo.getDeptCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		List<Map<String, ?>> list = service.selectMtngList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/mtng/{id}")
	public ResponseEntity<?> selectMtng(@PathVariable String id, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		Map<String, Object> param = new HashMap<>();
		param.put("mtng_id", id);
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		Map<String, ?> map = service.selectMtng(param);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@PostMapping("/mtng")
	@Transactional
	public ResponseEntity<?> insertMtng(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_USR_ID", userInfo.getUsrId());
		int n = service.insertMtng(param);
		
		String result = n > -1? (String) param.get("mtng_id") : Constant.FAIL;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PutMapping("/mtng")
	@Transactional
	public ResponseEntity<?> updateMtng(@RequestBody Map<String, Object> param, HttpSession session) {
		if("appr".equals(param.get("type"))) {
			return this.updateAppr(param, session);
		} else {
			UserInfo userInfo = sessionManager.get(session.getId());
			
			param.put("SESS_USR_ID", userInfo.getUsrId());
			int n = service.updateMtng(param);
			
			String result = n > -1? (String) param.get("mtng_id") : Constant.FAIL;
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/mtng/{id}")
	public ResponseEntity<?> deleteMtng(@PathVariable String id , HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		Map<String, String> param = new HashMap<>();
		param.put("mtng_id", id);
		param.put("SESS_USR_ID", userInfo.getUsrId());
		
		int n = service.deleteMtng(param);
		
		String result = (n > -1?  Constant.SUCCESS : Constant.FAIL);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// 회의체 관리 상세 삭제
	@PutMapping("/mtng/{id}")
	public ResponseEntity<?> delMtng(@PathVariable String id , HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		Map<String, String> param = new HashMap<>();
		param.put("mtng_id", id);
		param.put("username", userInfo.getUsrId());
		
		int n = service.delMtng(param);
		
		String result = (n > -1?  Constant.SUCCESS : Constant.FAIL);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@SuppressWarnings("unchecked")
	private ResponseEntity<?> updateAppr(Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
				
		String bizNo = param.get("mtng_id").toString();
		String usrId = userInfo.getUsrId();
		param.put("SESS_USR_ID", usrId);
		param.put("SESS_LANG", userInfo.getLangCd());
		
		// 다국어 처리
		String[] mlangNo = {
				"LB00000719","LB00000731","LB00000021","LB00000728","LB00000733"
				,"LB00000730","LB00000755","LB00000726","LB00000756","LB00000727"
				,"LB00000759","LB00000761","LB00000762","LB00000120","LB00000764"
				,"LB00000765","LB00000741","LB00000701","LB00000766"};
		
		List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo), userInfo.getLangCd());
		
		Map<String, ?> content = service.selectMtng(param);
		List<Map<String, ?>> sbjcList = (List<Map<String, ?>>) content.get("sbjc_list");
		List<Map<String, ?>> atndList = (List<Map<String, ?>>) content.get("atnd_list");
				
		List<Map<String, ?>> fileList = new ArrayList();
		
		if (content.get("mtng_rslt_atfl_no") != null && !"".equals(content.get("mtng_rslt_atfl_no"))) {
			Map<String, String> fileParam = new HashMap<>();
			fileParam.put("id", content.get("mtng_rslt_atfl_no").toString());
			try {
				fileList = fileService.selectFileList(fileParam);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// 회의안건 html 태그
		String sbjcTag = "";
		for(int i=0; i < sbjcList.size(); i++) {
			sbjcTag += "            <tr>\r\n" + 
					   "                <td style=\"width: 5%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + (i+1) + "<br></p>\r\n" + 
					   "                </td>\r\n" +
					   "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + sbjcList.get(i).get("mtng_sbjc_txt") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + sbjcList.get(i).get("mtng_sbjc_rslt_txt") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "            </tr>\r\n";
		}
		
		// 회의참석자 html 태그
		String atndTag = "";
		for(int i=0; i < atndList.size(); i++) {
			atndTag += "            <tr>\r\n" + 
					   "                <td style=\"width: 5%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + (i+1) + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + atndList.get(i).get("mtng_atnd_cl_nm") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + atndList.get(i).get("mtng_atnd_nm") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + atndList.get(i).get("mtng_atnd_pstn_nm") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"width: 30%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + atndList.get(i).get("mtng_atnd_dept_nm") + "<br></p>\r\n" + 
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
						  "						<p>" + "<a href=\"" + serverUrl + "/api/v1/public/downloadSeq/" + fileList.get(i).get("atfl_no") + "-" +  fileList.get(i).get("atfl_seq") + "\">" + fileList.get(i).get("name") + "</a>" + "<br></p>\r\n" + 
						  "                </td>\r\n" + 
						  "            </tr>\r\n";
			}
		}
		
		// 결재 제목
		String aprv_ttl = content.get("mtng_ttl").toString();
		// 결재 내용
		String aprv_cntn = "<div>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "기본정보" + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "사업장" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + content.get("wkpl_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "회의구분" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + content.get("mtng_cl_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "정기/임시 구분" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + content.get("mtng_cyc_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 				
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "회의명" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"5\" style=\"width: 58%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + content.get("mtng_ttl") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "회의장소" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + content.get("mtng_pl_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "회의일자" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + content.get("mtng_dt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "회의시간" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + content.get("mtng_bgn_hm") + " ~ " + content.get("mtng_end_hm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "최종 수정자" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + content.get("lsch_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 	
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "회의 결과" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"8\" style=\"width: 88%; height: 72px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + content.get("mtng_rslt_txt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 			
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 
				
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "회의 안건" + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 5%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
				"                </td>\r\n" +
				"                <td style=\"width: 40%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "의제" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 40%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "결과" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				sbjcTag + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 
				
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "회의 참석자" + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 5%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "구분" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "성명" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "직책" + "<br></p>\r\n" + 
				"                </td>\r\n" +
				"                <td style=\"width: 30%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "회사" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				atndTag + 
				"        </tbody>\r\n" + 
				"    </table>\r\n";
		
				if (fileList.size() > 0) {
				aprv_cntn = aprv_cntn +
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "첨부파일 목록" + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 5%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 95%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "파일명" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				subFile + 
				"        </tbody>\r\n" + 
				"    </table>\r\n";
				}
				aprv_cntn = aprv_cntn +
						"    <p>\r\n" + 
						"        <spam style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "바로가기" + " :&nbsp;</spam><a href=\"" + frontUrl + "/mtng/mtngmgntdtllup" + "/" + content.get("mtng_id") + "\" style=\"font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"target=\"_blank\">" + frontUrl + "/mtng/mtngmgntdtllup" + "/" + content.get("mtng_id") + "</a>\r\n" + 
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
		aprvParam.put("aprv_mthd_nm", "aprvMtngApprAf");
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
			param.put("mtng_rslt_aprv_no", aprv_no);
			service.updateAppr(param);
			
			data.put("Success", "Success");
			data.put("aprvNo", aprv_no);
			data.put("usrId", usrId);
		}
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

}
