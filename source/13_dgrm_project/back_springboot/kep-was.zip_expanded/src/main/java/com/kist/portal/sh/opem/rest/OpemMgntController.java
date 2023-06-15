package com.kist.portal.sh.opem.rest;

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
import com.kist.portal.sh.opem.service.OpemMgntService;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/she/hlth")
public class OpemMgntController {
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
	private OpemMgntService service;
	
	@GetMapping("/opem")
	public ResponseEntity<?> selectOpemMgntList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_DEPT", userInfo.getDeptCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		
		List<Map<String, ?>> list = service.selectOpemMgntList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/opem/{id}")
	public ResponseEntity<?> selectOpemMgnt(@PathVariable String id, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		Map<String, Object> param = new HashMap<>();
		param.put("wem_id", id);
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());

		Map<String, ?> map = service.selectOpemMgnt(param);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@PostMapping("/opem")
	@Transactional
	public ResponseEntity<?> insertOpemMgnt(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		
		param.put("SESS_USR_ID", userInfo.getUsrId());
		int n = service.insertOpemMgnt(param);
		
		String id = param.get("wkpl_id") + "-" + param.get("wem_year") + "-" + param.get("wem_sn");
		
		String result = n > -1? id : Constant.FAIL;
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@PutMapping("/opem")
	@Transactional
	public ResponseEntity<?> updateOpemMgnt(@RequestBody Map<String, Object> param, HttpSession session) {
		if("appr".equals(param.get("type"))) {
			return this.updateAppr(param, session);
		} else {
			UserInfo userInfo = sessionManager.get(session.getId());
			
			param.put("SESS_USR_ID", userInfo.getUsrId());
			int n = service.updateOpemMgnt(param);
			
			String id = param.get("wkpl_id") + "-" + param.get("wem_year") + "-" + param.get("wem_sn");
			
			String result = n > -1? id : Constant.FAIL;
			return new ResponseEntity<>(result, HttpStatus.OK);
		}
	}
	
	@DeleteMapping("/opem/{id}")
	public ResponseEntity<?> deleteOpemMgnt(@PathVariable String id , HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());

		Map<String, String> param = new HashMap<>();
		param.put("wem_id", id);
		param.put("SESS_USR_ID", userInfo.getUsrId());
		
		int n = service.deleteOpemMgnt(param);
		
		String result = (n > -1?  Constant.SUCCESS : Constant.FAIL);
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	@SuppressWarnings("unchecked")
	private ResponseEntity<?> updateAppr(Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
				
		String bizNo = param.get("wem_id").toString();
		String usrId = userInfo.getUsrId();
		param.put("SESS_USR_ID", usrId);
		param.put("SESS_LANG", userInfo.getLangCd());
		
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
		List<Map<String, String>> mlangCntn = mUtil.getMLangList(Arrays.asList(mlangNo), userInfo.getLangCd());
		
		Map<String, ?> content = service.selectOpemMgnt(param);
		List<Map<String, ?>> rsltList = (List<Map<String, ?>>) content.get("rslt_list");
		
		List<Map<String, ?>> fileList = new ArrayList();
		
		if (content.get("wem_atfl_no") != null && !"".equals(content.get("wem_atfl_no"))) {
			Map<String, String> fileParam = new HashMap<>();
			fileParam.put("id", content.get("wem_atfl_no").toString());
			try {
				fileList = fileService.selectFileList(fileParam);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// 측정결과 html 태그
		String subTag = "";
		for(int i=0; i < rsltList.size(); i++) {
			subTag += "            <tr>\r\n" + 
					   "                <td style=\"width: 5%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + (i+1) + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + rsltList.get(i).get("wem_dept_nm") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + rsltList.get(i).get("wem_pl_nm") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + rsltList.get(i).get("hmfc_nm") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + rsltList.get(i).get("hmfc_unit_nm") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + rsltList.get(i).get("hmfc_msrm_vl") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + rsltList.get(i).get("hmfc_exps_stnd_vl") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "                <td style=\"width: 15%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					   "                    <p>" + rsltList.get(i).get("hmfc_rslt_jgmt_nm") + "<br></p>\r\n" + 
					   "                </td>\r\n" + 
					   "            </tr>\r\n";
		}
		
		// 결재 제목
		String aprv_ttl = "작업환경 측정";
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
				"                    <p style=\"font-weight: bold;\">" + "의뢰자" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + content.get("wem_req_usr_nm") + "<br></p>\r\n" + 
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
				"                    <p style=\"font-weight: bold;\">" + "측정구분" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + content.get("wem_cl_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "측정연도" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + content.get("wem_year") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "반기" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + content.get("wem_cyc_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "측정기간" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + content.get("wem_bgn_dt") + " ~ " + content.get("wem_end_dt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "측정기관" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + content.get("wem_orga_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "개선대상 여부" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + content.get("wem_btrm_tgt_yn") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "측정결과 종합의견" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"8\" style=\"width: 88%; height: 72px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + content.get("wem_oval_opin_txt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "개선계획 정보" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"8\" style=\"width: 88%; height: 72px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + content.get("wem_btrm_plan_txt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 
				
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "측정 결과" + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 5%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "대상부서" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "측정장소" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "유해인자" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "단위" + "<br></p>\r\n" + 
				"                </td>\r\n" +
				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "측정값" + "<br></p>\r\n" + 
				"                </td>\r\n" +
				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "노출기준" + "<br></p>\r\n" + 
				"                </td>\r\n" +
				"                <td style=\"width: 15%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246); text-align: center;\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "판정" + "<br></p>\r\n" + 
				"                </td>\r\n" +
				"            </tr>\r\n" +
				subTag + 
				"        </tbody>\r\n" + 
				"    </table>\r\n";
				
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
						"        <spam style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "바로가기" + " :&nbsp;</spam><a href=\"" + frontUrl + "/opem/opemmgntdtllup" + "/" + content.get("wem_id") + "\" style=\"font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"target=\"_blank\">" + frontUrl + "/opem/opemmgntdtllup" + "/" + content.get("wem_id") + "</a>\r\n" + 
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
		aprvParam.put("aprv_mthd_nm", "aprvOpemMgntApprAf");
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
			param.put("wem_aprv_no", aprv_no);
			service.updateAppr(param);
			
			data.put("Success", "Success");
			data.put("aprvNo", aprv_no);
			data.put("usrId", usrId);
		}
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}

}
