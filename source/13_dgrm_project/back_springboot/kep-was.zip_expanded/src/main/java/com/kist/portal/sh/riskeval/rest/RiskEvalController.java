package com.kist.portal.sh.riskeval.rest;

import java.util.ArrayList;
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
import com.kist.portal.common.comm.service.CommService;
import com.kist.portal.common.comm.service.FileService;
import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.sh.riskeval.service.RiskEvalService;
import com.kist.portal.sh.riskeval.service.RiskEvalWkplPrcsMgntService;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/she/riskeval")
public class RiskEvalController {
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
	private FileService fileService;
	
	@Autowired
	private CommService commService;
		
	@Autowired
	private RiskEvalWkplPrcsMgntService wkplService;
	
	@Autowired
	private RiskEvalService service;
	
	@GetMapping("/basic")
	public ResponseEntity<?> selectRiskEvalBasicList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("login_id", userInfo.getUsrId());
		param.put("SESS_DEPT", userInfo.getDeptCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		List<Map<String, ?>> list = service.selectRiskEvalBasicList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@SuppressWarnings("unchecked")
	@GetMapping("/basic/{id}")
	public ResponseEntity<?> selectRiskEvalBasic(@PathVariable String id, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		Map<String, String> param = new HashMap<>();
		param.put("dgsn_apal_id", id);
		param.put("login_id", userInfo.getUsrId());
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		Map<String, ?> map = service.selectRiskEvalBasic(param);
		Map<String, String> map2 = (Map<String, String>) map;
		map2.put("profiles", profiles);
		return new ResponseEntity<>(map2, HttpStatus.OK);
	}
	
	@PostMapping("/basic")
	@Transactional
	public ResponseEntity<?> insertRiskEvalBasic(@RequestBody Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		int n = 1;
		param.put("SESS_USR_ID", userInfo.getUsrId());
		
		n *= service.insertRiskEvalBasic(param);
		
		String result = n > -1? (String) param.get("dgsn_apal_id") : Constant.FAIL;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PutMapping("/basic")
	@Transactional
	public ResponseEntity<?> updateRiskEvalBasic(@RequestBody Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
			
		int n = 1;
		param.put("SESS_USR_ID", userInfo.getUsrId());

		if("add".equals(param.get("smcl_dngp_id"))) {
			param.put("upp_dngp_id", param.get("mdcl_dngp_id"));
			param.put("dngp_nm", param.get("new_dngp_nm"));
			n *= wkplService.insertRiskEvalWkplPrcsMgnt2(param);
			param.put("smcl_dngp_id", param.get("dngp_id"));
		}
		
		n *= service.updateRiskEvalBasic(param);
			
		String result = n > -1? (String) param.get("dgsn_apal_id") : Constant.FAIL;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@DeleteMapping("/basic/{id}")
	@Transactional
	public ResponseEntity<?> deleteRiskEvalBasic(@PathVariable String id , HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		Map<String, String> param = new HashMap<>();
		param.put("dgsn_apal_id", id);
		param.put("SESS_USR_ID", userInfo.getUsrId());
		
		int n = service.deleteRiskEvalBasic(param);
		
		String result = (n > -1?  Constant.SUCCESS : Constant.FAIL);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@GetMapping("/tgtdtlwk")
	public ResponseEntity<?> selectRiskEvalDtlList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_DEPT", userInfo.getDeptCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		List<Map<String, ?>> list = service.selectRiskEvalDtlList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PutMapping("/tgtdtlwk")
	@SuppressWarnings("unchecked")
	@Transactional
	public ResponseEntity<?> saveRiskEvalTgtdtlwk(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());		
		
		List<Map<String, String>> insertList = (List<Map<String, String>>)param.get("insertList");
		List<Map<String, String>> updateList = (List<Map<String, String>>)param.get("updateList");
		List<Map<String, String>> deleteList = (List<Map<String, String>>)param.get("deleteList");
		
		int n = 0;
		
		for(Map<String, String> map : insertList) {
			map.put("SESS_USR_ID", userInfo.getUsrId());
			n *= service.insertRiskEvalTgtdtlwk(map);
		}
		
		for(Map<String, String> map : updateList) {
			map.put("SESS_USR_ID", userInfo.getUsrId());
			n *= service.updateRiskEvalTgtdtlwk(map);
		}
		
		for(Map<String, String> map : deleteList) {
			map.put("SESS_USR_ID", userInfo.getUsrId());
			n *= service.deleteRiskEvalDtl(map);
		}
		
		Map<String, String> stepParam = new HashMap<>();
		stepParam.put("dgsn_apal_id", (String) param.get("dgsn_apal_id"));
		stepParam.put("dgsn_apal_drup_stp_cd", (String) param.get("dgsn_apal_drup_stp_cd"));
		stepParam.put("dgsn_apal_prst_cd", (String) param.get("dgsn_apal_prst_cd"));
		stepParam.put("SESS_USR_ID",  userInfo.getUsrId());
		n *= service.updateRiskEvalBasicStep(stepParam);
		
		String result = n > -1? Constant.SUCCESS : Constant.FAIL;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PutMapping("/harmdngft")
	@SuppressWarnings("unchecked")
	@Transactional
	public ResponseEntity<?> saveRiskEvalHarmdngft(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());		
		
		List<Map<String, String>> updateList = (List<Map<String, String>>)param.get("updateList");
		List<Map<String, String>> deleteList = (List<Map<String, String>>)param.get("deleteList");
		
		int n = 0;
		
		for(Map<String, String> map : updateList) {
			map.put("SESS_USR_ID", userInfo.getUsrId());
			n *= service.updateRiskEvalHarmdngft(map);
		}
		
		for(Map<String, String> map : deleteList) {
			map.put("SESS_USR_ID", userInfo.getUsrId());
			n *= service.deleteRiskEvalDtl(map);
		}
		
		Map<String, String> stepParam = new HashMap<>();
		stepParam.put("dgsn_apal_id", (String) param.get("dgsn_apal_id"));
		stepParam.put("dgsn_apal_drup_stp_cd", (String) param.get("dgsn_apal_drup_stp_cd"));
		stepParam.put("dgsn_apal_prst_cd", (String) param.get("dgsn_apal_prst_cd"));
		stepParam.put("SESS_USR_ID",  userInfo.getUsrId());
		n *= service.updateRiskEvalBasicStep(stepParam);
		
		String result = n > -1? Constant.SUCCESS : Constant.FAIL;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PutMapping("/riskestmt")
	@SuppressWarnings("unchecked")
	@Transactional
	public ResponseEntity<?> saveRiskEvalRiskestmt(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());		
		
		List<Map<String, String>> updateList = (List<Map<String, String>>)param.get("updateList");
		List<Map<String, String>> deleteList = (List<Map<String, String>>)param.get("deleteList");
		
		int n = 0;
		
		for(Map<String, String> map : updateList) {
			map.put("SESS_USR_ID", userInfo.getUsrId());
			n *= service.updateRiskEvalRiskestmt(map);
		}
		
		for(Map<String, String> map : deleteList) {
			map.put("SESS_USR_ID", userInfo.getUsrId());
			n *= service.deleteRiskEvalDtl(map);
		}
		
		Map<String, String> stepParam = new HashMap<>();
		stepParam.put("dgsn_apal_id", (String) param.get("dgsn_apal_id"));
		stepParam.put("dgsn_apal_drup_stp_cd", (String) param.get("dgsn_apal_drup_stp_cd"));
		stepParam.put("dgsn_apal_prst_cd", (String) param.get("dgsn_apal_prst_cd"));
		stepParam.put("SESS_USR_ID",  userInfo.getUsrId());
		n *= service.updateRiskEvalBasicStep(stepParam);
		
		String result = n > -1? Constant.SUCCESS : Constant.FAIL;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PutMapping("/riskrdtplan")
	@SuppressWarnings("unchecked")
	@Transactional
	public ResponseEntity<?> saveRiskEvalRiskrdtplan(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());		
		
		List<Map<String, String>> updateList = (List<Map<String, String>>)param.get("updateList");
		List<Map<String, String>> deleteList = (List<Map<String, String>>)param.get("deleteList");
		
		int n = 0;
		
		for(Map<String, String> map : updateList) {
			map.put("SESS_USR_ID", userInfo.getUsrId());
			n *= service.updateRiskEvalRiskrdtplan(map);
		}
		
		for(Map<String, String> map : deleteList) {
			map.put("SESS_USR_ID", userInfo.getUsrId());
			n *= service.deleteRiskEvalDtl(map);
		}
		
		Map<String, String> stepParam = new HashMap<>();
		stepParam.put("dgsn_apal_id", (String) param.get("dgsn_apal_id"));
		stepParam.put("dgsn_apal_drup_stp_cd", (String) param.get("dgsn_apal_drup_stp_cd"));
		stepParam.put("dgsn_apal_prst_cd", (String) param.get("dgsn_apal_prst_cd"));
		stepParam.put("SESS_USR_ID",  userInfo.getUsrId());
		n *= service.updateRiskEvalBasicStep(stepParam);
		
		if((param.get("dgsn_apal_sgn_atfl_no") + "").equalsIgnoreCase("0")) {
			stepParam.put("dgsn_apal_sgn_atfl_no", null);
		}else {
			stepParam.put("dgsn_apal_sgn_atfl_no", param.get("dgsn_apal_sgn_atfl_no") + "");
		}
		n *= service.updateRiskEvalBasicFile(stepParam);
		
		String result = n > -1? Constant.SUCCESS : Constant.FAIL;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PostMapping("/riskrdtplan")
	@SuppressWarnings("unchecked")
	@Transactional
	public ResponseEntity<?> checkRiskEvalBasicAppr(@RequestBody Map<String, Object> param, HttpSession session) {
		List<String> idList = (List<String>) param.get("idList");
		if(idList == null) {
			Map<String, String> one = new HashMap<String, String>();
			one.put("dgsn_apal_id", param.get("dgsn_apal_id").toString());
			return updateRiskEvalBasicAppr(one, session);
		} else {
			return updateRiskEvalBasicMultiAppr(param, session);
		}
	}

	@Transactional
	private ResponseEntity<?> updateRiskEvalBasicAppr(Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
				
		String bizNo = param.get("dgsn_apal_id");
		String usrId = userInfo.getUsrId();
		param.put("SESS_USR_ID", usrId);
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("cd_grp_no", "SH00000084");
		
		List<Map<String, ?>> codeList = commService.selectCdList(param);				
		Map<String, ?> content = service.selectRiskEvalBasic(param);
		List<Map<String, ?>> dtlList = service.selectRiskEvalDtlList(param);				
		List<Map<String, ?>> fileList = new ArrayList<>();
		
		if (content.get("dgsn_apal_sgn_atfl_no") != null && !"".equals(content.get("dgsn_apal_sgn_atfl_no"))) {
			Map<String, String> fileParam = new HashMap<>();
			fileParam.put("id", content.get("dgsn_apal_sgn_atfl_no").toString());
			try {
				fileList = fileService.selectFileList(fileParam);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
				
		String dtlTag = "";
		String cdNm1 = "";
		String cdNm2 = "";
		String bg1 = "";
		String bg2 = "";
		int scr1 = 0;
		int scr2 = 0;
		Map<String, String> chkMap = null;
		for(int i=0; i < dtlList.size(); i++) {
			scr1 = Integer.parseInt(dtlList.get(i).get("dgsn_apal_scr").toString());
			chkMap = chkLevel(scr1, codeList);
			cdNm1 = chkMap.get("cdNm");
			bg1 = chkMap.get("bg");
			scr2 = Integer.parseInt(dtlList.get(i).get("btt_af_dgsn_apal_scr").toString());
			chkMap = chkLevel(scr2, codeList);
			cdNm2 = chkMap.get("cdNm");
			bg2 = chkMap.get("bg");
			dtlTag +=  "            <tr>\r\n" + 
				    "                <td style=\"padding: 0px 3px;\">\r\n" + 
				    "                    <p>" + (i+1) + "<br></p>\r\n" + 
				    "                </td>\r\n" + 
				    "                <td style=\"padding: 0px 3px;\">\r\n" + 
				    "                    <p><a href=\"" + frontUrl + "/riskeval/riskevalriskrdtplanlup" + "/" + content.get("dgsn_apal_id") + "\" style=\"font-family: Malgun Gothic; font-size: medium;\"target=\"_blank\">" + content.get("dgsn_apal_id")  + "-" + dtlList.get(i).get("sort_sn") + "</a><br></p>\r\n" + 
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
				    "            </tr>\r\n";
		}
		
		// 첨부파일 html 태그
		String subFile = "";
		if (fileList.size() > 0) {
			for(int i=0; i<fileList.size(); i++) {
				subFile += "<tr>\r\n" + 
						   "	<td style=\"width: 5%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
						   "    	<p>" + (i+1) + "<br></p>\r\n" + 
						   "    </td>\r\n" + 
						   "    <td style=\"width: 95%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
						   "		<p>" + "<a href=\"" + serverUrl + "/api/v1/public/downloadSeq/" + fileList.get(i).get("atfl_no") + "-" +  fileList.get(i).get("atfl_seq") + "\">" + fileList.get(i).get("name") + "</a>" + "<br></p>\r\n" + 
						   "    </td>\r\n" + 
						   "</tr>\r\n";
			}
		}
		
		// 결재 제목
		String aprv_ttl = "위험성평가 검토/실시 요청";
		// 결재 내용
		String aprv_cntn = "<div>\r\n" + 
				"    <p style=\"color: rgb(0, 0, 0); font-family: Malgun Gothic; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: Malgun Gothic; font-size: medium;\">" + "위험성평가표" + "</p>\r\n" + 
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
				"   		<col style=\"width: 100px;\"/> \r\n" +
				"   		<col style=\"width: 180px;\"/> \r\n" +
				"   		<col style=\"width: 100px;\"/> \r\n" +
				"   		<col style=\"width: 200px;\"/> \r\n" +
				"   		<col style=\"width: 100px;\"/> \r\n" +
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
			    "            </tr>\r\n" + 
				"        </thead>\r\n" + 
				"        <tbody>\r\n" + 
				dtlTag + 
				"		</tbody>\r\n" + 
				"	</table>\r\n";
		
				if (fileList.size() > 0) {
				aprv_cntn = aprv_cntn +
				"	<p style=\"color: rgb(0, 0, 0); font-family: Malgun Gothic; font-size: medium;\"><br></p>\r\n" + 
				"   <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: Malgun Gothic; font-size: medium;\">" + "첨부파일 목록" + "</p>\r\n" + 
				"   <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"       <tbody>\r\n" + 
				"          <tr>\r\n" + 
				"               <td style=\"width: 5%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                   <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
				"               </td>\r\n" + 
				"               <td style=\"width: 95%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                   <p style=\"font-weight: bold;\">" + "파일명" + "<br></p>\r\n" + 
				"               </td>\r\n" + 
				"           </tr>\r\n" +
				subFile + 
				"       </tbody>\r\n" + 
				"	</table>\r\n";
				}
				aprv_cntn = aprv_cntn +
						"    <p>\r\n" + 
						"        <spam style=\"color: rgb(0, 0, 0); font-family: Malgun Gothic; font-size: medium;\">" + "바로가기" + " :&nbsp;</spam><a href=\"" + frontUrl + "/riskeval/riskevalriskrdtplanlup" + "/" + content.get("dgsn_apal_id") + "\" style=\"font-family: Malgun Gothic; font-size: medium;\"target=\"_blank\">" + frontUrl + "/riskeval/riskevalriskrdtplanlup" + "/" + content.get("dgsn_apal_id") + "</a>\r\n" + 
						"    </p>\r\n" +
				"</div>";
		
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
		aprvParam.put("aprv_mthd_nm", "aprvRiskEvalBasicApprAf");
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
			param.put("dgsn_apal_aprv_no", aprv_no);
			param.put("dgsn_apal_drup_stp_cd", "4");
			service.updateRiskEvalBasicAppr(param);
			
			data.put("Success", "Success");
			data.put("aprvNo", aprv_no);
			data.put("usrId", usrId);
		}
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	private ResponseEntity<?> updateRiskEvalBasicMultiAppr(Map<String, Object> multiParam, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
				
		Map<String, String> param = new HashMap<>();
		String bizNo = "riskevalmulti";
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
		int scr1 = 0;
		int scr2 = 0;
		int cnt = 1;
		Map<String, String> chkMap = null;
		
		List<String> idList = (List<String>) multiParam.get("idList");
		for(String id : idList) {
			param.put("dgsn_apal_id", id);
			Map<String, ?> content = service.selectRiskEvalBasic(param);
			List<Map<String, ?>> dtlList = service.selectRiskEvalDtlList(param);
			
			for(int i=0; i < dtlList.size(); i++) {
				String ddmt = "";
				if(dtlList.get(i).get("dgsn_dec_meas_txt") != null) {
					ddmt = dtlList.get(i).get("dgsn_dec_meas_txt").toString().replaceAll("\n", "<br/>");
				}

				String dbren = "";
				if(dtlList.get(i).get("dgsn_btt_rspn_usr_nm") != null) {
					dbren = dtlList.get(i).get("dgsn_btt_rspn_usr_nm").toString();
				}

				String dbcsd = "";
				if(dtlList.get(i).get("dgsn_btt_cmpy_schd_dt") != null) {
					dbcsd = dtlList.get(i).get("dgsn_btt_cmpy_schd_dt").toString();
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
					    "                    <p><a href=\"" + frontUrl + "/riskeval/riskevalriskrdtplanlup" + "/" + content.get("dgsn_apal_id") + "\" style=\"font-family: Malgun Gothic; font-size: medium;\"target=\"_blank\">" + content.get("dgsn_apal_id")  + "-" + dtlList.get(i).get("sort_sn") + "</a><br></p>\r\n" + 
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
					    "                    <p>" + ddmt + "<br></p>\r\n" + 
					    "                </td>\r\n" + 
					    "                <td style=\"padding: 0px 3px; color:rgb(255, 255, 255); background:" + bg2 + "\">\r\n" + 
					    "                    <p>" + cdNm2 + "<br></p>\r\n" + 
					    "                </td>\r\n" + 
					    "                <td style=\"padding: 0px 3px;\">\r\n" + 
					    "                    <p>" + dbren + "<br></p>\r\n" + 
					    "                </td>\r\n" + 
					    "                <td style=\"padding: 0px 3px;\">\r\n" + 
					    "                    <p>" + dbcsd + "<br></p>\r\n" + 
					    "                </td>\r\n" + 
					    "            </tr>\r\n";
			}
		}
		
		// 결재 제목
		String aprv_ttl = "위험성평가 검토/실시 보고";
		// 결재 내용
		String aprv_cntn = "<div>\r\n" + 
				"    <p style=\"color: rgb(0, 0, 0); font-family: Malgun Gothic; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: Malgun Gothic; font-size: medium;\">" + "위험성평가표" + "</p>\r\n" + 
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
				"   		<col style=\"width: 100px;\"/> \r\n" +
				"   		<col style=\"width: 180px;\"/> \r\n" +
				"   		<col style=\"width: 100px;\"/> \r\n" +
				"   		<col style=\"width: 200px;\"/> \r\n" +
				"   		<col style=\"width: 100px;\"/> \r\n" +
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
			    "            </tr>\r\n" + 
				"        </thead>\r\n" + 
				"        <tbody>\r\n" + 
				dtlTag + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 
				"</div><br/>";
		
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
		aprvParam.put("aprv_mthd_nm", "aprvRiskEvalAftActFinalApprAf");
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
				param.put("dgsn_apal_id", id);
				param.put("tsnd_aprv_no", aprv_no);
				service.updateRiskEvalBasicAppr(param);
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

	@PostMapping("/riskEvalCopy")
	@Transactional
	public ResponseEntity<?> insertRiskEvalCopy(@RequestBody Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		int n = 1;
		param.put("SESS_USR_ID", userInfo.getUsrId());
		
		n *= service.insertRiskEvalBasicCopy(param);
		if(n > 0) {
			n *= service.insertRiskEvalTgtdtlwkCopy(param);
		}
		String result = n > -1? (String) param.get("dgsn_apal_id") : Constant.FAIL;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
