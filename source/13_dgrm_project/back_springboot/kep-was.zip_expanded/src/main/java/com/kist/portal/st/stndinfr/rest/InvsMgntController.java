package com.kist.portal.st.stndinfr.rest;

import java.text.DecimalFormat;
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
import com.kist.portal.common.chems.service.CdService;
import com.kist.portal.common.comm.service.FileService;
import com.kist.portal.common.login.model.UserInfo;
import com.kist.portal.common.utils.Constant;
import com.kist.portal.common.utils.MultiLanguageUtil;
import com.kist.portal.st.stndinfr.service.InvsMgntService;
import com.kist.portal.sysconf.manager.SessionManager;

@RestController
@RequestMapping("/api/v1/st/stndinfr")
public class InvsMgntController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SessionManager sessionManager;
	@Autowired
	private CdService cdService;
	
	@Autowired
	private InvsMgntService service;
	
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
	
	// 사업장별 투자 관리 목록
	@GetMapping("/invs-mgnt-list")
	public ResponseEntity<?> selectInvsMgntList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		List<Map<String, ?>> list = service.selectInvsMgntList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	// 사업장별 투자 관리 상세 조회
	@GetMapping("/invs-mgnt-dtl/{id}")
	public ResponseEntity<?> selectInvsMgntDtlInfo(@PathVariable String id, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		String[] key = id.split("-");
		Map<String, String> param = new HashMap<>();
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		param.put("wkpl_id", key[0]);
		param.put("invs_year", key[1]);
		
		// 기본정보
		List<Map<String, ?>> basicInfo = service.selectInvsMgntDtlInfo(param);
		// 계획 정보
		List<Map<String, ?>> planList = service.selectInvsMgntDtlPlan(param);
		// 실적 정보
		List<Map<String, ?>> rsltList = service.selectInvsMgntDtlRslt(param);
		// 투자계획대비집행 정보
		List<Map<String, ?>> totList = service.selectInvsMgntDtlTotRslt(param);
		
		// 엑셀업로드 양식 정보
		List<Map<String, ?>> excel = service.selectInvsMgntExcelTemp(param);
		
		
		Map<String, List<Map<String, ?>>> data = new HashMap<>();
		data.put("basic", basicInfo);
		data.put("plan", planList);
		data.put("rslt", rsltList);
		data.put("tot", totList);
		data.put("excel", excel);
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
	
	// 사업장별 투자 관리 상세 삭제
	@DeleteMapping("/invs-mgnt-dtl/{id}")
	@Transactional
	public ResponseEntity<?> updateInvsMgntDtlInfoDel(@PathVariable String id, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		String[] key = id.split("-");
		Map<String, String> param = new HashMap<>();
		param.put("wkpl_id", key[0]);
		param.put("invs_year", key[1]);
		
		int n = 0;
		int m = 0;
		int j = 0;
		n = service.deleteInvsMgntDtlPlanAll(param);
		m = service.deleteInvsMgntDtlRsltAll(param);
		j = service.deleteInvsMgntDtlInfoDel(param);
		
		String result;
		result = (n > -1? (m > -1? (j > -1? Constant.SUCCESS : Constant.FAIL) : Constant.FAIL) : Constant.FAIL);
		
		return new ResponseEntity<>(result, HttpStatus.OK);
	}
	
	// 사업장별 투자 관리 상세 저장
	@PutMapping("/invs-mgnt-dtl")
	@Transactional
	public ResponseEntity<?> insertInvsMgntDtlInfo(@RequestBody Map<String, Object> param, HttpSession session) {
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
		int p = 0;
		
		// 기본정보
		List<Map<String, String>> infoRow = (List<Map<String, String>>)param.get("infoRow");
		
		// 계획 정보
		//insert, update, delete 데이터 초기화
		List<Map<String, String>> insertPlanList = (List<Map<String, String>>)param.get("insertPlanRow");
		List<Map<String, String>> updatePlanList = (List<Map<String, String>>)param.get("updatePlanRow");
		List<Map<String, String>> deletePlanList = (List<Map<String, String>>)param.get("deletePlanRow");
		
		// 실적 정보
		//insert, update, delete 데이터 초기화
		List<Map<String, String>> insertRsltList = (List<Map<String, String>>)param.get("insertRsltRow");
		List<Map<String, String>> updateRsltList = (List<Map<String, String>>)param.get("updateRsltRow");
		List<Map<String, String>> deleteRsltList = (List<Map<String, String>>)param.get("deleteRsltRow");
		
		Map<String, String> tempInfo = infoRow.get(0);
		String passYn = "Y";
		if ("Y".equals(tempInfo.get("new_yn"))) {
			passYn = service.selectInvsMgntPass(tempInfo);
		}
		
		// 중복된 키가 있으면 패스
		if ("N".equals(passYn)) {
			return new ResponseEntity<>("OverlapFail", HttpStatus.OK);
		}
		
		// 기본정보 저장
		tempInfo.put("username", userInfo.getUsrId());
		n = service.insertInvsMgntDtlInfo(tempInfo);
		
		// 계획 정보
		if (deletePlanList != null) {
			// 삭제
			for(int i = 0; i < deletePlanList.size(); i++) {
				Map<String, String> tempList = deletePlanList.get(i);
				k = service.deleteInvsMgntDtlPlan(tempList);
			}
		}
		
		if (insertPlanList != null) {
			// String passPlanYn = "Y";
			// 인서트
			for(int i = 0; i < insertPlanList.size(); i++) {
				Map<String, String> tempList = insertPlanList.get(i);
				tempList.put("wkpl_id", tempInfo.get("wkpl_id"));
				tempList.put("invs_year", tempInfo.get("invs_year"));
				tempList.put("username", userInfo.getUsrId());
				if ("".equals(tempList.get("invs_item_reg_sn")) || null == tempList.get("invs_item_reg_sn")) {
					tempList.put("invs_item_reg_sn", "0");
//					passPlanYn = service.selectInvsMgntPlanPass(tempList);
//					// 중복된 키가 있으면 패스
//					if ("N".equals(passPlanYn)) {
//						return new ResponseEntity<>("OverlapFail", HttpStatus.OK);
//					}
				}
				
				m = service.insertInvsMgntDtlPlan(tempList);
			}
		}
		
		if (updatePlanList != null) {
			// 업데이트
			for (int i = 0; i < updatePlanList.size(); i++) {
				Map<String, String> tempList = updatePlanList.get(i);
				tempList.put("username", userInfo.getUsrId());
				j = service.insertInvsMgntDtlPlan(tempList);
			}
		}
		
		
		// 실적 정보
		if (deleteRsltList != null) {
			// 삭제
			for(int i = 0; i < deleteRsltList.size(); i++) {
				Map<String, String> tempList = deleteRsltList.get(i);
				p = service.deleteInvsMgntDtlRslt(tempList);
			}
		}
		
		if (insertRsltList != null) {
			// 인서트
			for(int i = 0; i < insertRsltList.size(); i++) {
				Map<String, String> tempList = insertRsltList.get(i);
				tempList.put("username", userInfo.getUsrId());
				tempList.put("wkpl_id", tempInfo.get("wkpl_id"));
				tempList.put("invs_year", tempInfo.get("invs_year"));
				if ("".equals(tempList.get("invs_item_reg_sn")) || null == tempList.get("invs_item_reg_sn")) {
					tempList.put("invs_item_reg_sn", "0");
				}
				
				l = service.insertInvsMgntDtlRslt(tempList);
			}
		}
		
		if (updateRsltList != null) {
			// 업데이트
			for (int i = 0; i < updateRsltList.size(); i++) {
				Map<String, String> tempList = updateRsltList.get(i);
				tempList.put("username", userInfo.getUsrId());
				o = service.insertInvsMgntDtlRslt(tempList);
			}
		}
		
		n = service.updateInvsMgntInvsAmt(tempInfo);
		
		Map<String, String> resultMap = new HashMap<>();
		
		String result;
		result = (n > -1? (m > -1? (j > -1? (k > -1? (l > -1? (o > -1? (p > -1? Constant.SUCCESS : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL);
		resultMap.put("result", result);
		resultMap.put("key1", tempInfo.get("wkpl_id"));
		resultMap.put("key2", tempInfo.get("invs_year"));
		
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
	
	// 사업장별 투자 관리 실적 저장
	@PutMapping("/invs-mgnt-dtl-rslt")
	@Transactional
	public ResponseEntity<?> insertInvsMgntDtlRslt(@RequestBody Map<String, Object> param, HttpSession session) {
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
		int p = 0;
		
		
		// 실적 정보
		//insert, update, delete 데이터 초기화
		List<Map<String, String>> insertRsltList = (List<Map<String, String>>)param.get("insertRsltRow");
		List<Map<String, String>> updateRsltList = (List<Map<String, String>>)param.get("updateRsltRow");
		List<Map<String, String>> deleteRsltList = (List<Map<String, String>>)param.get("deleteRsltRow");
		
		
		// 실적 정보
		if (deleteRsltList != null) {
			// 삭제
			for(int i = 0; i < deleteRsltList.size(); i++) {
				Map<String, String> tempList = deleteRsltList.get(i);
				p = service.deleteInvsMgntDtlRslt(tempList);
			}
		}
		
		if (insertRsltList != null) {
			// 인서트
			for(int i = 0; i < insertRsltList.size(); i++) {
				Map<String, String> tempList = insertRsltList.get(i);
				tempList.put("username", userInfo.getUsrId());
				l = service.insertInvsMgntDtlRslt(tempList);
			}
		}
		
		if (updateRsltList != null) {
			// 업데이트
			for (int i = 0; i < updateRsltList.size(); i++) {
				Map<String, String> tempList = updateRsltList.get(i);
				tempList.put("username", userInfo.getUsrId());
				o = service.insertInvsMgntDtlRslt(tempList);
			}
		}
		
		Map<String, String> resultMap = new HashMap<>();
		
		String result;
		result = (n > -1? (m > -1? (j > -1? (k > -1? (l > -1? (o > -1? (p > -1? Constant.SUCCESS : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL) : Constant.FAIL);
		resultMap.put("result", result);
		
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
	
	// 사업장별 투자 관리 결재상신
	@PutMapping("/invs-mgnt-dtl-appr")
	@Transactional
	public ResponseEntity<?> updateInvsMgntAppr(@RequestBody Map<String, Object> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		int n = 0;
		// 기본정보
		List<Map<String, String>> infoRow = (List<Map<String, String>>)param.get("infoRow");
		
		Map<String, String> tempInfo = infoRow.get(0);
		
		tempInfo.put("username", userInfo.getUsrId());
		n = service.updateInvsMgntAppr(tempInfo);
		
		
		Map<String, String> resultMap = new HashMap<>();
		
		String result;
		result = (n > -1? Constant.SUCCESS : Constant.FAIL);
		resultMap.put("result", result);
		
		return new ResponseEntity<>(resultMap, HttpStatus.OK);
	}
	
	// 전사 투자 상세현황 목록
	@GetMapping("/invs-mgnt-tot-list")
	public ResponseEntity<?> selectInvsMgntTotList(@RequestParam Map<String, String> param, HttpSession session) {
		UserInfo userInfo = sessionManager.get(session.getId());
		param.put("SESS_LANG", userInfo.getLangCd());
		param.put("SESS_TIME_ZONE", userInfo.getTimeZone());
		List<Map<String, ?>> list = service.selectInvsMgntTotList(param);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	// 사업장별 투자 관리 결재상신
	@PutMapping("/invs-mgnt-dtl-appr-af")
	@Transactional
	public ResponseEntity<?> updateInvsMgntDtlAppr(@RequestBody Map<String, Object> param, HttpSession session) {
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
		List<Map<String, ?>> basicInfo = service.selectInvsMgntDtlInfo(schParam);
		// 투자계획대비집행 정보
		List<Map<String, ?>> totList = service.selectInvsMgntDtlTotRslt(schParam);
		// 계획 정보
		List<Map<String, ?>> planList = service.selectInvsMgntDtlPlan(schParam);
		// 실적 정보
		List<Map<String, ?>> rsltList = service.selectInvsMgntDtlRslt(schParam);
		
		Map<String, ?> tempBasic = basicInfo.get(0);
		DecimalFormat decFormat = new DecimalFormat("###,###");
		List<Map<String, ?>> fileList = new ArrayList();
				
		if (tempBasic.get("invs_atfl_no") != null && !"".equals(tempBasic.get("invs_atfl_no"))) {
			Map<String, String> fileParam = new HashMap<>();
			fileParam.put("id", tempBasic.get("invs_atfl_no").toString());
			try {
				fileList = fileService.selectFileList(fileParam);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// 투자계획대비집행 html 태그
		String subTag = "";
		String subPlan = "";
		String subRslt = "";
		
		// 투자계획대비집행 html 태그
		for(int i=0; i<totList.size(); i++) {
			    subTag += "            <tr>\r\n" + 
					      "                <td style=\"width: 5%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + (i+1) + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 7%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					      "                    <p>" + totList.get(i).get("invs_cat_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 6%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: right;\">\r\n" + 
					      "                    <p>" + decFormat.format(totList.get(i).get("invs_item_amt")) + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 6%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: right;\">\r\n" + 
					      "                    <p>" + decFormat.format(totList.get(i).get("prfr_amt_tot")) + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 6%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: right;\">\r\n" + 
					      "                    <p>" + decFormat.format(totList.get(i).get("amt_per")) + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 6%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: right;\">\r\n" + 
					      "                    <p>" + decFormat.format(totList.get(i).get("prfr_amt_01")) + "<br></p>\r\n" + 
					      "                </td>\r\n" +
					      "                <td style=\"width: 6%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: right;\">\r\n" + 
					      "                    <p>" + decFormat.format(totList.get(i).get("prfr_amt_02")) + "<br></p>\r\n" + 
					      "                </td>\r\n" +
					      "                <td style=\"width: 6%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: right;\">\r\n" + 
					      "                    <p>" + decFormat.format(totList.get(i).get("prfr_amt_03")) + "<br></p>\r\n" + 
					      "                </td>\r\n" +
					      "                <td style=\"width: 6%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: right;\">\r\n" + 
					      "                    <p>" + decFormat.format(totList.get(i).get("prfr_amt_04")) + "<br></p>\r\n" + 
					      "                </td>\r\n" +
					      "                <td style=\"width: 6%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: right;\">\r\n" + 
					      "                    <p>" + decFormat.format(totList.get(i).get("prfr_amt_05")) + "<br></p>\r\n" + 
					      "                </td>\r\n" +
					      "                <td style=\"width: 6%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: right;\">\r\n" + 
					      "                    <p>" + decFormat.format(totList.get(i).get("prfr_amt_06")) + "<br></p>\r\n" + 
					      "                </td>\r\n" +
					      "                <td style=\"width: 6%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: right;\">\r\n" + 
					      "                    <p>" + decFormat.format(totList.get(i).get("prfr_amt_07")) + "<br></p>\r\n" + 
					      "                </td>\r\n" +
					      "                <td style=\"width: 6%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: right;\">\r\n" + 
					      "                    <p>" + decFormat.format(totList.get(i).get("prfr_amt_08")) + "<br></p>\r\n" + 
					      "                </td>\r\n" +
					      "                <td style=\"width: 6%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: right;\">\r\n" + 
					      "                    <p>" + decFormat.format(totList.get(i).get("prfr_amt_09")) + "<br></p>\r\n" + 
					      "                </td>\r\n" +
					      "                <td style=\"width: 6%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: right;\">\r\n" + 
					      "                    <p>" + decFormat.format(totList.get(i).get("prfr_amt_10")) + "<br></p>\r\n" + 
					      "                </td>\r\n" +
					      "                <td style=\"width: 6%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: right;\">\r\n" + 
					      "                    <p>" + decFormat.format(totList.get(i).get("prfr_amt_11")) + "<br></p>\r\n" + 
					      "                </td>\r\n" +
					      "                <td style=\"width: 6%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: right;\">\r\n" + 
					      "                    <p>" + decFormat.format(totList.get(i).get("prfr_amt_12")) + "<br></p>\r\n" + 
					      "                </td>\r\n" +
					      "            </tr>\r\n";
		}
		
		// 계획내용 html 태그
		for(int i=0; i<planList.size(); i++) {
			    subPlan += "            <tr>\r\n" + 
					      "                <td style=\"width: 5%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + (i+1) + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					      "                    <p>" + planList.get(i).get("invs_cat_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + planList.get(i).get("shcdsd_wkpl_invs_item_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 45%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					      "                    <p>" + planList.get(i).get("invs_item_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: right;\">\r\n" + 
					      "                    <p>" + decFormat.format(planList.get(i).get("invs_item_amt")) + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "            </tr>\r\n";
		}
		
		// 집행내용 html 태그
		for(int i=0; i<rsltList.size(); i++) {
			    subRslt += "            <tr>\r\n" + 
					      "                <td style=\"width: 5%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-left: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + (i+1) + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + rsltList.get(i).get("prfr_reg_mth_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					      "                    <p>" + rsltList.get(i).get("invs_cat_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: center;\">\r\n" + 
					      "                    <p>" + rsltList.get(i).get("invs_stpl_item_nm") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 35%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
					      "                    <p>" + rsltList.get(i).get("prfr_txt") + "<br></p>\r\n" + 
					      "                </td>\r\n" + 
					      "                <td style=\"width: 10%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; text-align: right;\">\r\n" + 
					      "                    <p>" + decFormat.format(rsltList.get(i).get("prfr_amt")) + "<br></p>\r\n" + 
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
		
		// 페이지 구분
		String sub_title = "";
		// 결제 제목 - 화학물질 규제 검토(자재코드 자재명)
		String aprv_ttl = "";
		// 결제 내용
		String aprv_cntn = "";
				
		
		// 결제 제목 - 년도 + 사업장명
		aprv_ttl = tempBasic.get("invs_year").toString() + ' ' + tempBasic.get("wkpl_nm").toString();
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
				"                    <p style=\"font-weight: bold;\">" + "투자년도" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempBasic.get("invs_year") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "최종 수정자" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempBasic.get("lsch_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "연간 투자금액" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + decFormat.format(tempBasic.get("yrly_invs_amt")) + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "진행상태" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 28%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempBasic.get("invs_prst_nm") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "최종 수정일시" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"2\" style=\"width: 18%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempBasic.get("upt_dt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "관련지침" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"8\" style=\"width: 58%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempBasic.get("invs_rel_guid_txt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"            <tr>\r\n" + 
				"                <td colspan=\"1\" style=\"width: 12%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "설명" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td colspan=\"8\" style=\"width: 58%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px;\">\r\n" + 
				"                    <p>" + tempBasic.get("wkpl_note_txt") + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 

				
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "투자 계획대비집행 목록" + "</p>" + 
				"	 <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: 12px; text-align: right;\">단위 : 천원</p>" + "\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 3%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 7%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "카테고리" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 6%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "계획금액" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 6%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "집행누계" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 6%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "실적(%)" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 6%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "1월" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 6%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "2월" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 6%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "3월" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 6%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "4월" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 6%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "5월" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 6%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "6월" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 6%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "7월" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 6%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "8월" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 6%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "9월" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 6%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "10월" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 6%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "11월" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 6%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "12월" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				subTag + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 
				
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "연간 계획 내용" + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 5%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 20%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "카테고리" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "약정항목" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 45%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "계획내용" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "계획금액" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				subPlan + 
				"        </tbody>\r\n" + 
				"    </table>\r\n" + 
				
				"    <p style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"><br></p>\r\n" + 
				"    <p style=\"font-weight: bold; color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "월별 집행 내용" + "</p>\r\n" + 
				"    <table class=\"txc-table\" width=\"100%\" cellspacing=\"0\" cellpadding=\"0\" style=\"border: none; border-collapse: collapse; word-break: break-all; font-size: 13.3333px;\">\r\n" + 
				"        <tbody>\r\n" + 
				"            <tr>\r\n" + 
				"                <td style=\"width: 5%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "No." + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "실적 월" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 20%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "카테고리" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 20%; height: 24px; border-bottom: 1px solid rgb(204, 204, 204); border-right: 1px solid rgb(204, 204, 204); border-top: 1px solid rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "약정항목" + "</p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 35%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "집행내용" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"                <td style=\"width: 10%; height: 24px; border-width: 1px; border-style: solid; border-color: rgb(204, 204, 204); padding: 0px 3px; background: rgb(246, 246, 246);\">\r\n" + 
				"                    <p style=\"font-weight: bold;\">" + "집행금액" + "<br></p>\r\n" + 
				"                </td>\r\n" + 
				"            </tr>\r\n" +
				subRslt + 
				"        </tbody>\r\n" + 
				"    </table>\r\n";
		
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
						"        <spam style=\"color: rgb(0, 0, 0); font-family: &quot;Malgun Gothic&quot;; font-size: medium;\">" + "바로가기" + " :&nbsp;</spam><a href=\"" + frontUrl + "/stndinfr/wkplinvsmgntdtllup" + "/" + tempBasic.get("wkpl_id") + "-" + tempBasic.get("invs_year") + "\" style=\"font-family: &quot;Malgun Gothic&quot;; font-size: medium;\"target=\"_blank\">" + frontUrl + "/stndinfr/wkplinvsmgntdtllup" + "/" + tempBasic.get("wkpl_id") + "-" + tempBasic.get("invs_year") + "</a>\r\n" + 
						"    </p>\r\n" +
				"</div>";
		
		Map<String, Object> aprvParam = new HashMap<>();
//					결재 키 
		String aprv_no = UUID.randomUUID().toString().replace("-", "");
		aprvParam.put("aprv_no", aprv_no);
//					결재 코드
		aprvParam.put("arpv_cd", "50");		
//			 		업무번호
		aprvParam.put("biz_no", tempBasic.get("wkpl_id").toString() + '-' + tempBasic.get("invs_year").toString());
//					상신자
		aprvParam.put("aprv_usr_id", usrId);
//					결재 상태 코드 - 결재상신
		aprvParam.put("aprv_stt_cd", "1");		
//					후행메서드	
		aprvParam.put("aprv_mthd_nm", "aprvInvsMgntApprAf");
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
			schParam.put("invs_aprv_no", aprv_no);
			schParam.put("username", userInfo.getUsrId());
			n = service.updateInvsMgntAppr(schParam);
			
			data.put("Success", "Success");
			data.put("aprvNo", aprv_no);
			data.put("usrId", usrId);
		}
		
		return new ResponseEntity<>(data, HttpStatus.OK);
	}
}
