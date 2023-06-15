package com.kist.portal.sh.rgltvltn.rest;

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
import com.kist.portal.sh.rgltvltn.service.RgltVltnService;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/she/sfty")
public class RgltVltnController {
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
	private RgltVltnService service;
	
	@GetMapping("/rgltvltn")
	public ResponseEntity<?> selectRgltVltnList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_DEPT", userInfo.getDeptCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		
		List<Map<String, ?>> list = service.selectRgltVltnList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/rgltvltn/{id}")
	public ResponseEntity<?> selectRgltVltn(@PathVariable String id, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		Map<String, String> param = new HashMap<>();
		param.put("rglt_vltn_id", id);
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		Map<String, ?> map = service.selectRgltVltn(param);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@PostMapping("/rgltvltn")
	@Transactional
	public ResponseEntity<?> insertRgltVltn(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_USR_ID", userInfo.getUsrId());
		int n = service.insertRgltVltn(param);
		
		String result = n > -1? (String) param.get("rglt_vltn_id") : Constant.FAIL;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PutMapping("/rgltvltn")
	@Transactional
	public ResponseEntity<?> updateRgltVltn(@RequestBody Map<String, Object> param, HttpSession session) {
		if("complete".equals(param.get("type"))) {
			return this.updateRgltVltnComplete(param, session);
		} else {
			UserInfo userInfo = sessionManager.get(session.getId());
		
			param.put("SESS_USR_ID", userInfo.getUsrId());
			int n = service.updateRgltVltn(param);
			
			String result = n > -1? (String) param.get("rglt_vltn_id") : Constant.FAIL;
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/rgltvltn/{id}")
	public ResponseEntity<?> deleteRgltVltn(@PathVariable String id , HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		Map<String, String> param = new HashMap<>();
		param.put("rglt_vltn_id", id);
		param.put("SESS_USR_ID", userInfo.getUsrId());
		
		int n = service.deleteRgltVltn(param);
		
		String result = (n > -1?  Constant.SUCCESS : Constant.FAIL);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PutMapping("/rgltvltn/{id}")
	public ResponseEntity<?> delRglt(@PathVariable String id , HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		Map<String, String> param = new HashMap<>();
		param.put("rglt_vltn_id", id);
		param.put("username", userInfo.getUsrId());
		
		int n = 0;
		n = service.delRglt(param);
		
		String result;
		result = (n > -1? Constant.SUCCESS : Constant.FAIL);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@SuppressWarnings("unchecked")
	private ResponseEntity<?> updateRgltVltnComplete(Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		String bizNo = param.get("rglt_vltn_id").toString();
		String usrId = userInfo.getUsrId();
		param.put("SESS_USR_ID", usrId);
		param.put("SESS_LANG", userInfo.getLangCd());
		
		// 다국어 처리
		String[] mlangNo = {
				"LB00000731","LB00000021","LB00000669","LB00000921","LB00000916"
				,"LB00000062","LB00000917","LB00000644","LB00000924","LB00000101"
				,"LB00000901","LB00000925","LB00000918","LB00000926","LB00000920"
				,"LB00000927","LB00000685","LB00000120","LB00000186","LB00000212"
				,"LB00000189","LB00000190","LB00000191","LB00000192","LB00000193"
				,"LB00000195","LB00000196","LB00000202","LB00000203"};
		
		List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo), userInfo.getLangCd());
		
		// 기본정보
		Map<String, ?>  content = service.selectRgltVltnComplete(param);
		
		List<Map<String, ?>> fileList = new ArrayList();
		
		if (content.get("rglt_vltn_atfl_no") != null && !"".equals(content.get("rglt_vltn_atfl_no"))) {
			Map<String, String> fileParam = new HashMap<>();
			fileParam.put("id", content.get("rglt_vltn_atfl_no").toString());
			try {
				fileList = fileService.selectFileList(fileParam);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// 결재 제목
		String aprv_ttl = "대외점검 정보";
		// 결재 내용
		String aprv_cntn = "<div>\r\n" +
			    "   <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000731") + "</p>\r\n" +
				"	<table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"border:none;border-collapse:collapse;word-break:break-all;font-family:&quot;Malgun Gothic&quot;, &quot;맑은 고딕&quot;;font-size:13.3333px;\">\r\n" +	
				"		<tbody>\r\n" + 
				"			<tr>\r\n" +
				"				<td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n"	+
				"					<p><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000021") + "</span><br></p>\r\n" +
				"				</td>\r\n" +
				"				<td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" +
				"					<p>" + content.get("wkpl_nm") + "<br></p>\r\n" +
				"				</td>\r\n" + 
				"				<td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"					<p><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000669") + "</span><br></p>\r\n" +
				"				</td>\r\n" +
				"				<td style=\"width: 28%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"3\">\r\n" +
				"					<p>" + content.get("rglt_chk_cl_nm") + "<br></p>\r\n" +
				"				</td>\r\n" +
				"				<td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" +
				"					<p><span style=\"font-size: 13.3333px; font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000921") + "</span><br></p>\r\n" +
				"				</td>\r\n" +
				"				<td style=\"width: 18%; height: 26px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"2\">\r\n" +
				"					<p><span style=\"font-size: 13.3333px;\">" + content.get("rglt_chk_orga_nm") + "<br></p>\r\n" +
				"				</td>\r\n" +
				"			</tr>\r\n" +
				"			<tr>\r\n" +
				"				<td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" +
				"					<p><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000916") + "</span><br></p>\r\n" +
				"				</td>\r\n" +
				"				<td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" +
				"					<p>" + content.get("rel_rglt_nm") + "<br></p>\r\n" +
				"				</td>\r\n" +
				"				<td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" +
				"					<p><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000062") + "</span><br></p>\r\n" +
				"				</td>\r\n" +
				"				<td style=\"width: 28%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"3\">\r\n" +
				"					<p>" + content.get("rglt_vltn_bsns_nm") + "<br></p>\r\n" +
				"				</td>\r\n" +
				"				<td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" +
				"					<p><span style=\"font-size: 13.3333px; font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000917") + "</span><br></p>\r\n" +
				"				</td>\r\n" +
				"				<td style=\"width: 18%; height: 26px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"2\">\r\n" +
				"					<p><span style=\"font-size: 13.3333px;\">" + content.get("rglt_vltn_chk_dt") + "</span><br></p>\r\n" +
				"				</td>\r\n" +
				"			</tr>\r\n" +
				"			<tr>\r\n" +
				"				<td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" +
				"					<p><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000644") + "</span><br></p>\r\n" +
				"				</td>\r\n" +
				"				<td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" +
				"					<p>" + content.get("rglt_vltn_wkpl_loc_nm") + "<br></p>\r\n" +
				"				</td>\r\n" +
				"				<td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" +
				"					<p><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000924") + "</span></p>\r\n" +
				"				</td>\r\n" +
				"				<td style=\"width: 28%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"3\">\r\n" +
				"					<p><span style=\"font-size: 13.3333px;\">" + content.get("kor_rglt_vltn_org_nm") + "</span><br>\r\n" +
				"				</td>\r\n" +
				"				<td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" +
				"					<p><span style=\"font-size: 13.3333px; font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000101") + "</span><br>\r\n" +
				"				</td>\r\n" +
				"				<td style=\"width: 18%; height: 26px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"2\">\r\n" +
				"					<p><span style=\"font-size: 13.3333px;\">" + content.get("rglt_vltn_usr_nm") + "</span><br>\r\n" +
				"				</td>\r\n" +
				"			</tr>\r\n" +
				"			<tr>\r\n" +
				"				<td style=\"width: 12%; height: 24px; height: 72px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" +
				"					<p><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000901") + "</span><br></p>\r\n" +
				"				</td>\r\n" +			
				"				<td colspan=\"11\"; style=\"width: 88%; height: 72px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" +
				"					<p>" + content.get("admn_dspt_txt") + "</p>\r\n" +
				"				</td>\r\n" +
				"			</tr>\r\n" +
				"			<tr>\r\n" +
				"				<td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" +
				"					<p><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000925") + "</span><br></p>" +
				"				</td>\r\n" +
				"				<td style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" +
				"					<p>" + content.get("rglt_vltn_yn") + "<br></p>\r\n" +
				"				</td>\r\n" +
				"				<td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" +
				"					<p><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000918") + "</span></p>\r\n" +
				"				</td>\r\n" +
				"				<td style=\"width: 28%; height: 25px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"3\">\r\n" +
				"					<p><span style=\"font-size: 13.3333px;\">" + content.get("rglt_dspt_cl_nm") + "</span><br></p>\r\n" +
				"				</td>\r\n" +
				"				<td style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" +
				"					<p><span style=\"font-size: 13.3333px; font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000926") + "</span><br></p>\r\n" +
				"				</td>\r\n" +
				"				<td style=\"width: 18%; height: 26px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\" colspan=\"2\">\r\n" +
				"					<p><span style=\"font-size: 13.3333px;\">" + content.get("fine_amt") + "&nbsp;/&nbsp;" + content.get("rglt_vltn_cnt") + "</span><br></p>" +
				"				</td>\r\n" +
				"			</tr>\r\n" +
				"			<tr>\r\n" +
				"				<td style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" +
				"					<p><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000920") + "</span><br></p>\r\n" +
				"				</td>\r\n" +
				"				<td colspan=\"11\"; style=\"width: 88%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" +
				"					<p>" + content.get("rglt_vltn_ttl") + "</p>\r\n" +
				"				</td>\r\n" +
				"			</tr>\r\n" +
				"			<tr>\r\n" +
				"				<td style=\"width: 12%; height: 72px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" +
				"					<p><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000927") + "</span><br></p>\r\n" +
				"				</td>\r\n" +
				"				<td colspan=11\"; style=\"width: 88%; height: 72px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" +
				"					<p>" + content.get("rglt_vltn_txt") + "</p>\r\n" +
				"				</td>\r\n" +
				"			</tr>\r\n" +
				"			<tr>\r\n" +
				"				<td style=\"width: 12%; height: 72px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" +
				"					<p><span style=\"font-weight: bold;\">" + mUtil.getMLang(mlangCntn, "LB00000685") + "</span><br></p>\r\n" +
				"				</td>\r\n" +
				"				<td colspan=\"11\"; style=\"width: 88%; height: 72px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" +
				"					<p>" + content.get("rglt_vltn_actn_rslt_txt") + "</p>\r\n" +
				"				</td>\r\n" +
				"			</tr>\r\n" +
				"		</tbody>\r\n" +
				"	</table>\r\n";
		
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
		
			if (fileList.size() > 0) {
				aprv_cntn = aprv_cntn +
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + mUtil.getMLang(mlangCntn, "LB00000120") + "</p>\r\n" + 
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
						"   <p>\r\n" +
					    "       <spam style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "바로가기" + " :&nbsp;</spam><a href=\"" + frontUrl + "/rgltvltn/rgltvltnmgntdtllup" + "/" + content.get("rglt_vltn_id") + "\" style=\"font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"target=\"_blank\">" + frontUrl + "/rgltvltn/rgltvltnmgntdtllup" + "/" + content.get("rglt_vltn_id") + "</a>\r\n" +
					    "   </p>\r\n" +
				"</div>\r\n";
		
		System.out.println(content.toString());
		
		Map<String, Object> aprvParam = new HashMap<>();
		//		결재 키 
		String aprv_no = UUID.randomUUID().toString().replace("-", "");
		aprvParam.put("aprv_no", aprv_no);
		//		결재 코드
		aprvParam.put("arpv_cd", "40");		
		// 		업무번호
		aprvParam.put("biz_no", bizNo);
		//		상신자
		aprvParam.put("aprv_usr_id", usrId);
		//		결재 상태 코드 - 결재상신
		aprvParam.put("aprv_stt_cd", "1");		
		//		후행메서드	
		aprvParam.put("aprv_mthd_nm", "aprvRgltVltnApprAf");
		//		제목
		aprvParam.put("aprv_ttl", aprv_ttl);
		//		내용
		aprvParam.put("aprv_cntn", aprv_cntn); 
		//		결재 비고	
		aprvParam.put("aprv_note", "");
		//		사원 번호	
		aprvParam.put("SESS_USR_ID", usrId);
		// 		결재 정보 table insert
		
		int n = aprvService.insertAprv(aprvParam);
		
		Map<String, Object> data = new HashMap<>();
		if(n > -1) {	
			param.put("rglt_vltn_aprv_no", aprv_no);
			service.updateRgltVltnComplete(param);
			
			data.put("Success", "Success");
			data.put("aprvNo", aprv_no);
			data.put("usrId", usrId);
		}
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
}
